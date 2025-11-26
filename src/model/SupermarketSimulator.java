package model;

import java.util.Scanner;

public class SupermarketSimulator {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Name: ");
        String name = sc.nextLine();

        System.out.print("Age: ");
        int age = Integer.parseInt(sc.nextLine());

        Shopper shopper = new Shopper(name, age, new Position(21, 10));

        Supermarket market = new Supermarket();
        market.initializeDisplays();

        while (market.isRunning()) {
            market.displayMap(shopper);

            System.out.print("Command (WASD move, IJKL turn, SPACE interact, V inventory, Q quit): ");
            String cmd = sc.nextLine().toUpperCase();

            if (cmd.equals("Q")) {
                market.stopSimulation();
            }
            else if (cmd.equals("V")) {
                shopper.viewInventory();
            }
            else if (cmd.equals(" ")) {
                Amenity a = market.getAmenityInFront(shopper);
                if (a != null) a.interact(shopper, market);
                else System.out.println("Nothing there.");
            }
            else if ("IJKL".contains(cmd)) {
                shopper.setFacing(Direction.direction(cmd.charAt(0)));
            }
            else if ("WASD".contains(cmd)) {
                shopper.move(cmd.charAt(0), market);
            }
        }

        System.out.println("Thanks for playing!");
    }
}
