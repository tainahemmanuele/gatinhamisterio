package com.gm;

import com.gm.model.Subscription;
import com.gm.model.User;
import com.gm.repository.SubscriptionRepository;
import com.gm.repository.UserRepository;
import com.gm.util.SubscriptionType;
import com.gm.util.UserRole;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.YearMonth;

@SpringBootApplication
public class GatinhamisterioApplication {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private SubscriptionRepository subscriptionRepository;

	@Bean
	InitializingBean sendDatabase() {
		return () -> {
			userRepository.save(new User(0,"John Romero","john@gmail.com","123456",UserRole.ADMIN));
			userRepository.save(new User(1,"Kim Swift","kim@gmail.com","654321",UserRole.CLIENT));
			subscriptionRepository.save(new Subscription(0, SubscriptionType.DEFAULT,100.00, YearMonth.now(),0L));
			subscriptionRepository.save(new Subscription(1, SubscriptionType.PREMIUM,200.00, YearMonth.now(),1L));

		};
	}

	public static void main(String[] args) {
		SpringApplication.run(GatinhamisterioApplication.class, args);
	}


}
