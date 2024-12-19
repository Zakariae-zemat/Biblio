package com.project.bibliotheque.security;
import com.project.bibliotheque.entities.Client;
import com.project.bibliotheque.entities.Utilisateur;
import com.project.bibliotheque.repositories.ClientRepository;
import com.project.bibliotheque.repositories.UtilisateurRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;
@CrossOrigin(origins = {"http://localhost:5173","http://localhost:8081/", "https://library-fz9o.onrender.com/"})
@RestController
@RequestMapping("/auth")
public class SecurityController {
    private final JwtEncoder jwtEncoder;
    private final AuthenticationManager authentiationManager;
    private final UtilisateurRepository utilisateurRepository;
    private final ClientRepository clientRepository;
    public SecurityController(AuthenticationManager authenticationManager, JwtEncoder jwtEncoder, UtilisateurRepository utilisateurRepository,ClientRepository clientRepository){
        this.authentiationManager= authenticationManager;
        this.jwtEncoder = jwtEncoder;
        this.utilisateurRepository = utilisateurRepository;
        this.clientRepository = clientRepository;
    }

    @GetMapping("/profile")
    public Authentication authentication(Authentication authentication){
        return authentication;
    }
    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> login(@RequestBody Map<Object,Object> body){
        String email = (String) body.get("email");
        String password = (String) body.get("password");
        try {
            Instant instant = Instant.now();
            Authentication authentication = authentiationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            String subject = authentication.getName();
            String scope = authentication.getAuthorities()
                    .stream().map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(" "));

            JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                    .issuedAt(instant)
                    .expiresAt(instant.plus(10, ChronoUnit.DAYS))
                    .subject(subject)
                    .claim("scope",scope)
                    .build();
            JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(
                    JwsHeader.with(MacAlgorithm.HS256).build(),
                    jwtClaimsSet
            );
            String jwt = jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
            return ResponseEntity.ok(Map.of("token",jwt, "role", scope, "email", email));
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email or password is incorrect"));
        } catch (Exception e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "An error occurred while processing your request"));
        }
    }
    @PostMapping("/register")
    public ResponseEntity<Map<String,String>> register(@RequestBody Map<Object,Object> body) throws ParseException {
        String email = (String) body.get("email");
        String password = (String) body.get("password");
        String nom = (String) body.get("nom");
        String prenom =  (String) body.get("prenom");
        String role = "CLIENT";
        String telephone = (String) body.get("telephone");
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String d = (String) body.get("naissance");
        String addresse = (String) body.get("addresse");
        Date naissance = formatter.parse(d);


        Utilisateur existingUser = utilisateurRepository.findByEmail(email);
        if (existingUser != null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email is already in use"));
        }

        Client newUser = new Client();
        newUser.setEmail(email);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);
        newUser.setPassword(encodedPassword);
        newUser.setNom(nom);
        newUser.setPrenom(prenom);
        newUser.setRole(role);
        newUser.setTelephone(telephone);
        newUser.setNaissance(naissance);
        newUser.setAddresse(addresse);
        clientRepository.save(newUser);
        String scope = newUser.getRole();
        // Generate token for new user
        Instant instant = Instant.now();
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .issuedAt(instant)
                .expiresAt(instant.plus(10, ChronoUnit.MINUTES))
                .subject(newUser.getNom())
                .claim("scope", newUser.getRole())
                .build();
        JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(
                JwsHeader.with(MacAlgorithm.HS256).build(),
                jwtClaimsSet
        );
        String jwt = jwtEncoder.encode(jwtEncoderParameters).getTokenValue();

        return ResponseEntity.ok(Map.of("token",jwt, "role", scope, "email", email));
    }
}
