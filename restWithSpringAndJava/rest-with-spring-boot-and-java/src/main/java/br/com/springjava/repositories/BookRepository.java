package br.com.springjava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.springjava.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> { 

}
