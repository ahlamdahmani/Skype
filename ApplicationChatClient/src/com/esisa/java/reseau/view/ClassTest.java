package com.esisa.java.reseau.view;

import java.util.ArrayList;
import java.util.Arrays;

public class ClassTest {

	
public String getTextFilter(String text){
		
		String textFi ="";
		ArrayList<String> data = new ArrayList<String>(Arrays.asList("ä", "ê", "ë", "ô", "î", "ï", "ô", "ö", "û", "ü", "ÿ", "€", "#", "+", "*", " ","²","&", "é", "~","{", "(", "[", "|", "è","`", "ç", "^","$", "£", "¤", "ù", "%", "*", "µ", ",", "?", ";", ":", "/", "!", "§", ">", "<" ));
			
		
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
		
		ArrayList<String> data = new ArrayList<String>(Arrays.asList("ä", "ê", "ë", "ô", "î",
				"ï", "ô", "ö", "û", "ü", "ÿ", "€", "#", "+", "*", " ","²","&",
				"é", "~","{", "(", "[", "|", "è","`", "ç", "^","$", "£", "¤", 
				"ù", "%", "*", "µ", ",", "?", ";", ":", "/", "!", "§", ">", "<" ));
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
//		     // La chaîne contient le filtre
//		} else {
//		     // La chaîne ne contient pas le filtre
//		}
		//////////////////////////////////////////////
		boolean carSpecial= false;
		String strTest = new String("bonjäu");
		ArrayList<String> data = new ArrayList<String>(Arrays.asList("ä", "ê", "ë", "ô", "î", "ï", "ô", "ö", "û", "ü", "ÿ", "€", "#", "+", "*", " ","²","&", "é", "~","{", "(", "[", "|", "è","`", "ç", "^","$", "£", "¤", "ù", "%", "*", "µ", ",", "?", ";", ":", "/", "!", "§", ">", "<" ));
		for(int i=0;i<data.size();i++){
			if(strTest.contains(data.get(i))){
				carSpecial =true;
			}
//			System.out.print(data.get(i) + " ");
		}
		
			System.out.println("le mot : " + strTest + " contient des carac speciaux : " + carSpecial);
	}

}
