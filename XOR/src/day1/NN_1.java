package day1;

/*
 * 제작자 : 나영채 
 * 개요   : 인공신경망 공부 XOR 모델 만들어보기
 * 목적   : 공부
 */

public class NN_1 {
	
	static double input [][] = {{0,0},{0,1},{1,0},{1,1}};
	
	public static void main(String [] args){
		Unit unit = new Unit();
		System.out.println("input\t hidden\t output");
		for(int i=0;i<4;i++){
			for(int j=0;j<2;j++)
				System.out.print(input[i][j]+" ");
			System.out.print("  ");
			unit.start(input[i]);
			System.out.print("  ");
			Unit2 unit2 = new Unit2();
			unit2.start(unit.sum);
		}
	}
}

class Unit{
	
	double weight1[][] = {{5,5},{-7,-7}};
	double bias1[] = {-8,3};
	
	double matmul(double x,double w){
		return x * w;
	}
	
	double sum[];
	void start(double x[]){
		sum = new double[2];
		for(int i=0;i<2;i++){
			for(int j=0;j<2;j++){
				sum[i] += matmul(x[j], weight1[i][j]);
			}
			sum[i] += bias1[i];
			sum[i] = actFunction(sum[i]);
			System.out.print(sum[i]+" ");
		}
	}
	double actFunction(double k){
		
		//return 1/(1+Math.exp(-k)); //sigmoid
		//return Math.tanh(k);
		return Math.max(0, k); //ReLU
	}
}

class Unit2{
	
	double weight2[] = {-11,-11};
	double bias2 = 6;
	
	double sum;
	void start(double hidden[]){
		
		for(int i=0;i<2;i++){
			sum += hidden[i] * weight2[i];
		}
		sum += bias2;
		sum = actFunction(sum);
		System.out.println(Math.round(sum));
	}
	double actFunction(double k){
		/*
		 * 혹시나 해서 언급하지만 Activation Fuction으로
		 * ReLU를 이용하더라도 마지막 Layer는 Sigmoid(또는 tanh)를 이용한다. 
		 * 0.5를 기준으로 0 ~ 1사이의 값을 나타내야 정확히 분류를 하는데 
		 * 좋기 때문이다.
		 * 참고함
		 */
		return 1/(1+Math.exp(-k)); //sigmoid
		//return Math.tanh(k);
		//return Math.max(0, k); //ReLU
	}
}
