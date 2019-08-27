package com.gm.util;

public class Validator {

    public boolean validString(String name){
        if(name.equals("")){
            return false;
        }else if(name.startsWith(" ")){
            return false;
        }else{
            return true;
        }
    }


    public boolean validEmail(String email){
        if (((email.endsWith(".com") || (email.endsWith(".com.br"))) && (email
                .matches("(.*)@(.*)")) == true)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validPrice(float price){
        return price > 0;
    }

    public boolean validCost(float cost){
        return cost > 0;
    }



}
