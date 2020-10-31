package io.touhidjisan.projectmtool.services;

import io.touhidjisan.projectmtool.repositories.UserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRespository userRespository;

    @Autowired
    public UserService(UserRespository userRespository) {
        this.userRespository = userRespository;
    }
}
