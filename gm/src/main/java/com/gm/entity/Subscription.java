package com.gm.entity;

import com.gm.util.SubscriptionType;

import javax.persistence.*;
import java.io.Serializable;
import java.time.YearMonth;

public class Subscription implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private SubscriptionType type;

    
    YearMonth subscriptionYearMonth;
    Double Price;

}
