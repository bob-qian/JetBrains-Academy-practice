package tictactoeWithAI;

public interface TTTPlayer {
    // Tic-tac-toe player must be able to make a move
    public void makeMove(Game game);

    // Tic-tac-toe player must have a retrievable symbol ("X" or "O")
    public String getSymbol();
}
