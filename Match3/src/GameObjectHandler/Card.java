package GameObjectHandler;


	import org.lwjgl.input.Mouse;
    import org.newdawn.slick.GameContainer;
    import org.newdawn.slick.Graphics;
    import org.newdawn.slick.Input;
    import org.newdawn.slick.SlickException;
    import org.newdawn.slick.geom.Rectangle;
    import org.newdawn.slick.state.StateBasedGame;
    import org.newdawn.slick.Image;

	public class Card {
		
		///<summary>
		/// Holds index locations of where it exists in the CH.Board[][],
		/// Has a bounding Rectangle to check for mouse intersections.
		/// Has an cardType to inform the CardHandler what type of image
		/// should be displayed within its bounding Rectangle. {This can
		/// be swapped out for an animation object}
		///<summary>
		
		int x, y, cardType;
		double drawX, drawY;
		private Rectangle boundingRectangle;
		
		public Card(int _x, int _y, int _cardType)
		{
			x = _x;
			y = _y;
			drawX = (double)_x;
			drawY = (double)_y;
			cardType = _cardType;
			boundingRectangle = new Rectangle( y * 50, x * 50, 50, 50);
		}
		public Card clone(){
			
			Card newCard = new Card(this.x, this.y, this.cardType);
			
			return newCard;
		}
				
		public int getCardType(){return cardType;}		
		public Rectangle getBoundingRectangle(){ return boundingRectangle;}
		public void setCardType(byte _value){cardType = _value;}
		public void setBoundingRectangle(Rectangle _value){ boundingRectangle = _value;}
		
	}
