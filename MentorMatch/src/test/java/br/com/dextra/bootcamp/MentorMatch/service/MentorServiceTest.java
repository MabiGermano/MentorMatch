package br.com.dextra.bootcamp.MentorMatch.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import br.com.dextra.bootcamp.MentorMatch.models.Mentor;
import br.com.dextra.bootcamp.MentorMatch.models.MentorResponse;
import br.com.dextra.bootcamp.MentorMatch.models.exceptions.UnexistentEntityException;
import br.com.dextra.bootcamp.MentorMatch.repositories.MentorRepository;
import br.com.dextra.bootcamp.MentorMatch.services.MentorService;

@SpringBootTest
public class MentorServiceTest {
	
	@Autowired
	MentorService mentorService;
	
	@MockBean
	MentorRepository mentorRepository;
	
	@Test
	public void creatingSuccess() {
		
		// Cenário
		Mentor mentor = new Mentor();
		mentor.setName("Maria");
		mentor.setKnowledge("Desenvedora");
		mentor.setBio("Desenvolvimento em java e javascript");
		
		Mockito.when(mentorRepository.save(Mockito.any(Mentor.class))).thenAnswer(i -> {
			Mentor mentorToReturn = i.getArgument(0);
			mentorToReturn.setId(1L);
			return mentorToReturn;
		});
		
		// Ação
		Mentor returnedMentor = mentorService.create(mentor);
		// Verificação
		assertThat(returnedMentor).isEqualTo(mentor);
	}
	
	
	@Test
	public void listSuccess() {
		
		//Cenário
		List<Mentor> mentorList = new ArrayList<Mentor>();
		mentorList.add(new Mentor(1L, "Ana", "Desenvolvedora", "Especialista em java"));
		mentorList.add(new Mentor(2L, "Mariana", "Desenvolvedora", "Especialista em javascript"));
		mentorList.add(new Mentor(3L, "Carol", "Desenvolvedora", "Especialista em python"));
		mentorList.add(new Mentor(4L, "Ariana", "Designer", "Especialista em prototipação de telas"));
		
		Mockito.when(mentorRepository.findAll())
		.thenReturn(mentorList);
		
		//Ação
		List<MentorResponse> returnedList = mentorService.list();
		
		//Verificação
		assertThat(returnedList).isNotEmpty();
	}
	
	@Test
	public void emptyList() {
		
		//Cenário
		List<Mentor> mentorList = new ArrayList<Mentor>();
		
		Mockito.when(mentorRepository.findAll())
		.thenReturn(mentorList);
		
		//Ação
		List<MentorResponse> returnedList = mentorService.list();
		
		//Verificação
		assertThat(returnedList).isEmpty();
	}
	
	@Test
	public void findOneSuccess() throws UnexistentEntityException {
		// Cenário
		Long id = 1L;
		Mentor mentor = new Mentor(1L, "Maria", "Desenvolvedora", "Desenvolvimento em java e javascript");
	
		Mockito.when(mentorRepository.findById(id)).thenReturn(Optional.of(mentor));
		
		// Ação		
		MentorResponse returnedMentor = mentorService.findOne(id);
		// Verificação
		assertThat(returnedMentor).usingRecursiveComparison().isEqualTo(mentor);
	}
	
	@Test
	public void findOneException() {
		//Cenário
		Long id = 2L;
		Mockito.when(mentorRepository.findById(id)).thenReturn(Optional.empty());
		
		
		//Verificação
		assertThrows(UnexistentEntityException.class, () -> {
			//Ação
			mentorService.findOne(id);
		});
	} 
	
}
