public class InitializeGame {
    
    public static void main(String[] args) {
        private MinesweeperController myController = new MinesweeperController();
        private MinesweeperView myGameWindow = new MinesweeperView();
        myGameWindow.addObserver(myController);
      
        
    }

}