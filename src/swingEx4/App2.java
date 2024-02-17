package swingEx4;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JScrollPane;

public class App2 extends JFrame{
	
	private DatabaseManager dbManager;
	
	
	/** ���ø����̼� ����*/
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {	// runnable�� ��ü ���� ����
			public void run() {
				try {
					App2 window = new App2();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	/** ���ø����̼� ���� */
	public App2() throws SQLException {
		this.dbManager = DatabaseManager.create();
		initialize();
		
	}

	/** �����ӿ� ������ ����*/
	private void initialize() throws SQLException {
		getContentPane().setBackground(new Color(173, 216, 230));
		
		setTitle("���� ���� ��ȸ");
		/** JTable�� �� ������*/
		playerTableModel playerModel = new playerTableModel();
		 
	    /** JTable ����*/
		JTable table = new JTable(playerModel);
	    table.setPreferredScrollableViewportSize(new Dimension(700, 525)); // ���� ũ�� ����� ��ũ�ѷ� ǥ ����
	    table.setFillsViewportHeight(true);	// ������ ���� ������ ���̺��� ����� �� �ֵ��� ����
	    
	    /** JTextField ����*/
	    JTextField conditionField = new JTextField();	// Condition ���� �ؽ�Ʈ �ʵ� �� ���� ��ư �����
	    conditionField.setPreferredSize(new Dimension(200, 23));
	    
	    
	    /** Jpanel ����*/
	    JPanel panel = new JPanel(new BorderLayout());	
	    panel.setBackground(new Color(173, 216, 230));
	    panel.add(new JScrollPane(table), BorderLayout.CENTER);
	    
	    /** ��ư ����*/
	    JButton btn1 = new JButton("��������");
	    btn1.setPreferredSize(new Dimension(97, 23));	// ��ư ũ�� ����
	    btn1.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            btn1Clicked();
	        }
	    });
	    
	    JButton saveButton = new JButton("Save");
	    saveButton.setPreferredSize(new Dimension(70, 23));
	    saveButton.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            int selectedRow = table.getSelectedRow();
	            if (selectedRow == -1) {
	                JOptionPane.showMessageDialog(null, "Condition�� ������ ���� �����ϼ���.");
	                return;
	            }
	            String name =  (String) table.getValueAt(selectedRow, 1);
	            String condition = conditionField.getText();
	            String position = (String) table.getValueAt(selectedRow, 3);

	            try {
	                DatabaseManager.getInstance().updatePlayerCondition(name, condition, position);
	                for (int i = 0; i < playerModel.getRowCount(); i++) {
	                    playerDAO player = playerModel.getPlayerData().get(i);
	                    if (player.getName().equals(name) && player.getPosition().equals(position)) {
	                        player.setCondition(condition);
	                        playerModel.fireTableCellUpdated(i, 7);
	                        JOptionPane.showMessageDialog(null, "Condition�� ���������� ����Ǿ����ϴ�.");
	                        break;
	                    }
	                }
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	                JOptionPane.showMessageDialog(null, "Condition ���忡 ���� �߻�: " + ex.getMessage());
	            }
	        }
	    });

	    
	    /** ���̾ƿ� ����*/
	    JPanel southPanel = new JPanel();
	    southPanel.setBackground(new Color(173, 216, 230));
	    southPanel.add(btn1);
	    
	    JPanel conditionPanel = new JPanel();
	    conditionPanel.setBackground(new Color(173, 216, 230));
	    conditionPanel.add(new JLabel("Condition ����: "));
	    conditionPanel.add(conditionField);
	    conditionPanel.add(saveButton);

	    getContentPane().add(panel, BorderLayout.CENTER);
	    getContentPane().add(southPanel, BorderLayout.SOUTH);
	    getContentPane().add(conditionPanel, BorderLayout.NORTH);
	    setSize(800, 640);
	    setLocationRelativeTo(null);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	}
	
	private void closeConnection() {	//	���� ����
	    dbManager.close(null, null, null);
	  }
	
	private void btn1Clicked() {	// btn1 Ŭ�� �̺�Ʈ ó�� �ڵ�
		closeConnection();
        dispose();
        App1 app1 = new App1();
        app1.setVisible(true);
	}
}
