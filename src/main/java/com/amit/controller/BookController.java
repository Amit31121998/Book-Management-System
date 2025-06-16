package com.amit.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.amit.dto.BookDTO;
import com.amit.entity.BookEntity;
import com.amit.repo.BookRepository;
import com.amit.service.BookService;
import com.amit.service.impl.BookServiceImpl;

@Controller
public class BookController {

	@Autowired
	private BookService bookService;
	
	@Autowired
	private BookServiceImpl bookServiceImp;
	
	@Autowired
	private BookRepository bookRepo;
	
	
	
	@PostMapping("/book")
	public String saveData(@Validated @ModelAttribute("book") BookDTO book, BindingResult result, Model model) {
	    if (result.hasErrors()) {
	        model.addAttribute("book", book); 
	        return "index";
	    }

	    BookEntity saved = bookRepo.save(bookServiceImp.convertToEntity(book));
	    model.addAttribute("msg", "Book Saved");
	    model.addAttribute("book", new BookDTO()); 
	    return "index";
	}

	
	@GetMapping("/")
	public String loadForm(Model model) {
	    model.addAttribute("book", new BookDTO());  
	    return "index";
	}
	
	@GetMapping("/books")
	public String getData(Model model) {
		model.addAttribute("book", new BookDTO());
		model.addAttribute("books", bookRepo.findAll());
		return "data";
	}
	
	
    @GetMapping("/list")
    public String showBookList(Model model, @ModelAttribute("msg") String msg) {
        List<BookEntity> books = bookRepo.findAll();
        model.addAttribute("books", books);
        model.addAttribute("msg", msg);
        return "data";
    }

    @GetMapping("/delete")
    public String deleteBook(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        bookRepo.deleteById(id);
        redirectAttributes.addFlashAttribute("msg", "Deleted successfully");
        return "redirect:/list";
    }

    @GetMapping("/edit")
    public String editBook(@RequestParam("id") Long id, Model model) {
        Optional<BookEntity> findById = bookRepo.findById(id);
        if (findById.isPresent()) {
            BookEntity book = findById.get();
            
            BookDTO bookDto = bookServiceImp.convertToDTO(book);
            model.addAttribute("book", bookDto);
        }
        return "index";
    }

}
