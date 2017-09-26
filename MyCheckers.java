package checkers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MyCheckers extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JMenuBar menubar;
	private JMenu options;
	private JMenuItem newGame, settings, exit;
	protected static Boolean playing;
	private Container container;
	private Dimension dimension;
	private int sizeX = 600;
	private int sizeY = 600;

	private CheckerPanel mainPanel;
	
	
	// constructor
	public MyCheckers(){
		super("Checkers");
		mainPanel = new CheckerPanel();
		
		container = getContentPane();
		container.setBackground(Color.blue); 
		dimension = new Dimension(sizeX, sizeY);
		container.setPreferredSize(dimension);
		pack();
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		playing = true;
		
		// setting up the menubar
		menubar = new JMenuBar();
		options = new JMenu("Options");
		options.getAccessibleContext().setAccessibleDescription("View the options");
		newGame = new JMenuItem("New Game");
		settings = new JMenuItem("Settings");
		exit = new JMenuItem("Exit");;
		options.add(newGame);  options.add(settings); options.addSeparator(); options.add(exit);
		menubar.add(options);

		
		// same functionality as the new game button 
		newGame.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainPanel.setUpBoard();
				mainPanel.getCounterLabelR().setText("Red Pieces: " + mainPanel.getCounterR());
				mainPanel.getCounterLabelB().setText("Black Pieces: " + mainPanel.getCounterB());
				mainPanel.repaint();
			}
			
		});
		
		// closes the program
		
		exit.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent arg0){
				System.exit(1);
			}
		});
		
		
		setJMenuBar(menubar);
		add(mainPanel, BorderLayout.CENTER);
		setVisible(true);
	}
	

}
