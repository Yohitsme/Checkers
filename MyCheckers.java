package checkers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;

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
	private JMenuItem newGame, settings, exit, stop;

	protected static Boolean playing = false;
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
		
		// setting up the menubar
		menubar = new JMenuBar();
		options = new JMenu("Options");
		options.getAccessibleContext().setAccessibleDescription("View the options");
		newGame = new JMenuItem("New Game");
		settings = new JMenuItem("Settings");
		exit = new JMenuItem("Exit");
		stop = new JMenuItem("Stop");
		options.add(newGame); options.add(stop); options.add(settings); options.addSeparator(); options.add(exit);
		menubar.add(options);

		setJMenuBar(menubar);
		add(mainPanel, BorderLayout.CENTER);
		setVisible(true);
	}
	

}
