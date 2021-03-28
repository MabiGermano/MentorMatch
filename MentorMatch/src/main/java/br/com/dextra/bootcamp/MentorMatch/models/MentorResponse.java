package br.com.dextra.bootcamp.MentorMatch.models;

import java.util.List;

public class MentorResponse {

	private String name;
	private String bio;
	private String knowledge;
	private List<Mentored> mentored;
	private List<Mentored> likedList;
	
	public MentorResponse(Mentor mentor) {
		this.name = mentor.getName();
		this.bio = mentor.getBio();
		this.knowledge = mentor.getKnowledge();
		this.mentored = mentor.getMentored();
		this.likedList = mentor.getLikedList();
	}
	
	public String getName() {
		return this.name;
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

	public List<Mentored> getMentored() {
		return mentored;
	}

	public void setMentored(List<Mentored> mentored) {
		this.mentored = mentored;
	}

	public List<Mentored> getLikedList() {
		return likedList;
	}

	public void setLikedList(List<Mentored> likedList) {
		this.likedList = likedList;
	}
}
