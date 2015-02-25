package com.esisa.java.reseau.models;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Message implements Serializable {
	public String type, contenu, destinataire, emetteur;

	public Message(String type, String emetteur, String contenu,
			String destinataire) {
		super();
		this.type = type;
		this.contenu = contenu;
		this.destinataire = destinataire;
		this.emetteur = emetteur;
	}

	@Override
	public String toString() {
		return "{Type : '" + type + "', Emetteur : '" + emetteur
				+ "', Contenu : '" + contenu + "', Destinataire : '"
				+ destinataire + "'}";
	}

}
