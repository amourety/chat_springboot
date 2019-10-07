package ru.itis.schedulers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.repositories.TokenRepository;

import java.time.LocalDateTime;

@Configuration
@EnableScheduling
public class ExpiredTokensScheduler {

    @Autowired
    private TokenRepository tokensRepository;

    @Scheduled(cron = "0 0 * ? * *")
    @Transactional
    public void removeExpiredTokens() {
        System.out.println("Удалил");
        tokensRepository.deleteTokensByExpiredDateTimeBefore(LocalDateTime.now());
    }

}