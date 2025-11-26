public class Fruit extends Product {
    public Fruit(String s, String n, double p) {
        super(n, p, ProductType.FRUIT);
    }

    @Override
    public String generateSerialNumber() {
        String serialNumber = "";

        serialNumber += "FRU";
        serialNumber += super.generateSerialNumber();

        return serialNumber;
    }
}
