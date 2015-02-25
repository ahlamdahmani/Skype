package com.esisa.java.reseau.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.InetSocketAddress;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.sosia.video.stream.agent.StreamClientAgent;
import us.sosia.video.stream.agent.StreamServerAgent;
import us.sosia.video.stream.agent.ui.SingleVideoDisplayWindow;
import us.sosia.video.stream.handler.StreamFrameListener;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;

@SuppressWarnings("serial")
public class CameraFrame extends JDialog implements ActionListener, Runnable {
	private final static Dimension dimension = new Dimension(320, 240);
	private final static SingleVideoDisplayWindow displayWindow = new SingleVideoDisplayWindow(
			"Stream example", dimension);
	protected final static Logger logger = LoggerFactory
			.getLogger(CameraFrame.class);

	
	public CameraFrame() {
		JPanel p1 = new JPanel();
		p1.setLayout(new BorderLayout());
		JPanel p = new JPanel();
		Webcam.setAutoOpenMode(true);
		Webcam webcam = Webcam.getDefault();
		webcam.setViewSize(dimension);
		StreamServerAgent serverAgent = new StreamServerAgent(webcam, dimension);
		serverAgent.start(new InetSocketAddress("192.168.43.52", 20000));
		WebcamPanel wp = new WebcamPanel(webcam);
		p.add(wp);
		p.add(displayWindow);
		JButton b = new JButton("Connecter");
		b.addActionListener(this);
		p1.add("Center",p);
		p1.add("South",b);
		setContentPane(p1);
		setResizable(false);
		pack();
		setVisible(true);
	}
	 protected static class StreamFrameListenerIMPL implements
	 StreamFrameListener {
	 private volatile long count = 0;
	
	 public void onFrameReceived(BufferedImage image) {
	 logger.info("frame received :{}", count++);
	 displayWindow.updateImage(image);
	 }
	
	 }

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();
		if (b.getText() == "Connecter") {
			 logger.info("setup dimension :{}", dimension);
			 StreamClientAgent clientAgent = new StreamClientAgent(
					 new StreamFrameListenerIMPL(), dimension);
			 clientAgent.connect(new InetSocketAddress("192.168.43.135",
			 20000));

		}
	}

	@Override
	public void run() {
		
	}
}
