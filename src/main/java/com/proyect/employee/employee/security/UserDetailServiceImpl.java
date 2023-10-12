package com.proyect.employee.employee.security;

import com.proyect.employee.employee.entities.Role;
import com.proyect.employee.employee.entities.User;
import com.proyect.employee.employee.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);
        if(user == null){
                throw new UsernameNotFoundException("User not found!.");
        }
        return new CustomUserDetails(user.getId(),user.getUsername(),
                user.getPassword(),
                getAuthorities(user.getRoles()),user.getIsActive());
    }

    public CustomUserDetails loadUserById(String id) throws UsernameNotFoundException {

        User user = userRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with id %s not found!.",id)));

        return new CustomUserDetails(user.getId(),user.getUsername(),
                user.getPassword(),
                getAuthorities(user.getRoles()),user.getIsActive());
    }


    public Collection<? extends GrantedAuthority> getAuthorities(Set<Role> roles) {
        return roles.stream()
                .flatMap(role -> role.getPermissions().stream())
                .map(permission -> new SimpleGrantedAuthority(permission.getCode().getCode()))
                .collect(Collectors.toSet());
    }
}
