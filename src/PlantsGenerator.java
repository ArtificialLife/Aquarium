import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class PlantsGenerator extends JFrame{

	public PlantsGenerator(){
		super();
	}
	
	
	public static void main(String[] args){
		String axiom = "F";
		//TREE 1
		Map<String, String> rules1 = new TreeMap<String, String>();
		rules1.put("F","FF-[-F+F+F]+[+F-F-F]");
		int iteraciones = 5;
		Grammar lSystem = new Grammar(axiom, rules1, iteraciones);
		Parser parser1 = new Parser(280, 50, Color.GREEN, Color.RED, lSystem);
		parser1.setSize(1000, 600);
		parser1.setVisible(true);
		Thread tree1 = new Thread(parser1);
		tree1.start();
		
		//TREE 2
		/*Map<String, String> rules2 = new TreeMap<String, String>();
		rules2.put("F","FF-[-F+F+F]+[+F-F-F]");
		int iteraciones2 = 5;
		Grammar lSystem2 = new Grammar(axiom, rules2, iteraciones2);
		Parser parser2 = new Parser(880, 50, Color.green, new Color(90,53,45),lSystem2);
		parser2.setSize(1000, 600);
		parser2.setVisible(true);
		Thread tree2 = new Thread(parser2);
		tree2.start();*/
		
		//TREE 3
		/*Map<String, String> rules3 = new TreeMap<String, String>();
		rules3.put("F","F[+F]F[-F][F]");
		int iteraciones3 = 5;
		Grammar lSystem3 = new Grammar(axiom, rules3, iteraciones3);
		Parser parser3 = new Parser(220, 50, Color.green, Color.GRAY,lSystem3);
		parser3.setSize(1000, 600);
		parser3.setVisible(true);
		Thread tree3 = new Thread(parser3);
		tree3.start();
		/*1
		//TREE 4
		Map<String, String> rules4 = new TreeMap<String, String>();
		rules4.put("F","F[+F]F[-F][F]");
		int iteraciones4 = 4;
		Grammar lSystem4 = new Grammar(axiom, rules4, iteraciones4);
		Parser parser4 = new Parser(610, 50, Color.GREEN , Color.lightGray,lSystem4);
		parser4.setSize(1000, 600);
		parser4.setVisible(true);
		Thread tree4 = new Thread(parser4);
		tree4.start();

		//TREE 5
		Map<String, String> rules5 = new TreeMap<String, String>();
		rules5.put("F","FF-[-F+F+F]+[+F-F-F]");
		int iteraciones5 = 4;
		Grammar lSystem5 = new Grammar(axiom, rules5, iteraciones5);
		Parser parser5 = new Parser(400, 50, new Color(90,53,45), Color.black,lSystem5);
		parser5.setSize(1000, 600);
		parser5.setVisible(true);
		Thread tree5 = new Thread(parser5);
		tree5.start();
		/*
		//TREE 6
		Map<String, String> rules6 = new TreeMap<String, String>();
		rules6.put("F","FF-[-F+F+F]+[+F-F-F]");
		int iteraciones6 = 4;
		Grammar lSystem6 = new Grammar(axiom, rules6, iteraciones6);
		Parser parser6 = new Parser(460, 80, Color.green, Color.cyan,lSystem6);
		parser6.setSize(1000, 600);
		parser6.setVisible(true);
		Thread tree6 = new Thread(parser6);
		tree6.start();
		*/
		//TREE 7
		Map<String, String> rules7 = new TreeMap<String, String>();
		rules7.put("F","F[+F]F[-F][F]");
		int iteraciones7 = 5;
		Grammar lSystem7 = new Grammar(axiom, rules7, iteraciones7);
		Parser parser7 = new Parser(1080, 120, new Color(90,53,45), Color.green,lSystem7);
		parser7.setSize(1300, 770);
		parser7.setVisible(true);
		Thread tree7 = new Thread(parser7);
		tree7.start();
/*
		//TREE 8
		Map<String, String> rules8 = new TreeMap<String, String>();
		rules8.put("X","F-[[X]+X]+F[+FX]-X");
		rules8.put("F","FF");
		int iteraciones8 = 4;
		Grammar lSystem8 = new Grammar("X", rules8, iteraciones8);
		Parser parser8 = new Parser(580, 130, new Color(90,53,45), Color.black,lSystem8);
		parser8.setSize(1000, 600);
		parser8.setVisible(true);
		Thread tree8 = new Thread(parser8);
		tree8.start();
				*/
		
		
		PlantsGenerator aquarium = new PlantsGenerator();
		//Fish 1
		/*Fish fish1 = new Fish("src/fisho.gif",30,20, 0.16,0.08,0.045, 0.06,0.6f, 0.9f);
		fish1.setVisible(true);
		fish1.setSize(1000, 600 );
		PlantsGenerator aquarium = new PlantsGenerator();
		aquarium.getContentPane().add(fish1);
		int fishNumber = 4;
		Thread[] threads = new Thread[fishNumber];
		threads[0]= new Thread(fish1);
		threads[0].start();
		
		//The rest of the fishes
		try{
			for(int j=1;j<fishNumber;j++){
				Fish aux = (Fish)fish1.clone();
				threads[j] = new Thread(aux);
				aquarium.getContentPane().add(aux);
				threads[j].start();
			}
		}catch(Exception e){
			e.printStackTrace();
		}*/
		int start = 100;
		List<Pez> peces = new LinkedList<Pez>();
		List<Thread> threads = new LinkedList<Thread>();
		int x,y;
		int r;
		String path;
		int[] h;
		for(int i=0;i<start;i++){
			x = (int)(Math.random()*1200);
			y = (int)(Math.random()*720);
			r = (int)(Math.random()*10);
			path = Pez.pathimgPerRace[r];
			h = Pez.genPerRace[r].clone();
			//peces.add(new Pez(x+Pez.positionRaces[r][0],y + Pez.positionRaces[r][1], path,h));
			peces.add(new Pez(x,y, path,h));
			peces.get(i).setSize(1300,770);
			peces.get(i).setVisible(true);
			threads.add(new Thread(peces.get(i)));
			threads.get(i).start();
			aquarium.getContentPane().add(peces.get(i));
		}
			
		
		//Configure View
		aquarium.getContentPane().add(parser1);
		//aquarium.getContentPane().add(parser2);
		//aquarium.getContentPane().add(parser3);
		//aquarium.getContentPane().add(parser4);
		//aquarium.getContentPane().add(parser5);
		//aquarium.getContentPane().add(parser6);
		aquarium.getContentPane().add(parser7);
		//aquarium.getContentPane().add(parser8);*/
		Background bg = new Background(0,0);
		bg.setSize(1300, 770);
		bg.setVisible(true);
		aquarium.add(bg);
		aquarium.setSize(1300,770);
		aquarium.setResizable(true);
		aquarium.setTitle("Neill's Aquarium");
		aquarium.setDefaultCloseOperation(EXIT_ON_CLOSE);
		aquarium.setVisible(true);
		Reproduccion rep = new Reproduccion(peces, aquarium, threads,bg);
		Thread tr = new Thread(rep);
		tr.start();
		
	}
}

