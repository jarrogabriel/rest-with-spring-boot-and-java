package br.com.springjava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.springjava.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> { 

}
