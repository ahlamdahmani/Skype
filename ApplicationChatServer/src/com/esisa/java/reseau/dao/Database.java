package com.esisa.java.reseau.dao;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Database {
	private String chemin;

	public Database(String chemin) {
		super();
		this.chemin = chemin;
	}

	public String getChemin() {
		return chemin;
	}

	public void setChemin(String chemin) {
		this.chemin = chemin;
	}

	public boolean Exists(String username){
		try {
			File f = new File(chemin);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(f);
			document.getDocumentElement().normalize();
			NodeList list = document.getElementsByTagName("user");
			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);
				if(node.getNodeType() == Node.ELEMENT_NODE){
					Element element = (Element)node;
					if(getTagValue("username", element).equals(username)){
						return true;
					}
				}
			}
			return false;
		} catch (Exception e) {
			System.out.println("Erreur : " + e.getMessage());
			return false;
		}
	}
	
	public boolean Login(String username,String password){
		if(!Exists(username)) return false;
		try {
			File f = new File(chemin);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(f);
			document.getDocumentElement().normalize();
			NodeList list = document.getElementsByTagName("user");
			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);
				if(node.getNodeType() == Node.ELEMENT_NODE){
					Element element = (Element)node;
					if(getTagValue("username", element).equals(username) && getTagValue("password", element).equals(password)){
						return true;
					}
				}
			}
			return false;
		} catch (Exception e) {
			System.out.println("Erreur : " + e.getMessage());
			return false;
		}
	}
	
	public void NouveauUtilisateur(String username,String password){
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			File f = new File(chemin);
			Document document = builder.parse(f);
			document.getDocumentElement().normalize();
			Node node = document.getFirstChild();
			Element newuser = document.createElement("user");
			Element newusername = document.createElement("username");
			newusername.setTextContent(username);
			Element newpassword = document.createElement("password");
			newpassword.setTextContent(password);
			newuser.appendChild(newusername);
			newuser.appendChild(newpassword);
			node.appendChild(newuser);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(new File(chemin));
			transformer.transform(source, result);
		} catch (Exception e) {
			System.out.println("Erreur : " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static String getTagValue(String tag,Element element){
		NodeList list = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = (Node) list.item(0);
		return node.getNodeValue();
	}
	
}
