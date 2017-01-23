package cn.xuyi.shoot;

import java.awt.image.BufferedImage;
import java.util.Random;

/** Ӣ�ۻ�: �Ƿ����� */
public class Hero extends FlyingObject {
	private int life; // ��
	private int doubleFire; // ����ֵ
	private BufferedImage[] images; // ͼƬ����(�л�ͼƬ)
	private int index; //Э��ͼƬ���л�

	/** ���췽�� */
	public Hero(){
		image = ShootGame.hero0; //ͼƬ
		width = image.getWidth(); //��
		height = image.getHeight(); //��
		x = 150; //x:�̶������
		y = 400; //y:�̶������
		life = 3; //��ʼ����Ϊ3
		doubleFire = 0; //����ֵĬ��Ϊ0(��������)
		images = new BufferedImage[]{ShootGame.hero0,ShootGame.hero1}; //��ʼͼƬ����hero0��hero1
		index = 0; //Э���л�(��ʼΪ0)
	}
	
	public void step(){	
		image = images[index++/10%images.length];
	}
	
	public Bullet[] shoot(){
		int xStep = this.width/4;
		int yStep = 20;
		if(doubleFire>0){
			Bullet[] bs = new Bullet[2];
			bs[0] = new Bullet(this.x+xStep, this.y-yStep);
			bs[1] = new Bullet(this.x+3*xStep, this.y-yStep);
			doubleFire -=2;
			return bs;
		}else{
			Bullet[] bs = new Bullet[1];
			bs[0] = new Bullet(this.x+2*xStep, this.y-yStep);			
			return bs;
		}
	}
	
	public boolean outOfBounds(){
		return false;
	}
	
	public void addDoubleFire(){
		doubleFire+=40;
	}
	
	public void addLife(){
		life++;
	}

	
}
















