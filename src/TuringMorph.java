import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JComponent;
import javax.swing.JFrame;

import org.omg.CORBA.INTERNAL;


public class TuringMorph extends JComponent implements Runnable {

	int width;
	int height;
	int a = 0;
	
	float r;
	float gr;
	
	double diffU;
	double diffV;
	double paramF;
	double paramK;
	
	boolean rndInitCondition;
	 
	double[][] U;
	double[][] V;
	 
	double[][] dU;
	double[][] dV;
	 
	int[][] offset;
	
	public static void main(String[] args) throws InterruptedException{
		JFrame frame = new JFrame();
		
		double diffU1 = 0.16;
		double diffV1 = 0.08;
		double paramF1 = 0.045;
		double paramK1 = 0.06;
		
		TuringMorph j = new TuringMorph(400, 400 , 0.67f, 0.34f, diffU1, diffV1, paramF1, paramK1);
		j.setSize(700,700);
		j.setVisible(true);
		frame.getContentPane().add(j);
		frame.setSize(700, 700);
		frame.setVisible(true);
		j.setUp();
		Thread t = new Thread(j);
		t.start();
		
	}
	
	@Override
	public void paint(Graphics g){
		for(int i=0;i<width;i++){
			for(int j=0;j<height;j++){
				g.setColor((new Color(r,gr,(float)U[i][j])));
				g.drawLine(i, j, i, j);
			}
		}
	}
	
	public TuringMorph(int width, int height, float r, float gr, double diffU, double diffV, double paramF, double paramk){
		super();
		this.width = width;
		this.height = height;
		this.U = new double[width][height];
		this.V = new double[width][height];
		 
		this.dU = new double[width][height];
		this.dV = new double[width][height];
		 
		this.offset = new int[width][2];
		this.r = r;
		this.gr = gr;
		this.diffU = diffU;
		this.diffV = diffV;
		this.paramF = paramF;
		this.paramK = paramk;
	}
	
	public void generateInitialState() {
	    for (int i = 0; i < width; i++) {
	        for (int j = 0; j < height; j++) {
	          U[i][j] = 1.0;
	          V[i][j] = 0.0;
	        }
	      }
	       
	      if (rndInitCondition) {
	          for (int i = width/3; i < 2*width/3; i++) {
	              for (int j = height/3; j < 2*height/3; j++) {    
	                 U[i][j] = 0.5*(1 + Math.random());
	                 V[i][j] = 0.25*( 1 + Math.random());
	            }
	          }
	      } else {
	        for (int i = width/3; i < 2*width/3; i++) {
	              for (int j = height/3; j < 2*height/3; j++) {    
	                 U[i][j] = 0.5;
	                 V[i][j] = 0.25;
	            }
	          }
	      }
	  }
	
	public void setUp(){
		
		rndInitCondition = true;
	    
		generateInitialState();
		
		for (int i = 1; i < width-1; i++) {
		      offset[i][0] = i-1;
		      offset[i][1] = i+1;
		    }
		     
		    offset[0][0] = width-1;
		    offset[0][1] = 1;
		     
		    offset[width-1][0] = width-2;
		    offset[width-1][1] = 0;
	}
	
	public synchronized void timestep(double F, double K, double diffU, double diffV) {
	      for (int i = 0; i < width; i++) {
	          for (int j = 0; j < height; j++) {
	            int p = i + j*height;
	             
	            double u = U[i][j];
	            double v = V[i][j];
	             
	            int left = offset[i][0];
	            int right = offset[i][1];
	            int up = offset[j][0];
	            int down = offset[j][1];
	             
	            double uvv = u*v*v;    
	          
	            double lapU = (U[left][j] + U[right][j] + U[i][up] + U[i][down] - 4*u);
	            double lapV = (V[left][j] + V[right][j] + V[i][up] + V[i][down] - 4*v);
	             
	            dU[i][j] = diffU*lapU  - uvv + F*(1 - u);
	            dV[i][j] = diffV*lapV + uvv - (K+F)*v;
	          }
	        }
	         
	         
	      for (int i= 0; i < width; i++) {
	        for (int j = 0; j < height; j++){
	            U[i][j] += dU[i][j];
	            V[i][j] += dV[i][j];
	        }
	      }
	  }
	public synchronized double[][] getU() {
		return U;
	}
	
	public int getA() {
		return a;
	}
	public void run(){
		Integer be=0;
		while(be<Integer.MAX_VALUE){
		for (int k = 0; k < 10; k++) {
		       timestep(paramF, paramK, diffU, diffV);
		    }
	    repaint();
	    be++;
	    a++;
		}
	}
}
