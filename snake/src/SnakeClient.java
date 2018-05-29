import java.util.ArrayList;
import java.util.Random;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.JOptionPane;



public class SnakeClient extends Frame implements ActionListener {
	public static final int Fram_width = 500; // 静态全局窗口大小
	public static final int Fram_length = 800; 
	public static boolean printable = true;//是否刷新画板的标志量
	public static int time = 0; 
	MenuBar jmb = null;
	Menu jm1 = null, jm2 = null, jm3 = null, jm4 = null;
	MenuItem jmi1 = null, jmi2 = null, jmi3 = null, jmi4 = null, jmi5 = null,
			jmi6 = null, jmi7 = null, jmi8 = null, jmi9 = null;
	Snake homeSnake =  new Snake(300, 600, Direction.STOP, this);
	Image screenImage = null;
	Random rand =new Random(25);
	List<barrier> bs = new ArrayList<barrier>();
	public void update(Graphics g) {                 //不断执行update函数来让对象动起来

		screenImage = this.createImage(Fram_width, Fram_length);

		Graphics gps = screenImage.getGraphics();
		Color c = gps.getColor();
		gps.setColor(Color.GRAY);
		gps.fillRect(0, 0, Fram_width, Fram_length);
		gps.setColor(c);
		framPaint(gps);
		g.drawImage(screenImage, 0, 0, null);
	}
	
	public void framPaint(Graphics g) {             //update内的函数，执行update时也要执行
		time +=1;
		if(homeSnake.isLive()){
			//单位时间对100取余等于0则建立5个一排的障碍物，对100取余等于50则建立不多于3个一排的障碍物
			if(time%100==0){
				for(int i=0;i<5;i++){
					bs.add(new barrier(100*i,50, rand.nextInt(5),this));
				}
			}
			if((time+50)%100==0){
				int[] pos =new int[3];
				for(int i=0;i<3;i++){
					pos[i] = rand.nextInt(5);
					for(int j=0;j<i;j++){
						if(pos[i]==pos[j]) pos[i]=-1;
					}
					if(pos[i]>=0)
					bs.add(new barrier(100*pos[i],50,rand.nextInt(100),this));
				}
			}
			
			//画出所有的障碍物及蛇
//			System.out.println("----------------------------------------");
			System.out.println(bs.size());
			for(int i=0;i<bs.size();i++){
				bs.get(i).draw(g);
//				System.out.println(bs.get(i).x+"  "+bs.get(i).y);
			}
			int last = bs.size();
			for(int i=0;i<last;i++){
				barrier bi = bs.get(i);
				if (!bi.live) {
					bs.remove(this);
//					System.out.println("Successfully remove");
					last -= 1;
				}
			}
			homeSnake.draw(g);
			int x = homeSnake.getX();
			int y = homeSnake.getY();
			
			//检查所有的障碍物和蛇是否相撞
			for(int i=0;i<bs.size();i++){
				barrier xi = bs.get(i);
				if(x>=xi.x&&x<xi.x+100&&y<=xi.y+100&&y>xi.y){
					hitBarrier(g,xi,homeSnake);
				}
			}
		}
		else{
			printable = false;
			g.drawString("Game over! ", 220, 300);
		}
	}
	
	public SnakeClient() {
		// printable = false;
		// 创建菜单及菜单选项
		jmb = new MenuBar();
		jm1 = new Menu("游戏");
		jm2 = new Menu("暂停/继续");
		jm3 = new Menu("帮助");
		jm4 = new Menu("游戏级别");
		jm1.setFont(new Font("TimesRoman", Font.BOLD, 15));// 设置菜单显示的字体
		jm2.setFont(new Font("TimesRoman", Font.BOLD, 15));// 设置菜单显示的字体
		jm3.setFont(new Font("TimesRoman", Font.BOLD, 15));// 设置菜单显示的字体
		jm4.setFont(new Font("TimesRoman", Font.BOLD, 15));// 设置菜单显示的字体

		jmi1 = new MenuItem("开始新游戏");
		jmi2 = new MenuItem("退出");
		jmi3 = new MenuItem("暂停");
		jmi4 = new MenuItem("继续");
		jmi5 = new MenuItem("游戏说明");
		jmi6 = new MenuItem("级别1");
		jmi7 = new MenuItem("级别2");
		jmi8 = new MenuItem("级别3");
		jmi9 = new MenuItem("级别4");
		jmi1.setFont(new Font("TimesRoman", Font.BOLD, 15));
		jmi2.setFont(new Font("TimesRoman", Font.BOLD, 15));
		jmi3.setFont(new Font("TimesRoman", Font.BOLD, 15));
		jmi4.setFont(new Font("TimesRoman", Font.BOLD, 15));
		jmi5.setFont(new Font("TimesRoman", Font.BOLD, 15));

		jm1.add(jmi1);
		jm1.add(jmi2);
		jm2.add(jmi3);
		jm2.add(jmi4);
		jm3.add(jmi5);
		jm4.add(jmi6);
		jm4.add(jmi7);
		jm4.add(jmi8);
		jm4.add(jmi9);

		jmb.add(jm1);
		jmb.add(jm2);

		jmb.add(jm4);
		jmb.add(jm3);

		jmi1.addActionListener(this);
		jmi1.setActionCommand("NewGame");
		jmi2.addActionListener(this);
		jmi2.setActionCommand("Exit");
		jmi3.addActionListener(this);
		jmi3.setActionCommand("Stop");
		jmi4.addActionListener(this);
		jmi4.setActionCommand("Continue");
		jmi5.addActionListener(this);
		jmi5.setActionCommand("help");
		jmi6.addActionListener(this);
		jmi6.setActionCommand("level1");
		jmi7.addActionListener(this);
		jmi7.setActionCommand("level2");
		jmi8.addActionListener(this);
		jmi8.setActionCommand("level3");
		jmi9.addActionListener(this);
		jmi9.setActionCommand("level4");

		this.setMenuBar(jmb);// 菜单Bar放到JFrame上
		this.setVisible(true);

		this.setSize(Fram_width, Fram_length); // 设置界面大小
		this.setLocation(280, 50); // 设置界面出现的位置
		this.setTitle("贪吃蛇碰方块");

		this.addWindowListener(new WindowAdapter() { // 窗口监听关闭
					public void windowClosing(WindowEvent e) {
						System.exit(0);
					}
				});
		this.setResizable(false);
		this.setBackground(Color.GREEN);
		this.setVisible(true);
		
		for(int i=0;i<5;i++){
			bs.add(new barrier(100*i,50, rand.nextInt(10),this));
		}

		this.addKeyListener(new KeyMonitor());// 键盘监听
		new Thread(new PaintThread()).start(); // 线程启动
	}
	
