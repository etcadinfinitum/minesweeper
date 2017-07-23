public class GameSquare {

    private boolean isMine;
    private int mineHint;
    private boolean isSelected;
    
    public GameSquare() {
        isSelected = false;
        mineHint = 0;
    }
  
    public void setBomb(boolean isBomb) {
        isMine = isBomb;
    }
  
    public boolean getBomb() {
        return isMine;
    }
  
    public boolean incrementHint() {
        mineHint++;
        return true;
    }
    
    public int getHint() {
        return mineHint;
    }
    
    public void clicked() {
        isSelected = true;
    }
    
    public boolean getClicked() {
        return isSelected;
    }
    
}