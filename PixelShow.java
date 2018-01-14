package Inter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;


public class PixelShow extends JPanel{
	private boolean DEBUG = false;
	private JTable table;
	public PixelShow(){
		init();
	}
	
	public void setRasterName(String rn){
		table.getModel().setValueAt(rn, 4, 0);
	}
	
	public void init(){
	
		this.setLayout(new BorderLayout());
	    table = new JTable(new MyTableModel());
	    table.setBackground(Color.WHITE);
	    table.setRowHeight(26);
	    //table.setPreferredScrollableViewportSize(new Dimension(200, 200));
	    table.setFillsViewportHeight(false);
	    //table.setCellEditor(null);
	    table.setRowSelectionAllowed(false);
	    table.setColumnSelectionAllowed(false);
	    initColumnSize(table);
	    //setUpValueColumn(table,table.getColumnModel().getColumn(2));
		this.add(table.getTableHeader(),BorderLayout.PAGE_START);
		this.add(table, BorderLayout.CENTER);

	    add(table);
        //setSize(200, 200);
        
	}
	
	public void insert(int imageX, int imageY, float lon, float lat, Object xx){
			if((float)xx == -32767){
				xx = "Invalid";
			}
			
		    table.getModel().setValueAt(imageX, 0, 1);
		    table.getModel().setValueAt(imageY, 1, 1);
		    table.getModel().setValueAt(lon, 2, 1);
		    table.getModel().setValueAt(lat, 3, 1);
		    table.getModel().setValueAt(xx, 4, 1);
	}
	
	public void insert(){
		table.getModel().setValueAt("Invalid", 0, 1);
		table.getModel().setValueAt("Invalid", 1, 1);
		table.getModel().setValueAt("Invalid", 2, 1);
		table.getModel().setValueAt("Invalid", 3, 1);
		table.getModel().setValueAt("Invalid", 4, 1);
	}
	
	   private void initColumnSize(JTable table){
	        //表格的每一列也是一个组件
	        TableColumn tc = null;
	        
	        for(int i = 0 ;i < table.getColumnCount();i++){
	            //注意:这里需要使用TableColumnModel来获取
	            //如果直接使用table.getColumn(identifier)会报错,
	            tc = table.getColumnModel().getColumn(i);
	            tc.setPreferredWidth(60);
	            
	        }
	    }
	   
	    public void setUpValueColumn(JTable table, TableColumn secondColumn) {
	        int numRows = table.getRowCount();
	        int numCols = table.getColumnCount();
	        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
	        TableModel model = table.getModel();

//	        System.out.println("Value of data: ");
	        for (int i = 0; i < numRows; i++) {
	            for (int j = 0; j < numCols; j++) {
	            	 renderer.setToolTipText(String.valueOf(model.getValueAt(i, j)));
	            	 secondColumn.setCellRenderer(renderer);
	            }
	        }
	        
	    }
	
	class MyTableModel extends AbstractTableModel{
		
	   private String[] columnNames = {"名称", "数值", "单位"};
	    //创建显示数据
	    private Object[][] data = {
	    	
            { "Image-X"  , "Invalid"   , "pixel"},
            { "Image-Y"  , "Invalid"   , "pixel"},
            { "Lontitude", "Invalid"  , "degree"},
            { "Latitude" , "Invalid"  , "degree"},
            { "Invalid" , "Invalid"  , "mg m^-3"}
           
	    };
	    public int getColumnCount() {
	        return columnNames.length;
	    }

	    public int getRowCount() {
	        return data.length;
	    }

	    public String getColumnName(int col) {
	        return columnNames[col];
	    }

	    public Object getValueAt(int row, int col) {
	        return data[row][col];
	    }
	    /*
         * JTable uses this method to determine the default renderer/ editor for
         * each cell. If we didn't implement this method, then the last column
         * would contain text ("true"/"false"), rather than a check box.
         */
        @SuppressWarnings({ "rawtypes", "unchecked" })
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        /*
         * Don't need to implement this method unless your table's editable.
         */
        public boolean isCellEditable(int row, int col) {
                return false;
        }
        /*
         * Don't need to implement this method unless your table's data can
         * change.
         */
        public void setValueAt(Object value, int row, int col) {
            if (DEBUG) {
                System.out.println("Setting value at " + row + "," + col
                        + " to " + value + " (an instance of "
                        + value.getClass() + ")");
            }

            data[row][col] = value;
            //System.out.println(data[row][col]); 
            fireTableCellUpdated(row, col);
            //fireTableDataChanged();
            
        }
	}

}
