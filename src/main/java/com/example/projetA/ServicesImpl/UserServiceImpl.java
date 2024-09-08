package com.example.projetA.ServicesImpl;


import com.example.projetA.Dto.UserDto;
import com.example.projetA.Entity.User;
import com.example.projetA.Repository.UserRepository;
import com.example.projetA.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public User create_User(UserDto userDto){

        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

            User user = new User();
            User user1 = User.builder()
                    .name(userDto.getName())
                    .password(userDto.getPassword())
                    .email(userDto.getEmail())
                    .role(User.Role.valueOf(userDto.getRole()))
                    .build();
            user= userRepository.save(user1);

            return user;

    }

    @Override
    public List<User> List_User() {
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @Override
    public void  SupprimerUser(Long idUser){
        Optional<User> optionalUser = userRepository.findById(idUser);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            //user.setStatut(StatutAppareils.NonActive);
            userRepository.save(user);
        }
        else {
            throw new RuntimeException ("Utilisateur  n'a pas ete trouvé");
        }

    }

    @Override
    public User modifierUser(Long idUser , UserDto userDto){
        Optional<User> optionalUser = userRepository.findById(idUser);
        if(optionalUser.isPresent()){
            User user =optionalUser.get();
            user.setName(userDto.getName());


            userRepository.save(user);

            return user;
        }
        else {
            throw new RuntimeException("Appareils n'a pas ete trouvé");
        }
    }

}
