package com.gm.subscription;

import com.gm.subscription.Subscription;
import org.springframework.data.repository.CrudRepository;

public interface SubscriptionRepository extends CrudRepository<Subscription,Long> {
}
