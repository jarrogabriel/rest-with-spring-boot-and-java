package br.com.springjava.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.springjava.data.vo.v1.PersonVO;
import br.com.springjava.exceptions.RequiredObjectIsNullException;
import br.com.springjava.model.Person;
import br.com.springjava.repositories.PersonRepository;
import br.com.springjava.services.PersonServices;
import br.com.springjava.unittests.mapper.mocks.MockPerson;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServicesTest {

	@Autowired
	MockPerson mockPerson;

	@InjectMocks
	private PersonServices personService;

	@Mock
	PersonRepository personRepository;

	@BeforeEach
	void setUpMocks() throws Exception {

		mockPerson = new MockPerson();
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindById() {

		Person person = mockPerson.mockEntity();
		person.setId(1L);
		Mockito.when(personRepository.findById(1L)).thenReturn(Optional.of(person));
		PersonVO personVO = personService.findById(1L);

		assertNotNull(personVO);
		assertNotNull(personVO.getKey());
		assertNotNull(personVO.getLinks());

		assertNotNull(personVO.toString().contains("links: "));
	}

	@Test
	void testFindAll() {
		List<Person> listPerson = mockPerson.mockEntityList();

		Mockito.when(personRepository.findAll()).thenReturn(listPerson);
		List<PersonVO> listPersonVO = personService.findAll();

		assertNotNull(listPersonVO);

		listPersonVO.stream().forEach((personVO) -> {
			assertNotNull(personVO.getKey());
			assertNotNull(personVO.getLinks());
			assertNotNull(personVO.toString().contains("links: "));
		});
	}

	@Test
	void testCreate() {
		// - Cria uma instância de como Person chega no endpoint antes do save no
		// repository
		// - De modo ainda não persistido no banco
		Person personPrePersist = mockPerson.mockEntity();

		// - Cria outra instância de como Person deverá sair do save do repository
		// - A mesma Person só que "persistida" (com ID).
		Person personPersisted = personPrePersist;
		personPersisted.setId(1L);

		// Configuro o Mockito para que, quando houver um save dentro do método create,
		// ele suspenda o save (para não alterar nada no banco) e me retorne o Person
		// "persistido"
		Mockito.when(personRepository.save(personPrePersist)).thenReturn(personPersisted);

		// Após configurar a ação do mockito, eu instâncio uma PersonVO que será
		// "gravada"
		PersonVO personVO = mockPerson.mockVO();

		// Chamo o método de gravação e, o Mockito, dentro do método create, se
		// encarrega de suspender o save e simular o resultado do mesmo me retornando
		// personPersisted
		PersonVO personVOPersisted = personService.create(personVO);

		// E por último, configuro meus testes de modo a garantir que a Person gravada
		// não seja nulla, tenha um identificador único e possua links de suporte a
		// HATEOAS
		assertNotNull(personVOPersisted);
		assertNotNull(personVOPersisted.getKey());
		assertNotNull(personVOPersisted.getLinks());
		assertNotNull(personVOPersisted.toString().contains("links: "));
	}

	@Test
	void testCreateWithNullPerson() {

		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			personService.create(null);
		});

		String expectedMessage = "It is not allowed to persist a null object!";
		String actualMessage = exception.getMessage();

		assertEquals(expectedMessage, actualMessage);
	}

	@Test
	void testUpdate() {
		Person personPreUpdate = mockPerson.mockEntity();
		personPreUpdate.setId(1L);

		Person personUpdated = personPreUpdate;
		personUpdated.setId(1L);

		Mockito.when(personRepository.save(personPreUpdate)).thenReturn(personUpdated);

		PersonVO personVO = mockPerson.mockVO();

		PersonVO personVOUpdated = personService.update(personVO);

		assertNotNull(personVOUpdated);

		assertEquals(personVO.getKey(), personVOUpdated.getKey());

		assertNotNull(personVOUpdated.getKey());
		assertNotNull(personVOUpdated.getLinks());
		assertNotNull(personVOUpdated.toString().contains("links: "));

	}

	@Test
	void testUpdateWithNullPerson() {

		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			personService.update(null);
		});

		String expectedMessage = "It is not allowed to persist a null object!";
		String actualMessage = exception.getMessage();

		assertEquals(expectedMessage, actualMessage);
	}

	@Test
	void testDelete() {

		Person personPreDelete = mockPerson.mockEntity();
		personPreDelete.setId(1L);

		Mockito.when(personRepository.findById(1L)).thenReturn(Optional.of(personPreDelete));

		personService.delete(1L);

	}

}
