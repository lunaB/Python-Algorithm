import java.util.Random;

public class FeedForward{
	public static void main(String [] args){
		NN nn = new NN();
	}
}

class NN{
	Random rand = new Random();
	
	double inputData [][] = {{0,0},{0,1},{1,0},{1,1}};
	double targetData [] = {0,1,1,0};
	
	int inputLayer = 2;
	int hiddenLayer[] = {2,1};
	int outputLayer = 1;
	
	double input[] = new double[inputLayer];
	Hidden hidden[] = new Hidden[hiddenLayer.length];
	double output[] = new double[outputLayer];
	
	public NN(){
		//���� [relu][sigmoid]
		hidden[0] = new Hidden(hiddenLayer[0], inputLayer,"relu");
		hidden[0].randSetting(1,8);
		hidden[1] = new Hidden(hiddenLayer[1], hiddenLayer[1],"sigmoid");
		hidden[1].randSetting(1,8);
		
		//����
		hidden[0].setInput(inputData[0]);
		hidden[0].feedForward();
		
		hidden[1].setInput(hidden[0].output);
		hidden[1].feedForward();
		
		System.out.println(hidden[1].output[0]);
	}
	
}

//�Ѱ��� ���� ���̾�
class Hidden{
	
	Nutil nUtil = new Nutil(); //��ƿ��Ƽ act �Լ����� 
	
	//����ġ �����迭�� ���� ����
	int unit=0; //���ְ���
	int frontLayer=0; //���� ���� ����
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
			output[i] = actFunction(sigma(i));
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
	
	public double sigma(int unitNum){
		double sigma=0;
		for(int i=0;i<frontLayer;i++){
			//x1 * w11 + x2 * w21
			sigma += weight[i][unitNum]*input[i];
		}
		sigma+=bias[unitNum];
		return sigma;
	}
	
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