package com.brain.book.library.dao;

import com.brain.book.library.model.Author;
import com.brain.book.library.model.Book;
import com.brain.book.library.model.GenreEnum;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * Создаём интерфейс BookRepository для взаимодействия с БД и Spring Data.Этот репозиторий будет с классом-сущностью
 * Book.
 *
 * @author Shakhov Yevhen
 */
@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
    /**
     * Создаём метод findBooksByAuthor, с помощью которого в дальнейшем будем искать книги по автору
     *
     * @param author
     * @return
     */
    List<Book> findBooksByAuthor(Author author);

    /**
     * Создаём метод findBooksByGenreIn, с помощью которого в дальнейшем будем искать книги по жанру
     *
     * @param genres
     * @return
     */
    List<Book> findBooksByGenreIn(Collection<GenreEnum> genres);
}
