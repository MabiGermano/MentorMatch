package br.com.dextra.bootcamp.MentorMatch.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Mentor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;
	
	private String knowledge;
	
	private String bio;
	
	@ManyToMany
	@JsonIgnore
	private List<Mentored> likedList;
	
	@OneToMany(mappedBy = "mentor")
	@JsonIgnore
	private List<Mentored> mentored;
	
	public Mentor() {}
	
	public Mentor(Long id, String name, String knowledge, String bio) {
		this.id = id;
		this.name = name;
		this.knowledge = knowledge;
		this.bio = bio;
	}
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKnowledge() {
		return knowledge;
	}

	public void setKnowledge(String knowledge) {
		this.knowledge = knowledge;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public List<Mentored> getLikedList() {
		return likedList;
	}

	public void setLikedList(List<Mentored> likedList) {
		this.likedList = likedList;
	}

	public List<Mentored> getMentored() {
		return mentored;
	}

	public void setMentored(List<Mentored> mentored) {
		this.mentored = mentored;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bio == null) ? 0 : bio.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((knowledge == null) ? 0 : knowledge.hashCode());
		result = prime * result + ((likedList == null) ? 0 : likedList.hashCode());
		result = prime * result + ((mentored == null) ? 0 : mentored.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mentor other = (Mentor) obj;
		if (bio == null) {
			if (other.bio != null)
				return false;
		} else if (!bio.equals(other.bio))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (knowledge == null) {
			if (other.knowledge != null)
				return false;
		} else if (!knowledge.equals(other.knowledge))
			return false;
		if (likedList == null) {
			if (other.likedList != null)
				return false;
		} else if (!likedList.equals(other.likedList))
			return false;
		if (mentored == null) {
			if (other.mentored != null)
				return false;
		} else if (!mentored.equals(other.mentored))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
