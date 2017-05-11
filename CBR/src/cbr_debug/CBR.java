package cbr_debug;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 
 *  Case Based Reasoning : ��� ��� �߷� ���
 *  ��) http://courses.ischool.berkeley.edu/i256/f06/projects/bonniejc.pdf
 * 	debug �Ϸ� [*]
 * 
 */
public class CBR {
	
	protected Table table;
	
	public CBR() {
		init();
	}
	
	protected void init() {
		table = new Table();
		
		//test data set
		table.add("i want a kitten", "Can we put mitten on it");		//Can we put mitten on it
		table.add("i want food", "Me too, I'm hungry");			//Me too, I'm hungry
		table.add("they had good food at the restaurant", "what kind did they have?");	//what kind did they have?
		
		System.out.println(table.calc("i want kitten"));
		
		System.out.println(table.calc("i want food"));
		
		System.out.println(table.calc("they had good food at restaurant"));
		
		
		table.debugWeight();
		
		System.out.println("i want food");
		System.out.println(table.calc("i want food"));
		
		//test
		//System.out.println(table.weightMap.get("i")[1]);
		//���� �������� weightMap value�� �����Ҽ� �ְ� �ٲ�ߵ�. <�Ϸ�>
	}
	
	public String feedString(String input){
		return table.calc(input);
	}
	
	public void addData(String in,String out){
		table.add(in, out);
	}
}

class Table {
	/*
	 * String				������ �Է¿�
	 * ArrayList<Integer> 	����Ǿ��ִ� ������ ��ġ
	 */
	protected Map<String, ArrayList<Integer>> wordMap; 
	protected ArrayList<Sentence> sentence;
	
	protected Table() {
		wordMap = new HashMap<String, ArrayList<Integer>>();
		sentence = new ArrayList<Sentence>();
	}
	
	/**
	 * sentence
	 */
	protected void add(String in, String out){
		String[] words = in.split(" ");
//		for(String word : words){
//			//a ������ ���߉� �Լ� ���θ���°������Ͱ���
//		}
//		System.out.println();
		
		//�ܾ� �Է�
		for(String word : words){
			if(wordMap.containsKey(word)){
				wordMap.get(word).add(sentence.size());
			}else{
				ArrayList<Integer> tmpList = new ArrayList<Integer>();
				tmpList.add(sentence.size()); //�ּ� �Է�
				wordMap.put(word, tmpList);
			}
		}
		
		sentence.add(new Sentence(out, words.length));
	}
	
	protected void debugWeight(){
		
		System.out.println(":::::::: sentence weight ::::::::");
		Iterator<Sentence> it1 = sentence.iterator();
		while(it1.hasNext()){
			System.out.println(it1.next().weight());
		}
		System.out.println();
		
		System.out.println(":::::::: word link ::::::::");
		for(String key : wordMap.keySet()){
			System.out.print(key+" ");
			for(int val : wordMap.get(key)){
				System.out.print(val+" ");
			}
			System.out.println();
		}
		System.out.println();
		
		
		
	}
	
	protected String calc(String input){
		/*
		 * *1���ܾ� (1/���忬���) / (1/�ܾ ����Ű�� ������ ���忬���) + ... +
		 * *2���ܾ� ... +
		 */
		
		String[] words = input.split(" ");
		
		/*
		 * Integer ���� index
		 * Float ���� ����ġ ��
		 */
		Map<Integer, Float> tmpMap = new HashMap<Integer, Float>();
		
		for(String word : words){
			if(wordMap.containsKey(word)){ // �ܾ���� �Ǿ�������� 
				for(int i=0;i<wordMap.get(word).size();i++){
					int sentenceNum = wordMap.get(word).get(i);
					//if(!tmpMap.containsKey(sentenceNum)){ // �ش� ��ȣ�� ������ �ȳ��������
						
						float last = tmpMap.getOrDefault(sentenceNum, (float) 0.0);
						tmpMap.put(sentenceNum, last + 
								(sentence.get(sentenceNum).weight() / wordWeightSum(word)));
						System.out.println(tmpMap.toString());
					//}
				}//end for
			}
		}//end for
		
		float maxValue = Collections.max(tmpMap.values());
		
		for(int key : tmpMap.keySet()){
			
		}
		
		for(int key : tmpMap.keySet()){
			if(tmpMap.get(key) == maxValue){
				return sentence.get(key).getSentence();
			}
		}
		
		return "";
	}
	
	// Ư�� �ܾ�� ����Ǿ��ִ� ������ ����ġ�� �� ���ϱ�
	protected float wordWeightSum(String word){
		
		Iterator<Integer> it = wordMap.get(word).iterator();
		
		float sum = 0;
		
		while(it.hasNext()){
			sum += sentence.get(it.next()).weight();
		}
		
		return sum;
	}
}

/**
 * output�� ����Ǿ��ִ� �ܾ��
 */
class Sentence {
	/**
	 * weight 	����� �ܾ��
	 * sentence ��� ����
	 */
	protected int wordNum;	
	protected String sentence; 
	
	protected Sentence(String sentence, int wordNum) {
		this.wordNum = wordNum;
		this.sentence = sentence;
	}
	
	protected String getSentence(){
		return sentence;
	}
	
	protected float weight(){
		return (float)1.0/wordNum;
	}
	
	protected void plusWeight(){
		wordNum++;
	}
}