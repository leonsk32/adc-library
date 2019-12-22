package com.example.library.app_service;

import com.example.library.domain.ranking.RankingList;
import com.example.library.domain.user.User;
import com.example.library.domain.user.UserRepository;
import com.example.library.exception.BusinessException;
import com.example.library.restapi.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<User> searchAll() {
        return userRepository.findAll();
    }

    @Override
    public User searchById(String userId) {
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new BusinessException("ユーザが存在しない。userId = " + userId);
        }
        return user;
    }

    @Override
    public void register(UserDto userDto) {
        User byId = userRepository.findById(userDto.getUserId());
        if (byId != null) {
            return;
        }
        User user = new User(
                userDto.getUserId(),
                userDto.getEmail(),
                userDto.getFamilyName(),
                userDto.getGivenName()
        );

        userRepository.register(user);
    }

    @Override
    public void delete(String userId) {
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new BusinessException("削除対象ユーザが存在しない。userId = " + userId);
        }
        userRepository.delete(userId);
    }

    @Override
    public RankingList searchLentRanking() {
        return userRepository.findLentRanking();
    }
}
