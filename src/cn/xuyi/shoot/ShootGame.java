package cn.xuyi.shoot;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ShootGame extends JPanel{	
	public static final int WIDTH=400;
	public static final int HEIGHT=654;
	
	public static BufferedImage background;
	public static BufferedImage airplane;
	public static BufferedImage bee;
	public static BufferedImage bullet;
	public static BufferedImage hero0;
	public static BufferedImage hero1;
	public static BufferedImage gameover;
	public static BufferedImage pause;
	public static BufferedImage start;
	
	private Hero hero = new Hero();
	private FlyingObject[] flyings = {};
	private Bullet[] bullets = {};
	
	static{
		try {
			background = ImageIO.read(ShootGame.class.getResource("background.png"));
			airplane = ImageIO.read(ShootGame.class.getResource("airplane.png"));
			bee = ImageIO.read(ShootGame.class.getResource("bee.png"));
			bullet = ImageIO.read(ShootGame.class.getResource("bullet.png"));
			hero0 = ImageIO.read(ShootGame.class.getResource("hero0.png"));
			hero1 = ImageIO.read(ShootGame.class.getResource("hero1.png"));
			gameover = ImageIO.read(ShootGame.class.getResource("gameover.png"));
			pause = ImageIO.read(ShootGame.class.getResource("pause.png"));
			start = ImageIO.read(ShootGame.class.getResource("start.png"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void paintHero(Graphics g){
		g.drawImage(hero.image, hero.x, hero.y, null);
	}
	
	public void paintFlyingObject(Graphics g){
		for(int i=0; i<flyings.length; i++){
			FlyingObject f = flyings[i];
			g.drawImage(f.image, f.x, f.y, null);
		}
	}
	
	public void paintBullet(Graphics g){
		for(int i=0; i<bullets.length; i++){
			Bullet b = bullets[i];
			g.drawImage(b.image, b.x, b.y, null);
		}
	}
	
	public void paint(Graphics g){
		g.drawImage(background, 0, 0, null);
		paintHero(g);
		paintFlyingObject(g);
		paintBullet(g);
		
	}
	
	public FlyingObject nextOne(){
		Random rand = new Random();
		int type = rand.nextInt(20);
		if(type==0){
			return new Bee();
		}else{
			return new Airplane();
		}
	}
	
	int time = 0;
	public void enterAction(){
		time++;
		if(time%40==0){
			FlyingObject one = nextOne();
			flyings=Arrays.copyOf(flyings, flyings.length+1);
			flyings[flyings.length-1]=one;
		}		
	}
	
	public void stepAction(){
		hero.step();
		for(int i=0; i<flyings.length; i++){
			flyings[i].step();
		}
		for(int i=0; i<bullets.length; i++){
			bullets[i].step();
		}
	}
	
	public void shootAction(){
		Bullet[] bs = hero.shoot();
		bullets = Arrays.copyOf(bullets, bullets.length+bs.length);
		System.arraycopy(bs, 0, bullets, bullets.length-bs.length, bs.length);
	}
	
	public void outOfBoundsAction(){
		FlyingObject[] flyingLives = new FlyingObject[flyings.length];
		int index =0;
		for(int i=0; i<flyings.length; i++){
			FlyingObject f = flyings[i];
			if(!f.outOfBounds()){
				flyingLives[index] = f;
				index++;				
			}
		}
		flyings = Arrays.copyOf(flyingLives, index);
		
		Bullet[] bulletsLives = new Bullet[bullets.length];
		index =0;
		for(int i=0; i<bullets.length; i++){
			Bullet b = bullets[i];
			if(!b.outOfBounds()){
				bulletsLives[index] = b;
				index++;				
			}
		}
		bullets = Arrays.copyOf(bulletsLives, index);
		
	}
	
	int score =0;
	public void bang(Bullet b){
		int index=-1;
		for(int i=0; i<flyings.length; i++){
			FlyingObject f = flyings[i];
			if(f.shootBy(b)){
				index = i;
				break;
			}
		}
		if(index!=-1){
			FlyingObject one = flyings[index];
			if(one instanceof Enemy){
				Enemy e = (Enemy)one;
				score += e.getScore();
			}
			if(one instanceof Award){
				Award a = (Award)one;
				int type =a.getType();
				switch(type){
				case Award.DOUBLE_FIRE:
					hero.addDoubleFire();
					break;
				case Award.LIFE:
					hero.addLife();
					break;
				}
			}
			FlyingObject t = flyings[index];
			flyings[index]=flyings[flyings.length-1];
			flyings[flyings.length-1]=t;
			flyings = Arrays.copyOf(flyings, flyings.length-1);
		}
	}
	
	public void bangAction(){
		for(int i=0; i<bullets.length; i++){
			bang(bullets[i]);
		}		
	}
	
	public void action(){
		Timer timer = new Timer();
		int intervel =10;
		timer.schedule(new TimerTask(){
			public void run(){
				enterAction();
				stepAction();
				shootAction();
				outOfBoundsAction();
				bangAction();
			}
		}, intervel, intervel);		
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		ShootGame game = new ShootGame();
		frame.add(game);
		
		frame.setSize(WIDTH, HEIGHT); //���ô��ڴ�С
		frame.setAlwaysOnTop(true); //����һֱ����
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //����Ĭ�Ϲرղ���(���ڹر����˳�)
		frame.setLocationRelativeTo(null); //���ô��ڳ�ʼλ��(������ʾ)
		frame.setVisible(true); //1.���ô��ڿɼ�  2.�������paint()
		
		game.action();
		

	}

}
