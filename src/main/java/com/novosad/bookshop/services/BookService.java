package com.novosad.bookshop.services;

import com.novosad.bookshop.entities.Book;
import com.novosad.bookshop.models.BookSearchModel;
import com.novosad.bookshop.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

import static com.novosad.bookshop.repositories.BookSpecifications.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BookService {

    @Value("${com.novosad.itemsOnPage}")
    private int pageSize;

    private final BookRepository bookRepository;

    public Page<Book> getBooksPageByCriteria(Integer pageNumber,
                                             Integer itemsOnPage,
                                             BookSearchModel bookSearchModel) {

        log.info(String.format("Obtaining books has started. Page - %s, items - %s, criteria - %s",
                pageNumber, itemsOnPage, bookSearchModel));

        Optional<Specification<Book>> optional = getBookSpecification(bookSearchModel);
        Pageable page = getPage(pageNumber, itemsOnPage);
        Page<Book> result;
        if (optional.isEmpty()) {
            result = bookRepository.findAll(page);
        } else {
            result = bookRepository.findAll(optional.get(), page);
        }

        log.info("Books obtaining has finished. Result - " + result.getContent());
        return result;
    }

    private Optional<Specification<Book>> getBookSpecification(BookSearchModel bookSearchModel) {
        log.info("Obtaining specification for the book search has started");

        Specification<Book> bookSpecification = null;

        String title = bookSearchModel.getTitle();
        if (!StringUtils.isEmpty(title)) {
            bookSpecification = titleContains(title);
        }

        List<String> authors = bookSearchModel.getAuthors();
        if (authors != null && !authors.isEmpty()) {
            bookSpecification = addSpecification(bookSpecification, matchAuthors(authors));
        }

        Integer minPublishingYear = bookSearchModel.getMinPublishingYear();
        if (minPublishingYear != null) {
            bookSpecification = addSpecification(bookSpecification, minPublicationYear(minPublishingYear));
        }

        Integer maxPublishingYear = bookSearchModel.getMaxPublishingYear();
        if (maxPublishingYear != null) {
            bookSpecification = addSpecification(bookSpecification, maxPublicationYear(maxPublishingYear));
        }

        log.info("Obtaining specification for the book search has finished.");

        return Optional.ofNullable(bookSpecification);
    }

    private Specification<Book> addSpecification(Specification<Book> general, Specification<Book> part) {
        if (general == null) {
            return Specification.where(part);
        } else {
            return general.and(part);
        }
    }

    private Pageable getPage(Integer pageNumber, Integer itemsOnPage) {
        final int page = pageNumber == null ? 0 : pageNumber;
        final int size = itemsOnPage == null ? pageSize : itemsOnPage;

        return PageRequest.of(page, size);
    }
}
