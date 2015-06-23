import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;


public class NonlinearAffineTransformation extends JComponent{
	
	int width;
	int height;
	BufferedImage imag;
	static String[] PRESET_FUNCTION = {"xto2","functionSin", "functionCos", "functionLog", "functionLogReverse"};
	
	boolean trans;
	public NonlinearAffineTransformation(int width, int height){
		super();
		this.width = width;
		this.height = height;
		try{
			imag = ImageIO.read(new BufferedInputStream(new FileInputStream("src/raza0.png")));
		}catch(Exception e){
			e.printStackTrace();
		}
		Image im = imag.getScaledInstance(150,150,100);
		imag = this.convertToBufferedImage(im);
	}
	
	
	public static Integer xto2(Integer x, Integer size, Double a, Double b){
		size = size/2;
		x = x - size;
		return  (int)Math.pow(x, 2)/15; 
	}
	
	
	public static int functionSin(Integer x, Integer size, Double a , Double b){
		
		size = size/2;
		x = x - size;
		//double a = 0.2; // 0.1 - 0.9
		//double b = 10;  // 1 - 9
		return  (int)(Math.sin((double)x*a)*b);
		
	}
	
	public static int functionCos(Integer x, Integer size, Double a , Double b){
		size = size/2;
		x = x - size;
		//double a = 0.2; // 0.1 - 0.9
		//double b = 10;  // 1 - 9
		return  (int)(Math.cos((double)x*a)*b);
	}
	
	public static int functionLog(Integer x, Integer size, Double a, Double b){
		//a = 50; // 30 - 60
		x = x + 1;
		return  (int)((Math.log(x)*a));
	}
	
	public static int functionLogReverse(Integer x, Integer size, Double a, Double b){
		x = size - x;
		//a = 50; // 30 - 60
		return  (int)((Math.log(x)*a));
	}
	
	public static int function(int x, int size, double a , double b){
		size = size/2;
		x = x - size;
		return  (int)Math.pow(x, 2)/100;
	}
	
	public BufferedImage convertToBufferedImage(Image image)
	{
	    BufferedImage newImage = new BufferedImage(
	        image.getWidth(this), image.getHeight(this),
	        BufferedImage.BITMASK);
	    Graphics2D g = newImage.createGraphics();
	    g.drawImage(image, 0, 0, null);
	    g.dispose();
	    return newImage;
	}
	
	public BufferedImage nonLinearTransform(String method, BufferedImage img, int side){
		/*
		 * side
		 * 0 -> up
		 * 1 -> right
		 * 2 -> down
		 * 3 -> left
		 * */
		int width = getWidthNewImageFunction(img, method, side);
		int addx = 0;
		int addy = 0;
		int xFunction=0;
		int yFunction=0;
		switch(side){
			case 0: case 2:
				yFunction = img.getHeight();
				xFunction = img.getWidth();
				addy = width;
				break;
			case 1: case 3:
				xFunction = img.getHeight();
				yFunction = img.getWidth();
				addx = width;
				break;
		}
		yFunction = img.getHeight();
		xFunction = img.getWidth();
		
		BufferedImage newImage = new BufferedImage(
		        img.getHeight(this) + addx, img.getHeight(this) + addy,
		        BufferedImage.BITMASK);
		Graphics2D g = newImage.createGraphics();
		double acentuar = 0;
		int anterior;
		int xDestino;
		int yDestino;
		for(int x=0;x<xFunction;x++){
			if(side==3 || side==0){
				acentuar = x*1.0/xFunction;
			}else{
				acentuar = ((xFunction-x)*1.0)/xFunction;
			}
			anterior = 0;
			for(int y=1;y<yFunction;y++){
				try{
					if(img.getRGB(x, y)==0){
						g.setColor(new Color(0,10,0,1));
					}else{
						g.setColor(new Color(img.getRGB(x, y)));
					}
				}catch (Exception e) {
					// TODO: handle exception
					System.out.println(x+" "+y);
					System.out.println(img.getWidth()+" "+img.getHeight());
					System.out.println("");
				}
				Method m;
				int xi = x;
				double ac = 0;
				try {
					m = this.getClass().getDeclaredMethod(method, Integer.class, Integer.class, Double.class, Double.class);
					int val = (Integer)m.invoke(this, y, yFunction,50.0,10.0);
					ac = val*acentuar;
				if(side==3 || side==0){
					xi = xi + val - (int)ac;
				}else{
					xi = xi - val + (int)ac;
				}
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				yDestino = 0;
				xDestino = 0;
				switch(side){
				case 0: case 2:
					yDestino = xi;
					xDestino = y;
					break;
				case 1: case 3:
					xDestino = xi;
					yDestino = y;
					break;
				}
				
				if(side==1 || side == 3){
		
					if(anterior==0){
						g.drawLine(xDestino,yDestino,xDestino,yDestino);
					}else{
						g.drawLine(xDestino,yDestino,anterior,yDestino);
					}
				}else{
					if(anterior==0){
						g.drawLine(yDestino,xDestino,yDestino,xDestino);
					}else{
						g.drawLine(yDestino,xDestino,yDestino, anterior);
					}
					
				}
				anterior = xDestino;
				
				//g.drawLine(x + function(y, img.getHeight(this),0.2,10),y, x + function(y-1, img.getHeight(this),0.2,10), y-1);
			}
		}
		g.dispose();
		/*int toRotate = 0;
		switch(side){
			case 0:
				toRotate = 270;
				break;
			case 1:
				toRotate = 270;
				break;
			case 2:
				toRotate = 270;
				break;
			case 3:
				toRotate = 0;
				break;
		}
		 AffineTransform transform = new AffineTransform();
		 transform.rotate(Math.toRadians(toRotate), newImage.getWidth()/2, newImage.getHeight()/2);
		 AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
		 newImage= op.filter(newImage, null);
		*/
		return newImage;
	}
	
	
	@Override
	public void paint(Graphics g){
		g.drawImage(nonLinearTransform( PRESET_FUNCTION[0], imag,0), 0, 0, this);
		g.drawImage(nonLinearTransform( PRESET_FUNCTION[0], imag,1),150, 0, this);
		g.drawImage(nonLinearTransform( PRESET_FUNCTION[0], imag,2),0, 150, this);
		g.drawImage(nonLinearTransform( PRESET_FUNCTION[0], imag,3), 150, 150, this);
		g.drawImage(imag, 0, 300, this);
	}
	
	private int getWidthNewImageFunction(BufferedImage img, String method,int side) {
		int max = 0;
		int value = 0;
		Method m;
		int width = img.getWidth();
		int height = img.getHeight();
		int yfunction = 0;
		int xfunction = 0;
		switch(side){
			case 0:
				yfunction = height;
				xfunction = width;
				break;
			case 1:
				yfunction = width;
				xfunction = height;
				break;
			case 2:
				yfunction = width;
				xfunction = height;
				break;
			case 3:
				yfunction = width;
				xfunction = height;
				break;
		};
		
		
		
		for(int i=0;i<xfunction;i++){
			try {
				m = this.getClass().getDeclaredMethod(method, Integer.class, Integer.class, Double.class, Double.class);
				value = (Integer)m.invoke(this, i , yfunction,50.0,10.0);
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(value>max) max = value;
		}
		return max;
	}


	public static void main(String[] args){

		NonlinearAffineTransformation nl = new NonlinearAffineTransformation(200,200);
		JFrame frame = new JFrame();
		frame.add(nl);
		frame.setSize(500, 500);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

}
