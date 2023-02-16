package ru.practicum.explorewithme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.explorewithme.entity.App;

import java.util.Optional;

public interface AppRepository extends JpaRepository<App, Long> {
    @Query(value = "INSERT INTO apps (name) VALUES (:name) RETURNING id", nativeQuery = true)
    Long saveApp(@Param("name") String name);

    Optional<App> findTopByName(String name);
}
