import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Random;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class barrier {
//	public static final int width = 20; //设置障碍物的固定参数
//	public static final int length = 20;
	public static  int speedY = 10; // 障碍物的全局静态速度
	public boolean live = true;
	int x, y;
	int num;

	SnakeClient sc;
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] bImags = null;
	static {
		bImags = new Image[] { // 储存barrier的图片
		tk.getImage(barrier.class.getResource("Images/1.png")), 
		};
	}

	public barrier(int x, int y,int num, SnakeClient sc) { // 构造函数
		this.x = x;
		this.y = y;
		this.num = num;
		this.sc = sc; // 获得界面控制
	}


	public void draw(Graphics g) {		
		g.drawImage(bImags[0], x, y,100,100,null);   //画障碍物坐标x,y,长宽各100        
//		g.drawRect(x,y,100,100);
		g.drawString(String.valueOf(num),x+50,y+50);  //障碍物中间写数字
		y += speedY;
		if(y>SnakeClient.Fram_length){
			live = false;
//			System.out.println("Successfully set live false");
		}
//		move(); // 调用子弹move()函数
	}
	
/*	private void move(){
		y += speedY;
		if(y>SnakeClient.Fram_length){
			live = false;
		}
	}*/

}
