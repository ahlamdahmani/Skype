package us.sosia.video.stream.agent;

import java.awt.Dimension;
import java.net.InetSocketAddress;

import com.github.sarxos.webcam.Webcam;

public class StreamServer {
	public StreamServer() {
		Webcam.setAutoOpenMode(true);
		Webcam webcam = Webcam.getDefault();
		Dimension dimension = new Dimension(320, 240);
		webcam.setViewSize(dimension);

		StreamServerAgent serverAgent = new StreamServerAgent(webcam, dimension);
		serverAgent.start(new InetSocketAddress("192.168.1.72", 20000));
		//StreamClient sc = new StreamClient();
	
	}

	public static void main(String[] args) {
		new StreamServer();
	}

}
