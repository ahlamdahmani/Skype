package com.esisa.java.reseau.view;

import java.util.ArrayList;
import java.util.Arrays;

public class ClassTest {

	
public String getTextFilter(String text){
		
		String textFi ="";
		ArrayList<String> data = new ArrayList<String>(Arrays.asList("�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "#", "+", "*", " ","�","&", "�", "~","{", "(", "[", "|", "�","`", "�", "^","$", "�", "�", "�", "%", "*", "�", ",", "?", ";", ":", "/", "!", "�", ">", "<" ));
			
		
		for (int i = 0 ; i< data.size();i++){
			if(text.contains(data.get(i))){
				
				textFi = "";
				}
			else{
				textFi = text;
			}
			}
		return textFi;
	}

	public boolean getTextFilter2(String text){
		boolean carSpecial = false;
		
		ArrayList<String> data = new ArrayList<String>(Arrays.asList("�", "�", "�", "�", "�",
				"�", "�", "�", "�", "�", "�", "�", "#", "+", "*", " ","�","&",
				"�", "~","{", "(", "[", "|", "�","`", "�", "^","$", "�", "�", 
				"�", "%", "*", "�", ",", "?", ";", ":", "/", "!", "�", ">", "<" ));
		for(int i=0;i<data.size();i++){
			if(text.contains(data.get(i))){
				carSpecial =true;
			}
		}
		return carSpecial;	
	}


	public static void main(String[] args) {
		
		//////////////////////////////////////////////
//		if (maString.indexOf(monfiltre) > -1) {
//		     // La cha�ne contient le filtre
//		} else {
//		     // La cha�ne ne contient pas le filtre
//		}
		//////////////////////////////////////////////
		boolean carSpecial= false;
		String strTest = new String("bonj�u");
		ArrayList<String> data = new ArrayList<String>(Arrays.asList("�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "#", "+", "*", " ","�","&", "�", "~","{", "(", "[", "|", "�","`", "�", "^","$", "�", "�", "�", "%", "*", "�", ",", "?", ";", ":", "/", "!", "�", ">", "<" ));
		for(int i=0;i<data.size();i++){
			if(strTest.contains(data.get(i))){
				carSpecial =true;
			}
//			System.out.print(data.get(i) + " ");
		}
		
			System.out.println("le mot : " + strTest + " contient des carac speciaux : " + carSpecial);
	}

}
