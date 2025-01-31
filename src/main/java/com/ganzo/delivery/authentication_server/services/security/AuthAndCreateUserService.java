package com.ganzo.delivery.authentication_server.services.security;

import com.ganzo.delivery.authentication_server.config.security.jwt.JwtUtil;
import com.ganzo.delivery.authentication_server.dto.auth.RequestAuthentication;
import com.ganzo.delivery.authentication_server.dto.auth.ResponseAuthentication;
import com.ganzo.delivery.authentication_server.entity.User;
import com.ganzo.delivery.authentication_server.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthAndCreateUserService implements AuthAndCreateUser {

    private final Logger logger = LogManager.getLogger(AuthAndCreateUserService.class);

    public static final String idConst = "id";
    public static final String userConst = "user";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;


    public AuthAndCreateUserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService,
            JwtUtil jwtUtil
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public User createAccount(User user) throws Exception {
        try {
            int exist = userRepository.findByEmail(user.getEmail()).isPresent() ? 1 : 0;
            if (exist > 0) {
                throw new Exception("Could not create a new user");
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            user = userRepository.save(user);
        } catch (Exception ex) {
            throw new Exception("Could not create a new user");
        }
        return user;
    }

    @Override
    public ResponseAuthentication authentication(RequestAuthentication authentication) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authentication.getEmail(),
                    authentication.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getEmail());
            String jwt = jwtUtil.generateToken(userDetails, getClaims(authentication.getEmail()));
            return new ResponseAuthentication(jwt, jwtUtil.durationToken());
        } catch (BadCredentialsException e) {
            logger.warn("Cannot by login user with the email address: {}", authentication.getEmail());
            throw new Exception("Error unknown when tried login user, verify you user and password");
        }
    }

    private Map<String, Object> getClaims(String emailAddress) {
        Map<String, Object> claims = new HashMap<>();
        try {
            Optional<User> user = userRepository.findByEmail(emailAddress);
            claims.put(idConst, user.get().getUserId());
            claims.put(userConst, user.get().getEmail());
        } catch (RuntimeException exception) {
            logger.error("Error when tried obtain a specialist doctor");
        } catch (Exception e) {
            logger.error("Error when tried obtain any claims to add jwt, detail: {}", e);
        }
        return claims;
    }

}
