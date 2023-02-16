package ru.practicum.explorewithme.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explorewithme.Constants;
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
    @Column(name = "app_id")
    private Long appId;

    @NotNull(message = "Uri is required!")
    private String uri;

    @IpCheckConstraint
    private String ip;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.dateFormat)
    private LocalDateTime timestamp;
}
