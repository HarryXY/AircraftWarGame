package cn.xuyi.shoot2;

import java.awt.image.BufferedImage;
import java.util.Random;

public class Bee  extends FlyingObject implements Award{
	int xSpeed;
	int ySpeed;
	
	public Bee(){
		image = ShootGame.bee;
		width = image.getWidth();
		height = image.getHeight();
		Random rand = new Random();
		x = rand.nextInt(ShootGame.WIDTH-this.width);
		y = -this.height;
		//y=200;
		xSpeed = 1;
		ySpeed = 2;
	}

	@Override
	public int getType() {
		Random rand = new Random();
		int type = rand.nextInt(2);
		if(type==0){
			return DOUBLE_FIRE;
		}else{
			return LIFE;
		}
		
	}

	@Override
	public void step() {
		x+=xSpeed;
		y+=ySpeed;
		if(x>ShootGame.WIDTH-this.width){
			xSpeed=-1;
		}
		if(x<0){
			xSpeed=1;
		}
		
	}
	public boolean outOfBounds() {
		return this.y>=ShootGame.HEIGHT;
	}
	
}
