package cn.xuyi.shoot;
import java.awt.image.BufferedImage;



/** ������ */
public abstract class FlyingObject {
	protected BufferedImage image; //ͼƬ
	protected int width; //��
	protected int height; //��
	protected int x; //x���
	protected int y; //y���
	
	public abstract void step();
	
	public abstract boolean outOfBounds();
	
	public abstract boolean shootBy();
	
	public boolean shootBy(Bullet bullet){
		int x1 = this.x;             //x1:�л��x
		int x2 = this.x+this.width;  //x2:�л��x+�л�Ŀ�
		int y1 = this.y;             //y1:�л��y
		int y2 = this.y+this.height; //y2:�л��y+�л�ĸ�
		int x = bullet.x;            //x:�ӵ���x
		int y = bullet.y;            //y:�ӵ���y
		
		return x>x1 && x<x2
			   &&
			   y>y1 && y<y2; //x��x1��x2֮�䣬���ң�y��y1��y2֮�䣬��Ϊײ����
	}
	
}













