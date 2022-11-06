package br.com.springjava.mapper.custom;

import java.util.Date;

import org.springframework.stereotype.Service;

import br.com.springjava.data.vo.v2.PersonVOV2;
import br.com.springjava.model.Person;

@Service
public class PersonMapper {

	public PersonVOV2 convertEntityToVO(Person person) {

		PersonVOV2 personVOV2 = new PersonVOV2();
		personVOV2.setId(person.getId());
		personVOV2.setFirstName(person.getFirstName());
		personVOV2.setLastName(person.getLastName());
		personVOV2.setAddress(person.getAddress());
		personVOV2.setGender(person.getGender());
		personVOV2.setBirthDay(new Date());

		return personVOV2;
	}

	public Person convertVOToEntity(PersonVOV2 personVOV2) {

		Person person = new Person();
		person.setId(personVOV2.getId());
		person.setFirstName(personVOV2.getFirstName());
		person.setLastName(personVOV2.getLastName());
		person.setAddress(personVOV2.getAddress());
		person.setGender(personVOV2.getGender());
		// person.setBirthDay(new Date());

		return person;
	}

}
