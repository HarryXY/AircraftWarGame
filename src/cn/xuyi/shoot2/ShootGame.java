package cn.xuyi.shoot2;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;



public class ShootGame extends JPanel{
	public static final int WIDTH = 400;
	public static final int HEIGHT = 654;
	
	public static BufferedImage background;  
	public static BufferedImage start;  
	public static BufferedImage pause; 
	public static BufferedImage gameover;  
	public static BufferedImage airplane; 
	public static BufferedImage bee; 
	public static BufferedImage bullet;  
	public static BufferedImage hero0;
	public static BufferedImage hero1; 
	
	private Hero hero = new Hero();
	private FlyingObject[] flyings = new FlyingObject[]{};
	private Bullet[] bullets = new Bullet[]{};	
	
	private int state;
	private static final int START = 0;
	private static final int RUNNING = 1;
	private static final int PAUSE = 2;
	private static final int GAME_OVER = 3;
	
	private int score=0;
	
	static{
		try {
			background = ImageIO.read(ShootGame.class.getResource("background.png"));
			start = ImageIO.read(ShootGame.class.getResource("start.png"));
			pause = ImageIO.read(ShootGame.class.getResource("pause.png"));
			gameover = ImageIO.read(ShootGame.class.getResource("gameover.png"));
			airplane = ImageIO.read(ShootGame.class.getResource("airplane.png"));
			bee = ImageIO.read(ShootGame.class.getResource("bee.png"));
			bullet = ImageIO.read(ShootGame.class.getResource("bullet.png"));
			hero0 = ImageIO.read(ShootGame.class.getResource("hero0.png"));
			hero1 = ImageIO.read(ShootGame.class.getResource("hero1.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void paintBullets(Graphics g){
		for(int i=0; i<bullets.length; i++){
			Bullet b = bullets[i];
			g.drawImage(b.image, b.x, b.y, null);
		}	
	}
	
	public void paintFlyingObjects(Graphics g){
		for(int i=0; i<flyings.length; i++){
			FlyingObject f = flyings[i];
			g.drawImage(f.image, f.x, f.y, null);
		}		
	}
	
	public void paintHero(Graphics g){
		g.drawImage(hero.image,hero.x, hero.y, null);
	}
	
	public void paintScoreAndLife(Graphics g){
		g.setColor(new Color(0xFF0000));
		g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,24));
		g.drawString("SCORE:"+score, 10, 25);
		g.drawString("LIFE:"+hero.life, 10, 60);
	}
	
	public void paintState(Graphics g){
		switch(state){
		case START:
			g.drawImage(start, 0, 0, null);
			break;
		case PAUSE:
			g.drawImage(pause, 0, 0, null);
			break;
		case GAME_OVER:
			g.drawImage(gameover, 0, 0, null);
			break;
		}
	}
	
	public void paint(Graphics g){
		g.drawImage(background,0,0,null);
		paintHero(g);
		paintFlyingObjects(g);
		paintBullets(g);
		paintScoreAndLife(g);
		paintState(g);
	}

	public FlyingObject nextOne(){
		Random rand = new Random();
		int type = rand.nextInt(20);
		if(type<5){
			return new Bee();
		}else{
			return new Airplane();
		}
	}
	
	int flyEnteredIndex =0;
	public void enterAction(){
		flyEnteredIndex++;
		if(flyEnteredIndex%40==0){
			FlyingObject obj = nextOne();
			flyings = Arrays.copyOf(flyings, flyings.length+1);
			flyings[flyings.length-1]=obj;
		}
	}
	
	public void stepAction(){
		hero.step();
		for(int i=0; i<flyings.length; i++){
			FlyingObject f = flyings[i];
			f.step();
		}
		for(int i=0; i<bullets.length; i++){
			Bullet b = bullets[i];
			b.step();
		}	
	}
	
	int shootIndex = 0;
	public void shootAction(){
		shootIndex++;
		if(shootIndex%30==0){
			Bullet[] bs = hero.shoot();
			bullets = Arrays.copyOf(bullets, bullets.length+bs.length);
			System.arraycopy(bs, 0, bullets, bullets.length-bs.length, bs.length);
		}
	}
	
	public void outOfBoundsAction(){
		hero.outOfBounds();
		
		FlyingObject[] flyingLives = new FlyingObject[flyings.length];
		int index =0;
		for(int i=0; i<flyings.length; i++){
			FlyingObject f = flyings[i];
			if(!f.outOfBounds()){
				flyingLives[index]=f;
				index ++;
			}
		}
		flyings = Arrays.copyOf(flyingLives, index);
		
		Bullet[] bulletLives = new Bullet[bullets.length];
		index =0;
		for(int i=0; i<bullets.length; i++){
			Bullet b = bullets[i];
			if(!b.outOfBounds()){
				bulletLives[index]=b;
				index ++;
			}
		}
		bullets = Arrays.copyOf(bulletLives, index);
	}
	
	public void bangAction(){
		for(int i=0; i<bullets.length; i++){
			Bullet b =bullets[i];
			bang(b);
		
		}
	}
		
	public void bang(Bullet b){
		int index = -1; //��ײ���˵��±�
		for(int i=0;i<flyings.length;i++){ //�������е���
			FlyingObject f = flyings[i]; //��ȡÿһ������
			if(f.shootBy(b)){ //ײ����
				index = i; //��¼��ײ���˵��±�
				break; //����ĵ��˲��ٱȽ�
			}
		}
		if(index!=-1){ //��ײ�ϵ�
			FlyingObject one = flyings[index]; //��ȡ��ײ�ĵ��˶���
			if(one instanceof Enemy){ //�ǵ���
				Enemy e = (Enemy)one; //ǿתΪ����
				score += e.getScore(); //�������
			}
			if(one instanceof Award){ //�ǽ���
				Award a = (Award)one; //ǿתΪ����
				int type = a.getType(); //��ȡ��������
				switch(type){ //��ݽ������͵Ĳ�ͬ����ȡ��ͬ�Ķ���
				case Award.DOUBLE_FIRE:   //����������Ϊ����ֵ
					hero.doubleFire+=40; //Ӣ�ۻ�������
					break;
				case Award.LIFE: //����������Ϊ��
					hero.life++; //Ӣ�ۻ�����
					break;
				}
			}
			//����ײ���˶��������������һ��Ԫ�ؽ���
			FlyingObject t = flyings[index];
			flyings[index] = flyings[flyings.length-1];
			flyings[flyings.length-1] = t;
			//����(ȥ�����һ��Ԫ�أ�����ײ�ĵ��˶���)
			flyings = Arrays.copyOf(flyings,flyings.length-1);
		}
	}
	
	public void checkGameOverAction(){
		if(isGemeOver()==true){
			state = GAME_OVER;
		}
	}
	
	public boolean isGemeOver(){
		for(int i=0; i<flyings.length; i++){
			FlyingObject f = flyings[i];
			int index = -1;
			if(hero.hit(f)){
				index = i;
				FlyingObject t = flyings[index];
				flyings[index] = flyings[flyings.length-1];
				flyings[flyings.length-1] = t;
				flyings = Arrays.copyOf(flyings, flyings.length-1);
				hero.life--;
				hero.doubleFire=0;
			}
		}	
		return hero.getLife()<=0;
	}
	
	public void action(){
		MouseAdapter l = new MouseAdapter(){
			public void mouseMoved(MouseEvent e){
				if(state == RUNNING){
					int x = e.getX();
					int y = e.getY();
					hero.moveTo(x,y);	
				}						
			}
			
			public void mouseEntered(MouseEvent e){
				if(state == PAUSE){
					state = RUNNING;
				}
			}
			
			public void mouseExited(MouseEvent e){
				if(state == RUNNING){
					state = PAUSE;
				}
			}
			
			public void mouseClicked(MouseEvent e){
				switch(state){
				case START:
					state = RUNNING;
					break;
				case GAME_OVER:
					flyings = new FlyingObject[0]; // 清空飞行物
					bullets = new Bullet[0]; // 清空子弹
					hero = new Hero(); // 重新创建英雄机
					score = 0; // 清空成绩
					state = START; // 状态设置为启动
					break;
				}
			}
		};
		
		this.addMouseListener(l);
		this.addMouseMotionListener(l);
		
		
		Timer timer = new Timer();
		int intervel = 10;
		timer.schedule(new TimerTask(){
			public void run() {
				if(state == RUNNING){
					enterAction();
					stepAction();
					shootAction();
					outOfBoundsAction();
					bangAction();
					checkGameOverAction();
				}				
				repaint();
			}
			
		}, intervel, intervel);
	}
	
	public static void main(String[] args) {
		System.out.print("main");
		JFrame frame = new JFrame();
		ShootGame game = new ShootGame();
		frame.add(game);
		
		frame.setSize(WIDTH, HEIGHT); 
		frame.setAlwaysOnTop(true); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
		frame.setLocationRelativeTo(null);  
		frame.setVisible(true);  
		
		game.action();

	}
}
