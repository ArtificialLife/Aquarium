
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Fish extends JComponent implements Cloneable, Runnable {

	
	public static double[][] PRESET = { // Values for Turing Morph 
		{0.16,0.08,0.035,0.06},
		{0.16,0.08,0.042, 0.065},
		{0.18,0.13,0.025, 0.056},
		{0.18,0.09,0.02, 0.056},
		{0.14, 0.06,0.035,0.065},
		{0.19, 0.09, 0.062,0.062},
		{0.16,0.08,0.05, 0.065}		
	};

	public static double[][] PRESET2 = { // Values for Affinetransformation
		{1.4, 0.2, 0.32,-0.08,45.0}, //ok
		{0.7, 0.7, -0.82,0.40,-25.0}, // ok
		{0.6, 0.7, 0.2,0.60,30.0}, //ok
		{0.7, 0.7, 0.2,0.90,-40.0}, //ok
		{1,   0.12, 0.32,-0.08,45.0}, //ok
		{0.9, 0.2, -0.82,0.40,-25.0}, // ok
		{0.5, 0.8, 0.2,0.60,30.0}, //ok
		{0.3, 0.2, 0.2,0.90,-40.0} //ok
		
	}; 


	
	double diffU1;
	double diffV1;
	double paramF1;
	double paramK1;
	Image im ;
	int zoom;
	int startX;
	int startY;
	double[][] paint;
	TuringMorph jo;
	Thread t;
	float r,gr;
	
	public Fish(String path, int x, int y, double du, double dv, double pf, double pk, float r, float gr){
		super();
		this.GetImage();
		this.setTransformationAffine();
		startX = x;
		startY = y;
		diffU1 = du;
		diffV1 = dv;
		paramF1 = pf;
		paramK1 = pk;
		this.r = r;
		this.gr = gr;
	}
	
	public void GetImage(){
		BufferedImage img = null;
		try{
			img = ImageIO.read(new BufferedInputStream(new FileInputStream("src/fisho.gif")));
		}catch(Exception e){
			System.out.println("Error "+e);
		}
		BufferedImage bufferedImage = new BufferedImage(img.getWidth(this), img.getHeight(this), BufferedImage.BITMASK);
		for(int i=0;i<img.getWidth(this);i++){
			for(int j = 0;j<img.getHeight(this);j++){
				if(img.getRGB(i, j)==Color.WHITE.getRGB()||img.getRGB(i, j)==Color.white.getRGB()){
					bufferedImage.setRGB(i, j, new Color(r,gr,0.87f).getRGB());
				}else{
					bufferedImage.setRGB(i, j, img.getRGB(i,j));
				}
			}
		}
		im = bufferedImage.getScaledInstance(bufferedImage.getWidth(), bufferedImage.getHeight(), 100);
		Random r = new Random();
		zoom = 200;
		im = im.getScaledInstance(zoom, zoom, 100);
	}
	
	
	
	public void setTransformationAffine(){
		int prese = (int)(Math.random()*PRESET2.length);
		AffineTransformation at = new AffineTransformation(PRESET2[prese][0],PRESET2[prese][1],PRESET2[prese][2],PRESET2[prese][3],PRESET2[prese][4]);
		im = new ImageIcon(at.getAffineTransformation(convertToBufferedImage(im), 700, 350)).getImage();
	}
	
	@Override
	public synchronized void paint(Graphics g){
		/*if(startX+im.getWidth(this)>1000){
			if(startX - (startX + im.getWidth(this)-1000)<0){
				im = im.getScaledInstance(im.getWidth(this)/2, im.getHeight(this)/2, 100);
			}else{
				startX = startX - (startX + im.getWidth(this)-1000);
			}
		}
		if(startY+im.getHeight(this)>600){
			if(startY - (startY + im.getHeight(this)-600)<0){
				im = im.getScaledInstance(im.getWidth(this)/2, im.getHeight(this)/2, 100);
			}else{
				startY = startY - (startY + im.getHeight(this)-600);
			}
		}*/
		BufferedImage bufferedImage = new BufferedImage(im.getWidth(this), im.getHeight(this), BufferedImage.BITMASK);
		BufferedImage img = convertToBufferedImage(im);
		try{
		for(int i=0;i<img.getWidth(this);i++){
			for(int j = 0;j<img.getHeight(this);j++){
				if(img.getRGB(i, j)==0) continue;
				if(img.getRGB(i, j)!=Color.BLACK.getRGB()||img.getRGB(i, j)!=Color.black.getRGB()){
					bufferedImage.setRGB(i, j, new Color(r,gr,(float)paint[i][j]).getRGB());
				}else{
					bufferedImage.setRGB(i, j, img.getRGB(i,j));
				}
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		g.drawImage(bufferedImage, startX, startY, this);
		
	}
	
	protected BufferedImage applyTransform(BufferedImage bi,
            AffineTransform atx,
            int interpolationType){
		Dimension d = getSize();
		BufferedImage displayImage =new BufferedImage(d.width, d.height, interpolationType);
		Graphics2D dispGc = displayImage.createGraphics();
		dispGc.drawImage(bi, atx, this);
		return displayImage;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		Random r = new Random();
		int startX = (int)(r.nextFloat()*800);
		int startY = (int)(r.nextFloat()*400);
		float uno = (float)Math.random();
		float dos = (float)Math.random();
		int ran = (int)(Math.random()*7);
		Fish fish =  new Fish("", startX, startY,PRESET[ran][0],PRESET[ran][1],PRESET[ran][2],PRESET[ran][3] ,uno, dos);
		fish.setVisible(true);
		fish.setSize(1000, 600);
		//System.out.println(im.getScaledInstance(100, 100, Image.SCALE_SMOOTH).getWidth(this)+" : "+im.getHeight(this));
		//
		//fish.setTuringMorph(Color.RED);
		//fish.setTransformationAffine();
		
		return fish;
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
	
	public static void main(String[] args){
		JFrame myframe = new JFrame();
		Fish fish = new Fish("src/fisho.gif", 50, 80, 0.16, 0.08, 0.045, 0.06,0.8f,0.5f);
		fish.setSize(1000,600);
		fish.setVisible(true);
		Thread thread = new Thread(fish);
		thread.start();
		myframe.getContentPane().add(fish);
		myframe.setVisible(true);
		myframe.setSize(1000, 600);
		myframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void run() {
		//Turing
		BufferedImage img = convertToBufferedImage(im);
		jo = new TuringMorph(img.getWidth(this), img.getHeight(this) , 0.67f, 0.34f, diffU1, diffV1, paramF1, paramK1);
		jo.setUp();
		jo.setSize(img.getWidth(this),img.getHeight(this));
		jo.setVisible(true);
		t = new Thread(jo);
		t.start();
		double x = 0;
		while(t.isAlive()){
			repaint();
			paint = jo.getU();
			if(paint==null) break;
			startX = startX + (int)x;
			x = x + 0.00000002;
			if(startX>1000) startX = 0;
		}
		
	}
	
	
}