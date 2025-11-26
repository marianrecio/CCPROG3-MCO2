package model;

public class Alcohol extends Product {
    public Alcohol(String s, String n, double p) {
        super(n, p, ProductType.ALCOHOL);
    }

    @Override
    public String generateSerialNumber() {
        String serialNumber = "";

        serialNumber += "ALC";
        serialNumber += super.generateSerialNumber();

        return serialNumber;
    }
}
