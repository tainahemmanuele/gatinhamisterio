package com.gm.validator;

public class ProductValidator {

    public boolean validName(String name){
        if(name.equals("")){
            return false;
        }else{
            return true;
        }
    }

    public boolean validPrice(float price){
        return price > 0;
    }

    public boolean validCost(float cost){
        return cost > 0;
    }

    
}
