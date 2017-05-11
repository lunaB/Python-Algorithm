
import java.util.Scanner;

import cbr.CBR;

public class Main {
	
	public static void main(String[] args) {
		CBR cbr = new CBR();
		//cbr_debug.CBR cbr = new cbr_debug.CBR();
		Scanner scan = new Scanner(System.in);
		//cbr.addData("", "");
		
		String sTmp="";
		System.out.println("CBR 체팅 프레임워크");
		System.out.print("[in 1] : ");
		int num = 2;
		while((sTmp=scan.nextLine()) != ""){
			System.out.println("[out "+num+"] : "+ cbr.feedString(sTmp));
			System.out.print("[in "+num+"] : ");
			num++;
		}
	}
	
}
