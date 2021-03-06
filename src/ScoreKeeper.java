public class ScoreKeeper implements Comparable {
    
    private String name;
    private int time;
    private GameDifficulty diff;
    
    public ScoreKeeper(String name, int time, GameDifficulty diff) {
        this.name = name;
        this.time = time;
        this.diff = diff;
    }
    
    public String getName() {
        return name;
    }
    
    public int getTime() {
        return time;
    }
    
    public GameDifficulty getLevel() {
        return diff;
    }
    
    public String toString() {
        return Integer.toString(time) + " by " + name;
    }
    
    public int compareTo(Object otherScore) {
        int otherTime = ((ScoreKeeper) otherScore).getTime();
        return this.getTime() - otherTime;
    }
    
}
