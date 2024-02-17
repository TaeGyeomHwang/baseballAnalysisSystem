package swingEx4;

import java.sql.SQLException;

import java.util.*;
import java.util.stream.Collectors;


// 추천전략 메소드
public class recommendationStrategyAnalysis {
	DatabaseManager dbmanager = new DatabaseManager();
	
	private ArrayList<analysisDAO> hitterUptrend_myTeam = new ArrayList<>();
    private ArrayList<analysisDAO> hitterDowntrend_myTeam = new ArrayList<>();
    
	public recommendationStrategyAnalysis(String myTeam, String opponentTeam) throws SQLException {
		
		
		ArrayList<playerDAO> playerList = DatabaseManager.getInstance().getPlayerList();			// 선수정보	
        ArrayList<teamDAO> teamList = DatabaseManager.getInstance().getTeamList();					// 22 팀기록
        
        // 선발명단 불러옴
		ArrayList <String> lineUpRight_myTeam = new rosterAnalysis().aList(myTeam, opponentTeam, "우투수");		// 우투수상대 홈팀 선발타자 라인업
		ArrayList <String> lineUpLeft_myTeam = new rosterAnalysis().aList(myTeam, opponentTeam, "좌투수");			// 우투수상대 홈팀 선발타자 라인업
		ArrayList <String> lineUpUnder_myTeam = new rosterAnalysis().aList(myTeam, opponentTeam, "언더");			// 우투수상대 홈팀 선발타자 라인업
		ArrayList <String> lineUpRight_opponentTeam = new rosterAnalysis().aList(opponentTeam, myTeam, "우투수");	// 우투수상대 상대팀 선발타자 라인업
		ArrayList <String> lineUpLeft_opponentTeam = new rosterAnalysis().aList(opponentTeam, myTeam, "좌투수");	// 우투수상대 상대팀 선발타자 라인업
		ArrayList <String> lineUpUnder_opponentTeam = new rosterAnalysis().aList(opponentTeam, myTeam, "언더");	// 우투수상대 상대팀 선발타자 라인업

        
        ArrayList<hitterDAO> hitterList22 = DatabaseManager.getInstance().getHitterList(2022);		// 22 타자
        ArrayList<hitterDAO> hitterList21 = DatabaseManager.getInstance().getHitterList(2021);		// 21 타자
        ArrayList<hitterDAO> hitterList20 = DatabaseManager.getInstance().getHitterList(2020);		// 20 타자
        ArrayList<hitterDAO> hitterList19 = DatabaseManager.getInstance().getHitterList(2019);		// 19 타자
        ArrayList<hitterDAO> hitterList18 = DatabaseManager.getInstance().getHitterList(2018);		// 18 타자
        
        ArrayList<pitcherDAO> pitcherList22= DatabaseManager.getInstance().getPitcherList(2022);		// 22 투수	
        ArrayList<pitcherDAO> pitcherList21 = DatabaseManager.getInstance().getPitcherList(2021);		// 21 투수
        ArrayList<pitcherDAO> pitcherList20 = DatabaseManager.getInstance().getPitcherList(2020);		// 20 투수
        ArrayList<pitcherDAO> pitcherList19 = DatabaseManager.getInstance().getPitcherList(2019);		// 19 투수
        ArrayList<pitcherDAO> pitcherList18 = DatabaseManager.getInstance().getPitcherList(2018);		// 18 투수
        
        ArrayList<hitterDAO> hitterOpponentList22 = DatabaseManager.getInstance().getHitterOpponentList(2022);	// 22 상대팀별 타자		
        ArrayList<hitterDAO> hitterTypeList22 = DatabaseManager.getInstance().getHitterTypeList(2022);			// 22 유형별 타자
        ArrayList<pitcherDAO> pitcherOpponentList22 = DatabaseManager.getInstance().getPitcherOpponentList(2022);	// 22 상대팀별 투수	
        ArrayList<pitcherDAO> pitcherTypeList22 = DatabaseManager.getInstance().getPitcherTypeList(2022);		// 22 유형별 투수
        
        ArrayList<defenseDAO> defenseList22 = DatabaseManager.getInstance().getDefenseList(2022);		// 22 수비
        ArrayList<runnerDAO> runnerList22 = DatabaseManager.getInstance().getRunnerList(2022);		// 22 주루
        
        

        
        /** 양측 팀 다승, 평균자책점, 탈삼진, 세이브, 타율, 타점, 홈런, 도루, OPS, XR, WHIP, GPA 1위 ~ 5위 */
        
        ArrayList <analysisDAO> rankW_myTeam = new ArrayList <analysisDAO>();			// 우리팀
        ArrayList <analysisDAO> rankEra_myTeam = new ArrayList <analysisDAO>();
        ArrayList <analysisDAO> rankSo_myTeam = new ArrayList <analysisDAO>();
        ArrayList <analysisDAO> rankSv_myTeam = new ArrayList <analysisDAO>();
        ArrayList <analysisDAO> rankAvg_myTeam = new ArrayList <analysisDAO>();
        ArrayList <analysisDAO> rankRbi_myTeam = new ArrayList <analysisDAO>();
        ArrayList <analysisDAO> rankHr_myTeam = new ArrayList <analysisDAO>();
        ArrayList <analysisDAO> rankSb_myTeam = new ArrayList <analysisDAO>();
        ArrayList <analysisDAO> rankSbPer_myTeam = new ArrayList <analysisDAO>();
        ArrayList <analysisDAO> rankOps_myTeam = new ArrayList <analysisDAO>();
        ArrayList <analysisDAO> rankXr_myTeam = new ArrayList <analysisDAO>();
        ArrayList <analysisDAO> rankWhip_myTeam = new ArrayList <analysisDAO>();
        ArrayList <analysisDAO> rankGpa_myTeam = new ArrayList <analysisDAO>();
        ArrayList <analysisDAO> rankPhba_myTeam = new ArrayList <analysisDAO>();	// 대타타율

        
        ArrayList <analysisDAO> rankW_opponentTeam = new ArrayList <analysisDAO>();		// 상대팀	
        ArrayList <analysisDAO> rankEra_opponentTeam = new ArrayList <analysisDAO>();
        ArrayList <analysisDAO> rankSo_opponentTeam = new ArrayList <analysisDAO>();
        ArrayList <analysisDAO> rankSv_opponentTeam = new ArrayList <analysisDAO>();
        ArrayList <analysisDAO> rankAvg_opponentTeam = new ArrayList <analysisDAO>();
        ArrayList <analysisDAO> rankRbi_opponentTeam = new ArrayList <analysisDAO>();
        ArrayList <analysisDAO> rankHr_opponentTeam = new ArrayList <analysisDAO>();
        ArrayList <analysisDAO> rankSb_opponentTeam = new ArrayList <analysisDAO>();
        ArrayList <analysisDAO> rankSbPer_opponentTeam = new ArrayList <analysisDAO>();
        ArrayList <analysisDAO> rankOps_opponentTeam = new ArrayList <analysisDAO>();
        ArrayList <analysisDAO> rankXr_opponentTeam = new ArrayList <analysisDAO>();
        ArrayList <analysisDAO> rankWhip_opponentTeam = new ArrayList <analysisDAO>();
        ArrayList <analysisDAO> rankGpa_opponentTeam = new ArrayList <analysisDAO>();
        ArrayList <analysisDAO> rankPhba_opponentTeam = new ArrayList <analysisDAO>();	// 대타타율

        ArrayList <analysisDAO> WeakLeftHand_myTeam = new ArrayList <analysisDAO>();	// 좌투수상대 약한 타자
        ArrayList <analysisDAO> WeakUnderHand_myTeam = new ArrayList <analysisDAO>();	// 언더상대 약한 타자
        ArrayList <analysisDAO> WeakLeftHitter_myTeam = new ArrayList <analysisDAO>();	// 좌타자상대 약한 투수
        ArrayList <analysisDAO> rankCsPer_myTeam = new ArrayList<analysisDAO>();		// 우리팀 도루저지율 
        ArrayList <analysisDAO> rankCsPer_opponentTeam = new ArrayList<analysisDAO>();	// 상대팀 도루저지율
        ArrayList <analysisDAO> teamCompare_myTeam = new ArrayList <analysisDAO>();			// 우리팀 팀기록
        ArrayList <analysisDAO> teamCompare_opponentTeam = new ArrayList <analysisDAO>();	// 상대팀 팀기록
        
        ArrayList <analysisDAO> pitcherUptrend_myTeam = new ArrayList<analysisDAO>();		// 우리팀 상승세 투수 
        ArrayList <analysisDAO> pitcherDowntrend_myTeam = new ArrayList<analysisDAO>();		// 우리팀 하락세 투수
        ArrayList <analysisDAO> pitcherUptrend_opponentTeam = new ArrayList<analysisDAO>();		// 상대팀 상승세 투수 
        ArrayList <analysisDAO> pitcherDowntrend_opponentTeam = new ArrayList<analysisDAO>();	// 상대팀 하락세 투수

        
        ArrayList <analysisDAO> fpctAvgLeft_myTeam = new ArrayList <analysisDAO>();			// 우리팀 좌투상대 중앙수비능력(평균수비율)
        ArrayList <analysisDAO> fpctAvgRight_myTeam = new ArrayList <analysisDAO>();		// 우리팀 우투상대 중앙수비능력(평균수비율)
        ArrayList <analysisDAO> fpctAvgUnder_myTeam = new ArrayList <analysisDAO>();		// 우리팀 언더상대 중앙수비능력(평균수비율)
        ArrayList <analysisDAO> fpctAvgLeft_opponentTeam = new ArrayList <analysisDAO>();	// 상대팀 좌투상대 중앙수비능력(평균수비율)
        ArrayList <analysisDAO> fpctAvgRight_opponentTeam = new ArrayList <analysisDAO>();	// 상대팀 우투상대 중앙수비능력(평균수비율)
        ArrayList <analysisDAO> fpctAvgUnder_opponentTeam = new ArrayList <analysisDAO>();	// 상대팀 언더상대 중앙수비능력(평균수비율)

        
        // 도루저지율
        for(int i = 0; i < defenseList22.size() ; i++) {
 	    	defenseDAO defense = defenseList22.get(i);
 	    	if (defense.getTeamName().equals(myTeam)) {
 	    		try {
 	    			analysisDAO dao = new analysisDAO();
 	    			
 	    			dao.setName(defense.getName());
 	    			dao.setCsPer(defense.getCsPer());
 	    			
 	    			rankCsPer_myTeam.add(dao);
 	    		}
     	    	catch(NullPointerException e) { 
     	    	}
        	}
 	    	if (defense.getTeamName().equals(opponentTeam)) {
 	    		try {
 	    			analysisDAO dao = new analysisDAO();
 	    			
 	    			dao.setName(defense.getName());
 	    			dao.setCsPer(defense.getCsPer());
 	    			
 	    			rankCsPer_opponentTeam.add(dao);
 	    		}
     	    	catch(NullPointerException e) { 
     	    	}
        	}
 	    }
        //도루
        for(int i = 0; i < runnerList22.size() ; i++) {
 	    	runnerDAO run = runnerList22.get(i);
 	    	if (run.getTeamName().equals(myTeam) && run.getG() > 30) {
 	    		try {
 	    			analysisDAO dao = new analysisDAO();
 	    			
 	    			dao.setName(run.getName());
 	    			dao.setSb(run.getSb());
 	    			dao.setSbPer(run.getSbPer());	

 	    	    	rankSb_myTeam.add(dao);
 	    	    	rankSbPer_myTeam.add(dao);

 	    		}
     	    	catch(NullPointerException e) { 
     	    	}
        	}
 	    	if (run.getTeamName().equals(opponentTeam) && run.getG() > 30) {
 	    		try {
 	    			analysisDAO dao = new analysisDAO();
 	    			
 	    			dao.setName(run.getName());
 	    			dao.setSb(run.getSb());
 	    			dao.setSbPer(run.getSbPer());	

 	    	    	rankSb_opponentTeam.add(dao);
 	    	    	rankSbPer_opponentTeam.add(dao);

 	    		}
     	    	catch(NullPointerException e) { 
     	    	}
        	}
        }
        
    	// 타자기록 모음
        for(int i = 0; i < hitterList22.size() ; i++) {
 	    	hitterDAO hitter = hitterList22.get(i);
 	    	if (hitter.getTeamName().equals(myTeam) && hitter.getG() > 30) {
 	    		try {
 	    			analysisDAO dao = new analysisDAO();
 	    			
 	    			dao.setName(hitter.getName());
 	    			dao.setAverage(hitter.getAverage());
 	    			dao.setRbi(hitter.getRbi());
 	    			dao.setHr(hitter.getHr());
 	    			dao.setOps(hitter.getOps());
 	    			dao.setXr(hitter.getXr());
 	    			dao.setGpa(hitter.getGpa());
 	    			dao.setPhba(hitter.getPhba());	
 	    			
 	    			rankAvg_myTeam.add(dao);
 	    			rankRbi_myTeam.add(dao);
 	    			rankHr_myTeam.add(dao);
 	    			rankOps_myTeam.add(dao);
 	    			rankXr_myTeam.add(dao);
 	    			rankGpa_myTeam.add(dao);
 	    			rankPhba_myTeam.add(dao);
 	    		}
     	    	catch(NullPointerException e) { 
     	    	}
        	}
 	    	if (hitter.getTeamName().equals(opponentTeam) && hitter.getG() > 30) {
 	    		try {
 	    			analysisDAO dao = new analysisDAO();
 	    			
 	    			dao.setName(hitter.getName());
 	    			dao.setAverage(hitter.getAverage());
 	    			dao.setRbi(hitter.getRbi());
 	    			dao.setHr(hitter.getHr());
 	    			dao.setOps(hitter.getOps());
 	    			dao.setXr(hitter.getXr());
 	    			dao.setGpa(hitter.getGpa());
 	    			dao.setPhba(hitter.getPhba());	
 	    			
 	    			rankAvg_opponentTeam.add(dao);
 	    			rankRbi_opponentTeam.add(dao);
 	    			rankHr_opponentTeam.add(dao);
 	    			rankOps_opponentTeam.add(dao);
 	    			rankXr_opponentTeam.add(dao);
 	    			rankGpa_opponentTeam.add(dao);
 	    			rankPhba_opponentTeam.add(dao);
 	    		}
     	    	catch(NullPointerException e) { 
     	    	}
        	}
 	    }
        
        
        /** 선발 라인업을 들고와서 중앙 수비능력을 가림 -> 선발 라인업이 우투상대, 좌투상대, 언더상대 세가지로 나올 수 있으므로 이 역시 세가지. */
        
        // 우투상대 선발 라인업 중앙 수비능력
        for(int k = 0; k < defenseList22.size(); k++) {
			defenseDAO def = defenseList22.get(k);
	   		if (def.getTeamName().equals(myTeam)){
	   			
	   			try {
	   				for(int j = 0; j < lineUpRight_myTeam.size(); j++) {
	   			   		String dao1 = lineUpRight_myTeam.get(j);
		   			
		   				if (dao1.equals(def.getName()) && def.getPosition().equals("유격수")) {	
		   					analysisDAO dao = new analysisDAO();
		 	    			
		 	    			dao.setName(def.getName());
		 	    			dao.setPosition(def.getPosition());
		 	    			dao.setFpct(def.getFpct());
		 	    			
		 	    			fpctAvgRight_myTeam.add(dao);
						}
		   				if (dao1.equals(def.getName()) && def.getPosition().equals("2루수")) {	
		   					analysisDAO dao = new analysisDAO();
		 	    			
		 	    			dao.setName(def.getName());
		 	    			dao.setPosition(def.getPosition());
		 	    			dao.setFpct(def.getFpct());
		 	    			
		 	    			fpctAvgRight_myTeam.add(dao);
						}
		   				if (dao1.equals(def.getName()) && def.getPosition().equals("중견수")) {	
		   					analysisDAO dao = new analysisDAO();
		 	    			
		 	    			dao.setName(def.getName());
		 	    			dao.setPosition(def.getPosition());
		 	    			dao.setFpct(def.getFpct());
		 	    			
		 	    			fpctAvgRight_myTeam.add(dao);
						}
				}
	   			}catch(NullPointerException e) { 
	    		}
	   		}
	   		if (def.getTeamName().equals(opponentTeam)){
	   			
	   			try {
	   				for(int j = 0; j < lineUpRight_opponentTeam.size(); j++) {
	   			   		String dao1 = lineUpRight_opponentTeam.get(j);
		   			
		   				if (dao1.equals(def.getName()) && def.getPosition().equals("유격수")) {	
		   					analysisDAO dao = new analysisDAO();
		 	    			
		 	    			dao.setName(def.getName());
		 	    			dao.setPosition(def.getPosition());
		 	    			dao.setFpct(def.getFpct());
		 	    			
		 	    			fpctAvgRight_opponentTeam.add(dao);
						}
		   				if (dao1.equals(def.getName()) && def.getPosition().equals("2루수")) {	
		   					analysisDAO dao = new analysisDAO();
		 	    			
		 	    			dao.setName(def.getName());
		 	    			dao.setPosition(def.getPosition());
		 	    			dao.setFpct(def.getFpct());
		 	    			
		 	    			fpctAvgRight_opponentTeam.add(dao);
						}
		   				if (dao1.equals(def.getName()) && def.getPosition().equals("중견수")) {	
		   					analysisDAO dao = new analysisDAO();
		 	    			
		 	    			dao.setName(def.getName());
		 	    			dao.setPosition(def.getPosition());
		 	    			dao.setFpct(def.getFpct());
		 	    			
		 	    			fpctAvgRight_opponentTeam.add(dao);
						}
				}
	   			}catch(NullPointerException e) { 
	    		}
	   		}
        }
        
        // 좌투상대 선발 라인업 중앙 수비능력
        for(int k = 0; k < defenseList22.size(); k++) {
			defenseDAO def = defenseList22.get(k);
	   		if (def.getTeamName().equals(myTeam)){
	   			
	   			try {
	   				for(int j = 0; j < lineUpLeft_myTeam.size(); j++) {
	   			   		String dao1 = lineUpLeft_myTeam.get(j);
		   			
		   				if (dao1.equals(def.getName()) && def.getPosition().equals("유격수")) {	
		   					analysisDAO dao = new analysisDAO();
		 	    			
		 	    			dao.setName(def.getName());
		 	    			dao.setPosition(def.getPosition());
		 	    			dao.setFpct(def.getFpct());
		 	    			
		 	    			fpctAvgLeft_myTeam.add(dao);
						}
		   				if (dao1.equals(def.getName()) && def.getPosition().equals("2루수")) {	
		   					analysisDAO dao = new analysisDAO();
		 	    			
		 	    			dao.setName(def.getName());
		 	    			dao.setPosition(def.getPosition());
		 	    			dao.setFpct(def.getFpct());
		 	    			
		 	    			fpctAvgLeft_myTeam.add(dao);
						}
		   				if (dao1.equals(def.getName()) && def.getPosition().equals("중견수")) {	
		   					analysisDAO dao = new analysisDAO();
		 	    			
		 	    			dao.setName(def.getName());
		 	    			dao.setPosition(def.getPosition());
		 	    			dao.setFpct(def.getFpct());
		 	    			
		 	    			fpctAvgLeft_myTeam.add(dao);
						}
				}
	   			}catch(NullPointerException e) { 
	    		}
	   		}
	   		if (def.getTeamName().equals(opponentTeam)){
	   			
	   			try {
	   				for(int j = 0; j < lineUpLeft_opponentTeam.size(); j++) {
	   			   		String dao1 = lineUpLeft_opponentTeam.get(j);
		   			
		   				if (dao1.equals(def.getName()) && def.getPosition().equals("유격수")) {	
		   					analysisDAO dao = new analysisDAO();
		 	    			
		 	    			dao.setName(def.getName());
		 	    			dao.setPosition(def.getPosition());
		 	    			dao.setFpct(def.getFpct());
		 	    			
		 	    			fpctAvgLeft_opponentTeam.add(dao);
						}
		   				if (dao1.equals(def.getName()) && def.getPosition().equals("2루수")) {	
		   					analysisDAO dao = new analysisDAO();
		 	    			
		 	    			dao.setName(def.getName());
		 	    			dao.setPosition(def.getPosition());
		 	    			dao.setFpct(def.getFpct());
		 	    			
		 	    			fpctAvgLeft_opponentTeam.add(dao);
						}
		   				if (dao1.equals(def.getName()) && def.getPosition().equals("중견수")) {	
		   					analysisDAO dao = new analysisDAO();
		 	    			
		 	    			dao.setName(def.getName());
		 	    			dao.setPosition(def.getPosition());
		 	    			dao.setFpct(def.getFpct());
		 	    			
		 	    			fpctAvgLeft_opponentTeam.add(dao);
						}
				}
	   			}catch(NullPointerException e) { 
	    		}
	   		}
        }
        // 언더상대 선발 라인업 중앙 수비능력
        for(int k = 0; k < defenseList22.size(); k++) {
			defenseDAO def = defenseList22.get(k);
	   		if (def.getTeamName().equals(myTeam)){
	   			
	   			try {
	   				for(int j = 0; j < lineUpUnder_myTeam.size(); j++) {
	   			   		String dao1 = lineUpUnder_myTeam.get(j);
		   			
		   				if (dao1.equals(def.getName()) && def.getPosition().equals("유격수")) {	
		   					analysisDAO dao = new analysisDAO();
		 	    			
		 	    			dao.setName(def.getName());
		 	    			dao.setPosition(def.getPosition());
		 	    			dao.setFpct(def.getFpct());
		 	    			
		 	    			fpctAvgUnder_myTeam.add(dao);
						}
		   				if (dao1.equals(def.getName()) && def.getPosition().equals("2루수")) {	
		   					analysisDAO dao = new analysisDAO();
		 	    			
		 	    			dao.setName(def.getName());
		 	    			dao.setPosition(def.getPosition());
		 	    			dao.setFpct(def.getFpct());
		 	    			
		 	    			fpctAvgUnder_myTeam.add(dao);
						}
		   				if (dao1.equals(def.getName()) && def.getPosition().equals("중견수")) {	
		   					analysisDAO dao = new analysisDAO();
		 	    			
		 	    			dao.setName(def.getName());
		 	    			dao.setPosition(def.getPosition());
		 	    			dao.setFpct(def.getFpct());
		 	    			
		 	    			fpctAvgUnder_myTeam.add(dao);
						}
				}
	   			}catch(NullPointerException e) { 
	    		}
	   		}
	   		if (def.getTeamName().equals(opponentTeam)){
	   			
	   			try {
	   				for(int j = 0; j < lineUpUnder_opponentTeam.size(); j++) {
	   			   		String dao1 = lineUpUnder_opponentTeam.get(j);
		   			
		   				if (dao1.equals(def.getName()) && def.getPosition().equals("유격수")) {	
		   					analysisDAO dao = new analysisDAO();
		 	    			
		 	    			dao.setName(def.getName());
		 	    			dao.setPosition(def.getPosition());
		 	    			dao.setFpct(def.getFpct());
		 	    			
		 	    			fpctAvgUnder_opponentTeam.add(dao);
						}
		   				if (dao1.equals(def.getName()) && def.getPosition().equals("2루수")) {	
		   					analysisDAO dao = new analysisDAO();
		 	    			
		 	    			dao.setName(def.getName());
		 	    			dao.setPosition(def.getPosition());
		 	    			dao.setFpct(def.getFpct());
		 	    			
		 	    			fpctAvgUnder_opponentTeam.add(dao);
						}
		   				if (dao1.equals(def.getName()) && def.getPosition().equals("중견수")) {	
		   					analysisDAO dao = new analysisDAO();
		 	    			
		 	    			dao.setName(def.getName());
		 	    			dao.setPosition(def.getPosition());
		 	    			dao.setFpct(def.getFpct());
		 	    			
		 	    			fpctAvgUnder_opponentTeam.add(dao);
						}
				}
	   			}catch(NullPointerException e) { 
	    		}
	   		}
        }
        
        
        
        // 좌투수, 언더 상대 약한 타자
        for(int i = 0; i < hitterTypeList22.size() ; i++) {
 	    	hitterDAO hitter = hitterTypeList22.get(i);
 	    	if (hitter.getTeamName().equals(myTeam) && hitter.getPitcherType().equals("좌투수") && hitter.getAb()> 20) {
 	    		try {
 	    			analysisDAO dao = new analysisDAO();
 	    			
 	    			dao.setName(hitter.getName());
 	    			dao.setAverage(hitter.getAverage());
 	    			dao.setAb(i);
 	    			
 	    			WeakLeftHand_myTeam.add(dao);
 	    		}
     	    	catch(NullPointerException e) { 
     	    	}
        	}
 	    	if (hitter.getTeamName().equals(myTeam) && hitter.getPitcherType().equals("언더") && hitter.getAb() > 10) {
 	    		try {
 	    			analysisDAO dao = new analysisDAO();
 	    			
 	    			dao.setName(hitter.getName());
 	    			dao.setAverage(hitter.getAverage());
 	    			
 	    			WeakUnderHand_myTeam.add(dao);
 	    		}
     	    	catch(NullPointerException e) { 
     	    	}
        	}
        }
        
        /** sorting 이 필요한 부분 -> collections.sort 메소드를 통해 오름차순 및 내림차순 정렬 */
        
        // myTeam 순위
        Collections.sort(rankW_myTeam, new Comparator<analysisDAO>() {				// 다승
            @Override
            public int compare(analysisDAO p1, analysisDAO p2) {
                return p2.getW() - p1.getW();
            }
        });	
        Collections.sort(rankEra_myTeam, new Comparator<analysisDAO>() {			// 평균자책점
            @Override
            public int compare(analysisDAO p1, analysisDAO p2) {
                return Double.compare(p1.getEra() , p2.getEra());
            }
        });
        Collections.sort(rankSo_myTeam, new Comparator<analysisDAO>() {				// 삼진
            @Override
            public int compare(analysisDAO p1, analysisDAO p2) {
                return p2.getSo() - p1.getSo();
            }
        });
        Collections.sort(rankSv_myTeam, new Comparator<analysisDAO>() {				// 세이브
            @Override
            public int compare(analysisDAO p1, analysisDAO p2) {
                return p2.getSv() - p1.getSv();
            }
        });
        Collections.sort(rankWhip_myTeam, new Comparator<analysisDAO>() {			// whip
            @Override
            public int compare(analysisDAO p1, analysisDAO p2) {
                return Double.compare(p1.getWhip() , p2.getWhip());
            }
        });
        
        Collections.sort(WeakLeftHitter_myTeam, new Comparator<analysisDAO>() {		// 좌타자상대 약한 투수
            @Override
            public int compare(analysisDAO p1, analysisDAO p2) {
                return Double.compare(p2.getAverage() , p1.getAverage());
            }
        });
        Collections.sort(WeakLeftHand_myTeam, new Comparator<analysisDAO>() {		// 좌투수상대 약한 타자
            @Override
            public int compare(analysisDAO p1, analysisDAO p2) {
                return Double.compare(p1.getAverage(), p2.getAverage());
            }
        });
        Collections.sort(WeakUnderHand_myTeam, new Comparator<analysisDAO>() {		// 좌투수상대 약한 타자
            @Override
            public int compare(analysisDAO p1, analysisDAO p2) {
                return Double.compare(p1.getAverage(), p2.getAverage());
            }
        });
        
        
        Collections.sort(rankAvg_myTeam, new Comparator<analysisDAO>() {			// 타율
            @Override
            public int compare(analysisDAO p1, analysisDAO p2) {
                return Double.compare(p2.getAverage(), p1.getAverage());
            }
        });
        Collections.sort(rankRbi_myTeam, new Comparator<analysisDAO>() {			// 타점
            @Override
            public int compare(analysisDAO p1, analysisDAO p2) {
                return p2.getRbi() - p1.getRbi();
            }
        });
        Collections.sort(rankHr_myTeam, new Comparator<analysisDAO>() {				// 홈런
            @Override
            public int compare(analysisDAO p1, analysisDAO p2) {
                return p2.getHr() - p1.getHr();
            }
        });
        Collections.sort(rankOps_myTeam, new Comparator<analysisDAO>() {			// ops
            @Override
            public int compare(analysisDAO p1, analysisDAO p2) {
                return Double.compare(p2.getOps(), p1.getOps());
            }
        });
        Collections.sort(rankXr_myTeam, new Comparator<analysisDAO>() {				// xr
            @Override
            public int compare(analysisDAO p1, analysisDAO p2) {
                return Double.compare(p2.getXr(), p1.getXr());
            }
        });
        Collections.sort(rankGpa_myTeam, new Comparator<analysisDAO>() {			// gpa
            @Override
            public int compare(analysisDAO p1, analysisDAO p2) {
                return Double.compare(p2.getGpa(), p1.getGpa() );
            }
        });
        Collections.sort(rankSb_myTeam, new Comparator<analysisDAO>() {				// 도루
            @Override
            public int compare(analysisDAO p1, analysisDAO p2) {
                return p2.getSb() - p1.getSb();
            }
        });
        Collections.sort(rankSbPer_myTeam, new Comparator<analysisDAO>() {			// 도루저지율
            @Override
            public int compare(analysisDAO p1, analysisDAO p2) {
                return Double.compare(p2.getSbPer(), p1.getSbPer());
            }
        });
        
        Collections.sort(rankPhba_myTeam, new Comparator<analysisDAO>() {			// 대타타율
            @Override
            public int compare(analysisDAO p1, analysisDAO p2) {
                return Double.compare(p2.getPhba(), p1.getPhba());
            }
        });
        Collections.sort(rankCsPer_myTeam, new Comparator<analysisDAO>() {		// 도루저지율
            @Override
            public int compare(analysisDAO p1, analysisDAO p2) {
                return Double.compare(p2.getCsPer() , p1.getCsPer());
            }
        });
        
        
        // opponentTeam 순위
        Collections.sort(rankW_opponentTeam, new Comparator<analysisDAO>() {		// 다승
            @Override
            public int compare(analysisDAO p1, analysisDAO p2) {
                return p2.getW() - p1.getW();
            }
        });	
        Collections.sort(rankEra_opponentTeam, new Comparator<analysisDAO>() {		// 평균자책점
            @Override
            public int compare(analysisDAO p1, analysisDAO p2) {
            	return Double.compare(p1.getEra() , p2.getEra());
            }
        });
        Collections.sort(rankSo_opponentTeam, new Comparator<analysisDAO>() {		// 삼진
            @Override
            public int compare(analysisDAO p1, analysisDAO p2) {
                return p2.getSo() - p1.getSo();
            }
        });
        Collections.sort(rankSv_opponentTeam, new Comparator<analysisDAO>() {		// 세이브
            @Override
            public int compare(analysisDAO p1, analysisDAO p2) {
                return p2.getSv() - p1.getSv();
            }
        });
        Collections.sort(rankWhip_opponentTeam, new Comparator<analysisDAO>() {		// whip
            @Override
            public int compare(analysisDAO p1, analysisDAO p2) {
                return Double.compare(p1.getWhip() , p2.getWhip());
            }
        });
        Collections.sort(rankAvg_opponentTeam, new Comparator<analysisDAO>() {		// 타율
            @Override
            public int compare(analysisDAO p1, analysisDAO p2) {
                return Double.compare(p2.getAverage(), p1.getAverage());
            }
        });
        Collections.sort(rankRbi_opponentTeam, new Comparator<analysisDAO>() {		// 타점
            @Override
            public int compare(analysisDAO p1, analysisDAO p2) {
                return p2.getRbi() - p1.getRbi();
            }
        });
        Collections.sort(rankHr_opponentTeam, new Comparator<analysisDAO>() {		// 홈런
            @Override
            public int compare(analysisDAO p1, analysisDAO p2) {
                return p2.getHr() - p1.getHr();
            }
        });
        Collections.sort(rankOps_opponentTeam, new Comparator<analysisDAO>() {		// ops
            @Override
            public int compare(analysisDAO p1, analysisDAO p2) {
                return Double.compare(p2.getOps(), p1.getOps());
            }
        });
        Collections.sort(rankXr_opponentTeam, new Comparator<analysisDAO>() {		// xr
            @Override
            public int compare(analysisDAO p1, analysisDAO p2) {
                return Double.compare(p2.getXr(), p1.getXr());
            }
        });
        Collections.sort(rankGpa_opponentTeam, new Comparator<analysisDAO>() {		// gpa
            @Override
            public int compare(analysisDAO p1, analysisDAO p2) {
                return Double.compare(p2.getGpa(), p1.getGpa());
            }
        });
        Collections.sort(rankSb_opponentTeam, new Comparator<analysisDAO>() {		// 도루
            @Override
            public int compare(analysisDAO p1, analysisDAO p2) {
                return p2.getSb() - p1.getSb();
            }
        });	
        Collections.sort(rankSbPer_opponentTeam, new Comparator<analysisDAO>() {	// 도루저지율
            @Override
            public int compare(analysisDAO p1, analysisDAO p2) {
                return Double.compare(p2.getSbPer(), p1.getSbPer());
            }
        });
        Collections.sort(rankPhba_opponentTeam, new Comparator<analysisDAO>() {		// 대타타율
            @Override
            public int compare(analysisDAO p1, analysisDAO p2) {
                return Double.compare(p2.getPhba(), p1.getPhba());
            }
        });
        Collections.sort(rankCsPer_opponentTeam, new Comparator<analysisDAO>() {		// 도루저지율
            @Override
            public int compare(analysisDAO p1, analysisDAO p2) {
                return Double.compare(p2.getCsPer() , p1.getCsPer());
            }
        });
        
        /** print 부분 -> rankW_myTeam, rankEra_myTeam 등을 get(i) 통해서 데이터 얻음. */ 
        
        float fpctSum = 0;	// fpct(수비율) 평균을 내기 위한 변수
        
        for(int i = 0; i < fpctAvgRight_myTeam.size() ; i++) {
        	analysisDAO dao = fpctAvgRight_myTeam.get(i);
        	fpctSum += dao.getFpct();
   		}
        
        
        fpctSum = 0;	// 초기화
        for(int i = 0; i < fpctAvgLeft_myTeam.size() ; i++) {
        	analysisDAO dao = fpctAvgLeft_myTeam.get(i);
        	fpctSum += dao.getFpct();
   		}
        
        
        fpctSum = 0;	// 초기화
        for(int i = 0; i < fpctAvgUnder_myTeam.size() ; i++) {
        	analysisDAO dao = fpctAvgUnder_myTeam.get(i);
        	fpctSum += dao.getFpct();
   		}
        
        
        	
        fpctSum = 0;	// fpct(수비율) 평균을 내기 위한 변수
        
        for(int i = 0; i < fpctAvgRight_opponentTeam.size() ; i++) {
        	analysisDAO dao = fpctAvgRight_opponentTeam.get(i);
        	fpctSum += dao.getFpct();
   		}
        
        
        fpctSum = 0;	// 초기화
        for(int i = 0; i < fpctAvgLeft_opponentTeam.size() ; i++) {
        	analysisDAO dao = fpctAvgLeft_opponentTeam.get(i);
        	fpctSum += dao.getFpct();
   		}
       
        
        fpctSum = 0;	// 초기화
        for(int i = 0; i < fpctAvgUnder_opponentTeam.size() ; i++) {
        	analysisDAO dao = fpctAvgUnder_opponentTeam.get(i);
        	fpctSum += dao.getFpct();
   		}
        
        
        
	}
	
