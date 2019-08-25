package com.gm.repository;

import com.gm.model.SubscriptionPayment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionPaymentRepository extends CrudRepository<SubscriptionPayment, Long> {
}
