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

    public boolean validValue(float value){
        return value >= 0;
    }

    public boolean validValueInt(int value){
        return value % 1 == 0 && value > 0;
    }






}