	/** 타자 상승세, 하락세 - ops */
	public class HitterTrend {
	    private ArrayList<analysisDAO> uptrend;
	    private ArrayList<analysisDAO> downtrend;

	    public HitterTrend(ArrayList<analysisDAO> uptrend, ArrayList<analysisDAO> downtrend) {
	        this.uptrend = uptrend;
	        this.downtrend = downtrend;
	    }

	    public ArrayList<analysisDAO> getUptrend() {
	        return uptrend;
	    }

	    public ArrayList<analysisDAO> getDowntrend() {
	        return downtrend;
	    }
	}
	public HitterTrend getHitterTrendMyTeam(ArrayList<hitterDAO> hitterList22, ArrayList<hitterDAO> hitterList21, String myTeam) {
	    ArrayList<analysisDAO> hitterUptrend_myTeam = new ArrayList<>();
	    ArrayList<analysisDAO> hitterDowntrend_myTeam = new ArrayList<>();

	    for(int i = 0; i < hitterList22.size() ; i++) {    
	        hitterDAO hit22 = hitterList22.get(i);
	        
	        for(int j = 0; j < hitterList21.size() ; j++) {
	            hitterDAO hit21 = hitterList21.get(j);
	            
	            if(hit21.getName().equals(hit22.getName()) && hit22.getTeamName().equals(myTeam) && hit21.getTeamName().equals(myTeam)) {
	                try {
	                    if(hit22.getOps() > hit21.getOps()) {       // 우리팀 ops 상승세 타자
	                        analysisDAO dao = new analysisDAO();
	                        dao.setTeamName(hit22.getTeamName());
	                        dao.setName(hit22.getName());
	                        dao.setOps22(hit22.getOps());
	                        dao.setOps21(hit21.getOps());
	                        hitterUptrend_myTeam.add(dao);
	                    }
	                    if(hit22.getOps() < hit21.getOps()) {       // 우리팀 ops 하락세 타자
	                        analysisDAO dao = new analysisDAO();
	                        dao.setTeamName(hit22.getTeamName());
	                        dao.setName(hit22.getName());
	                        dao.setOps22(hit22.getOps());
	                        dao.setOps21(hit21.getOps());
	                        hitterDowntrend_myTeam.add(dao);
	                    }
	                }
	                catch(NullPointerException e) { 
	                }
	            }
	        }
	    }
	    return new HitterTrend(hitterUptrend_myTeam, hitterDowntrend_myTeam);
	}
	public HitterTrend getHitterTrendOpponentTeam(ArrayList<hitterDAO> hitterList22, ArrayList<hitterDAO> hitterList21, String opponentTeam) {
	    ArrayList<analysisDAO> hitterUptrend_opponentTeam = new ArrayList<analysisDAO>();
	    ArrayList<analysisDAO> hitterDowntrend_opponentTeam = new ArrayList<analysisDAO>();

	    for (int i = 0; i < hitterList22.size(); i++) {
	        hitterDAO hitter22 = hitterList22.get(i);

	        for (int j = 0; j < hitterList21.size(); j++) {
	            hitterDAO hitter21 = hitterList21.get(j);

	            if (hitter21.getName().equals(hitter22.getName()) && hitter22.getTeamName().equals(opponentTeam) && hitter21.getTeamName().equals(opponentTeam)) {
	                try {
	                    if (hitter22.getOps() > hitter21.getOps()) {
	                        analysisDAO dao = new analysisDAO();
	                        dao.setTeamName(hitter22.getTeamName());
	                        dao.setName(hitter22.getName());
	                        dao.setOps22(hitter22.getOps());
	                        dao.setOps21(hitter21.getOps());
	                        hitterUptrend_opponentTeam.add(dao);
	                    } else if (hitter22.getOps() < hitter21.getOps()) { 
	                        analysisDAO dao = new analysisDAO();
	                        dao.setTeamName(hitter22.getTeamName());
	                        dao.setName(hitter22.getName());
	                        dao.setOps22(hitter22.getOps());
	                        dao.setOps21(hitter21.getOps());
	                        hitterDowntrend_opponentTeam.add(dao);
	                    }
	                } catch (NullPointerException e) {
	                }
	            }
	        }
	    }
	    return new HitterTrend(hitterUptrend_opponentTeam, hitterDowntrend_opponentTeam);
	}
	
	
	
