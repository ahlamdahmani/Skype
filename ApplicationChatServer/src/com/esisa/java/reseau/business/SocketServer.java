package com.esisa.java.reseau.business;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import com.esisa.java.reseau.dao.ClassTestFile;
import com.esisa.java.reseau.dao.Database;
import com.esisa.java.reseau.models.Message;
import com.esisa.java.reseau.models.ServerThread;
import com.esisa.java.reseau.view.ServerFrame;

public class SocketServer implements Runnable {
	private ServerThread clients[];
	private ServerSocket server = null;
	private Thread thread = null;
	private int clientCount = 0, port = 12345;
	private ServerFrame view;
	private Database db;

	public SocketServer(ServerFrame view) {
		super();
		this.view = view;
		clients = new ServerThread[50];
		db = new Database(view.getPath());
		try {
			server = new ServerSocket(port);
			port = server.getLocalPort();
			start();
		} catch (IOException e) {
			view.RetryStart(port);
			e.printStackTrace();
		}
	}

	public SocketServer(ServerFrame view, int port) {
		clients = new ServerThread[50];
		this.view = view;
		this.port = port;
		db = new Database(view.getPath());
		try {
			server = new ServerSocket(port);
			port = server.getLocalPort();
			start();
		} catch (IOException e) {
			view.RetryStart(port);
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (thread != null) {
			try {
				addThread(server.accept());
			} catch (Exception ioe) {
				view.RetryStart(port);
				ioe.printStackTrace();
			}
		}
	}

	public void start() {
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}

	@SuppressWarnings("deprecation")
	public void stop() {
		if (thread != null) {
			thread.stop();
			thread = null;
		}
	}

	private int findClient(int ID) {
		for (int i = 0; i < clientCount; i++) {
			if (clients[i].getID() == ID) {
				return i;
			}
		}
		return -1;
	}

	public void Envoyer(String type, String emetteur, String contenu) {
		Message msg = new Message(type, emetteur, contenu, "Tout");
		for (int i = 0; i < clientCount; i++) {
			clients[i].send(msg);
		}
	}

	public void SendUserList(String user) {
		for (int i = 0; i < clientCount; i++) {
			findUserThread(user).send(
					new Message("NouveauUtilisateur", "SERVEUR",
							clients[i].getUsername(), user));
		}
	}

	public ServerThread findUserThread(String user) {
		for (int i = 0; i < clientCount; i++) {
			if (clients[i].getUsername().equals(user)) {
				return clients[i];
			}
		}
		return null;
	}

	private void addThread(Socket socket) {
		if (clientCount < clients.length) {
			clients[clientCount] = new ServerThread(this, socket);
			try {
				clients[clientCount].open();
				clients[clientCount].start();
				clientCount++;
			} catch (IOException ioe) {
			}
		} else {
		}
	}

	@SuppressWarnings("deprecation")
	public synchronized void supprimer(int ID) {
		int pos = findClient(ID);
		if (pos >= 0) {
			ServerThread serverThread = clients[pos];
			if (pos < clientCount - 1) {
				for (int i = pos + 1; i < clientCount; i++) {
					clients[i - 1] = clients[i];
				}
			}
			clientCount--;
			try {
				serverThread.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
			serverThread.stop();
		}
	}

	public synchronized void traiter(int ID, Message msg) {
		String path= "resources/logConv.txt";
		Date date = new  Date();
		String chaineNouv = "\n" + date.toString() + "\n" ;

		if (msg.contenu.contains(".bye")) {
			Envoyer("deconnexion", "SERVEUR", msg.emetteur);
			supprimer(ID);
		} else {
			if (msg.type.equals("connexion")) {
				if (findUserThread(msg.emetteur) == null) {
					if (db.Login(msg.emetteur, msg.contenu)) {
						clients[findClient(ID)].setUsername(msg.emetteur);
						clients[findClient(ID)].send(new Message("connexion",
								"SERVEUR", "OK", msg.emetteur));
						Envoyer("NouveauUtilisateur", "SERVEUR", msg.emetteur);
						SendUserList(msg.emetteur);
					} else {
						clients[findClient(ID)].send(new Message("connexion",
								"SERVEUR", "NON", msg.emetteur));
					}
				} else {
					clients[findClient(ID)].send(new Message("connexion",
							"SERVEUR", "NON", msg.emetteur));
				}
			} 
			else if (msg.type.equals("message")) {
				if (msg.destinataire.equals("Tout")) {
					Envoyer("message", msg.emetteur, msg.contenu);
					ClassTestFile.writeFile(chaineNouv + msg.emetteur + "\n" + msg.contenu, path);
				} else {
					findUserThread(msg.destinataire).send(
							new Message(msg.type, msg.emetteur, msg.contenu,
									msg.destinataire));
					clients[findClient(ID)].send(new Message(msg.type, msg.emetteur, msg.contenu,
							msg.destinataire));
					ClassTestFile.writeFile(chaineNouv + msg.type + msg.emetteur + msg.destinataire + "\n" + msg.contenu, path);

				}
			}
				 else if(msg.type.equals("test")){
		                clients[findClient(ID)].send(new Message("test", "SERVEUR", "OK", msg.emetteur));
			} else if (msg.type.equals("inscription")) {
				if (findUserThread(msg.emetteur) == null) {
					if (!db.Exists(msg.emetteur)) {
						db.NouveauUtilisateur(msg.emetteur, msg.contenu);
						clients[findClient(ID)].setUsername(msg.emetteur);
						clients[findClient(ID)].send(new Message("inscription",
								"SERVEUR", "OK", msg.emetteur));
						clients[findClient(ID)].send(new Message("connexion",
								"SERVEUR", "OK", msg.emetteur));
						Envoyer("NouveauUtilisateur", "SERVEUR", msg.emetteur);
						SendUserList(msg.emetteur);
					} else {
						clients[findClient(ID)].send(new Message("connexion",
								"SERVEUR", "NON", msg.emetteur));
					}
				} else {
					clients[findClient(ID)].send(new Message("connexion",
							"SERVEUR", "NON", msg.emetteur));
				}
			} else if (msg.type.equals("telecharger rej")) {
				if (msg.destinataire.equals("Tout")) {
					clients[findClient(ID)].send(new Message("message",
							"SERVEUR", "Telechargement est annulé",
							msg.emetteur));
				} else {
					findUserThread(msg.destinataire).send(
							new Message("telecharger rej", msg.emetteur,
									msg.contenu, msg.destinataire));
				}
			} else if (msg.type.equals("telecharger acp")) {
				if (!msg.contenu.equals("NO")) {
					String IP = findUserThread(msg.emetteur).getSocket()
							.getInetAddress().getHostAddress();
					findUserThread(msg.destinataire).send(
							new Message("telecharger acp", IP, msg.contenu,
									msg.destinataire));
				} else {
					findUserThread(msg.destinataire).send(
							new Message("telecharger acp", msg.emetteur,
									msg.contenu, msg.destinataire));
				}
			}
		}
	}

	public ServerThread[] getClients() {
		return clients;
	}

	public void setClients(ServerThread[] clients) {
		this.clients = clients;
	}

	public ServerSocket getServer() {
		return server;
	}

	public void setServer(ServerSocket server) {
		this.server = server;
	}

	public Thread getThread() {
		return thread;
	}

	public void setThread(Thread thread) {
		this.thread = thread;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public ServerFrame getView() {
		return view;
	}

	public void setView(ServerFrame view) {
		this.view = view;
	}

	public Database getDb() {
		return db;
	}

	public void setDb(Database db) {
		this.db = db;
	}

}
