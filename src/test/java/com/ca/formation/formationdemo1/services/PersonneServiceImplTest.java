package com.ca.formation.formationdemo1.services;

import com.ca.formation.formationdemo1.exception.ResourceNotFoundException;
import com.ca.formation.formationdemo1.models.Personne;
import com.ca.formation.formationdemo1.repositories.PersonneRepository;
import com.ca.formation.formationdemo1.services.impl.PersonneServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class PersonneServiceImplTest {

  @Mock
  PersonneRepository personneRepository;

  @InjectMocks
  private PersonneServiceImpl personneServiceImpl;

  @Test
  public void ajouterPersonne() {
    Personne personne = new Personne("tonux", "samb", 50);
    personne.setId(1L);
    when(personneRepository.save(any())).thenReturn(personne);

    Personne personneResponse = personneServiceImpl.addPersonne(new Personne("tonux", "samb", 50));

    assertNotNull(personneResponse);

    verify(personneRepository, atLeastOnce()).save(any());
  }

  // TODO: ajouter les autres tests sur methodes
  @Test
  public void deletePersonne(){
    doNothing().when(personneRepository).deleteById((Long) any());
    personneServiceImpl.deletePersonne(15L); //id de la personne ajouté dans ajouterPesonne personneControllerTest
    verify(personneRepository).deleteById((Long) any());
  }

  @Test //à corriger
  public void testGetPersonneParNom(){
    ArrayList<Personne> personneList = new ArrayList<>();

    when(personneRepository.findByNom((String) any())).thenReturn(personneList);

    List<Personne> actualPersonneParNom = personneServiceImpl.getPersonneParNom("TOMAVO");
    assertSame(personneList, actualPersonneParNom);
    assertTrue(actualPersonneParNom.isEmpty());
    verify(personneRepository).findByNom((String) any());
  }

  @Test //Juste
  public void testUpdatePersonne() throws ResourceNotFoundException {
    Personne personne = new Personne();
    personne.setAge(15);
    personne.setId(125L);
    personne.setNom("TOMAVO");
    personne.setPrenom("Clarisse");
    Optional<Personne> ofResult = Optional.of(personne);

    Personne personne1 = new Personne();
    personne1.setAge(18);
    personne1.setId(15L);
    personne1.setNom("ALFKI");
    personne1.setPrenom("AlfkiPre");
    when(personneRepository.save((Personne) any())).thenReturn(personne1);
    when(personneRepository.findById((Long) any())).thenReturn(ofResult);

    Personne personne2 = new Personne();
    personne2.setAge(18);
    personne2.setId(15L);
    personne2.setNom("SARR");
    personne2.setPrenom("Fidele");

    when(personneRepository.save((Personne) any())).thenReturn(personne1);
    when(personneRepository.findById((Long) any())).thenReturn(ofResult);

    assertSame(personne1, personneServiceImpl.updatePersonne(15L, personne2)); //la mise a jour passe
    verify(personneRepository).save((Personne) any());
    verify(personneRepository).findById((Long) any());
  }

  @Test
  public void getPersonnnes(){
    List<Personne> listPersonne = this.personneServiceImpl.getPersonnes();
    assertNotNull(listPersonne);
  }

  @Test
  public void getPersonne() throws ResourceNotFoundException {
    Personne personne = new Personne();
    personne.setAge(18);
    personne.setId(15L);
    personne.setNom("TOMAVO");
    personne.setPrenom("Clarisse");
    Optional<Personne> ofResult = Optional.of(personne);
    when(personneRepository.findById((Long) any())).thenReturn(ofResult);
    assertSame(personne, personneServiceImpl.getPersonne(15L));
    verify(personneRepository).findById((Long) any());
  }

  @Test
  public void findByNomLike() {
    String nom = "Barry";
    List<Personne> personnes = this.personneServiceImpl.findByNomLike(nom);
    assertEquals("Barry",nom);
  }

  @Test
  public void findByPrenom(){
    String prenoms = "Boubacar";
    List<Personne> personneList = this.personneServiceImpl.findByPrenomLike(prenoms);
    assertEquals("Boubacar", prenoms);
  }

  @Test
  public void count(){
    Long nombrePersonnes = this.personneServiceImpl.count();
    assertEquals(nombrePersonnes,personneRepository.count());
  }
}
