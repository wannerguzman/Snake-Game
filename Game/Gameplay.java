import java.awt.Color;  
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;


import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
/**
 * Game Play of snake game takes play in this class
 * Class will extend JPanela and keyListener and ActonListener 
 * We will need to override the methods of KeyListner and ActionListener since the are interfaces
 * @author wanner
 *
 */
public class Gameplay extends JPanel implements KeyListener, ActionListener{
	

	private static final long serialVersionUID = 1L;

	//Detect space press.
	private int waitforspacepress = 0;
	
	//The first index stores the position of head and other is the other positions are the body of snake.
	//Position of head is stored to previous index.
	//The positions shift to the back side
	private int[] snakexlength = new int[750];
	private int[] snakeylength = new int[750];
	
	//Detect direction.
	private boolean left = false;
	private boolean right = false;
	private boolean down = false;
	private boolean up = false;
	
	//Snake face.
	private ImageIcon rightmouth;
	private ImageIcon leftmouth;
	private ImageIcon upmouth;
	private ImageIcon downmouth;
	
	//Timer class manage speed of snake and delay time
	private Timer timer;
	private int delay = 100;
	
	//Snake tail image, title and apple/obstacle.
	private ImageIcon snakeimage;
	private ImageIcon titleImage;
	private ImageIcon enemyimage;
	
	//Default snake length.
	private int lengthofsnake = 3;
	
	//Detect if game is initiating.
	private int move = 0;
	
	//Different positions from (x,y) axis that the apple/obstacle can be positioned at.
	private int [] enemyxpos = {25,50,75,100,125,150,175,200,225,250,275,300,325,
			350,375,400,425,450,475,500,525,550,575,600,625,650,
			675,700,725,750,775,800,825,850};
	private int [] enemyypos = {75,100,125,150,175,200,225,250,275,300,325,350,
			375,400,425,450,475,500,525,550,575,600,625};
	
	
	//Random class for randomly creating a position for apple.
	//xpos will have an index value between 0-34
	//ypos will have an index value between 0-23
	private Random random = new Random();
	private int xpos = random.nextInt(34);
	private int ypos = random.nextInt(23);
	
	//keep track of score/
	private int score = 0;
	/**
	 * Constructor calls super class and adds KeyListener to this Panel.
	 * We also start a timer in this class.
	 */
	
