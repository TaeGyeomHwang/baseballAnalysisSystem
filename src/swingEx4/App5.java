package swingEx4;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import swingEx4.recommendationStrategyAnalysis.FpctAvgLeft;
import swingEx4.recommendationStrategyAnalysis.FpctAvgRight;
import swingEx4.recommendationStrategyAnalysis.FpctAvgUnder;
import swingEx4.recommendationStrategyAnalysis.HitterTrend;
import swingEx4.recommendationStrategyAnalysis.PitcherTrend;
import swingEx4.recommendationStrategyAnalysis.WeakLeftPitcher;
import swingEx4.recommendationStrategyAnalysis.WeakLeftUnderHitter;
import swingEx4.defenseDAO;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;

public class App5 extends JFrame{
	private JComboBox<String> homeTeamComboBox;
    private JComboBox<String> awayTeamComboBox;
    private JButton analyzeButton;

	/** 어플리케이션 실행*/
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App5 window = new App5();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/** 어플리케이션 생성*/
	public App5() {
		initialize();
	}

	/** 프레임에 콘텐츠 생성*/
	private void initialize() {
		pack();
		getContentPane().setBackground(new Color(173, 216, 230));
		setMinimumSize(new Dimension(1000, 800));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);
		
		Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        
        JPanel northPanel = new JPanel();
        northPanel.setBackground(new Color(173, 216, 230));
		contentPane.add(northPanel, BorderLayout.NORTH);
		
		// 홈팀 선택 
        JLabel homeTeamLabel = new JLabel("홈 팀:");
        northPanel.add(homeTeamLabel);

        homeTeamComboBox = new JComboBox<>();
        homeTeamComboBox.addItem("두산");
        homeTeamComboBox.addItem("롯데");
        homeTeamComboBox.addItem("삼성");
        homeTeamComboBox.addItem("키움");
        homeTeamComboBox.addItem("한화");
        homeTeamComboBox.addItem("KIA");
        homeTeamComboBox.addItem("KT");
        homeTeamComboBox.addItem("LG");
        homeTeamComboBox.addItem("NC");
        homeTeamComboBox.addItem("SSG");
        northPanel.add(homeTeamComboBox);
        
        // 어웨이 팀 선택
        JLabel awayTeamLabel = new JLabel("어웨이 팀:");
        northPanel.add(awayTeamLabel);

        awayTeamComboBox = new JComboBox<>();
        awayTeamComboBox.addItem("두산");
        awayTeamComboBox.addItem("롯데");
        awayTeamComboBox.addItem("삼성");
        awayTeamComboBox.addItem("키움");
        awayTeamComboBox.addItem("한화");
        awayTeamComboBox.addItem("KIA");
        awayTeamComboBox.addItem("KT");
        awayTeamComboBox.addItem("LG");
        awayTeamComboBox.addItem("NC");
        awayTeamComboBox.addItem("SSG");
        northPanel.add(awayTeamComboBox);
        
