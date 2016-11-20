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
	double targetData [][] = {{0,0},{0,1},{0,1},{1,0}}; // xor 과 and 연산을 같이
	
	int inputUnit = 2;
	int hiddenLayerUnit[] = {3,3};
	int outputUnit = 2;
	
	//double input[] = new double[inputUnit];
	//---위는 유닛 변수고 아레는 레이어 변수임
	Hidden hidden[] = new Hidden[hiddenLayerUnit.length];
	Output output;
	
	double alpha = 0.01; //학습량
	
	/*
	 * 구조
	 * input	hidden	 hidden  output
	 *   #		  #			#		#
	 *   #		  #			#		#
	 *   		  #			#
	 */
	
	public NN(){
		//세팅 [relu][sigmoid]
		
		//1
		hidden[0] = new Hidden(hiddenLayerUnit[0],inputUnit,"relu");
		hidden[0].randSetting(4, 4);
		
		//2
		hidden[1] = new Hidden(hiddenLayerUnit[1],hiddenLayerUnit[0],"relu");
		hidden[1].randSetting(4, 4);
		
		//레이어 한개--
		output = new Output(outputUnit,hiddenLayerUnit[1],"sigmoid");
		output.randSetting(4, 4);
		//---
		
		//시작
		System.out.printf(" input \t\t hidden1 \t hidden2 \t output \t target \t error");
		for(int i=0;i<inputData.length;i++){
			hidden[0].setInput(inputData[i]);//인풋 i
			hidden[0].feedForward();
			
			hidden[1].setInput(hidden[0].output);
			hidden[1].feedForward();
			
			output.setInput(hidden[1].output);
			output.feedForward();
			output.setTarget(targetData[i]);//타겟 i
			output.error();
			
			System.out.println();
			System.out.printf("%f\t%f\t%f\t%f\t%f\t%f\n",inputData[i][0],hidden[0].output[0],hidden[1].output[0],output.output[0],output.target[0],output.error[0]);
			System.out.printf("%f\t%f\t%f\t%f\t%f\t%f\n",inputData[i][1],hidden[0].output[1],hidden[1].output[1],output.output[1],output.target[1],output.error[1]);
		}
		System.out.println("Error : "+output.errorSum);
	}
	
}

//한개의 히든 레이어
class Hidden{
	
	Nutil nUtil = new Nutil(); //유틸리티 act 함수정의 
	
	//가중치 동적배열을 위해 받음
	int unit; //유닛개수
	int frontLayer; //이전 유닛 개수
	//로컬저장 편미분변수 추가해야됨
	
	double input[];
	double output[]; //나오는 시그마값을 저장
	
	double weight[][]; //가중치
	double bias[]; //원래는 바이어스에 가중치가 붙어야되지만 1이라 가정하면 됨
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