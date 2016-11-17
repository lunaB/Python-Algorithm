package day2;

import java.util.Random;

public class BackProp{
	
	double input [][] = {{0,0},{0,1},{1,0},{1,1}};
	double target [] = {0,1,1,0};
	
	public static void main(String [] args){
		BackProp bp = new BackProp();
		bp.start();
	}
	
	public void start(){
		
		NN nn = new NN();
		
		for(int cnt=0;cnt<10;cnt++){ //ÇÐ½ÀÈ½¼ö 10
			for(int i=0;i<input.length;i++){ // 4
				for(int j=0;j<input[i].length;j++){ //2
					
				}
			}
		}
	}
	
}

//------------------------------------------
class NN{
	Random rand = new Random();
	
	int inputLayer = 2;
	int hiddenLayer[] = {2,1};
	int outputLayer = 1;
	
	double input[] = new double[inputLayer];
	Hidden hidden[] = new Hidden[hiddenLayer.length];
	double output[] = new double[outputLayer];
	
	public NN(){
		for(int i=0;i<hiddenLayer.length;i++){
			hidden[i] = new Hidden(hiddenLayer[i]);
		}
	}
	
}

/*
 * --OOO
 *   O O--------
 * --OOO   l
 *         l
 * --------l
 * 
 * f=g(x)+b
 * g(x)=w*x
 * 
 * 
 */

class Hidden{
	
	int unit=0;
	
	public Hidden(int unit){
		this.unit = unit;
	}
	
	//------------------------------------------
	
	
	
	double ReLU(double x){
		return Math.max(0, x);
	}
	double sigmoid(double x){
		return 1/(1+Math.exp(-x));
	}
	void feedForward(double x[],double weight,double bias){
		
		double sigma[] = new double[2];
		
		for(int i=0;i<x.length;i++){
			
		}
	}
	
}