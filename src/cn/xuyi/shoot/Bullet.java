package cn.xuyi.shoot;

/** �ӵ�: �Ƿ����� */
public class Bullet extends FlyingObject {
	private int speed = 3; //�߲��Ĳ���
	/** ���췽��  x:�ӵ���x���  y:�ӵ���y��� */
	public Bullet(int x,int y){
		image = ShootGame.bullet; //ͼƬ
		width = image.getWidth(); //��
		height = image.getHeight(); //��
		this.x = x; //x:���̶���ֵ������������궯��
		this.y = y; //y:���̶���ֵ������������궯��
	}

	public void step(){
		y-=speed;
	}
	
	public boolean outOfBounds(){
		return y <= -this.height;
	}

	
}









