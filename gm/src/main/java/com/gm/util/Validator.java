package com.gm.util;

import com.gm.product.Product;

import java.time.YearMonth;
import java.util.InputMismatchException;
import java.util.Set;

public class Validator {

    public boolean validString(String word) throws ValidatorException{
        if (word.equals("")) {
            throw new ValidatorException("String: " + word + " is empty.");
        } else if (word.startsWith(" ")) {
            throw new ValidatorException("String: " + word + " starts with space.");
        }
            return true;

    }


    public boolean validEmail(String email) throws ValidatorException{
        if (((email.endsWith(".com") || (email.endsWith(".com.br"))) && (email
                .matches("(.*)@(.*)")) == true)) {
            return true;
        } else {
            throw new ValidatorException("Email: " + email + " isn't valid");
        }
    }

    public boolean validValue(float value) throws ValidatorException {
        if (value <= 0)
            throw new ValidatorException("Value: " + value + "is <= 0");

        return value >= 0;
    }

    public boolean validValue(Double value) throws ValidatorException{
        if (value <= 0)
            throw new ValidatorException("Value: " + value + "is <= 0");

        return value >= 0;
    }
    public boolean validValueInt(int value) throws ValidatorException{
        if (!(value % 1 == 0 && value > 0))
                throw new ValidatorException("Value: " + value + "is <= 0");

        return value % 1 == 0 && value > 0;
    }


    public boolean validPassword(String password) throws ValidatorException{
        if (password.equals("")) {
            throw new ValidatorException("Password: " + password + "is empty");
        } else if (password.startsWith(" ")) {
            throw new ValidatorException("Password: " + password + "starts with space");
        } else if (password.length() < 5) {
            throw new ValidatorException("Password: " + password + "has less than 5 digits");
        }

            return true;
    }

    //metodo do repositorio publico: https://github.com/feharaujo/Cpf-Validator/blob/master/src/com/fearaujo/CpfValidator.java
    public boolean validCPF(String CPF) throws ValidatorException{
        if (CPF == null)
            throw new ValidatorException("CPF should'nt be null");

        // considera-se erro CPF's formados por uma sequencia de numeros iguais
        if (CPF.equals("00000000000") || CPF.equals("11111111111") || CPF.equals("22222222222") ||
                CPF.equals("33333333333") || CPF.equals("44444444444") || CPF.equals("55555555555")
                || CPF.equals("66666666666") || CPF.equals("77777777777") || CPF.equals("88888888888")
                || CPF.equals("99999999999") || (CPF.length() != 11))
            throw new ValidatorException("CPF: " + CPF + "has equal characters or has a length != 11");
        char dig10,
                dig11;
        int sm, i, r, num, peso;
        // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
        try {
            // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {
                // converte o i-esimo caractere do CPF em um numero:
                // por exemplo, transforma o caractere '0' no inteiro 0
                // (48 eh a posicao de '0' na tabela ASCII)
                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }
            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char) (r + 48);
            // converte no respectivo caractere numerico
            // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;
            for (i = 0; i < 10; i++) {
                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }
            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else dig11 = (char) (r + 48);
            // Verifica se os digitos calculados conferem com os digitos informados.
            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
                return (true);
            else
                throw new ValidatorException("CPF: "+ CPF + "is a invalid CPF");
        } catch (InputMismatchException erro) {
                throw new ValidatorException("CPF: "+ CPF + "is a invalid CPF");
        }
    }

    public boolean validListProduct(Set<Product> products) throws ValidatorException{
        if (products.size() <= 0)
            throw new ValidatorException("Products size: "+ products.size() + " <= 0");

        return products.size() >= 0 && validListProductAux(products);
    }

    public boolean validListProductAux(Set<Product> products) throws ValidatorException{
        boolean status = true;
        if (products.size() == 0) {
            return false;
        } else {
            for (Product product : products) {
                if (validString(product.getName()) && validString(product.getBarcode()) &&
                        validString(product.getBrand()) && validString(product.getDistributor())
                        && validValue(product.getCost()) && validValue(product.getPrice())
                        && validValueInt(product.getStock())) {
                    status = true;
                } else {
                    status = false;
                    break;
                }
            }
        }
        return status;
    }

    public boolean validSubscriptionType(Object type) throws ValidatorException{
        if (!(type instanceof SubscriptionType))
            throw new ValidatorException("Object: " + type + "Not an instance of Valid Subscription Type");

        return type instanceof SubscriptionType;
    }

    public boolean validProductType(Object type) throws ValidatorException{
        if (!(type instanceof ProductType))
            throw new ValidatorException("Object: " + type + "Not an instance of Valid Product Type");

        return type instanceof ProductType;
    }

    public boolean validYearMonthSubscription(Object yearMonth) throws ValidatorException{
        if (!(yearMonth instanceof YearMonth))
            throw new ValidatorException("Object: " + yearMonth + " Not an instance of YearMonth Type");
        return yearMonth instanceof YearMonth;
    }
}
