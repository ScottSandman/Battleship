package com.company;

/**
 * Classic Battleship Game
 * This class implements a player vs computer game of Battleship.
 * Uses a generic 10x10 ocean grid for placing ships, tracking hits and misses;
 * Player and computer alternate turns; remaining ships updated after each round.
 * Game ends when either player or computer has no remaining ships.
 * @author: Scott Sandman
 * @version: 1.0
 */

import java.util.Scanner;

public class Main {

    //Scanner required for user input
    Scanner input = new Scanner(System.in);
    
    /**
     * 2d array representing ocean grid. Starting values are null.
     */
    String[][] ocean = new String[10][10];
    // integer variable to track number of player ships
    static int numPlayerShips = 5;
    // integer variable to track computer ships
    static int numComputerShips = 5;
    
    /**
     * Method to print ocean grid
     * 0 - represents a missed turn by either player or computer.
     * 1 - represents player ships (shown on ocean grid as "@".
     * 2 - represents computer ships (not shown on ocean grid).
     * '*' - represents a ship that has been sunk.
     */
    public void printGrid() {
        System.out.println("  0123456789  ");
        for (int row=0; row < ocean.length; row++) {
            System.out.print(row + "|");
            for (int col=0; col < ocean[row].length; col++) {
                if (ocean[row][col] == null) {
                    System.out.print(" ");
                } else if (ocean[row][col] == "1") {
                    System.out.print("@");
                } else if (ocean[row][col] == "2") {
                    System.out.print(" ");
                } else {
                    System.out.print(ocean[row][col]);
                }
            }
            System.out.println("|" + row);
       }
        System.out.println("  0123456789  ");
    }
    
    /**
     * Method to deploy players ships.
     * Accepts x and y coordinates; value between 0 and 9.
     */
    public void playerDeployShips() {
        System.out.println("It's time to deploy your ships");
        int count = 1;
        while (count<=5) {
            System.out.print("Enter x coordinate for your ship " +count+ ": ");
            int x = input.nextInt();
            System.out.print("Enter y coordinate for your ship " +count+ ": ");
            int y = input.nextInt();
            if (x >= 10 || y >= 10) {
                System.out.println("Coordinate is outside grid. Please select again.");
            } else if (ocean[x][y] == "1") {
                System.out.println("You already have a ship there!");
            } else {
                ocean[x][y] = "1";
                System.out.println("Ship " +count+ " has been deployed.");
                count++;
            }
        }
    }
    
    /**
     * Method to deploy computer ships.
     * Selects x and y coordinates between 0 and 9 using random selections.
     * Does not allow computer to deploy ships if the players ship occupies the spot on the grid.
     */
    public void computerDeployShips() {
        System.out.println("The computer is deploying ships.");
        int count = 1;
        while (count<=5) {
            int x = (int)(Math.random() * 10);
            int y = (int)(Math.random() * 10);
            if (ocean[x][y] != "1" && x <= 9 && y <= 9) {
                ocean[x][y] = "2";
                System.out.println("Ship " +count+ " deployed.");
                count++;
            }
        }
    }
    
    /**
     * Method for player to attempt to sink computer's ship.
     * Accepts x and y coordinates.
     * Verifies coordinate is in ocean grid.
     * Notifies the player if they have sunk their own ship, the computer's ship or missed.
     * Modifies numPlayerShips or numComputerShips if a ship was sunk.
     */
    public void playerTurn() {
        System.out.println("YOUR TURN");
        int count = 1;
        while (count>0) {
            System.out.print("Enter x coordinate: ");
            int x = input.nextInt();
            System.out.print("Enter y coordinate: ");
            int y = input.nextInt();
            if (x > 9 || y > 9) {
                System.out.println("Please select inside the grid!");
            } else if (ocean[x][y] == "1") {
                System.out.println("You sunk your own ship!");
                ocean[x][y] = "*";
                count--;
                numPlayerShips--;
            } else if (ocean[x][y] == "2") {
                System.out.println("Boom! You sunk the ship!");
                ocean[x][y] = "*";
                count--;
                numComputerShips--;
            } else {
                System.out.println("You missed!");
                ocean[x][y] = "0";
                count--;
            }
        }
    }
    
    /**
     * Method to implement computer's turn.
     * Generates random x and y coordinates between 0 and 9.
     * Verifies coordinates have not already been guessed.
     * Notifies player if their ship or a computer's ship has been sunk.
     * Modifies numPlayerShips or numComputerShips if a ship was sunk.
     */
    public void computerTurn() {
        System.out.println("COMPUTER'S TURN");
        int count = 1;
        while (count>0) {
            int x = (int)(Math.random() * 10);
            int y = (int)(Math.random() * 10);
            if (x <= 9 && y <= 9 && ocean[x][y] != "0" && ocean[x][y] != "*") {
                if (ocean[x][y] == "2") {
                    System.out.println("The Computer sank it's own ship!");
                    ocean[x][y] = "*";
                    numComputerShips--;
                    count--;
                } else if (ocean[x][y] == "1") {
                    System.out.println("The Computer sank your ship!");
                    ocean[x][y] = "*";
                    numPlayerShips--;
                    count--;
                } else {
                    System.out.println("Computer Missed.");
                    ocean[x][y] = "0";
                    count--;
                }
            }
        }
    }
    
    /**
     * main method implementing the game.
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("*** Welcome to Battleship! ***");
        System.out.println();
        Main bs = new Main();
        bs.printGrid();
        bs.playerDeployShips();
        bs.printGrid();
        bs.computerDeployShips();
        bs.printGrid();
        
        while (numPlayerShips > 0 && numComputerShips > 0) {
            bs.playerTurn();
            bs.computerTurn();
            bs.printGrid();
            System.out.println("Your Ships: " + numPlayerShips + " | Computer Ships: " + numComputerShips);
        }
        
        if (numComputerShips == 0) {
            System.out.println("Congratulations! You won!");
        } else {
            System.out.println("Sorry. The Computer won.");
        }
    }
}