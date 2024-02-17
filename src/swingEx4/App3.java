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
    
    /** ���ø����̼� ����*/
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
    
    /** ���ø����̼� ���� */
    public App3() throws SQLException {
		initialize();
	}

    /** �����ӿ� ������ ����*/
    private void initialize() throws SQLException{

        setTitle("���� ��� ��ȸ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // JComboBox�� ����� ������ �߰�
        yearComboBox = new JComboBox<Integer>();
        yearComboBox.addItem(2022);
        yearComboBox.addItem(2021);
        yearComboBox.addItem(2020);
        yearComboBox.addItem(2019);
        yearComboBox.addItem(2018);
        
	    JButton btn1 = new JButton("��������");
	    btn1.setPreferredSize(new Dimension(97, 23));	// ��ư ũ�� ����
	    btn1.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            btn1Clicked();
	        }
	    });
        
	    
        // TableModel ����
        int year = 2022;
        DatabaseManager dbManager = DatabaseManager.getInstance();
        
        ArrayList<hitterDAO> hitterTypeList = dbManager.getHitterTypeList(year);	//	Ÿ�� ���� �����͸� �˻��ϰ� ������ �����Ϳ� ���� ����� ���͸�
	    ArrayList<hitterDAO> leftHandedPitcherData = hitterTypeList.stream()
	        .filter(h -> "������".equalsIgnoreCase(h.getPitcherType()))
	        .collect(Collectors.toCollection(ArrayList::new));
	    
	    
        hitterTableModel hitterModel = new hitterTableModel(year, leftHandedPitcherData);	//	leftHandedPitcherData ����� hitterTableModel �����ڿ� ����
        pitcherModel = new pitcherTableModel(year);
        defenseModel = new defenseTableModel(year);
        runnerModel = new runnerTableModel(year);

        // ���� â�� ���̾ƿ� ����
        Container contentPane = getContentPane();
		getContentPane().setBackground(new Color(173, 216, 230));
        contentPane.setLayout(new BorderLayout());
	
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(173, 216, 230));
        topPanel.add(new JLabel("���� ����:"));
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
        
        tabbedPane.addTab("Ÿ�� ���", new JScrollPane(hitterTable));
        tabbedPane.addTab("���� ���", new JScrollPane(pitcherTable));
        tabbedPane.addTab("���� ���", new JScrollPane(defenseTable));
        tabbedPane.addTab("�ַ� ���", new JScrollPane(runnerTable));
        contentPane.add(tabbedPane, BorderLayout.CENTER);
        
        tabbedPane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if (tabbedPane.getSelectedIndex() != 0) { // ������ ���� hitterTable�� �ƴ� ���
                	resetTableBackground(hitterTable);
                    resetTableBackground(runnerTable);
                }
            }
        });

        // yearComboBox�� �����ʸ� �߰�, ���̺��� �����͸� ������Ʈ
        yearComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
					updateTable();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
            }
        });
        
        // ������ ���̺��� �׸��� Ŭ���ϴ� ������ �߰�
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
        
        //	Į�� �̸��� ���콺 �ø��� ����
        JTableHeader header1 = hitterTable.getTableHeader();
        JTableHeader header2 = hitterTable.getTableHeader();
        JTableHeader header3 = hitterTable.getTableHeader();
        JTableHeader header4 = hitterTable.getTableHeader();
        header1.setToolTipText("�����Ϸ��� ǥ�� �׸��� ��������.");
        header2.setToolTipText("�����Ϸ��� ǥ�� �׸��� ��������.");
        header3.setToolTipText("�����Ϸ��� ǥ�� �׸��� ��������.");
        header4.setToolTipText("�����Ϸ��� ǥ�� �׸��� ��������.");
        
        // â ǥ��
        pack();
        setSize(2200,800);
        setLocationRelativeTo(null);
        setVisible(true);
    }
 
    /**	yearComboBox�� �����ʸ� ���� ǥ�� ������Ʈ�ϴ� �޼ҵ�	
     * @throws SQLException */
    public void updateTable() throws SQLException {
        int selectedYear = Integer.parseInt(yearComboBox.getSelectedItem().toString());

        // ������ ������ Ÿ��, ����, ����, �ַ翡 ���� ���̺� �� ��������
        DatabaseManager dbManager = DatabaseManager.getInstance();

        ArrayList<hitterDAO> hitterTypeList = dbManager.getHitterTypeList(selectedYear);
        ArrayList<hitterDAO> leftHandedPitcherData = hitterTypeList.stream()
            .filter(h -> "������".equalsIgnoreCase(h.getPitcherType()))
            .collect(Collectors.toCollection(ArrayList::new));

        hitterTableModel hitterModel = new hitterTableModel(selectedYear,leftHandedPitcherData);
        pitcherTableModel pitcherModel = new pitcherTableModel(selectedYear);
        defenseTableModel defenseModel = new defenseTableModel(selectedYear);
        runnerTableModel runnerModel = new runnerTableModel(selectedYear);

        // tabbedPane���� ���̺� ������Ʈ
        hitterTable.setModel(hitterModel);
        pitcherTable.setModel(pitcherModel);
        defenseTable.setModel(defenseModel);
        runnerTable.setModel(runnerModel);

        // hitterTable�� runnerTable�� ���� �ʱ�ȭ
        resetTableBackground(hitterTable);
        resetTableBackground(runnerTable);
    }

    /**	btn1 Ŭ�� �̺�Ʈ ó�� �ڵ�*/
    private void btn1Clicked() {	
		closeConnection();
        dispose();
        App1 app1 = new App1();
        app1.setVisible(true);
	}
    /**	���� ����*/
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
