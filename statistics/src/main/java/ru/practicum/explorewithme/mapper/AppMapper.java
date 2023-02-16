package ru.practicum.explorewithme.mapper;

import ru.practicum.explorewithme.dto.AppDto;
import ru.practicum.explorewithme.entity.App;

public class AppMapper {

    public static AppDto toDto(App app) {
        return new AppDto(
                app.getId(), app.getName()
        );
    }
}
