package com.example.ticketPlatform.filter;


import com.example.ticketPlatform.Repositories.UserRepo;
import com.example.ticketPlatform.domain.entities.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

import static com.example.ticketPlatform.util.JwtUtil.JwtRoleExtractor.extractRoleEndingWithRole;

@Component
@RequiredArgsConstructor
public class UserProvisioningFilter extends OncePerRequestFilter {
    private final UserRepo userRepo;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null
                && authentication.isAuthenticated()
                && authentication.getPrincipal() instanceof Jwt jwt){
            UUID keyclockId = UUID.fromString(jwt.getSubject());
            if (!userRepo.existsById(keyclockId)){
                User newUser = new  User();
                newUser.setId(keyclockId);
                newUser.setEmail(jwt.getClaimAsString("email"));
                newUser.setName(jwt.getClaimAsString("preferred_username"));
                String role = extractRoleEndingWithRole(jwt.getTokenValue());
                newUser.setRole(role);
                userRepo.save(newUser);
            }
        }
        filterChain.doFilter(request,response);



    }
}
