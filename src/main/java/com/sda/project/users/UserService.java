package com.sda.project.users;

import com.sda.project.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service("userService")
public class UserService implements UserDetailsService {

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

        String oldPassword = user.getPassword();
        user.setPassword(bCryptPasswordEncoder.encode(oldPassword));

        return userRepository.save(user);
    }

    public Optional<User> update(User user) throws Exception {
        if (user.getId() == null) {
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

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> byEmail = userRepository.findByEmail(email);
        User user;
        if (byEmail.isPresent()) {
            user = byEmail.get();
        }else {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthority(user));
    }

    private Collection<? extends GrantedAuthority> getAuthority(User user) {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_" + "USER")); //TODO add role field to user; can be user, admin etc.
    }
}
