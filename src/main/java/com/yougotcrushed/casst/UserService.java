package com.yougotcrushed.casst;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private NotificationService notificationService;
    private UserRepository userRepository;

    public UserService(NotificationService notificationService, UserRepository userRepository) {
        this.notificationService = notificationService;
        this.userRepository = userRepository;

    }

    public void registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("User with email" + user.getEmail() + " already exist");
        }
        userRepository.save(user);
        notificationService.send("You register successfully!", user.getEmail());
    }

}
