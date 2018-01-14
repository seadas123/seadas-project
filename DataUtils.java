package Inter;

import gov.nasa.gsfc.seadas.dataio.L2ProductReaderPlugIn;
import gov.nasa.gsfc.seadas.dataio.SeadasProductReader;
import gov.nasa.gsfc.seadas.dataio.SeadasProductReader.ProductType;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.imageio.ImageIO;

import org.esa.beam.framework.dataio.ProductIOException;
import org.esa.beam.framework.datamodel.Band;
import org.esa.beam.framework.datamodel.Product;
import org.esa.beam.framework.datamodel.ProductData;

public class DataUtils {
	
	private L2ProductReaderPlugIn l2prpi = new L2ProductReaderPlugIn();
	private SeadasProductReader spr = new SeadasProductReader(l2prpi);
	private Product product;
	private Band[] band = null;//Rrs
	private Band lonBand=null, latBand=null;//Lon, Lat
	private int[] color_data = null;
	private float[] data = null;
	
	public float[][] single_data;
	private BufferedImage image = null;
	private float[] lonData = null;
	private float[] latData = null;
	private int n=0;
	private int colorIndex = 0;
	private int rasterW = 0;
	private int rasterH = 0;
	private  int[][][] color_tab = { 
			
			//彩色颜色
			
			{	{0,0,131},{0,0,135},{0,0,139},{0,0,143},{0,0,147},
				{0,0,151},{0,0,155},{0,0,159},{0,0,163},{0,0,167},
				{0,0,171},{0,0,175},{0,0,179},{0,0,183},{0,0,187},
				{0,0,191},{0,0,195},{0,0,199},{0,0,203},{0,0,207},
				{0,0,211},{0,0,215},{0,0,219},{0,0,223},{0,0,227},
				{0,0,231},{0,0,235},{0,0,239},{0,0,243},{0,0,247},
				{0,0,251},{0,0,255},{0,4,255},{0,8,255},{0,12,255},
				{0,16,255},{0,20,255},{0,24,255},{0,28,255},{0,32,255},
				{0,36,255},{0,40,255},{0,44,255},{0,48,255},{0,52,255},
				{0,56,255},{0,60,255},{0,64,255},{0,68,255},{0,72,255},
				{0,76,255},{0,80,255},{0,84,255},{0,88,255},{0,92,255},
				{0,96,255},{0,100,255},{0,104,255},{0,108,255},{0,112,255},
				{0,116,255},{0,120,255},{0,124,255},{0,128,255},{0,131,255},
				{0,135,255},{0,139,255},{0,143,255},{0,147,255},{0,151,255},
				{0,155,255},{0,159,255},{0,163,255},{0,167,255},{0,171,255},
				{0,175,255},{0,179,255},{0,183,255},{0,187,255},{0,191,255},
				{0,195,255},{0,199,255},{0,203,255},{0,207,255},{0,211,255},
				{0,215,255},{0,219,255},{0,223,255},{0,227,255},{0,231,255},
				{0,235,255},{0,239,255},{0,243,255},{0,247,255},{0,251,255},
				{0,255,255},{4,255,251},{8,255,247},{12,255,243},{16,255,239},
				{20,255,235},{24,255,231},{28,255,227},{32,255,223},{36,255,219},
				{40,255,215},{44,255,211},{48,255,207},{52,255,203},{56,255,199},
				{60,255,195},{64,255,191},{68,255,187},{72,255,183},{76,255,179},
				{80,255,175},{84,255,171},{88,255,167},{92,255,163},{96,255,159},
				{100,255,155},{104,255,151},{108,255,147},{112,255,143},{116,255,139},
				{120,255,135},{124,255,131},{128,255,128},{131,255,124},{135,255,120},
				{139,255,116},{143,255,112},{147,255,108},{151,255,104},{155,255,100},
				{159,255,96},{163,255,92},{167,255,88},{171,255,84},{175,255,80},
				{179,255,76},{183,255,72},{187,255,68},{191,255,64},{195,255,60},
				{199,255,56},{203,255,52},{207,255,48},{211,255,44},{215,255,40},
				{219,255,36},{223,255,32},{227,255,28},{231,255,24},{235,255,20},
				{239,255,16},{243,255,12},{247,255,8},{251,255,4},{255,255,0},
				{255,251,0},{255,247,0},{255,243,0},{255,239,0},{255,235,0},
				{255,231,0},{255,227,0},{255,223,0},{255,219,0},{255,215,0},
				{255,211,0},{255,207,0},{255,203,0},{255,199,0},{255,195,0},
				{255,191,0},{255,187,0},{255,183,0},{255,179,0},{255,175,0},
				{255,171,0},{255,167,0},{255,163,0},{255,159,0},{255,155,0},
				{255,151,0},{255,147,0},{255,143,0},{255,139,0},{255,135,0},
				{255,131,0},{255,128,0},{255,124,0},{255,120,0},{255,116,0},
				{255,112,0},{255,108,0},{255,104,0},{255,100,0},{255,96,0},
				{255,92,0},{255,88,0},{255,84,0},{255,80,0},{255,76,0},
				{255,72,0},{255,68,0},{255,64,0},{255,60,0},{255,56,0},
				{255,52,0},{255,48,0},{255,44,0},{255,40,0},{255,36,0},
				{255,32,0},{255,28,0},{255,24,0},{255,20,0},{255,16,0},
				{255,12,0},{255,8,0},{255,4,0},{255,0,0},{251,0,0},
				{247,0,0},{243,0,0},{239,0,0},{235,0,0},{231,0,0},
				{227,0,0},{223,0,0},{219,0,0},{215,0,0},{211,0,0},
				{207,0,0},{203,0,0},{199,0,0},{195,0,0},{191,0,0},
				{187,0,0},{183,0,0},{179,0,0},{175,0,0},{171,0,0},
				{167,0,0},{163,0,0},{159,0,0},{155,0,0},{151,0,0},
				{147,0,0},{143,0,0},{139,0,0},{135,0,0},{131,0,0},{128,0,0}},
			
			
			//灰色颜色
			
			{		{0,0,0},{1,1,1},{2,2,2},{3,3,3},{4,4,4},
					{5,5,5},{6,6,6},{7,7,7},{8,8,8},{9,9,9},
					{10,10,10},{11,11,11},{12,12,12},{13,13,13},{14,14,14},
					{15,15,15},{16,16,16},{17,17,17},{18,18,18},{19,19,19},
					{20,20,20},{21,21,21},{22,22,22},{23,23,23},{24,24,24},
					{25,25,25},{26,26,26},{27,27,27},{28,28,28},{29,29,29},
					{30,30,30},{31,31,31},{32,32,32},{33,33,33},{34,34,34},
					{35,35,35},{36,36,36},{37,37,37},{38,38,38},{39,39,39},
					{40,40,40},{41,41,41},{42,42,42},{43,43,43},{44,44,44},
					{45,45,45},{46,46,46},{47,47,47},{48,48,48},{49,49,49},
					{50,50,50},{51,51,51},{52,52,52},{53,53,53},{54,54,54},
					{55,55,55},{56,56,56},{57,57,57},{58,58,58},{59,59,59},
					{60,60,60},{61,61,61},{62,62,62},{63,63,63},{64,64,64},
					{65,65,65},{66,66,66},{67,67,67},{68,68,68},{69,69,69},
					{70,70,70},{71,71,71},{72,72,72},{73,73,73},{74,74,74},
					{75,75,75},{76,76,76},{77,77,77},{78,78,78},{79,79,79},
					{80,80,80},{81,81,81},{82,82,82},{83,83,83},{84,84,84},
					{85,85,85},{86,86,86},{87,87,87},{88,88,88},{89,89,89},
					{90,90,90},{91,91,91},{92,92,92},{93,93,93},{94,94,94},
					{95,95,95},{96,96,96},{97,97,97},{98,98,98},{99,99,99},
					{100,100,100},{101,101,101},{102,102,102},{103,103,103},{104,104,104},
					{105,105,105},{106,106,106},{107,107,107},{108,108,108},{109,109,109},
					{110,110,110},{111,111,111},{112,112,112},{113,113,113},{114,114,114},
					{115,115,115},{116,116,116},{117,117,117},{118,118,118},{119,119,119},
					{120,120,120},{121,121,121},{122,122,122},{123,123,123},{124,124,124},
					{125,125,125},{126,126,126},{127,127,127},{128,128,128},{129,129,129},
					{130,130,130},{131,131,131},{132,132,132},{133,133,133},{134,134,134},
					{135,135,135},{136,136,136},{137,137,137},{138,138,138},{139,139,139},
					{140,140,140},{141,141,141},{142,142,142},{143,143,143},{144,144,144},
					{145,145,145},{146,146,146},{147,147,147},{148,148,148},{149,149,149},
					{150,150,150},{151,151,151},{152,152,152},{153,153,153},{154,154,154},
					{155,155,155},{156,156,156},{157,157,157},{158,158,158},{159,159,159},
					{160,160,160},{161,161,161},{162,162,162},{163,163,163},{164,164,164},
					{165,165,165},{166,166,166},{167,167,167},{168,168,168},{169,169,169},
					{170,170,170},{171,171,171},{172,172,172},{173,173,173},{174,174,174},
					{175,175,175},{176,176,176},{177,177,177},{178,178,178},{179,179,179},
					{180,180,180},{181,181,181},{182,182,182},{183,183,183},{184,184,184},
					{185,185,185},{186,186,186},{187,187,187},{188,188,188},{189,189,189},
					{190,190,190},{191,191,191},{192,192,192},{193,193,193},{194,194,194},
					{195,195,195},{196,196,196},{197,197,197},{198,198,198},{199,199,199},
					{200,200,200},{201,201,201},{202,202,202},{203,203,203},{204,204,204},
					{205,205,205},{206,206,206},{207,207,207},{208,208,208},{209,209,209},
					{210,210,210},{211,211,211},{212,212,212},{213,213,213},{214,214,214},
					{215,215,215},{216,216,216},{217,217,217},{218,218,218},{219,219,219},
					{220,220,220},{221,221,221},{222,222,222},{223,223,223},{224,224,224},
					{225,225,225},{226,226,226},{227,227,227},{228,228,228},{229,229,229},
					{230,230,230},{231,231,231},{232,232,232},{233,233,233},{234,234,234},
					{235,235,235},{236,236,236},{237,237,237},{238,238,238},{239,239,239},
					{240,240,240},{241,241,241},{242,242,242},{243,243,243},{244,244,244},
					{245,245,245},{246,246,246},{247,247,247},{248,248,248},{249,249,249},
					{250,250,250},{251,251,251},{252,252,252},{253,253,253},{254,254,254},
					{255,255,255}}
	};
	
