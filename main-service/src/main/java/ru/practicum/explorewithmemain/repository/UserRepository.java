package ru.practicum.explorewithmemain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.explorewithmemain.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select exists(select 1 from users where id = :id)", nativeQuery = true)
    Boolean isUserExists(@Param("id") Long userId);
}
