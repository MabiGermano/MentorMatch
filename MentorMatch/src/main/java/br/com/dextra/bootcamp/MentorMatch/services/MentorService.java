package br.com.dextra.bootcamp.MentorMatch.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dextra.bootcamp.MentorMatch.models.Mentor;
import br.com.dextra.bootcamp.MentorMatch.models.MentorResponse;
import br.com.dextra.bootcamp.MentorMatch.models.Mentored;
import br.com.dextra.bootcamp.MentorMatch.models.exceptions.HasMentorException;
import br.com.dextra.bootcamp.MentorMatch.models.exceptions.UnexistentEntityException;
import br.com.dextra.bootcamp.MentorMatch.repositories.MentorRepository;

@Service
public class MentorService {

	@Autowired
	MentorRepository mentorRepository;
	
	@Autowired
	MentoredService mentoredService;
	
	@Autowired
	MatchService matchService;

	public Mentor create(Mentor mentor) {
		return mentorRepository.save(mentor);
	}

	public List<MentorResponse> list() {
		return mentorRepository.findAll().stream()
				.map(mentor -> new MentorResponse(mentor))
				.collect(Collectors.toList());
	}
	
	public MentorResponse findOne(Long id) throws UnexistentEntityException {
		Mentor mentor = this.findOnDataBaseById(id);
		return new MentorResponse(mentor);
	}

	public Mentor update(Mentor mentor) {
		return mentorRepository.save(mentor);
	}
	
	public void delete(Long id) {
		mentorRepository.deleteById(id);
	}

	public void likeMentored(Long mentoredId, Long mentorId) throws UnexistentEntityException, HasMentorException {

		Mentor mentor = this.findOnDataBaseById(mentorId);
		Mentored mentored = mentoredService.findOnDataBaseById(mentoredId);
		
		if (mentored.getMentor() != null) {
			throw new HasMentorException("Mentorada já possui mentora");
		}
		
		mentor.getLikedList().add(mentored);
		matchService.checkMatch(mentor, mentored, Mentor.class);
	}
	
	public Mentor findOnDataBaseById(Long id) throws UnexistentEntityException {
		Optional<Mentor> mentor = mentorRepository.findById(id);
		if (mentor.isEmpty()) {
			throw new UnexistentEntityException("O mentor não existe no banco de dados");
		}
		return mentor.get();
	}

}