	/** 투수 상승세, 하락세 - whip */
	public class PitcherTrend {
	    private ArrayList<analysisDAO> uptrend;
	    private ArrayList<analysisDAO> downtrend;

	    public PitcherTrend(ArrayList<analysisDAO> uptrend, ArrayList<analysisDAO> downtrend) {
	        this.uptrend = uptrend;
	        this.downtrend = downtrend;
	    }

	    public ArrayList<analysisDAO> getUptrend() {
	        return uptrend;
	    }

	    public ArrayList<analysisDAO> getDowntrend() {
	        return downtrend;
	    }
	}
	public PitcherTrend getPitcherTrendMyTeam(ArrayList<pitcherDAO> pitcherList22, ArrayList<pitcherDAO> pitcherList21, String myTeam) {
	    ArrayList<analysisDAO> pitcherUptrend_myTeam = new ArrayList<>();
	    ArrayList<analysisDAO> pitcherDowntrend_myTeam = new ArrayList<>();

	    for(int i = 0; i < pitcherList22.size() ; i++) {    
	    	pitcherDAO pit22 = pitcherList22.get(i);
	        
	        for(int j = 0; j < pitcherList21.size() ; j++) {
	        	pitcherDAO pit21 = pitcherList21.get(j);
	            
	            if(pit21.getName().equals(pit22.getName()) && pit22.getTeamName().equals(myTeam) && pit21.getTeamName().equals(myTeam)) {
	                try {
	                    if(pit22.getWhip() > pit21.getWhip()) {       // 우리팀 Whip 상승세 투수
	                        analysisDAO dao = new analysisDAO();
	                        dao.setTeamName(pit22.getTeamName());
	                        dao.setName(pit22.getName());
	                        dao.setWhip22(pit22.getWhip());
	                        dao.setWhip21(pit21.getWhip());
	                        pitcherUptrend_myTeam.add(dao);
	                    }
	                    if(pit22.getWhip() < pit21.getWhip()) {       // 우리팀 Whip 하락세 투수
	                        analysisDAO dao = new analysisDAO();
	                        dao.setTeamName(pit22.getTeamName());
	                        dao.setName(pit22.getName());
	                        dao.setWhip22(pit22.getWhip());
	                        dao.setWhip21(pit21.getWhip());
	                        pitcherDowntrend_myTeam.add(dao);
	                    }
	                }
	                catch(NullPointerException e) { 
	                }
	            }
	        }
	    }
	    return new PitcherTrend(pitcherUptrend_myTeam, pitcherDowntrend_myTeam);
	}
	public PitcherTrend getpitcherTrendOpponentTeam(ArrayList<pitcherDAO> pitcherList22, ArrayList<pitcherDAO> pitcherList21, String opponentTeam) {
	    ArrayList<analysisDAO> pitcherUptrend_opponentTeam = new ArrayList<analysisDAO>();
	    ArrayList<analysisDAO> pitcherDowntrend_opponentTeam = new ArrayList<analysisDAO>();

	    for (int i = 0; i < pitcherList22.size(); i++) {
	        pitcherDAO pitcher22 = pitcherList22.get(i);

	        for (int j = 0; j < pitcherList21.size(); j++) {
	            pitcherDAO pitcher21 = pitcherList21.get(j);

	            if (pitcher21.getName().equals(pitcher22.getName()) && pitcher22.getTeamName().equals(opponentTeam) && pitcher21.getTeamName().equals(opponentTeam)) {
	                try {
	                    if (pitcher22.getWhip() > pitcher21.getWhip()) {
	                        analysisDAO dao = new analysisDAO();
	                        dao.setTeamName(pitcher22.getTeamName());
	                        dao.setName(pitcher22.getName());
	                        dao.setWhip22(pitcher22.getWhip());
	                        dao.setWhip21(pitcher21.getWhip());
	                        pitcherUptrend_opponentTeam.add(dao);
	                    } else if (pitcher22.getWhip() < pitcher21.getWhip()) { 
	                        analysisDAO dao = new analysisDAO();
	                        dao.setTeamName(pitcher22.getTeamName());
	                        dao.setName(pitcher22.getName());
	                        dao.setWhip22(pitcher22.getWhip());
	                        dao.setWhip21(pitcher21.getWhip());
	                        pitcherDowntrend_opponentTeam.add(dao);
	                    }
	                } catch (NullPointerException e) {
	                }
	            }
	        }
	    }
	    return new PitcherTrend(pitcherUptrend_opponentTeam, pitcherDowntrend_opponentTeam);
	}
	
	
	/** 좌타자 상대 약한 투수 */
	public class WeakLeftPitcher {
	    private ArrayList<analysisDAO> weakLeftPitcher_myTeam;
	    private ArrayList<analysisDAO> weakLeftPitcher_opponentTeam;

