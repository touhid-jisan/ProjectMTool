package io.touhidjisan.projectmtool.services;

import io.touhidjisan.projectmtool.model.User;
import io.touhidjisan.projectmtool.repositories.UserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRespository userRespository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRespository userRespository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRespository = userRespository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User saveUser(User newUser) {
        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        // username has to be unique (exception)

        // make sure that password and confirmPassword match

        // we don't persist or show the confirmPassword


        return userRespository.save(newUser);
    }
}
