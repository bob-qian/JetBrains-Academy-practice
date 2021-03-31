package battleship;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);

        BattleshipMap userMap = new BattleshipMap();
        System.out.println(userMap);

        // Ask for an input for each type of ship
        String processStatus = "";
        for (String shipType : shipsAndLengths.keySet()) {
            System.out.println("Enter the coordinates of the " +
                    shipType + " (" + shipsAndLengths.get(shipType) + " cells):\n");
            do {
                String[] userCoordinates = scnr.nextLine().split(" ");
                processStatus = userMap.addShip(userCoordinates, shipType);
                if (!processStatus.equals("success")) {
                    System.out.println("\n" + processStatus + " Try again:\n");
                }
            } while (!processStatus.equals("success"));
            System.out.println("\n" + userMap);
        }

        // Clear the process status for next loop, taking shots
        processStatus = "";
        System.out.println("The game starts!\n");
        BattleshipMap opponentShootingMap = new BattleshipMap();
        System.out.println(opponentShootingMap);

        // Loop to take shots
        System.out.println("Take a shot!\n");
        do {
            String userShot = scnr.nextLine();
            processStatus = userMap.takeShot(userShot, opponentShootingMap);
            if (!processStatus.equals("success")) {
                System.out.println("\n" + processStatus + " Try again:\n");
            }
        } while (!processStatus.equals("success"));

        System.out.println("\n" + userMap);
    }

    private static LinkedHashMap<String, Integer> shipsAndLengths = new LinkedHashMap<>();

    private static class BattleshipMap {
        static final String[] rows = new String[]{"A", "B", "C", "D", "E",
                "F", "G", "H", "I", "J" };
        static final String[] columns = new String[]{"1", "2", "3", "4", "5",
                "6", "7", "8", "9", "10" };
        private HashMap<String, String> map;

        public BattleshipMap() {
            shipsAndLengths.put("Aircraft Carrier", 5);
            shipsAndLengths.put("Battleship", 4);
            shipsAndLengths.put("Submarine", 3);
            shipsAndLengths.put("Cruiser", 3);
            shipsAndLengths.put("Destroyer", 2);

            map = new HashMap<>();
            // Populate the battleship map
            for (int i = 0; i < rows.length; i++) {
                for (int j = 0; j < columns.length; j++) {
                    map.put(rows[i]+columns[j], "~");
                }
            }
        }

        public HashMap<String, String> getMap() {
            return map;
        }

        public String takeShot(String cell, BattleshipMap opponentShootingMap) {
            String cellBeingShot = map.get(cell);

            if (cellBeingShot == null) {
                return("Error! You entered the wrong coordinates!");
            }

            if (cellBeingShot.equals("O")) {
                map.put(cell, "X");
                opponentShootingMap.getMap().put(cell, "X");

                System.out.println("\n" + opponentShootingMap);
                System.out.println("You hit a ship!");
            } else {
                map.put(cell, "M");
                opponentShootingMap.getMap().put(cell, "M");

                System.out.println("\n" + opponentShootingMap);
                System.out.println("You missed!");
            }

            return "success";
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

        @Override
        public String toString() {
            StringBuilder displayedMap = new StringBuilder(" ");

            for (String column : columns) {
                displayedMap.append(" " + column);
            }

            for (int i = 0; i < rows.length; i++) {
                displayedMap.append("\n" + rows[i]);
                for (int j = 0; j < columns.length; j++) {
                    // key = rows[i] + columns[j]
                    displayedMap.append(" " + map.get(rows[i] + columns[j]));
                }
            }

            displayedMap.append("\n");
            return displayedMap.toString();
        }
    }

}
