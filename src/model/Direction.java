package model;

public enum Direction {
    NORTH, SOUTH, EAST, WEST;

    public static Direction direction(char c) {
        switch (c) {
            case 'I': 
              return NORTH;
            case 'K': 
              return SOUTH;
            case 'J': 
              return WEST;
            case 'L': 
              return EAST;
            default: 
              return NORTH;
        }
    }
}