	@SuppressWarnings("restriction")
	public Gameplay() {
		super();
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
	}
	/**
	 * @Param g
	 * First we create the game window.
	 * We add a title to it, internal game play window and its color, border colors.
	 * We also initiate the four different type of heads for the snake, and obstacle.
	 * Here we also increment the snakes length and check if the snake collapse with itself which would end the game.
	 * When move is 0 the game will have a default start location. Else when you move left and right it will later be incremented.
	 */
	public void paint(Graphics g) {
		if(move == 0) {
			snakexlength[2] = 50;
			snakexlength[1] = 75;
			snakexlength[0] = 100;
			
			snakeylength[2] = 100;
			snakeylength[1] = 100;
			snakeylength[0] = 100;	
		}
		
		//Draw title image border.
		g.setColor(Color.BLACK);
		g.drawRect(24, 10, 851, 55);
		
		//Draw the title image.
		titleImage = new ImageIcon("snaketitle.jpg");
		titleImage.paintIcon(this, g, 25, 11);
		
		
		//Draw border for game-play.
		g.setColor(Color.BLACK);
		g.drawRect(24, 74, 851, 577);
		
		//Draw background for the game-play.
		g.setColor(new Color(153,102,0));
		g.fillRect(25, 75, 850, 575);
		
		//Right mouth image for snake.
		rightmouth = new ImageIcon("rightmouth.png");
		rightmouth.paintIcon(this,g,snakexlength[0], snakeylength[0]);
		
		//Draw score.
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", Font.PLAIN,14));
		g.drawString("Scores: "+score, 780, 30);
		
		//Draw the length of snake.
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", Font.PLAIN,14));
		g.drawString("Length: "+lengthofsnake, 780, 50);
		
		//Action performed when starting the game if you press up,down,left,right.
		//Redraw mouth and modify snake
		for(int i=0; i<lengthofsnake; i++) {
			if(i==0 && right) {
				rightmouth = new ImageIcon("rightmouth.png");
				rightmouth.paintIcon(this,g,snakexlength[i], snakeylength[i]);
			}
			if(i==0 && left) {
				leftmouth = new ImageIcon("leftmouth.png");
				leftmouth.paintIcon(this,g,snakexlength[i], snakeylength[i]);
			}
			if(i==0 && up) {
				upmouth = new ImageIcon("upmouth.png");
				upmouth.paintIcon(this,g,snakexlength[i], snakeylength[i]);
			}
			if(i==0 && down) {
				downmouth = new ImageIcon("downmouth.png");
			    downmouth.paintIcon(this,g,snakexlength[i], snakeylength[i]);
			}
			if(i!=0) {
				snakeimage = new ImageIcon("snakeimage.png");
				snakeimage.paintIcon(this,g,snakexlength[i], snakeylength[i]);
			}
		}
		//If the apple or obstacle is detected, increment snake length and score and put the apple in a new position
		//We will check if the obstacle was detected by checking if the snake collaps with the apple
		enemyimage = new ImageIcon("enemy.png");
		if((enemyxpos[xpos]==snakexlength[0]) && enemyypos[ypos]==snakeylength[0]) {
			score++;
			lengthofsnake++;
			xpos = random.nextInt(34);
			ypos = random.nextInt(23);
		}
		//repaint apple in the new location
		enemyimage.paintIcon(this, g, enemyxpos[xpos], enemyypos[ypos]);
		//check if the snake collapse with itself, if so end the game and wait for space press
		//waitforspacepress will be 1 when its waiting.
		for(int i=1; i<lengthofsnake; i++) {
			if(snakexlength[i]==snakexlength[0] && snakeylength[i]==snakeylength[0]) {
				right = false;
				left = false;
				up = false;
				down = false;
				
				g.setColor(Color.WHITE);
				g.setFont(new Font("arial",Font.BOLD,50));
				g.drawString("Game Over", 300, 300);
				g.setFont(new Font("arial",Font.BOLD,20));
				g.drawString("Space to RESTART", 350, 340);
				
				waitforspacepress = 1;
			}
		}
		
		g.dispose();
	}
    /**
     * @param e
     * ActionPefromed from for the timer. Every-time the timer restarts it will form the following actions and repaint.
     * Check if either right,left,down, up is true and modify the snake respectively.
     * Shifts the position of head to the next index and shift the position of the body.
     */
	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if(right) {
			for(int i=lengthofsnake-1; i>=0; i-- ) {
				snakeylength[i+1] = snakeylength[i]; 
			}
			for(int i=lengthofsnake; i>=0; i--) {
				if(i==0) {
					snakexlength[i] = snakexlength[i] + 25;
				}
				else {
					snakexlength[i] = snakexlength[i-1];
				}
				if(snakexlength[i]>850) {
					snakexlength[i] = 25;
				}
			}
			repaint();
		}
		if(left) {
			for(int i=lengthofsnake-1; i>=0; i-- ) {
				snakeylength[i+1] = snakeylength[i]; 
			}
			for(int i=lengthofsnake; i>=0; i--) {
				if(i==0) {
					snakexlength[i] = snakexlength[i] - 25;
				}
				else {
					snakexlength[i] = snakexlength[i-1];
				}
				if(snakexlength[i]<25) {
					snakexlength[i] = 850;
				}
			}
			repaint();
			
		}
		if(down) {
			for(int i=lengthofsnake-1; i>=0; i-- ) {
				snakexlength[i+1] = snakexlength[i]; 
			}
			for(int i=lengthofsnake; i>=0; i--) {
				if(i==0) {
					snakeylength[i] = snakeylength[i] + 25;
				}
				else {
					snakeylength[i] = snakeylength[i-1];
				}
				if(snakeylength[i]>625) {
					snakeylength[i] = 75;
				}
			}
			repaint();
		}
		if(up) {
			for(int i=lengthofsnake-1; i>=0; i-- ) {
				snakexlength[i+1] = snakexlength[i]; 
			}
			for(int i=lengthofsnake; i>=0; i--) {
				if(i==0) {
					snakeylength[i] = snakeylength[i] - 25;
				}
				else {
					snakeylength[i] = snakeylength[i-1];
				}
				if(snakeylength[i]<75) {
					snakeylength[i] = 625;
				}
			}
			repaint();
			
		}
	}
    /**
     * No need to use this method .
     */
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
    /**
     * @param e
     * We get the code of the event that took place and check if it was a space,right,left,down or up key.
     * If it was a space restart the game.
     * Increment move
     * If the game ended we are waiting for a space press so any of the following press will not take effect, until we restart the game.
     * If right is pressed set right true and the left,up,down false.
     * If left is pressed set left true and right,up,down false. Flip left with right if right is true to prevent snake from collapsing with itself.
     * If up is pressed set up true and down,left right false. Flip up with down if down is true to prevent snake from collapsing with itself.
     * If down is pressed set down true and up,left,right false.Flip up with down if up is true to prevent snake from collapsing with itself.
     */
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			waitforspacepress = 0;
			move = 0;
			score = 0;
			lengthofsnake = 3;
			repaint();
		}
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT && waitforspacepress != 1) {
			move++;
			right = true;
			if(!left) {
				right = true;
			}
			else {
				right = false;
				left = true;
			}
			up = false;
			down = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT && waitforspacepress != 1) {
			move++;
			left = true;
			if(!right) {
				left = true;
			}
			else {
				left = false;
				right = true;
			}
			up = false;
			down = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN && waitforspacepress != 1) {
			move++;
			down = true;
			if(!up) {
				down = true;
			}
			else {
				down = false;
				up = true;
			}
			left = false;
			right = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_UP && waitforspacepress != 1) {
			move++;
			up = true;
			if(!down) {
				up = true;
			}
			else {
				up= false;
				down = true;
			}
			right = false;
			left = false;
		}
	}
    /**
     * @param e
     * No need to use this method that check when a key is released.
     */
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
