import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;


public class Parser extends Component implements Runnable{
	
	
	Grammar grammar;
	List<Line> lineas;
	String tree;
	int initX;
	int initY;
	Color color;
	Color punticos;
	
	public Parser(int initX, int initY, Color color, Color puntos, Grammar grammar){
		super();
		this.initX = initX;
		this.initY = initY;
		this.color = color;
		this.punticos = puntos;
		this.grammar = grammar;
	}
	
	@Override
	public void paint(Graphics g){
		tree = grammar.generateLSystem();
		generateTree();
		super.paint(g);
		Line linea;
		//g.setColor(new Color(0,102,51));
		//g.fillRect(0, 0, 1200, 600);
		g.setColor(this.color);
		for(int i=0;i<lineas.size();i++){
			linea = lineas.get(i);
			g.setColor(color);
			g.drawLine(linea.x1, linea.y1, linea.x2, linea.y2);
			g.setColor(punticos);
			g.drawOval(linea.x1, linea.y1, 2, 1);
			
		}
	}
	public void lineas(){
		
		System.out.println(lineas);
	}
	
	public void generateTree(){
		lineas = new LinkedList<Line>();
		Stack<Punto> checkPoint  = new Stack<Punto>();
		int startPointX = initX;
		int startPointY = initY; //cada Y debe ser restado 600;
		int distancia = 5;
		int angle = 0;
		int random ;
		int changeAngle;
		for(int i=0;i<tree.length();i++){
			random = (int)(Math.random()*5);
			changeAngle = 25 + random;
			switch(tree.charAt(i)){
			case 'F':
				int prevx = startPointX;
				int prevy = startPointY;
				startPointX = startPointX  + (int)(distancia*Math.sin(Math.toRadians(angle)));
				startPointY = startPointY  + (int)(distancia*Math.cos(Math.toRadians(angle)));
				Line line = new Line(prevx,-prevy+600,startPointX, -startPointY+600);
				lineas.add(line);
				break;
			case '[':
				Punto check = new Punto(startPointX, startPointY, angle);
				//System.out.println(check);
				checkPoint.add(check);
				break;
			case '+':
				angle = changeAngle + angle;
				//System.out.println("Angle for right: "+angle);
				break;
			case '-':
				angle = angle - changeAngle;
				//System.out.println("Angle for left: "+angle);
				break;
			case ']':
				Punto checki = checkPoint.pop();
				startPointX = checki.getX();
				startPointY = checki.getY();
				angle = checki.getAngle();
				//System.out.println("Termino branch: "+angle);
				break;
			case 'K':
			   //Nothing fur now
				break;
			}
			
		}
		
	}


	@Override
	public void run() {
		repaint();
	}  
}

class Punto{
	
	int x,y, angle;
	public Punto(int x, int y, int angle){
		this.x = x;
		this.y = y;
		this.angle = angle;
		
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getAngle(){
		return angle;
	}
	
	@Override
	public String toString(){
		
		return "[x: "+x+", y: "+y+"]";
	}
	
}

class Line{
	
	int x1,x2,y1,y2;
	
	public Line(int x1,int y1, int x2, int y2){
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	} 
	
	public int getX1(){
		
		return x1;
	}
	
	public int getX2(){
		
		return x2;
	}

	public int getY1(){
	
		return y1;
	}

	public int getY2(){
	
		return y2;
	}
	
	@Override
	public String toString(){
		
		return "[x1: "+x1+", y1: "+y1+", x2: "+x2+", y2: "+y2+"]";
	}
}

class Background extends JComponent{
	
	Image im ;
	int startX;
	int startY;
	
	public Background(int x, int y){
		super();
		BufferedImage img = null;
		try{
			img = ImageIO.read(new BufferedInputStream(new FileInputStream("src/background.jpg")));
		}catch(Exception e){
			System.out.println("Error "+e);
		}
		im = new ImageIcon(img).getImage();
		startX = x;
		startY = y;
	}
	
	@Override
	public void paint(Graphics g){
		g.setColor(Color.white);
		Image ima = im.getScaledInstance(1000, 600, Image.SCALE_FAST); 
		g.drawImage(ima, startX, startY, this);
	}
	
}