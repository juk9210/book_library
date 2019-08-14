package com.brain.book.library.controller;

import com.brain.book.library.dao.AuthorRepository;
import com.brain.book.library.model.Author;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Описываем контроллер для сущности автора, который будет обрабатывать REST запросы
 *
 * @author Shakhov Yevhen
 */
@RestController
@RequestMapping("/api/authors") //указываем как к нему будем обращаться
public class AuthorRestController {
    /*
    Создаём поле AuthorRepository чтобы можно было работать с ними в нашем сервисе. И делаем их final
    для того чтобы нельзя было их изменять.
     */
    private final AuthorRepository authorRepository;

    /**
     * Создаём конструктор с нашим полем
     *
     * @param authorRepository
     */
    public AuthorRestController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    /**
     * Обработчик для запроса GET. Получаем весь список наших созданных авторов
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<Iterable<Author>> getAuthorList() {   //получаем весь список наших созданных авторов
        return new ResponseEntity<>( authorRepository.findAll(), HttpStatus.OK );
    }

    /**
     * Обработчик для запроса GET. Получаем нашего автора по id.
     *
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public ResponseEntity<Author> getAuthor(@PathVariable Long id) {
        return new ResponseEntity<>( authorRepository.findById( id ).orElse( null ), HttpStatus.OK );
    }

    /**
     * Обработчик для запроса DELETE.  Удаляем автора по id.
     *
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public ResponseEntity removeAuthor(@PathVariable Long id) {
        authorRepository.deleteById( id );
        return new ResponseEntity<>( HttpStatus.OK );
    }

    /**
     * Обработчик для запроса POST. Будем сохранять нового автора.
     *
     * @param author
     * @return
     */
    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        Author savedAuthor = authorRepository.save( author );
        return new ResponseEntity<>( savedAuthor, HttpStatus.CREATED );
    }

    /**
     * Обработчик для запроса PUT.Будем обновлять список наших авторов.
     *
     * @param author
     * @return
     */
    @PutMapping("{id}")
    public ResponseEntity<Author> updateAuthor(@RequestBody Author author) {
        Author savedAuthor = authorRepository.save( author );
        return new ResponseEntity<>( savedAuthor, HttpStatus.OK );
    }
}

