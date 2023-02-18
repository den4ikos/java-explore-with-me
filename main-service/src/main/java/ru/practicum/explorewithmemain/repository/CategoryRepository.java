package ru.practicum.explorewithmemain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explorewithmemain.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Page<Category> findAll(Pageable page);
}
