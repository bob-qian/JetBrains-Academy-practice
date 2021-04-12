/**
 * A class to run the Battleship game.
 * @author Bob Qian
 * @version 1.0
 */

package battleship;

import java.util.*;

public class Main {

    public static final Scanner scnr = new Scanner(System.in);

    public static void main(String[] args) {

        BattleshipMap userMap = new BattleshipMap();
        System.out.println(userMap);

        //setUpShips(userMap);

        // Temporary code to pre-initialize game status
        userMap.addShip(new String[]{"F3", "F7"}, "Aircraft Carrier");
        userMap.addShip(new String[]{"A1", "D1"}, "Battleship");
        userMap.addShip(new String[]{"J10", "J8"}, "Submarine");
        userMap.addShip(new String[]{"B9", "D9"}, "Cruiser");
        userMap.addShip(new String[]{"I2", "J2"}, "Destroyer");
        System.out.println(userMap);

        System.out.println("The game starts!\n");
        BattleshipMap opponentShootingMap = new BattleshipMap();
        System.out.println(opponentShootingMap);

        System.out.println("Take a shot!\n");
        do {
            processOneShot(userMap, opponentShootingMap);
            if (!userMap.isDefeated()) {
                System.out.println("Try again:\n");
            }
        } while (!userMap.isDefeated());

        System.out.println("You sank the last ship. You won. Congratulations!");
    }

    /**
     * Loops through all 5 ships and takes user input to set location for each.
     * @param inputMap the BattleshipMap object for the ships to be created and stored in
     */
    public static void setUpShips(BattleshipMap inputMap) {
        String processStatus = "";

        // Ask for an input for each type of ship
        for (String shipType : shipsAndLengths.keySet()) {
            System.out.println("Enter the coordinates of the " +
                    shipType + " (" + shipsAndLengths.get(shipType) + " cells):\n");
            do {
                String[] userCoordinates = scnr.nextLine().split(" ");
                processStatus = inputMap.addShip(userCoordinates, shipType);
                if (!processStatus.equals("success")) {
                    System.out.println("\n" + processStatus + " Try again:\n");
                }
            } while (!processStatus.equals("success"));
            System.out.println("\n" + inputMap);
        }
    }

    private static void processOneShot(BattleshipMap userMap, BattleshipMap opponentShootingMap) {
        String processStatus = "";

        // Loop to take shots
        do {
            String userShot = scnr.nextLine();
            processStatus = userMap.takeShot(userShot, opponentShootingMap);
            if (!processStatus.equals("success")) {
                System.out.println("\n" + processStatus + " Try again:\n");
            }
        } while (!processStatus.equals("success"));
    }

    private static LinkedHashMap<String, Integer> shipsAndLengths = new LinkedHashMap<>();

    private static class BattleshipMap {
        private HashMap<String, String> map;

        /**
         * Constructor initializing a BattleshipMap object.
         * Creates internal map that has no ships, only "~".
         */
        public BattleshipMap() {
            //TODO Need to try to use ENUMS
            shipEnums();

            map = new HashMap<>();
            // Populate the battleship map
            for (int i = 65; i <= 74; i++) {
                for (int j = 1; j <= 10; j++) {
                    map.put((char)(i) + String.valueOf(j), "~");
                }
            }
        }

        //TODO Need to try to use ENUMS
        public void shipEnums() {
            shipsAndLengths.put("Aircraft Carrier", 5);
            shipsAndLengths.put("Battleship", 4);
            shipsAndLengths.put("Submarine", 3);
            shipsAndLengths.put("Cruiser", 3);
            shipsAndLengths.put("Destroyer", 2);
        }

        /**
         * Gets internal map for each BattleshipMap object.
         * @return the internal HashMap map for each BattleshipMap object.
         */
        public HashMap<String, String> getMap() {
            return map;
        }

