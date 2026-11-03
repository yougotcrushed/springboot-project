package com.yougotcrushed.casst;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class CasstApplication {


//    public static void main(String[] args) {
//        ApplicationContext context = SpringApplication.run(CasstApplication.class, args);
//        var orderService = context.getBean(OrderService.class);
//        orderService.placeOrder();
//    }

//    public static void main(String[] args) {
//            ApplicationContext context = SpringApplication.run(CasstApplication.class, args);
//            var notificationManager = context.getBean(NotificationManager.class);
//            notificationManager.sendNotification();
//        }

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(CasstApplication.class, args);
        var userService = context.getBean(UserService.class);
        userService.registerUser(new User(1L, "okechukwu@gmail.com", "123456", "Ben"));
    }

}
