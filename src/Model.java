import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;
import java.awt.*;
import java.util.Random;
// HelloMVC: a simple MVC example
// the model is just a counter 
// inspired by code by Joseph Mack, http://www.austintek.com/mvc/

public  class Model extends Observable {
	// the data in the model, just a counter
	public static int ball_x = 150;
	public static int ball_y = 160;
	public static int ball_dx = 2;
	public static int ball_dy = 2;
	public static int ball_width = 20;
	public static int paddle_x = 50;
	public static int paddle_y = 270;
	public static int paddle_dx = 10;
	public static ArrayList<Grid> bricks = new ArrayList<Grid>();
 	public static int frame_height = 100;
 	public static int frame_width = 300;
	public static int paddle_width = frame_width/6;
	public static int score = 0;
	public static int FPS;

	public class Grid{
		int x_pos;
		int y_pos;
		int g_width;
		int g_height;
		Color g_c;
		Rectangle g_r;
		Grid(int x,int y,int gwidth,int gheight, Color c){
			x_pos = x;
			y_pos = y;
			g_width = gwidth;
			g_height = gheight;
			g_c = c;
			g_r = new Rectangle(x,y,gwidth,gheight);
		}
	}
	Model() {
		setChanged();
		int gwidth = frame_width / 10;
		int gheight = frame_height / 5;
		for (int i = 0; i < 50 ; i++) {
			if (i >= 0 && i <= 9) {
				bricks.add(new Grid(i + i * gwidth, 0, gwidth, gheight, Color.BLUE));
			} else if (i >= 10 && i <= 19) {
				bricks.add(new Grid((i % 10) + (i % 10) * gwidth, gheight, gwidth, gheight, Color.GREEN));
			} else if (i >= 20 && i <= 29) {
				bricks.add(new Grid((i % 10) + (i % 10) * gwidth, 2 * gheight, gwidth, gheight, Color.red));
			} else if (i >= 30 && i <= 39) {
				bricks.add(new Grid((i % 10) + (i % 10) * gwidth, 3 * gheight, gwidth, gheight, Color.yellow));
			} else if (i >= 40 && i <= 49) {
				bricks.add(new Grid((i % 10) + (i % 10) * gwidth, 4 * gheight, gwidth, gheight, Color.pink));
			}
		}
		for (int j = 0; j < 5; j ++){
			Random randomGenerator = new Random();
			int index = randomGenerator.nextInt(50);
			Grid g = new Grid(bricks.get(index).x_pos,bricks.get(index).y_pos, bricks.get(index).g_width, bricks.get(index).g_height, Color.black);
			bricks.set(index,g);
		}
	}

	public void set_frame_height(int hei){
		frame_height = hei;
	}
	public void set_FPS(int f){
		FPS = f;
	}
	public void set_ballspeed(int s){
		ball_dx = s;
		ball_dy = s;
	}
	public void set_frame_width(int wid){
		frame_width = wid;
		paddle_width = frame_width/6;
	}
	public void resize_set() {
		for (int i = 0; i < 50; i++) {
			if (bricks.get(i) != null) {
				int gwidth = frame_width / 10;
				int gheight = frame_height / 5;
				if (i >= 0 && i <= 9) {
					Grid g = new Grid(i + i * gwidth, 0, gwidth, gheight, bricks.get(i).g_c);
					bricks.set(i, g);
				} else if (i >= 10 && i <= 19) {
					Grid g = new Grid((i % 10) + (i % 10) * gwidth, gheight, gwidth, gheight, bricks.get(i).g_c);
					bricks.set(i,g);
				} else if (i >= 20 && i <= 29) {
					Grid g = new Grid((i % 10) + (i % 10) * gwidth, 2*gheight, gwidth, gheight, bricks.get(i).g_c);
					bricks.set(i, g);
				} else if (i >= 30 && i <= 39) {
					Grid g = new Grid((i % 10) + (i % 10) * gwidth,3*gheight, gwidth, gheight, bricks.get(i).g_c);
					bricks.set(i,g);
				} else if (i >= 40 && i <= 49) {
					Grid g = new Grid((i % 10) + (i % 10) * gwidth, 4 * gheight, gwidth, gheight, bricks.get(i).g_c);
					bricks.set(i, g);
				}
			}
		}
		this.setChanged();
		this.notifyObservers();
	}
	public  void moveright() {
		if (paddle_x > (frame_width - paddle_width)) {
			paddle_x = frame_width - paddle_width;
		} else {
			paddle_x += paddle_dx;
		}
		this.setChanged();
		this.notifyObservers();
	}
	public void updatescore() {
		score++;
		this.setChanged();
		this.notifyObservers();
	}

	public  void moverLeft(){
		if (paddle_x < 0) {
			paddle_x =0;
		} else {
			paddle_x -= paddle_dx;
		}
		this.setChanged();
		this.notifyObservers();
	}
}