        // 분석하기 버튼
        analyzeButton = new JButton("분석하기");
        analyzeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
					analyzeTeams();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
            }
        });
        northPanel.add(analyzeButton);

        
		JPanel southPanel = new JPanel();
		southPanel.setBackground(new Color(173, 216, 230));
		
		contentPane.add(southPanel, BorderLayout.SOUTH);
		JButton btn1 = new JButton("메인으로");
		btn1.setPreferredSize(new Dimension(97, 23));	// 버튼 크기 설정
		southPanel.add(btn1);
		
		btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				App1 a1 = new App1();
				a1.setVisible(true);
				dispose();
			}
		});
	}
	
    private void analyzeTeams() throws SQLException {
        String homeTeam = (String) homeTeamComboBox.getSelectedItem();
        String awayTeam = (String) awayTeamComboBox.getSelectedItem();

        TeamAnalyzer teamAnalyzer = new TeamAnalyzer(homeTeam, awayTeam);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(new Color(173, 216, 230));
        teamAnalyzer.analyzeTeams(tabbedPane, homeTeam, awayTeam);

        // 패널을 프레임에 추가
        getContentPane().add(tabbedPane, BorderLayout.CENTER);

        // 화면을 새로고침
        revalidate();
        repaint();
    }

	
	private class TeamAnalyzer {
	        private String homeTeam;
	        private String awayTeam;
	        private List<pitcherDAO> homeTeamPitchers;
	        private List<pitcherDAO> awayTeamPitchers;
	        private List<hitterDAO> homeTeamHitters;
	        private List<hitterDAO> awayTeamHitters;
	        private List<runnerDAO> homeTeamRunners;
	        private List<runnerDAO> awayTeamRunners;
	        private List<hitterDAO> homeTeamLeftHitters;
	        private List<hitterDAO> homeTeamUnderHitters;
	        private List<hitterDAO> awayTeamLeftHitters;
	        private List<hitterDAO> awayTeamUnderHitters;
	        
	        public TeamAnalyzer(String homeTeam, String awayTeam) {
	            this.homeTeam = homeTeam;
	            this.awayTeam = awayTeam;
	            try {
	                fetchAndInitializeAttributes();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }

	        
	        public void analyzeTeams(JTabbedPane tabbedPane, String homeTeam,String awayTeam) throws SQLException {
	            // 가져온 데이터 분석
	            createStatsPanel(tabbedPane);
	            createHitterTrendPanel(tabbedPane, homeTeam,awayTeam);
	            createPitcherTrendPanel(tabbedPane, homeTeam,awayTeam);
	            createWeakLeftPitcherPanel(tabbedPane, homeTeam, awayTeam);
	        }
	        
	        private void fetchAndInitializeAttributes() throws SQLException {
	            DatabaseManager dbManager = DatabaseManager.getInstance();
	            
	            List<pitcherDAO> allPitchers = dbManager.getPitcherList(2022);
	            homeTeamPitchers = new ArrayList<>();
	            awayTeamPitchers = new ArrayList<>();
	            
	            for (pitcherDAO pitcher : allPitchers) {
	                String pitcherTeam = pitcher.getTeamName();
	                if (pitcherTeam.equals(homeTeam)) {
	                    homeTeamPitchers.add(pitcher);
	                } else if (pitcherTeam.equals(awayTeam)) {
	                    awayTeamPitchers.add(pitcher);
	                }
	            }

	            // 타자도 동일한 코드 사용
	            List<hitterDAO> allHitters = dbManager.getHitterList(2022);
	            homeTeamHitters = new ArrayList<>();
	            awayTeamHitters = new ArrayList<>();
	            
	            for (hitterDAO hitter : allHitters) {
	                String hitterTeam = hitter.getTeamName();
	                if (hitterTeam.equals(homeTeam)) {
	                    homeTeamHitters.add(hitter);
	                } else if (hitterTeam.equals(awayTeam)) {
	                    awayTeamHitters.add(hitter);
	                }
	            }
	            
	            List<hitterDAO> typeHitters = dbManager.getHitterTypeList(2022);
	            homeTeamLeftHitters = new ArrayList<>();
	            homeTeamUnderHitters = new ArrayList<>();
	            awayTeamLeftHitters = new ArrayList<>();
	            awayTeamUnderHitters = new ArrayList<>();
	            
	            for (hitterDAO hitter : typeHitters) {
	                String hitterTeam = hitter.getTeamName();
	                String pitcherType = hitter.getPitcherType();
	                if (hitterTeam.equals(homeTeam)&&pitcherType.equals("좌투수")) {
	                	homeTeamLeftHitters.add(hitter);
	                } 
	                  else if (hitterTeam.equals(homeTeam)&&pitcherType.equals("언더")) {
	                	homeTeamUnderHitters.add(hitter);
	                } 
	                  else if (hitterTeam.equals(awayTeam)&&pitcherType.equals("좌투수")) {
	                	  awayTeamLeftHitters.add(hitter);
	                }
	                  else if (hitterTeam.equals(awayTeam)&&pitcherType.equals("언더")) {
	                	  awayTeamUnderHitters.add(hitter);
	                }
	            }
	            
	            List<runnerDAO> allRunners = dbManager.getRunnerList(2022);
	            homeTeamRunners = new ArrayList<>();
	            awayTeamRunners = new ArrayList<>();
	            
	            for (runnerDAO runner : allRunners) {
	                String runnerTeam = runner.getTeamName();
	                if (runnerTeam.equals(homeTeam)) {
	                	homeTeamRunners.add(runner);
	                } else if (runnerTeam.equals(awayTeam)) {
	                	awayTeamRunners.add(runner);
	                }
	            }
	        }
	        
	        private <T> void displayTopFive(String title, List<T> homeTeamData, List<T> awayTeamData, Comparator<T> comparator, TeamAnalyzer teamAnalyzer, int[] displayedColumns, JPanel statsPanel, GridBagConstraints gbc) {
	            JTable topFiveTable = teamAnalyzer.createTopFiveTable(title, homeTeamData, awayTeamData, comparator, displayedColumns);
	            
	            // 표 크기 계산
	            int totalColumnWidth = 0;
	            for (int i = 0; i < topFiveTable.getColumnCount(); i++) {
	                TableColumn column = topFiveTable.getColumnModel().getColumn(i);
	                totalColumnWidth += column.getPreferredWidth();
	            }
	            int totalRowHeight = topFiveTable.getRowHeight() * 10;

	            JScrollPane scrollPane = new JScrollPane(topFiveTable);
	            scrollPane.setBackground(new Color(173, 216, 230));
	            
	            
	            Dimension preferredTableSize = new Dimension(totalColumnWidth, totalRowHeight);
	            topFiveTable.setPreferredScrollableViewportSize(preferredTableSize);
	            scrollPane.setPreferredSize(preferredTableSize);

	            topFiveTable.setFillsViewportHeight(true);
	            topFiveTable.revalidate();
	            topFiveTable.repaint();


	            // titleLabel 생성
	            JLabel titleLabel = new JLabel(title);
	            titleLabel.setFont(new Font("나눔고딕", Font.BOLD, 18));
	            titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
	            titleLabel.setPreferredSize(new Dimension(scrollPane.getPreferredSize().width, 30));

	            // titleLabel을 추가하기 전에 GridBagConstraints를 재설정
	            gbc.gridwidth = 1;
	            gbc.gridheight = 1;
	            gbc.weightx = 1;
	            gbc.weighty = 1;
	            gbc.fill = GridBagConstraints.BOTH;
	            statsPanel.add(titleLabel, gbc);
	            gbc.gridy++; // 다음 행으로 이동하여 scrollPane 생성
	            statsPanel.add(scrollPane, gbc);
	            gbc.gridy++; // 다음 행으로 이동하여 titleLabel 생성
	        }

	        private void createStatsPanel(JTabbedPane tabbedPane) {
	            JPanel statsPanel = new JPanel();
	            statsPanel.setLayout(new GridBagLayout());
	            GridBagConstraints gbc = new GridBagConstraints();

	            gbc.insets = new Insets(2, 5, 2, 5); // 상단 및 하단 인셋 줄이기

	            // Pitchers
	            gbc.gridx = 0;
	            gbc.gridy = 0;
	            displayTopFive("● 투수 최다승리", homeTeamPitchers, awayTeamPitchers, Comparator.comparing((pitcherDAO p) -> p.getW()).reversed(), this, new int[]{0, 1, 2}, statsPanel, gbc);
	            gbc.gridx = 1;
	            gbc.gridy = 0;
	            displayTopFive("● 투수 평균자책점", homeTeamPitchers, awayTeamPitchers, Comparator.comparing((pitcherDAO p) -> p.getEra()), this, new int[]{0, 1, 3}, statsPanel, gbc);
	            gbc.gridx = 2;
	            gbc.gridy = 0;
	            displayTopFive("● 투수 탈삼진", homeTeamPitchers, awayTeamPitchers, Comparator.comparing((pitcherDAO p) -> p.getSo()).reversed(), this, new int[]{0, 1, 4}, statsPanel, gbc);
	            
	            gbc.gridx = 0;
	            gbc.gridy = 2;
	            displayTopFive("● 투수 세이브", homeTeamPitchers, awayTeamPitchers, Comparator.comparing((pitcherDAO p) -> p.getSv()).reversed(), this, new int[]{0, 1, 5}, statsPanel, gbc);
	            gbc.gridx = 1;
	            gbc.gridy = 2;
	            displayTopFive("● 투수 WHIP", homeTeamPitchers, awayTeamPitchers, Comparator.comparing((pitcherDAO p) -> p.getWhip()), this, new int[]{0, 1, 6}, statsPanel, gbc);
	            // Hitters
	            gbc.gridx = 2;
	            gbc.gridy = 2;
	            displayTopFive("● 타자 타율", homeTeamHitters, awayTeamHitters, Comparator.comparing((hitterDAO b) -> b.getAverage()).reversed(), this, new int[]{0, 1, 2}, statsPanel, gbc);

	            gbc.gridx = 0;
	            gbc.gridy = 4;
	            displayTopFive("● 타자 타점", homeTeamHitters, awayTeamHitters, Comparator.comparing((hitterDAO b) -> b.getRbi()).reversed(), this, new int[]{0, 1, 3}, statsPanel, gbc);
	            gbc.gridx = 1;
	            gbc.gridy = 4;
	            displayTopFive("● 타자 홈런", homeTeamHitters, awayTeamHitters, Comparator.comparing((hitterDAO b) -> b.getHr()).reversed(), this, new int[]{0, 1, 4}, statsPanel, gbc);
	            gbc.gridx = 2;
	            gbc.gridy = 4;
	            displayTopFive("● 타자 도루성공", homeTeamRunners, awayTeamRunners, Comparator.comparing((runnerDAO r) -> r.getSb()).reversed(), this, new int[]{0, 1, 2}, statsPanel, gbc);

	            gbc.gridx = 0;
	            gbc.gridy = 6;
	            displayTopFive("● 타자 OPS", homeTeamHitters, awayTeamHitters, Comparator.comparing((hitterDAO b) -> b.getOps()).reversed(), this, new int[]{0, 1, 5}, statsPanel, gbc);
	            gbc.gridx = 1;
	            gbc.gridy = 6;
	            displayTopFive("● 타자 XR", homeTeamHitters, awayTeamHitters, Comparator.comparing((hitterDAO b) -> b.getXr()).reversed(), this, new int[]{0, 1, 6}, statsPanel, gbc);
	            gbc.gridx = 2;
	            gbc.gridy = 6;
	            displayTopFive("● 타자 GPA", homeTeamHitters, awayTeamHitters, Comparator.comparing((hitterDAO b) -> b.getGpa()).reversed(), this, new int[]{0, 1, 7}, statsPanel, gbc);

	            tabbedPane.addTab("주요 기록", statsPanel);
	            tabbedPane.setBackground(new Color(173, 216, 230));
	            
	        }
	        
	        public <T> JTable createTopFiveTable(String title, List<T> homeTeamData, List<T> awayTeamData, Comparator<T> comparator, int[] displayedColumns) {
	            homeTeamData.sort(comparator);
	            awayTeamData.sort(comparator);
	            
	            List<T> combinedData = new ArrayList<>();
	            combinedData.addAll(homeTeamData.subList(0, Math.min(5, homeTeamData.size())));
	            combinedData.addAll(awayTeamData.subList(0, Math.min(5, awayTeamData.size())));
	            
	            String[] allColumnNames;
	            if (!combinedData.isEmpty()) {
	                Object firstElement = combinedData.get(0);
	                if (firstElement instanceof pitcherDAO) {
	                    allColumnNames = new String[]{"Player Name", "Team", "Wins", "ERA", "Strikeouts", "Saves", "WHIP"};
	                } else if (firstElement instanceof hitterDAO) {
	                    allColumnNames = new String[]{"Player Name", "Team", "Batting Average", "RBIs", "Home Runs", "OPS", "XR", "GPA"};
	                } else if (firstElement instanceof runnerDAO) {
	                    allColumnNames = new String[]{"Player Name", "Team", "Stolen Bases"};
	                } else {
	                    throw new IllegalArgumentException("Invalid data type");
	                }
	            } else {
	                throw new IllegalStateException("No data found");
	            }

	            String[] columnNames = new String[displayedColumns.length];
	            for (int i = 0; i < displayedColumns.length; i++) {
	                columnNames[i] = allColumnNames[displayedColumns[i]];
	            }

	            Object[][] rowData = new Object[combinedData.size()][displayedColumns.length];
	            
	            for (int i = 0; i < combinedData.size(); i++) {
	                T item = combinedData.get(i);
	                Object[] itemData;
	                if (item instanceof pitcherDAO) {
	                    pitcherDAO pitcher = (pitcherDAO) item;
	                    itemData = new Object[]{pitcher.getName(), pitcher.getTeamName(), pitcher.getW(), pitcher.getEra(), pitcher.getSo(), pitcher.getSv(), pitcher.getWhip()};
	                } else if (item instanceof hitterDAO) {
	                    hitterDAO hitter = (hitterDAO) item;
	                    itemData = new Object[]{hitter.getName(), hitter.getTeamName(), hitter.getAverage(), hitter.getRbi(), hitter.getHr(), hitter.getOps(), hitter.getXr(), hitter.getGpa()};
	                } else if (item instanceof runnerDAO) {
	                    runnerDAO runner = (runnerDAO) item;
	                    itemData = new Object[]{runner.getName(), runner.getTeamName(), runner.getSb()};
	                } else {
	                    continue;
	                }

	                for (int j = 0; j < displayedColumns.length; j++) {
	                    if (displayedColumns[j] >= 0 && displayedColumns[j] < itemData.length) {
	                        rowData[i][j] = itemData[displayedColumns[j]];
	                    } else {
	                        // 에러익셉션
	                        System.err.println("Invalid index in displayedColumns: " + displayedColumns[j]);
	                        rowData[i][j] = null;
	                    }
	                }
	            }
	            
	            JTable table = new JTable(rowData, columnNames);
	            return table;
	        }

	        private void createHitterTrendPanel(JTabbedPane tabbedPane, String hometeam, String awayteam) throws SQLException {
	        	JPanel hitterTrendPanel = new JPanel();
	            hitterTrendPanel.setLayout(new BoxLayout(hitterTrendPanel, BoxLayout.Y_AXIS));
	            ArrayList<hitterDAO> hitterList22 = DatabaseManager.getInstance().getHitterList(2022);
		        ArrayList<hitterDAO> hitterList21 = DatabaseManager.getInstance().getHitterList(2021);
	            recommendationStrategyAnalysis analysis = new recommendationStrategyAnalysis(hometeam, awayteam);
		        HitterTrend hitterTrendMyTeam = analysis.getHitterTrendMyTeam(hitterList22, hitterList21, hometeam);
		        HitterTrend hitterTrendOpponentTeam = analysis.getHitterTrendMyTeam(hitterList22, hitterList21, awayteam);
		        
		        
	            ArrayList<analysisDAO> uptrendList1 = hitterTrendMyTeam.getUptrend();
	            ArrayList<analysisDAO> downtrendList1 = hitterTrendMyTeam.getDowntrend();
	            ArrayList<analysisDAO> uptrendList2 = hitterTrendOpponentTeam.getUptrend();
	            ArrayList<analysisDAO> downtrendList2 = hitterTrendOpponentTeam.getDowntrend();

	            DefaultTableModel uptrendTableModel1 = new DefaultTableModel() {
	                @Override
	                public boolean isCellEditable(int row, int column) {
	                    return false;
	                }
	            };
	            DefaultTableModel uptrendTableModel2 = new DefaultTableModel() {
	                @Override
	                public boolean isCellEditable(int row, int column) {
	                    return false;
	                }
	            };
	            uptrendTableModel1.addColumn("Player");
	            uptrendTableModel1.addColumn("2021");
	            uptrendTableModel1.addColumn("2022");
	            for (analysisDAO uptrendItem : uptrendList1) {
	                Object[] row = new Object[3];
	                row[0] = uptrendItem.getName();
	                row[1] = uptrendItem.getOps21();
	                row[2] = uptrendItem.getOps22();
	                uptrendTableModel1.addRow(row);
	            }
	            uptrendTableModel2.addColumn("Player");
	            uptrendTableModel2.addColumn("2021");
	            uptrendTableModel2.addColumn("2022");
	            for (analysisDAO uptrendItem : uptrendList2) {
	                Object[] row = new Object[3];
	                row[0] = uptrendItem.getName();
	                row[1] = uptrendItem.getOps21();
	                row[2] = uptrendItem.getOps22();
	                uptrendTableModel2.addRow(row);
	            }
	            DefaultTableModel downtrendTableModel1 = new DefaultTableModel() {
	                @Override
	                public boolean isCellEditable(int row, int column) {
	                    return false;
	                }
	            };
	            DefaultTableModel downtrendTableModel2 = new DefaultTableModel() {
	                @Override
	                public boolean isCellEditable(int row, int column) {
	                    return false;
	                }
	            };
	            downtrendTableModel1.addColumn("Player");
	            downtrendTableModel1.addColumn("2021");
	            downtrendTableModel1.addColumn("2022");
	            for (analysisDAO downtrendItem : downtrendList1) {
	                Object[] row = new Object[3];
	                row[0] = downtrendItem.getName();
	                row[1] = downtrendItem.getOps21();
	                row[2] = downtrendItem.getOps22();
	                downtrendTableModel1.addRow(row);
	            }
	            downtrendTableModel2.addColumn("Player");
	            downtrendTableModel2.addColumn("2021");
	            downtrendTableModel2.addColumn("2022");
	            for (analysisDAO downtrendItem : downtrendList2) {
	                Object[] row = new Object[3];
	                row[0] = downtrendItem.getName();
	                row[1] = downtrendItem.getOps21();
	                row[2] = downtrendItem.getOps22();
	                downtrendTableModel2.addRow(row);
	            }

	            JTable uptrendTable1 = new JTable(uptrendTableModel1);
	            JTable downtrendTable1 = new JTable(downtrendTableModel1);
	            JTable uptrendTable2 = new JTable(uptrendTableModel2);
	            JTable downtrendTable2 = new JTable(downtrendTableModel2);

	            JScrollPane uptrendScrollPane1 = new JScrollPane(uptrendTable1);
	            JScrollPane downtrendScrollPane1 = new JScrollPane(downtrendTable1);
	            JScrollPane uptrendScrollPane2 = new JScrollPane(uptrendTable2);
	            JScrollPane downtrendScrollPane2 = new JScrollPane(downtrendTable2);

	            Font labelFont = new Font("나눔고딕", Font.BOLD, 16);
	            JLabel uptrendLabel1 = new JLabel("● 홈 팀 상승세 타자", SwingConstants.CENTER);
	            uptrendLabel1.setFont(labelFont);
	            JLabel downtrendLabel1 = new JLabel("● 홈 팀 하락세 타자", SwingConstants.CENTER);
	            downtrendLabel1.setFont(labelFont);
	            JLabel uptrendLabel2 = new JLabel("● 어웨이 팀 상승세 타자", SwingConstants.CENTER);
	            uptrendLabel2.setFont(labelFont);
	            JLabel downtrendLabel2 = new JLabel("● 어웨이 팀 하락세 타자", SwingConstants.CENTER);
	            downtrendLabel2.setFont(labelFont);

	            hitterTrendPanel.add(Box.createVerticalGlue());
	            hitterTrendPanel.add(uptrendLabel1);
	            hitterTrendPanel.add(uptrendScrollPane1);
	            hitterTrendPanel.add(downtrendLabel1);
	            hitterTrendPanel.add(downtrendScrollPane1);
	            hitterTrendPanel.add(Box.createVerticalStrut(10));
	            hitterTrendPanel.add(uptrendLabel2);
	            hitterTrendPanel.add(uptrendScrollPane2);
	            hitterTrendPanel.add(downtrendLabel2);
	            hitterTrendPanel.add(downtrendScrollPane2);
	            hitterTrendPanel.add(Box.createVerticalGlue());

	            tabbedPane.addTab("타자 추세", hitterTrendPanel);
	        }
	        
	        private void createPitcherTrendPanel(JTabbedPane tabbedPane, String hometeam, String awayteam) throws SQLException {
	        	JPanel pitcherTrendPanel = new JPanel();
	            pitcherTrendPanel.setLayout(new BoxLayout(pitcherTrendPanel, BoxLayout.Y_AXIS));
	            ArrayList<pitcherDAO> pitcherList22 = DatabaseManager.getInstance().getPitcherList(2022);
		        ArrayList<pitcherDAO> pitcherList21 = DatabaseManager.getInstance().getPitcherList(2021);
	            recommendationStrategyAnalysis analysis = new recommendationStrategyAnalysis(hometeam, awayteam);
		        PitcherTrend pitcherTrendMyTeam = analysis.getPitcherTrendMyTeam(pitcherList22, pitcherList21, hometeam);
		        PitcherTrend pitcherTrendOpponentTeam = analysis.getPitcherTrendMyTeam(pitcherList22, pitcherList21, awayteam);
		        
		        
	            ArrayList<analysisDAO> uptrendList1 = pitcherTrendMyTeam.getUptrend();
	            ArrayList<analysisDAO> downtrendList1 = pitcherTrendMyTeam.getDowntrend();
	            ArrayList<analysisDAO> uptrendList2 = pitcherTrendOpponentTeam.getUptrend();
	            ArrayList<analysisDAO> downtrendList2 = pitcherTrendOpponentTeam.getDowntrend();

	            DefaultTableModel uptrendTableModel1 = new DefaultTableModel() {
	                @Override
	                public boolean isCellEditable(int row, int column) {
	                    return false;
	                }
	            };
	            DefaultTableModel uptrendTableModel2 = new DefaultTableModel() {
	                @Override
	                public boolean isCellEditable(int row, int column) {
	                    return false;
	                }
	            };
	            uptrendTableModel1.addColumn("Player");
	            uptrendTableModel1.addColumn("2022");
	            uptrendTableModel1.addColumn("2023");
	            for (analysisDAO uptrendItem : uptrendList1) {
	                Object[] row = new Object[3];
	                row[0] = uptrendItem.getName();
	                row[1] = uptrendItem.getWhip21();
	                row[2] = uptrendItem.getWhip22();
	                uptrendTableModel1.addRow(row);
	            }
	            uptrendTableModel2.addColumn("Player");
	            uptrendTableModel2.addColumn("2022");
	            uptrendTableModel2.addColumn("2023");
	            for (analysisDAO uptrendItem : uptrendList2) {
	                Object[] row = new Object[3];
	                row[0] = uptrendItem.getName();
	                row[1] = uptrendItem.getWhip21();
	                row[2] = uptrendItem.getWhip22();
	                uptrendTableModel2.addRow(row);
	            }
	            DefaultTableModel downtrendTableModel1 = new DefaultTableModel() {
	                @Override
	                public boolean isCellEditable(int row, int column) {
	                    return false;
	                }
	            };
	            DefaultTableModel downtrendTableModel2 = new DefaultTableModel() {
	                @Override
	                public boolean isCellEditable(int row, int column) {
	                    return false;
	                }
	            };
	            downtrendTableModel1.addColumn("Player");
	            downtrendTableModel1.addColumn("2022");
	            downtrendTableModel1.addColumn("2023");
	            for (analysisDAO downtrendItem : downtrendList1) {
	                Object[] row = new Object[3];
	                row[0] = downtrendItem.getName();
	                row[1] = downtrendItem.getWhip21();
	                row[2] = downtrendItem.getWhip22();
	                downtrendTableModel1.addRow(row);
	            }
	            downtrendTableModel2.addColumn("Player");
	            downtrendTableModel2.addColumn("2022");
	            downtrendTableModel2.addColumn("2023");
	            for (analysisDAO downtrendItem : downtrendList2) {
	                Object[] row = new Object[3];
	                row[0] = downtrendItem.getName();
	                row[1] = downtrendItem.getWhip21();
	                row[2] = downtrendItem.getWhip22();
	                downtrendTableModel2.addRow(row);
	            }

	            JTable uptrendTable1 = new JTable(uptrendTableModel1);
	            JTable downtrendTable1 = new JTable(downtrendTableModel1);
	            JTable uptrendTable2 = new JTable(uptrendTableModel2);
	            JTable downtrendTable2 = new JTable(downtrendTableModel2);

	            JScrollPane uptrendScrollPane1 = new JScrollPane(uptrendTable1);
	            JScrollPane downtrendScrollPane1 = new JScrollPane(downtrendTable1);
	            JScrollPane uptrendScrollPane2 = new JScrollPane(uptrendTable2);
	            JScrollPane downtrendScrollPane2 = new JScrollPane(downtrendTable2);

	            Font labelFont = new Font("나눔고딕", Font.BOLD, 16);
	            JLabel uptrendLabel1 = new JLabel("● 홈 팀 상승세 투수", SwingConstants.CENTER);
	            uptrendLabel1.setFont(labelFont);
	            JLabel downtrendLabel1 = new JLabel("● 홈 팀 하락세 투수", SwingConstants.CENTER);
	            downtrendLabel1.setFont(labelFont);
	            JLabel uptrendLabel2 = new JLabel("● 어웨이 팀 상승세 투수", SwingConstants.CENTER);
	            uptrendLabel2.setFont(labelFont);
	            JLabel downtrendLabel2 = new JLabel("● 어웨이 팀 하락세 투수", SwingConstants.CENTER);
	            downtrendLabel2.setFont(labelFont);

	            pitcherTrendPanel.add(Box.createVerticalGlue());
	            pitcherTrendPanel.add(uptrendLabel1);
	            pitcherTrendPanel.add(uptrendScrollPane1);
	            pitcherTrendPanel.add(downtrendLabel1);
	            pitcherTrendPanel.add(downtrendScrollPane1);
	            pitcherTrendPanel.add(Box.createVerticalStrut(10));
	            pitcherTrendPanel.add(uptrendLabel2);
	            pitcherTrendPanel.add(uptrendScrollPane2);
	            pitcherTrendPanel.add(downtrendLabel2);
	            pitcherTrendPanel.add(downtrendScrollPane2);
	            pitcherTrendPanel.add(Box.createVerticalGlue());

	            tabbedPane.addTab("투수 추세", pitcherTrendPanel);
	        }
	        private void createWeakLeftPitcherPanel(JTabbedPane tabbedPane, String homeTeam, String awayTeam) throws SQLException {
	            ArrayList<pitcherDAO> pitcherTypeList22 = DatabaseManager.getInstance().getPitcherTypeList(2022);
	            ArrayList<hitterDAO> hitterTypeList22 = DatabaseManager.getInstance().getHitterTypeList(2022);
	            recommendationStrategyAnalysis analysis = new recommendationStrategyAnalysis(homeTeam, awayTeam);
	            WeakLeftPitcher weakLeftPitcher = analysis.getWeakLeftPitchers(pitcherTypeList22, homeTeam, awayTeam);
	            WeakLeftUnderHitter weakLeftUnderHitter = analysis.getWeakLeftHitters(hitterTypeList22, homeTeam, awayTeam);
	            
	            JPanel panel = new JPanel();
	            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

	            JPanel homePanel1 = new JPanel(new BorderLayout());
	            JTable homeTable1 = new JTable(new DefaultTableModel(
	                new Object[][]{},
	                new String[]{
	                    "Name", "Team", "AVG"
	                }
	            ));
	            JPanel homePanel2 = new JPanel(new BorderLayout());
	            JTable homeTable2 = new JTable(new DefaultTableModel(
		                new Object[][]{},
		                new String[]{
		                    "Name", "Team", "AVG"
		                }
		            ));
	            JPanel homePanel3 = new JPanel(new BorderLayout());
	            JTable homeTable3 = new JTable(new DefaultTableModel(
		                new Object[][]{},
		                new String[]{
		                    "Name", "Team", "AVG"
		                }
		            ));
	            JScrollPane homeScrollPane1 = new JScrollPane(homeTable1);
	            homePanel1.add(homeScrollPane1, BorderLayout.CENTER);
	            JScrollPane homeScrollPane2 = new JScrollPane(homeTable2);
	            homePanel2.add(homeScrollPane2, BorderLayout.CENTER);
	            JScrollPane homeScrollPane3 = new JScrollPane(homeTable3);
	            homePanel3.add(homeScrollPane3, BorderLayout.CENTER);

	            JPanel awayPanel1 = new JPanel(new BorderLayout());
	            JTable awayTable1 = new JTable(new DefaultTableModel(
	                new Object[][]{},
	                new String[]{
	                    "Name", "Team", "AVG"
	                }
	            ));
	            JPanel awayPanel2 = new JPanel(new BorderLayout());
	            JTable awayTable2 = new JTable(new DefaultTableModel(
	                new Object[][]{},
	                new String[]{
	                    "Name", "Team", "AVG"
	                }
	            ));
	            JPanel awayPanel3 = new JPanel(new BorderLayout());
	            JTable awayTable3 = new JTable(new DefaultTableModel(
	                new Object[][]{},
	                new String[]{
	                    "Name", "Team", "AVG"
	                }
	            ));
	            JScrollPane awayScrollPane1 = new JScrollPane(awayTable1);
	            awayPanel1.add(awayScrollPane1, BorderLayout.CENTER);
	            JScrollPane awayScrollPane2 = new JScrollPane(awayTable2);
	            awayPanel2.add(awayScrollPane2, BorderLayout.CENTER);
	            JScrollPane awayScrollPane3 = new JScrollPane(awayTable3);
	            awayPanel3.add(awayScrollPane3, BorderLayout.CENTER);

	            for (analysisDAO dao : weakLeftPitcher.getWeakLeftPitcher_myTeam()) {
	                if (dao.getAverage() > 0.300) {
	                    ((DefaultTableModel) homeTable1.getModel()).addRow(new Object[]{
	                        dao.getName(),
	                        dao.getTeamName(),
	                        dao.getAverage()
	                    });
	                }
	            }
	            for (analysisDAO dao : weakLeftPitcher.getWeakLeftPitcher_opponentTeam()) {
	                if (dao.getAverage() > 0.300) {
	                    ((DefaultTableModel) homeTable2.getModel()).addRow(new Object[]{
	                        dao.getName(),
	                        dao.getTeamName(),
	                        dao.getAverage()
	                    });
	                }
	            }
	            for (analysisDAO dao : weakLeftUnderHitter.getWeakLeftHitter_myTeam()) {
	                if (dao.getAverage() < 0.300) {
	                    ((DefaultTableModel) homeTable3.getModel()).addRow(new Object[]{
	                        dao.getName(),
	                        dao.getTeamName(),
	                        dao.getAverage()
	                    });
	                }
	            }
	            for (analysisDAO dao : weakLeftUnderHitter.getWeakLeftHitter_opponentTeam()) {
	                if (dao.getAverage() < 0.300) {
	                    ((DefaultTableModel) awayTable1.getModel()).addRow(new Object[]{
	                        dao.getName(),
	                        dao.getTeamName(),
	                        dao.getAverage()
	                    });
	                }
	            }
	            for (analysisDAO dao : weakLeftUnderHitter.getWeakUnderHitter_myTeam()) {
	                if (dao.getAverage() < 0.300) {
	                    ((DefaultTableModel) awayTable2.getModel()).addRow(new Object[]{
	                        dao.getName(),
	                        dao.getTeamName(),
	                        dao.getAverage()
	                    });
	                }
	            }
	            for (analysisDAO dao : weakLeftUnderHitter.getWeakUnderHitter_opponentTeam()) {
	                if (dao.getAverage() < 0.300) {
	                    ((DefaultTableModel) awayTable3.getModel()).addRow(new Object[]{
	                        dao.getName(),
	                        dao.getTeamName(),
	                        dao.getAverage()
	                    });
	                }
	            }

	            Font labelFont = new Font("나눔고딕", Font.BOLD, 16);
	            JLabel homeLabel1 = new JLabel("● 홈 팀의 좌타자 상대로 약한 투수", SwingConstants.CENTER);
	            homeLabel1.setFont(labelFont);
	            JLabel homeLabel2 = new JLabel("● 어웨이 팀의 좌타자 상대로 약한 투수", SwingConstants.CENTER);
	            homeLabel2.setFont(labelFont);
	            JLabel homeLabel3 = new JLabel("● 홈 팀의 좌투수 상대로 약한 타자", SwingConstants.CENTER);
	            homeLabel3.setFont(labelFont);
	            JLabel awayLabel1 = new JLabel("● 어웨이 팀의 좌투수 상대로 약한 타자", SwingConstants.CENTER);
	            awayLabel1.setFont(labelFont);
	            JLabel awayLabel2 = new JLabel("● 홈 팀의 언더핸드 상대로 약한 타자", SwingConstants.CENTER);
	            awayLabel2.setFont(labelFont);
	            JLabel awayLabel3 = new JLabel("● 어웨이 팀의 언더핸드 상대로 약한 타자", SwingConstants.CENTER);
	            awayLabel3.setFont(labelFont);

	            panel.add(Box.createVerticalGlue());
	            panel.add(homeLabel1);
	            panel.add(homePanel1);
	            panel.add(Box.createVerticalStrut(10));
	            panel.add(homeLabel2);
	            panel.add(homePanel2);
	            panel.add(Box.createVerticalStrut(10));
	            panel.add(homeLabel3);
	            panel.add(homePanel3);
	            panel.add(Box.createVerticalStrut(10));
	            panel.add(awayLabel1);
	            panel.add(awayPanel1);
	            panel.add(Box.createVerticalStrut(10));
	            panel.add(awayLabel2);
	            panel.add(awayPanel2);
	            panel.add(Box.createVerticalStrut(10));
	            panel.add(awayLabel3);
	            panel.add(awayPanel3);
	            panel.add(Box.createVerticalGlue());

	            tabbedPane.addTab("약점 분석", panel);
	        }

	}
	
}