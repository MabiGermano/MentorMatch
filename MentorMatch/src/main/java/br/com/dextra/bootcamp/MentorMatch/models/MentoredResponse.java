package br.com.dextra.bootcamp.MentorMatch.models;

import java.util.List;

public class MentoredResponse {

	private String name;
	private String bio;
	private String knowledge;
	private Mentor mentor;
	private List<Mentor> likedList;
	
	public MentoredResponse(Mentored mentored) {
		this.name = mentored.getName();
		this.bio = mentored.getBio();
		this.knowledge = mentored.getKnowledge();
		this.mentor = mentored.getMentor();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getKnowledge() {
		return knowledge;
	}

	public void setKnowledge(String knowledge) {
		this.knowledge = knowledge;
	}

	public Mentor getMentor() {
		return mentor;
	}

	public void setMentor(Mentor mentor) {
		this.mentor = mentor;
	}

	public List<Mentor> getLikedList() {
		return likedList;
	}

	public void setLikedList(List<Mentor> likedList) {
		this.likedList = likedList;
	}
	
	
}
