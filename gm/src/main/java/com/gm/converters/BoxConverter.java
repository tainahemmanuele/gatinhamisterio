package com.gm.converters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.gm.box.Box;

public class BoxConverter implements DynamoDBTypeConverter<String, Box>  {

        @Override
        public String convert(Box object) {
                Box boxObject = (Box) object;
                String box = "";
                try {
                        if (box != null) {
                                box = String.format("%s , %s , %s , %s , %s , %s",boxObject.getBarcode(), boxObject.getName(), boxObject.getCost(),
                                        boxObject.getPrice(), boxObject.getStock(), new ProductConverter().convert(boxObject.getProducts()));
                        }
                }
                catch (Exception e) {
                        e.printStackTrace();
                }
                return box;
        }

        @Override
        public Box unconvert(String s) {

                Box box = new Box();
                try {
                        if (s != null && s.length() != 0) {
                                String[] data = s.split(",");
                                box.setBarcode(data[0].trim());
                                box.setName(data[1].trim());
                                box.setCost(Float.parseFloat(data[2].trim()));
                                box.setPrice(Float.parseFloat(data[3].trim()));
                                box.setStock(Integer.parseInt(data[4].trim()));
                                box.setProducts(new ProductConverter().unconvert(data[5].trim()));
                        }
                }
                catch (Exception e) {
                        e.printStackTrace();
                }

                return box;
        }
}

