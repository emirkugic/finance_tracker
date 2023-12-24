package ba.edu.ibu.finance_tracker.core.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import ba.edu.ibu.finance_tracker.rest.websockets.MainSocketHandler;

@Service
public class NotificationService {
    private final MainSocketHandler mainSocketHandler;

    public NotificationService(MainSocketHandler mainSocketHandler) {
        this.mainSocketHandler = mainSocketHandler;
    }

    public void broadcastMessage(String message) throws IOException {
        mainSocketHandler.broadcastMessage(message);
    }

    public void sendMessage(String userId, String message) {
        mainSocketHandler.sendMessage(userId, message);
    }
}