package cn.xuyi.shoot2;

public class Bullet extends FlyingObject {
	int speed;
	
	public Bullet(int x, int y){
		image = ShootGame.bullet;
		width = image.getWidth();
		height = image.getHeight();
		speed=2;
		this.x=x;
		this.y=y;
	}

	@Override
	public void step() {
		y-=speed;		
	}
	
	public boolean outOfBounds() {
		return this.y<=-this.height;
	}

}
