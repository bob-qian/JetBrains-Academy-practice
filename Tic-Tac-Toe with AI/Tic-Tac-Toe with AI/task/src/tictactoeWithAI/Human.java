package tictactoeWithAI;

public class Human implements TTTPlayer {
    String symbol; // X or O

    public Human(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Takes user input to make a human move
     * @param game a tic-tac-toe game
     */
    public void makeMove(Game game) {
        game.makeMove(this.symbol);
    }

    public String getSymbol() {
        return symbol;
    }
}
