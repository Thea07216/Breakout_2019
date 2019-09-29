// HelloMVC: a simple MVC example
// the model is just a counter 
// inspired by code by Joseph Mack, http://www.austintek.com/mvc/
import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;
class View extends JPanel implements Observer {


	private Model model;
	int prev_width = 300;
	int prev_height = 300;
	int cur_width = 300;
	int cur_height = 300;
	int acc = 4;
	int flag = 0;
	JLabel jl;

	View(Model model) {

		// create the view UI
		//this.setLayout(new GridBagLayout());
		//this.add(button, new GridBagConstraints());
		
		// set the model 
		this.model = model;
		this.setFocusable(true);
		this.setLayout(null);
		this.addKeyListener(new KeyAdapter() {
								//  @Override
								public void keyPressed(KeyEvent e) {
									if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
										model.moveright();
									} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
										model.moverLeft();
									}
								}
							}
		);
		this.addMouseMotionListener(new MouseAdapter() {

			public void mouseMoved(MouseEvent e) {
				if(e.getPoint().getX() > model.paddle_x){
					model.moveright();
				} else if (e.getPoint().getX() < model.paddle_x){
					model.moverLeft();
				}
			}// end mouseDragged
		});
		this.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent componentEvent) {
				prev_height = model.frame_height;
				prev_width = model.frame_width;
				model.set_frame_width(getWidth());
				model.set_frame_height(getHeight()/3);
				cur_width = getWidth();
				cur_height = getHeight();
				model.paddle_x = model.paddle_x*getWidth()/prev_width;
				model.paddle_y = getHeight()-10;
				model.resize_set();
			}
		});
		jl = new JLabel();
		jl.setBounds(10,cur_height/2,cur_width/2,20);
		this.add(jl);

		// setup the event to go to the "controller"
		// (this anonymous class is essentially the controller)
	}

	public void check_collision(int ball_x, int ball_y){
		ArrayList<Model.Grid> bricks = model.bricks;
		for (int i = 49; i >= 0; i--){
			if (bricks.get(i) != null){
				if(bricks.get(i).g_r.contains(new Point(ball_x,ball_y))){
					//System.out.println("checking " + i);
					//System.out.println(ball_x +", " + ball_y + ", "+ bricks.get(i).x_pos + ", " + bricks.get(i).y_pos);
//					int dist1 = (ball_x-bricks.get(i).x_pos)*(ball_x-bricks.get(i).x_pos) + (ball_y-bricks.get(i).y_pos)*(ball_y-bricks.get(i).y_pos);
//					int dist2 = (ball_x-bricks.get(i).x_pos)*(ball_x-bricks.get(i).x_pos) + (ball_y-bricks.get(i).y_pos-bricks.get(i).g_height)*(ball_y-bricks.get(i).y_pos-bricks.get(i).g_height);
//					int dist3 = (ball_x-bricks.get(i).x_pos-bricks.get(i).g_width)*(ball_x-bricks.get(i).x_pos-bricks.get(i).g_width)+ (ball_y-bricks.get(i).y_pos)*(ball_y-bricks.get(i).y_pos);
//					int dist4 = (ball_x-bricks.get(i).x_pos-bricks.get(i).g_width)*(ball_x-bricks.get(i).x_pos-bricks.get(i).g_width) + (ball_y-bricks.get(i).y_pos-bricks.get(i).g_height)*(ball_y-bricks.get(i).y_pos-bricks.get(i).g_height);
//					int sum24 = dist2+dist4;
//					int sum12 = dist2+dist1;
//					int sum13 = dist1+dist3;
//					int sum34 = dist3+dist4;
//					int mindist = Math.min(Math.min(sum24,sum12),Math.min(sum13,sum34));
//					if(sum24 == mindist || sum13 == mindist){
//						model.ball_dy *= -1;
//						//System.out.println("######## " + i);
//					}else if(sum34 == mindist || sum12 == mindist){
//						model.ball_dx *= -1;
//						//System.out.println("@@@@@@@@ " + i);
//					}
					int dist1 = Math.abs(bricks.get(i).x_pos -ball_x);
					int dist2 = Math.abs((bricks.get(i).x_pos +bricks.get(i).g_width) -ball_x);
					int dist3 = Math.abs(bricks.get(i).y_pos + ball_y);
					int dist4 = Math.abs((bricks.get(i).y_pos +bricks.get(i).g_height) -ball_y);
					int mindist = Math.min(Math.min(dist1,dist2),Math.min(dist3,dist4));
					if(mindist == dist1  || mindist == dist2){
						model.ball_dx *= -1;
					} else if (mindist == dist3 || mindist == dist4){
						model.ball_dy *= -1;
					}
					if (bricks.get(i).g_c == Color.black) {
						if(flag == 0) {
							flag = 1;
							model.paddle_width = model.paddle_width*2;
							//System.out.println("&&&&&&&&&&&");
						}
					}
					model.updatescore();
					bricks.set(i,null);
				}
			}
		}
		repaint();

	}

	public void handle_animation() {

		if (getSize().height == 0) {
			//JOptionPane.showMessageDialog(this, "m288zhan", "Breakout Game", JOptionPane.INFORMATION_MESSAGE);
		} else {
			if (model.ball_y > (getSize().height - model.ball_width)) {
				if (model.ball_x >= model.paddle_x && model.ball_x <= model.paddle_x + model.paddle_width) {
					model.ball_dy *= -1;
					if(flag == 1){
						if (acc == 0){
							model.paddle_width = model.paddle_width/2;
							flag = 0;
							acc = 4;
						}else{
							acc--;
						}
					}
				} else {
					//int n = JOptionPane.showMessageDialog(this, "Game Over", "InformationMessage", JOptionPane.INFORMATION_MESSAGE);
					int n = JOptionPane.showOptionDialog(this, "Game Over", "Check", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
					if (n == 0) {
						model.ball_dy *= -1;
					} else {
						System.exit(0);
					}
				}
			}
			if (model.ball_y < getHeight()/3+2) {
				if(model.ball_y < 2){
					model.ball_dy *= -1;
					model.ball_dx *= -1;
				}
				//System.out.println(model.ball_y);
				if (model.ball_x < 0 || model.ball_x > (getSize().width - model.ball_width)) { model.ball_dx *= -1; }
				check_collision(model.ball_x,model.ball_y);
			} else {
				if (model.ball_x < 0 || model.ball_x > (getSize().width - model.ball_width)) { model.ball_dx *= -1; }
			}
		}
		model.ball_x += model.ball_dx;
		model.ball_y += model.ball_dy;

		// force painting
		repaint();
	}

	// Observer interface 
	@Override
	public void update(Observable arg0, Object arg1) {
		//System.out.println("View: update");
		jl.setText("Score: " + model.score + "\n" + "FPS: " + model.FPS);
		repaint();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.RED);
		g.fillOval(model.ball_x, model.ball_y, model.ball_width, model.ball_width);
		g.setColor(Color.blue);
		g.fillRect(model.paddle_x, model.paddle_y, model.paddle_width, 10);
		ArrayList<Model.Grid> mbricks = model.bricks;
		for (int i = 0; i < 50; i++) {
			if (mbricks.get(i) != null) {
				g.setColor(mbricks.get(i).g_c);
				g.fillRect(mbricks.get(i).x_pos, mbricks.get(i).y_pos, mbricks.get(i).g_width, mbricks.get(i).g_height);
			}
		}
	}
} 
