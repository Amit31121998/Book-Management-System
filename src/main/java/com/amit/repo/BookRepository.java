package com.amit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amit.entity.BookEntity;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
	
	

}
