package com.ssafy.cobook.domain.book;

import com.ssafy.cobook.domain.genre.Genre;
import com.ssafy.cobook.service.dto.book.BookBySearchResDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b where b.title like %:keyword% OR b.author like %:keyword%")
    List<BookBySearchResDto> findByKeyword(@Param("keyword") String keyword);

    List<Book> findAllByGenre(Genre genre);
}
