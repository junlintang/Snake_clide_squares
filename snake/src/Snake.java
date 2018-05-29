import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.awt.Graphics;

public class Snake {
	public static  int speedX = 10; // 静态全局变量速度---------可以作为扩张来设置级别，速度快的话比较难
	public static int count = 0;
	public static final int width = 35, length = 35; //蛇每一部分的全局大小，具有不可改变性
	private Direction direction = Direction.STOP; // 初始化状态为静止
//	private Direction Kdirection = Direction.U; // 初始化方向为向上
	SnakeClient sc;

	private boolean good;
	private int x, y;
	private int oldX, oldY;
	public boolean live = true; // 初始化为活着
	public int len = 5; // 初始生命值,贪吃蛇的长度为5
//	private snakeBlock[] sBlock;
	List<snakeBlock> sBlock = new ArrayList<snakeBlock>();

	private static Random r = new Random();

	private boolean bL = false, bU = false, bR = false, bD = false;
	

	public Snake(int x, int y) {// Snake的构造函数1
		this.x = x;
		this.y = y;
		this.oldX = x;
		this.oldY = y;
	}

	public Snake(int x, int y, Direction dir, SnakeClient sc) {// Snake的构造函数2
		this(x, y);
		this.direction = dir;
		this.sc = sc;
		this.initiate();
	}
	
	public void initiate(){           //初始化
		for(int i=0;i<len;i++){
			sBlock.add(new snakeBlock(x,y+i*20,sc));
//			System.out.println(sBlock.get(i).x+" "+sBlock.get(i).y);
		}
	}

	public void draw(Graphics g) {
		g.drawString(String.valueOf(len), x+6, y);

		move(g);   //调用move函数
	}

	void move(Graphics g) {

		this.oldX = x;
		this.oldY = y;

		switch (direction) {  //选择移动方向
		case L:
			x -= speedX;
			break;
		case R:
			x += speedX;
			break;
		case STOP:
			break;
		}

		if (x < 0)
			x = 0;
		if (y < 40)      //防止走出规定区域
			y = 40;
		if (x + Snake.width > SnakeClient.Fram_width)  //超过区域则恢复到边界
			x = SnakeClient.Fram_width - Snake.width;
		if (y + Snake.length > SnakeClient.Fram_length)
			y = SnakeClient.Fram_length - Snake.length;
		int tempx = x;
		for(int i=0;i<len;i++){                //后面的sblock得到前面的sblock的横坐标，实现连续移动
			snakeBlock si = sBlock.get(i);
			si.changexy(tempx);
			tempx = si.oldx;
			si.draw(g);
		}

	}



	public void keyPressed(KeyEvent e) {  //接受键盘事件
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_RIGHT: //监听向右键
			bR = true;
			break;
			
		case KeyEvent.VK_LEFT://监听向左键
			bL = true;
			break;
		
		}
		decideDirection();//调用函数确定移动方向
	}

	void decideDirection() {
		if (!bL && !bU && bR && !bD)  //向右移动
			direction = Direction.R;

		else if (bL && !bU && !bR && !bD)   //向左移
			direction = Direction.L;

		else if (!bL && !bU && !bR && !bD)
			direction = Direction.STOP;  //没有按键，就保持不动
	}

	public void keyReleased(KeyEvent e) {  //键盘释放监听
		int key = e.getKeyCode();
		switch (key) {
			
		case KeyEvent.VK_RIGHT:
			bR = false;
			break;
		
		case KeyEvent.VK_LEFT:
			bL = false;
			break;
			

		}
		decideDirection();  //释放键盘后确定移动方向
	}


	public Rectangle getRect() {
		return new Rectangle(x, y, width, length);
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}