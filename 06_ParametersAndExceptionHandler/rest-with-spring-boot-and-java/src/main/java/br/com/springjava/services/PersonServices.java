package br.com.springjava.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import br.com.springjava.model.Person;

@Service
public class PersonServices {

	private static final AtomicLong counter = new AtomicLong();

	private Logger logger = Logger.getLogger(PersonServices.class.getName());

	public List<Person> findAll() {
		
		logger.info("Finding all people!");
		List<Person> persons = new ArrayList<Person>();

		for (int i = 1; i <= 8; i++) {
			Person person = mockPerson(i);
			persons.add(person);
		}

		return persons;
	}


	public Person findById(String idString) {

		logger.info("Finding one person!");

		Person person = new Person();

		person.setId(counter.incrementAndGet());
		person.setFirstName("Gabriel");
		person.setLastName("Jarró");
		person.setAddress("São Vicente - São Paulo");
		person.setGender("Male");

		return person;
	}
	
	private Person mockPerson(int i) {
		Person person = new Person();

		person.setId(counter.incrementAndGet());
		person.setFirstName("Gabriel " + i);
		person.setLastName("Jarró da Terra: " + i);
		person.setAddress("São Vicente - São Paulo da Terra: " + i);
		person.setGender("Male cód.: "+ i);

		return person;
	}


	public Person create(Person person) {
		
		logger.info("Creating one person");
		person.setId(counter.incrementAndGet());
		return person;
		
	}
	
	
	public Person update(Person person) {
		
		logger.info("Updating one person");
		person.setId(counter.incrementAndGet());
		return person;
		
	}


	public void delete(Integer personId) {
		
		logger.info("Deleting one person");
		
	}
	
}