class Reproduccion implements Runnable{

	List<Pez> peces;
	List<Thread> threads;
	JFrame frame;
	Background bg;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			Thread.sleep(1000);
		}catch(Exception e){
			
		}
		Pez pez1;
		Pez pez2;
		while(true){
			pez1 = selectOne(peces);
			if(pez1 == null) continue;
			pez2 = selectTwo(peces, pez1);
			if(pez2 == null) continue;
			pez1 = pez1.reproduce(pez2);
			peces.add(pez1);
			pez1.setSize(1300,770);
			pez1.setVisible(true);
			threads.add(new Thread(pez1));
			threads.get(threads.size()-1).start();
			frame.getContentPane().remove(bg);
			frame.getContentPane().add(pez1);
			frame.getContentPane().add(bg);
		}
		
	}
	
	private Pez selectTwo(List<Pez> peces2, Pez pez1) {
		for(int i=0;i<peces.size();i++){
			if(peces.get(i).x < 200 && peces.get(i).y < 200 && peces.get(i)!=pez1){
				return peces.get(i);
			}
		}
		return null;
	}

	private Pez selectOne(List<Pez> peces) {
		// TODO Auto-generated method stub
		for(int i=0;i<peces.size();i++){
			if(peces.get(i).x < 200 && peces.get(i).y < 200){
				return peces.get(i);
			}
		}
		return null;
	}

	public Reproduccion(List<Pez> peces, JFrame frame, List<Thread> threads, Background bg){
		this.bg = bg;
		this.frame = frame;
		this.peces = peces;
		this.threads = threads;
	}
	
}
