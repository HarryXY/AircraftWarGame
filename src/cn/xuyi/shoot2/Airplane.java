package cn.xuyi.shoot2;

import java.awt.image.BufferedImage;
import java.util.Random;

public class Airplane extends FlyingObject implements Enemy{
	int speed=2;
		
	public Airplane(){
		image = ShootGame.airplane;
		width = image.getWidth();
		height = image.getHeight();
		Random rand = new Random();
		x = rand.nextInt(ShootGame.WIDTH-this.width);
		y = -this.height;
		//y=100;
	}

	@Override
	public int getScore() {
		// TODO Auto-generated method stub
		return 5;
	}

	@Override
	public void step() {
		y+=speed;;		
	}

	@Override
	public boolean outOfBounds() {
		return this.y>=ShootGame.HEIGHT;
	}


}
