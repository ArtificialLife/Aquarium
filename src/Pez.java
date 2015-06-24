import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;


public class Pez extends JComponent implements Runnable{
	BufferedImage img;
	String pathImg;
	int x;
	int y;
	int[] gen;
	NonlinearAffineTransformation nat;
	String race;
	boolean leader = false;
	Pez lider;
	
	public void setLider(){
		leader = true;
	}
	
	static String[] pathimgPerRace = {
		"src/raza0.png", //Raza0
		"src/raza1.png", //Raza1
		"src/raza2.png", //Raza2
		"src/raza3.png", //Raza3
		"src/raza4.png", //Raza4
		"src/raza5.png", //Raza5
		"src/raza6.png", //Raza6
		"src/raza7.png", //Raza7
		"src/raza8.png", //Raza8
		"src/raza9.png", //Raza9
	};
	static int[][] genPerRace = {
		{0,1,0,1, 5,2,250}, // raza0
		{1,1,2,1, 7,2,250}, // raza1
		{2,1,1,1, 2,3,250}, // raza2
		{0,1,2,1, 1,2,250}, // raza3
		{2,1,3,1, 4,6,250}, // raza4
		{1,1,1,1, 2,4,250}, // raza5
		{2,1,0,1, 5,1,250}, // raza6
		{1,1,2,1, 4,2,250}, // raza7
		{0,1,1,1, 2,10,250}, // raza8
		{0,1,0,1, 1,2,250}, // raza9
		
		/*
		 * 0 - trans 1
		 * 1 - lado 1
		 * 2 - trans 2
		 * 3 - lado 2
		 * 4 - movimientox
		 * 5 - movimientoy
		 * 6 - velocidad
		 * 
		 * 
		 * */
		
	};
	
	static String[] races = {
		"raza0",
		"raza1",
		"raza2",
		"raza3",
		"raza4",
		"raza5",
		"raza6",
		"raza7",
		"raza8",
		"raza9",
		
	};
	
	static int[][] positionRaces ={
		{0,0},
		{0,200},
		{200,500},
		{0,500},
		{900,300},
		{700,100},
		{700,400},
		{900,200},
		{100,600},
		{900,500}
		
	};
	


	public Pez(int x, int y, String pathImg, int[] gen, Pez lider){
		this.lider = lider;
		this.gen = gen.clone();
		this.pathImg = pathImg;
		try{
			img = ImageIO.read(new BufferedInputStream(new FileInputStream(pathImg)));
		}catch(Exception e){
			System.out.println("Error "+e);
		}
		nat = new NonlinearAffineTransformation(1300, 770);
		img = convertToBufferedImage(img.getScaledInstance(50,50, Image.SCALE_SMOOTH));
		img = convertToBufferedImage(nat.nonLinearTransform( NonlinearAffineTransformation.PRESET_FUNCTION[gen[0]], img, gen[1]));
		
		this.x = x;
		this.y = y;
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
	
	@Override
	public void paint(Graphics g){
		g.drawImage(img, x,y , this);
	}
	
	public static void main(String[] args){
		
		JFrame j = new JFrame();
		int[] h = {0,1,1,1,0,1,0,1, 5,-3,250};
		Pez p = new Pez(10, 10 , "src/raza0.png" ,h,null);
		p.setSize(700,700);
		p.setVisible(true);
		Thread mythread = new Thread(p);
		mythread.start();
		j.getContentPane().add(p);
		j.setSize(700, 700);
		j.setVisible(true);
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

	@Override
	public void run() {
		boolean xbackwards=false;
	    boolean ybackwards=false;
		while(true){
			repaint();
			if(leader){
				if(xbackwards){
					x = x - gen[4];
					if(x < 50) xbackwards = false;
				}else{
					x = x + gen[4];
					if(x > 1200) xbackwards = true;
				}
			
				if(ybackwards){
					y = y - gen[5];
					if(y < 50) ybackwards = false;
				}else{
					y = y + gen[5];
					if(y > 720) ybackwards = true;
				}
			}else{
				
				int x1 = lider.x;
				int y1 = lider.y;
				int distx = x - x1;
				int disty = y - y1;
				if(Math.abs(distx)>Math.abs(disty)){
					if(Math.abs(distx)>45){
						if(distx<0){
							x = x + gen[4] + 5;
						}else{
							x = x - gen[4] - 5;
						}
					}else{
						x = x + (int)(Math.random()*15);
					}
				}else{
					if(Math.abs(disty)>45){
						if(disty<0){
							y = y + gen[4] + 5;
						}else{
							y = y - gen[4] - 5;
						}
					}else{
						y = y + (int)(Math.random()*15);
					}
					
				}
				
			}
			
			try {
				Thread.sleep(gen[6]);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public Pez reproduce(Pez pez){
		String path = this.pathImg;
		int[] newGen = pez.gen;
		int x = pez.x;
		int y = pez.y;
		int random = (int)(Math.random()*(newGen.length-2));
		for(int i=random;i<newGen.length;i++){
			newGen[i] = this.gen[i];
		}
		System.out.println("Creado Pez: x "+ x + " , y "+ y );
		if(pez.lider == null){
			return new Pez(150,100,path, newGen,pez);
		}else{
			return new Pez(150,100,path, newGen,pez.lider);
		}
	}
	
	
	
}
