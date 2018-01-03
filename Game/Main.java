import java.awt.Color;  
import javax.swing.JFrame;

/**
 * Main class with main method that runs the Snake Game-play.
 * 
 * @author wanner
 *
 */
public class Main {
	/**
	 * Main method to run that runs the Snake Game.
	 * Create the Frame for the game.
	 * Set bounds and background to brown color, make sure it is visible and that it closes when pressing x.
	 * Create a GamePlay Panel and add it to frame.
	 * @param args
	 */
	public static void main(String[] args) {
		Color Brown = new Color(102,51,0);
		// make a JFrame.
		// JFrame is the window in which our game runs.
		JFrame obj = new JFrame();
		// Create a game-play class that extends JPanel.
		Gameplay gameplay = new Gameplay();
		// Set the properties of this frame.
		// Like title, size, background.
		obj.setBounds(10, 10, 905, 720);
		obj.setTitle("Snake Game");
		obj.setBackground(Brown);
		obj.setResizable(false); // SetResizable to false so user can't change size of frame.
		obj.setVisible(true); // True to make window visible.
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit application using system exit.
		// Add game-play JPanel to obj JFrame.
		obj.add(gameplay);
	}
}