	public DataUtils(){

	}
	
	public DataUtils(String path) {
		spr.setInput(path);
		try {
			product = spr.readProductNodesImpl();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	public void setNewPath(String newp) {
		spr.setInput(newp);
		try {
			product = spr.readProductNodesImpl();
			band = null;
			lonBand = null;
			latBand = null;
			color_data = null;
			data = null;
			single_data = null;
			image = null;
			lonData = null;
			latData = null;
			System.gc();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	public String[] getBandNames() {
		return product.getBandNames();
	}
	
	//获取以Rrs开头的波段名
	public List<String> getRrs(){
		String[] bandName = getBandNames();
		List<String> Rrs = new ArrayList<>();
		for (int j = 0; j < bandName.length; j++) {
			if (bandName[j].substring(0, 3).equals("Rrs")) {
				Rrs.add(bandName[j]);
			}
		}
		return Rrs;
	}
	
	
	public void close(){
		try {
			band = null;
			lonBand = null;
			latBand = null;
			color_data = null;
			data = null;
			single_data = null;
			image = null;
			lonData = null;
			latData = null;
			product.dispose();
			product.closeProductReader();
			product.closeIO();
			spr.close();
			System.gc();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//释放每次点击变量时占用的内存
	public void release(){
//		image = null;
		System.gc();
	}
	
	//获取文件类型
	public ProductType getProductType(){
		ProductType productType=null;
		try {
			productType =  spr.findProductType();
		} catch (ProductIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return productType;
	}
	
	//获取鼠标点击时的band数据
	public float[] getData(String bandName) {
		Band band=null;
		double scale_factor = 0;
		double add_offset = 0;
		band = product.getBand(bandName);
//		rasterW=band.getRasterWidth();
//		rasterH=band.getRasterHeight();
		if (data==null){
			data = new float[rasterW * rasterH];
		}
		try {
			band.readPixels(0, 0, rasterW, rasterH, data);
			scale_factor = band.getScalingFactor();
			add_offset = band.getScalingOffset();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		if(band.getDataType()==ProductData.TYPE_INT16){
//			for(int i=0;i<rasterW*rasterH;i++)
//			{
//				data[i]= (float) ((data[i] - add_offset) / scale_factor);	
//			}
//			
//		}
			for(int i=0;i<rasterW*rasterH;i++)
			{
				if(Math.abs(data[i]-(-32767.0 * scale_factor + add_offset))<=0.0001)
					data[i]=-32767.0f;
			}


//		try {
//			band.readPixels(0, 0, rasterW, rasterH, data);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		band = null;
		return data;
	}
	
	//获取地理经度
	public float[] getLon(){
		return lonData;
	}
	
	//获取地理纬度
	public float[] getLat(){
		return latData;
	}
	
	public Band getBand(String bandName) {
		Band band = null;
		band = product.getBand(bandName);
		return band;
	}


	//获取Rrs的波段长度
	public float[] getLast_4_Rrs(){
		List<String> Rrs = null;
		Rrs = getRrs();
		String[] last4s = new String[Rrs.size()];
		float[] last4d = new float[Rrs.size()]; 
		for(int i=0; i<Rrs.size(); i++){
			last4s[i] = Rrs.get(i).substring(4);
			last4d[i] = Float.parseFloat(last4s[i]);
		}
		last4s = null;
		Rrs = null;
		return last4d;
	}
	
	//获取所有Rrs波段值
	public float[][] getDataSingle(int x1, int x2) {
		List<String> Rrs = null;
		Rrs = getRrs();//应该每个文件只执行一次，这里执行了很多次
		if(single_data==null | Rrs==null | band==null | n!=Rrs.size()){
			Rrs = getRrs();
			n=Rrs.size();
			band = new Band[n];
			single_data = new float[n][1];
		}

		for(int i=0;i<Rrs.size();i++){
			band[i]=getBand(Rrs.get(i));
			try {
				band[i].readPixels(x1, x2, 1, 1, single_data[i]);	
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Rrs = null;
		return single_data;
	}

	//获取图片颜色标志信息
	public int[] getFlag() {
		Band band = null;
		int[] flags = new int[rasterW * rasterH];
		
		band = product.getBand("l2_flags");
		try {
			band.readPixels(0, 0, rasterW, rasterH, flags);
		} catch (IOException e) {
			e.printStackTrace();
		}
		band = null;
		return flags;
	}
	
	//获取处理后的图片颜色数组
	public int[] getColorData(){
		return color_data;
	}
	
	//根据元数据和属性名生成图像
	public BufferedImage generateBot(String bandName) {
		float minValue = 0;
		float maxValue = 0;
//		float[] temp_data=null;
		float temp_data=0;
		boolean log_10_Flag = false;
		int[] flags = getFlag();
		if (color_data == null){
			color_data = new int[rasterW * rasterH];
//			temp_data = new float[rasterW * rasterH];
		}
		switch(bandName){
		case "aot_862":
			minValue = 0.0f;
			maxValue = 0.25f;
			colorIndex = 0;
			break;
		case "angstrom":
			minValue = -0.15f;
			maxValue = 2.2f;
			colorIndex = 0;
			break;
		case "chlor_a": 
		case "chl_ocx":
			minValue = (float) Math.log10(0.01);
			maxValue = (float) Math.log10(20);
			log_10_Flag = true;
			colorIndex = 0;
			break;
		case "Kd_490":
			minValue = (float) Math.log10(0.01);
			maxValue = (float) Math.log10(6.0);
			log_10_Flag = true;
			colorIndex = 0;
			break;
		case "pic":
			minValue = (float) Math.log10(0.00005);
			maxValue = (float) Math.log10(0.05);
			log_10_Flag = true;
			colorIndex = 0;
			break;
		case "poc":
			minValue = (float) Math.log10(10);
			maxValue = (float) Math.log10(1000);
			log_10_Flag = true;
			colorIndex = 0;
			break;
		case "par":
			minValue = 0;
			maxValue = 76.2f;
			colorIndex = 0;
			break;
		case "l2_flags":
			minValue = 0;
			maxValue = 1343226177;
			colorIndex = 1;
			break;
		case "longitude":
			minValue = 110.097f;
			maxValue = 140.643f;
			colorIndex = 1;
			break;
		case "latitude":
			minValue = 16.775f;
			maxValue = 38.073f;
			colorIndex = 1;
			break;
		default:
			minValue = 0.0f;
			maxValue = 0.025f;
			colorIndex = 0;
			break;
		}

		int index = 0;
		for (int j = 0; j < data.length; ++j, ++index) {
			if ((flags[j] & 2) == 2&&colorIndex==0) {			
				color_data[j] = 200 << 24 | 100 << 16 | 100 << 8 | 100;
				continue;
			}
			if (data[index] == -32767) {
				color_data[j] = 200 << 24 | 255 << 16 | 255 << 8 | 255;
				continue;
			}
			if(log_10_Flag){
				temp_data = (float) Math.log10(data[index]);
			}else{
				temp_data = data[index];
			}
			
			if (temp_data < minValue)
				temp_data = minValue;
			if (temp_data > maxValue)
				temp_data = maxValue;
			color_data[index] = (int) Math.ceil((temp_data - minValue)
					/ ((maxValue - minValue) / (color_tab[colorIndex].length -1)));
			if (color_data[index] < 0)
				color_data[index] = 0;
			if (color_data[index] > color_tab[colorIndex].length - 1)
				color_data[index] = color_tab[colorIndex].length - 1;
			// 分别把RGB三个值，合成一个值
			color_data[j] =200 << 24 | color_tab[colorIndex][color_data[j]][0] << 16
					| color_tab[colorIndex][color_data[j]][1] << 8
					| color_tab[colorIndex][color_data[j]][2];

		}
		flags = null;
		if (image == null) {
			image = new BufferedImage(rasterW, rasterH, BufferedImage.TYPE_INT_ARGB);
		}
		image.setRGB(0, 0, rasterW, rasterH, color_data, 0, rasterW);
		return image;
	}
	
		//读取txt文件里的颜色数据
		// try{
		// int index=0;
		// //BufferedReader color_tab_in =new BufferedReader(new
		// FileReader("C:/Users/JYK--YE/Workspaces/MyEclipse Professional 2014/MySeadas/color_tab2.txt"));
		// File file = new File("color_tab2.txt");
		// BufferedReader color_tab_in =new BufferedReader(new
		// FileReader(file));
		// while((color_line=color_tab_in.readLine())!=null){
		// String []str = color_line.split("\\s");
		// color_tab[index][0]=Integer.parseInt(str[0]);
		// color_tab[index][1]=Integer.parseInt(str[1]);
		// color_tab[index][2]=Integer.parseInt(str[2]);
		// ++index;
		// }
		// color_tab_in.close();
		// }catch(FileNotFoundException e){
		// e.printStackTrace();
		// }catch(IOException e){
		// e.printStackTrace();
		// }

	
	//世界地图
	public BufferedImage mergeImage(){
		String f_1 = System.getProperty("user.dir") + "\\3.jpeg";
		BufferedImage images ;
		try {
			images = ImageIO.read(new File(f_1));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}	
		f_1 = null;
		return images;
	}
	
	public int getRasterHeight(){
		return rasterH;
	}
	
	public int getRasterWidth(){
		return rasterW;
	}
	
	//取图片四个角的位置
	public List<float[]> getPoints(){
		float[] lon = new float[4];
		float[] lat = new float[4];
		
		lonBand = getBand("longitude");
		latBand = getBand("latitude");

		if(lonData==null | latData == null){
			rasterW=lonBand.getRasterWidth();
			rasterH=lonBand.getRasterHeight();
			lonData = new float[rasterW * rasterH];
			latData = new float[rasterW * rasterH];
		}
		try {
			latBand.readPixels(0, 0, rasterW, rasterH, latData);
			lonBand.readPixels(0, 0, rasterW, rasterH, lonData);
		} catch (IOException e) {
			e.printStackTrace();
		}
		

		lon[0] = lonData[0];
		lat[0] = latData[0];
		lon[1] = lonData[rasterW - 1];
		lat[1] = latData[rasterW - 1];
		lon[2] = lonData[rasterW * rasterH - 1];
		lat[2] = latData[rasterW * rasterH - 1];
		lon[3] = lonData[(rasterH - 1) * rasterW];
		lat[3] = latData[(rasterH - 1) * rasterW];

		List<float[]> list = new ArrayList<>();
		list.add(lat);
		list.add(lon);
		lon = null;
		lat = null;
	
		return list;
	}
	
	//横向合并两张图片
	public BufferedImage mergeImage(float[] lon, float[] lat) {
		int[][] LonR;
		int[][] LonL;
		int[][] LatR;
		int[][] LatL;
		String f_1 = System.getProperty("user.dir") + "\\0-0.jpeg";
		String f_2 = System.getProperty("user.dir") + "\\1-0.jpeg";
		// System.out.println(System.getProperty("user.dir")+"\\0-0.jpeg");
		// String[] files =
		// {"C:/Users/JYK--YE/Workspaces/MyEclipse Professional 2014/MySeadas/0-0.jpeg",
		// "C:/Users/JYK--YE/Workspaces/MyEclipse Professional 2014/MySeadas/1-0.jpeg"};
		String[] files = { f_1, f_2 };
		int len = files.length;
		if (len < 1) {
			throw new RuntimeException("图片数量小于1");
		}
		File[] src = new File[len];
		BufferedImage[] images = new BufferedImage[len];
		int[][] ImageArrays = new int[len][];
		for (int i = 0; i < len; i++) {
			try {
				src[i] = new File(files[i]);
				images[i] = ImageIO.read(src[i]);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			int width = images[i].getWidth();
			int height = images[i].getHeight();
			ImageArrays[i] = new int[width * height];
			ImageArrays[i] = images[i].getRGB(0, 0, width, height,
					ImageArrays[i], 0, width);
		}
		int newHeight = 0;
		int newWidth = 0;
		for (int i = 0; i < images.length; i++) {
			// 横向
			newHeight = newHeight > images[i].getHeight() ? newHeight
					: images[i].getHeight();
			newWidth += images[i].getWidth();
		}

		BufferedImage ImageNew;
		// 生成新图片
		try {
			ImageNew = new BufferedImage(newWidth, newHeight,
					BufferedImage.TYPE_INT_RGB);

			int width_i = 0;
			for (int i = 0; i < images.length; i++) {
				ImageNew.setRGB(width_i, 0, images[i].getWidth(), newHeight,
						ImageArrays[i], 0, images[i].getWidth());
				width_i += images[i].getWidth();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		int[] newImageArray = new int[newHeight * newWidth];
		newImageArray = ImageNew.getRGB(0, 0, newWidth, newHeight,
				newImageArray, 0, newWidth);

		List<int[][]> list = getLonLat(lon, lat);
		LonL = list.get(0);
		LatR = list.get(1);
		LatL = list.get(2);
		LonR = list.get(3);

		//根据图片边缘定位图片对应世界地图中的位置，用红色填充
		for (int k = 0; k < 2; k++) {
			for (int j = 0; j < LonL[k].length; j++) {
				newImageArray[(LatL[k][j] - 1) * 1350 + (LonL[k][j] - 1)] = 0xff0000;//红色
			}
		}
		for (int k = 0; k < 2; k++) {
			for (int j = 0; j < LonR[k].length; j++) {
				newImageArray[(LatR[k][j] - 1) * 1350 + (LonR[k][j] - 1)] = 0xff0000;//红色
			}
		}

		ImageNew.setRGB(0, 0, ImageNew.getWidth(), ImageNew.getHeight(),
				newImageArray, 0, ImageNew.getWidth());

		return ImageNew;
	}

	//获取图片边缘经纬度
	private List<int[][]> getLonLat(float[] lon, float[] lat) {
		int[][] LonR;
		int[][] LonL;
		int[][] LatR;
		int[][] LatL;
		float[][] LonRow = new float[2][rasterW];
		float[][] LatRow = new float[2][rasterW];
		float[][] LonLine = new float[2][rasterH];
		float[][] LatLine = new float[2][rasterH];
		LonR = new int[2][rasterW];
		LatR = new int[2][rasterW];
		LonL = new int[2][rasterH];
		LatL = new int[2][rasterH];

		int k=(rasterH-1)*rasterW;
		for (int i = 0; i < rasterW; i++) {
			LonRow[0][i] = lon[i];
			LatRow[0][i] = lat[i];
			LatRow[1][i] = lat[i + k];
			LonRow[1][i] = lon[i + k];
		}
        
		
		for (int i = 0; i < rasterH; i++) {
			LonLine[0][i] = lon[i * rasterW];
			LatLine[0][i] = lat[i * rasterW];
			LonLine[1][i] = lon[3199 + i * rasterW];
			LatLine[1][i] = lat[3199 + i * rasterW];
		}
		for (int j = 0; j < 2; j++) {
			for (int i = 0; i < LonLine[j].length; i++) {
				if (LonLine[j][i] > 180) {
					LonLine[j][i] -= 360;
				}
				if (LonLine[j][i] < -180) {
					LonLine[j][i] += 360;
				}
				LonL[j][i] = (int) Math.ceil((LonLine[j][i] + 179.5)
						/ (179.5 + 179.5) * 1350);
				if (LonL[j][i] < 1) {
					LonL[j][i] = 1;
				} else if (LonL[j][i] > 1350) {
					LonL[j][i] = 1350;
				}

				if (LatLine[j][i] < -90) {
					LatLine[j][i] += 180;
				}
				if (LatLine[j][i] > 90) {
					LatLine[j][i] -= 180;
				}
				LatL[j][i] = (int) Math.ceil((LatLine[j][i] - 89.5)
						/ (-89.5 - 89.5) * 675);
				if (LatL[j][i] < 1) {
					LatL[j][i] = 1;
				} else if (LatL[j][i] > 675) {
					LatL[j][i] = 675;
				}
			}
		}
		for (int j = 0; j < 2; j++) {
			for (int i = 0; i < LonRow[j].length; i++) {
				if (LonRow[j][i] < -180) {
					LonRow[j][i] += 360;
				}
				if (LonRow[j][i] > 180) {
					LonRow[j][i] -= 360;
				}
				LonR[j][i] = (int) Math.ceil((LonRow[j][i] + 179.5)
						/ (179.5 + 179.5) * 1350);
				if (LonR[j][i] < 1) {
					LonR[j][i] = 1;
				} else if (LonR[j][i] > 1350) {
					LonR[j][i] = 1350;
				}

				if (LatRow[j][i] > 90) {
					LatRow[j][i] -= 180;
				}
				if (LatRow[j][i] < -90) {
					LatRow[j][i] += 180;
				}
				LatR[j][i] = (int) Math.ceil((LatRow[j][i] - 89.5)
						/ (-89.5 - 89.5) * 675);
				if (LatR[j][i] < 1) {
					LatR[j][i] = 1;
				} else if (LatR[j][i] > 675) {
					LatR[j][i] = 675;
				}
			}
		}

		List<int[][]> list = new ArrayList<>();
		list.add(LonL);
		list.add(LatR);
		list.add(LatL);
		list.add(LonR);
		
		return list;
	}
	
}
