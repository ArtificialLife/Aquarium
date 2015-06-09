import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
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
	
	boolean trans;
	public NonlinearAffineTransformation(boolean trans, int width, int height){
		super();
		this.trans = trans;
		this.width = width;
		this.height = height;
		try{
			imag = ImageIO.read(new BufferedInputStream(new FileInputStream("src/fishy.gif")));
		}catch(Exception e){
			e.printStackTrace();
		}
		Image im = imag.getScaledInstance(150,150,100);
		imag = this.convertToBufferedImage(im);
	}
	
	
	public static Integer xto2(Integer x, Integer size, Double a, Double b){
		size = size/2;
		x = x - size;
		return  (int)Math.pow(x, 2)/100;
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
	
	public BufferedImage nonLinearTransform(String method, BufferedImage img){
		
		int width = getWidthNewImageFunction(img);
		
		BufferedImage newImage = new BufferedImage(
		        width + img.getWidth(), img.getHeight(this),
		        BufferedImage.BITMASK);
		Graphics2D g = newImage.createGraphics();
		for(int x=0;x<img.getWidth(this);x++){
			for(int y=1;y<img.getHeight(this);y++){
				try{
					if(img.getRGB(x, y)==-1){
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
				try {
					m = this.getClass().getDeclaredMethod(method, Integer.class, Integer.class, Double.class, Double.class);
					xi = xi + (Integer)m.invoke(this, y, img.getHeight(this),0.2,10.0);
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
				g.drawLine(xi,y,xi,y);
				//g.drawLine(x + function(y, img.getHeight(this),0.2,10),y, x + function(y-1, img.getHeight(this),0.2,10), y-1);
			}
		}
		g.dispose();
		return newImage;
	}
	
	
	@Override
	public void paint(Graphics g){
		g.drawImage(nonLinearTransform("functionSin", imag), 0, 0, this);
	}
	
	private int getWidthNewImageFunction(BufferedImage img) {
		int max = 0;
		int value;
		for(int i=0;i<img.getWidth();i++){
			value = function(i,img.getHeight(),0.2,10);
			if(value>max) max = value;
		}
		return max;
	}


	public static void main(String[] args){

		NonlinearAffineTransformation nl = new NonlinearAffineTransformation(true, 200,200);
		JFrame frame = new JFrame();
		frame.add(nl);
		frame.setSize(500, 500);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

}
