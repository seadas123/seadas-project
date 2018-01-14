package Inter;

import gov.nasa.gsfc.seadas.dataio.SeadasProductReader.ProductType;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;
import javax.swing.JToolBar;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.AbstractAction;
import javax.swing.JTree;
import javax.swing.SwingConstants;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;


public class MainView {
	private JFrame frame=null;
	private JInternalFrame fileManager = null;
	private JInternalFrame worldMap = null;
	private JInternalFrame informationShow = null;
	private JInternalFrame navigationControl = null;
	private JInternalFrame spectumView = null;
	private JTabbedPane tabbedPaneCenter = null;
	private JButton[] button = new JButton[10];
	private BigPanel bigPanel = null;
	private AnotherSmallImage asi = new AnotherSmallImage();
	private TreeView treeview = new TreeView();
	private DataUtils du = new DataUtils();
	private Color backGroundColor = null;
	private FileNameExtensionFilter filter = null;
	private JScrollPane jsp = null;
	private JFileChooser chooser=null;
	private WorldMapPanel wmp=null;
	private List<String> pathList = new ArrayList<>();
	private String path = null;
	private boolean isAdd = false;
	private boolean isSameFile = false;
	private DefaultMutableTreeNode currentNode;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainView window = new MainView();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainView() {
		initialize();
		String path = "/images/SOED.jpg";
		try {
			Image img = ImageIO.read(this.getClass().getResource(path));
			frame.setIconImage(img);
			frame.setTitle("SOED-VIIRSң�����ݴ���ϵͳ1.0--Beta��");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1000, 700);
		frame.setMinimumSize(new Dimension(1000, 700));
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// �˵���
		JMenuBar menuBar = new JMenuBar();
		menuBar.setToolTipText("\u7F16\u8F91");
		menuBar.setPreferredSize(new Dimension(1000, 20));
		frame.setJMenuBar(menuBar);

		JMenu mnOpen = new JMenu("�ļ�");
		menuBar.add(mnOpen);
		mnOpen.setPreferredSize(new Dimension(45, 20));

		JMenuItem open = new JMenuItem("��");
		mnOpen.add(open);

		JMenu menu_1 = new JMenu("\u5904\u7406");
		menuBar.add(menu_1);
		menu_1.setPreferredSize(new Dimension(45, 20));

		JMenuItem menuItem = new JMenuItem("��������");
		menu_1.add(menuItem);

		JMenu menu_2 = new JMenu("����");
		menuBar.add(menu_2);
		menu_2.setPreferredSize(new Dimension(45, 20));

		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		// ������
		JToolBar topToolBar = new JToolBar();
		topToolBar.setPreferredSize(new Dimension(1000, 30));
		frame.getContentPane().add(topToolBar, BorderLayout.NORTH);

		button[0] = new JButton();
		backGroundColor = button[0].getBackground();
		topToolBar.add(button[0]);
		button[0].setIcon(new ImageIcon(MainView.class
				.getResource("/images/Open24.png")));
		button[0].setText("");
		button[0].setBorderPainted(false);

		button[1] = new JButton();
		topToolBar.add(button[1]);
		button[1].setIcon(new ImageIcon(MainView.class
				.getResource("/images/SelectTool24.gif")));
		button[1].setText("");
		button[1].setBorderPainted(false);

		button[2] = new JButton();
		topToolBar.add(button[2]);
		button[2].setIcon(new ImageIcon(MainView.class
				.getResource("/images/PannerTool24.gif")));
		button[2].setText("");
		button[2].setBorderPainted(false);

		button[3] = new JButton();
		topToolBar.add(button[3]);
		button[3].setIcon(new ImageIcon(MainView.class
				.getResource("/images/ZoomTo24.gif")));
		button[3].setText("");
		button[3].setBorderPainted(false);

		button[4] = new JButton();
		topToolBar.add(button[4]);
		button[4].setIcon(new ImageIcon(MainView.class
				.getResource("/images/ZoomAll24.gif")));
		button[4].setText("");
		button[4].setBorderPainted(false);

		button[5] = new JButton();
		topToolBar.add(button[5]);
		button[5].setIcon(new ImageIcon(MainView.class
				.getResource("/images/ZoomPixel24.gif")));
		button[5].setText("");
		button[5].setBorderPainted(false);

		button[6] = new JButton();
		topToolBar.add(button[6]);
		button[6].setIcon(new ImageIcon(MainView.class
				.getResource("/images/bathymetry.png")));
		button[6].setText("");
		button[6].setEnabled(false);
		button[6].setBorderPainted(false);
		
		button[7] = new JButton();
		topToolBar.add(button[7]);
		button[7].setIcon(new ImageIcon(MainView.class
				.getResource("/images/coastline_24.png")));
		button[7].setText("");
		button[7].setEnabled(false);
		button[7].setBorderPainted(false);
		
		button[8] = new JButton();
		topToolBar.add(button[8]);
		button[8].setIcon(new ImageIcon(MainView.class
				.getResource("/images/ContourOverlay22.png")));
		button[8].setText("");
		button[8].setEnabled(false);
		button[8].setBorderPainted(false);
		
		button[9] = new JButton();
		topToolBar.add(button[9]);
		button[9].setIcon(new ImageIcon(MainView.class
				.getResource("/images/dannys_spatial_subset24.png")));
		button[9].setText("");
		button[9].setEnabled(false);
		button[9].setBorderPainted(false);

		// �ײ����
		JPanel panelSouth = new JPanel();
		panelSouth.setBorder(null);
		panelSouth.setBackground(Color.GRAY);
		panelSouth.setPreferredSize(new Dimension(1000, 20));
		frame.getContentPane().add(panelSouth, BorderLayout.SOUTH);
		// �ұ����
		final JPanel desktopPaneEast = new JPanel();
		desktopPaneEast.setBorder(null);
		desktopPaneEast.setPreferredSize(new Dimension(300, 630));
		desktopPaneEast.setBackground(Color.LIGHT_GRAY);
		frame.getContentPane().add(desktopPaneEast, BorderLayout.EAST);

		// �м����
		tabbedPaneCenter = new JTabbedPane(SwingConstants.TOP);
		tabbedPaneCenter.setBorder(new EmptyBorder(0, 0, 0, 0));
		tabbedPaneCenter.setBackground(Color.WHITE);
		frame.getContentPane().add(tabbedPaneCenter, BorderLayout.CENTER);

		// ������
		JPanel panelWest = new JPanel();
		panelWest.setBorder(null);
		panelWest.setPreferredSize(new Dimension(220, 630));
		panelWest.setBackground(Color.GRAY);
		frame.getContentPane().add(panelWest, BorderLayout.WEST);

		spectumView = new JInternalFrame(
				"\u5149\u8C31\u56FE");
		spectumView.setEnabled(false);
		spectumView.setPreferredSize(new Dimension(300, 180));
		spectumView.setBorder(null);
		spectumView.setVisible(true);
		spectumView.getContentPane().setLayout(new BorderLayout(0, 0));

		informationShow = new JInternalFrame(
				"\u5C5E\u6027\u4FE1\u606F");
		informationShow.setEnabled(false);
		informationShow.setPreferredSize(new Dimension(300, 110));
		informationShow.setBorder(null);
		informationShow.setVisible(true);
		informationShow.getContentPane().setLayout(new BorderLayout(0, 0));

		navigationControl = new JInternalFrame(
				"\u5BFC\u822A\u7A97\u53E3");
		navigationControl.setEnabled(false);
		navigationControl.setPreferredSize(new Dimension(300, 230));
		navigationControl.setBorder(new EmptyBorder(0, 0, 0, 0));
		navigationControl.setVisible(true);
		navigationControl.getContentPane().setLayout(new BorderLayout(0, 0));
		desktopPaneEast.setLayout(new BorderLayout(0, 0));
		desktopPaneEast.add(spectumView, BorderLayout.NORTH);
		desktopPaneEast.add(informationShow);
		desktopPaneEast.add(navigationControl, BorderLayout.SOUTH);
		panelWest.setPreferredSize(new Dimension(220, 210));
		panelWest.setLayout(new BorderLayout(0, 0));

		fileManager = new JInternalFrame(
				"\u6570\u636E\u5217\u8868");
		fileManager.setBorder(BorderFactory.createEmptyBorder());
		panelWest.add(fileManager);
		fileManager.setVisible(true);

		worldMap = new JInternalFrame("�����ͼ");
		worldMap.setBorder(BorderFactory.createEmptyBorder());
		panelWest.add(worldMap, BorderLayout.SOUTH);
		worldMap.setPreferredSize(new Dimension(220, 210));
		WorldMapPanel wm = new WorldMapPanel();
		worldMap.getContentPane().add(wm);
		worldMap.setVisible(true);
		wm = null;
		 
		int fw = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width - 520;
		int fh = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height - 100 - 40 - 27;// 40��windows�������߶�
		bigPanel = new BigPanel(fw, fh);
		
		MenuListener ml = new MenuListener();
		open.addActionListener(ml);
		button[0].addActionListener(ml);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new DialogShow();
			}
		});

		ToolbarListener tl = new ToolbarListener();
		for (int i = 0; i < button.length; i++) {
			button[i].addActionListener(tl);
		}
	}
	//�˵������¼�
	public class MenuListener implements ActionListener{
		
		public MenuListener(){
			
		}
	 
	    public void actionPerformed(ActionEvent e) {   
	    	String str = (e.getActionCommand().trim());
	    	if("��".equals(str)){
	    		if(chooser==null){
	    			chooser=new JFileChooser();  
	    		}
	    	 	if(filter==null){
	    	 		filter=new FileNameExtensionFilter("Files", "nc");      	 		
	    	 	}
	            chooser.setFileFilter(filter  );  
	            chooser.setCurrentDirectory(new File("."));  
	            int result=chooser.showOpenDialog(null);  
	            if(result==JFileChooser.APPROVE_OPTION){  
	            	path=chooser.getSelectedFile().getPath();  
	            	if(pathList.size()>0){
	            		for(String s : pathList){
	            			if(!s.equalsIgnoreCase(path)){
	            				isAdd = true;
	            			}else{
	            				System.out.println("same path is invalid");
	            				return;
	            			}
	            		}
	            	}else{
	            		pathList.add(path);
	            	}
	            	if(isAdd){
	            		pathList.add(path);
	            	}
	            	du.setNewPath(path);
	            	
	            	treeview.AddtoTree(path, du.getBandNames(), du.getProductType());     
//	            	fileManager.getContentPane().removeAll();
	        	
	            	if(jsp==null){
	            		jsp = new JScrollPane(treeview.getTree());//Ӧ��ִֻ��һ��
	            		fileManager.getContentPane().add(jsp);         
	            	}        	
//	            	fileManager.updateUI();
//	            	jsp = null;
	            	
	            	if(worldMap.getContentPane().getComponentCount()>=1){
	        			worldMap.getContentPane().removeAll();			
	        		}
	        		wmp = new WorldMapPanel(du.getPoints());
	        		worldMap.getContentPane().add(wmp);
	        		worldMap.updateUI();
	        		bigPanel.setLonLat(du.getLon(), du.getLat());
	        		bigPanel.setRasterWH(du.getRasterWidth(), du.getRasterHeight());
	        		
	            }
	           
	        }  
	    }
	}

	//�����������¼�
	private class ToolbarListener extends AbstractAction {
		public void actionPerformed(ActionEvent e) {
			JButton tempButton = (JButton) e.getSource();
			//�ļ���
			if (tempButton == button[0]) {
				if(chooser==null){
	    			chooser=new JFileChooser();  
	    		}
	    	 	if(filter==null){
	    	 		filter=new FileNameExtensionFilter("Files", "nc");      	 		
	    	 	}
	            chooser.setFileFilter(filter);  
	            chooser.setCurrentDirectory(new File("."));   
	            int result=chooser.showOpenDialog(null);  
	            if(result==JFileChooser.APPROVE_OPTION){  
	            	path=chooser.getSelectedFile().getPath();  
	            	if(pathList.size()>0){
	            		for(String s : pathList){
	            			if(!s.equalsIgnoreCase(path)){
	            				isAdd = true;
	            			}else{
	            				System.out.println("same path is invalid");
	            				return;
	            			}
	            		}
	            	}else{
	            		pathList.add(path);
	            	}
	            	if(isAdd){
	            		pathList.add(path);
	            		isAdd = false;
	            	}
	           
	            	du.setNewPath(path);
	            	
	            	treeview.AddtoTree(path, du.getBandNames(),du.getProductType());           	
//	            	fileManager.getContentPane().removeAll();
	            	if(jsp==null){
	            		jsp = new JScrollPane(treeview.getTree());//Ӧ��ִֻ��һ��
	            		fileManager.getContentPane().add(jsp);         
	            	}
//	            	
	            	fileManager.updateUI();
//	            	jsp = null;
	            	
	            	if(worldMap.getContentPane().getComponentCount()>=1){
	        			worldMap.getContentPane().removeAll();	
	        		}
	        		wmp = new WorldMapPanel(du.getPoints());	
	        		bigPanel.setLonLat(du.getLon(), du.getLat());
	        		bigPanel.setRasterWH(du.getRasterWidth(), du.getRasterHeight());
	        		worldMap.getContentPane().add(wmp);
	        		worldMap.updateUI();
	        		
	            }
			}
			//����ѡȡ
			if (tempButton == button[1]) {
				bigPanel.setMouseFlags(1);
				
				for(int i=0;i<button.length;i++){
					if(i==1){
						button[i].setBackground(Color.WHITE);
					}else{
						button[i].setBackground(backGroundColor);
					}
				}
			}
			//ͼ���϶�
			if (tempButton == button[2]) {
				bigPanel.setMouseFlags(2);
				for(int i=0;i<button.length;i++){
					if(i==2){
						button[i].setBackground(Color.WHITE);
					}else{
						button[i].setBackground(backGroundColor);
					}
				}
			}
			//ѡ��������ʾ
			if (tempButton == button[3]) {
				bigPanel.setMouseFlags(3);
				for(int i=0;i<button.length;i++){
					if(i==3){
						button[i].setBackground(Color.WHITE);
					}else{
						button[i].setBackground(backGroundColor);
					}
				}
			}
			//�Զ�����
			if (tempButton == button[4]) {
				bigPanel.setAutoAdaption();
				for(int i=0;i<button.length;i++){
					if(i==4){
						button[i].setBackground(Color.WHITE);
					}else{
						button[i].setBackground(backGroundColor);
					}
				}
			}
			//�ָ�ͼ��ԭʼ��С
			if (tempButton == button[5]) {
				bigPanel.setMaximize();
				for(int i=0;i<button.length;i++){
					if(i==5){
						button[i].setBackground(Color.WHITE);
					}else{
						button[i].setBackground(backGroundColor);
					}
				}
			}
			if (tempButton == button[6]) {

			}
			if (tempButton == button[7]) {

			}
			if (tempButton == button[8]) {

			}
			if (tempButton == button[9]) {
				
			}
	
		}
	}
	
	//��������Ŀ¼��jtree��
	 class TreeView {
		private JPanel bigp = null;
		private BufferedImage bot_img = null;
		private float[] data = null;
		private int t = 0, n = 0;
		private PixelShow ps = null;
		private LineCharts lc = null;
		private JPanel panel = null;
		private JPopupMenu jpm = new JPopupMenu();
		// �����˵�������
		private JMenuItem[] jmi = { new JMenuItem("չ��"),
				new JMenuItem("�۵�"), new JMenuItem("�ر�") };
		private DefaultMutableTreeNode selNode;
		private JTree tree;
		//���弸����ʼ�ڵ�����  
		private DefaultMutableTreeNode root = new DefaultMutableTreeNode();
		private DefaultMutableTreeNode[] node0 = new DefaultMutableTreeNode[6];
		private DefaultMutableTreeNode[] aot = new DefaultMutableTreeNode[6];
		private DefaultMutableTreeNode[] Rrs = new DefaultMutableTreeNode[6];
		private DefaultMutableTreeNode[] Kd = new DefaultMutableTreeNode[6];
		private DefaultMutableTreeNode[] node = new DefaultMutableTreeNode[3];
		
		public TreeView() {
			node[0]=new DefaultMutableTreeNode("L1B");
			node[1]=new DefaultMutableTreeNode("L2_NASA");
			node[2]=new DefaultMutableTreeNode("L2_SOED");
			for(int i=0;i<3;i++)
				root.add(node[i]);
			
			tree = new JTree(root);
			// tree.setShowsRootHandles(false);
			tree.setRootVisible(false);
			tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
			tree.addMouseListener(new TreeListener());				

		}

		public JTree getTree(){
			return tree;
		}
		//���ļ����ݰ���Ŀ¼��ʾ����
		public void AddtoTree(String path, String[] band_names, ProductType productType) {
			node0[t] = new DefaultMutableTreeNode(new File(path).getName());
			aot[t] = new DefaultMutableTreeNode("aot");
			Rrs[t] = new DefaultMutableTreeNode("Rrs");
			Kd[t] = new DefaultMutableTreeNode("Kd");

			for (int j = 0; j < band_names.length; j++) {
				if (band_names[j].substring(0, 3).equals("aot")) {
					aot[t].add(new DefaultMutableTreeNode(band_names[j]));
					node0[t].add(aot[t]);
				} else if (band_names[j].substring(0, 3).equals("Rrs")) {
					Rrs[t].add(new DefaultMutableTreeNode(band_names[j]));
					node0[t].add(Rrs[t]);
				} else if (band_names[j].substring(0, 2).equals("Kd")) {
					Kd[t].add(new DefaultMutableTreeNode(band_names[j]));
					node0[t].add(Kd[t]);
				} else
					node0[t].add(new DefaultMutableTreeNode(band_names[j]));

			}
			//�����ļ�������ӵ���Ӧ�б�
			if(productType==ProductType.Level2){
				node[2].add(node0[t]);
			}
//			node[2].add(node0[t]);
			t++;
			tree.updateUI(); 
			
			MyPopMenuActionListener ma = new MyPopMenuActionListener();
			for (int i = 0; i < jmi.length; i++) {
				// ���˵�����ӵ�����ʽ�˵���
				jpm.add(jmi[i]);
				jmi[i].addActionListener(ma);
			}	
		
		}
		
		//�ڲ��࣬��������ڵ��¼�
		 class TreeListener extends MouseAdapter {
			 
			public void mouseClicked(MouseEvent me){
				JTree tree_1 = (JTree) me.getSource();
				n = tree_1.getRowForLocation(me.getX(), me.getY());
				if (n < 0)   return;
				TreePath selTree = tree_1.getPathForRow(n);
				currentNode = (DefaultMutableTreeNode) selTree.getLastPathComponent();	
				//�һ�Ŀ¼��ʾ�˵�
				if(me.isMetaDown()&&currentNode.getParent().getParent()==root){
					jpm.show(me.getComponent(), me.getX(), me.getY());
				}
			}
			
			public void mousePressed(MouseEvent me) {
				JTree tree = (JTree) me.getSource();
				int n = tree.getRowForLocation(me.getX(), me.getY());
				if (n < 0)   return;
				TreePath selTree = tree.getPathForRow(n);
				selNode = (DefaultMutableTreeNode) selTree.getLastPathComponent();			
				String preName=null;
				if (selNode.isLeaf() && me.getClickCount() == 2) {
					if(preName==selNode.toString()&&isSameFile){
						return;
					}
					
					preName=selNode.toString();
					if(selNode.getLevel()==4&&path.indexOf(selNode.getParent().getParent().toString()) == -1){
						isSameFile = false;
//						System.out.println("�Ҳ�������ļ���, �ҵĸ��ڵ㣺 "+selNode.getParent().toString());
						du.close();
						for(String s : pathList){
							if(s.indexOf(selNode.getParent().getParent().toString()) != -1){
								path = s;
								
								du.setNewPath(path);
								if(worldMap.getContentPane().getComponentCount()>=1){
				        			worldMap.getContentPane().removeAll();			
				        		}
				        		wmp = new WorldMapPanel(du.getPoints());
				        		worldMap.getContentPane().add(wmp);
				        		worldMap.updateUI();
				        		bigPanel.setLonLat(du.getLon(), du.getLat());
				        		bigPanel.setRasterWH(du.getRasterWidth(), du.getRasterHeight());
							}
						}
					}else{
						isSameFile = true;
					}
					if(selNode.getLevel()==3&&path.indexOf(selNode.getParent().toString()) == -1){
						isSameFile = false;
//						System.out.println("�Ҳ�������ļ���, �ҵĸ��ڵ㣺 "+selNode.getParent().toString());
						du.close();
						for(String s : pathList){
							if(s.indexOf(selNode.getParent().toString()) != -1){
								path = s;
								
								du.setNewPath(path);
								if(worldMap.getContentPane().getComponentCount()>=1){
				        			worldMap.getContentPane().removeAll();			
				        		}
				        		wmp = new WorldMapPanel(du.getPoints());
				        		worldMap.getContentPane().add(wmp);
				        		worldMap.updateUI();
				        		bigPanel.setLonLat(du.getLon(), du.getLat());
				        		bigPanel.setRasterWH(du.getRasterWidth(), du.getRasterHeight());
							}
						}
					
					}else{
						isSameFile = true;
					}
					if(ps==null){
						ps = new PixelShow();
						informationShow.getContentPane().removeAll();
						informationShow.getContentPane().add("Center", new JScrollPane(ps));
					}		
					ps.setRasterName(selNode.toString());
					if(lc==null){
						lc = new LineCharts();					
						spectumView.add(lc.createDemoPanel());
					}
					du.release();
					data = du.getData(selNode.toString());
					bot_img = du.generateBot(selNode.toString());
					if (tabbedPaneCenter.getTabCount() == 1) {
						tabbedPaneCenter.remove(0);
					}
					navigationControl.getContentPane().removeAll();
					panel = asi.init(bot_img, du);
					navigationControl.getContentPane().add(panel);
					navigationControl.updateUI();
			
					bigPanel.setInit(asi, du, data, lc, ps);
					bigp = bigPanel.init(bot_img);
					tabbedPaneCenter.addTab(selNode.toString(), bigp);
					tabbedPaneCenter.setTabComponentAt(tabbedPaneCenter.getTabCount() - 1,
							new ButtonTabComponent(tabbedPaneCenter,  navigationControl));

				}

			}
		}
		 
		 //�ڲ��࣬��������ڵ���һ���ʾ�˵����ܵ��¼�
		class MyPopMenuActionListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				//���ڵ�ر�
				if(e.getActionCommand()=="�ر�"){
					
					if(currentNode.getParent()==node[2]&&path.indexOf(currentNode.toString())!=-1){
						((DefaultMutableTreeNode) currentNode.getParent()).remove(currentNode);
						pathList.remove(path);
						du.close();
						tabbedPaneCenter.removeAll();
						navigationControl.removeAll();
					}
					if(currentNode.getParent()==node[2]&&path.indexOf(currentNode.toString())==-1){
						String temp = null;
						for(String s : pathList){
							if(s.indexOf(currentNode.toString()) != -1){
								temp = s;
								break;
							}
						}
						pathList.remove(temp);
						((DefaultMutableTreeNode) currentNode.getParent()).remove(currentNode);
						temp = null;
					}

					tree.updateUI();
				
				}
				//���ڵ�չ��
				if(e.getActionCommand()=="չ��"&&!selNode.isLeaf()){
					tree.expandRow(n);
				}
				//���ڵ��۵�
				if(e.getActionCommand()=="�۵�"&&!selNode.isLeaf()){
					tree.collapseRow(n);
				}
			}
		}

	}



}
