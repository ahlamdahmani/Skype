package com.esisa.java.reseau.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.esisa.java.reseau.business.SocketServer;

@SuppressWarnings("serial")
public class ServerFrame extends JFrame implements ActionListener {
	private SocketServer server;
	private Thread serverThread;
	private String path = "resources/users.xml";
	private JButton b1,b3;
	
	public ServerFrame() {
		init();
	}
	
	public void init(){
		setTitle("Serveur");
		Icon icon = new ImageIcon(getClass().getResource("/start.png"));
		b1 = new JButton(icon);
		b1.setName("Start Server");
		b1.setToolTipText("Start Server");
		b1.addActionListener(this);
		Icon icon2 = new ImageIcon(getClass().getResource("/quitter.png"));
		b3 = new JButton(icon2);
		b3.setName("Quitter");
		b3.setToolTipText("Quitter");
		b3.addActionListener(this);
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		JPanel p1 = new JPanel();
		p1.setLayout(new BoxLayout(p1, BoxLayout.X_AXIS));
		p1.add(b1);
		p1.add(b3);
		p.add("North",p1);
		setContentPane(p);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setResizable(false);
		setVisible(true);
	}
	
	public void RetryStart(int port){
        if(server != null){ server.stop(); }
        server = new SocketServer(this, port);
    }
	
	
	public SocketServer getServer() {
		return server;
	}

	public void setServer(SocketServer server) {
		this.server = server;
	}

	public Thread getServerThread() {
		return serverThread;
	}

	public void setServerThread(Thread serverThread) {
		this.serverThread = serverThread;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton src = (JButton)e.getSource();
		switch(src.getName()){
			case "Start Server" :
				server = new SocketServer(this);
		        b1.setEnabled(false); 
		        this.setState(ICONIFIED);
				break;
			case "Quitter" :
					System.exit(0);
				break;
		}
	}
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			System.out.println("Erreur : " + e.getMessage());
		}
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new ServerFrame();
			}
		});
	}

}
