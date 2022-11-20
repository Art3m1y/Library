package library.models;

import org.springframework.jdbc.core.ResultSetExtractor;

import javax.validation.constraints.*;

public class Book {

    private int book_id;

    private Integer person_id;
    @NotNull(message = "Поле с названием произведения не может быть пустым")
    @Size(min = 9, max = 100, message = "Название произведения должно иметь длину от 9 до 100 символов")
    private String name;
    @NotNull(message = "Поле с именем не может быть пустым")
    @Size(min = 9, max = 100, message = "Твое имя должно быть длиннее, чем 9 символом и короче, чем 100 символов")
    @Pattern(regexp = "[А-Я][а-я]+ [А-Я][а-я]+ [А-Я][а-я]+", message = "Имя автора должно соответствовать образцу")
    private String author;

    @NotNull(message = "Поле с годом написания произведения не может быть пустым")
    @Min(value = 1000, message = "Минимальное значение года написания произведения - 1000 год")
    @Max(value = 2022, message = "Максимальное значение года написания произведения - 2022 год")
    private int year;

    public Book(int book_id, int person_id, String name, String author, int year) {
        this.book_id = book_id;
        this.person_id = person_id;
        this.name = name;
        this.author = author;
        this.year = year;
    }

    public Book() {
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public Integer getPerson_id() {
        return person_id;
    }

    public void setPerson_id(Integer person_id) {
        this.person_id = person_id;
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

    @Override
    public String toString() {
        return "Book{" +
                "book_id=" + book_id +
                ", person_id=" + person_id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", year='" + year + '\'' +
                '}';
    }
}
