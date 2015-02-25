package com.esisa.java.reseau.dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

import com.esisa.java.reseau.models.Message;

	public class ClassTestFile {
		
		private String msgLog;
		private String dateLog;
		private Message messageLog;
		
		
		public String getMsgLog() {
			return msgLog;
		}

		public void setMsgLog(String msgLog) {
			this.msgLog = msgLog;
		}

		public String getDateLog() {
			return dateLog;
		}

		public void setDateLog(String dateLog) {
			this.dateLog = dateLog;
		}

		public Message getMessageLog() {
			return messageLog;
		}

		public void setMessageLog(Message messageLog) {
			this.messageLog = messageLog;
		}

		public static void writeFile(String fileContent, String filePathOutput) {
			try {

				/**
				 * donne comme argument le nom du fichier et false signifie qu'on
				// n'écrase pas le contenu du fichier et faire un append sur le contenu
				 */
			
				FileWriter fileWriter = new FileWriter(filePathOutput, true);

				
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

				// On écrit dans le fichier 
				bufferedWriter.write(fileContent);

				bufferedWriter.flush();
				bufferedWriter.close();
				System.out.println("Fichier créé");
			} catch (IOException ioe) {
				System.err
						.println("Erreur levée de type IOException au niveau de la méthode "
								+ "writeFile(...) : ");
				ioe.printStackTrace();
			}
//			System.out.println("");
		}// End writeFile(...)

		public static void readFile(String filePathInput, String filePathOutput) {

			Scanner scanner = null;
			String line = null;
			StringBuffer str = new StringBuffer();
			try {
				scanner = new Scanner(new File(filePathInput));

				// On boucle sur chaque champ detecté
				while (scanner.hasNextLine()) {
					line = scanner.nextLine();

					if (line != null)
						str.append(line + "\r\n");
				}

				scanner.close();
			} catch (FileNotFoundException e) {
				System.err.println("Erreur levée de type FileNotFoundException"
						+ " au niveau de la méthode " + "readFile(...) : ");
				e.printStackTrace();
			}

			ClassTestFile.writeFile(str.toString(), filePathOutput);
		}// End readFile(...)

		public static void formatSQLFileContentToIntegrateThisInTheJAVACode(
				String filePathInput, String filePathOutput) {

			Scanner scanner = null;
			String line = null;
			StringBuffer str = new StringBuffer();
			try {
				scanner = new Scanner(new File(filePathInput));

				// On boucle sur chaque champ detecté
				while (scanner.hasNextLine()) {
					line = scanner.nextLine();

					if (line != null)
						str.append(".append(\"" + line + " \")\r\n");
				}

				scanner.close();
			} catch (FileNotFoundException e) {
				System.err
						.println("Erreur levée de type FileNotFoundException"
								+ " au niveau de la méthode "
								+ "formatSQLFileContentToIntegrateThisInTheJAVACode(...) : ");
				e.printStackTrace();
			}

			ClassTestFile.writeFile(str.toString(), filePathOutput);
		}// End formatSQLFileContentToIntegrateThisInTheJAVACode(...)
		
		public static void main(String[] args) {
			String path= "resources/testLog.txt";
			Date date = new  Date();
			String chaineNouv = "\n" + date.toString() + "\n" ;
			System.out.println("la date : " + date.toString() );
			writeFile(chaineNouv +"test test44" + " msg" , path);
		}	
		
	}// End public class ClassTestFile

