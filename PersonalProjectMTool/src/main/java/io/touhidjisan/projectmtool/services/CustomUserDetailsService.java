package io.touhidjisan.projectmtool.services;

import io.touhidjisan.projectmtool.model.User;
import io.touhidjisan.projectmtool.repositories.UserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRespository userRespository;

    @Autowired
    public CustomUserDetailsService(UserRespository userRespository) {
        this.userRespository = userRespository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRespository.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("User Not Found");
        }
        return user;
    }

    @Transactional
    public User loadUserById(Long id) {
        User user = userRespository.getById(id);
        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

}
