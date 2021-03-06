	import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

public class AffineTransformation extends JComponent {
	
	BufferedImage original;	
	BufferedImage im;
	double scalex;
	double scaley;
	double shearx;
	double sheary;
	double rot;
	
	   public AffineTransformation(double sx , double sy, double shx, double shy, double rot) {
		   super();
		    scalex = sx;
		    scaley = sy;
		    shearx = shx;
		    sheary = shy;
		    this.rot = rot;
		   
	   }

	   public BufferedImage getAffineTransformation(BufferedImage img, int MaxX, int MaxY){
		original = img;
	    Graphics2D g = img.createGraphics();
	    g = getGraphicsScale(g);
	    g = getGraphicsShear(g);
	    g = getGraphicsRotate(g);
	    BufferedImage displayImage =new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.BITMASK);
	    Graphics2D ger = displayImage.createGraphics();
	    if(img.getWidth()>MaxX || img.getHeight()>MaxY){
	    	g.scale(0.7, 0.7);
	    }
	    ger.drawImage(img, g.getTransform(), this);
	    im = displayImage;
	    return displayImage;
		   
	   }
	   
	  private Graphics2D getGraphicsRotate(Graphics2D g) {
		  g.rotate(Math.toRadians(rot));
		  return g;
	}

	private Graphics2D getGraphicsShear(Graphics2D g) {
		 g.shear(shearx, sheary);
		 //g = getGraphicsRotate(g);
		 return g;
	}

	private Graphics2D getGraphicsScale(Graphics2D g) {
		g.scale(scalex, scaley);
		return g;
	}

	public void paint(Graphics g) {
	  
		Image or = original.getScaledInstance(150, 150, 100);
	    g.drawString("Original (Scaled)", (100+or.getWidth(this))/2, 50);
	    g.drawImage(or, 100, 100, this);
	    g.drawString("Transformado", (100+or.getWidth(this)+im.getWidth())/2, 50);
	    g.drawImage(im, 100+or.getWidth(this), 100, this);
	  }
	  public static void main(String[] a) {
		  
		  
	    JFrame frame = new JFrame("Shear the oval");
	    double scalex = 0.7;
		double scaley = 0.7;
		double shearx = -0.90;
		double sheary = 0.25;
		double rot = -15.0;
	    AffineTransformation tx = new AffineTransformation(scalex, scaley,shearx,sheary,rot);
	    BufferedImage img = null;
		try{
			img = ImageIO.read(new BufferedInputStream(new FileInputStream("src/fisho.gif")));
		}catch(Exception e){
			System.out.println("Error "+e);
		}
		tx.getAffineTransformation(img,500,300);
	    frame.getContentPane().add(tx);
	    frame.setSize(1000, 650);
	    frame.setVisible(true);
	  }
	}  