        /**
         * Takes a "shot" at the specified cell location. This updates the "hidden"
         * map of the BattleshipMap object it is called on. The shot is represented
         * to the shooter on the visible map (opponentShootingMap).
         * @param cell the cell location of the "shot"
         * @param opponentShootingMap visible map that will show shot location
         * @return String denoting successful shot ("success") or an error message.
         */
        public String takeShot(String cell, BattleshipMap opponentShootingMap) {
            String cellBeingShot = map.get(cell);

            if (cellBeingShot == null) {
                return("Error! You entered the wrong coordinates!");
            }

            if (cellBeingShot.equals("O")) {
                map.put(cell, "X");
                opponentShootingMap.getMap().put(cell, "X");

                boolean isShipAlive = containsO(cell);
                // Revert the traverse
                revertTraverse();

                System.out.println("\n" + opponentShootingMap);
                if (!isShipAlive) {
                    System.out.println("You sank a ship! Specify a new target:");
                } else {
                    System.out.print("You hit a ship! ");
                }
            } else if (cellBeingShot.equals("X") || cellBeingShot.equals("M")) {
                return("Error! You already fired here.");
            } else {
                map.put(cell, "M");
                opponentShootingMap.getMap().put(cell, "M");

                System.out.println("\n" + opponentShootingMap);
                System.out.print("You missed. ");
            }

            return "success";
        }

        // Helper method checking bounds
        protected boolean inBounds(String cell) {
            char row = cell.charAt(0);
            int col = Integer.parseInt(cell.substring(1));

            if (row < 'A' || row > 'J') {
                return false;
            }

            if (col < 1 || col > 10) {
                return false;
            }

            return true;
        }

        // Helper method to implement recursive checks on adjacent cells for takeShot method
        // Returns true if a ship has been sunk and false if not
        private boolean containsO(String cell) {

            // Temporarily mark this cell as traversed
            map.put(cell, "T");

            char row = cell.charAt(0);
            int col = Integer.parseInt(cell.substring(1));

            String upperCell = (char) (row - 1) + String.valueOf(col);
            String lowerCell = (char) (row + 1) + String.valueOf(col);
            String leftCell = row + String.valueOf(col - 1);
            String rightCell = row + String.valueOf(col + 1);

            boolean doesUpperContainO = false;
            boolean doesLowerContainO = false;
            boolean doesLeftContainO = false;
            boolean doesRightContainO = false;

            if (inBounds(upperCell)) {
                if (map.get(upperCell).equals("O")) {
                    return true;
                } else if (map.get(upperCell).equals("X")) {
                    doesUpperContainO = containsO(upperCell);
                }
            }

            if (inBounds(lowerCell)) {
                if (map.get(lowerCell).equals("O")) {
                    return true;
                } else if (map.get(lowerCell).equals("X")) {
                    doesLowerContainO = containsO(lowerCell);
                }
            }

            if (inBounds(leftCell)) {
                if (map.get(leftCell).equals("O")) {
                    return true;
                } else if (map.get(leftCell).equals("X")) {
                    doesLeftContainO = containsO(leftCell);
                }
            }

            if (inBounds(rightCell)) {
                if (map.get(rightCell).equals("O")) {
                    return true;
                } else if (map.get(rightCell).equals("X")) {
                    doesRightContainO = containsO(rightCell);
                }
            }

            if (doesUpperContainO || doesLowerContainO || doesLeftContainO || doesRightContainO) {
                return true;
            }

            return false;
        }

        private void revertTraverse() {
            for (String cell : map.keySet()) {
                if (map.get(cell).equals("T")) {
                    map.put(cell, "X");
                }
            }
        }

        /**
         * Returns true if all ships in internal map have been hit (that is, no more Os left)
         * @return
         */
        public boolean isDefeated() {
            for (String cell : map.keySet()) {
                if (map.get(cell).equals("O")) {
                    return false;
                }
            }
            return true;
        }

