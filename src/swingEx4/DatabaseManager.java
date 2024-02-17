package swingEx4;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
	
	/** DB연결 상수 선언*/
    private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String USERNAME = "scott";
    private static final String PASSWORD = "TIGER";
    
    /** 인스턴스, 커넥션 변수 선언*/
    private static DatabaseManager instance = null;
    private Connection conn = null;
    
    public static DatabaseManager create() throws SQLException {
        return new DatabaseManager();
    }
    
    /** JDBC 드라이버와 포트 설정 */
    DatabaseManager() {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    
    /** 어플리케이션 전체에서 공유하는 DatabaseManager 클래스의 인스턴스 하나만 생성 */
    public static synchronized DatabaseManager getInstance() {	//	synchronized 통해 인스턴스 생성이 스레드로부터 이루어짐 >> 멀티스레드로 동작해서 경합상태 해결
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }
    
    /** connection 변수가 null인 경우 다시 연결시키는 메소드*/
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
    }
    
    /** 연결 종료 메소드*/
    public static void close(Connection conn, PreparedStatement psmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (psmt != null) {
                psmt.close();
            }
            
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    
    public void updatePlayerCondition(String name, String condition, String position) throws SQLException {
        try{
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement statement = connection.prepareStatement("UPDATE player SET CONDITION = ? WHERE PLAYER_NAME = ? AND POSITION = ?");
            
            statement.setString(1, condition);
            statement.setString(2, name);
            statement.setString(3, position);
            statement.executeUpdate();
            
            statement.close();
            connection.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    //	DB에 분석한 선수 명단 저장
    public void savePlayerList(List<Player> playerList) {
        try {
        	// 데이터베이스에 연결
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            
            //  statement 만들기
            PreparedStatement statement = connection.prepareStatement("INSERT INTO ANALYZED_PLAYERS (back_number, player_name, position, situation) VALUES (?, ?, ?,?)");
            
            // 각 플레이어를 데이터베이스에 삽입
            for (Player player : playerList) {
                statement.setInt(1, player.getBackNumber());
                statement.setString(2, player.getPlayerName());
                statement.setString(3, player.getPosition());
                statement.setString(4, player.getSituation());
                statement.executeUpdate();
            }
            
            // statement 와 connection 닫기
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**  쿼리문 전송 & DB에 접근해서 ArrayList에 데이터 저장하는 메소드  */
    public ArrayList<playerDAO> getPlayerList() throws SQLException { // 선수정보 데이터
    	
        ArrayList<playerDAO> players = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
        	if (conn == null || conn.isClosed()) { // connection이 null이거나 연결이 끊어진 경우
                conn = getConnection(); // getConnection() 메소드를 이용하여 연결 객체를 다시 생성
            }
            String query = "select * from player";
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();

            while (rs.next()) {
            	playerDAO playerDAO = new playerDAO();
                populatePlayerDAO(playerDAO, rs);
                players.add(playerDAO);
            }
        } finally {
            close(conn, pstmt, rs);
        }

        return players;
    }
    
    public ArrayList<teamDAO> getTeamList() throws SQLException {	// 팀 기록 데이터
        ArrayList<teamDAO> teams = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
        	if (conn == null || conn.isClosed()) { // connection이 null이거나 연결이 끊어진 경우
                conn = getConnection(); // getConnection() 메소드를 이용하여 연결 객체를 다시 생성
            }
            String query = "select * from record_team";
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();

            while (rs.next()) {
            	teamDAO teamDAO = new teamDAO();
                populateTeamDAO(teamDAO, rs);
                teams.add(teamDAO);
            }
        } finally {
        	close(conn, pstmt, rs);
        }
        
        return teams;
    }

    public ArrayList<defenseDAO> getDefenseList(int year) throws SQLException {
        ArrayList<defenseDAO> defense = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            }

            String query = "select * from record_defense_" + year;
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                defenseDAO defenseDAO = new defenseDAO();
                populateDefenseDAO(defenseDAO, rs);
                defense.add(defenseDAO);
            }
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                }
            }

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
        return defense;
    }

    public ArrayList<hitterDAO> getHitterList(int year) throws SQLException {	//	전체 타자기록 데이터 가져오는 코드
        ArrayList<hitterDAO> hitter = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            }

            String query = "select * from record_hitter_" + year;
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                hitterDAO hitterDAO = new hitterDAO();
                populateHitterDAO(hitterDAO, rs);
                hitter.add(hitterDAO);
            }
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                }
            }

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
        return hitter;
    }
    
    public ArrayList<hitterDAO> getHitterOpponentList(int year) throws SQLException {	// 타자 상대팀별 데이터 가져오는 코드
        ArrayList<hitterDAO> hitter = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            }

            String query = "select * from record_hitter_opponent_" + year;
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                hitterDAO hitterDAO = new hitterDAO();
                populateHitterOpponentDAO(hitterDAO, rs);
                hitter.add(hitterDAO);
            }
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                }
            }

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
        return hitter;
    }
    
    public ArrayList<hitterDAO> getHitterTypeList(int year) throws SQLException {	// 타자 투수유형별 데이터 가져오는 코드
        ArrayList<hitterDAO> hitter = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            }

            String query = "select * from record_hitter_type_" + year;
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                hitterDAO hitterDAO = new hitterDAO();
                populateHitterTypeDAO(hitterDAO, rs);
                hitter.add(hitterDAO);
            }
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                }
            }

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
        return hitter;
    }
    
    public ArrayList<pitcherDAO> getPitcherList(int year) throws SQLException {
        ArrayList<pitcherDAO> pitcher = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            }

            String query = "select * from record_pitcher_" + year;
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                pitcherDAO pitcherDAO = new pitcherDAO();
                populatePitcherDAO(pitcherDAO, rs);
                pitcher.add(pitcherDAO);
            }
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                }
            }

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
        return pitcher;
    }
    
    public ArrayList<pitcherDAO> getPitcherOpponentList(int year) throws SQLException {	//	투수 상대팀별 기록
        ArrayList<pitcherDAO> pitcher = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            }

            String query = "select * from record_pitcher_opponent_" + year;
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                pitcherDAO pitcherDAO = new pitcherDAO();
                populatePitcherOpponentDAO(pitcherDAO, rs);
                pitcher.add(pitcherDAO);
            }
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                }
            }

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
        return pitcher;
    }
    
    public ArrayList<pitcherDAO> getPitcherTypeList(int year) throws SQLException {	//	투수 타자유형별 기록
        ArrayList<pitcherDAO> pitcher = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            }

            String query = "select * from record_pitcher_type_" + year;
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                pitcherDAO pitcherDAO = new pitcherDAO();
                populatePitcherTypeDAO(pitcherDAO, rs);
                pitcher.add(pitcherDAO);
            }
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                }
            }

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
        return pitcher;
    }
    
    public ArrayList<runnerDAO> getRunnerList(int year) throws SQLException {
        ArrayList<runnerDAO> runner = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            }

            String query = "select * from record_runner_" + year;
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                runnerDAO runnerDAO = new runnerDAO();
                populateRunnerDAO(runnerDAO, rs);
                runner.add(runnerDAO);
            }
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                }
            }

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
        return runner;
    }
    /** 중복되는 setter들 정리*/
    private static void populatePlayerDAO(playerDAO dao, ResultSet rs) throws SQLException {	// 선수정보 중복되는 setter 정리
        dao.setBackNumber(rs.getInt("UNIFORM_NUMBER"));
        dao.setName(rs.getString("PLAYER_NAME"));
        dao.setTeamName(rs.getString("TEAM_NAME"));
        dao.setPosition(rs.getString("POSITION"));
        dao.setBirthdate(rs.getString("BIRTHDATE")); //date 타입을 string타입으로 형변환 후 가져옴
        dao.setPhysical(rs.getString("PHYSICAL"));
        dao.setCareer(rs.getString("CAREER"));
        dao.setCondition(rs.getString("CONDITION"));
    }
    private static void populateTeamDAO(teamDAO dao, ResultSet rs) throws SQLException {	// 팀 기록 중복되는 setter 정리
        dao.setRank(rs.getInt("RANK"));
        dao.setTeamName(rs.getString("TEAM_NAME"));
        dao.setG(rs.getInt("G"));
        dao.setW(rs.getInt("W"));
        dao.setL(rs.getInt("L"));
        dao.setWpct(rs.getFloat("WPCT"));
    }
    private static void populateDefenseDAO(defenseDAO dao, ResultSet rs) throws SQLException {	// 수비 기록 중복되는 setter 정리
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
    private static void populateHitterDAO(hitterDAO dao, ResultSet rs) throws SQLException {	// 타자 기록 중복되는 setter 정리
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
        dao.setPhba(rs.getFloat("PH_BA"));
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
    private static void populateHitterOpponentDAO(hitterDAO dao, ResultSet rs) throws SQLException {	// 타자 상대팀별 기록 setter
    	dao.setName(rs.getString("PLAYER_NAME"));
        dao.setTeamName(rs.getString("TEAM_NAME"));
        dao.setAverage(rs.getFloat("AVG"));
        dao.setAb(rs.getInt("AB"));
        dao.setH(rs.getInt("H"));
        dao.setDoubleBase(rs.getInt("DOUBLE"));
        dao.setTripleBase(rs.getInt("TRIPLE"));
        dao.setHr(rs.getInt("HR"));
        dao.setRbi(rs.getInt("RBI"));
        dao.setBb(rs.getInt("BB"));
        dao.setHbp(rs.getInt("HBP"));
        dao.setSo(rs.getInt("SO"));
        dao.setGdp(rs.getInt("GDP"));
        dao.setOpponent(rs.getString("OPPONENT"));
    }
    private static void populateHitterTypeDAO(hitterDAO dao, ResultSet rs) throws SQLException {	//	타자 투수유형별 기록 setter
    	dao.setName(rs.getString("PLAYER_NAME"));
        dao.setTeamName(rs.getString("TEAM_NAME"));
        dao.setAverage(rs.getFloat("AVG"));
        dao.setAb(rs.getInt("AB"));
        dao.setH(rs.getInt("H"));
        dao.setDoubleBase(rs.getInt("DOUBLE"));
        dao.setTripleBase(rs.getInt("TRIPLE"));
        dao.setHr(rs.getInt("HR"));
        dao.setRbi(rs.getInt("RBI"));
        dao.setBb(rs.getInt("BB"));
        dao.setHbp(rs.getInt("HBP"));
        dao.setSo(rs.getInt("SO"));
        dao.setGdp(rs.getInt("GDP"));
        dao.setPitcherType(rs.getString("PITCHER_TYPE"));
    }
    private static void populatePitcherDAO(pitcherDAO dao, ResultSet rs) throws SQLException {	// 투수 기록 중복되는 setter 정리
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
    private static void populatePitcherOpponentDAO(pitcherDAO dao, ResultSet rs) throws SQLException {	//	투수 상대팀별 기록 setter
        dao.setName(rs.getString("PLAYER_NAME"));
        dao.setTeamName(rs.getString("TEAM_NAME"));
        dao.setH(rs.getInt("H"));
        dao.setDoubleBase(rs.getInt("DOUBLE"));
        dao.setTripleBase(rs.getInt("TRIPLE"));
        dao.setHr(rs.getInt("HR"));
        dao.setBb(rs.getInt("BB"));
        dao.setHbp(rs.getInt("HBP"));
        dao.setSo(rs.getInt("SO"));
        dao.setWp(rs.getInt("WP"));
        dao.setBk(rs.getInt("BK"));
        dao.setAvg(rs.getFloat("AVG"));
        dao.setOpponent(rs.getString("OPPONENT"));
    }
    private static void populatePitcherTypeDAO(pitcherDAO dao, ResultSet rs) throws SQLException {	//	투수 타자유형별 기록 setter
        dao.setName(rs.getString("PLAYER_NAME"));
        dao.setTeamName(rs.getString("TEAM_NAME"));
        dao.setH(rs.getInt("H"));
        dao.setDoubleBase(rs.getInt("DOUBLE"));
        dao.setTripleBase(rs.getInt("TRIPLE"));
        dao.setHr(rs.getInt("HR"));
        dao.setBb(rs.getInt("BB"));
        dao.setHbp(rs.getInt("HBP"));
        dao.setSo(rs.getInt("SO"));
        dao.setWp(rs.getInt("WP"));
        dao.setBk(rs.getInt("BK"));
        dao.setAvg(rs.getFloat("AVG"));
        dao.setHitterType(rs.getString("HITTER_TYPE"));
    }
    private static void populateRunnerDAO(runnerDAO dao, ResultSet rs) throws SQLException {	// 주루 기록 중복되는 setter 정리
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
