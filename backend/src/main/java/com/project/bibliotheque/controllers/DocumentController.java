package com.project.bibliotheque.controllers;

import com.project.bibliotheque.dtos.DocumentDto;
import com.project.bibliotheque.entities.*;
import com.project.bibliotheque.repositories.*;
import com.project.bibliotheque.services.DocumentService;
import com.project.bibliotheque.services.RapportService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8081", "https://library-fz9o.onrender.com/"})
@RestController
@RequestMapping("/documents")
public class DocumentController {
    private final DocumentService documentService;
    private final LivreRepository livreRepository;
    private final CasseteVideoRepository casseteVideoRepository;
    private final JournaleRepository journaleRipositry;
    private final DisqueCompacteRepository disqueCompacteRepository;
    private final PeriodiqueRepository periodiqueRepository;
    private final DiscripteurRepository discripteurRepository;
    private final RapportService rapportService;
    private final DocumentRepository documentRepository;

    public DocumentController(DocumentService documentService, DiscripteurRepository discripteurRepository, LivreRepository livreRepository, JournaleRepository journaleRipositry, DisqueCompacteRepository disqueCompacteRepository, CasseteVideoRepository casseteVideoRepository , PeriodiqueRepository periodiqueRepository, RapportService rapportService, DocumentRepository documentRepository){
        this.documentService = documentService;
        this.discripteurRepository = discripteurRepository;
        this.livreRepository = livreRepository;
        this.journaleRipositry = journaleRipositry;
        this.disqueCompacteRepository = disqueCompacteRepository;
        this.casseteVideoRepository = casseteVideoRepository;
        this.periodiqueRepository = periodiqueRepository;
        this.rapportService = rapportService;
        this.documentRepository = documentRepository;
    }
    @GetMapping("/{id}")
    public DocumentDto getDocumentById(@PathVariable Long id){
        return documentService.getDocumentById(id);
    }

