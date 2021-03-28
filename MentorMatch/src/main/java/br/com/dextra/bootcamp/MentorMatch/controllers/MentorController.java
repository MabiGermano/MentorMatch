package br.com.dextra.bootcamp.MentorMatch.controllers;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dextra.bootcamp.MentorMatch.models.Mentor;
import br.com.dextra.bootcamp.MentorMatch.models.MentorResponse;
import br.com.dextra.bootcamp.MentorMatch.models.exceptions.HasMentorException;
import br.com.dextra.bootcamp.MentorMatch.models.exceptions.UnexistentEntityException;
import br.com.dextra.bootcamp.MentorMatch.services.MentorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api
@RestController
@RequestMapping("api/v1/mentor")
public class MentorController {

	@Autowired
	MentorService mentorService;

	@PostMapping
	@ApiOperation(value = "Criação de mentora")
	@ApiResponses({
		@ApiResponse( code= HttpServletResponse.SC_CREATED, message = "Criando uma mentora", response = Long.class )
	})
	public ResponseEntity<Long> create (@RequestBody Mentor mentor) {
		Mentor mentorResponse = mentorService.create(mentor);
		return ResponseEntity.status(HttpStatus.CREATED).body(mentorResponse.getId());
	}
	
	@GetMapping
	@ApiOperation(value = "Listagem de mentoras")
	@ApiResponses({
		@ApiResponse( code= HttpServletResponse.SC_OK, message = "Listagem de mentoras", response = List.class),
		@ApiResponse( code= HttpServletResponse.SC_NO_CONTENT, message = "Não foram encontradas mentoras", response = List.class)
	})
	public ResponseEntity<List<MentorResponse>> list() {
		List<MentorResponse> returnedList = mentorService.list();
		if(returnedList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return ResponseEntity.ok(returnedList);
	}
	
	@GetMapping("/{id}")
	@ApiOperation(value = "Encontrar mentora por ID")
	@ApiResponses({
		@ApiResponse( code= HttpServletResponse.SC_OK, message = "Encontrar mentora por ID", response = MentorResponse.class),
		@ApiResponse( code= HttpServletResponse.SC_NOT_FOUND, message = "Não foi encontrada a mentora especificada", response = String.class)
	})
	public ResponseEntity<Object> findOne(@PathVariable(name = "id") Long id) {
		try {
			MentorResponse mentor = mentorService.findOne(id);
			return ResponseEntity.ok(mentor);
		} catch (UnexistentEntityException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@PutMapping("/{id}")
	@ApiOperation(value = "Alteração de mentora")
	@ApiResponses({
		@ApiResponse( code= HttpServletResponse.SC_OK, message = "Alteração de mentora", response = Long.class )
	})
	public ResponseEntity<Long> update(@PathVariable(name="id") Long id , @RequestBody Mentor mentor) {
		mentor.setId(id);
		Mentor returnedMentor = mentorService.update(mentor);
		
		return ResponseEntity.status(HttpStatus.OK).body(returnedMentor.getId());
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Deleção de mentora")
	@ApiResponses({
		@ApiResponse( code= HttpServletResponse.SC_OK, message = "Deleção de mentora", response = String.class )
	})
	public ResponseEntity<String> delete(@PathVariable(name="id") Long id) {
		mentorService.delete(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@PostMapping("/like/{mentoredId}")
	public ResponseEntity<String> like (@PathVariable(name = "mentoredId") Long mentoredId, 
			@RequestHeader("id") Long mentorId) {
		
		try {
			mentorService.likeMentored(mentoredId, mentorId);
			return ResponseEntity.status(HttpStatus.ACCEPTED).build();
		} catch (UnexistentEntityException | HasMentorException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		} 
	}
}
