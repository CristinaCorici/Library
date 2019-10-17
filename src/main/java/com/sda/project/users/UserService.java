package com.sda.project.users;

import com.sda.project.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> get() {
        return userRepository.findAll();
    }

    public User addUser(User user) {

        String oldPassword=user.getPassword();
        user.setPassword(bCryptPasswordEncoder.encode(oldPassword));

        return userRepository.save(user);
    }

    public Optional<User> update(User user) throws Exception{
        if (user.getId()==null) {
            throw new Exception("The providede user ID is null");
        }
        Optional<User> existingUser = userRepository.findById(user.getId());
        if (existingUser.isPresent()) {
            return Optional.of(userRepository.save(user));
        } else {
            return Optional.empty();
        }

    }

    public void delete(Long id) throws EmptyResultDataAccessException {
        userRepository.deleteById(id);
    }

}