	//碰撞函数，碰撞后让蛇的长度和方块的数值同时减少，减到0的话就将用户的蛇的生命置为false
	public void hitBarrier(Graphics g,barrier b,Snake s){
		for(int i = Math.min(b.num,s.len);i>0;i--){
			b.num -= 1;
			s.len -= 1;
			s.sBlock.remove(s.len);
		}
		if(b.num==0){
			bs.remove(b);
		}
		if(s.len==0){
			s.live = false;
		}
	}
	
	//设置多线程函数，每隔50毫秒刷新画图函数
	private class PaintThread implements Runnable {
		public void run() {
			// TODO Auto-generated method stub
			while (printable) {
				repaint();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) {
		new SnakeClient(); // 实例化
	}
	
	private class KeyMonitor extends KeyAdapter {

		public void keyReleased(KeyEvent e) { // 监听键盘释放
			homeSnake.keyReleased(e);
		}

		public void keyPressed(KeyEvent e) { // 监听键盘按下
			homeSnake.keyPressed(e);
		}

	}

	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("NewGame")) {
			time = 0;
			printable = false;
			Object[] options = { "确定", "取消" };
			int response = JOptionPane.showOptionDialog(this, "您确认要开始新游戏！", "",
					JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null,
					options, options[0]);
			if (response == 0) {

				printable = true;
				this.dispose();
				new SnakeClient();
			} else {
				printable = true;
				new Thread(new PaintThread()).start(); // 线程启动
			}

		} else if (e.getActionCommand().endsWith("Stop")) {
			printable = false;
			// try {
			// Thread.sleep(10000);
			//
			// } catch (InterruptedException e1) {
			// // TODO Auto-generated catch block
			// e1.printStackTrace();
			// }
		} else if (e.getActionCommand().equals("Continue")) {

			if (!printable) {
				printable = true;
				new Thread(new PaintThread()).start(); // 线程启动
			}
			// System.out.println("继续");
		} else if (e.getActionCommand().equals("Exit")) {
			printable = false;
			Object[] options = { "确定", "取消" };
			int response = JOptionPane.showOptionDialog(this, "您确认要退出吗", "",
					JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null,
					options, options[0]);
			if (response == 0) {
				System.out.println("退出");
				System.exit(0);
			} else {
				printable = true;
				new Thread(new PaintThread()).start(); // 线程启动

			}

		} else if (e.getActionCommand().equals("help")) {
			printable = false;
			JOptionPane.showMessageDialog(null, "用→ ← ↑ ↓控制方向，F键盘发射，R重新开始！",
					"提示！", JOptionPane.INFORMATION_MESSAGE);
			this.setVisible(true);
			printable = true;
			new Thread(new PaintThread()).start(); // 线程启动
		} /*else if (e.getActionCommand().equals("level1")) {
			Tank.count = 12;
			Tank.speedX = 6;
			Tank.speedY = 6;
			Bullets.speedX = 10;
			Bullets.speedY = 10;
			this.dispose();
			new TankClient();
		} else if (e.getActionCommand().equals("level2")) {
			Tank.count = 12;
			Tank.speedX = 10;
			Tank.speedY = 10;
			Bullets.speedX = 12;
			Bullets.speedY = 12;
			this.dispose();
			new TankClient();

		} else if (e.getActionCommand().equals("level3")) {
			Tank.count = 20;
			Tank.speedX = 14;
			Tank.speedY = 14;
			Bullets.speedX = 16;
			Bullets.speedY = 16;
			this.dispose();
			new TankClient();
		} else if (e.getActionCommand().equals("level4")) {
			Tank.count = 20;
			Tank.speedX = 16;
			Tank.speedY = 16;
			Bullets.speedX = 18;
			Bullets.speedY = 18;
			this.dispose();
			new TankClient();
		}*/
	}
}
