package com.studio.tensor.ldm.controller;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Ellipse2D;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/draw")
public class DrawController
{
	@ResponseBody
	@RequestMapping("/pic")
	public void drawPic(Double[] data, Integer width, Integer height,
			HttpServletResponse response) throws IOException
	{
		ChartUtilities.writeChartAsPNG(response.getOutputStream(),
				createLineChart(), width, height, null);
	}
	
	public JFreeChart createLineChart() 
	{
		//创建分类数据集
		DefaultCategoryDataset localDefaultCategoryDataset = new DefaultCategoryDataset();
		localDefaultCategoryDataset.addValue(212.0D, "Classes", "JDK 1.0");
		localDefaultCategoryDataset.addValue(504.0D, "Classes", "JDK 1.1");
		localDefaultCategoryDataset.addValue(1520.0D, "Classes", "JDK 1.2");
		localDefaultCategoryDataset.addValue(1842.0D, "Classes", "JDK 1.3");
		localDefaultCategoryDataset.addValue(2991.0D, "Classes", "JDK 1.4");
		localDefaultCategoryDataset.addValue(3500.0D, "Classes", "JDK 1.5");
		
		//创建线状图
		JFreeChart localJFreeChart = ChartFactory.createLineChart("Java Standard Class Library", null, "Class Count", localDefaultCategoryDataset, PlotOrientation.VERTICAL, false, true, false);
	    localJFreeChart.addSubtitle(new TextTitle("Number of Classes By Release"));
	    
	    //设置文本标题
	    TextTitle localTextTitle = new TextTitle("Source: Java In A Nutshell (5th Edition) by David Flanagan (O'Reilly)");
	    localTextTitle.setFont(new Font("SansSerif", 0, 10));
	    localTextTitle.setPosition(RectangleEdge.BOTTOM);
	    localTextTitle.setHorizontalAlignment(HorizontalAlignment.RIGHT);
	    localJFreeChart.addSubtitle(localTextTitle);
	    
	    //分类描绘点
	    CategoryPlot localCategoryPlot = (CategoryPlot)localJFreeChart.getPlot();
	    localCategoryPlot.setRangePannable(true);
	    localCategoryPlot.setRangeGridlinesVisible(false);
	    //设置坐标轴
	    Object localObject = (NumberAxis)localCategoryPlot.getRangeAxis();
	    ((NumberAxis)localObject).setStandardTickUnits(NumberAxis.createIntegerTickUnits());
	    ChartUtilities.applyCurrentTheme(localJFreeChart);
	    
	    //得到图表渲染器
	    LineAndShapeRenderer localLineAndShapeRenderer = (LineAndShapeRenderer)localCategoryPlot.getRenderer();
	    //设置渲染器属性
	    localLineAndShapeRenderer.setBaseShapesVisible(true);
	    localLineAndShapeRenderer.setDrawOutlines(true);
	    localLineAndShapeRenderer.setUseFillPaint(true);
	    localLineAndShapeRenderer.setBaseFillPaint(Color.white);
	    localLineAndShapeRenderer.setSeriesStroke(0, new BasicStroke(3.0F));
	    localLineAndShapeRenderer.setSeriesOutlineStroke(0, new BasicStroke(2.0F));
	    localLineAndShapeRenderer.setSeriesShape(0, new Ellipse2D.Double(-5.0D, -5.0D, 10.0D, 10.0D));
	    
	    return localJFreeChart;
	}
}
