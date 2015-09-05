/*
 * 1.画出坦克
 * 2.实现我的坦克的上下移动 
 * 3.实现我的坦克发射子弹(最多连续发射五颗)
 * 4.击中坦克产生爆炸效果 
 */
package zmc;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TankGame1_0  extends JFrame {

	private MyJPanel  mp  = null;
	
	public TankGame1_0() throws Exception {
		
		mp = new MyJPanel();
		this.setName("坦克大战");
		this.setBounds(200, 200, 400, 300);
		this.setResizable(true);//设置此窗体是否可由用户调整大小
		this.add(mp);
		this.addKeyListener(mp);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		try{
		Thread thr = new Thread(mp);
		thr.start();
		}
		catch (Exception e){
			throw e;
		}
	}
	
	public static void main(String[] args)  throws Exception{
	    new TankGame1_0();
	}

}

class MyJPanel extends JPanel implements KeyListener,Runnable{
	
	//定义我的坦克
	private MyTank myTank = null;
	//定义敌方坦克
	private Vector<EnemyTank> ets = new Vector<EnemyTank>();
	private int intSize = 3;
	//定义三张图片，三张图片才能组成一颗炸弹（爆炸效果）
	Image image1=null;
	Image image2=null;
	Image image3=null;
	
	public MyJPanel(){
		myTank = new MyTank(180,250);
		myTank.setColor(1);
		
		for(int i=0 ; i<intSize;i++){
			EnemyTank et = new EnemyTank((i+1)*50,0);
			et.setColor(0);
			et.setDirect(3);
			ets.add(et);
		}
		
		//初始化三张图片
				try {
					image1=ImageIO.read(new File("1.png"));
					image2=ImageIO.read(new File("1.png"));
					image3=ImageIO.read(new File("1.png"));
				} catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
				}		
	}
	
	public void run(){
		
		while(true){
			try{
			Thread.sleep(100);}
			catch(Exception e){
				e.printStackTrace();
			}
			for(int i=0;i<this.myTank.shots.size();i++){
				Shot temp_shot = this.myTank.shots.get(i);
				  if(temp_shot.isLive){
				   for(int j=0;j<this.ets.size();j++){
					   EnemyTank  temp_tank = this.ets.get(j);
					   if(temp_tank.isLive){
					     hitTank(temp_shot,temp_tank); 
					   }
				   }
				  }
			}
			repaint();
		}
		
	}
	
	public void paint(Graphics g){
		
		super.paint(g);
		g.fillRect(0, 0, 400, 300);
		//画出我的坦克
        drawTank(g,myTank.getX(),myTank.getY(),myTank.getcolor(),myTank.getDirect());
        //画出敌方的坦克
        for(EnemyTank et:ets){
        	if(et.isLive)
        	  drawTank(g,et.getX(),et.getY(),et.getcolor(),et.getDirect());
        }
        //画子弹
        for(int i=0;i<this.myTank.shots.size();i++){
        	Shot Temp_Shot = this.myTank.shots.get(i);
        	if(Temp_Shot !=null&&Temp_Shot.isLive==true){
        		g.setColor(Color.ORANGE);
        		g.fill3DRect(Temp_Shot .getX(),Temp_Shot .getY(),2, 2, false);
        	}
        	if(Temp_Shot.isLive==false){
                this.myTank.shots.remove(Temp_Shot);
        	}
        }
	}
	//判断子弹是否击中敌人坦克
	public void hitTank(Shot s ,EnemyTank et){
		
		int X = s.getX();
		int Y = s.getY();
		
		switch(et.getDirect()){
		case 0:
		case 3: if((X>=et.getX()&&X<=et.getX()+20)&&(Y>=et.getY()&&Y<=et.getY()+30)){
			                   //击中
			                 s.isLive = false;
			                 et.isLive =false;
						}
		                 break;
		case 1:
		case 2: if((X>=et.getX()&&X<=et.getX()+30)&&(Y>=et.getY()&&Y<=et.getY()+20)){
			                s.isLive = false;
			                et.isLive = false;
		}
		    break;
		}
		
	}
	
	//画出坦克
	public void drawTank(Graphics g , int x, int y, int color , int direct){
		
		//坦克的颜色,1代表我的坦克的颜色，0代表敌方坦克的颜色
		 switch(color){
		 case 0: 
			 			g.setColor(Color.CYAN); break;
		 case 1: 
			 			g.setColor(Color.YELLOW); break;
		 }
		 
		 //方向 0代表向上1代表向左2代表向右3代表向下
		 switch(direct){
		 case 0: 
			    //画出左边矩形
				g.fill3DRect(x,y,5, 30, false);//false/true决定是凹与凸
				//画出中间矩形
				g.fill3DRect(x+5, y+5, 10, 20, false);
				//画出右边矩形
				g.fill3DRect(x+15, y, 5, 30, false);
				//画出中间圆形
				g.fillOval(x+5, y+10, 10, 10);
				//画出炮筒
				g.drawLine(x+10, y+15, x+10,y);
				break;
		 case 1:
			    //画出上边矩形
				g.fill3DRect(x,y,30,5, false);//false/true决定是凹与凸
				//画出中间矩形
				g.fill3DRect(x+5, y+5, 20,10, false);
				//画出下边矩形
				g.fill3DRect(x, y+15, 30,5, false);
				//画出中间圆形
				g.fillOval(x+10, y+5, 10, 10);
				//画出炮筒
				g.drawLine(x+15, y+10, x,y+10);
				break;
		  case 2:
			    //画出上边矩形
				g.fill3DRect(x,y,30,5, false);//false/true决定是凹与凸
				//画出中间矩形
				g.fill3DRect(x+5, y+5, 20,10, false);
				//画出下边矩形
				g.fill3DRect(x, y+15, 30,5, false);
				//画出中间圆形
				g.fillOval(x+10, y+5, 10, 10);
				//画出炮筒
				g.drawLine( x+15,y+10,x+30, y+10);
				break;
		  case 3: 
			    //画出左边矩形
				g.fill3DRect(x,y,5, 30, false);//false/true决定是凹与凸
				//画出中间矩形
				g.fill3DRect(x+5, y+5, 10, 20, false);
				//画出右边矩形
				g.fill3DRect(x+15, y, 5, 30, false);
				//画出中间圆形
				g.fillOval(x+5, y+10, 10, 10);
				//画出炮筒
				g.drawLine(x+10, y+15, x+10,y+30);
				break;
		 }
	}
	
	public  void  keyPressed(KeyEvent e) {
		 //向上
		 if((e.getKeyChar()=='w')||(e.getKeyChar()=='W')||(e.getKeyCode()==KeyEvent.VK_UP)){
				 this.myTank.moveUp();
				// this.repaint();
	    }
		 //向左
		 else  if((e.getKeyChar()=='a')||(e.getKeyChar()=='A')||(e.getKeyCode()==KeyEvent.VK_LEFT)){
			 this.myTank.moveLeft();
			// this.repaint();
       }
		 //向右
		 else  if((e.getKeyChar()=='d')||(e.getKeyChar()=='D')||(e.getKeyCode()==KeyEvent.VK_RIGHT)){
			 this.myTank.moveRight();
			// this.repaint();
	        }		 
		 //向下
		 else  if((e.getKeyChar()=='s')||(e.getKeyChar()=='S')||(e.getKeyCode()==KeyEvent.VK_DOWN)){
			 this.myTank.moveDown();
			// this.repaint();
	        }
		 //我的坦克发子弹
		 if((e.getKeyChar()=='j')||(e.getKeyChar()=='J')){
			 //最多发射五颗子弹
			 if(this.myTank.shots.size()<5){
			this.myTank.ShotEnemy();
			// Thread thr = new Thread(this);
			// thr.start();
			 }
	        }
	 }
	        
	public   void  keyReleased(KeyEvent e) {
		 
	 }
	         
	public   void  keyTyped(KeyEvent e) {
		 
	 }
	
}
