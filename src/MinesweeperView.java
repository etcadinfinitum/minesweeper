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
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.border.EtchedBorder;

public class MinesweeperView extends JFrame {

    private GameSquare[][] gameState;
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
    private ArrayList scores;
    private long startTime;
    private JTextField name;
    private GameDifficulty currentGameLevel;
    private boolean highScore = false;
    
    public MinesweeperView(MinesweeperController controls) {
        this.controls = controls;
        setSize(600,700);
        setLocation(100, 0);
        menuPanel = new JPanel();
        menuPanel.setLayout(null);
        menuPanel.setBounds(0, 0, 600, 100);
        getContentPane().add(menuPanel);
        yOffset = menuPanel.getHeight();
        gamePanel = new JLayeredPane();
        gamePanel.setLayout(null);
        gamePanel.setBounds(0, 100, 600, 600);
        defaultBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1);
        getContentPane().add(gamePanel);
        setResizable(false);
        generateMenu();
        scores = new ArrayList(3);
        for (int i = 0; i < 3; i++) {
            scores.add(new ArrayList<ScoreKeeper>(6));
        }
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
    
    public void displayScores() {
        Border leaderBoardBorder = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        gamePanel.removeAll();
        
        JLabel easyScores = new JLabel();
        easyScores.setBounds(25, 125, 167, 425);
        Border easyBorder = BorderFactory.createTitledBorder(defaultBorder, "Easy");
        easyScores.setBorder(easyBorder);
        String easyText = "";
        if (((ArrayList<ScoreKeeper>)scores.get(0)).isEmpty()) {
            easyText += "No leaders yet";
        } else {
            for (int i = 0; i < 5; i++) {
                easyText += Integer.toString(i + 1);
                easyText += ". " + ((ArrayList<ScoreKeeper>)scores.get(0)).get(4 - i).toString();
                easyText += "\n";
            }
        }
        easyScores.setText(easyText);
        gamePanel.add(easyScores);
        
        JLabel medScores = new JLabel();
        medScores.setBounds(217, 125, 167, 425);
        Border medBorder = BorderFactory.createTitledBorder(defaultBorder, "Medium");
        medScores.setBorder(medBorder);        
        String medText = "";
        if (((ArrayList<ScoreKeeper>)scores.get(1)).isEmpty()) {
            medText += "No leaders yet";
        } else {
            for (int i = 0; i < 5; i++) {
                //if ()
                medText += Integer.toString(i + 1);
                medText += ". " + ((ArrayList<ScoreKeeper>)scores.get(1)).get(4 - i).toString();
                medText += "\n";
            }
        }
        medScores.setText(medText);
        gamePanel.add(medScores);

        JLabel hardScores = new JLabel();
        hardScores.setBounds(409, 125, 167, 425);
        Border hardBorder = BorderFactory.createTitledBorder(defaultBorder, "Medium");
        hardScores.setBorder(hardBorder);        
        String hardText = "";
        if (((ArrayList<ScoreKeeper>)scores.get(1)).isEmpty()) {
            hardText += "No leaders yet";
        } else {
            for (int i = 0; i < 5; i++) {
                hardText += Integer.toString(i + 1);
                hardText += ". " + ((ArrayList<ScoreKeeper>)scores.get(1)).get(4 - i).toString();
                hardText += "\n";
            }
        }
        hardScores.setText(hardText);
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

    public void drawGame(GameSquare[][] gameState) {
        menuPanel.removeAll();
        gamePanel.removeAll();
        gamePanel.setBorder(defaultBorder);
        
        xOffset = (600 - (gameState.length * squareSize)) / 2;
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
                boardButtons[row][col].putClientProperty("column", col);
                boardButtons[row][col].putClientProperty("row", row);
                boardButtons[row][col].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        JButton thisButton = (JButton) e.getSource();
                        controls.userChoice((int)thisButton.getClientProperty("row"), 
                                (int)thisButton.getClientProperty("column"));
                    }
                });
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
        
        // popup frame
        JFrame gameOverPopup = new JFrame();
        gameOverPopup.setResizable(false);
        gameOverPopup.setLayout(null);
        gameOverPopup.setBounds(100, 500, 200, 200);
        // popup label
        JLabel info = new JLabel();
        info.setBounds(20, 20, 160, 80);
        gameOverPopup.getContentPane().add(info);
        // popup button
        JButton returnToMenu = new JButton();
        returnToMenu.setBounds(50, 125, 100, 50);
        gameOverPopup.getContentPane().add(returnToMenu);

        if (didUserWin) {
            if (currentGameLevel == GameDifficulty.EASY) {
                if (((ArrayList<ScoreKeeper>)scores.get(0)).size() < 5
                     || ((ArrayList<ScoreKeeper>)scores.get(0)).get(4).getTime() > elapsedTime) {
                    highScore = true;
                }
            } else if (currentGameLevel == GameDifficulty.MEDIUM) {
                if (((ArrayList<ScoreKeeper>)scores.get(1)).size() < 5
                     || ((ArrayList<ScoreKeeper>)scores.get(1)).get(4).getTime() > elapsedTime) {
                    highScore = true;
                }
            } else {
                if (((ArrayList<ScoreKeeper>)scores.get(2)).size() < 5
                     || ((ArrayList<ScoreKeeper>)scores.get(2)).get(4).getTime() > elapsedTime) {
                    highScore = true;
                }
            }
            gameOverPopup.setSize(200, 300);
            returnToMenu.setLocation(50, 225);
            name = new JTextField("Anonymous");
            name.setBounds(25, 125, 150, 50);
            if (highScore) {
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
                    generateMenu();
                    gameOverPopup.dispose();
                    if (highScore) {
                        ScoreKeeper newHighScore = new ScoreKeeper(name.getText(), elapsedTime, currentGameLevel);
                        addHighScore(newHighScore);
                    }
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
        easy.setBounds(spacing, 150, 100, 50);
        easy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gamePanel.removeAll();
                controls.startNewGame(GameDifficulty.EASY, 15, 15);
                currentGameLevel = GameDifficulty.EASY;
            }
        });
        gamePanel.add(easy);
        
        JButton medium = new JButton("Medium");
        medium.setBounds(spacing * 2 + 100, 150, 100, 50);
        medium.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gamePanel.removeAll();
                controls.startNewGame(GameDifficulty.MEDIUM, 20, 20);
                currentGameLevel = GameDifficulty.MEDIUM;
            }
        });
        gamePanel.add(medium);
        
        JButton hard = new JButton("Hard");
        hard.setBounds(spacing * 3 + 200, 150, 100, 50);
        hard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gamePanel.removeAll();
                controls.startNewGame(GameDifficulty.HARD, 24, 24);
                currentGameLevel = GameDifficulty.HARD;
            }
        });
        gamePanel.add(hard);
    }
    
    public int getSquareSize() {
        return squareSize;
    }
    
    public Dimension getBoardPanel() {
        Dimension r = new Dimension(gamePanel.getWidth(), gamePanel.getHeight());
        return r;
    }
    
    
    private void addHighScore(ScoreKeeper newHighScore) {
        boolean isAdded = false;
        if (currentGameLevel == GameDifficulty.EASY) {
            for (int i = 0; i < ((ArrayList<ScoreKeeper>)scores.get(0)).size() - 1; i++) {
                while (!isAdded) {
                    if (((ArrayList<ScoreKeeper>)scores.get(0)).get(i).getTime() > newHighScore.getTime() &&
                            ((ArrayList<ScoreKeeper>)scores.get(0)).get(i + 1).getTime() <= newHighScore.getTime()) {
                        ((ArrayList<ScoreKeeper>)scores.get(0)).add(i + 1, newHighScore);
                        ((ArrayList<ScoreKeeper>)scores.get(0)).remove(5);
                        isAdded = true;
                    }
                }
            }
            if (!isAdded) {
                ((ArrayList<ScoreKeeper>)scores.get(0)).add(4, newHighScore);
                ((ArrayList<ScoreKeeper>)scores.get(0)).remove(5);
                isAdded = true;
            }
        } else if (currentGameLevel == GameDifficulty.MEDIUM) {
            for (int i = 0; i < 4; i++) {
                while (!isAdded) {
                    if (((ArrayList<ScoreKeeper>)scores.get(1)).get(i).getTime() > newHighScore.getTime() &&
                            ((ArrayList<ScoreKeeper>)scores.get(1)).get(i + 1).getTime() <= newHighScore.getTime()) {
                        ((ArrayList<ScoreKeeper>)scores.get(1)).add(i + 1, newHighScore);
                        ((ArrayList<ScoreKeeper>)scores.get(1)).remove(5);
                        isAdded = true;
                    }
                }
            }
            if (!isAdded) {
                ((ArrayList<ScoreKeeper>)scores.get(1)).add(4, newHighScore);
                ((ArrayList<ScoreKeeper>)scores.get(1)).remove(5);
                isAdded = true;
            }
        } else {
            for (int i = 0; i < 4; i++) {
                while (!isAdded) {
                    if (((ArrayList<ScoreKeeper>)scores.get(2)).get(i).getTime() > newHighScore.getTime() &&
                            ((ArrayList<ScoreKeeper>)scores.get(2)).get(i + 1).getTime() <= newHighScore.getTime()) {
                        ((ArrayList<ScoreKeeper>)scores.get(2)).add(i + 1, newHighScore);
                        ((ArrayList<ScoreKeeper>)scores.get(2)).remove(5);
                        isAdded = true;
                    }
                }
            }
            if (!isAdded) {
                ((ArrayList<ScoreKeeper>)scores.get(2)).add(4, newHighScore);
                ((ArrayList<ScoreKeeper>)scores.get(2)).remove(5);
                isAdded = true;
            }
        }
    }
    
    
}