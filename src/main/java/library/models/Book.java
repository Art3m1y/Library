package library.models;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Entity
@Table
public class Book {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int book_id;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private Person person;

    @Column()
    @Temporal(TemporalType.TIMESTAMP)
    private Date assignedAt;

    @Transient
    private boolean overdue;

    @Column
    @NotNull(message = "Поле с названием произведения не может быть пустым")
    @Size(min = 9, max = 100, message = "Название произведения должно иметь длину от 9 до 100 символов")
    private String name;
    @Column
    @NotNull(message = "Поле с именем не может быть пустым")
    @Size(min = 9, max = 100, message = "Твое имя должно быть длиннее, чем 9 символом и короче, чем 100 символов")
    @Pattern(regexp = "[А-Я][а-я]+ [А-Я][а-я]+ [А-Я][а-я]+", message = "Имя автора должно соответствовать образцу")
    private String author;

    @Column
    @NotNull(message = "Поле с годом написания произведения не может быть пустым")
    @Min(value = 1000, message = "Минимальное значение года написания произведения - 1000 год")
    @Max(value = 2022, message = "Максимальное значение года написания произведения - 2022 год")
    private int year;

    public Book() {
    }

    public Book(String name, String author, int year) {
        this.name = name;
        this.author = author;
        this.year = year;
    }

    public boolean isOverdue() {
        if ((new Date().getTime() - assignedAt.getTime()) > 864000000) {
            return true;
        } else {
            return false;
        }
    }

    public Date getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(Date assignedAt) {
        this.assignedAt = assignedAt;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
