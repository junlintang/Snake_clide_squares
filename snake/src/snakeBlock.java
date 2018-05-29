import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class snakeBlock {
	public static final int width = 20; //设置障碍物的固定参数
	public static final int length = 20;
	public int x, y;
	int oldx,oldy;

	SnakeClient sc;
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] sImags = null;
	static {
		sImags = new Image[] { // 储存snakeBlock的图片
		tk.getImage(snakeBlock.class.getResource("Images/2.png")), };
	}

	public snakeBlock(int x, int y, SnakeClient sc) { // 构造函数
		this.x = x;
		this.y = y;
		this.sc = sc; // 获得界面控制
	}
	
	public void changexy(int oldx){//改变当前sblock的横坐标，并记录下旧的横坐标
		this.oldx = x;
		this.x = oldx;
	}

	public void draw(Graphics g) {// 画snakeBlock
		g.drawImage(sImags[0], x, y,20,20, null);
	}

}
