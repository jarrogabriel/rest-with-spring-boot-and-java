package br.com.springjava.unittests.mapper.mocks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.springjava.data.vo.v1.BookVO;
import br.com.springjava.model.Book;

public class MockBook {


    public Book mockEntity() {
        return mockEntity(1);
    }
    
    public BookVO mockVO() {
        return mockVO(1);
    }
    
    public List<Book> mockEntityList() {
        List<Book> books = new ArrayList<Book>();
        for (int i = 0; i < 14; i++) {
            books.add(mockEntity(i));
        }
        return books;
    }

    public List<BookVO> mockVOList() {
        List<BookVO> books = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            books.add(mockVO(i));
        }
        return books;
    }
    
    public Book mockEntity(Integer number) {
    	
        Book book = new Book();
        book.setId(number.longValue());
        book.setAuthor("RANDOM AUTHOR");
        book.setLaunchDate(new Date("01/01/1970"));
        book.setPrice((double) 25);
        book.setTitle("RANDOM TITLE");
        
        return book;
    }

    public BookVO mockVO(Integer number) {
        BookVO book = new BookVO();
        book.setKey(number.longValue());
        book.setAuthor("RANDOM AUTHOR");
        book.setLaunchDate(new Date("01/01/1970"));
        book.setPrice((double) 25);
        book.setTitle("RANDOM TITLE");
        
        return book;
    }

}
