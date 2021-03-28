package br.com.dextra.bootcamp.MentorMatch.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dextra.bootcamp.MentorMatch.models.Mentor;
import br.com.dextra.bootcamp.MentorMatch.models.Mentored;
import br.com.dextra.bootcamp.MentorMatch.models.MentoredResponse;
import br.com.dextra.bootcamp.MentorMatch.models.exceptions.HasMentorException;
import br.com.dextra.bootcamp.MentorMatch.models.exceptions.UnexistentEntityException;
import br.com.dextra.bootcamp.MentorMatch.repositories.MentoredRepository;

@Service
public class MentoredService {

	@Autowired
	MentoredRepository mentoredRepository;
	
	@Autowired
	MentorService mentorService;
	
	@Autowired 
	MatchService matchService;
	
	
	public Mentored create(Mentored mentored) {
		return mentoredRepository.save(mentored);
	}

	public List<MentoredResponse> list() {
		return mentoredRepository.findAll().stream()
				.map(mentored -> new MentoredResponse(mentored))
				.collect(Collectors.toList());
	}
	
	public MentoredResponse findOne(Long id) throws UnexistentEntityException {
		Mentored mentored = this.findOnDataBaseById(id);
		return new MentoredResponse(mentored);
	}

	public Mentored update(Mentored mentored) {
		return mentoredRepository.save(mentored);
	}
	
	public void delete(Long id) {
		mentoredRepository.deleteById(id);
	}

	public void likeMentor(Long mentoredId, Long mentorId) throws UnexistentEntityException, HasMentorException {

		Mentored mentored = this.findOnDataBaseById(mentoredId);
		Mentor mentor = mentorService.findOnDataBaseById(mentorId);
		
		if(mentored.getMentor() != null) {
			Mentor currentMentor = mentored.getMentor();
			currentMentor.getMentored().remove(mentored);
			currentMentor.getLikedList().remove(mentored);
			mentorService.update(currentMentor);
			mentored.setMentor(null);
		} 
		
		mentored.getLikedList().add(mentor);
		matchService.checkMatch(mentor, mentored, Mentored.class);
	}
	
	public Mentored findOnDataBaseById(Long id) throws UnexistentEntityException {
		Optional<Mentored> mentored = mentoredRepository.findById(id);
		if (mentored.isEmpty()) {
			throw new UnexistentEntityException("O mentorado não existe no banco de dados");
		}
		return mentored.get();
	}
}
