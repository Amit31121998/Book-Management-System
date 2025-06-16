package com.amit.service;

import java.util.List;

import com.amit.dto.BookDTO;

public interface BookService {

	boolean saveBook(BookDTO bookDTO);

	BookDTO updateBook(BookDTO bookDTO);

	BookDTO getBookById(Long id);

	List<BookDTO> getAllBooks();

	boolean deleteBook(Long id);
}
