package checkers;

public class Moves {
	
	// a new class that stores a possible set of moves
	private int startX, startY, endX, endY, jumpedX, jumpedY;
	private Boolean jump = false;;
	
	public Moves(int startX, int startY, int endX, int endY){
		this.startX = startX; 
		this.startY = startY; 
		this.endX = endX; 
		this.endY = endY;
	}
	
	public boolean equals(Moves other){
		// check if all 4 integer variables are the same
		if (this.startX == other.startX && this.startY == other.startY 
				&& this.endX == other.endX && this.endY == other.endY){
			return true;
		}
		return false;
	}
	
	public void setJump(Boolean tof, int jumpedX, int jumpedY){
		jump = tof;
		this.jumpedX = jumpedX;
		this.jumpedY = jumpedY;
	}
	
	public void setJump(Boolean tof){
		jump = tof;
	}
	
	public Boolean getJump(){
		return jump;
	}
	
	public int getJumpedX() {
		return jumpedX;
	}

	public void setJumpedX(int jumpedX) {
		this.jumpedX = jumpedX;
	}

	public int getJumpedY() {
		return jumpedY;
	}

	public void setJumpedY(int jumpedY) {
		this.jumpedY = jumpedY;
	}

	public String toString(){
		String str;
		str = startX + " , " + startY + " | " + endX + " , " + endY + " , jump:" + getJump();
		return str;
	}
	
}
