package Inter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

class WorldMapPanel extends JPanel {
	private BufferedImage image;
	private List<float[]> list;
	private float[] lon = null;
	private float[] lat = null;
	
	public WorldMapPanel(List<float[]> list) {
		this.list = list;
		lon = list.get(1);
		lat = list.get(0);
		init();
	}
	
	public WorldMapPanel() {
		init();
	}
	
	public void init(){
		String f_1 = System.getProperty("user.dir") + "\\3.jpeg";
		try {
			image = ImageIO.read(new File(f_1));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.clearRect(0, 0, getWidth(), getHeight());
		if (image != null) {
			int xlength;
			int ylength;
			int starty;
			int startx;
			if (image.getWidth() / image.getHeight() > getWidth() / getHeight()) {
				startx = 0;
				starty = (getHeight() - getWidth() * image.getHeight() / image.getWidth()) / 2;
				xlength = getWidth();
				ylength = getWidth() * image.getHeight() / image.getWidth();

			} else {
				startx = (getWidth() - getHeight() * image.getWidth() / image.getHeight()) / 2;
				starty = 0;
				xlength = getHeight() * image.getWidth() / image.getHeight();
				ylength = getHeight();

			}

			g.drawImage(image, startx, starty, xlength, ylength, this);
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(Color.RED);
			if (list != null) {
				for (int i = 0; i < 3; i++) {
					g2.drawLine((int) Math.round((lon[i] + 179.5) / (179.5 * 2) * xlength) + startx,
							(int) Math.round((lat[i] - 89.5) / (-89.5 * 2) * ylength) + starty,
							(int) Math.round((lon[i + 1] + 179.5) / (179.5 * 2) * xlength) + startx,
							(int) Math.round((lat[i + 1] - 89.5) / (-89.5 * 2) * ylength) + starty);
				}
				g2.drawLine((int) Math.round((lon[3] + 179.5) / (179.5 * 2) * xlength) + startx,
						(int) Math.round((lat[3] - 89.5) / (-89.5 * 2) * ylength) + starty,
						(int) Math.round((lon[0] + 179.5) / (179.5 * 2) * xlength) + startx,
						(int) Math.round((lat[0] - 89.5) / (-89.5 * 2) * ylength) + starty);


			}
			g2.dispose();
		}

	}

}

