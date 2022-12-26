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

import br.com.springjava.data.vo.v1.BookVO;
import br.com.springjava.data.vo.v1.PersonVO;
import br.com.springjava.exceptions.RequiredObjectIsNullException;
import br.com.springjava.model.Book;
import br.com.springjava.model.Person;
import br.com.springjava.repositories.BookRepository;
import br.com.springjava.services.BookServices;
import br.com.springjava.unittests.mapper.mocks.MockBook;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BookServicesTest {

	@Autowired
	MockBook mockBook;

	@InjectMocks
	private BookServices bookService;

	@Mock
	BookRepository bookRepository;

	@BeforeEach
	void setUpMocks() throws Exception {

		mockBook = new MockBook();
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindById() {

		Book book = mockBook.mockEntity();
		book.setId(1L);
		Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
		BookVO bookVO = bookService.findById(1L);

		assertNotNull(bookVO);
		assertNotNull(bookVO.getKey());
		assertNotNull(bookVO.getLinks());

		assertNotNull(bookVO.toString().contains("links: "));
	}

	@Test
	void testFindAll() {
		List<Book> listBook = mockBook.mockEntityList();

		Mockito.when(bookRepository.findAll()).thenReturn(listBook);
		List<BookVO> listBookVO = bookService.findAll();

		assertNotNull(listBookVO);

		listBookVO.stream().forEach((bookVO) -> {
			assertNotNull(bookVO.getKey());
			assertNotNull(bookVO.getLinks());
			assertNotNull(bookVO.toString().contains("links: "));
		});
	}

	@Test
	void testCreate() {

		Book bookPrePersist = mockBook.mockEntity();

		Book bookPersisted = bookPrePersist;
		bookPersisted.setId(1L);

		Mockito.when(bookRepository.save(bookPrePersist)).thenReturn(bookPersisted);

		BookVO bookVO = mockBook.mockVO();

		BookVO bookVOPersisted = bookService.create(bookVO);

		assertNotNull(bookVOPersisted);
		assertNotNull(bookVOPersisted.getKey());
		assertNotNull(bookVOPersisted.getLinks());
		assertNotNull(bookVOPersisted.toString().contains("links: "));
	}

	@Test
	void testCreateWithNullBook() {

		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			bookService.create(null);
		});

		String expectedMessage = "It is not allowed to persist a null object!";
		String actualMessage = exception.getMessage();

		assertEquals(expectedMessage, actualMessage);
	}

	@Test
	void testUpdate() {
		Book bookPreUpdate = mockBook.mockEntity();
		bookPreUpdate.setId(1L);

		Book bookUpdated = bookPreUpdate;
		bookUpdated.setId(1L);

		Mockito.when(bookRepository.save(bookPreUpdate)).thenReturn(bookUpdated);

		BookVO bookVO = mockBook.mockVO();

		BookVO bookVOUpdated = bookService.update(bookVO);

		assertNotNull(bookVOUpdated);

		assertEquals(bookVO.getKey(), bookVOUpdated.getKey());

		assertNotNull(bookVOUpdated.getKey());
		assertNotNull(bookVOUpdated.getLinks());
		assertNotNull(bookVOUpdated.toString().contains("links: "));

	}

	@Test
	void testUpdateWithNullBook() {

		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			bookService.update(null);
		});

		String expectedMessage = "It is not allowed to persist a null object!";
		String actualMessage = exception.getMessage();

		assertEquals(expectedMessage, actualMessage);
	}

	@Test
	void testDelete() {

		Book bookPreDelete = mockBook.mockEntity();
		bookPreDelete.setId(1L);

		Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(bookPreDelete));

		bookService.delete(1L);

	}

}
