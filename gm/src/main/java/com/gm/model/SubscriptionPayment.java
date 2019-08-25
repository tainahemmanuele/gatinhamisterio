package com.gm.model;

import com.gm.util.DispatchStatus;
import com.gm.util.SubscriptionPaymentStatus;
import com.gm.util.SubscriptionPaymentType;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class SubscriptionPayment implements Serializable {

    @ManyToOne
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("subscription_id")
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;

    private int quantity;
    private DispatchStatus dispatchStatus;
    private SubscriptionPaymentType paymentType;
    private SubscriptionPaymentStatus paymentStatus;

    public SubscriptionPayment() {}

    public SubscriptionPayment(User user, Subscription subscription, int quantity, DispatchStatus dispatchStatus, SubscriptionPaymentType paymentType, SubscriptionPaymentStatus paymentStatus) {
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
        if (!(o instanceof SubscriptionPayment)) return false;
        SubscriptionPayment that = (SubscriptionPayment) o;
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

    public SubscriptionPaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(SubscriptionPaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public SubscriptionPaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(SubscriptionPaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
