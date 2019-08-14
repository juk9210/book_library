package com.brain.book.library.service;

import com.brain.book.library.dao.AuthorRepository;
import com.brain.book.library.dao.BookRepository;
import com.brain.book.library.model.Author;
import com.brain.book.library.model.Book;
import com.brain.book.library.model.GenreEnum;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Создаём класс-сервис, в котором будем описывать уже основную логику и работать не напрямую с сущностями, а с
 * репозиториями
 *
 * @author Shakhov Yevhen
 */
@Service
public class BookLibraryService {
    /*
    Создаём поля AuthorRepository и BookRepository чтобы можно было работать с ними в нашем сервисе. И делаем их final
    для того чтобы нельзя было их изменять.
     */
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    /**
     * Создаём конструктор с нашими полями
     *
     * @param authorRepository
     * @param bookRepository
     */
    public BookLibraryService(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    /**
     * Создаём метод addNewBook, который будет создавать новую книгу
     *
     * @param book
     */
    public void addNewBook(Book book) {
        Author author = book.getAuthor();
        if (author == null) {      //Если автора не будет, то выкинет ошибку  - "Невозможно сохранить книгу без автора"
            throw new RuntimeException( "Невозможно сохранить книгу без автора" );
        }
        if (author.getId() == null) {  //Если по id автора не найдёт,то тогда сохранит нового автора.
            author = authorRepository.save( author );
            book.setAuthor( author );
        }
        bookRepository.save( book );  // сохраняем новую книгу
    }

    /**
     * Создаём метод findAuthorByFullName по которому будем искать автора по его Ф.И.О.
     *
     * @param name
     * @param secondName
     * @param lastName
     * @return
     */
    public Author findAuthorByFullName(String name, String secondName, String lastName) {
        Author author = authorRepository
                .getAuthorByNameAndSecondNameAndLastName( name, secondName, lastName )
                .orElse( null ); //Ищем автора по его Ф.И.О. Если не найден,то будет кидать такую ошибку -
        // "Невозможно найти автора по данным Ф.И.О."
        if (author == null) {
            throw new RuntimeException( "Невозможно найти автора по данным Ф.И.О." );
        }
        return author;
    }

    /**
     * Создаём метод findBooksByGenres по которому будем искать книги по жанру.
     * Создаём метод
     *
     * @param genres
     * @return
     */
    public List<Book> findBooksByGenres(Set<GenreEnum> genres) {
        System.out.println( "Выводим список книг по жанрам" );
        return bookRepository.findBooksByGenreIn( genres );
    }

    /**
     * Создаём метод findBooksByAuthor по которому будем искать книги по автору.
     *
     * @param name
     * @param secondName
     * @param lastName
     * @return
     */
    public List<Book> findBooksByAuthor(String name, String secondName, String lastName) {
        System.out.println( "Выводим список книг по автору" );
        Author author = findAuthorByFullName( name, secondName, lastName );
        return bookRepository.findBooksByAuthor( author );
    }
}
