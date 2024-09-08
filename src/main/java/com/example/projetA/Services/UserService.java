package com.example.projetA.Services;

import com.example.projetA.Dto.UserDto;
import com.example.projetA.Entity.User;

import java.util.List;

public interface UserService {
    public User create_User (UserDto userDto) throws Exception;

    public List<User> List_User();

    public void SupprimerUser(Long id);

    public User modifierUser(Long id, UserDto userDto );
}
