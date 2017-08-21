import java.util.Observer;
import java.util.Observable;

public class MinesweeperController implements Observer {

    private MinesweeperModel model;
    private MinesweeperView view;
    private GameSquare[][] boardState;
    private boolean currentGame;
    private int lastX;
    private int lastY;

    public void MinesweeperController() {
        model = null;
        currentGame = false;
    }
    
    public void startNewGame(GameDifficulty diff, int boardHeight, int boardWidth) {
        int newBoardHeight = boardHeight;
        int newBoardWidth = boardWidth;
        model = new MinesweeperModel((Observer) this, newBoardHeight, newBoardWidth, diff);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        model = (MinesweeperModel) arg;
        if (currentGame) {
            if (model.getGameStatus()) {
                view.updateGame(model.getBoardState());
            } else {
                view.updateGame(model.getBoardState(), model.didUserWin());
                currentGame = false;
                model = null;
                boardState = null;
            }
        } else {
            view.drawGame(model.getBoardState());
            currentGame = true;
        }
    }
    
    public void userChoice(int row, int col) {
        lastX = row;
        lastY = col;
        model.changeBoardState(row, col);
    }
    
    public void knowView(MinesweeperView view) {
        this.view = view;
    }
    
    public int getLastX() {
        return lastX;
    }
    
    public int getLastY() {
        return lastY;
    }

}