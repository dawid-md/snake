import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Kobra extends JPanel implements KeyListener, ActionListener {
	Timer timer = new Timer(60, this);
	ArrayList<Rectangle> Shp = new ArrayList<Rectangle>();
	Iterator<Rectangle> it = Shp.iterator();
	Random gen = new Random();
	boolean let = true;
	
	static int size, points = 0;
	
	int posX = 350;
	int posY = 300;
	
	Rectangle sn;
	Rectangle fn = new Rectangle(250, 400, 10, 10);
	Rectangle frame = new Rectangle(200, 200, 400, 400);
	
	int vx = 10;
	int vy = 0;
	
	int aA, aB, bA, bB;
	
	int ranX, ranY;
	
public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.black);
		g2.fillRect(0, 0, 790, 780);	
		g2.setColor(Color.white);
		g2.draw(frame);
		g.setColor(Color.white);
		g.drawString("Score: " + points, 9, 20);
		drawSnake(g2);
		drawFood(g2);
	 }	
	
	public Kobra() {
		JFrame frame = new JFrame();	
		frame.setLocation(500, 100);
		frame.setSize(800, 800);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.addKeyListener(this);
		frame.setFocusable(true);
		frame.add(this);
		createS(5);
		timer.start();
	}
	
	public void createS(int length) {
		size = length;
		for (int i = 0; i < length; i++) {
			Shp.add(new Rectangle(posX - i*10, posY, 10, 10));
		}
	}
	
	public void createFood() {
		ranX = gen.nextInt(400);
		ranY = gen.nextInt(400);

		fn.x = ranX + 200;
		fn.y = ranY + 200;
	}
	
	public void drawSnake(Graphics2D g2) {
		if (size > 0) {for(Shape s: Shp) {
			g2.setStroke(new BasicStroke(2));
			g2.setColor(Color.blue);
			g2.fill(s);
			g2.setColor(Color.black);
			g2.draw(s);			
			repaint();
		}}	
	}
	
	public void drawFood(Graphics2D g2) {
		g2.setColor(Color.red);
		g2.setStroke(new BasicStroke(1));
		g2.fill(fn);
	}	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {	
 		{
			let = true;
			
			Shp.get(0).x = Shp.get(0).x + vx;
			Shp.get(0).y = Shp.get(0).y + vy;
			
			aA = Shp.get(0).x - vx;
			aB = Shp.get(0).y - vy;
			
			for(int i = 1; i < Shp.size(); i+=2) {
				
			bA = Shp.get(i).x;
			bB = Shp.get(i).y;
			
			Shp.get(i).x = aA;
			Shp.get(i).y = aB;	
			
			if((i+1) < Shp.size()) {
				
			aA = Shp.get(i+1).x;
			aB = Shp.get(i+1).y;
			
			Shp.get(i+1).x = bA;
			Shp.get(i+1).y = bB; 
		
			}
			}
			
			if (Shp.get(0).x >= 600) Shp.get(0).x = 200;
			if (Shp.get(0).y >= 600) Shp.get(0).y = 200;
			
			if (Shp.get(0).x < 200) Shp.get(0).x = 590;
			if (Shp.get(0).y < 200) Shp.get(0).y = 590;
		}	
	  	checkCol();
	  	checkEat();
}

	public void checkCol() {
		for(int i = 1; i < Shp.size(); i++) {
			if (Shp.get(0).intersects(Shp.get(i))) {timer.stop();}  
		}	
	}
	
	public void checkEat() {
		if (Shp.get(0).intersects(fn)) {
			Shp.add(new Rectangle(bA, bB, 10, 10));
			points += 10;
			createFood();
		}
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		int Code = arg0.getKeyCode();
		
		if (((Code == KeyEvent.VK_S) && (vx != 0)) && (let == true)) {let = false; vy = 10; vx = 0;   }
		if (((Code == KeyEvent.VK_D) && (vy != 0)) && (let == true)) {let = false; vy = 0; vx = 10;   }
		if (((Code == KeyEvent.VK_A) && (vy != 0)) && (let == true)) {let = false; vy = 0; vx = -10;  }
		if (((Code == KeyEvent.VK_W) && (vx != 0)) && (let == true)) {let = false; vy = -10; vx = 0;  }
		
		if (Code == KeyEvent.VK_R) { 
			for(int i = Shp.size() - 1; i >=0; i--) {
				Shp.remove(i); } 
			timer.start(); createS(5); points = 0;
			}
		 }

	@Override
	public void keyReleased(KeyEvent arg0) {	
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}

	public static void main(String[] args) {
		new Kobra();
	}
}