package checkers;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CheckerPanel extends JPanel implements MouseListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Boolean chose = false; // whether the user has chosen a piece
	private int sizeX = 600;
	private int sizeY = 600;
	private int firstX = 100;
	private int firstY = 100;
	private Boolean globalJump = false;	// checks the board to see if a jump must be made this turn
	private int fromX, fromY, toX, toY;	// movement integers
	private JButton newGameButton;	// button for starting a new game
	private JButton resignButton;	// button for resigning (dependent on whose turn it is)
	private JLabel counterLabelR;	// label to display the counter for red checkers
	private JLabel counterLabelB;	// label to display the counter for black checkers
	private JLabel winLabelR;	// label to display the wins for Red
	private JLabel winLabelB;	// label to display the wins for Black
	private int[][] board;	// the main board using various integers to represent the colours
	
	// -1 -> always empty, 0 -> empty tile, 1 -> red piece, 2 -> black piece, 3 -> red king piece, 4 -> black king piece
	protected final int  ALWAYS_EMPTY = -1,
								EMPTY = 0,
								RED = 1,
								BLACK = 2,
								RED_KING = 3,
								BLACK_KING = 4;
	
	// turn integer variable will dictate who's turn it is using the same schema as above
		private int turn = RED;
		private Game game = new Game();
		private int counterB; 	private int counterR;	// piece counters
		private int winB; 	private int winR;	// win counters 	
		private File soundFile;
		private AudioInputStream audioIn; 
		private Clip clip;

	public CheckerPanel(){
		setSize(sizeX, sizeY);
		setBackground(Color.cyan);
		setBorder(BorderFactory.createLineBorder(Color.blue, 5));
		setLayout(null);
		winR = 0; winB = 0;
		newGameButton = new JButton("New Game");
		resignButton = new JButton("Resign");
		newGameButton.setSize(new Dimension(120,60));
		resignButton.setSize(new Dimension(120, 60));
		newGameButton.setLocation(100, 510);
		resignButton.setLocation(380, 510);
		setUpBoard();
		// setting up the piece counters
		setCounterLabelR(new JLabel("Red Pieces: " + getCounterR()));
		setCounterLabelB(new JLabel("Black Pieces: " + getCounterB()));
		getCounterLabelR().setSize(new Dimension(120, 60));
		getCounterLabelB().setSize(new Dimension(120, 60));
		getCounterLabelR().setLocation(515, 445);
		getCounterLabelB().setLocation(508, 147);
		// setting up the win counters
		winLabelR = new JLabel("WINS: \n" + winR);
		winLabelB = new JLabel("WINS: \n" + winB);
		winLabelR.setSize(new Dimension(60, 60));
		winLabelB.setSize(new Dimension(60, 60));
		winLabelR.setLocation(530, 25);
		winLabelB.setLocation(530, 510);

		
		
		// if new game is clicked
		newGameButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				setUpBoard();
				getCounterLabelR().setText("Red Pieces: " + getCounterR());
				getCounterLabelB().setText("Black Pieces: " + getCounterB());
				repaint();
				
			}
		});
		
		// if resign is clicked
		resignButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (MyCheckers.playing){
					if (turn == 1){
						winB += 1;
						winLabelB.setText("WINS: \n" + winB);
					} else if (turn == 2){
						winR += 1;
						winLabelR.setText("WINS: \n" + winR);
					}
				}
				MyCheckers.playing = false;

			}
		});
		
		addMouseListener(this);
		add(newGameButton);
		add(resignButton);
		//add(counterLabelR);
		//add(counterLabelB);
		add(winLabelR);
		add(winLabelB);
		setVisible(true);
	}
	
	// this is essential when painting the board
	public void setMovement(int fromX, int fromY, int toX, int toY){
		this.fromX = fromX;
		this.fromY = fromY;
		this.toX = toX;
		this.toY = toY;
	}
	
	// resets the movement parameters to some dummy values
	public void resetMovement(){
		this.fromX = -100;
		this.fromY = -100;
		this.toX = -100;
		this.toY = -100;
	}
	
	// set up the board for new games
	public void setUpBoard(){
		turn = RED;
		setCounterB(12); setCounterR(12);
		board = new int[8][8];
		for (int i = 0; i < 8; i++){
			for (int j = 0; j < 8; j++){
				// pieces are only found on squares where the x and y have the same remainder after mod2
				// however not found on EVERY square where that condition is met
				if (i % 2 == j % 2){
					// black pieces are found on rows 0 1 and 2
					if (i < 3){
						board[i][j] = BLACK;
					// red pieces are found on rows 6, 7 and 8
					} else if (i > 4){
						board[i][j] = RED;
					// rows 4 and 5 start off empty
					} else {
						board[i][j] = EMPTY;
					}
				} else { 
					// every other square is empty and will always be empty
					board[i][j] = ALWAYS_EMPTY;
				}
			}
		}	
		MyCheckers.playing = true;
	}
		
	// returns the checker board 2d int array
	public int[][] getBoard(){
		return board;
	}
	
	// returns what piece is at this position	
	public int pieceAt(int row, int col){
		return board[row][col];
	}
	
	public void pieceAt(int row, int col, int type){
		board[row][col] = type;
	}
	
	public void printArray(){
		for (int i = 0; i < 8; i++){
		for (int j = 0; j < 8; j++){
				System.out.println(i + " , " + j + " : " + board[i][j]);
			}
		}
	}
	
	// checks if the piece can jump again - at most a piece can jump 3 times if not king...
	public Boolean scoutJump(int toX, int toY){
		
		Vector<Moves> moreMoves = game.getLegalMoves(toX, toY, board);
		for (Moves items: moreMoves){
			if (items.getJump()){
				return true;
			}
		}
		return false;
	}
	
	// returns if the player with the active turn has 0 pieces
	public Boolean checkOver(){
		if (getCounterR() == 0 || getCounterB() == 0){
			return true;
		}
		return false;
	}
	
	// movement (does not check for legality of move)
	public void move(int fromX, int fromY, int toX, int toY) throws LineUnavailableException{
		// this just changes the int values of the 2d array on square to another square
		Vector<Moves> moves = game.getLegalMoves(fromX, fromY, board);

		Moves currentMove = new Moves(fromX, fromY, toX, toY);
		boolean legal = false;
		boolean moreJumps = false;
		boolean mustJump = false;
		boolean isJump = false;
		int col = 0;
		
		// checks if any piece must jump
		globalJump = game.checkGlobalJump(board, turn);
		
		// checks if the certain piece must jump
		for (Moves items : moves){
			// if the moveset holds a jump move, the user must jump
			if (items.getJump() == true){
				mustJump = true;
				}
		}

		
		for (Moves items: moves){
			// checking if the user chose a move from the move-set AND if the move was jump if must jump is true
			if ( currentMove.equals(items)){
				
				// sets the current move jump status to that of the one it matched with
				currentMove.setJump(items.getJump());
							
				// if the current move is not a jump but it must be a jump then the move is not legal
				if (items.getJump() == false && globalJump == true){
					legal = false;
				}
				
				// if mustjump is false and the move is not a jump it is legal
				else if (items.getJump() == false && globalJump == false){
					legal = true;
				}
				
				if (items.getJump()){
					
					col = board[items.getJumpedX()][items.getJumpedY()];
					board[items.getJumpedX()][items.getJumpedY()] = EMPTY;
					isJump = true;
					legal = true;
					if (col == 1 || col == 3){
						if (getCounterR() - 1 >= 0){
							setCounterR(getCounterR() - 1);
							getCounterLabelR().setText("Red Pieces: " + getCounterR());
						}
					} else if (col == 2 || col == 4){
						if (getCounterB() - 1 >= 0){
							setCounterB(getCounterB() - 1);
							getCounterLabelB().setText("Black Pieces: " + getCounterB());
						}
					}
				}
				
			}
		}
		
				
		
		int holder = board[fromX][fromY];
		if (fromX != toX && fromY != toY && legal){
			if (holder!= EMPTY || holder!= ALWAYS_EMPTY){
				board[fromX][fromY] = EMPTY;
				board[toX][toY] = holder;
				
				soundFile = new File("src/checkers/staple.wav");
				try {
					audioIn = AudioSystem.getAudioInputStream(soundFile);
				} catch (UnsupportedAudioFileException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				clip = AudioSystem.getClip();
				
				try {
					clip.open(audioIn);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				clip.start();
				
				if (isJump){
					if (scoutJump(toX, toY)){
						moreJumps = true;
					}
				}
				
				if (turn == 1 && moreJumps == false){
					if (globalJump == true){
						globalJump = false;
					}
					turn = 2;
				} else  if (turn == 2 && !moreJumps){
					if (globalJump == true){
						globalJump = false;
					}
					turn = 1;
				}
			}
		}
		repaint();
		
		if (checkOver()){
			if (turn == 1){
				winR += 1;
				winLabelR.setText("WINS: \n" + winR);
			}
			if (turn == 2){
			winB += 1;
			winLabelB.setText("WINS: \n" + winB);
		 	}
			MyCheckers.playing = false;
		}
		

	}
	
	// returns who's turn it is
	public int getTurn(){
		return turn;
	}
	
	// crown a piece
	public void crown(){
		for (int i = 0; i < 8; i++){
			if (board[0][i] == 1){
				board[0][i] = 3;
			} else if (board[7][i] == 2){
				board[7][i] = 4;
			}
		}
	}
		

	// alternates the players turn (this will be performed automatically after each move)
	public void changeTurn(){
		if (turn == RED){
			turn = BLACK;
		} else {
			turn = RED;
		}
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
				
		if (MyCheckers.playing){
			
			crown();
			
			// drawing the centre square
			g.setColor(Color.GRAY);
			g.fillRect(firstX, firstX, 400, 400); 
			
			// drawing the checker board and the checkers according to the int 2d array board
			for (int row = 0; row < 8; row++){
				for (int col = 0; col <8; col++){
					if (row % 2 == col % 2)
						g.setColor(Color.LIGHT_GRAY);
					 else {
						g.setColor(Color.gray);
					}
					g.fillRect(firstX+(col*50), firstY+(row*50), 50, 50);
					// this switch will check what colour piece is at what position
					// and update the board with that colour checker in that location
					switch (pieceAt(row, col)){
						case RED:
							g.setColor(Color.red);
							g.fillOval((6 + firstX) + (col*50), (6+firstY) + (row*50), 40, 40);
							break;
						case BLACK:
							g.setColor(Color.black);
							g.fillOval((6 + firstX) + (col*50), (6+firstY) + (row*50), 40, 40);
							break;
						case RED_KING:
			                g.setColor(Color.red);
			                 g.fillOval((6 + firstX) + col*50, (6+firstY) + row*50, 40, 40);
			                g.setColor(Color.white);
			                g.drawString("K", (22 + firstX) + col*50, (29+firstY) + row*50);
			                break;
			             case BLACK_KING:
			                g.setColor(Color.black);
			                g.fillOval((6 + firstX) + col*50, (6+firstY) + row*50, 40, 40);
			                g.setColor(Color.white);
			                g.drawString("K", (22 + firstX) + col*50, (29+firstY) + row*50);
			                break;
					}
					
					if (chose){
						// highlights the chosen square
						g.setColor(Color.BLUE);
						g.drawRect(firstX + (fromY * 50), firstY + (50 * fromX), 50, 50);
						
					}
					
					if (turn == 1){
						g.setColor(Color.red);
						g.fillOval(538, 430, 30, 30);
						g.drawString("RED TURN", 522, 420);
					} else {
						g.setColor(Color.black);
						g.fillOval(538, 130, 30, 30);
						g.drawString("BLACK TURN", 519, 120);
					}
					
				}
			}
		
		// making the border
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.black);
		g2.setStroke(new BasicStroke(5));
		g2.drawRect(firstX, firstY, 400, 400);
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		int xCord = event.getX();
		int yCord = event.getY();
		int colour;
		
		if (MyCheckers.playing){
				// checks whether the click was within the checker board
				if ((xCord >= firstX+2) && (yCord >= firstY+2) && (xCord <= firstX+398) && (yCord <= firstY+398) ){
					// map the board to a 2d grid space where each tile is 50 by 50. checks which tile was clicked
					for (int i = 0; i < 8; i++ ){
						for (int j = 0; j < 8; j++){
							if ( ( xCord >= (firstX + ((j)*50)) )  && (xCord < (firstX + ((j+1)*50))) ){
								if ( ( yCord >= (firstY + ((i)*50)) )  && (yCord < (firstY + ((i+1)*50))) ){
									if (!chose){
										colour = board[i][j];
										if (colour == EMPTY || colour == ALWAYS_EMPTY || colour != turn){
											break;
										}
										fromX = i; fromY = j;
										chose = true;
										repaint();
									} else {
										toX = i; toY = j;
										chose = false;
										try {
											move(fromX, fromY, toX, toY);
										} catch (LineUnavailableException e) {
											e.printStackTrace();
										}
								}
							}
						}
					}
				}
			}
		}
		
	}

	// unused methods but need to be implemented
	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}

	public JLabel getCounterLabelR() {
		return counterLabelR;
	}

	public void setCounterLabelR(JLabel counterLabelR) {
		this.counterLabelR = counterLabelR;
	}

	public JLabel getCounterLabelB() {
		return counterLabelB;
	}

	public void setCounterLabelB(JLabel counterLabelB) {
		this.counterLabelB = counterLabelB;
	}

	public int getCounterR() {
		return counterR;
	}

	public void setCounterR(int counterR) {
		this.counterR = counterR;
	}

	public int getCounterB() {
		return counterB;
	}

	public void setCounterB(int counterB) {
		this.counterB = counterB;
	}
	
}
