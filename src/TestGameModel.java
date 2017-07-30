import java.util.Scanner;
import java.util.InputMismatchException;

public class TestGameModel {

    public static void main(String[] args) {
        MinesweeperModel myModel = new MinesweeperModel(30, 30, GameDifficulty.MEDIUM, null);
        GameSquare[][] myModelState = myModel.getBoardState();
        Scanner console = new Scanner(System.in);
        printBoard(myModelState);
        while (myModel.getGameStatus()) {
            System.out.println("Choose your next square to click");
            try {
                int newX = console.nextInt();
                int newY = console.nextInt();
                myModel.changeBoardState(newX, newY);
                myModelState = myModel.getBoardState();
                System.out.println("\n");
                printBoard(myModelState);
            } catch (InputMismatchException e) {
                System.out.println("Oops");
            }
        }
    }
    
    private static void printBoard(GameSquare[][] modelState) {
        for (int row = 0; row < modelState.length; row++) {
            for (int col = 0; col < modelState[row].length; col++) {
                String clickedFlag = "";
                if (modelState[row][col].getClicked()) {
                    clickedFlag += "*";
                } else {
                    clickedFlag += " ";
                }
                if (modelState[row][col].getBomb()) {
                    System.out.print("M" + clickedFlag + " ");
                } else if (modelState[row][col].getHint() > 0) {
                    System.out.print(modelState[row][col].getHint() + clickedFlag + " ");
                } else {
                    System.out.print("-" + clickedFlag + " ");
                }
            }
            System.out.println();
        } 
    }

}