    @GetMapping
    public Page<DocumentDto> getAllDocuments(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "8") int size,@RequestParam(defaultValue = "") String search){
        return documentService.getAllDocuments(page,size,search);
    }
    @GetMapping("/type")
    public Page<DocumentDto> getDocumentsByType(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "8") int size,@RequestParam(defaultValue = "") String search,@RequestParam String type){
        return documentService.getDocumentsByType(page,size,search,type);
    }
    @GetMapping("/language")
    public Page<DocumentDto> getDocumentsByLangue(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "8") int size,@RequestParam(defaultValue = "") String search,@RequestParam String langue){
        return documentService.getDocumentsByLangue(page,size,search,langue);
    }
    @GetMapping("/search")
    public Page<DocumentDto> getDocumentsByTitreContaining(@RequestParam String titre, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "8") int size){
        Pageable pageable = PageRequest.of(page, size);
        return documentService.getDocumentsByTitreContaining(titre, pageable);
    }
    @PostMapping
    public ResponseEntity<Map<String,String>> addDocument(@RequestBody Map<Object,Object> body){
        String titre = (String) body.get("titre");
        String auteur = (String) body.get("auteur");
        String description = (String) body.get("description");
        String imgUrl = (String) body.get("imageUrl");
        Long fraixExige = Long.parseLong((String) body.get("fraixExige"));
        Integer classificationDewey = Integer.parseInt((String) body.get("classificationDewey"));
        Integer nombreExemplaire = Integer.parseInt((String) body.get("nombreExemplaire"));
        boolean estFortementdemander = (boolean) body.get("estFortementDemande");
        Object datePublicationObj = body.get("datePublication");
        Date datePublication = null;
        if (datePublicationObj instanceof Date) {
            datePublication = (Date) datePublicationObj;
        } else if (datePublicationObj instanceof String) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            try {
                datePublication = formatter.parse((String) datePublicationObj);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        String langue = (String) body.get("langue");
        String type = (String) body.get("type");
        String keywords = (String) body.get("discripteurs");
        String format = (String) body.get("format");
        Integer edition =null;
        List<String> keywordList = Arrays.asList(keywords.split("\\s*,\\s*"));
        Integer duree = null;
        if(type.equals("DisqueCompacte") || type.equals("CasseteVideo")){
            edition = Integer.parseInt((String) body.get("edition"));
             duree = Integer.parseInt((String) body.get("duree"));
        }
        //navigate into the kywords and get the discripteur
        List<Discripteur> discripteurs = new ArrayList<>();
        for(String keyword : keywordList) {
            // You can access each keyword here
            Discripteur discripteur = discripteurRepository.findById(Long.parseLong(keyword)).orElse(null);
            discripteurs.add(discripteur);
        }

        switch (type){
            case "Livre":
                Livre livre = new Livre();
                livre.setTitre(titre);
                livre.setAuteur(auteur);
                livre.setDescription(description);
                livre.setImgUrl(imgUrl);
                livre.setFraixExige(fraixExige);
                livre.setClassification(classificationDewey);
                livre.setNombreExemplaire(nombreExemplaire);
                livre.setEstFortementdemander(estFortementdemander);
                livre.setDatePublication(datePublication);
                if(estFortementdemander){
                    livre.setEstPretable(false);
                }
                livre.setLangue(langue);
                livre.setEstPretable(true);
                livre.setEtat(EtatDocument.CREE);
                livre.setDiscripteurs(discripteurs);
                livreRepository.save(livre);
                break;
            case "Journale":
                Journale journale = new Journale();
                journale.setTitre(titre);
                journale.setAuteur(auteur);
                journale.setDescription(description);
                journale.setImgUrl(imgUrl);
                journale.setFraixExige(fraixExige);
                journale.setNombreExemplaire(nombreExemplaire);
                journale.setEstFortementdemander(false);
                journale.setDatePublication(datePublication);
                journale.setLangue(langue);
                journale.setEstPretable(false);
                journale.setEtat(EtatDocument.CREE);
                journale.setDiscripteurs(discripteurs);
                journaleRipositry.save(journale);
                break;
                case "DisqueCompacte":
                    DisqueCompacte disqueCompacte = new DisqueCompacte();
                    disqueCompacte.setTitre(titre);
                    disqueCompacte.setAuteur(auteur);
                    disqueCompacte.setDescription(description);
                    disqueCompacte.setImgUrl(imgUrl);
                    disqueCompacte.setFraixExige(fraixExige);
                    disqueCompacte.setNombreExemplaire(nombreExemplaire);
                    disqueCompacte.setEstFortementdemander(true);
                    disqueCompacte.setDatePublication(datePublication);
                    disqueCompacte.setLangue(langue);
                    disqueCompacte.setEstPretable(false);
                    disqueCompacte.setEtat(EtatDocument.CREE);
                    disqueCompacte.setDiscripteurs(discripteurs);
                    disqueCompacte.setDuree(duree);
                    disqueCompacteRepository.save(disqueCompacte);
                    break;
            case "CasseteVideo":
                CasseteVideo casseteVideo = new CasseteVideo();
                casseteVideo.setTitre(titre);
                casseteVideo.setAuteur(auteur);
                casseteVideo.setDescription(description);
                casseteVideo.setImgUrl(imgUrl);
                casseteVideo.setFraixExige(fraixExige);
                casseteVideo.setNombreExemplaire(nombreExemplaire);
                casseteVideo.setEstFortementdemander(estFortementdemander);
                casseteVideo.setDatePublication(datePublication);
                casseteVideo.setLangue(langue);
                casseteVideo.setEtat(EtatDocument.CREE);
                casseteVideo.setDiscripteurs(discripteurs);
                casseteVideo.setFormat(FormatExtention.valueOf(format));
                casseteVideo.setDuree(duree);
                casseteVideo.setEstPretable(true);
                casseteVideo.setDuree(duree);
                casseteVideoRepository.save(casseteVideo);
                break;
            case "Periodique":
                Periodique periodique = new Periodique();
                periodique.setTitre(titre);
                periodique.setAuteur(auteur);
                periodique.setEstPretable(false);
                periodique.setDescription(description);
                periodique.setImgUrl(imgUrl);
                periodique.setFraixExige(fraixExige);
                periodique.setNombreExemplaire(nombreExemplaire);
                periodique.setEstFortementdemander(estFortementdemander);
                periodique.setDatePublication(datePublication);
                periodique.setLangue(langue);
                periodique.setEtat(EtatDocument.CREE);
                periodique.setDiscripteurs(discripteurs);
                periodique.setEstPretable(false);
                periodique.setEdition(edition);
                periodiqueRepository.save(periodique);
                break;
        }
        return ResponseEntity.ok(Map.of("success", "Document added successfully"));
    }
    @PutMapping("/{id}")
    public DocumentDto updateDocument(@PathVariable Long id, @RequestBody DocumentDto documentDto){
        documentDto.setIdDocument(id);
        return documentService.updateDocument(documentDto);
    }
    @PutMapping("/perdu")
    public ResponseEntity<Map<String,String>> setDocumentPerdu(@RequestParam Long id){
        Document document = documentRepository.findById(id).orElse(null);
        document.setNombreExemplaire(document.getNombreExemplaire()-1);
        documentRepository.save(document);

        rapportService.addRapport(new Date(), "perdu", document.getIdDocument());
        return ResponseEntity.ok(Map.of("message", "exmemplaire marqué comme perdu"));
    }
    @DeleteMapping("/{id}")
    public void deleteDocumentById(@PathVariable Long id){
        documentService.deleteDocumentById(id);
    }
}