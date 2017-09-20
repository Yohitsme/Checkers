package checkers;

import java.util.Vector;

public class Game {
	
	// handle the game rules for checkers
	Vector<Moves> moves;
	
	// will return a Vector of all legal moves
	@SuppressWarnings("rawtypes")
	public Vector getLegalMoves(int x, int y, int[][] board){
		moves = new Vector<Moves>();
		
		// check if the spaces diagonal to a checker are empty, if so those are legal moves
		// depending on the colour of the checker -> the overall difference must be of value 2
		int colour = board[x][y];
		// if red
		
		// covers forward diagonal movement for black and red but doesn't cover the jumps yet
		if (colour == 1){
			
			
			// first make sure everything is in bounds
			if ((x-1 >= 0) && (y-1 >= 0)){
				// secondly check if the move is to an empty spot
				if (board[x-1][y-1] == 0){
					moves.add(new Moves(x, y, x-1, y-1));
				}
			}
			if ((x-1 >= 0) && (y+1 <= 7)){
				if (board[x-1][y+1] == 0) {
					moves.add(new Moves(x, y, x-1, y+1));
					}
				}
			// third, check if the move is to an opposite piece -
			// now need to check if can JUMP or NOT
			// again need to check new bounds
			if ((x-2 >= 0) && (y-2 >= 0)){
				if (board[x-1][y-1] == 2 || board[x-1][y-1] == 4){
					if (board[x-2][y-2] == 0){
						moves.add(new Moves(x, y, x-2, y-2));
						moves.lastElement().setJump(true, x-1, y-1);
						
					}
				}
			}
			if ((x-2 >= 0) && (y+2 <= 7)){
				if (board[x-1][y+1] == 2 || board[x-1][y+1] == 4){
					if (board[x-2][y+2] == 0){
						moves.add(new Moves(x, y, x-2, y+2));
						moves.lastElement().setJump(true, x-1, y+1);
					}
				}
			}
			
			
			
		// if black
		
		} else if (colour == 2) { 
			if ((y-1 >= 0) && (x+1 <= 7)){
				if (board[x+1][y-1] == 0){
					moves.add(new Moves(x, y, x+1, y-1));
					}
				} 
			if ((y+1 <= 7) && (x+1 <=7)){
				if (board[x+1][y+1] == 0) {
					moves.add(new Moves(x, y, x+1, y+1));
				}
			}
			
			// handling the jumps
			if ((y-2 >= 0) && (x+2 <= 7)){
				if (board[x+1][y-1] == 1 || board[x+1][y-1] == 3){
					if (board[x+2][y-2] == 0){
						moves.add(new Moves(x, y, x+2, y-2));
						moves.lastElement().setJump(true, x+1, y-1);
					}
				}
			}
			if ((y+2 <= 7) && (x+2 <= 7)){
				if (board[x+1][y+1] == 1 || board[x+1][y+1] == 3){
					if (board[x+2][y+2] == 0){
						moves.add(new Moves(x, y, x+2, y+2));
						moves.lastElement().setJump(true, x+1, y+1);
					}
				}
			}

			
			// red king
		} else if (colour == 3) { 
			
			
			if ((x-1 >= 0) && (y-1 >= 0)){
				if (board[x-1][y-1] == 0){
					moves.add(new Moves(x, y, x-1, y-1));
				}
			}
			if ((x-1 >= 0) && (y+1 <= 7)){
				if (board[x-1][y+1] == 0) {
					moves.add(new Moves(x, y, x-1, y+1));
					}
				}
			
			if ((y-1 >= 0) && (x+1 <= 7)){
				if (board[x+1][y-1] == 0){
					moves.add(new Moves(x, y, x+1, y-1));
					}
				} 
			if ((y+1 <= 7) && (x+1 <=7)){
				if (board[x+1][y+1] == 0) {
					moves.add(new Moves(x, y, x+1, y+1));
				}
			}
			
			// handles jumps upwards
			if ((x-2 >= 0) && (y-2 >= 0)){
				if (board[x-1][y-1] == 2 || board[x-1][y-1] == 4){
					if (board[x-2][y-2] == 0){
						moves.add(new Moves(x, y, x-2, y-2));
						moves.lastElement().setJump(true, x-1, y-1);
					}
				}
			}
			if ((x-2 >= 0) && (y+2 <= 7)){
				if (board[x-1][y+1] == 2 || board[x-1][y+1] == 4){
					if (board[x-2][y+2] == 0){
						moves.add(new Moves(x, y, x-2, y+2));
						moves.lastElement().setJump(true, x-1, y+1);
					}
				}
			}
			
			// handles jumps downwards
			if ((y-2 >= 0) && (x+2 <= 7)){
				if (board[x+1][y-1] == 2 || board[x+1][y-1] == 4){
					if (board[x+2][y-2] == 0){
						moves.add(new Moves(x, y, x+2, y-2));
						moves.lastElement().setJump(true, x+1, y-1);
					}
				}
			}
			if ((y+2 <= 7) && (x+2 <= 7)){
				if (board[x+1][y+1] == 2 || board[x+1][y+1] == 4){
					if (board[x+2][y+2] == 0){
						moves.add(new Moves(x, y, x+2, y+2));
						moves.lastElement().setJump(true, x+1, y+1);
					}
				}
			}
			
			
			
			
			 //black king
		} else if (colour == 4) { 
			
			
			
			if ((x-1 >= 0) && (y-1 >= 0)){
				if (board[x-1][y-1] == 0){
					moves.add(new Moves(x, y, x-1, y-1));
				}
			}
			if ((x-1 >= 0) && (y+1 <= 7)){
				if (board[x-1][y+1] == 0) {
					moves.add(new Moves(x, y, x-1, y+1));
					}
				}
			
			if ((y-1 >= 0) && (x+1 <= 7)){
				if (board[x+1][y-1] == 0){
					moves.add(new Moves(x, y, x+1, y-1));
					}
				} 
			if ((y+1 <= 7) && (x+1 <=7)){
				if (board[x+1][y+1] == 0) {
					moves.add(new Moves(x, y, x+1, y+1));
				}
			}
			
			// handles jumps upwards
			if ((x-2 >= 0) && (y-2 >= 0)){
				if (board[x-1][y-1] == 1 || board[x-1][y-1] == 3){
					if (board[x-2][y-2] == 0){
						moves.add(new Moves(x, y, x-2, y-2));
						moves.lastElement().setJump(true, x-1, y-1);
					}
				}
			}
			if ((x-2 >= 0) && (y+2 <= 7)){
				if (board[x-1][y+1] == 1 || board[x-1][y+1] == 3){
					if (board[x-2][y+2] == 0){
						moves.add(new Moves(x, y, x-2, y+2));
						moves.lastElement().setJump(true, x-1, y+1);
					}
				}
			}
			
			// handles jumps downwards
			if ((y-2 >= 0) && (x+2 <= 7)){
				if (board[x+1][y-1] == 1 || board[x+1][y-1] == 3){
					if (board[x+2][y-2] == 0){
						moves.add(new Moves(x, y, x+2, y-2));
						moves.lastElement().setJump(true, x+1, y-1);
					}
				}
			}
			if ((y+2 <= 7) && (x+2 <= 7)){
				if (board[x+1][y+1] == 1 || board[x+1][y+1] == 3){
					if (board[x+2][y+2] == 0){
						moves.add(new Moves(x, y, x+2, y+2));
						moves.lastElement().setJump(true, x+1, y+1);
					}
				}
			}			
			
			
		}
		
		
		
		return moves;
	}
	

}
