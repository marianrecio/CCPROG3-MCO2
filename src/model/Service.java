package model;

public abstract class Service implements Amenity {
    protected String name;

    public Service(String name) {
        this.name = name;
    }

    public boolean isPassable() { 
        return true; 
    }
}
