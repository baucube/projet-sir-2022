package com.ca.formation.formationdemo1.controllers.api;

import com.ca.formation.formationdemo1.exception.ResourceNotFoundException;
import com.ca.formation.formationdemo1.models.Personne;
import com.ca.formation.formationdemo1.models.Role;
import com.ca.formation.formationdemo1.services.PersonneService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/v2/personnes")
public class ApiPersonneController {

    private final PersonneService personneService;

    public ApiPersonneController(PersonneService personneService) {
        this.personneService = personneService;
    }

    /**
     * - GET /api/v1/personnes
     * - POST /api/v1/personnnes
     * - PATCH /api/v1/personnnes/{id}
     * - PUT /api/v1/personnnes/{id}
     * - GET /api/v1/personnes/{id}
     * - DELETE /api/v1/personnes/{id}
     * - GET /api/v1/personnes/search?nom="Jean"
     */

    @PreAuthorize("hasAuthority('"+ Role.READ+"')")
    @GetMapping("/hello")
    public String hello(){
        return "Bonjour tout le monde";
    }

    @PreAuthorize("hasAuthority('"+ Role.ADMIN+"')")
    @GetMapping("/bye")
    public  String byebye(){
        return "Bye bye";
    }

    /**
     * /api/v1/personnes
     * @return List Personne
     */
    @GetMapping
    public ResponseEntity<List<Personne>> getToutPersonne(){
        List<Personne> personnes = personneService.getPersonnes();
        return ResponseEntity.ok().body(personnes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Personne> getPersonne(@PathVariable(value="id") Long id) throws ResourceNotFoundException {
        Personne personne = personneService.getPersonne(id);
       return ResponseEntity.ok().body(personne);
    }

    @PostMapping
    public ResponseEntity<Personne> addPersonne(@RequestBody Personne personne){
        Personne personneResponse = personneService.addPersonne(personne);
        return ResponseEntity.ok().body(personneResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Personne> updatePersonne(@PathVariable(value="id") Long id, @RequestBody Personne personneRequest ) throws Exception {

        Personne personne = personneService.updatePersonne(id, personneRequest);

        return ResponseEntity.ok().body(personne);
    }

    @DeleteMapping("/{id}")
    public String deletePersonne(@PathVariable(value="id") Long id){
        personneService.deletePersonne(id);
        return "OK";
    }

    @GetMapping("/search")
    public ResponseEntity<List<Personne>> getPersonneParNom(@RequestParam(name = "nom") String nom){
        List<Personne> personnes = personneService.getPersonneParNom(nom);
        return ResponseEntity.ok().body(personnes);
    }

    @GetMapping("/noms")
    ResponseEntity<List<Personne>> findByNomLike(String nom){
        return new ResponseEntity<List<Personne>>(personneService.findByNomLike("%"+nom+"%"), HttpStatus.OK);
    }

    @GetMapping("/prenoms")
    ResponseEntity<List<Personne>> findByPrenomLike(String prenom){
        return new ResponseEntity<List<Personne>>(personneService.findByPrenomLike("%"+prenom+"%"),HttpStatus.OK);
    }

    @GetMapping("/nombrepersonnes")
    public Long count(){
        return personneService.count();
    }

}
