package ru.practicum.explorewithmemain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Getter
@Setter
@AllArgsConstructor
public class NewUserRequestDto {
    @NotBlank(message = "Email can not be blank")
    @Email(message = "Email must be valid")
    private String email;
    @NotBlank(message = "Name is required")
    private String name;
}
