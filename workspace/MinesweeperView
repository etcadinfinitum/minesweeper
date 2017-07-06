import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.util.Math;
import java.util.ArrayList;
import java.awt.ActionListener;
import java.awt.ActionEvent;


public class MinesweeperView extends JPanel implements Observer {
    
    private JPanel gamePanel;
    private static boolean activeGame;
    private JButton newGame;
    private JButton highScores;
    private JPanel[][] gameBoard;
    
    public MinesweeperView() {
        setSize(600,600);
        setLocation(100, 100);
        setVisible(true);
        
        gamePanel = new JPanel();
        getContentPane().add(gamePanel);
        
        generateMenu();
    }
    
    public boolean generateMenu() {
        gamePanel.removeAll();
        
        newGame = new JButton("New Game");
        newGame.setBounds(Math.floor((getContentPane().WIDTH - 100)/2), 75, 100, 50);
        newGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                activeGame = true;
                notifyObservers();
            }
        });
        gamePanel.add(newGame); 
        
        highScores = new JButton("High Scores");
        highScores.setBounds((getContentPane().WIDTH - 100)/2, 150, 100, 50);
        highScores.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                notifyObservers();
            }
        });
        
    }
    

    public void generateBoard(int boardWidth, int boardHeight) {
        gameBoard = new JPanel[boardWidth][boardHeight];
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                gameBoard[row][col] = new JPanel();
                gameBoard[row][col].setBounds(row * 10, col * 10, 10, 10);
                gameBoard[row][col].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        notifyObservers();
                        
                    }
                });
            }
        }
        
    }    
    
    public void displayBoard() {
        gamePanel.removeAll();
        for (int row = 0; row < gameBoard.length; row++) {
            for (int col = 0; col < gameBoard[row].length; col++) {
                gamePanel.add(gameBoard[row][col]);
                
            }
        }
        this.repaint();
    }
    
    

  public void update(Observable o, Object arg) {
      displayBoard();
      
  }
    
    
    
    
    
    
    
}