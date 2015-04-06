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
	
	   public AffineTransformation() {
		   super();
	   }

	   public BufferedImage getAffineTransformation(BufferedImage img, int MaxX, int MaxY){
		original = img;
	    Graphics2D g = img.createGraphics();
	    g = getGraphicsScale(g);
	    g = getGraphicsShear(g);
	    g = getGraphicsTranslate(g);
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
		  g.rotate(Math.random()*10 * Math.PI / 180.0);
		  return g;
	}

	private Graphics2D getGraphicsTranslate(Graphics2D g) {
		  g.translate(Math.random(),Math.random());
		  return g;
	}

	private Graphics2D getGraphicsShear(Graphics2D g) {
		 int h = (int)(Math.random()*5);
		 for(int i=0;i<h;i++){
			 g.shear(0.5, 0.15);
			 if(Math.random()<0.6){
				 g = getGraphicsRotate(g);
			 }
		 }
		 return g;
	}

	private Graphics2D getGraphicsScale(Graphics2D g) {
			g.scale(Math.random(),Math.random());
		return g;
	}

	public void paint(Graphics g) {
	    /*Graphics2D g2d = (Graphics2D) g;
	    AffineTransform affineTransform1 = AffineTransform.
	          getTranslateInstance(0, 0);
	    g2d.transform(affineTransform1);
	    g2d.setPaint(Color.red);
	    BufferedImage img = null;
		try{
			img = ImageIO.read(new BufferedInputStream(new FileInputStream("src/fisho.gif")));
		}catch(Exception e){
			System.out.println("Error "+e);
		}
	    g2d.drawImage(img, 10, 10, img.getHeight(), img.getHeight(), this);

	    AffineTransform affineTransform2 = AffineTransform.
	          getTranslateInstance(0, 0);
	      affineTransform2.shear(-.7, -.2);
	      g2d.transform(affineTransform2);
	      g2d.transform(AffineTransform.getTranslateInstance(100, 100));
	      
	    Stroke stroke = new BasicStroke(1, BasicStroke.CAP_BUTT,
	  	        BasicStroke.JOIN_BEVEL, 0, new float[] { 3, 1 }, 0);
	  	    g2d.setStroke(stroke);

	      
	    AffineTransform affineTransform3 = AffineTransform.
		          getTranslateInstance(0, 0);
		      affineTransform3.shear(-0.7,-0.1);
		      g2d.transform(affineTransform3);
		      g2d.transform(AffineTransform.getTranslateInstance(100, 100));
	      
	    stroke = new BasicStroke(1, BasicStroke.CAP_BUTT,
	        BasicStroke.JOIN_BEVEL, 0, new float[] { 3, 1 }, 0);
	    g2d.setStroke(stroke);
	    g2d.drawImage(img, 10, 10, img.getHeight(), img.getHeight(), this);
	    
	    //*/
		Image or = original.getScaledInstance(150, 150, 100);
	    g.drawString("Original (Scaled)", (100+or.getWidth(this))/2, 50);
	    g.drawImage(or, 100, 100, this);
	    g.drawString("Transformado", (100+or.getWidth(this)+im.getWidth())/2, 50);
	    g.drawImage(im, 100+or.getWidth(this), 100, this);
	  }
	  public static void main(String[] a) {
		  
		  
	    JFrame frame = new JFrame("Shear the oval");
	    AffineTransformation tx = new AffineTransformation();
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