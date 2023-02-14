package ru.practicum.explorewithme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.explorewithme.dto.HitDtoInterface;
import ru.practicum.explorewithme.entity.Hit;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HitRepository extends JpaRepository<Hit, Long> {
    @Query(value = "SELECT app AS app, uri AS uri, COUNT(DISTINCT ip) AS hits FROM Hit WHERE uri IN :uris AND timestamp >= :start AND timestamp <= :end GROUP BY app, uri ORDER BY hits DESC")
    List<HitDtoInterface> calculateUniqueHits(@Param("uris") List<String> uris, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query(value = "SELECT app AS app, uri AS uri, COUNT(ip) AS hits FROM Hit WHERE uri IN :uris AND timestamp >= :start AND timestamp <= :end GROUP BY app, uri ORDER BY hits DESC")
    List<HitDtoInterface> calculateHits(@Param("uris") List<String> uris, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
