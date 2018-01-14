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
	
	private int[] botWH = new int[2];//ͼƬ��ʵʱ��С
	private int[] topXY = new int[2];//ͼƬ���Ͻǵ��ʵʱλ��
	private int flag = 2;//Ĭ�ϵ���������Ϊ��־
	private int tabW, tabH, b_w, b_h;//tabW,tabHΪ�м䴰�ڵĴ�С��b_w,b_hΪͼƬ����ͼƬ�����ʹ��ڴ�С��ʾ�Ĵ�С
	
	private float[] longitude;
	private float[] latitude;
	private float[] data;//ͼ��ԭʼ����
	private int rasterW, rasterH;//ͼƬԭʼ��С
	
	private int[] tempColorData;//�������к��ͼƬ����
	private int[] a;//���Ʋü�ͼƬ�Ĵ�С
	private float[][] Rrs = null;//��Ʒ����ֵ
	
	//�����м䴰�ڵĴ�С
	public BigPanel(int w, int h){
		tabH = h;
		tabW = w;
	}

	//����ͼƬ��γ��
	public void setLonLat(float[] lon, float[]lat){
		longitude = lon;
		latitude = lat;
	}
	
	//����ͼƬԭʼ��С
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
	
	//ͼƬ��С�Զ�����
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
	
	//����ͼƬԭʼ��С
	public void setMaximize(){
		int w = tabW * asi.getPicWidth() / rasterW;
		int h = tabH * asi.getPicHeight() / rasterH;
		int x = 150 - w / 2;//����ͼ��Ϊ300
		int y = 105 - h / 2;//����ͼ�ĸ�Ϊ210
		asi.setBounds(x, y, w, h);
		botWH[0] = tabW;
		botWH[1] = tabH;
		topXY[0] = 0; 
		topXY[1] = 0;
		a=asi.getXYandWH();
	    cutColorData(a[0], a[1], a[2], a[3]);	
		panel.repaint();
	}
	
	//�м䴰�����
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

	//���������Ʒ�ʽ
	public void setMouseFlags(int num){
		flag = num;
	}
	
	//����ָ����С��ȡԪ����
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
	
	//�м�ͼƬ��������
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
							System.out.println("����Խ��: "+imageX+"    "+imageY);
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
							System.out.println("����Խ��: "+imageX+"    "+imageY);
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

			//�󲨶�ֵ�����ֵ����Сֵ
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
