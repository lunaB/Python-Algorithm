package cbr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 *  Case Based Reasoning : 사례 기반 추론 기법
 *  
 * 	개발 : 나영채
 * 
 * 	개요 : CBR 알고리즘을 사용한 대화형 챗봇 라이브러리
 * 
 * 	17-03-26 시작 / 중단
 *  17-05-05 시작
 * 
 *  참고 위치 : http://courses.ischool.berkeley.edu/i256/f06/projects/bonniejc.pdf
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
		//개발 수정사항 weightMap value를 수정할수 있게 바꿔야됨. <완료>
	}
	
}

class Table {
	/*
	 * String				유저의 입력예
	 * ArrayList<Integer> 	연결되어있는 문장의 위치
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
//			//a 같은거 빼야됌 함수 새로만드는게좋은것같음
//		}
//		System.out.println();
		
		//단어 입력
		for(String word : words){
			if(wordMap.containsKey(word)){
				wordMap.get(word).add(sentence.size());
			}else{
				ArrayList<Integer> tmpList = new ArrayList<Integer>();
				tmpList.add(sentence.size()); //주소 입력
				wordMap.put(word, tmpList);
			}
		}
		
		sentence.add(new Sentence(out, words.length));
	}
	
	public void calc(String input){
		/*
		 * *1번단어 (1/문장연결수) / (1/단어가 가르키는 문장의 문장연결수) + ... +
		 * *2번단어 ... +
		 */
		
		String[] words = input.split(" ");
		Map<Integer,Integer> tmpMap = new HashMap<Integer,Integer>();
		for(String word : words){
			if(wordMap.containsKey(word)){ // 단어선언이 되어있을경우
				for(int i=0;i<wordMap.get(word).size();i++){
					int sentenceNum = wordMap.get(word).get(i);
					if(tmpMap.containsKey(sentenceNum)){ // 해당 번호의 문장이 나왔을경우
						
					}else {
						//tmpMap.put(sentenceNum, )
					}
					
				}
			}
		}
	}
}

/**
 * output과 연결되어있는 단어수
 */
class Sentence {
	/**
	 * weight 	연결된 단어수
	 * sentence 결과 문장
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