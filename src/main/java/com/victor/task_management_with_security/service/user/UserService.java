package com.victor.task_management_with_security.service.user;

import com.victor.task_management_with_security.dto.user.ProfileEditRequest;
import com.victor.task_management_with_security.dto.user.UserResponse;
import com.victor.task_management_with_security.entity.Task;
import com.victor.task_management_with_security.entity.User;
import com.victor.task_management_with_security.exception.NotPermittedException;
import com.victor.task_management_with_security.mapper.UserMapper;
import com.victor.task_management_with_security.repository.UserRepository;
import com.victor.task_management_with_security.utils.PageResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public PageResponse<UserResponse> findAllUsers(
            int page,
            int size
    ) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("createdAt").descending());

        Page<User> usersPage = userRepository.findAll(pageable);

        List<UserResponse> userResponses = usersPage.stream()
                .map(userMapper::toUserResponse)
                .toList();

        return new PageResponse<>(
                userResponses,
                usersPage.getNumber(),
                usersPage.getSize(),
                usersPage.getTotalElements(),
                usersPage.getTotalPages(),
                usersPage.isFirst(),
                usersPage.isLast()
        );
    }

    public UserResponse findUserById(
            Integer userId
    ) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "User Not Found"
                ));
        return userMapper.toUserResponse(user);
    }

    public UserResponse profile(Authentication authenticatedUser) {
        Integer userId = ((User) authenticatedUser.getPrincipal()).getId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "User Not Found"
                ));
        return userMapper.toUserResponse(user);
    }

    public UserResponse edit(
            Integer userId,
            ProfileEditRequest profileEditRequest,
            Authentication authenticatedUser
    ) {

        User connectedUser = (User) authenticatedUser.getPrincipal();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("No User Found"));

        if (!Objects.equals(userId, connectedUser.getId())) {
            throw new NotPermittedException(
                    "You cannot edit a task that belongs to another user!"
            );
        }

        if (profileEditRequest.firstName() != null) {
            user.setFirstName(profileEditRequest.firstName());
        }
        if (profileEditRequest.lastName() != null) {
            user.setLastName(profileEditRequest.lastName());
        }

        userRepository.save(user);

        return userMapper.toUserResponse(user);
    }
}
