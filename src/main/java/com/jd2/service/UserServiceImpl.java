package com.jd2.service;

import com.jd2.domain.User;
import com.jd2.repository.user.UserRepositoryInterface;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    //@NonNull
    private final UserRepositoryInterface userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
