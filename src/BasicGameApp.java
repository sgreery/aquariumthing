//Basic Game Application
//Version 2
// Basic Object, Image, Movement
// Celtic moves to the right.
// Threaded

//K. Chun 8/2018

//*******************************************************************************
//Import Section
//Add Java libraries needed for the game
//import java.awt.Canvas;

//Graphics Libraries
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;


//*******************************************************************************
// Class Definition Section

public class BasicGameApp implements Runnable {

   //Variable Definition Section
   //Declare the variables used in the program 
   //You can set their initial values too
   
   //Sets the width and height of the program window
	final int WIDTH = 1000;
	final int HEIGHT = 700;

   //Declare the variables needed for the graphics
	public JFrame frame;
	public Canvas canvas;
   public JPanel panel;
   
	public BufferStrategy bufferStrategy;
	public Image basketballPic;
	public Image basketballguyPic;
	public Image hoopPic;
	public Image backgroundPic;

   //Declare the objects used in the program
   //These are things that are made up of more than one variable type
	private Celtic basketballPlayer;
	private Celtic basketball;
	private Celtic hoop;


   // Main method definition
   // This is the code that runs first and automatically
	public static void main(String[] args) {
		BasicGameApp ex = new BasicGameApp();   //creates a new instance of the game
		new Thread(ex).start();                 //creates a threads & starts up the code in the run( ) method  
	}


   // Constructor Method
   // This has the same name as the class
   // This section is the setup portion of the program
   // Initialize your variables and construct your program objects here.
	public BasicGameApp() {
      
      setUpGraphics();
       
      //variable and objects
      //create (construct) the objects needed for the game and load up
		basketballguyPic = Toolkit.getDefaultToolkit().getImage("aicelticsguy.png");
		basketballPlayer = new Celtic(100, 100);
		basketballPic = Toolkit.getDefaultToolkit().getImage("goldbaskteball.png");
		basketball = new Celtic(500,500);
		hoopPic = Toolkit.getDefaultToolkit().getImage("aihoop.png");
		hoop = new Celtic(200, 600);
		backgroundPic = Toolkit.getDefaultToolkit().getImage("aicourt.png");

	}// BasicGameApp()

   
//*******************************************************************************
//User Method Section
//
// put your code to do things here.

   // main thread
   // this is the code that plays the game after you set things up
	public void run() {

      //for the moment we will loop things forever.
		while (true) {

         moveThings();  //move all the game objects
         render();  // paint the graphics
         pause(20); // sleep for 10 ms
		}
	}


	public void moveThings()
	{
      //calls the move( ) code in the objects
		basketballPlayer.move();
		basketball.move();
		hoop.move();
		basketballPlayer.bounce();
		basketball.bounce();
		hoop.bounce();
		collisions();
	}
	public void collisions(){
		if(hoop.rec.intersects(basketballPlayer.rec) && hoop.isCrashing == false){
			System.out.println("explosion!");
			hoop.dx = -hoop.dx;
			hoop.dy = -hoop.dy;
			basketballPlayer.dx = -basketballPlayer.dx;
			basketballPlayer.dy = -basketballPlayer.dy;
			basketballPlayer.width = basketballPlayer.width + 20;
			basketballPlayer.height = basketballPlayer.height + 20;
			hoop.dx = hoop.dx * 2;
			hoop.dy = hoop.dy * 2;
			hoop.isCrashing = true;
		}
		if(!hoop.rec.intersects(basketballPlayer.rec)){
			hoop.isCrashing = false;
		}
		if(basketball.rec.intersects(basketballPlayer.rec) && hoop.isCrashing == false){
			System.out.println("explosion!");
			basketball.dx = -basketball.dx;
			basketball.dy = -basketball.dy;
			basketballPlayer.dx = -basketballPlayer.dx;
			basketballPlayer.dy = -basketballPlayer.dy;
			basketballPlayer.width = basketballPlayer.width + 20;
			basketballPlayer.height = basketballPlayer.height + 20;
			basketball.dx = basketball.dx * 2;
			basketball.dy = basketball.dy * 2;
			basketball.isCrashing = true;
		}
		if(!basketball.rec.intersects(basketballPlayer.rec)){
			basketball.isCrashing = false;
		}
	}

	
   //Pauses or sleeps the computer for the amount specified in milliseconds
   public void pause(int time ){
   		//sleep
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {

			}
   }

   //Graphics setup method
   private void setUpGraphics() {
      frame = new JFrame("Application Template");   //Create the program window or frame.  Names it.
   
      panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
      panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
      panel.setLayout(null);   //set the layout
   
      // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
      // and trap input events (Mouse and Keyboard events)
      canvas = new Canvas();  
      canvas.setBounds(0, 0, WIDTH, HEIGHT);
      canvas.setIgnoreRepaint(true);
   
      panel.add(canvas);  // adds the canvas to the panel.
   
      // frame operations
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
      frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
      frame.setResizable(false);   //makes it so the frame cannot be resized
      frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!
      
      // sets up things so the screen displays images nicely.
      canvas.createBufferStrategy(2);
      bufferStrategy = canvas.getBufferStrategy();
      canvas.requestFocus();
      System.out.println("DONE graphic setup");
   
   }


	//paints things on the screen using bufferStrategy
	private void render() {
		Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();

		g.clearRect(0, 0, WIDTH, HEIGHT);

      //draws the images of the various celtics related things
		g.drawImage(backgroundPic, 0, 0, WIDTH, HEIGHT, null);
		g.drawImage(basketballguyPic, basketballPlayer.xpos, basketballPlayer.ypos, basketballPlayer.width, basketballPlayer.height, null);
		g.drawImage(basketballPic, basketball.xpos, basketball.ypos, basketball.width, basketball.height, null);
		g.drawImage(hoopPic, hoop.xpos, hoop.ypos, hoop.width, hoop.height, null);


		g.dispose();

		bufferStrategy.show();
	}
}