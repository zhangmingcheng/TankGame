package zmc;

import java.util.Vector;

class Tank{
	
	 private int x=0,y=0;//坦克的横纵坐标
	 private int direct = 0;
     private int speed = 3;
     private int color = 0;
     
      public Tank(int x,int y ){
    	  this.x = x;
    	  this.y = y;
    	  
      }
      public void setColor(int color){
    	  this.color = color;
      }
      public int getcolor(){
    	  return this.color;
      }
      public void moveUp(){
    	  this.setDirect(0);
    	  if(this.y>=this.speed)
    	  this.y-=this.speed;
      }
      public void moveLeft(){
    	  this.setDirect(1);
      	  if(this.x>=this.speed)
    	  this.x-=this.speed;
      }
      public void moveRight(){
    	  this.setDirect(2);
      	  if(this.x+this.speed<=370)
    	  this.x+=this.speed;
      }
      public void moveDown(){
    	  this.setDirect(3);
      	  if(this.y+this.speed<=270)
    	  this.y+=this.speed;
      }
      
 	 public int getX() {
 		return x;
 	}

 	public void setX(int x) {
 		this.x = x;
 	}

 	public int getY() {
 		return y;
 	}

 	public void setY(int y) {
 		this.y = y;
 	}
    public int getDirect() {
		return direct;
	}

	public void setDirect(int direct) {
		this.direct = direct;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

}
//-------------子弹------------------------//
class Shot implements Runnable{
	private int x,y;//子弹的横纵坐标
	private int direct ;
	private int speed = 2;
    boolean isLive = true;
	public Shot(int x, int y,int direct ){
		this.x = x;
		this.y =y;
		this.direct=direct;
	}
	
	public void run(){
		while(true){
			try{Thread.sleep(50);}
			catch(Exception e){
				e.printStackTrace();
			}
			switch(this.direct){
			case 0: 
				this.y-=speed;
				break;
			case 1: 
				this.x-=speed;
				break;
			case 2:
				this.x+=speed;
				break;
			case 3:	
			this.y+=speed;
			break;
			}
			if(x<0||x>400||y<0||y>300)
			{
				this.isLive=false;
				break;
			}
		}
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getDirect() {
		return direct;
	}
	public void setDirect(int direct) {
		this.direct = direct;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
}
//-------------敌方坦克-----------------//
class EnemyTank extends Tank{
	
	boolean isLive = true;
	
	public EnemyTank(int x, int y){
		super(x,y);
	}
}
//-------------我的坦克-----------------//
class MyTank extends Tank{
	
    Shot myShot = null;
    Vector<Shot>  shots = new Vector<Shot>();
    
	public MyTank(int x,int y){
		super(x,y);
	}
	
	public void ShotEnemy(){
	
		int X = this.getX();
		int Y = this.getY();
		switch(this.getDirect()){
	 	case 0:
			myShot = new Shot(X+10,Y,0);
			this.shots.add(myShot);
			break;
		case 1:
			myShot = new Shot(X,Y+10,1);
			this.shots.add(myShot);
			break;
		case 2:
			myShot = new Shot(X+30,Y+10,2);
			this.shots.add(myShot);
			break;
		case 3:
			myShot = new Shot(X+10,Y+30,3);
			this.shots.add(myShot);
			break;
		}
		Thread thread = new Thread(myShot);
		thread.start();
		
	}
	
}

public class Members {
	
}
