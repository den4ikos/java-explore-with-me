package ru.practicum.explorewithmemain.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.explorewithmemain.entity.Event;
import ru.practicum.explorewithmemain.entity.User;
import ru.practicum.explorewithmemain.helper.State;

import java.util.List;
import java.util.Set;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query(value = "select exists(select 1 from events where id = :id)", nativeQuery = true)
    Boolean isEventExists(@Param("id") Long eventId);

    List<Event> findByInitiatorId(Long userId, Pageable page);

    @Query(value = "select e from Event e where e.state in :states")
    List<Event> findEventsByStates(@Param("states") Set<State> states, Pageable page);

    @Query(value = "select e from Event e where e.state in :states and e.category.id in :categories and e.initiator.id in :users")
    List<Event> findEventsByCategoriesAndUsersAndStates(@Param("categories") Set<Long> categories, @Param("users") Set<Long> users, @Param("states") Set<State> states, Pageable page);

    @Query(value = "select e from Event e where e.state in :states and e.category.id in :categories")
    List<Event> findEventsByCategoriesAndStates(@Param("categories") Set<Long> categories, @Param("states") Set<State> states, Pageable page);

    @Query(value = "select e from Event e where e.state in :states and e.initiator in :users")
    List<Event> findEventsByEventsAndStates(@Param("users") Set<Long> users, @Param("states") Set<State> states, Pageable page);

    @Query(value = "select e from Event e where e.category.id in :categories and (lower(e.annotation) like concat('%',lower(:text),'%') or lower(e.description) like concat('%',lower(:text),'%'))")
    List<Event> findEventsForPublicWithCategoriesAndText(@Param("categories") Set<Long> categories, @Param("text") String text);

    @Query(value = "select e from Event e where lower(e.annotation) like concat('%',lower(:text),'%') or lower(e.description) like concat('%',lower(:text),'%')")
    List<Event> findEventsForPublicByText(@Param("text") String text);

    Event findByIdAndStateIn(Long id, Set<State> states);

    Event findByIdAndInitiator(Long eventId, User user);

    List<Event> findAllByIdIn(Set<Long> eventIds);
}
