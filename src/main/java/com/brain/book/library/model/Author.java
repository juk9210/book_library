package com.brain.book.library.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Создаём класс Author,который будет нашей таблицей в БД
 *
 * @author Shakhov Yevhen
 */
@Entity                                 //аннотация для превращения класса в сущность
@Table                                  //привязка сущности к таблице
@NoArgsConstructor                      //создаёт конструктор класса без параметров
@Data          //генерирует сразу такие реализации toString, EqualsAndHashCode,Getter / @Setter,RequiredArgsConstructor
public class Author implements Serializable {
    /*
   Создаём поля класса,которые в будущем будут нашими колонками в таблице
    */
    @Id                                 //указываем что это поле является ключем
    @GeneratedValue(strategy = GenerationType.IDENTITY)   //итератор для значения id
    private Long id;

    @Column(name = "name", nullable = false) //привязка поля к колонке таблицы и указываем что оно не может быть пустым
    private String name;

    @Column(name = "second_name")   //привязка поля к колонке таблицы
    private String secondName;

    @Column(name = "last_name", nullable = false)
    //привязка поля к колонке таблицы и указываем что оно не может быть пустым
    private String lastName;

    @Column(name = "birth_date")  //привязка поля к колонке таблицы
    private LocalDate birthDate;
}
