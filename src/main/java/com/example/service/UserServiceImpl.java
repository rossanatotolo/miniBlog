package com.example.service;

import com.example.dto.UserDtoInput;
import com.example.dto.UserDtoOutput;
import com.example.exception.DuplicatedDataException;
import com.example.exception.NotFoundException;
import com.example.mapper.UserMapper;
import com.example.model.User;
import com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<UserDtoOutput> getAllUsers(List<Long> ids, final int page, final int size) {
        PageRequest pageRequest = PageRequest.of(page, Math.min(size, 50), Sort.by(Sort.Direction.DESC, "createdAt"));
        log.info("Запрос на получение списка пользователей.");
        final List<User> users;

        if (Objects.isNull(ids) || ids.isEmpty()) {
            users = userRepository.findAll(pageRequest).getContent();
            log.info("Получен список всех пользователей.");
        } else {
            users = userRepository.findByIdIn(ids, pageRequest);
            log.info("Получен список пользователей по заданным id.");
        }

        return UserMapper.toListDto(users);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDtoOutput getUserById(final long userId) {
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("Пользователь с id = %d не найден.", userId)));

        log.info("Получен пользователь с id = {}.", userId);
        return UserMapper.toUserDto(user);
    }

    @Override
    public UserDtoOutput createUser(final UserDtoInput userDtoInput) {
        if (userRepository.existsByEmailOrUsername(userDtoInput.getEmail(), userDtoInput.getUsername())) {
            log.warn("Пользователь уже существует.");
            throw new DuplicatedDataException("Пользователь уже существует.");
        }
        final User user = userRepository.save(UserMapper.toUser(userDtoInput));

        log.info("Пользователь с id = {} добавлен.", user.getId());
        return UserMapper.toUserDto(user);
    }

    @Override
    public void deleteUser(final long userId) {
        if (!userRepository.existsById(userId)) {
            log.warn("Пользователь для удаления не найден: id = {}.", userId);
            throw new NotFoundException(String.format("Пользователь с id = %d не найден.", userId));
        }

        userRepository.deleteById(userId);
        log.info("Пользователь с id  = {} удален.", userId);
    }
}
