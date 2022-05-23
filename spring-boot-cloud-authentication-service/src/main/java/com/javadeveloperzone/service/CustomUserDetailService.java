package com.javadeveloperzone.service;
import com.javadeveloperzone.entity.User;
import com.javadeveloperzone.payroll.UserRequest;
import com.javadeveloperzone.repo.UserRepository;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    public CustomUserDetailService(UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s) //
            .orElseThrow(()-> new RuntimeException("Пользователь "+s+" не найден"));
    }

    public ResponseEntity<?> registration(UserRequest user){
        EntityModel<User> entityUser = EntityModel.of(
                userRepository.save(userRepository.findByUsername(user.getUsername()).orElseGet(
                        () -> {
                            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
                            return User.builder()
                                    .username(user.getUsername())
                                    .password(user.getPassword()).build();
                })));
        return ResponseEntity //
                .created(entityUser.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityUser);
    }
}