	    public WeakLeftPitcher(ArrayList<analysisDAO> weakLeftPitcher_myTeam, ArrayList<analysisDAO> weakLeftPitcher_opponentTeam) {
	        this.weakLeftPitcher_myTeam = weakLeftPitcher_myTeam;
	        this.weakLeftPitcher_opponentTeam = weakLeftPitcher_opponentTeam;
	    }

	    public ArrayList<analysisDAO> getWeakLeftPitcher_myTeam() {
	        return weakLeftPitcher_myTeam;
	    }

	    public ArrayList<analysisDAO> getWeakLeftPitcher_opponentTeam() {
	        return weakLeftPitcher_opponentTeam;
	    }
	}

	public WeakLeftPitcher getWeakLeftPitchers(ArrayList<pitcherDAO> pitcherList22, String myTeam, String opponentTeam) {
	    ArrayList<analysisDAO> weakLeftPitcher_myTeam = new ArrayList<>();
	    ArrayList<analysisDAO> weakLeftPitcher_opponentTeam = new ArrayList<>();

	    for(int i = 0; i < pitcherList22.size() ; i++) {
	        pitcherDAO pitch = pitcherList22.get(i);
	        
	        if (pitch.getTeamName().equals(myTeam) && pitch.getHitterType().equals("좌타자")) {
	            try {
	                analysisDAO dao = new analysisDAO();
	                
	                dao.setTeamName(pitch.getTeamName());
	                dao.setName(pitch.getName());
	                dao.setAverage(pitch.getAvg());

	                weakLeftPitcher_myTeam.add(dao);
	            } catch (NullPointerException e) {
	            }
	        }

	        if (pitch.getTeamName().equals(opponentTeam) && pitch.getHitterType().equals("좌타자")) {
	            try {
	                analysisDAO dao = new analysisDAO();

	                dao.setTeamName(pitch.getTeamName());
	                dao.setName(pitch.getName());
	                dao.setAverage(pitch.getAvg());

	                weakLeftPitcher_opponentTeam.add(dao);
	            } catch (NullPointerException e) {
	            }
	        }
	    }
	    return new WeakLeftPitcher(weakLeftPitcher_myTeam, weakLeftPitcher_opponentTeam);
	}
	/** 좌투수 상대 약한 타자 */
	public class WeakLeftUnderHitter {
	    private ArrayList<analysisDAO> weakLeftHitter_myTeam;
	    private ArrayList<analysisDAO> weakLeftHitter_opponentTeam;
	    private ArrayList<analysisDAO> weakUnderHitter_myTeam;
	    private ArrayList<analysisDAO> weakUnderHitter_opponentTeam;

