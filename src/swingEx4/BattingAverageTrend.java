package swingEx4;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartTheme;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class BattingAverageTrend {
    
	public static void main(String[] args) {
	    
		ArrayList<hitterDAO> hitters2018 = new ArrayList<>();
		ArrayList<hitterDAO> hitters2019 = new ArrayList<>();
		ArrayList<hitterDAO> hitters2020 = new ArrayList<>();
		ArrayList<hitterDAO> hitters2021 = new ArrayList<>();
		ArrayList<hitterDAO> hitters2022 = new ArrayList<>();
	    
	    // 2018-2022 시즌 데이터 가져오기
	    try {
	        DatabaseManager dbManager = new DatabaseManager();
	        hitters2018.addAll(dbManager.getHitterList(2018));
	        hitters2019.addAll(dbManager.getHitterList(2019));
	        hitters2020.addAll(dbManager.getHitterList(2020));
	        hitters2021.addAll(dbManager.getHitterList(2021));
	        hitters2022.addAll(dbManager.getHitterList(2022));

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    // Get player name from console input
	    Scanner scanner = new Scanner(System.in);
	    System.out.print("선수 이름 입력: ");
	    String playerName = scanner.nextLine();
	    
	    // Create dataset for player
	    XYSeries series = new XYSeries(playerName);
	    double sum = 0;
	    for (int i = 0; i < hitters2018.size(); i++) {
	        double average = hitters2018.get(i).getAverage();
	        String name = hitters2018.get(i).getName();
	        if (name.equals(playerName)) {
	        	series.add(2018, average);
	        	sum += average;
	        	sum = sum/2;
	        	continue;
	        }
	    }
	    for (int i = 0; i < hitters2019.size(); i++) {
	        double average = hitters2019.get(i).getAverage();
	        String name = hitters2019.get(i).getName();
	        if (name.equals(playerName)) {
	        	series.add(2019, average);
	        	sum += average;
	        	sum = sum/2;
	        	continue;
	        }
	    }
	    for (int i = 0; i < hitters2020.size(); i++) {
	        double average = hitters2020.get(i).getAverage();
	        String name = hitters2020.get(i).getName();
	        if (name.equals(playerName)) {
	        	series.add(2020, average);
	        	sum += average;
	        	sum = sum/2;
	        	continue;
	        }
	    }
	    for (int i = 0; i < hitters2021.size(); i++) {
	        double average = hitters2021.get(i).getAverage();
	        String name = hitters2021.get(i).getName();
	        if (name.equals(playerName)) {
	        	series.add(2021, average);
	        	sum += average;
	        	sum = sum/2;
	        	continue;
	        }
	    }
	    for (int i = 0; i < hitters2022.size(); i++) {
	        double average = hitters2022.get(i).getAverage();
	        String name = hitters2022.get(i).getName();
	        if (name.equals(playerName)) {
	        	series.add(2022, average);
	        	sum += average;
	        	sum = sum/2;
	        	
	        	// 콘솔에 출력
	    	    if (sum < average) {
	    	        System.out.println(playerName + "'s batting average is on an upward trend.");
	    	    } else {
	    	        System.out.println(playerName + "'s batting average is on a downward trend.");
	    	    }
	    	    continue;
	        }
	    }
	    
	    
	    
	    // dataset 생성
	    XYDataset dataset = new XYSeriesCollection(series);
	    
	    // chart 생성
	    JFreeChart chart = ChartFactory.createXYLineChart(
	    		playerName + "의 타율 변화 트렌드", // chart title
	    "시즌", // x axis label
	    "타율", // y axis label
	    dataset, // data
	    PlotOrientation.VERTICAL,
	    true, // legend
	    true, // tooltips
	    false // urls
	    );
	    
	    // plot 설정
	    XYPlot plot = chart.getXYPlot();
	    plot.setBackgroundPaint(Color.WHITE);
	    plot.setRangeGridlinePaint(Color.BLACK);
	    plot.setDomainGridlinePaint(Color.BLACK);
	    plot.setDomainCrosshairVisible(true);
	    plot.setRangeCrosshairVisible(true);
	    plot.setDomainPannable(true);
	    plot.setRangePannable(true);
	    NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
	    rangeAxis.setTickUnit(new NumberTickUnit(0.01));
	    rangeAxis.setRange(0.05, 0.4);
	    NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
	    domainAxis.setTickUnit(new NumberTickUnit(1));
	    domainAxis.setRange(2017, 2023);

	    //	renderer 설정
	    XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
	    renderer.setSeriesPaint(0, Color.RED);
	    renderer.setSeriesStroke(0, new java.awt.BasicStroke(2.0f));
	    plot.setRenderer(renderer);

	    // PNG로 차트 저장
	    try {
	    ChartUtils.saveChartAsPNG(new File(playerName + "_average.png"), chart, 600, 400);
	    System.out.println("Chart saved as file: " + playerName + "_average.png");
	    } catch (IOException e) {
	    System.err.println("Error saving file: " + e.getMessage());
	    }

	    Font font = new Font("나눔고딕", Font.PLAIN, 12);
	    chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        chart.getTitle().setFont(new Font("나눔고딕", Font.BOLD, 16));
        chart.getLegend().setItemFont(font);
        chart.getXYPlot().getDomainAxis().setLabelFont(font.deriveFont(14f));
        chart.getXYPlot().getDomainAxis().setTickLabelFont(font.deriveFont(12f));
        chart.getXYPlot().getRangeAxis().setLabelFont(font.deriveFont(14f));
	    
	    // Display chart in JFrame
	    ChartFrame frame = new ChartFrame(playerName + "의 타율 변화 트렌드", chart);
	    frame.setPreferredSize(new Dimension(700, 500));
	    frame.pack();
	    frame.setVisible(true);
	    }
}