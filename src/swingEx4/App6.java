package swingEx4;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;

public class App6 extends JFrame{
	
	public static void main(String[] args) {
	    EventQueue.invokeLater(new Runnable() {
	        public void run() {
	            try {
	                App6 window = new App6();
	                window.setVisible(true);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    });
	}


	/** 어플리케이션 생성*/
	public App6() {
		initialize();
	}

	/** 프레임에 콘텐츠 생성*/
	private void initialize() {
		setSize(820, 300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground(new Color(173, 216, 230));


	    JTabbedPane tabbedPane = new JTabbedPane();
	    tabbedPane.setBackground(new Color(173, 216, 230));
	    getContentPane().add(tabbedPane);

	    Object[][] tableData1 = prediction.getTable1();
	    String[] columnNames1 = {"", "WIN", "POST", "YEAR", "TEAM", "AVG", "G", "PA", "AB", "R", "H", "...", "PB", "SB_A", "CS_A", "CS%", "SBA", "SB", "CS", "SB%", "OOB", "PKO.1"};
	    DefaultTableModel tableModel1 = new DefaultTableModel(tableData1, columnNames1);
	    JTable table1 = new JTable(tableModel1);
	    JScrollPane scrollPane1 = new JScrollPane(table1);
	    scrollPane1.setBackground(new Color(173, 216, 230));
	    tabbedPane.addTab("csv 상위 5 칼럼", null, scrollPane1, null);

	    Object[][] tableData2 = prediction.getTable2();
	    String[] columnNames2 = {"", "WIN", "POST", "YEAR", "TEAM", "AVG", "G", "PA", "AB", "R", "H", "...", "PB", "SB_A", "CS_A", "CS%", "SBA", "SB", "CS", "SB%", "OOB", "PKO.1"};
	    DefaultTableModel tableModel2 = new DefaultTableModel(tableData2, columnNames2);
	    JTable table2 = new JTable(tableModel2);
	    JScrollPane scrollPane2 = new JScrollPane(table2);
	    scrollPane2.setBackground(new Color(173, 216, 230));
	    tabbedPane.addTab("각 성적의 평균값", null, scrollPane2, null);

	    Object[][] tableData3 = prediction.getTable3();
	    String[] columnNames3 = {"",	"AVG",	"OPS",	"RISP",	"PH-BA",	"ERA",	"WHIP",	"FPCT"};
	    DefaultTableModel tableModel3 = new DefaultTableModel(tableData3, columnNames3);
	    JTable table3 = new JTable(tableModel3);
	    JScrollPane scrollPane3 = new JScrollPane(table3);
	    scrollPane3.setBackground(new Color(173, 216, 230));
	    tabbedPane.addTab("데이터프레임 상위 5개 행", null, scrollPane3, null);

	    Object[][] tableData4 = prediction.getTable4();
	    String[] columnNames4 = {"팀 이름", "우승 확률", "예측 순위", "실제 순위"};
	    DefaultTableModel tableModel4 = new DefaultTableModel(tableData4, columnNames4);
	    JTable table4 = new JTable(tableModel4);
	    JScrollPane scrollPane4 = new JScrollPane(table4);
	    scrollPane4.setBackground(new Color(173, 216, 230));
	    tabbedPane.addTab("22시즌 순위 예측", null, scrollPane4, null);

	    Object[][] tableData5 = prediction.getTable5();
	    String[] columnNames5 = {"팀 이름", "우승 확률","예측 순위","실제 순위"};
	    DefaultTableModel tableModel5 = new DefaultTableModel(tableData5, columnNames5);
	    JTable table5 = new JTable(tableModel5);
	    JScrollPane scrollPane5 = new JScrollPane(table5);
	    scrollPane5.setBackground(new Color(173, 216, 230));
	    tabbedPane.addTab("23시즌 순위 예측", null, scrollPane5, null);

	    Object[][] tableData6 = prediction.getTable6();
	    String[] columnNames6 = {"순위", "변수명", "중요도"};
	    DefaultTableModel tableModel6 = new DefaultTableModel(tableData6, columnNames6);
	    JTable table6 = new JTable(tableModel6);
	    JScrollPane scrollPane6 = new JScrollPane(table6);
	    scrollPane6.setBackground(new Color(173, 216, 230));
	    tabbedPane.addTab("변수중요도", null, scrollPane6, null);

	    JPanel panel = new JPanel(new BorderLayout());
	    tabbedPane.setBounds(0, 0, 800, 600);
	    panel.add(tabbedPane);
	    panel.setBackground(new Color(173, 216, 230));
	    
	    // 버튼 배치를 위한 JPanel 생성
	    JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(173, 216, 230));
        
        JButton btn1 = new JButton("메인으로");
        btn1.setPreferredSize(new Dimension(97, 23));
        
        btn1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                App1 app1 = new App1();
                app1.setVisible(true);
            }
        });
        
        // buttonPanel에 버튼 배치
        buttonPanel.add(btn1);
        
        // main panel에 buttonPanel 배치
        panel.add(buttonPanel, BorderLayout.SOUTH);


	        
	    // panel에 ContentPane 추가
	    setContentPane(panel);
			}

	}

	
