package library.daos;

import library.models.Book;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getListBook() {
        return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }

    public List<Book> getListBookByPersonID(int id) {
        List<Book> bookList = jdbcTemplate.query("SELECT * FROM Book WHERE person_id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
        if (bookList.size() == 0) {
            return null;
        } else {
            return bookList;
        }
    }

    public Book getBookByID(int id) {
        List<Book> bookList = jdbcTemplate.query("SELECT * FROM Book WHERE book_id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
        if (bookList.size() == 1) {
            return bookList.get(0);
        } else {
            return null;
        }
    }

    public Book getBookByName(String name) {
        List<Book> bookList = jdbcTemplate.query("SELECT * FROM Book WHERE name = ?", new Object[]{name}, new BeanPropertyRowMapper<>(Book.class));
        if (bookList.size() == 1) {
            return bookList.get(0);
        } else {
            return null;
        }
    }

    public void saveBook(Book book) {
        jdbcTemplate.update("INSERT INTO Book(name, author, year) VALUES (?, ?, ?)", book.getName(), book.getAuthor(), book.getYear());
    }

    public void deleteBook(int id) {
        jdbcTemplate.update("DELETE FROM Book WHERE book_id = ?", id);
    }

    public void editBook(int id, Book book) {
        jdbcTemplate.update("UPDATE Book SET name = ?, author = ?, year = ? WHERE book_id = ?", book.getName(), book.getAuthor(), book.getYear(), id);
    }

    public void assignBook(int id, Book book) {
        jdbcTemplate.update("UPDATE Book SET person_id = ? WHERE book_id = ?", book.getPerson_id(), id);
    }
}
