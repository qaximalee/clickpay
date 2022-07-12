package com.clickpay.service.user;

import com.clickpay.errors.general.BadRequestException;
import com.clickpay.errors.general.EntityNotFoundException;
import com.clickpay.errors.general.EntityNotSavedException;
import com.clickpay.errors.user.UserNotActiveException;
import com.clickpay.model.user.User;
import com.clickpay.repository.user.UserRepository;
import com.clickpay.utils.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@Service
public class UserService implements IUserService, UserDetailsService {

    private static final String ADMIN = "ADMIN";
    private static final String DEALER = "DEALER";
    private static final String OFFICER = "OFFICER";

    private final UserRepository repo;

    @Autowired
    public UserService(final UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repo.findByUsernameIgnoreCase(username.trim());
        if (user == null) {
            log.error("Username: "+username+" is not found.");
            throw new UsernameNotFoundException("Authentication error username or password is incorrect.");
        }
        if (!user.isActive()) {
            log.error("Username: "+username+" is not active.");
            throw new UserNotActiveException("User is not verified. Please verify your account.");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.isActive(),
                true, true,
                true, getAuthorities()
                );
    }

    public Collection<GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();

        GrantedAuthority adminAuthority = () -> "ADMIN";
        grantedAuthorities.add(adminAuthority);
        GrantedAuthority officerAuthority = () -> "OFFICER";
        grantedAuthorities.add(officerAuthority);
        GrantedAuthority dealerAuthority = () -> "DEALER";
        grantedAuthorities.add(dealerAuthority);
        GrantedAuthority userAuthority = () -> "USER";
        grantedAuthorities.add(userAuthority);
        return grantedAuthorities;
    }

    @Override
    public User findByUsername(String username) throws EntityNotFoundException {
        User user = repo.findByUsername(username);
        if (user == null) {
            log.error("User not found with username: "+username);
            throw new EntityNotFoundException("User not found with provided username.");
        }
        return user;
    }

    @Override
    public User save(User user) throws BadRequestException, EntityNotSavedException {
        log.info("Creating user.");

        if (user == null) {
            log.error("User should not be null.");
            throw new BadRequestException("User should not be null.");
        }
        try {
            user = repo.save(user);
            log.debug("User with user id: "+user.getId()+ " created successfully.");
            return user;
        } catch (Exception e) {
            log.error("User can not be saved.");
            throw new EntityNotSavedException("User can not be saved.");
        }
    }
}
