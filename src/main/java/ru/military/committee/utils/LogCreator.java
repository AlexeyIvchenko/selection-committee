package ru.military.committee.utils;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;

import java.security.Principal;

public class LogCreator {
    public static void logEvent(Logger log, Principal principal, String logType, String logMessage) {
        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        switch (logType) {
            case "info": {
                log.info("<" + loginedUser.getUsername() + ">" + logMessage);
                break;
            }
            case "error": {
                log.error("<" + loginedUser.getUsername() + ">" + logMessage);
                break;
            }

        }
    }
}
