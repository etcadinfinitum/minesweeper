import java.util.Observable;


public class MinesweeperModel {/// implements Observable {
    
    private GameSquare[][] squareTraits;
    private boolean activeGame;
    
    public MinesweeperModel(int boardHeight, int boardWidth, GameDifficulty diff) {
        activeGame = true;
        squareTraits = new GameSquare[boardWidth][boardHeight];
        for (int row = 0; row < squareTraits.length; row++) {
            for (int col = 0; col < squareTraits[row].length; col++) {
                squareTraits[row][col] = new GameSquare();
            }
        }
        determineSegmentMines(diff);
        determineSegmentValue();
    }
    
    private void determineSegmentMines(GameDifficulty diff) {
        int mineQty = squareTraits.length * squareTraits[0].length;
        switch (diff) {
            case EASY: mineQty = (int)Math.floor(mineQty / 10);
                break;
            case MEDIUM: mineQty = (int)Math.floor(mineQty / 8);
                break;
            case HARD: mineQty = (int)Math.floor (mineQty / 6);
                break;
        }
        
        for (int i = 1; i <= mineQty; i++) {
            boolean isValid = false;
            while (!isValid) {
                int mineX = (int)Math.floor(Math.random() * squareTraits.length);
                int mineY = (int)Math.floor(Math.random() * squareTraits[0].length);
                if (!squareTraits[mineX][mineY].getBomb()) {
                    squareTraits[mineX][mineY].setBomb(true);
                    isValid = true;
                }
            }
        }
        
    }
  
    private void determineSegmentValue() {
        for (int row = 0; row < squareTraits.length; row++) {
            for (int col = 0; col < squareTraits[row].length; col++) {
                if (squareTraits[row][col].getBomb()) {
                    /*
                    It would be wise to set up recursive method for incrementing
                    surrounding squares?
                    */
                    
                    int rowMin;
                    int rowMax;
                    int colMin;
                    int colMax;
                    if (row == 0) {
                        rowMin = 0;
                    } else {
                        rowMin = -1;
                    }
                    if (row == squareTraits.length - 1) {
                        rowMax = 0;
                    } else {
                        rowMax = 1;
                    }
                    if (col == 0) {
                        colMin = 0;
                    } else {
                        colMin = -1;
                    }
                    if (col == squareTraits[row].length - 1) {
                        colMax = 0;
                    } else {
                        colMax = 1;
                    }
                    
                    for (int checkRow = (row + rowMin); checkRow <= (row + rowMax); checkRow++) {
                        for (int checkCol = (col + colMin); checkCol <= (col + colMax); checkCol++) {
                            squareTraits[checkRow][checkCol].incrementHint();
                        }
                    }
                    
                }
            }
        }
    }
  
  
    /*
    External accessors and manipulators below
    */
    
    public void changeBoardState(int buttonX, int buttonY) {
        if (squareTraits[buttonX][buttonY].getBomb()) {
            activeGame = false;
            for (int row = 0; row < squareTraits.length; row++) {
                for (int col = 0; col < squareTraits[row].length; col++) {
                    if (squareTraits[row][col].getBomb()) {
                        squareTraits[row][col].clicked();
                    }
                }
            }
        } else {
            uncoverSquares(buttonX, buttonY);
        }
    }
    
    private void uncoverSquares(int currX, int currY) {
        // System.out.println("currX is " + currX + "; currY is " + currY);
        if (currX < 0 || currY < 0 || currX >= squareTraits.length || currY >= squareTraits[0].length || squareTraits[currX][currY].getClicked()) {
            
        } else if (squareTraits[currX][currY].getHint() != 0) {
            squareTraits[currX][currY].clicked();
        } else {
            squareTraits[currX][currY].clicked();
            uncoverSquares(currX + 1, currY);
            uncoverSquares(currX, currY + 1);
            uncoverSquares(currX + 1, currY + 1);
            uncoverSquares(currX - 1, currY);
            uncoverSquares(currX, currY - 1);
            uncoverSquares(currX - 1, currY - 1);
            uncoverSquares(currX + 1, currY - 1);
            uncoverSquares(currX + 1, currY + 1);
        }
    }
    
    public GameSquare[][] getBoardState() {
        return squareTraits;
    }
  
    public GameSquare getSquareState(int row, int col) {
        return squareTraits[row][col];
    }
    
    public boolean getGameStatus() {
        return activeGame;
    }
    
}