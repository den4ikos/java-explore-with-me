package ru.practicum.explorewithmemain.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explorewithmemain.entity.Compilation;

import java.util.List;

public interface CompilationRepository extends JpaRepository<Compilation, Long> {
    List<Compilation> findByPinnedOrderByIdDesc(Boolean pinned, PageRequest pageRequest);
}
