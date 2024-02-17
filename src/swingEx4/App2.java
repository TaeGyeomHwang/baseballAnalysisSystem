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
	
	
	/** 어플리케이션 실행*/
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {	// runnable로 객체 만들어서 실행
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

	/** 어플리케이션 생성 */
	public App2() throws SQLException {
		this.dbManager = DatabaseManager.create();
		initialize();
		
	}

	/** 프레임에 콘텐츠 생성*/
	private void initialize() throws SQLException {
		getContentPane().setBackground(new Color(173, 216, 230));
		
		setTitle("선수 정보 조회");
		/** JTable에 들어갈 데이터*/
		playerTableModel playerModel = new playerTableModel();
		 
	    /** JTable 선언*/
		JTable table = new JTable(playerModel);
	    table.setPreferredScrollableViewportSize(new Dimension(700, 525)); // 지정 크기 벗어나면 스크롤로 표 보기
	    table.setFillsViewportHeight(true);	// 지정한 높이 완전히 테이블이 사용할 수 있도록 설정
	    
	    /** JTextField 선언*/
	    JTextField conditionField = new JTextField();	// Condition 열에 텍스트 필드 및 저장 버튼 만들기
	    conditionField.setPreferredSize(new Dimension(200, 23));
	    
	    
	    /** Jpanel 선언*/
	    JPanel panel = new JPanel(new BorderLayout());	
	    panel.setBackground(new Color(173, 216, 230));
	    panel.add(new JScrollPane(table), BorderLayout.CENTER);
	    
	    /** 버튼 생성*/
	    JButton btn1 = new JButton("메인으로");
	    btn1.setPreferredSize(new Dimension(97, 23));	// 버튼 크기 설정
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
	                JOptionPane.showMessageDialog(null, "Condition을 저장할 행을 선택하세요.");
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
	                        JOptionPane.showMessageDialog(null, "Condition이 성공적으로 저장되었습니다.");
	                        break;
	                    }
	                }
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	                JOptionPane.showMessageDialog(null, "Condition 저장에 에러 발생: " + ex.getMessage());
	            }
	        }
	    });

	    
	    /** 레이아웃 설정*/
	    JPanel southPanel = new JPanel();
	    southPanel.setBackground(new Color(173, 216, 230));
	    southPanel.add(btn1);
	    
	    JPanel conditionPanel = new JPanel();
	    conditionPanel.setBackground(new Color(173, 216, 230));
	    conditionPanel.add(new JLabel("Condition 수정: "));
	    conditionPanel.add(conditionField);
	    conditionPanel.add(saveButton);

	    getContentPane().add(panel, BorderLayout.CENTER);
	    getContentPane().add(southPanel, BorderLayout.SOUTH);
	    getContentPane().add(conditionPanel, BorderLayout.NORTH);
	    setSize(800, 640);
	    setLocationRelativeTo(null);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	}
	
	private void closeConnection() {	//	연결 종료
	    dbManager.close(null, null, null);
	  }
	
	private void btn1Clicked() {	// btn1 클릭 이벤트 처리 코드
		closeConnection();
        dispose();
        App1 app1 = new App1();
        app1.setVisible(true);
	}
}
