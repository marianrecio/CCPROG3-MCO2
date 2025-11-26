package model;

import java.util.List;
import java.util.ArrayList;

public class Shopper {
    private String name;
    private int age;
    private Position position;
    private Direction facing = Direction.NORTH;
    private Equipment equipment;
    private List<Product> handItems = new ArrayList<>();

    public Shopper(String name, int age, Position start) {
        this.name = name;
        this.age = age;
        this.position = start;
    }

    public String getName() { 
        return name; 
    }
    public int getAge() { 
        return age; 
    }
    public Position getPosition() { 
        return position;
    }
    public Direction getFacing() { 
        return facing; 
    }

    public void setFacing(Direction d) { 
        facing = d; 
    }

    public boolean addProduct(Product p) {
        if (age < 18 && p.getType() == ProductType.ALCOHOL) {
            System.out.println("You are underage.");
            return false;
        }

        if (equipment != null)
            return equipment.addItem(p);

        if (handItems.size() < 2) {
            handItems.add(p);
            return true;
        }

        System.out.println("Hands are full.");
        return false;
    }

    public List<Product> getAllProducts() {
        List<Product> all = new ArrayList<>();
        if (equipment != null) all.addAll(equipment.getItems());
        all.addAll(handItems);
        return all;
    }

    public void clearAllProducts() {
        if (equipment != null) equipment.clear();
        handItems.clear();
    }

    public void viewInventory() {
        List<Product> all = getAllProducts();

        if (all.isEmpty()) {
            System.out.println("Inventory empty.");
            return;
        }

        System.out.println("Inventory:");
        for (Product p : all) {
            System.out.println("- " + p.getName());
        }  
    }

        public boolean equipBasket() {
        if (equipment == null) {
            equipment = new Basket();
            System.out.println("You picked up a basket.");
            return true;
        } else {
            System.out.println("You already have equipment!");
            return false;
        }
    }

    public boolean equipCart() {
        if (equipment == null) {
            equipment = new Cart();
            System.out.println("You picked up a cart.");
            return true;
        } else {
            System.out.println("You already have equipment!");
            return false;
        }
    }

    public void move(char dirChar, Supermarket market) {
        int newRow = position.getRow();
        int newCol = position.getCol();

        switch (dirChar) {
            case 'W': 
                newRow--; 
                break;
            case 'S': 
                newRow++; 
                break;
            case 'A': 
                newCol--; 
                break;
            case 'D': 
                newCol++; 
                break;
        }

        if (market.isPassable(newRow, newCol)) {
            position.setRow(newRow);
            position.setCol(newCol);
        } else {
            System.out.println("Blocked!");
        }
    }
}
