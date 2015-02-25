package com.esisa.java.reseau.business;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;


import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.esisa.java.reseau.models.Message;
import com.esisa.java.reseau.models.Telechargement;
import com.esisa.java.reseau.models.Telecharger;
import com.esisa.java.reseau.view.ClientFrame;

public class SocketClient implements Runnable {
	private int port;
	private String serverAdresse;
	private Socket socket;
	private ClientFrame clientFrame;
	private ObjectInputStream is;
	private ObjectOutputStream ous;

	public SocketClient(ClientFrame clientFrame) throws IOException {
		super();
		this.clientFrame = clientFrame;
		this.serverAdresse = clientFrame.getServerAdresse();
		this.port = clientFrame.getPort();
		socket = new Socket(InetAddress.getByName(serverAdresse), port);
		ous = new ObjectOutputStream(socket.getOutputStream());
		ous.flush();
		is = new ObjectInputStream(socket.getInputStream());
	}

	@SuppressWarnings({ "deprecation" })
	@Override
	public void run() {
		boolean keepRunning = true;
		while (keepRunning) {
			try {
				Message msg = (Message) is.readObject();
				if (msg.type.equals("message")) {
					if (msg.destinataire.equals(clientFrame.getUsername())) {
						clientFrame.getText().append(msg.emetteur + " : " + msg.contenu + "\n");
					} else {
						clientFrame.getText().append(msg.emetteur + " => " + msg.destinataire + " : " + msg.contenu+ "\n");
					}
				} 
				else if (msg.type.equals("connexion")) {
					if (msg.contenu.equals("OK")) {
						clientFrame.getConnexion().setEnabled(false);
						clientFrame.getInscription().setEnabled(false);
						clientFrame.getText().append("SERVEUR : Connexion Réussi\n");
						clientFrame.getUser().setEnabled(false);
						clientFrame.getJp().setEnabled(false);
						clientFrame.getParcourir().setEnabled(true);
					} else {
						clientFrame.getText().append("SERVEUR : Connexion Echoué\n");
					}
				}
					else if(msg.type.equals("test")){
						clientFrame.getText().append("Application : Connexion au Serveur établie\n");
	                }
				else if (msg.type.equals("NouveauUtilisateur")) {
					if (!msg.contenu.equals(clientFrame.getUsername())) {
						boolean exists = false;
						for (int i = 0; i < clientFrame.getModel().getSize(); i++) {
							if (clientFrame.getModel().getElementAt(i).equals(
									msg.contenu)) {
								exists = true;
								break;
							}
						}
						if (!exists) {
							clientFrame.getModel().addElement(msg.contenu);
						}
					}
				} else if (msg.type.equals("inscription")) {
					if (msg.contenu.equals("OK")) {
						clientFrame.getConnexion().setEnabled(false);
						clientFrame.getInscription().setEnabled(false);
						clientFrame.getText().append("SERVEUR : Inscription Réussi\n");
					} else {
						clientFrame.getText().append("SERVEUR : Inscription Echoué\n");
					}
				} else if (msg.type.equals("deconnexion")) {
					if (msg.contenu.equals(clientFrame.getUsername())) {
						clientFrame.getText().append(msg.emetteur + " : Bye\n");
						for (int i = 1; i < clientFrame.getModel().size(); i++) {
							clientFrame.getModel().removeElementAt(i);
						}
						clientFrame.getClientThread().stop();
					} else {
						clientFrame.getModel().removeElement(msg.contenu);
						clientFrame.getText().append(msg.emetteur + " : " + msg.contenu + " c'est déconnecté\n");
					}
				} else if (msg.type.equals("telecharger rej")) {
					Icon icon = new ImageIcon(getClass().getResource("/telecharger.png"));
					if (JOptionPane.showConfirmDialog(clientFrame,
							("Accepter '" + msg.contenu + "' de "
									+ msg.emetteur + " ?"),"Téléchargement",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,icon) == 0) {

						JFileChooser jf = new JFileChooser(".");
						jf.setSelectedFile(new File(msg.contenu));
						int valeur = jf.showSaveDialog(clientFrame);

						String sauvegarder = jf.getSelectedFile().getPath();
						if (sauvegarder != null
								&& valeur == JFileChooser.APPROVE_OPTION) {
							Telecharger telecharger = new Telecharger(
									sauvegarder, clientFrame);
							Thread t = new Thread(telecharger);
							t.start();
							send(new Message("telecharger acp",
									clientFrame.getUsername(),
									("" + telecharger.getPort()), msg.emetteur));
						} else {
							send(new Message("telecharger acp",
									clientFrame.getUsername(), "NO", msg.emetteur));
						}
					} else {
						send(new Message("telecharger acp",
								clientFrame.getUsername(), "NO", msg.emetteur));
					}
				} else if (msg.type.equals("telecharger acp")) {
					if (!msg.contenu.equals("NO")) {
						int port = Integer.parseInt(msg.contenu);
						String adresse = msg.emetteur;
						clientFrame.getEnvoyer().setEnabled(false);
						Telechargement telechargement = new Telechargement(
								adresse, port, clientFrame.getFile(), clientFrame);
						Thread t = new Thread(telechargement);
						t.start();
					} else {
						clientFrame.getText().append("SERVEUR : " + msg.emetteur + " demande de fichier rejeté\n");
					}
				} else {
					clientFrame.getText().append("SERVEUR : message de type inconnu \n");
				}
			} catch (Exception ex) {
				keepRunning = false;
				clientFrame.getText().append("Application : Connexion Echoué\n");
				for (int i = 1; i < clientFrame.getModel().size(); i++) {
					clientFrame.getModel().removeElementAt(i);
				}
				clientFrame.getClientThread().stop();
				System.out.println("Erreur : " + ex.getMessage());
			}
		}
	}
	
	public void send(Object msg){
        try {
            ous.writeObject(msg);
            ous.flush();
        } 
        catch (IOException ex) {
            System.out.println("Erreur : " + ex.getMessage());
        }
    }

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	

}
