package com.brain.book.library.dao;

import com.brain.book.library.model.Author;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Создаём интерфейс AuthorRepository для взаимодействия с БД и Spring Data.Этот репозиторий будет с классом-сущностью
 * Author
 *
 * @author Shakhov Yevhen
 */
public interface AuthorRepository extends CrudRepository<Author, Long> {
    /**
     * Создаём метод getAuthorByNameAndSecondNameAndLastName с помощью которого в дальнейшем будем искать автора по
     * имени, отчеству и фамилии.
     *
     * @param name
     * @param secondName
     * @param lastName
     * @return
     */
    Optional<Author> getAuthorByNameAndSecondNameAndLastName(String name, String secondName, String lastName);

    /*
    Этот метод можно записать и по другому - с помощью аннотации @Query.Но использовать будем первый вариант
     */
    @Query("from Author a where a.name = ?1 and a.secondName = ?2 and a.lastName = ?3")
    Optional<Author> getAuthorByFullName(String name, String secondName, String lastName);
}