	    public WeakLeftUnderHitter(ArrayList<analysisDAO> weakLeftHitter_myTeam,ArrayList<analysisDAO> weakLeftHitter_opponentTeam, ArrayList<analysisDAO> weakUnderHitter_myTeam, ArrayList<analysisDAO> weakUnderHitter_opponentTeam) {
	        this.weakLeftHitter_myTeam = weakLeftHitter_myTeam;
	        this.weakLeftHitter_opponentTeam = weakLeftHitter_opponentTeam;
	        this.weakUnderHitter_myTeam = weakUnderHitter_myTeam;
	        this.weakUnderHitter_opponentTeam = weakUnderHitter_opponentTeam;
	    }

	    public ArrayList<analysisDAO> getWeakLeftHitter_myTeam() {
	        return weakLeftHitter_myTeam;
	    }
	    public ArrayList<analysisDAO> getWeakLeftHitter_opponentTeam() {
	        return weakLeftHitter_opponentTeam;
	    }
	    public ArrayList<analysisDAO> getWeakUnderHitter_myTeam() {
	        return weakUnderHitter_myTeam;
	    }
	    public ArrayList<analysisDAO> getWeakUnderHitter_opponentTeam() {
	        return weakUnderHitter_opponentTeam;
	    }
	}
	public WeakLeftUnderHitter getWeakLeftHitters(ArrayList<hitterDAO> hitterTypeList22, String myTeam, String opponentTeam) {
	    ArrayList<analysisDAO> weakLeftHand_myTeam = new ArrayList<>();
	    ArrayList<analysisDAO> weakLeftHand_opponentTeam = new ArrayList<>();
	    ArrayList<analysisDAO> weakUnderHand_myTeam = new ArrayList<>();
	    ArrayList<analysisDAO> weakUnderHand_opponentTeam = new ArrayList<>();

	    for (int i = 0; i < hitterTypeList22.size(); i++) {
	        hitterDAO hitter = hitterTypeList22.get(i);
	        
	        if (hitter.getTeamName().equals(myTeam) && hitter.getPitcherType().equals("좌투수") && hitter.getAb() > 20) {
	            try {
	                analysisDAO dao = new analysisDAO();
	                
	                dao.setTeamName(hitter.getTeamName());
	                dao.setName(hitter.getName());
	                dao.setAverage(hitter.getAverage());
	                
	                weakLeftHand_myTeam.add(dao);
	            } catch (NullPointerException e) { 
	            }
	        }
	        if (hitter.getTeamName().equals(opponentTeam) && hitter.getPitcherType().equals("좌투수") && hitter.getAb() > 20) {
	            try {
	                analysisDAO dao = new analysisDAO();
	                
	                dao.setTeamName(hitter.getTeamName());
	                dao.setName(hitter.getName());
	                dao.setAverage(hitter.getAverage());
	                
	                weakLeftHand_opponentTeam.add(dao);
	            } catch (NullPointerException e) { 
	            }
	        }
	        
	        if (hitter.getTeamName().equals(myTeam) && hitter.getPitcherType().equals("언더") && hitter.getAb() > 10) {
	            try {
	                analysisDAO dao = new analysisDAO();
	                
	                dao.setTeamName(hitter.getTeamName());
	                dao.setName(hitter.getName());
	                dao.setAverage(hitter.getAverage());
	                
	                weakUnderHand_myTeam.add(dao);
	            } catch (NullPointerException e) { 
	            }
	        }
	        if (hitter.getTeamName().equals(opponentTeam) && hitter.getPitcherType().equals("언더") && hitter.getAb() > 10) {
	            try {
	                analysisDAO dao = new analysisDAO();
	                
	                dao.setTeamName(hitter.getTeamName());
	                dao.setName(hitter.getName());
	                dao.setAverage(hitter.getAverage());
	                
	                weakUnderHand_opponentTeam.add(dao);
	            } catch (NullPointerException e) { 
	            }
	        }
	    }
	    
	    return new WeakLeftUnderHitter(weakLeftHand_myTeam,weakLeftHand_opponentTeam, weakUnderHand_myTeam,weakUnderHand_opponentTeam);
	}
	
