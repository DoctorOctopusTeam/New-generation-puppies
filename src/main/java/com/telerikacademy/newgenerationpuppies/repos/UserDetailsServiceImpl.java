package com.telerikacademy.newgenerationpuppies.repos;

import com.telerikacademy.newgenerationpuppies.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import static java.util.Collections.emptyList;

public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user1 = userRepository.findByUsername(username);
        if(user1 == null){
            throw new UsernameNotFoundException(username);
        }
        //return new User(user1.getUserName(), user1.getPassword(), emptyList());
        return new
                org.springframework.security.core.userdetails.User(
                user1.getUserName(), user1.getPassword(), emptyList());
    }


}
