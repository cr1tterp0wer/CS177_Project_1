package MAIN;

import java.awt.Point;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import Animation.Effects.Text;

public class GameOver extends BasicGameState{

	public static final int ID = 3;
	public final float WIDTH, HEIGHT;
	private Image background;
	private Input input;
    private Text text;

	public GameOver(float _WIDTH, float _HEIGHT)
	{
		WIDTH = _WIDTH;
		HEIGHT = _HEIGHT;

	}


	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)throws SlickException 
	{

		try{
			background = new Image("Content/ImageFiles/GameOver.png");
			text = new Text();
		}catch(SlickException e){}

	}
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException
	{
		input = container.getInput();

		if(input.isKeyPressed(Input.KEY_ESCAPE))
		{
			container.exit();
		}

		if(input.isKeyPressed(Input.KEY_ENTER))
		{
			game.enterState(0);
		}
	}



	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException
	{

		background.draw(0, 0, WIDTH, HEIGHT);
		
		text.draw("Press ESC to Escape", WIDTH * 0.25f,  HEIGHT * 0.75f, WIDTH * 0.03f, WIDTH * 0.03f, Color.gray );
	//	g.drawString("Press ESC to ESCAPE", WIDTH * 0.25f, HEIGHT * 0.75f);
		
		text.draw("Press ENTER to CONTINUE",WIDTH * 0.25f,HEIGHT * 0.65f, WIDTH * 0.03f, WIDTH * 0.03f,   Color.gray );
	//	g.drawString("Press ENTER to CONTINUE", WIDTH * 0.25f, HEIGHT * 0.65f);
	}

	
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}
}
