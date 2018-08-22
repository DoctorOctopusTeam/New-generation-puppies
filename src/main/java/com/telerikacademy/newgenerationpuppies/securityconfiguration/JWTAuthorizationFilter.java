package com.telerikacademy.newgenerationpuppies.securityconfiguration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.telerikacademy.newgenerationpuppies.models.User;
import com.telerikacademy.newgenerationpuppies.models.UserPrincipal;
import com.telerikacademy.newgenerationpuppies.repos.UserDetailsServiceImpl;
import com.telerikacademy.newgenerationpuppies.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//import static com.auth0.samples.authapi.springbootauthupdated.security.SecurityConstants.HEADER_STRING;
//import static com.auth0.samples.authapi.springbootauthupdated.security.SecurityConstants.SECRET;
//import static com.auth0.samples.authapi.springbootauthupdated.security.SecurityConstants.TOKEN_PREFIX;



public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    //---------------------------------------------------------
    @Autowired
    private UserDetailsService udsi = new UserDetailsServiceImpl();

    public JWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
        //this.uds = uds;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null) {
            // parse the token.
            String user = JWT.require(Algorithm.HMAC512("SecretKeyToGenJWTs".getBytes()))
                    .build()
                    .verify(token.replace("Bearer ", ""))
                    .getSubject();

            if (user != null) {
                //------------------------------------------------------------------------
                //LOADS THE CURRENT STATUS OF THE USER IN THE DB
                UserDetails up = udsi.loadUserByUsername(user);
                //------------------------------------------------------------------------

                return new UsernamePasswordAuthenticationToken(up, null, up.getAuthorities());//user, null, new ArrayList<>();
            }
            return null;
        }
        return null;
    }

}
