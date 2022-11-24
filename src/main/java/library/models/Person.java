package library.models;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Table
public class Person {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int person_id;
    @Column
    @NotNull(message = "Поле с именем не может быть пустым")
    @Size(min = 9, max = 100, message = "Твое имя должно быть длиннее, чем 9 символом и короче, чем 100 символов")
    @Pattern(regexp = "[А-Я][а-я]+ [А-Я][а-я]+ [А-Я][а-я]+", message = "Ваше имя должно иметь правильный формат")
    private String name;
    @Column
    @NotNull(message = "Поле с годом рождения не может быть пустым")
    @Min(value = 1900, message = "Минимальное значение года рождения - 1900 год")
    @Max(value = 2022, message = "Максимальное значение года рождения - 2022 год")
    private int year_of_birth;

    @OneToMany(mappedBy = "person", fetch = FetchType.EAGER)
    private List<Book> bookList;

    public Person() {
    }

    public Person(String name, int year_of_birth) {
        this.name = name;
        this.year_of_birth = year_of_birth;
    }

    public Person(List<Book> bookList) {
        this.bookList = bookList;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear_of_birth() {
        return year_of_birth;
    }

    public void setYear_of_birth(int year_of_birth) {
        this.year_of_birth = year_of_birth;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int personId) {
        this.person_id = personId;
    }

    @Override
    public String toString() {
        return "Person{" +
                "person_id=" + person_id +
                ", name='" + name + '\'' +
                ", year_of_birth=" + year_of_birth +
                ", bookList=" + bookList +
                '}';
    }
}
