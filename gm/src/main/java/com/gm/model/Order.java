package com.gm.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gm.util.DispatchStatus;
import com.gm.util.PaymentStatus;
import com.gm.util.PaymentType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @ManyToOne
    @MapsId("subscription_id")
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;

    private int quantity;
    private DispatchStatus dispatchStatus;
    private PaymentType paymentType;
    private PaymentStatus paymentStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order() {}

    public Order(User user, Subscription subscription, int quantity, DispatchStatus dispatchStatus, PaymentType paymentType, PaymentStatus paymentStatus) {
        this.user = user;
        this.subscription = subscription;
        this.quantity = quantity;
        this.dispatchStatus = dispatchStatus;
        this.paymentType = paymentType;
        this.paymentStatus = paymentStatus;
    }

    @Override
    public String toString() {
        return "SubscriptionPayment{" +
                "user=" + user +
                ", subscription=" + subscription +
                ", quantity=" + quantity +
                ", dispatchStatus=" + dispatchStatus +
                ", paymentType=" + paymentType +
                ", paymentStatus=" + paymentStatus +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order that = (Order) o;
        return getQuantity() == that.getQuantity() &&
                Objects.equals(getUser(), that.getUser()) &&
                Objects.equals(getSubscription(), that.getSubscription()) &&
                getDispatchStatus() == that.getDispatchStatus() &&
                getPaymentType() == that.getPaymentType() &&
                getPaymentStatus() == that.getPaymentStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser(), getSubscription(), getQuantity(), getDispatchStatus(), getPaymentType(), getPaymentStatus());
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public DispatchStatus getDispatchStatus() {
        return dispatchStatus;
    }

    public void setDispatchStatus(DispatchStatus dispatchStatus) {
        this.dispatchStatus = dispatchStatus;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
