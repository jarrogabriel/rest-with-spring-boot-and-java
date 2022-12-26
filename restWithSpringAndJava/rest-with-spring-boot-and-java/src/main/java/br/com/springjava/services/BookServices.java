package br.com.springjava.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.springjava.controllers.BookController;
import br.com.springjava.data.vo.v1.BookVO;
import br.com.springjava.exceptions.RequiredObjectIsNullException;
import br.com.springjava.exceptions.ResourceNotFoundException;
import br.com.springjava.mapper.DozerMapper;
import br.com.springjava.model.Book;
import br.com.springjava.repositories.BookRepository;

@Service
public class BookServices {

	private Logger logger = Logger.getLogger(BookServices.class.getName());

	@Autowired
	BookRepository bookRepository;

	public List<BookVO> findAll() {

		logger.info("Finding all Books!");

		List<BookVO> listBooksVO = DozerMapper.parseListObjects(bookRepository.findAll(), BookVO.class);

		listBooksVO.stream().forEach(
				bookVO -> bookVO.add(linkTo(methodOn(BookController.class).findById(bookVO.getKey())).withSelfRel()));

		return listBooksVO;
	}

	public BookVO findById(Long bookId) {

		logger.info("Finding one Book!");

		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

		BookVO bookVO = DozerMapper.parseObject(book, BookVO.class);
		bookVO.add(linkTo(methodOn(BookController.class).findById(bookId)).withSelfRel());

		return bookVO;
	}

	public BookVO create(BookVO bookVO) {

		if (bookVO == null) {
			throw new RequiredObjectIsNullException();
		}

		logger.info("Creating one book");

		Book book = DozerMapper.parseObject(bookVO, Book.class);

		bookVO = DozerMapper.parseObject(bookRepository.save(book), BookVO.class);

		bookVO.add(linkTo(methodOn(BookController.class).findById(bookVO.getKey())).withSelfRel());

		return bookVO;
	}

	public BookVO update(BookVO bookVO) {

		if (bookVO == null) {
			throw new RequiredObjectIsNullException();
		}

		logger.info("Updating one book");

		Book book = DozerMapper.parseObject(bookVO, Book.class);

		bookVO = DozerMapper.parseObject(bookRepository.save(book), BookVO.class);
		bookVO.add(linkTo(methodOn(BookController.class).findById(bookVO.getKey())).withSelfRel());
		return bookVO;
	}

	public void delete(Long bookId) {

		logger.info("Deleting one book");

		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

		bookRepository.delete(book);

	}

}
