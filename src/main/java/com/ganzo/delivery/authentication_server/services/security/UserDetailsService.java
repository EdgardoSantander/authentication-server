package com.ganzo.delivery.authentication_server.services.security;

import com.ganzo.delivery.authentication_server.entity.Enrollment;
import com.ganzo.delivery.authentication_server.entity.User;
import com.ganzo.delivery.authentication_server.repository.EnrollmentRepository;
import com.ganzo.delivery.authentication_server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(username);
        if (userOptional.isPresent()) {
            Optional<Set<Enrollment>> enrollments = enrollmentRepository.findByUser(userOptional.get());
            enrollments.ifPresent(enrollmentList -> userOptional.get().setEnrollments(enrollmentList));
        }
        return new org.springframework.security.core.userdetails.User(userOptional.get().getEmail(), userOptional.get().getPassword(),
                getAuthorities(userOptional.get().getEnrollments()
                        .stream()
                        .map(enrollment -> {
                            return enrollment.getRole().getTitle();
                        })
                        .collect(Collectors.toList())));
    }

    private Collection<GrantedAuthority> getAuthorities(List<String> roles){
        Collection<GrantedAuthority> authorities = new ArrayList<>(roles.size());
        for (String rol : roles) authorities.add(new SimpleGrantedAuthority(rol.toUpperCase()));
        return authorities;
    }

}
