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
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class CheckerPanel extends JPanel implements MouseListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Boolean chose = false; // whether the user has chosen a piece
	private Boolean selected = false;
	private int sizeX = 600;
	private int sizeY = 600;
	private int firstX = 100;
	private int firstY = 100;
	private int fromX, fromY, toX, toY;	// movement integers
	private JButton newGameButton;	// button for starting a new game
	private JButton resignButton;	// button for resigning (dependent on whose turn it is)
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

	public CheckerPanel(){
		setSize(sizeX, sizeY);
		setBackground(Color.cyan);
		setBorder(BorderFactory.createLineBorder(Color.blue, 5));
		setLayout(null);
		newGameButton = new JButton("New Game");
		resignButton = new JButton("Resign");
		newGameButton.setSize(new Dimension(120,60));
		resignButton.setSize(new Dimension(120, 60));
		newGameButton.setLocation(100, 520);
		resignButton.setLocation(380, 520);
		setUpBoard();
		
		// if new game is clicked
		newGameButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				setUpBoard();
				repaint();
				
			}
		});
		
		// if resign is clicked
		resignButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
			}
		});
		
		addMouseListener(this);
		add(newGameButton);
		add(resignButton);
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
	public void scoutJump(int toX, int toY){
		
		Vector<Moves> moreMoves = game.getLegalMoves(toX, toY, board);
		for (Moves items: moreMoves){
			if (items.getJump()){
				
			}
		}
		
	}
	
	// movement (does not check for legality of move)
	public void move(int fromX, int fromY, int toX, int toY){
		// this just changes the int values of the 2d array on square to another square
		Vector<Moves> moves = game.getLegalMoves(fromX, fromY, board);
		Moves currentMove = new Moves(fromX, fromY, toX, toY);
		boolean legal = false;
		for (Moves items : moves){
			if ( currentMove.equals(items)){
				legal = true;
				if (items.getJump()){
					board[items.getJumpedX()][items.getJumpedY()] = EMPTY;
					// scountJump();
				}
			}
		}
		
		int holder = board[fromX][fromY];
		if (fromX != toX && fromY != toY && legal){
			if (holder!= EMPTY || holder!= ALWAYS_EMPTY){
				board[fromX][fromY] = EMPTY;
				board[toX][toY] = holder;
				if (turn == 1){
					turn = 2;
				} else {
					turn = 1;
				}
			}
		}
		repaint();

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
				
		if (!MyCheckers.playing){
			
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
									move(fromX, fromY, toX, toY);
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
	
}
