package MAIN;

import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import Audio.OutOfRangeException;
import Audio.SoundTrack;
import GameObjectHandler.CardHandler;
import Scores.ScoresInfo;



public class FirstState extends BasicGameState {

	public static final int ID = 1;
	public final float WIDTH, HEIGHT;
	private static CardHandler CH;
	private SoundTrack backgroundMusic;
	private boolean startMusic = true;
	private Image cursor;
	private Image background;
	private Point bgPoint1, bgPoint2;
	private static ScoresInfo scoresList;
	private  Input input;
	
	
	
	public FirstState(float _WIDTH, float _HEIGHT)
	{
		WIDTH = _WIDTH;
		HEIGHT = _HEIGHT;
		CH = new CardHandler(WIDTH, HEIGHT);
	}


	@Override //returns the state id for state switching
	public int getID() {
		return ID;
	}


	@Override //initialize the logic
	public void init( GameContainer container, StateBasedGame game ) throws SlickException
	{
		CH.init();
		   try{
			cursor = new Image("Content/ImageFiles/Cursor.png");
			background = new Image("Content/ImageFiles/starBG.jpg");
			
			// retrieve the scores list and print it for debugging
			scoresList=retrieveScores();
			System.out.println("scores:\n" + scoresList);
			
			
			}catch(SlickException e){}
		    bgPoint1 = new Point(0,0);
			bgPoint2 = new Point(0, (int)HEIGHT);
	} 


	@Override // update the cardHandler -> cardHandler.update() pls keep this method as clean as possible
	public void update( GameContainer container, StateBasedGame game, int delta ) throws SlickException
	{

	    input = container.getInput();
		
		//Update the CardHandler
		CH.update(container, game, delta);
		
		
		if(game.getCurrentStateID() == ID && startMusic)
		{
			   backgroundMusic = SoundTrack.TRACK_TWO;
			try {
				backgroundMusic.setVolume(0.3f);
				backgroundMusic.play();
			    } catch (OutOfRangeException e) {e.printStackTrace();}
			   finally{ startMusic = false;}
		}
		
		if(input.isKeyDown(Input.KEY_1))
		{
			startMusic = true;
			game.enterState(0);
		}
		
		//check for escape
		if(input.isKeyDown(Input.KEY_ESCAPE))
		{
			// add score to the list and save the list
			if (CH.getScoreObject() != null) {
				scoresList.addEntry(CH.getScoreObject());
				saveScores(scoresList);
			}
			container.exit();
		}
		
		//gameover
		if((int)CH.getTime().getTime() <= 0)
		{
			// add score to the list
			scoresList.addEntry(CH.getScoreObject());
			// save the scores list to the file
			saveScores(scoresList);
			game.enterState(2);
		}
		
		
		
		//update Background
		
		bgPoint1.y -= (int)((60 / CH.getTime().getTime()));
	    bgPoint2.y -= (int)(60 / (CH.getTime().getTime()));
		
		if(bgPoint1.y < -HEIGHT)
		{
		  bgPoint1.y = (int)HEIGHT;
		}
		if(bgPoint2.y < -HEIGHT)
		{
			bgPoint2.y = (int)HEIGHT;
		}
		
	} 


	@Override //Graphics g is your rendering component
	public void render( GameContainer container, StateBasedGame game, Graphics g ) throws SlickException 
	{ 

		//draw background
		background.draw(bgPoint1.x, bgPoint1.y,WIDTH, HEIGHT);
		background.draw(bgPoint2.x, bgPoint2.y,WIDTH, HEIGHT);
		

		//Render the Card Handler
		CH.render(container, game, g);
		// countdown
		g.drawString("" + CH.getTime(), WIDTH * 0.5f, HEIGHT * 0.10f);
		// score
		g.drawString("SCORE: " + CH.getScoreAmount(), WIDTH * 0.25f, HEIGHT * 0.15f);
		cursor.draw(Mouse.getX(), HEIGHT-Mouse.getY());
		
	}
	
	/**
	 * Retrieve the scores list. </br>
	 * ScoresInfos object are serializable and are stored in the
	 * file 'Content/Backups/scores.ser'.</br>
	 * If the file does not exist, a new ScoresInfo object is instantiated,
	 * since it is the first time the game runs locally. It returns
	 * this new object.</br>
	 * If the file exists, it retrieves and returns the ScoresInfo object
	 *  saved in it.
	 * @return ScoresInfo object that contains the scores list
	 */
	public static ScoresInfo retrieveScores() {
		ScoresInfo list = null;
		
	    try {
	      File f = new File("Content/Backups/scores.ser");
	      if (f.exists()) {
	    	  FileInputStream fichier = new FileInputStream(f);
		      ObjectInputStream ois = new ObjectInputStream(fichier);
		      list = (ScoresInfo) ois.readObject();
	      } else {
	    	  list = new ScoresInfo();
	      }
	      
	    } 
	    catch (java.io.IOException e) {
	      e.printStackTrace();
	    }
	    catch (ClassNotFoundException e) {
	      e.printStackTrace();
	    }
	    
	    return list;
	}
	
	/**
	 * Save the scores list into the 'Content/Backups/scores.ser' file
	 * (uses serialization)
	 * @param list ScoresInfo object that stores the scores list
	 */
	public static void saveScores(ScoresInfo list) {
	    try {
	        FileOutputStream fichier 
	           = new FileOutputStream("Content/Backups/scores.ser");
	        ObjectOutputStream oos = new ObjectOutputStream(fichier);
	        oos.writeObject(list);
	        oos.flush();
	        oos.close();
	      }
	      catch (java.io.IOException e) {
	        e.printStackTrace();
	      }
	}
	
	public ScoresInfo getScore()
	{
		return scoresList;
	}
}

