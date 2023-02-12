package ru.practicum.explorewithme.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explorewithme.annotation.IpCheckConstraint;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "hits")
public class Hit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "App is required!")
    private String app;

    @NotNull(message = "Uri is required!")
    private String uri;

    @IpCheckConstraint
    private String ip;

    private LocalDateTime timestamp;
}
