package br.com.springjava.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.springjava.exceptions.ResourceNotFoundException;
import br.com.springjava.model.Person;
import br.com.springjava.repositories.PersonRepository;

@Service
public class PersonServices {

	private Logger logger = Logger.getLogger(PersonServices.class.getName());

	@Autowired
	PersonRepository personRepository;

	public List<Person> findAll() {

		logger.info("Finding all people!");

		return personRepository.findAll();
	}

	public Person findById(Long personId) {

		logger.info("Finding one person!");

		Person person = new Person();

		return personRepository.findById(personId)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
	}

	public Person create(Person person) {

		logger.info("Creating one person");

		return personRepository.save(person);

	}

	public Person update(Person person) {

		logger.info("Updating one person");

		return personRepository.save(person);

	}

	public void delete(Long personId) {

		logger.info("Deleting one person");

		Person entity = personRepository.findById(personId)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		
		personRepository.delete(entity);
		
	}

}
