package tictactoeWithAI;

public class AI implements TTTPlayer {
    String symbol; // X or O
    String difficulty; // easy, medium, hard

    public AI(String symbol, String difficulty) {
        this.symbol = symbol;
        this.difficulty = difficulty;
    }

    /**
     * Makes an AI move
     * @param game a tic-tac-toe game
     */
    public void makeMove(Game game) {
        // Make an easy move if the AI difficulty is set to "easy"
        if (this.difficulty.equals("easy")) {
            makeMoveEasy(game);
        }
    }

    // Makes an "easy" move (randomly generated move)
    private void makeMoveEasy(Game game) {
        System.out.println("Making move level \"easy\"");

        boolean added = false;

        do {
            int randomRow = (int) (Math.random() * 3) + 1;
            int randomCol = (int) (Math.random() * 3) + 1;
            added = game.addCoordinates(randomRow, randomCol, this.symbol);
        } while (!added);

        System.out.println(game);
    }

    public String getSymbol() {
        return symbol;
    }
}
