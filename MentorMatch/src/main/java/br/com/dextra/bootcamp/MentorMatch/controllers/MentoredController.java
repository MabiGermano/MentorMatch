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

import br.com.dextra.bootcamp.MentorMatch.models.Mentored;
import br.com.dextra.bootcamp.MentorMatch.models.MentoredResponse;
import br.com.dextra.bootcamp.MentorMatch.models.exceptions.HasMentorException;
import br.com.dextra.bootcamp.MentorMatch.models.exceptions.UnexistentEntityException;
import br.com.dextra.bootcamp.MentorMatch.services.MentoredService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api
@RestController
@RequestMapping("api/v1/mentored")
public class MentoredController {

	@Autowired
	MentoredService mentoredService;

	@PostMapping
	@ApiOperation(value = "Criação de mentorada")
	@ApiResponses({
		@ApiResponse( code= HttpServletResponse.SC_CREATED, message = "Criando uma mentorada", response = Long.class )
	})
	public ResponseEntity<Long> create (@RequestBody Mentored mentored) {
		Mentored mentoredResponse = mentoredService.create(mentored);
		return ResponseEntity.status(HttpStatus.CREATED).body(mentoredResponse.getId());
	}
	
	@GetMapping
	@ApiOperation(value = "Listagem de mentoradas")
	@ApiResponses({
		@ApiResponse( code= HttpServletResponse.SC_OK, message = "Listagem de mentoradas", response = List.class),
		@ApiResponse( code= HttpServletResponse.SC_NO_CONTENT, message = "Não foram encontradas mentoradas", response = List.class)
	})
	public ResponseEntity<List<MentoredResponse>> list() {
		List<MentoredResponse> returnedList = mentoredService.list();
		if(returnedList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return ResponseEntity.ok(returnedList);
	}
	
	@GetMapping("/{id}")
	@ApiOperation(value = "Encontrar mentorada por ID")
	@ApiResponses({
		@ApiResponse( code= HttpServletResponse.SC_OK, message = "Encontrar mentorada por ID", response = MentoredResponse.class),
		@ApiResponse( code= HttpServletResponse.SC_NOT_FOUND, message = "Não foi encontrada a mentorada especificada", response = String.class)
	})
	public ResponseEntity<Object> findOne(@PathVariable(name = "id") Long id) {
		try {
			MentoredResponse mentored = mentoredService.findOne(id);
			return ResponseEntity.ok(mentored);
		} catch (UnexistentEntityException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@PutMapping("/{id}")
	@ApiOperation(value = "Alteração de mentorada")
	@ApiResponses({
		@ApiResponse( code= HttpServletResponse.SC_OK, message = "Alteração de mentorada", response = Long.class )
	})
	public ResponseEntity<Long> update(@PathVariable(name="id") Long id , @RequestBody Mentored mentored) {
		mentored.setId(id);
		Mentored returnedMentored = mentoredService.update(mentored);
		
		return ResponseEntity.status(HttpStatus.OK).body(returnedMentored.getId());
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Deleção de mentorada")
	@ApiResponses({
		@ApiResponse( code= HttpServletResponse.SC_OK, message = "Deleção de mentorada", response = String.class )
	})
	public ResponseEntity<String> delete(@PathVariable(name="id") Long id) {
		mentoredService.delete(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@PostMapping("/like/{mentorId}")
	public ResponseEntity<String> like (@PathVariable(name = "mentorId") Long mentorId, 
			@RequestHeader("id") Long mentoredId) {
		
		try {
			mentoredService.likeMentor(mentoredId, mentorId);
			return ResponseEntity.status(HttpStatus.ACCEPTED).build();
		} catch (UnexistentEntityException | HasMentorException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		} 
	}
}
