package swingEx4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import javax.swing.table.*;

public class App3 extends JFrame {
    private JComboBox<Integer> yearComboBox;
    private pitcherTableModel pitcherModel;
    private defenseTableModel defenseModel;
    private runnerTableModel runnerModel;
    private JTabbedPane tabbedPane;
    private JTable hitterTable;
    private JTable pitcherTable;
    private JTable defenseTable;
    private JTable runnerTable;
    private DatabaseManager dbManager;
    
    /** 어플리케이션 실행*/
    public static void main(String[] args) {
    	EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App3 window = new App3();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
    }
    
    /** 어플리케이션 생성 */
    public App3() throws SQLException {
		initialize();
	}

    /** 프레임에 콘텐츠 생성*/
    private void initialize() throws SQLException{

        setTitle("선수 기록 조회");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // JComboBox를 만들고 연도를 추가
        yearComboBox = new JComboBox<Integer>();
        yearComboBox.addItem(2022);
        yearComboBox.addItem(2021);
        yearComboBox.addItem(2020);
        yearComboBox.addItem(2019);
        yearComboBox.addItem(2018);
        
	    JButton btn1 = new JButton("메인으로");
	    btn1.setPreferredSize(new Dimension(97, 23));	// 버튼 크기 설정
	    btn1.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            btn1Clicked();
	        }
	    });
        
	    
        // TableModel 생성
        int year = 2022;
        DatabaseManager dbManager = DatabaseManager.getInstance();
        
        ArrayList<hitterDAO> hitterTypeList = dbManager.getHitterTypeList(year);	//	타자 유형 데이터를 검색하고 좌투수 데이터에 대한 목록을 필터링
	    ArrayList<hitterDAO> leftHandedPitcherData = hitterTypeList.stream()
	        .filter(h -> "좌투수".equalsIgnoreCase(h.getPitcherType()))
	        .collect(Collectors.toCollection(ArrayList::new));
	    
	    
        hitterTableModel hitterModel = new hitterTableModel(year, leftHandedPitcherData);	//	leftHandedPitcherData 목록을 hitterTableModel 생성자에 전달
        pitcherModel = new pitcherTableModel(year);
        defenseModel = new defenseTableModel(year);
        runnerModel = new runnerTableModel(year);

        // 메인 창의 레이아웃 설정
        Container contentPane = getContentPane();
		getContentPane().setBackground(new Color(173, 216, 230));
        contentPane.setLayout(new BorderLayout());
	
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(173, 216, 230));
        topPanel.add(new JLabel("시즌 선택:"));
        topPanel.add(yearComboBox);
        contentPane.add(topPanel, BorderLayout.NORTH);
        
        JPanel southPanel = new JPanel();
        southPanel.setBackground(new Color(173, 216, 230));
	    southPanel.add(btn1);
	    contentPane.add(southPanel, BorderLayout.SOUTH);

        tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(new Color(173, 216, 230));

        hitterTable = new JTable(hitterModel);
        pitcherTable = new JTable(pitcherModel);
        defenseTable = new JTable(defenseModel);
        runnerTable = new JTable(runnerModel);

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (table == hitterTable) {
                    hitterDAO hitter = ((hitterTableModel) hitterTable.getModel()).getRowAt(row);
                    if (hitter.getAb() >= 50 && hitter.getAverage() >= 0.275) {
                        c.setBackground(Color.YELLOW);
                    } else {
                        c.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
                    }
                } else if (table == runnerTable) {
                    runnerDAO runner = ((runnerTableModel) runnerTable.getModel()).getRowAt(row);
                    if (runner.getSba() >= 10 && runner.getSbPer() >= 70) {
                        c.setBackground(Color.YELLOW);
                    } else {
                        c.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
                    }
                } else {
                    c.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
                }

                return c;
            }
        };

        
        hitterTable.setDefaultRenderer(Object.class, renderer);
        runnerTable.setDefaultRenderer(Object.class, renderer);
        
        tabbedPane.addTab("타자 기록", new JScrollPane(hitterTable));
        tabbedPane.addTab("투수 기록", new JScrollPane(pitcherTable));
        tabbedPane.addTab("수비 기록", new JScrollPane(defenseTable));
        tabbedPane.addTab("주루 기록", new JScrollPane(runnerTable));
        contentPane.add(tabbedPane, BorderLayout.CENTER);
        
        tabbedPane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if (tabbedPane.getSelectedIndex() != 0) { // 선택한 탭이 hitterTable이 아닌 경우
                	resetTableBackground(hitterTable);
                    resetTableBackground(runnerTable);
                }
            }
        });

        // yearComboBox에 리스너를 추가, 테이블의 데이터를 업데이트
        yearComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
					updateTable();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
            }
        });
        
        // 각각의 테이블의 항목을 클릭하는 리스너 추가
        hitterTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                hitterTableModel model = (hitterTableModel) hitterTable.getModel();
                if (model == null) {
                    return;
                }
                if (SwingUtilities.isLeftMouseButton(e)) {
                    int column = hitterTable.columnAtPoint(e.getPoint());
                    if (column >= 0 && column < model.getColumnCount()) {
                        model.sortData(column);
                    }
                }
            }
        });
        pitcherTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                pitcherTableModel model = (pitcherTableModel) pitcherTable.getModel();
                if (model == null) {
                    return;
                }
                if (SwingUtilities.isLeftMouseButton(e)) {
                    int column = pitcherTable.columnAtPoint(e.getPoint());
                    if (column >= 0 && column < model.getColumnCount()) {
                        model.sortData(column);
                    }
                }
            }
        });
        defenseTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                defenseTableModel model = (defenseTableModel) defenseTable.getModel();
                if (model == null) {
                    return;
                }
                if (SwingUtilities.isLeftMouseButton(e)) {
                    int column = defenseTable.columnAtPoint(e.getPoint());
                    if (column >= 0 && column < model.getColumnCount()) {
                        model.sortData(column);
                    }
                }
            }
        });
        runnerTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                runnerTableModel model = (runnerTableModel) runnerTable.getModel();
                if (model == null) {
                    return;
                }
                if (SwingUtilities.isLeftMouseButton(e)) {
                    int column = runnerTable.columnAtPoint(e.getPoint());
                    if (column >= 0 && column < model.getColumnCount()) {
                        model.sortData(column);
                    }
                }
            }
        });
        
        //	칼럼 이름에 마우스 올리면 도움말
        JTableHeader header1 = hitterTable.getTableHeader();
        JTableHeader header2 = hitterTable.getTableHeader();
        JTableHeader header3 = hitterTable.getTableHeader();
        JTableHeader header4 = hitterTable.getTableHeader();
        header1.setToolTipText("정렬하려는 표의 항목을 누르세요.");
        header2.setToolTipText("정렬하려는 표의 항목을 누르세요.");
        header3.setToolTipText("정렬하려는 표의 항목을 누르세요.");
        header4.setToolTipText("정렬하려는 표의 항목을 누르세요.");
        
        // 창 표시
        pack();
        setSize(2200,800);
        setLocationRelativeTo(null);
        setVisible(true);
    }
 
    /**	yearComboBox의 리스너를 통해 표를 업데이트하는 메소드	
     * @throws SQLException */
    public void updateTable() throws SQLException {
        int selectedYear = Integer.parseInt(yearComboBox.getSelectedItem().toString());

        // 선택한 연도의 타자, 투수, 수비, 주루에 대한 테이블 모델 가져오기
        DatabaseManager dbManager = DatabaseManager.getInstance();

        ArrayList<hitterDAO> hitterTypeList = dbManager.getHitterTypeList(selectedYear);
        ArrayList<hitterDAO> leftHandedPitcherData = hitterTypeList.stream()
            .filter(h -> "좌투수".equalsIgnoreCase(h.getPitcherType()))
            .collect(Collectors.toCollection(ArrayList::new));

        hitterTableModel hitterModel = new hitterTableModel(selectedYear,leftHandedPitcherData);
        pitcherTableModel pitcherModel = new pitcherTableModel(selectedYear);
        defenseTableModel defenseModel = new defenseTableModel(selectedYear);
        runnerTableModel runnerModel = new runnerTableModel(selectedYear);

        // tabbedPane에서 테이블 업데이트
        hitterTable.setModel(hitterModel);
        pitcherTable.setModel(pitcherModel);
        defenseTable.setModel(defenseModel);
        runnerTable.setModel(runnerModel);

        // hitterTable과 runnerTable의 배경색 초기화
        resetTableBackground(hitterTable);
        resetTableBackground(runnerTable);
    }

    /**	btn1 클릭 이벤트 처리 코드*/
    private void btn1Clicked() {	
		closeConnection();
        dispose();
        App1 app1 = new App1();
        app1.setVisible(true);
	}
    /**	연결 종료*/
	private void closeConnection() {	
	    dbManager.close(null, null, null);
	}

	private void resetTableBackground(JTable table) {
	    for (int row = 0; row < table.getRowCount(); row++) {
	        for (int col = 0; col < table.getColumnCount(); col++) {
	            TableCellRenderer renderer = table.getCellRenderer(row, col);
	            Component c = table.prepareRenderer(renderer, row, col);
	            c.setBackground(table.getBackground());
	        }
	    }
	    table.repaint();
	}
}
