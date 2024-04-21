package com.exampl.security;

import com.exampl.domain.Admin;
import com.exampl.domain.User;
import com.exampl.repository.AdminRepository;
import com.exampl.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AdminRepository adminRepository;

    @Autowired
    public CustomUserDetailService(UserRepository userRepository, AdminRepository adminRepository) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(username);
        if (user != null) {
            // User found in the User table
            return buildUserDetails(user, "USER");
        } else {
            Admin admin = adminRepository.findAdminByEmail(username);
            if (admin != null) {
                // User found in the Admin table
                return buildUserDetails(admin, "ADMIN");
            } else {
                throw new UsernameNotFoundException("User not found.");
            }
        }
    }

    private UserDetails buildUserDetails(Object user, String role) {
        if (user instanceof User) {
            return org.springframework.security.core.userdetails.User
                    .withUsername(((User) user).getEmail())
                    .password(((User) user).getPasswordUser())
                    .roles(role)
                    .build();
        } else if (user instanceof Admin) {
            return org.springframework.security.core.userdetails.User
                    .withUsername(((Admin) user).getEmail())
                    .password(((Admin) user).getPasswordAdmin())
                    .roles(role)
                    .build();
        }
        throw new UsernameNotFoundException("User not found.");
    }
}
