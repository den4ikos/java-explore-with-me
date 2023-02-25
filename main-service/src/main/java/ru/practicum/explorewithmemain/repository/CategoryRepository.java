package ru.practicum.explorewithmemain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.explorewithmemain.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query(value = "select count(e) from Event e where e.category.id = :cat")
    Long countAllRelatedEvents(@Param("cat") Long catId);

    boolean existsByName(@Param("name") String name);
}
