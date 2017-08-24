import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.border.EtchedBorder;

public class MinesweeperView extends JFrame {

    private MinesweeperController controls;
    private JButton[][] boardButtons;
    private JPanel[][] boardPanels;
    private int squareSize = 25;
    private JLayeredPane gamePanel;
    private JPanel menuPanel;
    private int xOffset = 0;
    private int yOffset = 0;
    private Border defaultBorder;
    private Color shade1 = Color.BLUE;
    private Color shade2 = new Color(1, 121, 1);
    private Color shade3 = Color.RED;
    private Color shade4 = new Color(1, 0, 128);
    private Color shade5 = new Color(129, 1, 2); 
    private Color shade6 = new Color(0, 128, 129);
    private Color shade7 = Color.BLACK;
    private Color shade8 = Color.LIGHT_GRAY;
    private ArrayList<ArrayList<ScoreKeeper>> scores;
    private long startTime;
    private JTextField name;
    private GameDifficulty currentGameLevel;
    private boolean highScore = false;
    private GameListener buttonListener;
    private int scoreQty = 10;
    
    public MinesweeperView(MinesweeperController controls) {
        this.controls = controls;
        setSize(640,740);
        setLocation(100, 0);
        menuPanel = new JPanel();
        menuPanel.setLayout(null);
        menuPanel.setBounds(0, 0, 640, 100);
        getContentPane().add(menuPanel);
        yOffset = menuPanel.getHeight();
        gamePanel = new JLayeredPane();
        gamePanel.setLayout(null);
        gamePanel.setBounds(0, 100, 640, 640);
        defaultBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1);
        getContentPane().add(gamePanel);
        setResizable(false);
        generateMenu();
        scores = new ArrayList<ArrayList<ScoreKeeper>>(3);
        for (int i = 0; i < 3; i++) {
            scores.add(new ArrayList<ScoreKeeper>(6));
        }
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        buttonListener = new GameListener();
    }
    
    public void generateMenu() {
        gamePanel.removeAll();
        menuPanel.removeAll();
        repaint();
        JButton newGame = new JButton("New Game");
        newGame.setBounds((menuPanel.getWidth() / 2) - 175, 25, 150, 50);
        newGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pickSettings();
            }
        });
        menuPanel.add(newGame);
        JButton highScores = new JButton("High Scores");
        highScores.setBounds((menuPanel.getWidth() / 2) + 25, 25, 150, 50);
        highScores.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayScores();
            }
        });
        menuPanel.add(highScores);
        repaint();
    }
    
    public void drawGame(GameSquare[][] gameState) {
        menuPanel.removeAll();
        gamePanel.removeAll();
        gamePanel.setBorder(defaultBorder);
        
        xOffset = ((600 - (gameState.length * squareSize)) / 2) + 20;
        yOffset = ((600 - (gameState[0].length * squareSize)) / 2) + 100;
        
        boardButtons = new JButton[gameState.length][gameState[0].length];
        boardPanels = new JPanel[gameState.length][gameState[0].length];
        
        for (int row = 0; row < gameState.length; row++) {
            for (int col = 0; col < gameState[row].length; col++) {
                boardPanels[row][col] = new JPanel();
                gamePanel.add(boardPanels[row][col], new Integer(1));
                boardPanels[row][col].setBounds((squareSize * col) + xOffset, 
                        (squareSize * row) + yOffset, squareSize, squareSize);
                boardPanels[row][col].setBorder(defaultBorder);
                JLabel thisLabel = new JLabel();
                boardPanels[row][col].add(thisLabel);
                thisLabel.setBounds((squareSize * col) + xOffset, 
                        (squareSize * row) + yOffset, squareSize, squareSize);
                if (gameState[row][col].getBomb()) {
                    thisLabel.setText("M");
                } else if (gameState[row][col].getHint() > 0) {
                    thisLabel.setText(Integer.toString(gameState[row][col].getHint()));
                    Color numColor = null;
                    switch (gameState[row][col].getHint()) {
                        case 1 : numColor = shade1;
                        break;
                        case 2 : numColor = shade2;
                        break;
                        case 3 : numColor = shade3;
                        break;
                        case 4 : numColor = shade4;
                        break;
                        case 5 : numColor = shade5;
                        break;
                        case 6 : numColor = shade6;
                        break;
                        case 7 : numColor = shade7;
                        break;
                        case 8 : numColor = shade8;
                        break;
                        default : numColor = shade8;
                        break;
                    }
                    thisLabel.setForeground(numColor);
                }
                boardButtons[row][col] = new JButton();
                gamePanel.add(boardButtons[row][col], new Integer(2));
                boardButtons[row][col].setBounds((squareSize * col) + xOffset, 
                        (squareSize * row) + yOffset, squareSize, squareSize);
                boardButtons[row][col].putClientProperty("col", col);
                boardButtons[row][col].putClientProperty("row", row);
                boardButtons[row][col].addActionListener(buttonListener);
            }
        }
        repaint();
        startTime = System.currentTimeMillis();
    }
    
    public void updateGame(GameSquare[][] gameState) {
        for (int row = 0; row < gameState.length; row++) {
            for (int col = 0; col < gameState[row].length; col++) {
                if (gameState[row][col].getClicked()) {
                    gamePanel.remove(boardButtons[row][col]);
                    gamePanel.repaint();
                }
            }
        }
    }

    public void updateGame(GameSquare[][] gameState, boolean didUserWin) {
        int elapsedTime = Math.round((System.currentTimeMillis() - startTime) / 1000);
        updateGame(gameState);
        for (int row = 0; row < boardButtons.length; row++) {
            for (int col = 0; col < boardButtons[row].length; col++) {
                ActionListener[] listen = boardButtons[row][col].getActionListeners();
                for (ActionListener l : listen) {
                    boardButtons[row][col].removeActionListener(l);
                }
            }
        }
        gamePanel.repaint();
        
        JFrame gameOverPopup = new JFrame();
        gameOverPopup.setResizable(false);
        gameOverPopup.setLayout(null);
        gameOverPopup.setBounds(50, 300, 200, 230);
        JLabel info = new JLabel();
        info.setBounds(20, 20, 160, 80);
        gameOverPopup.getContentPane().add(info);
        JButton returnToMenu = new JButton();
        returnToMenu.setBounds(50, 125, 100, 50);
        gameOverPopup.getContentPane().add(returnToMenu);

        if (didUserWin) {
            int scoreIndex = getScoreArray();
            if (((ArrayList<ScoreKeeper>)scores.get(scoreIndex)).size() < 5
                 || ((ArrayList<ScoreKeeper>)scores.get(scoreIndex)).get(4).getTime() > elapsedTime) {
                highScore = true;
            }
            gameOverPopup.setSize(200, 300);
            returnToMenu.setLocation(50, 225);
            name = new JTextField("Anonymous");
            name.setBounds(25, 145, 150, 30);
            info.setSize(160,95);
            if (highScore) {
                gameOverPopup.setSize(200,330);
                gameOverPopup.setTitle("New High Score!");
                gameOverPopup.add(name);
                info.setText("<html>You Won! <br>Your score is: " + elapsedTime + 
                    " sec<br>This is a new high score! Enter your name below to save your score, then "
                    + "click OK to return to the game menu.");                
                
            } else {
                gameOverPopup.setTitle("Winner!");
                info.setText("<html>You Won! <br>Your score is: " + elapsedTime + 
                    " sec<br>Click OK to return to the game menu.");                
            }

            returnToMenu.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    ScoreKeeper newHighScore = new ScoreKeeper(name.getText(), elapsedTime, currentGameLevel);
                    addHighScore(newHighScore);
                    gameOverPopup.dispose();
                    generateMenu();
                }
            });
        } else {
            boardPanels[controls.getLastX()][controls.getLastY()].setBackground(Color.red);
            gameOverPopup.setTitle("Sorry!");
            info.setText("<html>You Lost! <br>Your time was: " + elapsedTime + 
                    " sec<br>Click OK to return to the game menu.");
            returnToMenu.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    generateMenu();
                    gameOverPopup.dispose();
                }
            });
        }
        returnToMenu.setText("OK");
        gameOverPopup.setVisible(true);
        gameOverPopup.repaint();
        startTime = 0;
        highScore = false;
    }
    
    private void pickSettings() {
        int spacing = (600 - 300) / 4;
        JButton easy = new JButton("Easy");
        easy.setBounds(spacing + 20, 150, 100, 50);
        easy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gamePanel.removeAll();
                controls.startNewGame(GameDifficulty.EASY, 15, 15);
                currentGameLevel = GameDifficulty.EASY;
            }
        });
        gamePanel.add(easy);
        
        JButton medium = new JButton("Medium");
        medium.setBounds(spacing * 2 + 100 + 20, 150, 100, 50);
        medium.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gamePanel.removeAll();
                controls.startNewGame(GameDifficulty.MEDIUM, 20, 20);
                currentGameLevel = GameDifficulty.MEDIUM;
            }
        });
        gamePanel.add(medium);
        
        JButton hard = new JButton("Hard");
        hard.setBounds(spacing * 3 + 200 + 20, 150, 100, 50);
        hard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gamePanel.removeAll();
                controls.startNewGame(GameDifficulty.HARD, 24, 24);
                currentGameLevel = GameDifficulty.HARD;
            }
        });
        gamePanel.add(hard);
