package com.example.auth_shop.data;

import com.example.auth_shop.model.Role;
import com.example.auth_shop.model.User;
import com.example.auth_shop.repository.RoleRepository;
import com.example.auth_shop.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Transactional
@Component
@RequiredArgsConstructor
public class DataInitial implements ApplicationListener<ApplicationReadyEvent> {

     private final UserRepository userRepository;
     private final PasswordEncoder passwordEncoder;
     private final RoleRepository roleRepository;


    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Set<String> defaultRoles = Set.of("ROLE_ADMIN", "ROLE_USER");
        createDefaultUserIfNotExist();
        createDefaultAdminIfNotExist();
        createDefaultRoleIfNotExist( defaultRoles);
//    createUser();
    }

    private void createDefaultUserIfNotExist() {
        Role userRole = roleRepository.findByName("ROLE_USER").get(0);
        for (int i = 0; i < 5; i++) {
            String defaultUser = "email" + i + "@gmail.com";
            if (userRepository.existsByEmail(defaultUser)) {
                continue;
            }
            User user = new User();
            user.setFirstName("fUser" + i );
            user.setLastName("lUser" + i);
            user.setEmail(defaultUser);
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRoles(Set.of(userRole));
            userRepository.save(user);
            System.out.println("Default user: " + defaultUser);
        }
    }

    private void createDefaultRoleIfNotExist(Set<String> roles) {
        roles.
                stream().filter(role -> roleRepository.findByName(role).isEmpty())
                .map(Role:: new).forEach(roleRepository::save);

    }

    private void createDefaultAdminIfNotExist() {
        Role adminRole = roleRepository.findByName("ROLE_ADMIN").get(0);
        for (int i = 0; i < 5; i++) {
            String defaultUser = "Admin" + i + "@gmail.com";
            if (userRepository.existsByEmail(defaultUser)) {
                continue;
            }
            User user = new User();
            user.setFirstName("admin" + i );
            user.setLastName("admin" + i);
            user.setEmail(defaultUser);
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRoles(Set.of(adminRole));
            userRepository.save(user);
            System.out.println("Default Admin: " + defaultUser);
        }
    }
}
