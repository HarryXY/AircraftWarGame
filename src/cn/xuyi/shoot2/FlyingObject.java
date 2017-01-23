package cn.xuyi.shoot2;

import java.awt.image.BufferedImage;

public abstract class FlyingObject {
	protected int width;
	protected int height;
	protected int x;
	protected int y;
	protected BufferedImage image;
	
	public abstract void step();
	
	public abstract boolean outOfBounds();
	
	public  boolean shootBy(Bullet bullet){
		/*
		if(bullet.x>this.x && bullet.x<this.x+this.width
				&& bullet.y>this.y && bullet.y<this.y+this.height){
			return true;
		}else{
			return false;
		}
		*/
		int x1 = this.x;             //x1:�л��x
		int x2 = this.x+this.width;  //x2:�л��x+�л�Ŀ�
		int y1 = this.y;             //y1:�л��y
		int y2 = this.y+this.height; //y2:�л��y+�л�ĸ�
		int x = bullet.x;            //x:�ӵ���x
		int y = bullet.y;            //y:�ӵ���y
		
		return x>x1 && x<x2
			   &&
			   y>y1 && y<y2;
	}
}
