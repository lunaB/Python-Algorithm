package cbr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 *  Case Based Reasoning : ��� ��� �߷� ���
 *  
 * 	���� : ����ä
 * 
 * 	���� : CBR �˰����� ����� ��ȭ�� ê�� ���̺귯��
 * 
 * 	17-03-26 ���� / �ߴ�
 *  17-05-05 ����
 * 
 *  ���� ��ġ : http://courses.ischool.berkeley.edu/i256/f06/projects/bonniejc.pdf
 *  			http://blog.naver.com/phillyai/220733534288
 */
public class CBR {
	
	Table table;
	
	public CBR() {
		init();
	}
	
	public void init() {
		table = new Table();
		
		//test data set
		table.add("i want a kitten", "Can we put mitten on it");		//Can we put mitten on it
		table.add("i want food", "Me too, I'm hungry");			//Me too, I'm hungry
		table.add("they had good food at theee restaurant", "what kind did they have?");	//what kind did they have?
		
		//test
		//System.out.println(table.weightMap.get("i")[1]);
		//���� �������� weightMap value�� �����Ҽ� �ְ� �ٲ�ߵ�. <�Ϸ�>
	}
	
}

class Table {
	/*
	 * String				������ �Է¿�
	 * ArrayList<Integer> 	����Ǿ��ִ� ������ ��ġ
	 */
	Map<String, ArrayList<Integer>> wordMap; 
	ArrayList<Sentence> sentence;
	
	public Table() {
		wordMap = new HashMap<String, ArrayList<Integer>>();
		sentence = new ArrayList<Sentence>();
	}
	
	/**
	 * sentence
	 */
	public void add(String in, String out){
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
	
	public void calc(String input){
		/*
		 * *1���ܾ� (1/���忬���) / (1/�ܾ ����Ű�� ������ ���忬���) + ... +
		 * *2���ܾ� ... +
		 */
		
		String[] words = input.split(" ");
		Map<Integer,Integer> tmpMap = new HashMap<Integer,Integer>();
		for(String word : words){
			if(wordMap.containsKey(word)){ // �ܾ���� �Ǿ��������
				for(int i=0;i<wordMap.get(word).size();i++){
					int sentenceNum = wordMap.get(word).get(i);
					if(tmpMap.containsKey(sentenceNum)){ // �ش� ��ȣ�� ������ ���������
						
					}else {
						//tmpMap.put(sentenceNum, )
					}
					
				}
			}
		}
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
	int wordNum;	
	String sentence; 
	
	public Sentence(String sentence, int wordNum) {
		this.wordNum = wordNum;
		this.sentence = sentence;
	}
	
	public float weight(){
		return (float)1.0/wordNum;
	}
	
	public void plusWeight(){
		wordNum++;
	}
}