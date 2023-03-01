package ru.practicum.explorewithmemain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explorewithmemain.helper.SubscriberStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "subscribers")
public class Subscriber {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "subscriber_id")
    private User subscriber;
    @ManyToOne
    @JoinColumn(name = "signatory_id")
    private User signatory;
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
    private SubscriberStatus status;
    private LocalDateTime created;
}
