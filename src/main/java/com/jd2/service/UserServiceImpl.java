package com.jd2.service;

import com.jd2.domain.User;
import com.jd2.repository.user.UserRepositoryInterface;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepositoryInterface userRepository;

    public User findById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findOne(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<User> findAll(int limit, int offset) {
        return userRepository.findAll(limit, offset);
    }

    public User create(User object) {
        return userRepository.create(object);
    }

    @Override
    public User update(User object) {
        return userRepository.update(object);
    }

    public Long delete(Long id) {
        return userRepository.delete(id);
    }

    @Override
    public Map<String, Object> getUserStats(boolean isDeleted) {
        return userRepository.getUserStats(isDeleted);
    }

    @Override
    public List<User> find_user_by_name_and_surname(String name, String surname) {
        return userRepository.find_user_by_name_and_surname(name, surname);
    }
}
