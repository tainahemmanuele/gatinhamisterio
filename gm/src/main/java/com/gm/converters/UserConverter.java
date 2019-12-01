package com.gm.converters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.gm.user.User;
import com.gm.util.UserRole;

public class UserConverter implements DynamoDBTypeConverter<String, User> {

    @Override
    public String convert(User object) {
        User userObject = (User) object;
        String user = null;
        try {
            if (userObject != null) {
                user = String.format("%s , %s , %s , %s , %s ", userObject.getName(), userObject.getEmail(), userObject.getPassword(),
                        userObject.getCPF(), userObject.getRole());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User unconvert(String s) {

        User user = new User();
        try {
            if (s != null && s.length() != 0) {
                String[] data = s.split(",");
                user.setName(data[0].trim());
                user.setEmail(data[1].trim());
                user.setPassword(data[2].trim());
                user.setCpf(data[3].trim());
                UserRole role = UserRole.valueOf(data[4].trim());
                user.setRole(role);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }
}
