package com.brain.book.library.controller;

import com.brain.book.library.dao.BookRepository;
import com.brain.book.library.model.Author;
import com.brain.book.library.model.Book;
import com.brain.book.library.model.GenreEnum;
import com.brain.book.library.service.BookLibraryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

/**
 * Описываем контроллер для сущности книги, который будет обрабатывать REST запросы
 *
 * @author Shakhov Yevhen
 */
@RestController
@RequestMapping("/api/books")  //указываем как к нему будем обращаться
public class BookRestController {
    /*
        Создаём поле BookRepository и BookLibraryService чтобы можно было работать с ними в нашем сервисе. И делаем их final
    для того чтобы нельзя было их изменять.
     */
    private final BookRepository bookRepository;
    private final BookLibraryService bookLibraryService;

    /**
     * Создаём конструктор с нашим полем
     *
     * @param bookRepository
     */
    public BookRestController(BookRepository bookRepository, BookLibraryService bookLibraryService) {
        this.bookRepository = bookRepository;
        this.bookLibraryService = bookLibraryService;
    }

    /**
     * Обработчик для запроса GET. Получаем весь список наших авторов
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<Iterable<Book>> getBookList() {
        return new ResponseEntity<>( bookRepository.findAll(), HttpStatus.OK );
    }

    /**
     * Обработчик для запроса GET. Получаем книгу по id.
     *
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public ResponseEntity<Book> getBook(@PathVariable Long id) {
        return new ResponseEntity<>( bookRepository.findById( id ).orElse( null ), HttpStatus.OK );
    }

    /**
     * Обработчик для запроса DELETE.  Удаляем книгу по id.
     *
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public ResponseEntity removeBook(@PathVariable Long id) {
        bookRepository.deleteById( id );
        return new ResponseEntity( HttpStatus.OK );
    }

    /**
     * Обработчик для запроса POST. Будем сохранять новую книгу
     *
     * @param book
     * @return
     */
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book savedBook = bookRepository.save( book );
        return new ResponseEntity<>( savedBook, HttpStatus.CREATED );
    }

    /**
     * Обработчик для запроса PUT.Будем обновлять список наших книг
     *
     * @param book
     * @return
     */
    @PutMapping("{id}")
    public ResponseEntity<Book> updateBook(@RequestBody Book book) {
        Book savedBook = bookRepository.save( book );
        return new ResponseEntity<>( savedBook, HttpStatus.OK );
    }

    /**
     * Обработчик для запроса GET для поиска книг по автору.
     * @param name
     * @param secondName
     * @param lastName
     * @return
     */
    @GetMapping("/search/by_author")
    public ResponseEntity<List<Book>> findBooksbyAuthor(@RequestParam("name") String name,
                                                @RequestParam("secondName") String secondName,
                                                @RequestParam("lastName") String lastName){
        Author author = bookLibraryService.findAuthorByFullName("Бармалей", "Иософич", "Фуражкин");
        return new ResponseEntity<>(bookRepository.findBooksByAuthor(author), HttpStatus.OK);
    }

    /**
     * Обработчик для запроса GET для поиска книг по жанру.
     * @param genres
     * @return
     */
    @GetMapping("/search/by_genre")
    public ResponseEntity<List<Book>> findBooksByGenre(@RequestParam("genre") Collection<GenreEnum> genres){
        List<Book> book = bookRepository.findBooksByGenreIn(genres);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }
}