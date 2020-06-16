package com.novosad.bookshop.controllers;

import com.novosad.bookshop.entities.Book;
import com.novosad.bookshop.models.ApiResponse;
import com.novosad.bookshop.models.BookSearchModel;
import com.novosad.bookshop.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public ResponseEntity<ApiResponse> getBooks(@RequestParam(required = false) Integer page,
                                                @RequestParam(required = false) Integer itemsOnPage,
                                                BookSearchModel bookSearchModel) {

        Page<Book> booksPageByCriteria = bookService.getBooksPageByCriteria(page, itemsOnPage, bookSearchModel);
        return ResponseEntity.ok(new ApiResponse(booksPageByCriteria));
    }
}
