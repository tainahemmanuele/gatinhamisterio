package com.gm;

import com.gm.box.Box;
import com.gm.box.BoxRepository;
import com.gm.order.Order;
import com.gm.order.OrderRepository;
import com.gm.product.Product;
import com.gm.product.ProductRepository;
import com.gm.subscription.Subscription;
import com.gm.subscription.SubscriptionRepository;
import com.gm.user.User;
import com.gm.user.UserRepository;
import com.gm.util.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.YearMonth;
import java.util.HashSet;
import java.util.Set;

@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication
public class GatinhamisterioApplication {
	public static void main(String[] args) {
		SpringApplication.run(GatinhamisterioApplication.class, args);
	}
}