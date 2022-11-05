package br.com.springjava.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.springjava.data.vo.v1.PersonVO;
import br.com.springjava.exceptions.ResourceNotFoundException;
import br.com.springjava.mapper.DozerMapper;
import br.com.springjava.model.Person;
import br.com.springjava.repositories.PersonRepository;

@Service
public class PersonServices {

	private Logger logger = Logger.getLogger(PersonServices.class.getName());

	@Autowired
	PersonRepository personRepository;

	public List<PersonVO> findAll() {

		logger.info("Finding all people!");

		return DozerMapper.parseListObjects(personRepository.findAll(), PersonVO.class);
	}

	public PersonVO findById(Long personId) {

		logger.info("Finding one person!");

		Person person = personRepository.findById(personId)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

		return DozerMapper.parseObject(person, PersonVO.class);
	}

	public PersonVO create(PersonVO personVO) {

		logger.info("Creating one person");

		// Converte o recebido personVO para Person
		Person person = DozerMapper.parseObject(personVO, Person.class);

		// Salva o Person convertido e já converte o resultado do save pra PersonVO para
		// poder retornar o método
		return DozerMapper.parseObject(personRepository.save(person), PersonVO.class);

	}

	public PersonVO update(PersonVO personVO) {

		logger.info("Updating one person");

		// Converte o recebido personVO para Person
		Person person = DozerMapper.parseObject(personVO, Person.class);

		// Salva o Person convertido e já converte o resultado do save pra PersonVO para
		// poder retornar o método
		return DozerMapper.parseObject(personRepository.save(person), PersonVO.class);

	}

	public void delete(Long personId) {

		logger.info("Deleting one person");

		Person person = personRepository.findById(personId)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

		personRepository.delete(person);

	}

}
