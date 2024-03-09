
import java.awt.Color;

public class Player {

    private String name;
    private int playerPosn;
    private int playerScore;
    private Color playerColor;

    public Player(int no) {
        name = "Player " + no;
        playerPosn = 0;
    }

    public void setPosition(int posn) {
        playerPosn = posn;
    }

    public void setPlayerColor(Color c) {
        playerColor = c;
    }

    public void incPosition(int posn) {
        playerPosn += posn;
    }

    public void incPlayerScore(int a) {
        playerScore += a;
    }

    /*
	public int returnPosition(){
		return playerPosn;
	}
	
	public String returnName(){
		return name;
	}
		
	public Color returnPlayerColor(){
		return playerColor;
	}
	
	public int returnPlayerScore(){
		return playerScore;
	}
     */
    public String getName() {
        return name;
    }

    public int getPlayerPosn() {
        return playerPosn;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public Color getPlayerColor() {
        return playerColor;
    }

}
