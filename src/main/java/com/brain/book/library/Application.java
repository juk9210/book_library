package com.brain.book.library;

import com.brain.book.library.model.Author;
import com.brain.book.library.model.Book;
import com.brain.book.library.model.GenreEnum;
import com.brain.book.library.service.BookLibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.time.LocalDate;
import java.time.Year;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Создаём класс для запуска приложения
 *
 * @author Shakhov Yevhen
 */
@SpringBootApplication
public class Application {
    /**
     * Делаем автосвязывание с нашим сервисом.
     */
    @Autowired
    private BookLibraryService bookLibraryService;

    /**
     * Создаём метод main, в котором запускаем наше приложение
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run( Application.class, args );
    }

    /**
     * Создаём метод run,в котором описываем все наши методы работы приложения.Этот метода помечаем аннотацией
     * EventListener для того чтобы запускался вместе с запуском приложения.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void run() {
        addTestNovel();
        addTestFantasy();
        addTestHumor();
        printAuthorBooks();
        printFilteredBooks();
    }

    /**
     * Создаём метод addTestNovel, в котором будет создаваться новая книга и автор
     */
    private void addTestNovel() {
        Book book = new Book();
        book.setBooking( "магазин на Маяке" );
        book.setName( "Хижина на острове" );
        book.setPagesCount( 450 );
        book.setReleaseDate( Year.of( 2015 ) );
        book.setGenre( GenreEnum.NOVEL );

        Author author = new Author();
        author.setName( "Бармалей" );
        author.setSecondName( "Иософич" );
        author.setLastName( "Фуражкин" );
        author.setBirthDate( LocalDate.of( 1985, 5, 15 ) );
        book.setAuthor( author );   //сохраняем автора книги
        bookLibraryService.addNewBook( book );   //добавляем нашу книгу
    }

    /**
     * Создаём метод addTestFantasy, в котором будет создаваться новая книга и автор
     */
    private void addTestFantasy() {
        Book book = new Book();
        book.setBooking( "в переходе на рынке" );
        book.setName( "Марсиани атакуют" );
        book.setPagesCount( 320 );
        book.setReleaseDate( Year.of( 2017 ) );
        book.setGenre( GenreEnum.FANTASY );

        Author author = bookLibraryService   // тут мы не будем создавать нового автора для этой книги.А с помощью
                // метода findAuthorByFullName найдем автора по Ф.И.О. и присвоим его к этой книге
                .findAuthorByFullName( "Бармалей", "Иософич", "Фуражкин" );
        book.setAuthor( author );  //сохраяняем автора книги
        bookLibraryService.addNewBook( book );   //добавляем нашу книгу
    }

    /**
     * Создаём метод addTestHumor, в котором будет создаваться новая книга и автор
     */
    private void addTestHumor() {
        Book book = new Book();
        book.setBooking( "в переходе на рынке" );
        book.setName( "Заметки Петросяна" );
        book.setPagesCount( 152 );
        book.setReleaseDate( Year.of( 2010 ) );
        book.setGenre( GenreEnum.HUMOR );

        Author author = new Author();
        author.setName( "Евгений" );
        author.setSecondName( "Ваганович" );
        author.setLastName( "Петросян" );
        author.setBirthDate( LocalDate.of( 1962, 2, 24 ) );
        book.setAuthor( author );
        bookLibraryService.addNewBook( book );
    }

    /**
     * Создаём метод printAuthorBooks,который будет нам выводить нам книги по поиску автора.Для этого применяем наш
     * метод findBooksByAuthor,который был описан в BookRepository.
     */
    private void printAuthorBooks() {
        List<Book> books = bookLibraryService
                .findBooksByAuthor( "Бармалей", "Иософич", "Фуражкин" );
        books.forEach( System.out::println ); //Применяя лямбду с помощью цикла выводим книги.
    }

    /**
     * Создаём метод printFilteredBooks, который будет фильтровать нам книги по заданным жанрам и выводить на экран
     */
    private void printFilteredBooks() {
        Set<GenreEnum> genres = new HashSet<>(); //Создаём список,в который добавим жанры книг по которым будем
        // фильтровать книги
        genres.add( GenreEnum.HUMOR );
        genres.add( GenreEnum.NOVEL );

        List<Book> books = bookLibraryService //Обращаемся к нашему сервису, в котором при помощи метода
                // findBooksByGenres будем искать наши книги по жанрам
                .findBooksByGenres( genres );
        books.forEach( System.out::println );   //Применяя лямбду с помощью цикла выводим книги.
    }
}
