package Inter;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import org.esa.beam.framework.datamodel.Band;


public class BigPanel {
	private BufferedImage bot_img;
	private PixelShow ps;
	private Shape sh_top;
	private LineCharts lc;
	private AnotherSmallImage asi;
	private JPanel panel;
	private DataUtils du;
	
	private int[] botWH = new int[2];//图片的实时大小
	private int[] topXY = new int[2];//图片左上角点的实时位置
	private int flag = 2;//默认的鼠标控制行为标志
	private int tabW, tabH, b_w, b_h;//tabW,tabH为中间窗口的大小；b_w,b_h为图片按照图片比例和窗口大小显示的大小
	
	private float[] longitude;
	private float[] latitude;
	private float[] data;//图像原始数据
	private int rasterW, rasterH;//图片原始大小
	
	private int[] tempColorData;//经过剪切后的图片数据
	private int[] a;//控制裁剪图片的大小
	private float[][] Rrs = null;//产品波段值
	
	//设置中间窗口的大小
	public BigPanel(int w, int h){
		tabH = h;
		tabW = w;
	}

	//设置图片经纬度
	public void setLonLat(float[] lon, float[]lat){
		longitude = lon;
		latitude = lat;
	}
	
	//设置图片原始大小
	public void setRasterWH(int w, int h){
	    rasterW = w;
	    rasterH = h;
	}
	
	
	public void setInit(AnotherSmallImage asi, DataUtils du
			, float[] dat, LineCharts lc, PixelShow ps){
	    this.du = du;
	    this.asi = asi;
		this.ps = ps;	
	    this.lc = lc;
		data = dat;
	}
	
	//图片大小自动适配
	public void setAutoAdaption(){
		asi.setAutoAdaption();
		botWH[0] = b_w;
		botWH[1] = b_h;
		if(asi.getPicScale()<=1.0f){
			topXY[0] = (tabW - b_w) / 2;
			topXY[1] = 0;
		}else{
			topXY[0] = 0;
			topXY[1] = (tabH - b_h) / 2;
		}
		a=asi.getXYandWH();
	    cutColorData(a[0], a[1], a[2], a[3]);
		panel.repaint();
		
	}
	
	//设置图片原始大小
	public void setMaximize(){
		int w = tabW * asi.getPicWidth() / rasterW;
		int h = tabH * asi.getPicHeight() / rasterH;
		int x = 150 - w / 2;//缩略图宽为300
		int y = 105 - h / 2;//缩略图的高为210
		asi.setBounds(x, y, w, h);
		botWH[0] = tabW;
		botWH[1] = tabH;
		topXY[0] = 0; 
		topXY[1] = 0;
		a=asi.getXYandWH();
	    cutColorData(a[0], a[1], a[2], a[3]);	
		panel.repaint();
	}
	
	//中间窗口面板
	public JPanel init(final BufferedImage image){
		 tempColorData=du.getColorData();
		 
		if(asi.getPicScale()<=1.0f){
			b_w=botWH[0] = (int) (tabH * asi.getPicScale());
			b_h=botWH[1] = tabH;
			topXY[0] = (tabW - b_w) / 2;
			topXY[1] = 0;
		}else{
			b_w=botWH[0] = tabW;
			b_h=botWH[1] = (int)(tabW / asi.getPicScale());
			topXY[0] = 0;
			topXY[1] = (tabH - b_h) / 2;
		}
	    asi.setBigWH(tabW, tabH);
		bot_img = image;
		panel = new JPanel(){
			protected void paintComponent(Graphics g){			
				super.paintComponent(g); 		
				if(image != null)		 
				{
					 Graphics2D g2d=(Graphics2D)g;
					 g2d.drawImage(bot_img, topXY[0],topXY[1], botWH[0], botWH[1], null);					
					 sh_top=new Rectangle.Float(topXY[0], topXY[1], botWH[0], botWH[1]);
					 g2d.setColor(Color.WHITE);  
					 g2d.draw(sh_top); 
//					 g2d.dispose();			
				}		
			}
		};  
		
		MouseControl mc=new MouseControl();
		panel.addMouseListener(mc);
		panel.addMouseMotionListener(mc);
		panel.addMouseWheelListener(mc);
		
		panel.setBackground(Color.WHITE);
		return panel;
	}

	//设置鼠标控制方式
	public void setMouseFlags(int num){
		flag = num;
	}
	
	//根据指定大小截取元数据
	public void cutColorData(int x, int y, int w, int h){
		
		int cutData[] = new int[w*h];
		int t=0;
		for(int i=y;i<y+h;i++){
			for(int j=x;j<x+w;j++){
				cutData[t++]=tempColorData[i*rasterW+j];
			}
		}
		bot_img=new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		bot_img.setRGB(0, 0, w, h, cutData, 0, w);				
	}
	
	//中间图片的鼠标控制
	 class MouseControl extends MouseAdapter implements MouseMotionListener, MouseWheelListener {
			private int x, y;
			private int dx, dy;			
			private float scale_x = 1.0f, scale_y = 1.0f;
			private JPanel rec = new JPanel();
		
