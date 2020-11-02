package io.touhidjisan.projectmtool.controller;

import io.touhidjisan.projectmtool.model.User;
import io.touhidjisan.projectmtool.payload.JWTLoginSuccessResponse;
import io.touhidjisan.projectmtool.payload.LoginRequest;
import io.touhidjisan.projectmtool.security.JwtTokenProvider;
import io.touhidjisan.projectmtool.services.MapValidationErrorService;
import io.touhidjisan.projectmtool.services.UserService;
import io.touhidjisan.projectmtool.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static io.touhidjisan.projectmtool.security.SecurityConstants.TOKEN_PREFIX;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private MapValidationErrorService mapValidationErrorService;
    private UserService userService;
    private UserValidator userValidator;

    private JwtTokenProvider jwtTokenProvider;
    private AuthenticationManager authenticationManager;

    @Autowired
    public UserController(MapValidationErrorService mapValidationErrorService, UserService userService,  UserValidator userValidator, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.mapValidationErrorService = mapValidationErrorService;
        this.userService = userService;
        this.userValidator = userValidator;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null) {
            return errorMap;
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = TOKEN_PREFIX + jwtTokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTLoginSuccessResponse(true, jwt));
    }




    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result) {

        // validate passwords match and length
        userValidator.validate(user, result);

        // validate other error
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null) {
            return errorMap;
        }
        User newUser = userService.saveUser(user);
        return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
    }

}
 