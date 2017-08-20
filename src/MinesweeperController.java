import java.util.Observer;
import java.util.Observable;

public class MinesweeperController implements Observer {

    private MinesweeperModel model;
    private MinesweeperView view;
    private GameSquare[][] boardState;
    private boolean currentGame;

    public void MinesweeperController() {
        model = null;
        currentGame = false;
    }
    
    public void startNewGame() {
        // defaults!!!
        System.out.println("le startNewGame - actionevent was transmitted correctly");
        int boardHeight = 20;
        int boardWidth = 20;
        GameDifficulty diff = GameDifficulty.HARD;
        model = new MinesweeperModel((Observer) this, boardHeight, boardWidth, diff);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        /*
        // System.out.println("calling runUpdates() in Controller class");
        model = (MinesweeperModel) arg;
        // System.out.println(model.toString());
        if (model.getGameStatus()) {
            view.drawGame(model.getBoardState());
            //System.out.println("you lost!");
        } else if (model.didUserWin()) {
            System.out.println("you won!");
            view.drawWin(model.getBoardState());
        } else {
            System.out.println("you lost!");
            view.drawLoss(model.getBoardState());
        }
        */
        model = (MinesweeperModel) arg;
        if (currentGame) {
            view.updateGame(model.getBoardState());
        } else {
            view.drawGame(model.getBoardState());
            currentGame = true;
        }
    }
    
    public void userChoice(int row, int col) {
        model.changeBoardState(row, col);
    }
    
    public void knowView(MinesweeperView view) {
        this.view = view;
    }

}