	/**선발 중앙 수비능력*/
	public class FpctAvgRight {
		private ArrayList<analysisDAO> fpctAvgRight_myTeam;
		private ArrayList<analysisDAO> fpctAvgRight_opponentTeam;

		public FpctAvgRight(ArrayList<analysisDAO> fpctAvgRight_myTeam,ArrayList<analysisDAO> fpctAvgRight_opponentTeam) {
			this.fpctAvgRight_myTeam = fpctAvgRight_myTeam;
			this.fpctAvgRight_opponentTeam = fpctAvgRight_opponentTeam; 
	    }
		public  ArrayList<analysisDAO> getFpctAvgRight_myTeam() {
	        return fpctAvgRight_myTeam;
	    }
	    public  ArrayList<analysisDAO> getFpctAvgRight_opponentTeam() {
	        return fpctAvgRight_opponentTeam;
	    }
	}
	public FpctAvgRight getFpctAvgRight(ArrayList<defenseDAO> defenseList22, String myTeam, String opponentTeam) {
	    ArrayList<analysisDAO> fpctAvgRight_myTeam = new ArrayList<>();
	    ArrayList<analysisDAO> fpctAvgRight_opponentTeam = new ArrayList<>();

	    // Find the player with the highest fpct for each position
	    for (int i = 0; i < defenseList22.size(); i++) {
	        defenseDAO def = defenseList22.get(i);

	        if (def.getTeamName().equals(myTeam) && (def.getPosition().equals("유격수") || def.getPosition().equals("2루수") || def.getPosition().equals("중견수"))) {
	            if (fpctAvgRight_myTeam.isEmpty()) {
	                analysisDAO dao = new analysisDAO();
	                dao.setTeamName(myTeam);
	                dao.setName(def.getName());
	                dao.setPosition(def.getPosition());
	                dao.setFpct(def.getFpct());
	                fpctAvgRight_myTeam.add(dao);
	            } else {
	                analysisDAO highestFpctPlayer = fpctAvgRight_myTeam.get(0);
	                if (def.getFpct() > highestFpctPlayer.getFpct()) {
	                    analysisDAO dao = new analysisDAO();
	                    dao.setTeamName(myTeam);
	                    dao.setName(def.getName());
	                    dao.setPosition(def.getPosition());
	                    dao.setFpct(def.getFpct());
	                    fpctAvgRight_myTeam.clear();
	                    fpctAvgRight_myTeam.add(dao);
	                } else if (def.getFpct() == highestFpctPlayer.getFpct()) {
	                    analysisDAO dao = new analysisDAO();
	                    dao.setTeamName(myTeam);
	                    dao.setName(def.getName());
	                    dao.setPosition(def.getPosition());
	                    dao.setFpct(def.getFpct());
	                    fpctAvgRight_myTeam.add(dao);
	                }
	            }
	        }

	        if (def.getTeamName().equals(opponentTeam) && (def.getPosition().equals("유격수") || def.getPosition().equals("2루수") || def.getPosition().equals("중견수"))) {
	            if (fpctAvgRight_opponentTeam.isEmpty()) {
	                analysisDAO dao = new analysisDAO();
	                dao.setTeamName(opponentTeam);
	                dao.setName(def.getName());
	                dao.setPosition(def.getPosition());
	                dao.setFpct(def.getFpct());
	                fpctAvgRight_opponentTeam.add(dao);
	            } else {
	                analysisDAO highestFpctPlayer = fpctAvgRight_opponentTeam.get(0);
	                if (def.getFpct() > highestFpctPlayer.getFpct()) {
	                    analysisDAO dao = new analysisDAO();
	                    dao.setTeamName(opponentTeam);
	                    dao.setName(def.getName());
	                    dao.setPosition(def.getPosition());
	                    dao.setFpct(def.getFpct());
	                    fpctAvgRight_opponentTeam.clear();
	                    fpctAvgRight_opponentTeam.add(dao);
	                } else if (def.getFpct() == highestFpctPlayer.getFpct()) {
	                    analysisDAO dao = new analysisDAO();
	                    dao.setTeamName(opponentTeam);
	                    dao.setName(def.getName());
	                    dao.setPosition(def.getPosition());
	                    dao.setFpct(def.getFpct());
	                    fpctAvgRight_opponentTeam.add(dao);
	                }
	            }
	        }
	    }
	    return new FpctAvgRight(fpctAvgRight_myTeam, fpctAvgRight_opponentTeam);
	}
	public class FpctAvgLeft {
	    private ArrayList<analysisDAO> fpctAvgLeft_myTeam;
	    private ArrayList<analysisDAO> fpctAvgLeft_opponentTeam;