        public String addShip(String[] cells, String shipType)
                throws IllegalArgumentException {
            final int SHIP_LENGTH = shipsAndLengths.get(shipType);

            HashSet<String> cellsOfShip = new HashSet<>();

            String startCell = cells[0];
            String endCell = cells[1];

            char startRow = startCell.charAt(0);
            char endRow = endCell.charAt(0);

            // Switch if input is in reverse
            if (startRow > endRow) {
                char temp = startRow;
                startRow = endRow;
                endRow = temp;
            }

            int startColumn = Integer.parseInt(startCell.substring(1));
            int endColumn = Integer.parseInt(endCell.substring(1));

            // Switch if input is in reverse
            if (startColumn > endColumn) {
                int temp2 = startColumn;
                startColumn = endColumn;
                endColumn = temp2;
            }

            // Check ship coordinates:
            if (startRow != endRow && startColumn != endColumn) {
                return "Error: Wrong ship location!";
            }

            // Horizontal ship?
            if (startRow == endRow) {
                // Check length
                if (endColumn - startColumn + 1 != SHIP_LENGTH) {
                    return("Error: wrong length of the " + shipType + "!");
                }

                // Check for collisions with other ships
                Set<String> collisionZone = new HashSet<>();

                // Left adjacent cell
                if (startColumn > 1) {
                    collisionZone.add(startRow + String.valueOf(startColumn-1));
                }

                // Right adjacent cell
                if (endColumn < 10) {
                    collisionZone.add(startRow + String.valueOf(endColumn+1));
                }

                // Top adjacent row
                if (startRow != 'A') {
                    for (int i = startColumn; i < (endColumn+1); i++) {
                        collisionZone.add((char)(startRow - 1) + String.valueOf(i));
                    }
                }

                // Bottom adjacent row
                if (startRow != 'J') {
                    for (int i = startColumn; i < (endColumn+1); i++) {
                        collisionZone.add((char)(startRow + 1) + String.valueOf(i));
                    }
                }

                // Check collision zone
                for (String cell : map.keySet()) {
                    if (map.get(cell).equals("O") && collisionZone.contains(cell)) {
                        return ("Error! You placed it too close to another one.");
                    }
                }

                // Proceed with horizontal ship creation
                for (int i = startColumn; i < (endColumn+1); i++) {
                    map.put(startRow + String.valueOf(i), "O");
                }


            } else { // Vertical ship
                // Check length
                if (endRow - startRow + 1 != SHIP_LENGTH) {
                    return("Error: wrong length of the " + shipType + "!");
                }

                // Check for collisions with other ships
                Set<String> collisionZone = new TreeSet<>();

                // Top adjacent cell
                if (startRow != 'A') {
                    collisionZone.add((char)(startRow - 1) + String.valueOf(startColumn));
                }

                // Bottom adjacent cell
                if (startRow != 'J') {
                    collisionZone.add((char)(endRow + 1) + String.valueOf(startColumn));
                }

                // Left adjacent row
                if (startColumn > 1) {
                    for (int i = startRow; i < (endRow+1); i++) {
                        collisionZone.add((char)(i) + String.valueOf(startColumn-1));
                    }
                }

                // Right adjacent row
                if (startColumn > 1) {
                    for (int i = startRow; i < (endRow+1); i++) {
                        collisionZone.add((char)(i) + String.valueOf(startColumn+1));
                    }
                }

                // Check collision zone
                for (String cell : map.keySet()) {
                    if (map.get(cell).equals("O") && collisionZone.contains(cell)) {
                        return ("Error! You placed it too close to another one.");
                    }
                }

                // Proceed with vertical ship creation
                for (int i = startRow; i < (endRow+1); i++) {
                    map.put((char)i + String.valueOf(startColumn), "O");
                }
            }

            return "success";
        }

        /***
         * Returns a string representation of the BattleshipMap.
         * @return a string representation of the BattleshipMap.
         */
        @Override
        public String toString() {
            StringBuilder displayedMap = new StringBuilder(" ");

            for (int i = 1; i <= 10; i++) {
                displayedMap.append(" " + i);
            }

            for (int i = 65; i <= 74; i++) {
                displayedMap.append("\n" + (char)(i));
                for (int j = 1; j <= 10; j++) {
                    displayedMap.append(" " + map.get((char)(i) + String.valueOf(j)));
                }
            }

            displayedMap.append("\n");
            return displayedMap.toString();
        }
    }

}
