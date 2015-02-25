package com.esisa.java.reseau.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.LayoutStyle;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.esisa.java.reseau.business.SocketClient;
import com.esisa.java.reseau.models.Message;

@SuppressWarnings("serial")
public class ClientFrame extends JFrame implements ActionListener,
		WindowListener {
	private SocketClient client;
	private int port;
	private String serverAdresse, username, password;
	private Thread clientThread;
	private DefaultListModel<Object> model;
	private File file;
	private JButton connexion, inscription, envoyer, parcourir, send,
			fontcolor, cam;
	private JLabel labelUser, labelPass, labelMessage, labelFichier, labelFont,
			labelsize;
	private JToggleButton bold, italique, souligne;
	private JComboBox<Object> font;
	private JComboBox<Object> size;
	private JList<Object> list;
	private JPasswordField jp;
	private JScrollPane jspane1, jspane2;
	private JSeparator jsp1, jsp2;
	private JTextArea text;
	private JTextPane textInput;
	private JTextField user, fichier;
	private Color couleur = Color.black;

	public ClientFrame() {
		init();
		setTitle("Messenger");
		model.addElement("Tout");
		list.setSelectedIndex(0);
		serverAdresse = "localhost";
		port = 12345;
		try {
			client = new SocketClient(ClientFrame.this);
			clientThread = new Thread(client);
			clientThread.start();
			client.send(new Message("test", "testUtilisateur", "testContenu",
					"SERVEUR"));
		} catch (Exception ex) {
			text.append("Application : Serveur introuvable\n");
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void init() {
		JPanel p, p2, p4, p5, p6, p7, p8;
		p = new JPanel();
		p2 = new JPanel();
		p4 = new JPanel();
		p5 = new JPanel();
		p6 = new JPanel();
		p7 = new JPanel();
		p8 = new JPanel();
		p.setLayout(new BorderLayout());
		p2.setLayout(new BoxLayout(p2, BoxLayout.X_AXIS));
		p4.setLayout(new BoxLayout(p4, BoxLayout.X_AXIS));
		p5.setLayout(new BoxLayout(p5, BoxLayout.X_AXIS));
		p6.setLayout(new BoxLayout(p6, BoxLayout.X_AXIS));
		p7.setLayout(new BoxLayout(p7, BoxLayout.Y_AXIS));
		p8.setLayout(new BoxLayout(p8, BoxLayout.X_AXIS));
		labelUser = new JLabel("Utilisateur :");
		labelUser.setFont(new Font("serif", Font.BOLD + Font.ITALIC, 12));
		user = new JTextField(10);
		user.setFont(new Font("serif", Font.BOLD, 12));
		labelPass = new JLabel("Mot de Passe :");
		labelPass.setFont(new Font("serif", Font.BOLD + Font.ITALIC, 12));
		jp = new JPasswordField(10);
		Icon icon1 = new ImageIcon(getClass().getResource("/connexion.png"));
		connexion = new JButton(icon1);
		connexion.setName("Connexion");
		connexion.setToolTipText("Connexion");
		connexion.addActionListener(this);
		Icon icon2 = new ImageIcon(getClass().getResource("/inscription.png"));
		inscription = new JButton(icon2);
		inscription.setName("Inscription");
		inscription.setToolTipText("Inscription");
		inscription.addActionListener(this);
		p2.add(labelUser);
		p2.add(user);
		p2.add(labelPass);
		p2.add(jp);
		p2.add(connexion);
		p2.add(inscription);
		jsp1 = new JSeparator();
		text = new JTextArea(20, 30);
		text.setFont(new Font("serif", Font.BOLD + Font.ITALIC, 14));
		text.setForeground(Color.BLUE);
		text.setEditable(false);
		jspane1 = new JScrollPane();
		jspane1.setViewportView(text);
		list = new JList<>();
		list.setModel(model = new DefaultListModel<>());
		list.setFont(new Font("serif", Font.BOLD + Font.ITALIC, 14));
		jspane2 = new JScrollPane();
		jspane2.setViewportView(list);
		GroupLayout g = new GroupLayout(p4);
		p4.setLayout(g);
		g.setHorizontalGroup(g.createParallelGroup().addGroup(
				GroupLayout.Alignment.TRAILING,
				g.createSequentialGroup()
						.addComponent(jspane1)
						.addGap(5, 5, 5)
						.addComponent(jspane2, GroupLayout.PREFERRED_SIZE, 108,
								GroupLayout.PREFERRED_SIZE)));
		g.setVerticalGroup(g
				.createSequentialGroup()
				.addGroup(
						g.createParallelGroup(GroupLayout.Alignment.LEADING,
								false)
								.addComponent(jspane1)
								.addComponent(jspane2,
										GroupLayout.DEFAULT_SIZE, 264,
										Short.MAX_VALUE))
				.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED));
		labelFont = new JLabel("Select Font :");
		font = new JComboBox<>(new DefaultComboBoxModel<>());
		labelsize = new JLabel("Size :");
		size = new JComboBox<>(new DefaultComboBoxModel<>());
		DefaultComboBoxModel cmbf = new DefaultComboBoxModel<>();
		GraphicsEnvironment e = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		Font[] fonts = e.getAllFonts();
		for (Font f : fonts) {
			cmbf.addElement(f.getFontName());
		}
		font.setModel(cmbf);
		DefaultComboBoxModel cmbs = new DefaultComboBoxModel<>();
		for (int i = 5; i < 48; i++) {
			cmbs.addElement(i);
		}
		size.setModel(cmbs);
		font.setSelectedIndex(0);
		size.setSelectedIndex(12);
		font.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateFont();
			}
		});
		size.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateFont();
			}
		});
		bold = new JToggleButton(new ImageIcon(getClass().getResource(
				"/bold.png")));
		bold.setName("Bold");
		bold.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateFont();
			}
		});
		italique = new JToggleButton(new ImageIcon(getClass().getResource(
				"/italic.png")));
		italique.setName("Italique");
		italique.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateFont();
			}
		});
		souligne = new JToggleButton(new ImageIcon(getClass().getResource(
				"/souligne.png")));
		souligne.setName("Souligne");
		souligne.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateFont();
			}
		});
		fontcolor = new JButton(new ImageIcon(getClass().getResource(
				"/fontcolor.png")));
		fontcolor.setName("Fontcolor");
		fontcolor.addActionListener(this);
		p8.add(labelFont);
		p8.add(font);
		p8.add(labelsize);
		p8.add(size);
		p8.add(bold);
		p8.add(italique);
		p8.add(souligne);
		p8.add(fontcolor);
		labelMessage = new JLabel("Message :");
		labelMessage.setFont(new Font("serif", Font.BOLD + Font.ITALIC, 12));
		textInput = new JTextPane();
		textInput.setEditable(false);
		textInput.setFont(new Font("serif", Font.BOLD, 12));
		Icon icon5 = new ImageIcon(getClass().getResource("/envoyer.png"));
		envoyer = new JButton(icon5);
		envoyer.setName("Envoyer");
		envoyer.setToolTipText("Envoyer");
		envoyer.setEnabled(false);
		envoyer.addActionListener(this);
		Icon icon8 = new ImageIcon(getClass().getResource("/cam.png"));
		cam = new JButton(icon8);
		cam.setName("Camera");
		cam.addActionListener(this);
		p5.add(labelMessage);
		p5.add(textInput);
		p5.add(envoyer);
		p5.add(cam);
		jsp2 = new JSeparator();
		labelFichier = new JLabel("Fichier :");
		labelFichier.setFont(new Font("serif", Font.BOLD + Font.ITALIC, 12));
		fichier = new JTextField(20);
		fichier.setFont(new Font("serif", Font.BOLD, 12));
		fichier.setEditable(false);
		Icon icon6 = new ImageIcon(getClass().getResource("/parcourir.png"));
		parcourir = new JButton(icon6);
		parcourir.setName("Parcourir");
		parcourir.setToolTipText("Parcourir");
		parcourir.setEnabled(false);
		parcourir.addActionListener(this);
		Icon icon7 = new ImageIcon(getClass().getResource("/send.png"));
		send = new JButton(icon7);
		send.setName("Send");
		send.setToolTipText("Envoyer");
		send.addActionListener(this);
		send.setEnabled(false);
		p6.add(labelFichier);
		p6.add(fichier);
		p6.add(parcourir);
		p6.add(send);
		p7.add(p2);
		p7.add(jsp1);
		p7.add(p4);
		p7.add(p8);
		p7.add(p5);
		p7.add(jsp2);
		p7.add(p6);
		p.add("Center", p7);
		setContentPane(p);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		pack();
	}

	public JTextArea getText() {
		return text;
	}

	public void setText(JTextArea text) {
		this.text = text;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getServerAdresse() {
		return serverAdresse;
	}

	public void setServerAdresse(String serverAdresse) {
		this.serverAdresse = serverAdresse;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public JPasswordField getJp() {
		return jp;
	}

	public void setJp(JPasswordField jp) {
		this.jp = jp;
	}

	public JTextField getUser() {
		return user;
	}

	public void setUser(JTextField user) {
		this.user = user;
	}

	public JButton getConnexion() {
		return connexion;
	}

	public void setConnexion(JButton connexion) {
		this.connexion = connexion;
	}

	public JButton getInscription() {
		return inscription;
	}

	public void setInscription(JButton inscription) {
		this.inscription = inscription;
	}

	public JButton getParcourir() {
		return parcourir;
	}

	public void setParcourir(JButton parcourir) {
		this.parcourir = parcourir;
	}

	public Thread getClientThread() {
		return clientThread;
	}

	public void setClientThread(Thread clientThread) {
		this.clientThread = clientThread;
	}

	public DefaultListModel<Object> getModel() {
		return model;
	}

	public void setModel(DefaultListModel<Object> model) {
		this.model = model;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public JButton getEnvoyer() {
		return envoyer;
	}

	public void setEnvoyer(JButton envoyer) {
		this.envoyer = envoyer;
	}

	public String getTextFilter2(String text) {

		String textFi = "";
		ArrayList<String> data = new ArrayList<String>(Arrays.asList("ä", "ê",
				"ë", "ô", "î", "ï", "ô", "ö", "û", "ü", "ÿ", "€", "#", "+",
				"*", " ", "²", "&", "é", "~", "{", "(", "[", "|", "è", "`",
				"ç", "^", "$", "£", "¤", "ù", "%", "*", "µ", ",", "?", ";",
				":", "/", "!", "§", ">", "<"));

		for (int i = 0; i < data.size(); i++) {
			// if(data.get(i).equals(text)){
			if (text.contains(data.get(i))) {

				textFi = "";
			} else {
				textFi = text;
			}
		}
		return textFi;
	}

	/**
	 * Pour tester si le text contient des cartère spéciaux "signature HSI"
	 * 
	 * @param text
	 * @return
	 */
	public boolean getTextFilter(String text) {
		boolean carCond = true;

		ArrayList<String> data = new ArrayList<String>(Arrays.asList("ä", "ê",
				"ë", "ô", "î", "ï", "ô", "ö", "û", "ü", "ÿ", "€", "#", "+",
				"*", " ", "²", "&", "é", "~", "{", "(", "[", "|", "è", "`",
				"ç", "^", "$", "£", "¤", "ù", "%", "*", "µ", ",", "?", ";",
				":", "/", "!", "§", ">", "<"));
		for (int i = 0; i < data.size(); i++) {
			if (text.contains(data.get(i))) {
				carCond = false;
			}
		}
		return carCond;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		boolean caraCond = false;
		JButton src = (JButton) e.getSource();
		switch (src.getName()) {
		case "Connexion":
			username = user.getText();
			password = new String(jp.getPassword());
			if (!username.isEmpty() && !password.isEmpty()) {
				/**
				 * supprimer le contenu des champs user & password " signature HSI"
				 */
				if (getTextFilter(username) == true
						|| getTextFilter(password) == true) {
					caraCond = true;
				}

				if (caraCond == false) {
					src.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							user.setText("");
							jp.setText("");
						}
					});
				}
				/**
				 * si les conditions de la saisie sont correcte, on envoie la
				 * connexion signature HSI
				 */

				username = user.getText();
				password = new String(jp.getPassword());
				// String xUs = user.getText();
				// String xPs = new String(jp.getPassword());
				if (getTextFilter(username) && getTextFilter(password)) {
					username = user.getText();
					password = new String(jp.getPassword());
					client.send(new Message("connexion", username, password,
							"SERVtestEUR"));
					envoyer.setEnabled(true);
					textInput.setEditable(true);
					setTitle(username);
				}
			}

			break;
		case "Inscription":
			username = user.getText();
			password = new String(jp.getPassword());
			if (!username.isEmpty() && !password.isEmpty()) {
				client.send(new Message("inscription", username, password,
						"SERVEUR"));
			}
			break;
		case "Envoyer":
			String msg = textInput.getText();
			String target = list.getSelectedValue().toString();
			if (!msg.isEmpty() && !target.isEmpty()) {
				textInput.setText("");
				client.send(new Message("message", username, msg, target));
				
			}
			break;
		case "Camera":
			new CameraFrame();
			break;
		case "Parcourir":
			JFileChooser fileChooser = new JFileChooser(".");
			fileChooser.showDialog(this, "Select File");
			file = fileChooser.getSelectedFile();
			if (file != null) {
				if (!file.getName().isEmpty()) {
					send.setEnabled(true);
					String str;
					if (fichier.getText().length() > 30) {
						String t = file.getPath();
						str = t.substring(0, 20) + " [...] "
								+ t.substring(t.length() - 20, t.length());
					} else {
						str = file.getPath();
					}
					fichier.setText(str);
				}
			}
			break;
		case "Send":
			long size = file.length();
			if (size < 120 * 1024 * 1024) {
				client.send(new Message("telecharger rej", username, file
						.getName(), list.getSelectedValue().toString()));
			} else {
				text.append("Application : Fichier est trop volumineux \n");
			}
			break;
		case "Fontcolor":
			couleur = JColorChooser.showDialog(this, "Choose Font Color",
					Color.BLACK);
			updateFont();
			break;
		}
	}

	public void updateFont() {
		StyledDocument document = textInput.getStyledDocument();
		SimpleAttributeSet sas = new SimpleAttributeSet();
		StyleConstants.setFontSize(sas,
				Integer.parseInt(size.getSelectedItem().toString()));
		StyleConstants.setForeground(sas, couleur);
		StyleConstants.setBold(sas, bold.isSelected());
		StyleConstants.setItalic(sas, italique.isSelected());
		StyleConstants.setUnderline(sas, souligne.isSelected());
		StyleConstants.setFontFamily(sas, font.getSelectedItem().toString());
		document.setCharacterAttributes(0, document.getLength(), sas, true);
	}

	public void windowActivated(WindowEvent e) {
	}

	public void windowClosed(WindowEvent e) {
	}

	@SuppressWarnings("deprecation")
	public void windowClosing(WindowEvent e) {
		try {
			client.send(new Message("message", username, ".bye", "SERVEUR"));
			clientThread.stop();
		} catch (Exception e2) {
			System.out.println("Erreur : " + e2.getMessage());
		}
	}

	public void windowDeactivated(WindowEvent e) {
	}

	public void windowDeiconified(WindowEvent e) {
	}

	public void windowIconified(WindowEvent e) {
	}

	public void windowOpened(WindowEvent e) {
	}

	public static void main(String[] args) {
		try {
			UIManager
					.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			System.out.println("Erreur : " + e.getMessage());
		}
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new ClientFrame().setVisible(true);
			}
		});
	}
}
