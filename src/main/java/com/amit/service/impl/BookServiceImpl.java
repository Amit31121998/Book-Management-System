package com.amit.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amit.dto.BookDTO;
import com.amit.entity.BookEntity;
import com.amit.repo.BookRepository;
import com.amit.service.BookService;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository bookRepo;

	@Override
	public boolean saveBook(BookDTO bookDTO) {

		BookEntity book = new BookEntity();
		bookRepo.save(convertToEntity(bookDTO));
		return true;
	}

	@Override
	public BookDTO updateBook(BookDTO bookDTO) {

		BookEntity convertToEntity = convertToEntity(bookDTO);

		BookEntity save = bookRepo.save(convertToEntity);

		return convertToDTO(save);
	}

	@Override
	public BookDTO getBookById(Long id) {
		Optional<BookEntity> findById = bookRepo.findById(id);

		if (findById.isPresent()) {
			BookEntity bookEntity = findById.get();

			BookDTO toDTO = convertToDTO(bookEntity);

			return toDTO;
		}
		return null;
	}

	@Override
	public List<BookDTO> getAllBooks() {

		return bookRepo.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public boolean deleteBook(Long id) {
		bookRepo.deleteById(id);
		return true;
	}

	public BookDTO convertToDTO(BookEntity entity) {
		BookDTO dto = new BookDTO();
		BeanUtils.copyProperties(entity, dto);
		return dto;
	}

	public BookEntity convertToEntity(BookDTO dto) {
		BookEntity entity = new BookEntity();
		BeanUtils.copyProperties(dto, entity);
		return entity;
	}
}
