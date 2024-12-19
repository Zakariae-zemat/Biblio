package com.project.bibliotheque.controllers;

import com.project.bibliotheque.dtos.RapportDto;
import com.project.bibliotheque.services.RapportService;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8081", "https://library-fz9o.onrender.com/"})
@RestController
@RequestMapping("/rapports")
public class RapportController {
    private final RapportService rapportService;

    public RapportController(RapportService rapportService) {
        this.rapportService = rapportService;
    }
    @GetMapping
    public List<RapportDto> getAllRapports(){
        return rapportService.getAllRapports();
    }
    @GetMapping("/{id}")
    public RapportDto getRapportById(@PathVariable Long id){
        return rapportService.getRapportById(id);
    }
    @GetMapping("/date")
    public List<RapportDto> getRapportByDateStatistique(@RequestParam String dateStatistique) throws ParseException {
        //convert String dateStatistique to Date
        Date date = null ;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        date = formatter.parse((String) dateStatistique);
        return rapportService.getRapportByDateStatistique(date);
    }
    @GetMapping("/month")
    public List<RapportDto> getRapportByJourStatistique(@RequestParam int year,@RequestParam int month) throws ParseException {
        //convert String dateStatistique to Date
        return rapportService.getRapportByMonth(month,year);
    }
    @PostMapping("/add")
    public RapportDto addRapport(@RequestBody Map<String, String> body) throws ParseException {
        Date dateStatistique = null ;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        dateStatistique = formatter.parse(body.get("dateStatistique"));
        Long idDocument = Long.parseLong(body.get("idDocument"));
        String typeStatistique = body.get("typeStatistique");
        return rapportService.addRapport(dateStatistique,typeStatistique,idDocument);
    }

}
