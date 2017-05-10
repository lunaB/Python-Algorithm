package kakao_parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class KakaoParser {
	
	File file;
	ArrayList<String> list;
	BufferedReader in;
	String[] name;
	String date;
	
	
	public KakaoParser(String resourcesName) {
		try {
			file = new File("./resources/"+resourcesName+".txt");
			try {
				in = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		init();
	}

	public void init() {
		String stmp = null; // ������ ���ڿ� ó�������� 2���� (��������)
		String atmp = null; // (��������)
		try {
			name = in.readLine().split(", ");
			
			date = in.readLine();
			in.readLine(); //�Ǿ� 3�� ����
			
			while((stmp = in.readLine()) != null){
				/*
				 * replace list
				 * ���� : --------------- 2017�� 5�� 8�� ������ ---------------
				 */
				if(stmp.matches("")){
					continue;
				}else if(stmp.matches("--------------- [0-9]{4}�� [0-9]{1,2}�� [0-9]{1,2}�� [��-�R]{3} ---------------")){
					continue;
				}
				
				String[] split = stmp.split("[\\[\\]]");
				if(split.length < 4){
					atmp += stmp;
					continue;
				}else {
					atmp = split[4];
					list.add(atmp);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> getList() {
		
		return null;
	}
}
