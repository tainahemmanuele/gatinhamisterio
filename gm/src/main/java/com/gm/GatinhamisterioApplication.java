package com.gm;

import com.gm.model.*;
import com.gm.repository.*;
import com.gm.util.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class GatinhamisterioApplication {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private SubscriptionRepository subscriptionRepository;
	@Autowired
	private BoxRepository boxRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private OrderRepository orderRepository;

	@Bean
	InitializingBean sendDatabase() {
		return () -> {
			List<Product> prods = new ArrayList<Product>();
			prods.add(new Product("100","Batom",20.0f,30.0f,10,"Garnier","Garnier", ProductType.BATOM));
			prods.add(new Product("101","Agua Micelar",30.0f,30.0f,10,"asdr","Garnfdier", ProductType.AGUA_MICELAR));
			prods.add(new Product("102","Shampoo",40.0f,60.0f,10,"fdfasr","Garnisser", ProductType.RIMEL));
			prods.add(new Product("103","Pó de Arroz",16.0f,30.0f,10,"easfier","Garanier", ProductType.RIMEL));
			prods.add(new Product("104","Presilha",17.0f,30.0f,10,"ascer","Garniaer", ProductType.DEMAQUILANTE));
			User u1 = new User("Kim Swift","kim@gmail.com","654321",UserRole.CLIENT);
			Box b1 = new Box("0","Caixa Agosto",80.0f,199.0f,10,prods);
			Subscription s1 = new Subscription( SubscriptionType.DEFAULT,100.00, YearMonth.now(),b1);

			userRepository.save(new User("John Romero","john@gmail.com","123456",UserRole.ADMIN));
			userRepository.save(new User("Jen Zee","jen@gmail.com","111222",UserRole.CLIENT));
			//userRepository.save(u1);
			subscriptionRepository.save(s1);
			//orderRepository.save(new Order(u1,s1,1, DispatchStatus.WAITING, PaymentType.BOLETO,PaymentStatus.REQUESTED));
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(GatinhamisterioApplication.class, args);
	}

}
