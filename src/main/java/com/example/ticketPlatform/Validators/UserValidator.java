package com.example.ticketPlatform.Validators;

import com.example.ticketPlatform.Repositories.UserRepo;
import com.example.ticketPlatform.domain.entities.User;
import com.example.ticketPlatform.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserValidator {
    private final UserRepo userRepo;

    public User getUserIfExists(UUID userId, String role) {
        return userRepo.findById(userId).orElseThrow(
                () -> new UserNotFoundException("{role}"+" not found")
        );
    }
}
