package com.ganzo.delivery.authentication_server.services.security;

import com.ganzo.libreries.entity.Enrollment;
import com.ganzo.libreries.entity.Role;
import com.ganzo.libreries.entity.User;
import com.ganzo.libreries.repository.EnrollmentRepository;
import com.ganzo.libreries.repository.RolRepository;
import com.ganzo.libreries.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EnrollmentRepository enrollmentRepository;
    @Autowired
    private RolRepository rolRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(username);
        List<Role> roles = new ArrayList<>();
        if (userOptional.isPresent()) {
            Optional<Set<Enrollment>> enrollments = enrollmentRepository.findByUser(userOptional.get().getUserId());
            if (enrollments.isPresent()) {
                roles = rolRepository.findAllById(enrollments.get().stream().map(Enrollment::getRole).toList());
            }
        }
        return new org.springframework.security.core.userdetails.User(userOptional.get().getEmail(), userOptional.get().getPassword(),
                getAuthorities(roles
                        .stream()
                        .map(Role::getTitle)
                        .collect(Collectors.toList())));
    }

    private Collection<GrantedAuthority> getAuthorities(List<String> roles){
        Collection<GrantedAuthority> authorities = new ArrayList<>(roles.size());
        for (String rol : roles) authorities.add(new SimpleGrantedAuthority(rol.toUpperCase()));
        return authorities;
    }

}