			public void mousePressed(MouseEvent e) {
				int xm = x = e.getX();
				int ym = y = e.getY();
				if(flag==1){
					if(sh_top.contains(xm , ym)){	
						int imageX, imageY;
						float sx = (float)(xm-topXY[0]) / botWH[0];
						float sy = (float)(ym-topXY[1]) / botWH[1];
						
						float[] b=asi.location(sx, sy);
						imageX = (int) (b[0] * rasterW) ;
						imageY = (int) (b[1] * rasterH) ;
						b = null;
						int num = imageY * rasterW + imageX;
						if(imageX > rasterW | imageY > rasterH){
							System.out.println("数组越界: "+imageX+"    "+imageY);
						}
						Rrs = du.getDataSingle(imageX, imageY);
			
						if(data[num]==-32767){
							lc.clear();
						}else{
							lc.set(set());
							lc.insert(du.getLast_4_Rrs(), Rrs);
						}
//						System.out.println(data[num]);
						ps.insert(imageX, imageY, longitude[num], latitude[num], data[num]);
					 }else{
						 ps.insert();
						 lc.clear();
					 }
				}
			}
			
			public void mouseReleased(MouseEvent e){

				if(flag == 3){
					rec.setVisible(false);	
					float sx = (float)(x-topXY[0]) / botWH[0];
					float sy = (float)(y-topXY[1]) / botWH[1];
					float sw = (float)dx / botWH[0];
					float sh = (float)dy / botWH[1];
					asi.setBounds(sx, sy, sw, sh);
					
					botWH[1]=tabH;
					botWH[0]=tabW;
					topXY[0]=0;
					topXY[1]=0;	

					a=asi.getXYandWH();
				    cutColorData(a[0], a[1], a[2], a[3]);	
					panel.repaint();
				}	
			}
			
			public void mouseDragged(MouseEvent e) {	
				dx = e.getX() - x;
				dy = e.getY() - y;
				if(flag == 2){				
					asi.setBlackXY(dx, dy);
					a=asi.getXYandWH();
				    cutColorData(a[0], a[1], a[2], a[3]);
					panel.repaint();
					x = x + dx;
					y = y + dy;
				}	
				if(flag == 3&&dx>=30&&dy>=30){
					if(sh_top.contains(x , y)){							
						rec.setBounds(x, y, dx, dy);
						rec.setOpaque(false);
						rec.setBackground(Color.BLACK);
						rec.setBorder(BorderFactory.createEtchedBorder());
						rec.setVisible(true);
						panel.add(rec);							
					}
				}
			}
			
			public void mouseMoved(MouseEvent e){
				int xm = e.getX();
				int ym = e.getY();
				if(flag==2 | flag==3){
					if(sh_top.contains(xm , ym)){	
						int imageX, imageY;
						float sx = (float)(xm-topXY[0]) / botWH[0];
						float sy = (float)(ym-topXY[1]) / botWH[1];
						
						float[] b=asi.location(sx, sy);
						imageX = (int) (b[0] * rasterW) ;
						imageY = (int) (b[1] * rasterH) ;
						int num = imageY * rasterW + imageX;
						if(imageX > rasterW | imageY > rasterH){
							System.out.println("数组越界: "+imageX+"    "+imageY);
						}
						Rrs = du.getDataSingle(imageX, imageY);
			
						if(data[num]==-32767){
							lc.clear();
						}else{
							lc.set(set());
							lc.insert(du.getLast_4_Rrs(), Rrs);
						}
//						System.out.println(data[num]);
						ps.insert(imageX, imageY, longitude[num], latitude[num], data[num]);
					 }else{
						 ps.insert();
						 lc.clear();
					 }

				}
				
			}

			public void mouseWheelMoved(MouseWheelEvent e) {
				int num = e.getWheelRotation();
	
				if(num < 0){
					scale_x = scale_y = 0.9f;
					asi.setBlackBounds(scale_x, scale_y, (float)tabW/tabH);
					float s = asi.getWinScale();
					if(asi.getPicScale()<=1.0f){
//						scale_x = scale_y = 0.9f;
//						asi.setBlackBounds(scale_x, scale_y, (float)tabW/tabH);
//						float s = asi.getWinScale();
						botWH[1]=tabH;
						botWH[0]=(int) (tabH * s);
						topXY[1]=0;
					    topXY[0]=(tabW - botWH[0])/2;
//					    a=asi.getXYandWH();
//					    cutColorData(a[0], a[1], a[2], a[3]);	
					}
					if(asi.getPicScale()>1.0f){
						botWH[0]=tabW;
						botWH[1]=(int) (tabW/s);
						topXY[1]=(tabH-botWH[1])/2;
					    topXY[0]=0;				   
					}
					 a=asi.getXYandWH();
					 cutColorData(a[0], a[1], a[2], a[3]);	
	
				}
				if(num > 0){		
					scale_x = scale_y = 1.1f;
					asi.setBlackBounds(scale_x, scale_y, (float)tabW/tabH);
					float s = asi.getWinScale();
					if(asi.getPicScale()<=1.0f){						
						botWH[1]=tabH;
						botWH[0]=(int) (tabH * s);
						topXY[1]=0;
					    topXY[0]=(tabW - botWH[0])/2;				   
					}
					if(asi.getPicScale()>1.0f){
						botWH[0]=tabW;
						botWH[1]=(int) (tabW/s);
						topXY[1]=(tabH-botWH[1])/2;
					    topXY[0]=0;
					}
					a=asi.getXYandWH();
					cutColorData(a[0], a[1], a[2], a[3]);		
				}				
				panel.repaint();
			}

			//求波段值的最大值，最小值
			public float[] set(){
				float[][] a=Rrs;
				float min = Rrs[0][0];
				float max = Rrs[0][0];
				for(float[] d1: a){
					for (float d : d1) {
						if (d < min) 
							min = d;    
					}					
				}
				for(float[] d1:a){
					for (float d : d1) {
						if (d > max) 
							max = d;    
					}
					
				}
				float[] b=new float[]{min,max};
				return b;																		
			}
			
		}
}
