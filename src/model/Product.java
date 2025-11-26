package model;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public abstract class Product {
    private String serialNumber;
    private String name;
    private double price;
    private ProductType type;

    private static final Random RANDOM = new Random();

    public Product(String name, double price, ProductType type) {
        this.name = name;
        this.price = price;
        this.type = type;
        this.serialNumber = generateSerialNumber();
    }

    public String getSerialNumber() {
        return serialNumber; 
    }
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price; 
    }
    public ProductType getType() {
        return type; 
    }

    protected String generateSerialNumber() {
        String serial = "";
        List<Integer> takenNumbers = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            int digit;
            do {
                digit = RANDOM.nextInt(10);
            } while (takenNumbers.contains(digit));
            serial += digit;
            takenNumbers.add(digit);
        }
        return serial;
    }
}
