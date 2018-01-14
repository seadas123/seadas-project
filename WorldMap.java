package Inter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class WorldMap extends JPanel{
	private BufferedImage worldMap;
	private int w, h;
	public WorldMap(int w, int h, BufferedImage worldMap){
		this.w = w;
		this.h = h;
		this.worldMap = worldMap;
	}
	
	protected void paintComponent(Graphics g){
		 super.paintComponent(g); 
    //如果设置了背景图片则显示
		 if(worldMap != null)
		 {
			 g.drawImage(worldMap, 0, 40, w, h, null);	
			 //this.repaint();
			 g.dispose();
		 }
	 }	 
}