/*
        JButton dummy = new JButton(" ledum dum");
        dummy.setBounds(spacing * 3 + 200, 300, 100, 50);
        dummy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gamePanel.removeAll();
                controls.startNewGame(GameDifficulty.DUMMY, 24, 24);
                currentGameLevel = GameDifficulty.DUMMY;
            }
        });
        gamePanel.add(dummy);
*/
    }

    public void displayScores() {
        Border leaderBoardBorder = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        gamePanel.removeAll();
        menuPanel.removeAll();
        
        JLabel easyScores = new JLabel();
        easyScores.setBounds(25, 125, 167, 425);
        Border easyBorder = BorderFactory.createTitledBorder(leaderBoardBorder, "Easy");
        easyScores.setBorder(easyBorder);
        String easyText = "";
        if (((ArrayList<ScoreKeeper>)scores.get(0)).isEmpty()) {
            easyText += "No leaders yet";
        } else {
            for (int i = 0; i < ((ArrayList<ScoreKeeper>)scores.get(0)).size(); i++) {
                easyText += Integer.toString(i + 1);
                
                easyText += ". " + ((ArrayList<ScoreKeeper>)scores.get(0)).get(i).toString();
                easyText += "<br>";
            }
        }
        easyScores.setText("<html>" + easyText);
        gamePanel.add(easyScores);
        
        JLabel medScores = new JLabel();
        medScores.setBounds(217, 125, 167, 425);
        Border medBorder = BorderFactory.createTitledBorder(leaderBoardBorder, "Medium");
        medScores.setBorder(medBorder);        
        String medText = "";
        if (((ArrayList<ScoreKeeper>)scores.get(1)).isEmpty()) {
            medText += "No leaders yet";
        } else {
            for (int i = 0; i < ((ArrayList<ScoreKeeper>)scores.get(1)).size(); i++) {
                //if ()
                medText += Integer.toString(i + 1);
                medText += ". " + ((ArrayList<ScoreKeeper>)scores.get(1)).get(i).toString();
                medText += "<br>";
            }
        }
        medScores.setText("<html>" + medText);
        gamePanel.add(medScores);

        JLabel hardScores = new JLabel();
        hardScores.setBounds(409, 125, 167, 425);
        Border hardBorder = BorderFactory.createTitledBorder(leaderBoardBorder, "Hard");
        hardScores.setBorder(hardBorder);        
        String hardText = "";
        if (((ArrayList<ScoreKeeper>)scores.get(2)).isEmpty()) {
            hardText += "No leaders yet";
        } else {
            for (int i = 0; i < ((ArrayList<ScoreKeeper>)scores.get(2)).size(); i++) {
                hardText += Integer.toString(i + 1);
                hardText += ". " + ((ArrayList<ScoreKeeper>)scores.get(2)).get(i).toString();
                hardText += "<br>";
            }
        }
        hardScores.setText("<html>" + hardText);
        gamePanel.add(hardScores);
        
        JButton backToMenu = new JButton("Return to Menu");
        backToMenu.setBounds(200, 575, 200, 100);
        backToMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                generateMenu();
            }
        });
        gamePanel.add(backToMenu);
    }
    
    private boolean addHighScore(ScoreKeeper newHighScore) {
        boolean isAdded = false;
        int scoreIndex = getScoreArray();
        ((ArrayList<ScoreKeeper>)scores.get(scoreIndex)).add(newHighScore);
        Collections.sort(((ArrayList<ScoreKeeper>)scores.get(scoreIndex)));
        return true;
    }
    
    private int getScoreArray() {
        int index = -1;
        switch (currentGameLevel) {
            case EASY : index = 0;
                break;
            case DUMMY : index = 0;
                break;
            case MEDIUM : index = 1;
                break;
            case HARD : index = 2;
                break;
        }
        return index;
    }
    
    public class GameListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton thisButton = (JButton) e.getSource();
            controls.userChoice((int)thisButton.getClientProperty("row"), 
                    (int)thisButton.getClientProperty("col"));            
        }
    }
    
}