package library.repositories;

import library.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    Optional<Book> findByName(String name);
    Optional<Book> findFirstByNameStartingWith(String staringWith);
}
