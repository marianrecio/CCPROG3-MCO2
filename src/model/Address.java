public class Address {
    private String floor;    
    private String group;    
    private int number;     

    public Address(String floor, String group, int number) {
        this.floor = floor;
        this.group = group;
        this.number = number;
    }

    public String getFloor() {
        return floor;
    }

    public String getGroup() {
        return group;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return floor + ", " + group + ", #" + number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address other = (Address) o;
        return number == other.number &&
               floor.equals(other.floor) &&
               group.equals(other.group);
    }
}
