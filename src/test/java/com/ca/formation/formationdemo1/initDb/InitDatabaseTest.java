package com.ca.formation.formationdemo1.initDb;

import com.ca.formation.formationdemo1.models.Role;
import com.ca.formation.formationdemo1.models.Utilisateur;
import com.ca.formation.formationdemo1.services.UtilisateurService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
class InitDatabaseTest {

    @Mock
    private UtilisateurService utilisateurService;

    @BeforeEach
    public void InitDatabase(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @Test
    void exception(){
        Exception exception = assertThrows(Exception.class, () ->
                utilisateurService.registration(new Utilisateur("michel@formation.sn", "Passer@123", "Michel", Set.of(new Role(Role.READ)))));
    }
}