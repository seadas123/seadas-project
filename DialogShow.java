package Inter;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class DialogShow extends JDialog{

	public DialogShow(){
		init();
	}
	public void init(){
		try {
			setIconImage(ImageIO.read(this.getClass().getResource("/images/SOED.jpg")));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		setBounds(200, 200, 700, 360);
		getContentPane().setLayout(new FlowLayout());
		getContentPane().add(label);   getContentPane().add(textField);    getContentPane().add(jb);
		getContentPane().add(label_2); getContentPane().add(textField_2);  getContentPane().add(jb_2);
		getContentPane().add(label_3); getContentPane().add(textField_3);  getContentPane().add(jb_3);
		getContentPane().add(label_4); getContentPane().add(textField_4);  getContentPane().add(jb_4);		
		getContentPane().add(label_5); getContentPane().add(textField_5);  getContentPane().add(jb_5);
		getContentPane().add(label_6); getContentPane().add(textField_6);  getContentPane().add(jb_6);
		getContentPane().add(label_7); getContentPane().add(textField_7);  getContentPane().add(jb_7);
		getContentPane().add(label_8); getContentPane().add(textField_8);  getContentPane().add(jb_8);		
		jb_9.setFont(new Font("宋体", Font.BOLD, 16));
		jb_9.setForeground(Color.BLUE);
		getContentPane().add(jb_9);
		
		ButtonControl bc = new ButtonControl();
		jb.addActionListener(bc);		
		jb_2.addActionListener(bc);	
		jb_3.addActionListener(bc);	
		jb_4.addActionListener(bc);	
		jb_5.addActionListener(bc);		
		jb_6.addActionListener(bc);	
		jb_7.addActionListener(bc);	
		jb_8.addActionListener(bc);	
		jb_9.addActionListener(bc);		
		
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
	
	JLabel label = new JLabel("叶绿素浓度遥感产品路径：");
	JLabel label_2 = new JLabel("海表面温度遥感产品路径：");
	JLabel label_3 = new JLabel("<html>412nm有色有机物吸收系数<br>遥感产品路径：");
	JLabel label_4 = new JLabel("<html>443nm有色有机物吸收系数<br>遥感产品路径：");
	JLabel label_5 = new JLabel("海平面压力遥感产品路径：");
	JLabel label_6 = new JLabel("    大气CO2遥感产品路径：");
	JLabel label_7 = new JLabel("           风速遥感产品路径：");
	JLabel label_8 = new JLabel("          预处理结果存放路径：");

	final JTextField textField = new JTextField(30);
	JTextField textField_2 = new JTextField(30);
	JTextField textField_3 = new JTextField(30);
	JTextField textField_4 = new JTextField(30);
	JTextField textField_5 = new JTextField(30);
	JTextField textField_6 = new JTextField(30);
	final JTextField textField_7 = new JTextField(30);
	JTextField textField_8 = new JTextField(30);

	final JButton jb = new JButton("选择文件");
	JButton jb_2 = new JButton("选择文件");
	JButton jb_3 = new JButton("选择文件");
	JButton jb_4 = new JButton("选择文件");
	JButton jb_5 = new JButton("选择文件");
	JButton jb_6 = new JButton("选择文件");
	JButton jb_7 = new JButton("选择文件");
	JButton jb_8 = new JButton("选择文件夹");
	
	JButton jb_9 = new JButton("生成数据");
	
	
	
	public String getFilePath(){
		JFileChooser chooser=new JFileChooser();  
        chooser.setCurrentDirectory(new File("."));   
        int result=chooser.showOpenDialog(null);  
        String path=null;
        if(result==JFileChooser.APPROVE_OPTION){  
        	path=chooser.getSelectedFile().getPath();  
        }
        return path;
	}
	
	
	private class ButtonControl  implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JButton tempButton = (JButton)e.getSource();
			if(tempButton == jb){
//				textField.setText(getDirtory());
			}
			if(tempButton == jb_2){
//				textField_2.setText(getDirtory());
			}
			if(tempButton == jb_3){
//				textField_3.setText(getDirtory());
			}
			if(tempButton == jb_4){
//				textField_4.setText(getDirtory());
			}
			if(tempButton == jb_5){
//				textField_5.setText(getDirtory());
			}
			if(tempButton == jb_6){
//				textField_6.setText(getDirtory());
			}
			if(tempButton == jb_7){
				textField_7.setText(getFilePath());
			}
			if(tempButton == jb_8){
				
			}
			if(tempButton == jb_9){
				System.out.println("-------9");
			}
		}
	}
}