	    public FpctAvgLeft(ArrayList<analysisDAO> fpctAvgLeft_myTeam, ArrayList<analysisDAO> fpctAvgLeft_opponentTeam) {
	        this.fpctAvgLeft_myTeam = fpctAvgLeft_myTeam;
	        this.fpctAvgLeft_opponentTeam = fpctAvgLeft_opponentTeam;
	    }

	    public ArrayList<analysisDAO> getFpctAvgLeft_myTeam() {
	        return fpctAvgLeft_myTeam;
	    }

	    public ArrayList<analysisDAO> getFpctAvgLeft_opponentTeam() {
	        return fpctAvgLeft_opponentTeam;
	    }
	}
	public FpctAvgLeft getFpctAvgLeft(ArrayList<defenseDAO> defenseList22, String myTeam, String opponentTeam) {
	    ArrayList<analysisDAO> fpctAvgLeft_myTeam = new ArrayList<>();
	    ArrayList<analysisDAO> fpctAvgLeft_opponentTeam = new ArrayList<>();

	    // Find the player with the highest fpct for each position
	    for (int i = 0; i < defenseList22.size(); i++) {
	        defenseDAO def = defenseList22.get(i);

	        if (def.getTeamName().equals(myTeam) && (def.getPosition().equals("유격수") || def.getPosition().equals("2루수") || def.getPosition().equals("중견수"))) {
	            if (fpctAvgLeft_myTeam.isEmpty()) {
	                analysisDAO dao = new analysisDAO();
	                dao.setTeamName(myTeam);
	                dao.setName(def.getName());
	                dao.setPosition(def.getPosition());
	                dao.setFpct(def.getFpct());
	                fpctAvgLeft_myTeam.add(dao);
	            } else {
	                analysisDAO highestFpctPlayer = fpctAvgLeft_myTeam.get(0);
	                if (def.getFpct() > highestFpctPlayer.getFpct()) {
	                    analysisDAO dao = new analysisDAO();
	                    dao.setTeamName(myTeam);
	                    dao.setName(def.getName());
	                    dao.setPosition(def.getPosition());
	                    dao.setFpct(def.getFpct());
	                    fpctAvgLeft_myTeam.clear();
	                    fpctAvgLeft_myTeam.add(dao);
	                } else if (def.getFpct() == highestFpctPlayer.getFpct()) {
	                    analysisDAO dao = new analysisDAO();
	                    dao.setTeamName(myTeam);
	                    dao.setName(def.getName());
	                    dao.setPosition(def.getPosition());
	                    dao.setFpct(def.getFpct());
	                    fpctAvgLeft_myTeam.add(dao);
	                }
	            }
	        }

	        if (def.getTeamName().equals(opponentTeam) && (def.getPosition().equals("유격수") || def.getPosition().equals("2루수") || def.getPosition().equals("중견수"))) {
	            if (fpctAvgLeft_opponentTeam.isEmpty()) {
	                analysisDAO dao = new analysisDAO();
	                dao.setTeamName(opponentTeam);
	                dao.setName(def.getName());
	                dao.setPosition(def.getPosition());
	                dao.setFpct(def.getFpct());
	                fpctAvgLeft_opponentTeam.add(dao);
	            } else {
	                analysisDAO highestFpctPlayer = fpctAvgLeft_opponentTeam.get(0);
	                if (def.getFpct() > highestFpctPlayer.getFpct()) {
	                	analysisDAO dao = new analysisDAO();
	                    dao.setTeamName(myTeam);
	                    dao.setName(def.getName());
	                    dao.setPosition(def.getPosition());
	                    dao.setFpct(def.getFpct());
	                    fpctAvgLeft_opponentTeam.add(dao);
	                }
	            }
	        }

	    }
	    return new FpctAvgLeft(fpctAvgLeft_myTeam, fpctAvgLeft_opponentTeam);
	}
	public class FpctAvgUnder {
	    private ArrayList<analysisDAO> fpctAvgUnder_myTeam;
	    private ArrayList<analysisDAO> fpctAvgUnder_opponentTeam;

