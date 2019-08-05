package com.brain.book.library.dao;

import com.brain.book.library.model.Author;
import com.brain.book.library.model.Book;
import com.brain.book.library.model.GenreEnum;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;

public interface BookRepository extends CrudRepository<Book,Long> {
    List<Book> findBooksByAuthor(Author author);
    List<Book> findBooksByGenreIn(Collection<GenreEnum> genres);
}
