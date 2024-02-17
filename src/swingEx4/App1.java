package swingEx4;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;

public class App1 extends JFrame {
	private JPanel contentPane;
	private Connection conn;

	/** ���ø����̼� ���� */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App1 frame = new App1();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/** ���ø����̼� ���� */
	public App1() {
		initialize();
		
	}

	/** �����ӿ� ������ ���� */
	private void initialize() {
		setTitle("���� ��� �м� �ý���");
		setBounds(0, 0, 400, 300);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setLayout(null);
		setContentPane(contentPane);
		getContentPane().setBackground(new Color(173, 216, 230));

		JButton btn1 = new JButton("���� ���� ��ȸ");
		btn1.setBounds(112, 30, 160, 30);
		contentPane.add(btn1);

		JButton btn2 = new JButton("���� ��� ��ȸ");
		btn2.setBounds(112, 70, 160, 30);
		contentPane.add(btn2);

		JButton btn3 = new JButton("���� / ��ü ��� �м�");
		btn3.setBounds(112, 110, 160, 30);
		contentPane.add(btn3);

		JButton btn4 = new JButton("��õ ���� �м�");
		btn4.setBounds(112, 150, 160, 30);
		contentPane.add(btn4);

		JButton btn5 = new JButton("�� ���� ����");
		btn5.setBounds(112, 190, 160, 30);
		contentPane.add(btn5);

		btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				App2 a2 = null;
				try {
					a2 = new App2();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				a2.setVisible(true);
				dispose();
			}
		});

		btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				App3 a3 = null;
				try {
					a3 = new App3();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				a3.setVisible(true);
				dispose();
			}
		});

		btn3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				App4 a4 = new App4();
				a4.setVisible(true);
				dispose();
			}
		});

		btn4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				App5 a5 = new App5();
				a5.setVisible(true);
				dispose();
			}
		});

		btn5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				App6 a6 = new App6();
				a6.setVisible(true);
				dispose();
			}
		});
	}
}
