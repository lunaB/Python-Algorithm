package day1;

/*
 * ������ : ����ä 
 * ����   : �ΰ��Ű�� ���� XOR �� ������
 * ����   : ����
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
		 * Ȥ�ó� �ؼ� ��������� Activation Fuction����
		 * ReLU�� �̿��ϴ��� ������ Layer�� Sigmoid(�Ǵ� tanh)�� �̿��Ѵ�. 
		 * 0.5�� �������� 0 ~ 1������ ���� ��Ÿ���� ��Ȯ�� �з��� �ϴµ� 
		 * ���� �����̴�.
		 * ������
		 */
		return 1/(1+Math.exp(-k)); //sigmoid
		//return Math.tanh(k);
		//return Math.max(0, k); //ReLU
	}
}
