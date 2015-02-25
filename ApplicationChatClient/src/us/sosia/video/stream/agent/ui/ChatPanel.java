package us.sosia.video.stream.agent.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatPanel extends JDialog implements ActionListener{
	private JTextArea textChat;
	private JTextField text;
	private JButton button;
	private MulticastSocket s;
	private DatagramPacket p;
	
	public ChatPanel() throws IOException {
		textChat = new JTextArea();
		text = new JTextField();
		button = new JButton("Send");
		button.addActionListener(this);
		JPanel p= new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
		p.add(text);
		p.add(button);
		add("Center",textChat);
		add("South",p);
		s = new MulticastSocket();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			JButton b = (JButton)e.getSource();
			byte[] buffer = text.getText().getBytes();
			p = new DatagramPacket(buffer, buffer.length,
					InetAddress.getByName("225.2.2.2"), 44444);
			s.setTimeToLive(15);
			s.send(p);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	public void start(){
		while (true) {
			try {
				s.receive(p);
				textChat.append(p.toString()+"\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
