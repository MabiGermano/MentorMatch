package br.com.dextra.bootcamp.MentorMatch.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dextra.bootcamp.MentorMatch.models.Mentor;
import br.com.dextra.bootcamp.MentorMatch.models.Mentored;

@Service
public class MatchService {

	@Autowired
	MentorService mentorService;
	
	@Autowired
	MentoredService mentoredService;
	
	public void checkMatch(Mentor mentor, Mentored mentored, Class<?> classOperation) {
	
		if(mentor.getLikedList().contains(mentored) && 
				mentored.getLikedList().contains(mentor)) {
				
				mentor.getMentored().add(mentored);
				mentored.setMentor(mentor);
				
				mentorService.update(mentor);
				mentoredService.update(mentored);
			}else {
				
				if(classOperation == Mentor.class) 
					mentorService.update(mentor);
				else 
					mentoredService.update(mentored);
				
			}
	}

}
