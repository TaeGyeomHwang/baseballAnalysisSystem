package swingEx4;

import java.sql.*;
import java.util.Date;
import java.util.ArrayList;

public class jdbcEx {
	
    private static ArrayList<playerDAO> player = new ArrayList<>();	// DB �����͸� �����ϴ� ArrayList ����
    private static ArrayList<teamDAO> team = new ArrayList<>();
    private static ArrayList<defenseDAO>[] defense = new ArrayList[5];
    private static ArrayList<hitterDAO>[] hitter = new ArrayList[5];
    private static ArrayList<pitcherDAO>[] pitcher = new ArrayList[5];
    private static ArrayList<runnerDAO>[] runner = new ArrayList[5];
    
        public static void main(String[] args) {           
        		
               Connection conn = null;	// DB���� ��Ʈ ���� ����
               PreparedStatement[] pstmt = new PreparedStatement[22];	// ���� ����, ���� ���� ����
               ResultSet[] rs = new ResultSet[22];						

               try {
	                   for (int i = 0; i < pstmt.length; i++) {	// PreparedStatement, ResultSet �迭 ����
	                       pstmt[i] = null;
	                       rs[i] = null;
	                   }
	                   
	               try {
	                       Class.forName("oracle.jdbc.driver.OracleDriver");	// jdbc ����̹�
	                       conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "scott", "tiger");	// ��Ʈ ����            

	                       String[] queries = {	// ������ �ۼ�
	                    		    "select * from player",
	                    		    "select * from record_team",
	                    		    "select * from record_defense_2022",
	                    		    "select * from record_defense_2021",
	                    		    "select * from record_defense_2020",
	                    		    "select * from record_defense_2019",
	                    		    "select * from record_defense_2018",
	                    		    "select * from record_hitter_2022",
	                    		    "select * from record_hitter_2021",
	                    		    "select * from record_hitter_2020",
	                    		    "select * from record_hitter_2019",
	                    		    "select * from record_hitter_2018",
	                    		    "select * from record_pitcher_2022",
	                    		    "select * from record_pitcher_2021",
	                    		    "select * from record_pitcher_2020",
	                    		    "select * from record_pitcher_2019",
	                    		    "select * from record_pitcher_2018",
	                    		    "select * from record_runner_2022",
	                    		    "select * from record_runner_2021",
	                    		    "select * from record_runner_2020",
	                    		    "select * from record_runner_2019",
	                    		    "select * from record_runner_2018"
	                    		};
	                       
	                       for (int i = 0; i < queries.length; i++) {	// ������ PreparedStatement, ResultSet ����
	                    	    pstmt[i] = conn.prepareStatement(queries[i]);
	                    	    rs[i] = pstmt[i].executeQuery();
	                    	}
	                       
	                       for (int i = 0; i < 22; i++) {	// �ۼ��� ������ 21�� ����
	                    	    while (rs[i].next()) {
	                    	        switch (i) {
	                    	            case 0:	// DB���� �������� �����ͼ� ArrayList�� ����
	                    	                playerDAO playerDAO = new playerDAO();
	                    	                populatePlayerDAO(playerDAO, rs[i]);
	                    	                player.add(playerDAO);
	                    	                break;
	                    	            case 1:	// DB���� �� ��� �����ͼ� ArrayList�� ����
	                    	                teamDAO teamDAO = new teamDAO();
	                    	                populateTeamDAO(teamDAO, rs[i]);
	                    	                team.add(teamDAO);
	                    	                break;
	                    	            case 2:
	                    	            case 3:
	                    	            case 4:
	                    	            case 5:
	                    	            case 6:
	                    	                if (defense[i-2] == null) {	// NullPointerException ����ó�� ���� �迭 �ʱ�ȭ
	                    	                    defense[i-2] = new ArrayList<>();
	                    	                }
	                    	                defenseDAO defenseDAO = new defenseDAO();	// DB���� ���� ��� �����ͼ� ArrayList�� ����
	                    	                populateDefenseDAO(defenseDAO, rs[i]);
	                    	                defense[i-2].add(defenseDAO);
	                    	                break;
	                    	            case 7:
	                    	            case 8:
	                    	            case 9:
	                    	            case 10:
	                    	            case 11:
	                    	                if (hitter[i-7] == null) {	// NullPointerException ����ó�� ���� �迭 �ʱ�ȭ
	                    	                    hitter[i-7] = new ArrayList<>();
	                    	                }
	                    	                hitterDAO hitterDAO = new hitterDAO();	// DB���� Ÿ�� ��� �����ͼ� ArrayList�� ����
	                    	                populateHitterDAO(hitterDAO, rs[i]);
	                    	                hitter[i-7].add(hitterDAO);
	                    	                break;
	                    	            case 12:
	                    	            case 13:
	                    	            case 14:
	                    	            case 15:
	                    	            case 16:
	                    	                if (pitcher[i-12] == null) {	// NullPointerException ����ó�� ���� �迭 �ʱ�ȭ
	                    	                    pitcher[i-12] = new ArrayList<>();
	                    	                }
	                    	                pitcherDAO pitcherDAO = new pitcherDAO();	// DB���� ���� ��� �����ͼ� ArrayList�� ����
	                    	                populatePitcherDAO(pitcherDAO, rs[i]);
	                    	                pitcher[i-12].add(pitcherDAO);
	                    	                break;
	                    	            case 17:
	                    	            case 18:
	                    	            case 19:
	                    	            case 20:
	                    	            case 21:
	                    	                if (runner[i-17] == null) {	// NullPointerException ����ó�� ���� �迭 �ʱ�ȭ
	                    	                    runner[i-17] = new ArrayList<>();
	                    	                }
	                    	                runnerDAO runnerDAO = new runnerDAO();	// DB���� �ַ� ��� �����ͼ� ArrayList�� ����
	                    	                populateRunnerDAO(runnerDAO, rs[i]);
	                    	                runner[i-17].add(runnerDAO);
	                    	                break;
	                    	        }
	                    	    }
	                    	}
	                       
	                       	// ArrayList Ȱ�� ��
	                       String ans ;
	                       playerDAO LeeMinHo = player.get(0);
	                       ans = LeeMinHo.getPhysical();
	                       System.out.println(ans);


	               }
	               catch (ClassNotFoundException e) { // Ŭ���� ���� ����ó��
	                       e.printStackTrace();
	               }catch (SQLException e) { // DB���� ����ó��
	                       e.printStackTrace();
	               }
               }finally{
	            	   for (int i = 0; i < pstmt.length; i++) {
	            		   
	            	        if (pstmt[i] != null)	// psmt ����, SQLException ����ó��
	            	        	try {pstmt[i].close();} catch (SQLException e) {
	        	            	}
	            	        
	            	        if (rs[i] != null)	// rs ����, SQLException ����ó��
	            	        	try {rs[i].close();} catch (SQLException e) {
	        	            	}
	            	    }
	            	   
	            	   if (conn != null) {	// conn ����, SQLException ����ó��
		            	    try {
		            	        conn.close();
		            	    } catch (SQLException e) {
		            	    }
	            	   }
               }
               
