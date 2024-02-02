package ru.madwey.LibraryCrudBoot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Book {
    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookId;

    @NotEmpty(message = "Вы не ввели название книги!")
    @Size(min = 2, max = 200, message = "Название должно быт от 2 до 200 символов!")
    @Column(name = "title")
    private String title;

    @NotEmpty(message = "Вы не ввели автора!")
    @Size(min = 2, max = 128, message = "Поле автор должно содержать от 2 до 128 симолов!")
    @Column(name = "author")
    private String author;

    @Min(value = 1800, message = "Некорректные данные!")
    @Max(value = 2023, message = "Некоректные данные!")
    @Column(name = "year")
    private int year;

    @Column(name = "received_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date receivedAt;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private Person owner;

    @Column(name = "is_overdue")
    private boolean isOverdue;
}
