package cn.xuyi.shoot;

import java.util.Random;

/** С�۷�:�Ƿ����Ҳ�ǽ��� */
public class Bee extends FlyingObject implements Award {
	private int xSpeed = 1; //x�߲��Ĳ���
	private int ySpeed = 2; //y�߲��Ĳ���
	private int awardType; //���������
	/** ���췽�� */
	public Bee(){
		image = ShootGame.bee; //ͼƬ
		width = image.getWidth(); //��
		height = image.getHeight(); //��
		Random rand = new Random(); //��������
		x = rand.nextInt(ShootGame.WIDTH-this.width); //x:0��(��Ļ��-�۷��)֮��������
		y = 200; //y:�����۷�ĸ�
		awardType = rand.nextInt(2); //0��1֮��������
	}
	
	/** ��дgetType() */
	public int getType(){
		return awardType; //���ؽ�������(0��1)
	}
	
	public void step(){
		x+=xSpeed;
		y+=ySpeed;
		if(x<=0){
			xSpeed=xSpeed;
		}
		if(x>=ShootGame.WIDTH-this.width){
			xSpeed=-xSpeed;
		}
	}
	
	public boolean outOfBounds(){
		return y>=ShootGame.HEIGHT;
	}

	
}











