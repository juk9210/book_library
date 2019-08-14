package com.brain.book.library.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Year;

/**
 * Создаём класс Book,который будет нашей таблицей в БД
 *
 * @author Shakhov Yevhen
 */
@Entity                //аннотация для превращения класса в сущность
@Table(name = "book")   //привязка сущности к определенной таблице
@NoArgsConstructor     //создаёт конструктор класса без параметров
@Data     //генерирует сразу такие реализации toString, EqualsAndHashCode,Getter / @Setter,RequiredArgsConstructor
public class Book implements Serializable {
    /*
    Создаём поля класса,которые в будущем будут нашими колонками в таблице
     */
    @Id   //указываем что это поле является ключем
    @GeneratedValue(strategy = GenerationType.IDENTITY)    //итератор для значения id
    private Long id;

    @Column(name = "name", nullable = false) //привязка поля к колонке таблицы и указываем что оно не может быть пустым
    private String name;

    @Column(name = "pages", nullable = false) //привязка поля к колонке таблицы и указываем что оно не может быть пустым
    private Integer pagesCount;

    @Column             //привязка поля к колонке таблицы
    private Year releaseDate;

    @Column                //привязка поля к колонке таблицы
    private String booking;

    @ManyToOne(fetch = FetchType.EAGER, optional = false) //указываем связь с таблицой Author - многие к одному и
    // указываем что поле не может быть пустым
    @JoinColumn(name = "author_id", nullable = false) //указываем привязку к внешнему ключу
    private Author author;

    @Column(name = "genre", nullable = false) //привязка поля к колонке таблицы и указываем что оно не может быть пустым
    @Enumerated(EnumType.STRING)   //указывает в какой форме перечисления должны быть сохранены в базе данных
    private GenreEnum genre;
}
