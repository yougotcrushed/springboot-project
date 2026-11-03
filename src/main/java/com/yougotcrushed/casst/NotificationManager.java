package com.yougotcrushed.casst;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class NotificationManager {
    private NotificationService notificationService;

    public NotificationManager(@Qualifier("sms") NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public void sendNotification() {
        notificationService.send("Just want to send you a very big congratulation on your tech career, God bless you.", "okechukwu@gmail.com");
    }
}
