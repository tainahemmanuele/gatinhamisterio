package com.gm.order;

import com.gm.order.Order;
import com.gm.subscription.Subscription;
import com.gm.user.User;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableScan
public interface OrderRepository extends CrudRepository<Order, Long> {

    //@Query("SELECT o FROM Order o WHERE o.user.id = :user_id")
    public List<Order> findOrderByUserId(@Param("user_id") long user_id);

    //@Query("SELECT o FROM Order o WHERE o.user.email = :email")
    public List<Order> findOrderByUserEmail(@Param("email") String email);

    //@Query("SELECT o FROM Order o WHERE o.subscription.id = :subscription_id")
    public List<Order> findOrderBySubscriptionId(@Param("subscription_id") long subscription_id);

   // @Query("SELECT o FROM Order o WHERE o.user.id = :user_id and o.subscription.id = :subscription_id")
    public Order findOrderBySubscriptionAndSubscription(@Param("user_id") long user_id, @Param("subscription_id") long subscription_id);

}
