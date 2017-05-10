package cbr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import kakao_parser.KakaoParser;

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
 *  17-05-10 �Ϸ�
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
		table.add("they had good food at the restaurant", "what kind did they have?");	//what kind did they have?
		
		//run
		System.out.println(table.calc("i want kitten"));
		System.out.println(table.calc("i want food"));
		System.out.println(table.calc("they had good food at restaurant"));
		
		
		KakaoParser kakaoParser = new KakaoParser("KakaoTalk_20170510_1257_39_882_group");
		
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
	
	public String calc(String input){
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
					float last = tmpMap.getOrDefault(sentenceNum, (float) 0.0);
					tmpMap.put(sentenceNum, last + 
							(sentence.get(sentenceNum).weight() / wordWeightSum(word)));
				
				}//end for
			}
		}//end for
		
		float maxValue = Collections.max(tmpMap.values());
		
		for(int key : tmpMap.keySet()){
			if(tmpMap.get(key) == maxValue){
				return sentence.get(key).getSentence();
			}
		}
		
		return "";
	}
	
	// Ư�� �ܾ�� ����Ǿ��ִ� ������ ����ġ�� �� ���ϱ�
	public float wordWeightSum(String word){
		
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
	private int wordNum;	
	private String sentence; 
	
	public Sentence(String sentence, int wordNum) {
		this.wordNum = wordNum;
		this.sentence = sentence;
	}
	
	public String getSentence(){
		return sentence;
	}
	
	public float weight(){
		return (float)1.0/wordNum;
	}
	
	public void plusWeight(){
		wordNum++;
	}
}