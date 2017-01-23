package cn.xuyi.shoot2;

import java.awt.image.BufferedImage;

public class Hero  extends FlyingObject{
	int life;
	int doubleFire;
	BufferedImage[] images;
	int index=0;
	
	public Hero(){
		image = ShootGame.hero0;
		width = image.getWidth();
		height = image.getHeight();
		x=150;
		y=400;
		life=3;
		doubleFire=0;
		images = new BufferedImage[]{ShootGame.hero0,ShootGame.hero1};
	}
	
	public void step(){
		
		image = images[index++/10%images.length];
	}
	
	public Bullet[] shoot(){
		int xStep = this.width/2;
		int yStep = 20;
		if(doubleFire==0){
			Bullet[] bs = new Bullet[1];
			bs[0]= new Bullet(x+xStep, y-yStep);
			return bs;
			
		}else {
			xStep = this.width/4;
			yStep = 20;
			Bullet[] bs = new Bullet[2];
			bs[0] = new Bullet(x+xStep, y-yStep);
			bs[1] = new Bullet(x+3*xStep, y-yStep);
			doubleFire -= 2;
			return bs;
		}
		
	}
	
	public boolean outOfBounds() {
		return false;
	}
	
	public void moveTo(int x, int y){
		this.x=x-this.width/2;
		this.y=y-this.height/2;
	}
	
	public boolean hit(FlyingObject other){
		
		int x1 = other.x - this.width/2;                 //x坐标最小距离
		int x2 = other.x + this.width/2 + other.width;   //x坐标最大距离
		int y1 = other.y - this.height/2;                //y坐标最小距离
		int y2 = other.y + this.height/2 + other.height; //y坐标最大距离
	
		int herox = this.x + this.width/2;               //英雄机x坐标中心点距离
		int heroy = this.y + this.height/2;              //英雄机y坐标中心点距离
		
		return herox>x1 && herox<x2 && heroy>y1 && heroy<y2;   //区间范围内为撞上了
	}
	
	public int getLife(){
		return life;
	}

}
