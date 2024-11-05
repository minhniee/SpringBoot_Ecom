package com.example.auth_shop.service.user;

import com.example.auth_shop.dto.UserDto;
import com.example.auth_shop.model.User;
import com.example.auth_shop.request.CreatedUserRequest;
import com.example.auth_shop.request.UpdateUserRequest;

public interface IUserService {
    User getUserById(Long id);
    User createUser(CreatedUserRequest req);
    User updateUser(UpdateUserRequest req, Long userId);
    void deleteUser(Long userId);


    UserDto convertToDTO(User user);

    User getAuthenticatedUser();
}
