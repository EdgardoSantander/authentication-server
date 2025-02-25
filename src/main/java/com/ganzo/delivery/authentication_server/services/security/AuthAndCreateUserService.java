package com.ganzo.delivery.authentication_server.services.security;

import com.ganzo.delivery.authentication_server.config.security.jwt.JwtUtil;
import com.ganzo.delivery.authentication_server.dto.auth.RequestAuthentication;
import com.ganzo.delivery.authentication_server.dto.auth.ResponseAuthentication;
import com.ganzo.libreries.entity.User;
import com.ganzo.libreries.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public User createAccount(User user) throws Exception {
        try {
            int exist = userRepository.findByEmail(user.getEmail()).isPresent() ? 1 : 0;
            if (exist > 0) {
                throw new Exception("Could not create a new user");
            }

            String password = user.getPassword();

            String encode = passwordEncoder.encode(password);

            user.setPassword(encode);

            user = userRepository.save(user);

            user.setPassword(null);
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
