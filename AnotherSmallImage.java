package Inter;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class AnotherSmallImage extends JPanel{
	private JPanel panel;
	private int width, height;//遮罩大小
	private int tx, ty;//遮罩左上角的点
	private int center_x, center_y;//图片中心点
	private int bigWidth, bigHeight;//大（中间）窗口的大小
	private float picScale;//图片原始比例
	private int b_w, b_h;//图片按比例显示的大小
	private int rasterW, rasterH;//图片原始大小
	
	public AnotherSmallImage(){

	}
	
	//初始化窗口信息
	public JPanel init(final BufferedImage bot_img, DataUtils du){
		rasterH=du.getRasterHeight();
		rasterW=du.getRasterWidth();
		center_x = 150;
		center_y = 105;
//		bot_img = bot_img_1;
		picScale = (float)bot_img.getWidth() / bot_img.getHeight();
		
		if(picScale<=1.0f){
			b_h=height = 210;
			b_w=width = (int) (210 * picScale);
			ty = 0;
			tx = (300 - b_w) / 2;
		}else{
			b_h=height = (int) (300 / picScale);
			b_w=width = 300;
			tx = 0;
			ty = (210 - b_h) / 2;
		}
		
	
		 panel = new JPanel(){
			 protected void paintComponent(Graphics g){
				 super.paintComponent(g);  
		     //如果设置了背景图片则显示
				 if(bot_img != null)
				 {
					 Graphics2D g2d=(Graphics2D)g;
					 if(picScale<=1.0f){
						 g.drawImage(bot_img, (300 - b_w) / 2, 0, b_w , b_h, null);				 
					 }
					 if(picScale>1.0f){
						 g.drawImage(bot_img, 0, (210 - b_h) / 2, b_w , b_h, null);				
					 }
					 AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);
					 g2d.setComposite(alphaComposite);
					 g2d.drawImage(generateMask(), tx, ty, width, height, null);
					 g2d.dispose();
				 }
			 }	 
		};

		return panel;
	}
	
	//生成遮罩
	 public BufferedImage generateMask(){		
		 int[]rgb={0,0,255};
		 int[] lightBlue=new int[200 * 200];
		 for(int i=0;i<200 * 200;i++){
			 lightBlue[i]= rgb[ 0 ]  << 16 |  rgb[ 1 ] << 8 |  rgb[ 2 ];		
		 }
		 BufferedImage image = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
		 image.setRGB(0, 0, 200,200, lightBlue, 0, 200);
		 return image;
	   }
	 
	 //获取大窗口大小
	public void setBigWH(int tabW, int tabH){
		bigWidth = tabW;
		bigHeight = tabH;
	}
	
	//设置遮罩移动
	public void setBlackXY(int move_x, int move_y){		
	
		tx = tx - (int)(move_x * (float)width / bigWidth);
		ty = ty - (int)(move_y * (float)height / bigHeight);
		if(picScale<=1.0f){
			if(tx<=((300 - (int) (210 * picScale)) / 2)){
				tx=((300 - (int) (210 * picScale)) / 2);
			}
			if((tx+width)>=(300 - b_w) / 2 + b_w){
				tx=(300 - b_w) / 2 + b_w-width;
			}
			if(ty<=0){
				ty=0;
			}
			if((ty+height)>=b_h){
				ty=b_h-height;
			}
		}
		if(picScale>1.0f){
			if(ty<=(210 - (int) (300 / picScale) / 2)){
				ty=(210 - (int) (300 / picScale) / 2);
			}		
			if((ty+height)>=b_h+(210 - b_h) / 2){//先这样写
				ty=b_h+(210 - b_h) / 2 - height;
			}
			if(tx<=0){
				tx=0;
			}
			if((tx+width)>=b_w){
				tx=b_w-width;
			}
		}
		center_x = tx + width / 2;
		center_y = ty + height / 2;
		panel.repaint();
	}
	
	//设置遮罩缩放
	public void setBlackBounds(float scale_x, float scale_y, float s){

		if(picScale<=1.0f){
			if((float)width/height<=s&&width==b_w){
				height = (int) (height / scale_y);
			}else{	
				height = (int) (height / scale_y);
				width=height * bigWidth / bigHeight;
			}
			if(width>b_w){
				width=b_w;
			}
			if(height>b_h){
				height=b_h;
			}
			if(height<=20){
				height=20;
				width=height * bigWidth / bigHeight;
			}
			tx = center_x - width / 2;
			ty = center_y - height / 2;
			if(tx<=((300 - (int) (210 * picScale)) / 2)){
				tx=(300 - (int) (210 * picScale)) / 2;
			}else if((tx+width)>=150 + b_w / 2){
				tx=150 + b_w / 2-width;
			}
		
			if(ty<=0){
				ty=0;
			}else if((ty+height)>=b_h){
				ty=b_h-height;
			}
			
		}else{
			if((float)width/height>=s&&height==b_h){
				width = (int) (width / scale_y);
			}else{	
				width = (int) (width / scale_y);
				height=width * bigHeight / bigWidth;
			}
			if(width>b_w){
				width=b_w;
			}
			if(height>b_h){
				height=b_h;
			}
			if(width<=20){
				width=20;
				height=width * bigHeight / bigWidth;
			}
			tx = center_x - width / 2;
			ty = center_y - height / 2;
			if(ty<=(210 - (int) (300 / picScale) / 2)){
				ty=(210 - (int) (300 / picScale) / 2);
			}		
			if((ty+height)>=b_h+(210 - b_h) / 2){//先这样写
				ty=b_h+(210 - b_h) / 2 - height;
			}
			if(tx<=0){
				tx=0;
			}
			if((tx+width)>=b_w){
				tx=b_w-width;
			}
		}
		center_x = tx + width / 2;
		center_y = ty + height / 2;
		panel.repaint();
//		maskPanel.setBounds(tx, ty, width, height);	
	}
	
	//设置遮罩位置信息
	public void setBounds(int x, int y, int w, int h){	
		tx = x;
		ty = y;
		if(w>=h){
			h=w * bigHeight / bigWidth;
		}else{
			w=h * bigWidth / bigHeight;
		}
		width = w;
		height = h; 
		center_x = tx + width / 2;
		center_y = ty + height / 2;
		panel.repaint();
	}
	
	//设置遮罩的位置信息
	public void setBounds(float x, float y, float w, float h){	
		tx = (int) (tx+x*width);
		ty = (int) (ty+y*height);
		if(picScale<=1.0f){
			if(height<=20){
				height=20;
				width=height * bigWidth / bigHeight;
			}else{
				height = (int) (height*h);
				width=height * bigWidth / bigHeight;
			}
			
		}else{
			if(width<=20){
				width=20;
				height=width * bigHeight / bigWidth;
			}else{
				width=(int) (width*w);
				height=width*bigHeight/bigWidth;
			}
		}
		
		
		center_x = tx + width / 2;
		center_y = ty + height / 2;
		panel.repaint();
	}
	
	//设置鼠标当前点所在图像的位置
	public float[] location(float x, float y){
		float[] a=new float[2];
		if(picScale<=1.0f){
			a[0]=(tx+x*width-(300-b_w)/2)/b_w;
			a[1]=(ty+y*height)/b_h;
		}else{
			a[0]=(tx+x*width)/b_w;
			a[1]=(ty+y*height-(210-b_h)/2)/b_h;
		}
		
		return a;
	}
	
	
	public float getPicScale(){
		return picScale;
	}
	
	public float getWinScale(){
		return (float)width/height;	
	}
	
	public int getPicWidth(){
		return b_w;
	}
	
	public int getPicHeight(){
		return b_h;
	}

	//获取裁剪图像位置信息
	public int[] getXYandWH(){
		int[] a = new int[4];
		if(picScale<=1.0f){
			a[0]=(tx-(300-b_w)/2)*rasterW/b_w;
			a[1]=ty*rasterH/b_h;
			a[2]=width *rasterW / b_w;
			a[3]=height*rasterH/210;		
		}else{
			a[0]=tx*rasterW/b_w;
			a[1]=(ty-(210-b_h)/2)*rasterH/b_h;
			a[2]=width *rasterW /300;
			a[3]=height*rasterH/b_h;
		}
		return a;
	}

	
	//遮罩自动适配窗口大小
	public void setAutoAdaption(){
		width=b_w;
		height=b_h;
		tx = (300 - b_w) / 2;
		ty = (210 - b_h) / 2;
		panel.repaint();
	}


}
