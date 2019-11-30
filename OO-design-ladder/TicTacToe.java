class AlreadyTakenException extends Exception {
	public AlreadyTakenException() {
		super("Already Taken");
	}
}

class GameEndException extends Exception {
	public GameEndException() {
		super("Game Ended");
	}
}

class TicTacToe {
    private static final char playerX = 'x';
    private static final char playerO = 'o';

    private char[][] board;
    private char currentPlayer;
    private boolean gameEnd;

    /** Initialize your data structure here. */
    public TicTacToe() {
        this.board = new char[3][3];
        this.currentPlayer = TicTacToe.playerX;
        this.gameEnd = false;
    }
    
    public boolean move(int row, int col) throws AlreadyTakenException, GameEndException {
        if (gameEnd)
            throw new GameEndException();
        if (this.board[row][col] == 0)
            throw new AlreadyTakenException();

        this.board[row][col] = this.currentPlayer;
        if (this.currentPlayerWins()) {
            this.gameEnd = true;
            System.out.println(this.currentPlayer + " player wins!");
        } else if (this.boardFull()) {
            System.out.println("It is a tie!");
        }
        this.switchPlayer();
        return this.gameEnd;
    }

    private boolean currentPlayerWins() {
        boolean diff = false;
        for (int i = 0; i < this.board.length; i++) {
            diff = false;
            for (int j = 0; j < this.board[0].length; j++) {
                if (this.board[i][j] != this.currentPlayer) {
                    diff = true;
                    break;
                }
            }
            if (!diff)
                return true;
        }

        for (int j = 0; j < this.board[0].length; j++) {
            diff = false;
            for (int i = 0; i < this.board.length; i++) {
                if (this.board[i][j] != this.currentPlayer) {
                    diff = true;
                    break;
                }
            }
            if (!diff)
                return true;
        }

        diff = false;
        for (int i = 0; i < this.board.length; i++) {
            if (this.board[i][i] != this.currentPlayer) {
                diff = true;
                break;
            }
        }
        if (!diff)
            return true;
        
        diff = false;
        for (int i = 0; i < this.board.length; i++) {
            if (this.board[i][this.board.length-1-i] != this.currentPlayer) {
                diff = true;
                break;
            }
        }
        if (!diff)
            return true;
        
        return false;
    }

    private boolean boardFull() {
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[0].length; j++) {
                if (this.board[i][j] == 0)
                    return false;
            }
        }
        return true;
    }

    private void switchPlayer() {
        if (this.currentPlayer == TicTacToe.playerX)
            this.currentPlayer = TicTacToe.playerO;
        else
            this.currentPlayer = TicTacToe.playerX;
    }
}