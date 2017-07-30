import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.lang.Math;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class MinesweeperView extends JFrame {

    private JPanel gamePanel;
    private JButton newGame;
    private JButton highScores;
    private GameSquare[][] gameState;
    private MinesweeperController controls;
    private int squareSize = 25;
    
    public MinesweeperView(MinesweeperController controls) {
        this.controls = controls;
        setSize(600,600);
        setLocation(100, 100);
        
        gamePanel = new JPanel();
        getContentPane().add(gamePanel);
        
        generateMenu();
    }
    
    public boolean generateMenu() {
        gamePanel.removeAll();
        
        newGame = new JButton("New Game");
        newGame.setBounds((int)Math.floor((getContentPane().WIDTH - 100)/2), 75, 100, 50);
        newGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controls.startNewGame();
            }
        });
        gamePanel.add(newGame); 
        
        highScores = new JButton("High Scores");
        highScores.setBounds((getContentPane().WIDTH - 100)/2, 150, 100, 50);
        highScores.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayScores();
            }
        });
        gamePanel.add(highScores);
        
        return true;
    }
    
    public void displayScores() {
        
    }    
    
    public void drawWin(GameSquare[][] gameState) {
        gamePanel.removeAll();
        for (int row = 0; row < gameState.length; row++) {
            for (int col = 0; col < gameState[row].length; col++) {
                if (gameState[row][col].getClicked()) {
                    if (gameState[row][col].getBomb()) {
                        JLabel thisLabel = new JLabel("M");
                        thisLabel.setBounds(squareSize * col, squareSize * row, squareSize, squareSize);
                        gamePanel.add(thisLabel);
                    } else {
                        if (gameState[row][col].getHint() > 0) {
                            JLabel thisLabel = new JLabel(Integer.toString(gameState[row][col].getHint()));
                            thisLabel.setBounds(squareSize * col, squareSize * row, squareSize, squareSize);
                            gamePanel.add(thisLabel);
                        }
                    }

                } else {
                    JButton thisButton = new JButton();
                    thisButton.setBounds(squareSize * col, squareSize * row, squareSize, squareSize);
                    thisButton.putClientProperty("column", col);
                    thisButton.putClientProperty("row", row);
                    thisButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            JButton thisButton = (JButton) e.getSource();
                            controls.userChoice((int)thisButton.getClientProperty("row"), (int)thisButton.getClientProperty("column"));
                        }
                    });
                    gamePanel.add(thisButton);
                }
            }
        }
        repaint();
    }
    
    public void drawLoss(GameSquare[][] gameState) {
        gamePanel.removeAll();
        for (int row = 0; row < gameState.length; row++) {
            for (int col = 0; col < gameState[row].length; col++) {
                if (gameState[row][col].getClicked()) {
                    if (gameState[row][col].getBomb()) {
                        JLabel thisLabel = new JLabel("M");
                        thisLabel.setBounds(squareSize * col, squareSize * row, squareSize, squareSize);
                        gamePanel.add(thisLabel);
                    } else {
                        JLabel thisLabel = new JLabel(Integer.toString(gameState[row][col].getHint()));
                        thisLabel.setBounds(squareSize * col, squareSize * row, squareSize, squareSize);
                        gamePanel.add(thisLabel);
                    }

                } else {
                    JButton thisButton = new JButton();
                    thisButton.setBounds(squareSize * col, squareSize * row, squareSize, squareSize);
                    thisButton.putClientProperty("column", col);
                    thisButton.putClientProperty("row", row);
                    thisButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            JButton thisButton = (JButton) e.getSource();
                            controls.userChoice((int)thisButton.getClientProperty("row"), (int)thisButton.getClientProperty("column"));
                        }
                    });
                    gamePanel.add(thisButton);
                }
            }
        }
        repaint();
    }
    
    public void drawGame(GameSquare[][] gameState) {
        gamePanel.removeAll();
        for (int row = 0; row < gameState.length; row++) {
            for (int col = 0; col < gameState[row].length; col++) {
                if (gameState[row][col].getClicked()) {
                    if (gameState[row][col].getBomb()) {
                        JLabel thisLabel = new JLabel("M");
                        thisLabel.setBounds(squareSize * col, squareSize * row, squareSize, squareSize);
                        gamePanel.add(thisLabel);
                    } else {
                        JLabel thisLabel = new JLabel(Integer.toString(gameState[row][col].getHint()));
                        thisLabel.setBounds(squareSize * col, squareSize * row, squareSize, squareSize);
                        gamePanel.add(thisLabel);
                    }

                } else {
                    JButton thisButton = new JButton();
                    thisButton.setBounds(squareSize * col, squareSize * row, squareSize, squareSize);
                    thisButton.putClientProperty("column", col);
                    thisButton.putClientProperty("row", row);
                    thisButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            JButton thisButton = (JButton) e.getSource();
                            controls.userChoice((int)thisButton.getClientProperty("row"), (int)thisButton.getClientProperty("column"));
                        }
                    });
                    gamePanel.add(thisButton);
                }
            }
        }
        repaint();
    }

    
    
}