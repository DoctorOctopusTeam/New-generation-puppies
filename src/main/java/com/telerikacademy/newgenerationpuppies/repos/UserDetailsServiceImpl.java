package com.telerikacademy.newgenerationpuppies.repos;

import com.telerikacademy.newgenerationpuppies.models.User;
import com.telerikacademy.newgenerationpuppies.models.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    //COULD NOT UNDERSTAND WHY THIS CANNOT BE INJECTED
    //@Autowired
    private UserRepository userRepository= new UserRepositoryImpl();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user1 = userRepository.findByUsername(username);
        if(user1 == null){
            throw new UsernameNotFoundException(username);
        }
    //--------------
        return UserPrincipal.create(user1);
    //----------------
//        return new
//                org.springframework.security.core.userdetails.User(
//                user1.getUserName(), user1.getPassword(), emptyList());
    }


}
