package swingEx4;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class App4 extends JFrame {

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    App4 window = new App4();
                    window.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public App4() {
        initialize();
    }

    private void initialize() {
        pack();
        setMinimumSize(new Dimension(600, 600));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        setLocationRelativeTo(null);

        Container contentPane = getContentPane();
        contentPane.setBackground(new Color(173, 216, 230));

        contentPane.setLayout(new BorderLayout());

        JPanel southPanel = new JPanel();
        southPanel.setBackground(new Color(173, 216, 230));
        JButton btn1 = new JButton("메인으로");
        btn1.setPreferredSize(new Dimension(97, 23));
        southPanel.add(btn1);
        contentPane.add(southPanel, BorderLayout.SOUTH);

        btn1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                App1 a1 = new App1();
                a1.setVisible(true);
                dispose();
            }
        });

        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(173, 216, 230));
        topPanel.add(new JLabel("홈 팀 선택:"));
        JComboBox<String> HomeTeamComboBox = new JComboBox<String>(new String[]{"두산", "롯데", "삼성", "키움", "한화", "KIA", "KT", "LG", "NC", "SSG"});
        JComboBox<String> AwayTeamComboBox = new JComboBox<String>(new String[]{"두산", "롯데", "삼성", "키움", "한화", "KIA", "KT", "LG", "NC", "SSG"});
        topPanel.add(HomeTeamComboBox);
        topPanel.add(new JLabel("어웨이 팀 선택:"));
        topPanel.add(AwayTeamComboBox);
        JButton analyzeButton = new JButton("분석하기");
        topPanel.add(analyzeButton);
        contentPane.add(topPanel, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel normalPanel = new JPanel();
        JPanel leftPanel = new JPanel();
        JPanel underPanel = new JPanel();
        normalPanel.setBackground(new Color(173, 216, 230));
        leftPanel.setBackground(new Color(173, 216, 230));
        underPanel.setBackground(new Color(173, 216, 230));

        tabbedPane.addTab("선수 명단 분석", normalPanel);
        tabbedPane.addTab("좌투수 상대 명단 분석", leftPanel);
        tabbedPane.addTab("언터핸드 상대 명단 분석", underPanel);

        contentPane.add(tabbedPane, BorderLayout.CENTER);

        analyzeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    DatabaseManager dbManager = DatabaseManager.getInstance();
                    ArrayList<defenseDAO> defenseList22 = dbManager.getDefenseList(2022);
                    ArrayList<hitterDAO> hitterOpponentList22 = dbManager.getHitterOpponentList(2022);
                    ArrayList<hitterDAO> hitterTypeList22 = dbManager.getHitterTypeList(2022);
                    ArrayList<runnerDAO> runnerList22 = dbManager.getRunnerList(2022);
                    ArrayList<pitcherDAO> pitcherList22 = dbManager.getPitcherList(2022);

                    String homeTeam = (String) HomeTeamComboBox.getSelectedItem();
                    String awayTeam = (String) AwayTeamComboBox.getSelectedItem();

                    rosterAnalysis startRoster = new rosterAnalysis();

                    ArrayList<lineUpDAO> normalLineup = startRoster.normalList(homeTeam, awayTeam, hitterOpponentList22, defenseList22, runnerList22);
                    ArrayList<lineUpDAO> leftLineup = startRoster.leftList(homeTeam, awayTeam, hitterOpponentList22, defenseList22, hitterTypeList22, runnerList22);
                    ArrayList<lineUpDAO> underLineup = startRoster.underList(homeTeam, awayTeam, hitterOpponentList22, defenseList22, hitterTypeList22, runnerList22);
                    ArrayList<lineUpDAO> pitchingList = startRoster.createPitchingList(pitcherList22, homeTeam);

                    displayLineUp(normalLineup, normalPanel, false);
                    displayLineUp(leftLineup, leftPanel, false);
                    displayLineUp(underLineup, underPanel, false);

                    // Adding the pitching list to all panels
                    displayPitchingList(pitchingList, normalPanel);
                    displayPitchingList(pitchingList, leftPanel);
                    displayPitchingList(pitchingList, underPanel);

                    contentPane.revalidate();
                    contentPane.repaint();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(App4.this, "database 접속중 오류 발생", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public void displayLineUp(ArrayList<lineUpDAO> lineup, JPanel panel, boolean isPitcherPanel) {
        DefaultTableModel tableModel1 = new DefaultTableModel(new String[]{"Batting Order", "Position", "Name"}, 0);
        DefaultTableModel tableModel2 = new DefaultTableModel(new String[]{"Position", "Name"}, 0);

        for (lineUpDAO player : lineup) {
            if (!isPitcherPanel) {
                if (player.getRank() == 1) {
                    tableModel1.addRow(new Object[]{player.getLineUp(), player.getPosition(), player.getName()});
                } else if ((player.getRank() == 2 || player.getRank() == 3 || player.getRank() == 4) && player.getName() != null && !player.getName().trim().isEmpty()) {
                    tableModel2.addRow(new Object[]{player.getPosition(), player.getName()});
                }
            }
        }

        panel.removeAll();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel label1 = new JLabel("● 선발 선수 명단");
        label1.setMaximumSize(new Dimension(Integer.MAX_VALUE, label1.getPreferredSize().height));
        label1.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(label1);

        JTable jTable1 = new JTable(tableModel1);
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel1);
        jTable1.setRowSorter(sorter);
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING)); // 0 refers to the "Batting Order" column
        sorter.setSortKeys(sortKeys);
        sorter.sort();
        panel.add(new JScrollPane(jTable1));

        JLabel label2 = new JLabel("● 후보 선수 명단");
        label2.setMaximumSize(new Dimension(Integer.MAX_VALUE, label2.getPreferredSize().height));
        label2.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(label2);

        JTable jTable2 = new JTable(tableModel2);
        panel.add(new JScrollPane(jTable2));
    }
    public void displayPitchingList(ArrayList<lineUpDAO> pitchingList, JPanel panel) {
        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"Position", "Name"}, 0);

        for (lineUpDAO player : pitchingList) {
            tableModel.addRow(new Object[]{player.getPosition(), player.getName()});
        }

        JLabel label = new JLabel("● 투수 명단");
        label.setMaximumSize(new Dimension(Integer.MAX_VALUE, label.getPreferredSize().height));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(label);

        JTable jTable = new JTable(tableModel);
        panel.add(new JScrollPane(jTable));
    }

}