               for (playerDAO item : player) {
                   System.out.println(item);
               }
        }
        
        public ArrayList<playerDAO> getPlayerList() {
            return player;
        }
        
        private static void populatePlayerDAO(playerDAO dao, ResultSet rs) throws SQLException {	// �������� �ߺ��Ǵ� setter ����
            dao.setBackNumber(rs.getInt("UNIFORM_NUMBER"));
            dao.setName(rs.getString("PLAYER_NAME"));
            dao.setTeamName(rs.getString("TEAM_NAME"));
            dao.setPosition(rs.getString("POSITION"));
            dao.setBirthdate(rs.getString("BIRTHDATE")); //date Ÿ���� stringŸ������ ����ȯ �� ������
            dao.setPhysical(rs.getString("PHYSICAL"));
            dao.setCareer(rs.getString("CAREER"));
        }
        
        private static void populateTeamDAO(teamDAO dao, ResultSet rs) throws SQLException {	// �� ��� �ߺ��Ǵ� setter ����
            dao.setRank(rs.getInt("RANK"));
            dao.setTeamName(rs.getString("TEAM_NAME"));
            dao.setG(rs.getInt("G"));
            dao.setW(rs.getInt("W"));
            dao.setL(rs.getInt("L"));
            dao.setWpct(rs.getFloat("WPCT"));
        }
        
        private static void populateDefenseDAO(defenseDAO dao, ResultSet rs) throws SQLException {	// ���� ��� �ߺ��Ǵ� setter ����
            dao.setName(rs.getString("PLAYER_NAME"));
            dao.setTeamName(rs.getString("TEAM_NAME"));
            dao.setPosition(rs.getString("POS"));
            dao.setG(rs.getInt("G"));
            dao.setGs(rs.getInt("GS"));
            dao.setIp(rs.getFloat("IP"));
            dao.setE(rs.getInt("E"));
            dao.setPko(rs.getInt("PKO"));
            dao.setPo(rs.getInt("PO"));
            dao.setA(rs.getInt("A"));
            dao.setDp(rs.getInt("DP"));
            dao.setFpct(rs.getFloat("FPCT"));
            dao.setPb(rs.getInt("PB"));
            dao.setSb(rs.getInt("SB"));
            dao.setCs(rs.getInt("CS"));
            dao.setCsPer(rs.getFloat("CS_PER"));
        }
        
        private static void populateHitterDAO(hitterDAO dao, ResultSet rs) throws SQLException {	// Ÿ�� ��� �ߺ��Ǵ� setter ����
            dao.setName(rs.getString("PLAYER_NAME"));
            dao.setTeamName(rs.getString("TEAM_NAME"));
            dao.setAverage(rs.getFloat("AVG"));
            dao.setG(rs.getInt("G"));
            dao.setPa(rs.getInt("PA"));
            dao.setAb(rs.getInt("AB"));
            dao.setR(rs.getInt("R"));
            dao.setH(rs.getInt("H"));
            dao.setDoubleBase(rs.getInt("DOUBLE"));
            dao.setTripleBase(rs.getInt("TRIPLE"));
            dao.setHr(rs.getInt("HR"));
            dao.setTb(rs.getInt("TB"));
            dao.setRbi(rs.getInt("RBI"));
            dao.setSac(rs.getInt("SAC"));
            dao.setSf(rs.getInt("SF"));
            dao.setBb(rs.getInt("BB"));
            dao.setIbb(rs.getInt("IBB"));
            dao.setHbp(rs.getInt("HBP"));
            dao.setSo(rs.getInt("SO"));
            dao.setGdp(rs.getInt("GDP"));
            dao.setSlg(rs.getFloat("SLG"));
            dao.setObp(rs.getFloat("OBP"));
            dao.setOps(rs.getFloat("OPS"));
            dao.setMh(rs.getInt("MH"));
            dao.setRisp(rs.getFloat("RISP"));
            dao.setPhba(rs.getInt("PH_BA"));
            dao.setXbh(rs.getInt("XBH"));
            dao.setGo(rs.getInt("GO"));
            dao.setAo(rs.getInt("AO"));
            dao.setGoao(rs.getFloat("GO_AO"));
            dao.setGwrbi(rs.getInt("GW_RBI"));
            dao.setBbk(rs.getFloat("BB_K"));
            dao.setPpa(rs.getFloat("P_PA"));
            dao.setIsop(rs.getFloat("ISOP"));
            dao.setXr(rs.getFloat("XR"));
            dao.setGpa(rs.getFloat("GPA"));
        }
        
        private static void populatePitcherDAO(pitcherDAO dao, ResultSet rs) throws SQLException {	// ���� ��� �ߺ��Ǵ� setter ����
            dao.setName(rs.getString("PLAYER_NAME"));
            dao.setTeamName(rs.getString("TEAM_NAME"));
            dao.setEra(rs.getFloat("ERA"));
            dao.setG(rs.getInt("G"));
            dao.setW(rs.getInt("W"));
            dao.setL(rs.getInt("L"));
            dao.setSv(rs.getInt("SV"));
            dao.setHld(rs.getInt("HLD"));
            dao.setWpct(rs.getFloat("WPCT"));
            dao.setIp(rs.getFloat("IP"));
            dao.setH(rs.getInt("H"));
            dao.setHr(rs.getInt("HR"));
            dao.setBb(rs.getInt("BB"));
            dao.setHbp(rs.getInt("HBP"));
            dao.setSo(rs.getInt("SO"));
            dao.setR(rs.getInt("R"));
            dao.setEr(rs.getInt("ER"));
            dao.setWhip(rs.getFloat("WHIP"));
            dao.setCg(rs.getInt("CG"));
            dao.setSho(rs.getInt("SHO"));
            dao.setQs(rs.getInt("QS"));
            dao.setBsv(rs.getInt("BSV"));
            dao.setTbf(rs.getInt("TBF"));
            dao.setNp(rs.getInt("NP"));
            dao.setAvg(rs.getFloat("AVG"));
            dao.setDoubleBase(rs.getInt("DOUBLE"));
            dao.setTripleBase(rs.getInt("TRIPLE"));
            dao.setSac(rs.getInt("SAC"));
            dao.setSf(rs.getInt("SF"));
            dao.setIbb(rs.getInt("IBB"));
            dao.setWp(rs.getInt("WP"));
            dao.setBk(rs.getInt("BK"));
            dao.setGs(rs.getInt("GS"));
            dao.setWgs(rs.getInt("WGS"));
            dao.setWgr(rs.getInt("WGR"));
            dao.setGf(rs.getInt("GF"));
            dao.setSvo(rs.getInt("SVO"));
            dao.setTs(rs.getInt("TS"));
            dao.setGdp(rs.getInt("GDP"));
            dao.setGo(rs.getInt("GO"));
            dao.setAo(rs.getInt("AO"));
            dao.setGoao(rs.getFloat("GO_AO"));
            dao.setBabip(rs.getFloat("BABIP"));
            dao.setPg(rs.getFloat("P_G"));
            dao.setPip(rs.getFloat("P_IP"));
            dao.setK9(rs.getFloat("K_9"));
            dao.setBb9(rs.getFloat("BB_9"));
            dao.setKbb(rs.getFloat("K_BB"));
            dao.setObp(rs.getFloat("OBP"));
            dao.setSlg(rs.getFloat("SLG"));
            dao.setOps(rs.getFloat("OPS"));
        }
        
        private static void populateRunnerDAO(runnerDAO dao, ResultSet rs) throws SQLException {	// �ַ� ��� �ߺ��Ǵ� setter ����
            dao.setName(rs.getString("PLAYER_NAME"));
            dao.setTeamName(rs.getString("TEAM_NAME"));
            dao.setG(rs.getInt("G"));
            dao.setSba(rs.getInt("SBA"));
            dao.setSb(rs.getInt("SB"));
            dao.setCs(rs.getInt("CS"));
            dao.setSbPer(rs.getFloat("SB_PER"));
            dao.setOob(rs.getInt("OOB"));
            dao.setPko(rs.getInt("PKO"));
        }
}