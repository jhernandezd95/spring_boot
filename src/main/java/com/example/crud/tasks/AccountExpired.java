package com.example.crud.tasks;

import com.example.crud.modules.auth.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AccountExpired {

    private static final Logger log = LoggerFactory.getLogger(AccountExpired.class);
    private static final int DAYS = 30;
    @Autowired
    private UserService userService;

    @Scheduled(cron = "0 0 0 * * *")
    public void reportCurrentTime() {
        log.info("Executing account expired task...");
        userService.expireAccounts(DAYS);
        log.info("Account expired task finished.");
    }

}
