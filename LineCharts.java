package Inter;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.RefineryUtilities;
 
public class LineCharts{
	private XYSeries localXYSeries1;
	private XYSeriesCollection localXYSeriesCollection;
	private  ValueAxis  vy , vx;
    private XYDataset createDataset(){
      localXYSeries1 = new XYSeries("Rrs");
//      localXYSeries1.add(450.5D, 0.15D);
//      localXYSeries1.add(502.0D, 0.25D);
//      localXYSeries1.add(387.6D, 0.67D);
//      localXYSeries1.add(454.0D, 0.64D);
//      localXYSeries1.add(800.0D, 1.0D);
      XYSeriesCollection localXYSeriesCollection= new XYSeriesCollection();
     localXYSeriesCollection.addSeries(localXYSeries1);

      return localXYSeriesCollection;
  }
    
  private  JFreeChart createChart(XYDataset paramXYDataset){
    JFreeChart localJFreeChart = ChartFactory.createXYLineChart(null, "Wavelength(nm)", null, paramXYDataset, PlotOrientation.VERTICAL, true, true, false);
    localJFreeChart.setBackgroundPaint(Color.white);
    XYPlot localXYPlot = (XYPlot)localJFreeChart.getPlot();
    vx = localXYPlot.getDomainAxis();
   // va.setFixedAutoRange(850.0D);
    vx.setLowerBound(300.0D);
    vx.setUpperBound(900.0D);
   // vx.setVisible(true);
    vy = localXYPlot.getRangeAxis();
//    vy.setLowerBound(0.0D);
//    vy.setUpperBound(1.0D);
    localXYPlot.setBackgroundPaint(Color.lightGray);
    //localXYPlot.setAxisOffset(new RectangleInsets(5.0D, 5.0D, 5.0D, 5.0D));
    localXYPlot.setDomainGridlinePaint(Color.white);
    localXYPlot.setRangeGridlinePaint(Color.white);
    XYLineAndShapeRenderer localXYLineAndShapeRenderer = (XYLineAndShapeRenderer)localXYPlot.getRenderer();
    localXYLineAndShapeRenderer.setBaseShapesVisible(true);
    localXYLineAndShapeRenderer.setBaseShapesFilled(true);
    localXYLineAndShapeRenderer.setSeriesPaint(0, Color.blue);
    //NumberAxis localNumberAxis = (NumberAxis)localXYPlot.getRangeAxis();
    //localNumberAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
    //localNumberAxis.setAutoRange(true);
    //localNumberAxis.setAutoRangeMinimumSize(0);
    
    return localJFreeChart;
  }
   
  public void set(float[] b){
	    vy.setLowerBound(b[0]-0.0005);
	    vy.setUpperBound(b[1]+0.0005);
  }

  public void insert(float[] rasterName, float[][] rasterLength){
	  
	//float x=400+(Math.random()*200);
	//float y=(Math.random()*5);
	  localXYSeries1.clear();
	  for(int i=0; i< rasterName.length; i++){
		  localXYSeries1.addOrUpdate(rasterName[i], rasterLength[i][0]);
	  }

    //localXYSeries1.addOrUpdate(x, y);
    //localXYSeries1.addOrUpdate(x+100, y);
    //localXYSeries1.addOrUpdate(x+150, y);
    //localXYSeries1.addOrUpdate(x+200, y);
    //localXYSeries1.addOrUpdate(x+250, y);
  
    //localXYSeriesCollection.addSeries(localXYSeries1);
  }
  public void clear(){
	  localXYSeries1.clear();
  }
  

  public  JPanel createDemoPanel()
  {
    JFreeChart localJFreeChart = createChart(createDataset());
    return new ChartPanel(localJFreeChart);
  }
  															
}