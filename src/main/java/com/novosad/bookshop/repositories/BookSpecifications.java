package com.novosad.bookshop.repositories;

import com.novosad.bookshop.entities.Author;
import com.novosad.bookshop.entities.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.ListJoin;
import java.util.List;

@Slf4j
public class BookSpecifications {

    public static Specification<Book> matchAuthors(List<String> authors) {
        log.info("Book authors matching. Input value - " + authors);
        return (book, cq, cb) -> {
            final ListJoin<Book, Author> bookAuthorsJoin = book.joinList("authors");
            return cb.lower(bookAuthorsJoin.get("name")).in(authors);
        };
    }

    public static Specification<Book> titleContains(String title) {
        log.info("Book title matching. Input value - " + title);
        return (book, cq, cb) -> cb.like(cb.lower(book.get("title")), "%" + title + "%");
    }

    public static Specification<Book> minPublicationYear(int minPublishingYear) {
        log.info("Set a minimum publication year. Input value - " + minPublishingYear);
        return (book, cq, cb) -> cb.greaterThanOrEqualTo(book.get("publishingYear"), minPublishingYear);
    }

    public static Specification<Book> maxPublicationYear(int maxPublishingYear) {
        log.info("Set a maximum publication year. Input value - " + maxPublishingYear);
        return (book, cq, cb) -> cb.lessThanOrEqualTo(book.get("publishingYear"), maxPublishingYear);
    }
}
