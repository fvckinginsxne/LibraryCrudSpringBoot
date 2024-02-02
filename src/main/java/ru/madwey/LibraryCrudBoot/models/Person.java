package ru.madwey.LibraryCrudBoot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "people")
public class Person {
    @Id
    @Column(name = "person_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int personId;

    @NotEmpty(message = "Вы не ввели имя!")
    @Size(min = 2, max = 32, message = "Имя должно быть длиной от 2 до 32 символов!")
    @Column(name = "full_name")
    private String fullName;

    @Column(name = "year_of_birth")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date yearOfBirth;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;
}
