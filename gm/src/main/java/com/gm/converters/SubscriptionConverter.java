package com.gm.converters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.gm.subscription.Subscription;
import com.gm.util.SubscriptionType;

import java.time.YearMonth;

public class SubscriptionConverter implements DynamoDBTypeConverter<String, Subscription> {

    @Override
    public String convert(Subscription object) {
        Subscription subscriptionObject = (Subscription) object;
        String subscription = null;
        try {
            if (subscriptionObject != null) {
                String box = new BoxConverter().convert(subscriptionObject.getBox());
                subscription = String.format("%s , %s , %s , %s  ", subscriptionObject.getType(),
                        subscriptionObject.getPrice(), subscriptionObject.getSubscriptionYearMonth(),
                        box);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return subscription;
    }

    @Override
    public Subscription unconvert(String s) {

        Subscription subscription = new Subscription();
        try {
            if (s != null && s.length() != 0) {
                String[] data = s.split(",");
                SubscriptionType subs = SubscriptionType.valueOf(data[0].trim());
                subscription.setType(subs);
                subscription.setPrice(Double.parseDouble(data[1].trim()));
                subscription.setSubscriptionYearMonth(YearMonth.parse(data[2].trim()));
                subscription.setBox(new BoxConverter().unconvert(data[3].trim()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return subscription;
    }
}
