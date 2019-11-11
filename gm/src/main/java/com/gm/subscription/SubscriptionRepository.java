package com.gm.subscription;

import com.gm.subscription.Subscription;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface SubscriptionRepository extends CrudRepository<Subscription,Long> {
}
