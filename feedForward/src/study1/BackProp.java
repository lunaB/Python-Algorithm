package study1;

import java.util.Random;

public class BackProp{
	public static void main(String [] args){
		NN nn = new NN();
	}
}

class NN{
	Random rand = new Random();
	
	double inputData [][] = {{0,0},{0,1},{1,0},{1,1}};
	double targetData [][] = {{0,0},{0,1},{0,1},{1,0}}; // xor �� and ������ ����
	
	int inputUnit = 2;
	int hiddenLayerUnit[] = {3,3};
	int outputUnit = 2;
	
	//double input[] = new double[inputUnit];
	//---���� ���� ������ �Ʒ��� ���̾� ������
	Hidden hidden[] = new Hidden[hiddenLayerUnit.length];
	Output output;
	
	double alpha = 0.01; //�н���
	
	/*
	 * ����
	 * input	hidden	 hidden  output
	 *   #		  #			#		#
	 *   #		  #			#		#
	 *   		  #			#
	 */
	
	public NN(){
		//���� [relu][sigmoid]
		
		//1
		hidden[0] = new Hidden(hiddenLayerUnit[0],inputUnit,"relu");
		hidden[0].randSetting(4, 4);
		
		//2
		hidden[1] = new Hidden(hiddenLayerUnit[1],hiddenLayerUnit[0],"relu");
		hidden[1].randSetting(4, 4);
		
		//���̾� �Ѱ�--
		output = new Output(outputUnit,hiddenLayerUnit[1],"sigmoid");
		output.randSetting(4, 4);
		//---
		
		//����
		System.out.printf(" input \t\t hidden1 \t hidden2 \t output \t target \t error");
		for(int i=0;i<inputData.length;i++){
			hidden[0].setInput(inputData[i]);//��ǲ i
			hidden[0].feedForward();
			
			hidden[1].setInput(hidden[0].output);
			hidden[1].feedForward();
			
			output.setInput(hidden[1].output);
			output.feedForward();
			output.setTarget(targetData[i]);//Ÿ�� i
			output.error();
			
			System.out.println();
			System.out.printf("%f\t%f\t%f\t%f\t%f\t%f\n",inputData[i][0],hidden[0].output[0],hidden[1].output[0],output.output[0],output.target[0],output.error[0]);
			System.out.printf("%f\t%f\t%f\t%f\t%f\t%f\n",inputData[i][1],hidden[0].output[1],hidden[1].output[1],output.output[1],output.target[1],output.error[1]);
		}
		System.out.println("Error : "+output.errorSum);
	}
	
}

//�Ѱ��� ���� ���̾�
class Hidden{
	
	Nutil nUtil = new Nutil(); //��ƿ��Ƽ act �Լ����� 
	
	//����ġ �����迭�� ���� ����
	int unit; //���ְ���
	int frontLayer; //���� ���� ����
	//�������� ��̺к��� �߰��ؾߵ�
	
	double input[];
	double output[]; //������ �ñ׸����� ����
	
	double weight[][]; //����ġ
	double bias[]; //������ ���̾�� ����ġ�� �پ�ߵ����� 1�̶� �����ϸ� ��
	String act;
	
	
	public Hidden(int unit,int frontLayer,String act){
		this.unit = unit;
		this.frontLayer = frontLayer;
		input = new double[frontLayer];
		output = new double[unit];
		weight = new double[frontLayer][unit];
		bias = new double[unit];
		this.act = act;
	}
	
	public void setInput(double input[]){
		this.input = input;
	}
	
	public void feedForward(){
		for(int i=0;i<unit;i++){
			output[i] = actFunction(f(g(i),i));
		}
	}
	
	public double actFunction(double x){
		if(act.equals("relu")){
			return nUtil.ReLU(x);
		}else if(act.equals("sigmoid")){
			return nUtil.sigmoid(x);
		}else{
			return nUtil.defAct(x);
		}
	}
	
	//sigma------------------------
	public double g(int unitNum){
		double sigma=0;
		for(int i=0;i<frontLayer;i++){
			//x1 * w11 + x2 * w21
			sigma += weight[i][unitNum]*input[i];
		}
		return sigma;
	}
	public double f(double x,int unitNum){
		return x+bias[unitNum];
	}
	//-----------------------------
	
	public void randSetting(double limW,double limB){ //-limW ~ limW ...
		Random rand = new Random();
		for(int i=0;i<unit;i++){
			for(int j=0;j<frontLayer;j++){	
				weight[j][i] = rand.nextDouble()*limW*2-limW;	
			}
			bias[i] = rand.nextDouble()*limB*2-limB;
		}
	}
}

class Output extends Hidden{
	double target[];
	double error[];
	double errorSum;
	
	public Output(int unit, int frontLayer, String act) {
		super(unit, frontLayer, act);
		target = new double[unit];
		error = new double[unit];
	}
	public void setTarget(double target[]){
		this.target = target;
	}
	public void error(){
		for(int i=0;i<unit;i++){
			error[i] = Math.pow(output[i]-target[i],2)/2;
			errorSum += error[i];
		}
	}
}

class Nutil{	
	double defAct(double x){
		return x;
	}
	double ReLU(double x){
		return Math.max(0, x);
	}
	double sigmoid(double x){
		return 1/(1+Math.exp(-x));
	}
}