	    public FpctAvgUnder(ArrayList<analysisDAO> fpctAvgUnder_myTeam, ArrayList<analysisDAO> fpctAvgUnder_opponentTeam) {
	        this.fpctAvgUnder_myTeam = fpctAvgUnder_myTeam;
	        this.fpctAvgUnder_opponentTeam = fpctAvgUnder_opponentTeam;
	    }

	    public ArrayList<analysisDAO> getFpctAvgUnder_myTeam() {
	        return fpctAvgUnder_myTeam;
	    }

	    public ArrayList<analysisDAO> getFpctAvgUnder_opponentTeam() {
	        return fpctAvgUnder_opponentTeam;
	    }
	}

	public FpctAvgUnder getFpctAvgUnder(ArrayList<defenseDAO> defenseList22, String myTeam, String opponentTeam) {
	    ArrayList<analysisDAO> fpctAvgUnder_myTeam = new ArrayList<>();
	    ArrayList<analysisDAO> fpctAvgUnder_opponentTeam = new ArrayList<>();

	    // Find the player with the highest fpct for each position
	    for (int i = 0; i < defenseList22.size(); i++) {
	        defenseDAO def = defenseList22.get(i);

	        if (def.getTeamName().equals(myTeam) && (def.getPosition().equals("유격수") || def.getPosition().equals("2루수") || def.getPosition().equals("중견수"))) {
	            if (fpctAvgUnder_myTeam.isEmpty()) {
	                analysisDAO dao = new analysisDAO();
	                dao.setTeamName(myTeam);
	                dao.setName(def.getName());
	                dao.setPosition(def.getPosition());
	                dao.setFpct(def.getFpct());
	                fpctAvgUnder_myTeam.add(dao);
	            } else {
	                analysisDAO highestFpctPlayer = fpctAvgUnder_myTeam.get(0);
	                if (def.getFpct() > highestFpctPlayer.getFpct()) {
	                    analysisDAO dao = new analysisDAO();
	                    dao.setTeamName(myTeam);
	                    dao.setName(def.getName());
	                    dao.setPosition(def.getPosition());
	                    dao.setFpct(def.getFpct());
	                    fpctAvgUnder_myTeam.clear();
	                    fpctAvgUnder_myTeam.add(dao);
	                } else if (def.getFpct() == highestFpctPlayer.getFpct()) {
	                    analysisDAO dao = new analysisDAO();
	                    dao.setTeamName(myTeam);
	                    dao.setName(def.getName());
	                    dao.setPosition(def.getPosition());
	                    dao.setFpct(def.getFpct());
	                    fpctAvgUnder_myTeam.add(dao);
	                }
	            }
	        }

	        if (def.getTeamName().equals(opponentTeam) && (def.getPosition().equals("유격수") || def.getPosition().equals("2루수") || def.getPosition().equals("중견수"))) {
	            if (fpctAvgUnder_opponentTeam.isEmpty()) {
	                analysisDAO dao = new analysisDAO();
	                dao.setTeamName(opponentTeam);
	                dao.setName(def.getName());
	                dao.setPosition(def.getPosition());
	                dao.setFpct(def.getFpct());
	                fpctAvgUnder_opponentTeam.add(dao);
	            } else {
	                analysisDAO highestFpctPlayer = fpctAvgUnder_opponentTeam.get(0);
	                if (def.getFpct() > highestFpctPlayer.getFpct()) {
	                    analysisDAO dao = new analysisDAO();
	                    dao.setTeamName(opponentTeam);
	                    dao.setName(def.getName());
	                    dao.setPosition(def.getPosition());
	                    dao.setFpct(def.getFpct());
		                fpctAvgUnder_opponentTeam.add(dao);
	                }
	            }
	        }
	    }
	    return new FpctAvgUnder(fpctAvgUnder_myTeam, fpctAvgUnder_opponentTeam);
	}

}