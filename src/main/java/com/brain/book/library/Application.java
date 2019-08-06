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

@SpringBootApplication
public class Application {
    @Autowired
    private BookLibraryService bookLibraryService;

    public static void main(String[] args) {
        SpringApplication.run( Application.class, args );
    }

    @EventListener(ApplicationReadyEvent.class)
    public void run() {
        addTestNovel();
        addTestFantasy();
        addTestHumor();
        printAuthorBooks();
        printFilteredBooks();
    }

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
        book.setAuthor( author );
        bookLibraryService.addNewBook( book );
    }

    private void addTestFantasy() {
        Book book = new Book();
        book.setBooking( "в переходе на рынке" );
        book.setName( "Марсиани атакуют" );
        book.setPagesCount( 320 );
        book.setReleaseDate( Year.of( 2017 ) );
        book.setGenre( GenreEnum.FANTASY );

        Author author = bookLibraryService
                .findAuthorByFullName( "Бармалей", "Иософич", "Фуражкин" );
        book.setAuthor( author );
        bookLibraryService.addNewBook( book );
    }

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
        author.setLastName( "Петрович" );
        author.setBirthDate( LocalDate.of( 1962, 2, 24 ) );
        book.setAuthor( author );
        bookLibraryService.addNewBook( book );
    }

    private void printAuthorBooks() {
        List<Book> books = bookLibraryService
                .findBooksByAuthor( "Бармалей", "Иософич", "Фуражкин" );
        books.forEach( System.out::println );
    }
    private void printFilteredBooks(){
        Set<GenreEnum> genres = new HashSet<>();
        genres.add( GenreEnum.HUMOR );
        genres.add( GenreEnum.NOVEL );

        List<Book> books = bookLibraryService
                .findBooksByGenres( genres );
        books.forEach( System.out::println );
    }
}
