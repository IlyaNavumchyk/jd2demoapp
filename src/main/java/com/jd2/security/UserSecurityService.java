package com.jd2.security;

import com.jd2.domain.Role;
import com.jd2.domain.User;
import com.jd2.repository.role.RoleRepositoryInterface;
import com.jd2.repository.user.UserRepositoryInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class UserSecurityService implements UserDetailsService {

    private final UserRepositoryInterface userRepository;

    private final RoleRepositoryInterface roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> searchResult = userRepository.findByLogin(username);

        if (searchResult.isPresent()) {

            User user = searchResult.get();

            //We are creating Spring Security User object
            return new org.springframework.security.core.userdetails.User(
                    user.getUserLogin(),
                    user.getUserPassword(),
//                        ["ROLE_USER", "ROLE_ADMIN"]
                    AuthorityUtils.commaSeparatedStringToAuthorityList(
                            roleRepository.findRolesByUserId(user.getId())
                                    .stream()
                                    .map(Role::getRoleName)
                                    .collect(Collectors.joining(","))
                    )
            );
        } else {
            throw new UsernameNotFoundException(String.format("User with login %s not found", username));
        }
    }
}
