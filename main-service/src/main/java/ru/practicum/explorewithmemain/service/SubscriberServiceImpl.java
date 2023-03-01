package ru.practicum.explorewithmemain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithmemain.Constants;
import ru.practicum.explorewithmemain.dto.SubscribeDto;
import ru.practicum.explorewithmemain.entity.User;
import ru.practicum.explorewithmemain.exception.NotFoundException;
import ru.practicum.explorewithmemain.repository.UserRepository;
import ru.practicum.explorewithmemain.service.interfaces.SubscriberService;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class SubscriberServiceImpl implements SubscriberService {
    private final UserRepository userRepository;

    @Override
    public SubscribeDto sendRequest(Long userId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format(Constants.notFoundError, "User with ID " + userId)));
        System.out.println(user);
        return null;
    }
}
