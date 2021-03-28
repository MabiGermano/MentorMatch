package br.com.dextra.bootcamp.MentorMatch.models.exceptions;

public class HasMentorException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public HasMentorException() {}
	public HasMentorException(String message) {
		super(message);
	}
}
