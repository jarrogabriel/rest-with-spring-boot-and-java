package br.com.springjava.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.springjava.model.Person;
import br.com.springjava.services.PersonServices;

@RestController
@RequestMapping("/person")
public class PersonController {

	@Autowired
	private PersonServices personServices;
	// PersonServices personServices = new PersonServices();

	@GetMapping(value = "/{personId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Person findById(@PathVariable(value = "personId") Long personId) throws Exception {

		return personServices.findById(personId);
	}
	@GetMapping( produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Person> findAll() {
		
		return personServices.findAll();
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Person create(@RequestBody Person person) {
		
		return personServices.create(person);
	}
	
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Person update(@RequestBody Person person) {
		
		return personServices.update(person);
	}
	
	@DeleteMapping(value="/{personId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> delete(@PathVariable Long personId) {
		
		personServices.delete(personId);
		
		return ResponseEntity.noContent().build();
	}
	
}
