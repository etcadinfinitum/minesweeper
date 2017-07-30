public class InitializeGame {
    
    public static void main(String[] args) {
        // 
        MinesweeperController controls = new MinesweeperController();
        MinesweeperView view = new MinesweeperView((MinesweeperController) controls);
        controls.knowView(view);
        view.setVisible(true);
    
    }

}