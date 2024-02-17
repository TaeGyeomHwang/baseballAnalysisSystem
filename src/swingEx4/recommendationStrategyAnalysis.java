package swingEx4;

import java.sql.SQLException;

import java.util.*;
import java.util.stream.Collectors;


// ��õ���� �޼ҵ�
public class recommendationStrategyAnalysis {
	DatabaseManager dbmanager = new DatabaseManager();
	
	private ArrayList<analysisDAO> hitterUptrend_myTeam = new ArrayList<>();
    private ArrayList<analysisDAO> hitterDowntrend_myTeam = new ArrayList<>();
    
	public recommendationStrategyAnalysis(String myTeam, String opponentTeam) throws SQLException {
		
		
		ArrayList<playerDAO> playerList = DatabaseManager.getInstance().getPlayerList();			// ��������	
        ArrayList<teamDAO> teamList = DatabaseManager.getInstance().getTeamList();					// 22 �����
        
        // ���߸�� �ҷ���
		ArrayList <String> lineUpRight_myTeam = new rosterAnalysis().aList(myTeam, opponentTeam, "������");		// ��������� Ȩ�� ����Ÿ�� ���ξ�
		ArrayList <String> lineUpLeft_myTeam = new rosterAnalysis().aList(myTeam, opponentTeam, "������");			// ��������� Ȩ�� ����Ÿ�� ���ξ�
		ArrayList <String> lineUpUnder_myTeam = new rosterAnalysis().aList(myTeam, opponentTeam, "���");			// ��������� Ȩ�� ����Ÿ�� ���ξ�
		ArrayList <String> lineUpRight_opponentTeam = new rosterAnalysis().aList(opponentTeam, myTeam, "������");	// ��������� ����� ����Ÿ�� ���ξ�
		ArrayList <String> lineUpLeft_opponentTeam = new rosterAnalysis().aList(opponentTeam, myTeam, "������");	// ��������� ����� ����Ÿ�� ���ξ�
		ArrayList <String> lineUpUnder_opponentTeam = new rosterAnalysis().aList(opponentTeam, myTeam, "���");	// ��������� ����� ����Ÿ�� ���ξ�

        
        ArrayList<hitterDAO> hitterList22 = DatabaseManager.getInstance().getHitterList(2022);		// 22 Ÿ��
        ArrayList<hitterDAO> hitterList21 = DatabaseManager.getInstance().getHitterList(2021);		// 21 Ÿ��
        ArrayList<hitterDAO> hitterList20 = DatabaseManager.getInstance().getHitterList(2020);		// 20 Ÿ��
        ArrayList<hitterDAO> hitterList19 = DatabaseManager.getInstance().getHitterList(2019);		// 19 Ÿ��
        ArrayList<hitterDAO> hitterList18 = DatabaseManager.getInstance().getHitterList(2018);		// 18 Ÿ��
        
        ArrayList<pitcherDAO> pitcherList22= DatabaseManager.getInstance().getPitcherList(2022);		// 22 ����	
        ArrayList<pitcherDAO> pitcherList21 = DatabaseManager.getInstance().getPitcherList(2021);		// 21 ����
        ArrayList<pitcherDAO> pitcherList20 = DatabaseManager.getInstance().getPitcherList(2020);		// 20 ����
        ArrayList<pitcherDAO> pitcherList19 = DatabaseManager.getInstance().getPitcherList(2019);		// 19 ����
        ArrayList<pitcherDAO> pitcherList18 = DatabaseManager.getInstance().getPitcherList(2018);		// 18 ����
        
        ArrayList<hitterDAO> hitterOpponentList22 = DatabaseManager.getInstance().getHitterOpponentList(2022);	// 22 ������� Ÿ��		
        ArrayList<hitterDAO> hitterTypeList22 = DatabaseManager.getInstance().getHitterTypeList(2022);			// 22 ������ Ÿ��
        ArrayList<pitcherDAO> pitcherOpponentList22 = DatabaseManager.getInstance().getPitcherOpponentList(2022);	// 22 ������� ����	
        ArrayList<pitcherDAO> pitcherTypeList22 = DatabaseManager.getInstance().getPitcherTypeList(2022);		// 22 ������ ����
        
        ArrayList<defenseDAO> defenseList22 = DatabaseManager.getInstance().getDefenseList(2022);		// 22 ����
        ArrayList<runnerDAO> runnerList22 = DatabaseManager.getInstance().getRunnerList(2022);		// 22 �ַ�
        
        

        
        /** ���� �� �ٽ�, �����å��, Ż����, ���̺�, Ÿ��, Ÿ��, Ȩ��, ����, OPS, XR, WHIP, GPA 1�� ~ 5�� */
        
        ArrayList <analysisDAO> rankW_myTeam = new ArrayList <analysisDAO>();			// �츮��
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
        ArrayList <analysisDAO> rankPhba_myTeam = new ArrayList <analysisDAO>();	// ��ŸŸ��

        
        ArrayList <analysisDAO> rankW_opponentTeam = new ArrayList <analysisDAO>();		// �����	
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
        ArrayList <analysisDAO> rankPhba_opponentTeam = new ArrayList <analysisDAO>();	// ��ŸŸ��

        ArrayList <analysisDAO> WeakLeftHand_myTeam = new ArrayList <analysisDAO>();	// ��������� ���� Ÿ��
        ArrayList <analysisDAO> WeakUnderHand_myTeam = new ArrayList <analysisDAO>();	// ������ ���� Ÿ��
        ArrayList <analysisDAO> WeakLeftHitter_myTeam = new ArrayList <analysisDAO>();	// ��Ÿ�ڻ�� ���� ����
        ArrayList <analysisDAO> rankCsPer_myTeam = new ArrayList<analysisDAO>();		// �츮�� ���������� 
        ArrayList <analysisDAO> rankCsPer_opponentTeam = new ArrayList<analysisDAO>();	// ����� ����������
        ArrayList <analysisDAO> teamCompare_myTeam = new ArrayList <analysisDAO>();			// �츮�� �����
        ArrayList <analysisDAO> teamCompare_opponentTeam = new ArrayList <analysisDAO>();	// ����� �����
        
        ArrayList <analysisDAO> pitcherUptrend_myTeam = new ArrayList<analysisDAO>();		// �츮�� ��¼� ���� 
        ArrayList <analysisDAO> pitcherDowntrend_myTeam = new ArrayList<analysisDAO>();		// �츮�� �϶��� ����
        ArrayList <analysisDAO> pitcherUptrend_opponentTeam = new ArrayList<analysisDAO>();		// ����� ��¼� ���� 
        ArrayList <analysisDAO> pitcherDowntrend_opponentTeam = new ArrayList<analysisDAO>();	// ����� �϶��� ����

        
        ArrayList <analysisDAO> fpctAvgLeft_myTeam = new ArrayList <analysisDAO>();			// �츮�� ������� �߾Ӽ���ɷ�(��ռ�����)
        ArrayList <analysisDAO> fpctAvgRight_myTeam = new ArrayList <analysisDAO>();		// �츮�� ������� �߾Ӽ���ɷ�(��ռ�����)
        ArrayList <analysisDAO> fpctAvgUnder_myTeam = new ArrayList <analysisDAO>();		// �츮�� ������ �߾Ӽ���ɷ�(��ռ�����)
        ArrayList <analysisDAO> fpctAvgLeft_opponentTeam = new ArrayList <analysisDAO>();	// ����� ������� �߾Ӽ���ɷ�(��ռ�����)
        ArrayList <analysisDAO> fpctAvgRight_opponentTeam = new ArrayList <analysisDAO>();	// ����� ������� �߾Ӽ���ɷ�(��ռ�����)
        ArrayList <analysisDAO> fpctAvgUnder_opponentTeam = new ArrayList <analysisDAO>();	// ����� ������ �߾Ӽ���ɷ�(��ռ�����)

        
        // ����������
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
        //����
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
        
    	// Ÿ�ڱ�� ����
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
        
        
        /** ���� ���ξ��� ���ͼ� �߾� ����ɷ��� ���� -> ���� ���ξ��� �������, �������, ������ �������� ���� �� �����Ƿ� �� ���� ������. */
        
        // ������� ���� ���ξ� �߾� ����ɷ�
        for(int k = 0; k < defenseList22.size(); k++) {
			defenseDAO def = defenseList22.get(k);
	   		if (def.getTeamName().equals(myTeam)){
	   			
	   			try {
	   				for(int j = 0; j < lineUpRight_myTeam.size(); j++) {
	   			   		String dao1 = lineUpRight_myTeam.get(j);
		   			
		   				if (dao1.equals(def.getName()) && def.getPosition().equals("���ݼ�")) {	
		   					analysisDAO dao = new analysisDAO();
		 	    			
		 	    			dao.setName(def.getName());
		 	    			dao.setPosition(def.getPosition());
		 	    			dao.setFpct(def.getFpct());
		 	    			
		 	    			fpctAvgRight_myTeam.add(dao);
						}
		   				if (dao1.equals(def.getName()) && def.getPosition().equals("2���")) {	
		   					analysisDAO dao = new analysisDAO();
		 	    			
		 	    			dao.setName(def.getName());
		 	    			dao.setPosition(def.getPosition());
		 	    			dao.setFpct(def.getFpct());
		 	    			
		 	    			fpctAvgRight_myTeam.add(dao);
						}
		   				if (dao1.equals(def.getName()) && def.getPosition().equals("�߰߼�")) {	
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
		   			
		   				if (dao1.equals(def.getName()) && def.getPosition().equals("���ݼ�")) {	
		   					analysisDAO dao = new analysisDAO();
		 	    			
		 	    			dao.setName(def.getName());
		 	    			dao.setPosition(def.getPosition());
		 	    			dao.setFpct(def.getFpct());
		 	    			
		 	    			fpctAvgRight_opponentTeam.add(dao);
						}
		   				if (dao1.equals(def.getName()) && def.getPosition().equals("2���")) {	
		   					analysisDAO dao = new analysisDAO();
		 	    			
		 	    			dao.setName(def.getName());
		 	    			dao.setPosition(def.getPosition());
		 	    			dao.setFpct(def.getFpct());
		 	    			
		 	    			fpctAvgRight_opponentTeam.add(dao);
						}
		   				if (dao1.equals(def.getName()) && def.getPosition().equals("�߰߼�")) {	
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
        
        // ������� ���� ���ξ� �߾� ����ɷ�
        for(int k = 0; k < defenseList22.size(); k++) {
			defenseDAO def = defenseList22.get(k);
	   		if (def.getTeamName().equals(myTeam)){
	   			
	   			try {
	   				for(int j = 0; j < lineUpLeft_myTeam.size(); j++) {
	   			   		String dao1 = lineUpLeft_myTeam.get(j);
		   			
		   				if (dao1.equals(def.getName()) && def.getPosition().equals("���ݼ�")) {	
		   					analysisDAO dao = new analysisDAO();
		 	    			
		 	    			dao.setName(def.getName());
		 	    			dao.setPosition(def.getPosition());
		 	    			dao.setFpct(def.getFpct());
		 	    			
		 	    			fpctAvgLeft_myTeam.add(dao);
						}
		   				if (dao1.equals(def.getName()) && def.getPosition().equals("2���")) {	
		   					analysisDAO dao = new analysisDAO();
		 	    			
		 	    			dao.setName(def.getName());
		 	    			dao.setPosition(def.getPosition());
		 	    			dao.setFpct(def.getFpct());
		 	    			
		 	    			fpctAvgLeft_myTeam.add(dao);
						}
		   				if (dao1.equals(def.getName()) && def.getPosition().equals("�߰߼�")) {	
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
		   			
		   				if (dao1.equals(def.getName()) && def.getPosition().equals("���ݼ�")) {	
		   					analysisDAO dao = new analysisDAO();
		 	    			
		 	    			dao.setName(def.getName());
		 	    			dao.setPosition(def.getPosition());
		 	    			dao.setFpct(def.getFpct());
		 	    			
		 	    			fpctAvgLeft_opponentTeam.add(dao);
						}
		   				if (dao1.equals(def.getName()) && def.getPosition().equals("2���")) {	
		   					analysisDAO dao = new analysisDAO();
		 	    			
		 	    			dao.setName(def.getName());
		 	    			dao.setPosition(def.getPosition());
		 	    			dao.setFpct(def.getFpct());
		 	    			
		 	    			fpctAvgLeft_opponentTeam.add(dao);
						}
		   				if (dao1.equals(def.getName()) && def.getPosition().equals("�߰߼�")) {	
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
        // ������ ���� ���ξ� �߾� ����ɷ�
        for(int k = 0; k < defenseList22.size(); k++) {
			defenseDAO def = defenseList22.get(k);
	   		if (def.getTeamName().equals(myTeam)){
	   			
	   			try {
	   				for(int j = 0; j < lineUpUnder_myTeam.size(); j++) {
	   			   		String dao1 = lineUpUnder_myTeam.get(j);
		   			
		   				if (dao1.equals(def.getName()) && def.getPosition().equals("���ݼ�")) {	
		   					analysisDAO dao = new analysisDAO();
		 	    			
		 	    			dao.setName(def.getName());
		 	    			dao.setPosition(def.getPosition());
		 	    			dao.setFpct(def.getFpct());
		 	    			
		 	    			fpctAvgUnder_myTeam.add(dao);
						}
		   				if (dao1.equals(def.getName()) && def.getPosition().equals("2���")) {	
		   					analysisDAO dao = new analysisDAO();
		 	    			
		 	    			dao.setName(def.getName());
		 	    			dao.setPosition(def.getPosition());
		 	    			dao.setFpct(def.getFpct());
		 	    			
		 	    			fpctAvgUnder_myTeam.add(dao);
						}
		   				if (dao1.equals(def.getName()) && def.getPosition().equals("�߰߼�")) {	
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
		   			
		   				if (dao1.equals(def.getName()) && def.getPosition().equals("���ݼ�")) {	
		   					analysisDAO dao = new analysisDAO();
		 	    			
		 	    			dao.setName(def.getName());
		 	    			dao.setPosition(def.getPosition());
		 	    			dao.setFpct(def.getFpct());
		 	    			
		 	    			fpctAvgUnder_opponentTeam.add(dao);
						}
		   				if (dao1.equals(def.getName()) && def.getPosition().equals("2���")) {	
		   					analysisDAO dao = new analysisDAO();
		 	    			
		 	    			dao.setName(def.getName());
		 	    			dao.setPosition(def.getPosition());
		 	    			dao.setFpct(def.getFpct());
		 	    			
		 	    			fpctAvgUnder_opponentTeam.add(dao);
						}
		   				if (dao1.equals(def.getName()) && def.getPosition().equals("�߰߼�")) {	
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
        
        
        
        // ������, ��� ��� ���� Ÿ��
        for(int i = 0; i < hitterTypeList22.size() ; i++) {
 	    	hitterDAO hitter = hitterTypeList22.get(i);
 	    	if (hitter.getTeamName().equals(myTeam) && hitter.getPitcherType().equals("������") && hitter.getAb()> 20) {
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
 	    	if (hitter.getTeamName().equals(myTeam) && hitter.getPitcherType().equals("���") && hitter.getAb() > 10) {
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
        
        /** sorting �� �ʿ��� �κ� -> collections.sort �޼ҵ带 ���� �������� �� �������� ���� */
        
        // myTeam ����
        Collections.sort(rankW_myTeam, new Comparator<analysisDAO>() {				// �ٽ�
            @Override
            public int compare(analysisDAO p1, analysisDAO p2) {
                return p2.getW() - p1.getW();
            }
        });	
        Collections.sort(rankEra_myTeam, new Comparator<analysisDAO>() {			// �����å��
            @Override
            public int compare(analysisDAO p1, analysisDAO p2) {
                return Double.compare(p1.getEra() , p2.getEra());
            }
        });
        Collections.sort(rankSo_myTeam, new Comparator<analysisDAO>() {				// ����
            @Override
            public int compare(analysisDAO p1, analysisDAO p2) {
                return p2.getSo() - p1.getSo();
            }
        });
        Collections.sort(rankSv_myTeam, new Comparator<analysisDAO>() {				// ���̺�
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
        
        Collections.sort(WeakLeftHitter_myTeam, new Comparator<analysisDAO>() {		// ��Ÿ�ڻ�� ���� ����
            @Override
            public int compare(analysisDAO p1, analysisDAO p2) {
                return Double.compare(p2.getAverage() , p1.getAverage());
            }
        });
        Collections.sort(WeakLeftHand_myTeam, new Comparator<analysisDAO>() {		// ��������� ���� Ÿ��
            @Override
            public int compare(analysisDAO p1, analysisDAO p2) {
                return Double.compare(p1.getAverage(), p2.getAverage());
            }
        });
        Collections.sort(WeakUnderHand_myTeam, new Comparator<analysisDAO>() {		// ��������� ���� Ÿ��
            @Override
            public int compare(analysisDAO p1, analysisDAO p2) {
                return Double.compare(p1.getAverage(), p2.getAverage());
            }
        });
        
        
        Collections.sort(rankAvg_myTeam, new Comparator<analysisDAO>() {			// Ÿ��
            @Override
            public int compare(analysisDAO p1, analysisDAO p2) {
                return Double.compare(p2.getAverage(), p1.getAverage());
            }
        });
        Collections.sort(rankRbi_myTeam, new Comparator<analysisDAO>() {			// Ÿ��
            @Override
            public int compare(analysisDAO p1, analysisDAO p2) {
                return p2.getRbi() - p1.getRbi();
            }
        });
        Collections.sort(rankHr_myTeam, new Comparator<analysisDAO>() {				// Ȩ��
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
        Collections.sort(rankSb_myTeam, new Comparator<analysisDAO>() {				// ����
            @Override
            public int compare(analysisDAO p1, analysisDAO p2) {
                return p2.getSb() - p1.getSb();
            }
        });
        Collections.sort(rankSbPer_myTeam, new Comparator<analysisDAO>() {			// ����������
            @Override
            public int compare(analysisDAO p1, analysisDAO p2) {
                return Double.compare(p2.getSbPer(), p1.getSbPer());
            }
        });
        
        Collections.sort(rankPhba_myTeam, new Comparator<analysisDAO>() {			// ��ŸŸ��
            @Override
            public int compare(analysisDAO p1, analysisDAO p2) {
                return Double.compare(p2.getPhba(), p1.getPhba());
            }
        });
        Collections.sort(rankCsPer_myTeam, new Comparator<analysisDAO>() {		// ����������
            @Override
            public int compare(analysisDAO p1, analysisDAO p2) {
                return Double.compare(p2.getCsPer() , p1.getCsPer());
            }
        });
        
        
        // opponentTeam ����
        Collections.sort(rankW_opponentTeam, new Comparator<analysisDAO>() {		// �ٽ�
            @Override
            public int compare(analysisDAO p1, analysisDAO p2) {
                return p2.getW() - p1.getW();
            }
        });	
        Collections.sort(rankEra_opponentTeam, new Comparator<analysisDAO>() {		// �����å��
            @Override
            public int compare(analysisDAO p1, analysisDAO p2) {
            	return Double.compare(p1.getEra() , p2.getEra());
            }
        });
        Collections.sort(rankSo_opponentTeam, new Comparator<analysisDAO>() {		// ����
            @Override
            public int compare(analysisDAO p1, analysisDAO p2) {
                return p2.getSo() - p1.getSo();
            }
        });
        Collections.sort(rankSv_opponentTeam, new Comparator<analysisDAO>() {		// ���̺�
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
        Collections.sort(rankAvg_opponentTeam, new Comparator<analysisDAO>() {		// Ÿ��
            @Override
            public int compare(analysisDAO p1, analysisDAO p2) {
                return Double.compare(p2.getAverage(), p1.getAverage());
            }
        });
        Collections.sort(rankRbi_opponentTeam, new Comparator<analysisDAO>() {		// Ÿ��
            @Override
            public int compare(analysisDAO p1, analysisDAO p2) {
                return p2.getRbi() - p1.getRbi();
            }
        });
        Collections.sort(rankHr_opponentTeam, new Comparator<analysisDAO>() {		// Ȩ��
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
        Collections.sort(rankSb_opponentTeam, new Comparator<analysisDAO>() {		// ����
            @Override
            public int compare(analysisDAO p1, analysisDAO p2) {
                return p2.getSb() - p1.getSb();
            }
        });	
        Collections.sort(rankSbPer_opponentTeam, new Comparator<analysisDAO>() {	// ����������
            @Override
            public int compare(analysisDAO p1, analysisDAO p2) {
                return Double.compare(p2.getSbPer(), p1.getSbPer());
            }
        });
        Collections.sort(rankPhba_opponentTeam, new Comparator<analysisDAO>() {		// ��ŸŸ��
            @Override
            public int compare(analysisDAO p1, analysisDAO p2) {
                return Double.compare(p2.getPhba(), p1.getPhba());
            }
        });
        Collections.sort(rankCsPer_opponentTeam, new Comparator<analysisDAO>() {		// ����������
            @Override
            public int compare(analysisDAO p1, analysisDAO p2) {
                return Double.compare(p2.getCsPer() , p1.getCsPer());
            }
        });
        
        /** print �κ� -> rankW_myTeam, rankEra_myTeam ���� get(i) ���ؼ� ������ ����. */ 
        
        float fpctSum = 0;	// fpct(������) ����� ���� ���� ����
        
        for(int i = 0; i < fpctAvgRight_myTeam.size() ; i++) {
        	analysisDAO dao = fpctAvgRight_myTeam.get(i);
        	fpctSum += dao.getFpct();
   		}
        
        
        fpctSum = 0;	// �ʱ�ȭ
        for(int i = 0; i < fpctAvgLeft_myTeam.size() ; i++) {
        	analysisDAO dao = fpctAvgLeft_myTeam.get(i);
        	fpctSum += dao.getFpct();
   		}
        
        
        fpctSum = 0;	// �ʱ�ȭ
        for(int i = 0; i < fpctAvgUnder_myTeam.size() ; i++) {
        	analysisDAO dao = fpctAvgUnder_myTeam.get(i);
        	fpctSum += dao.getFpct();
   		}
        
        
        	
        fpctSum = 0;	// fpct(������) ����� ���� ���� ����
        
        for(int i = 0; i < fpctAvgRight_opponentTeam.size() ; i++) {
        	analysisDAO dao = fpctAvgRight_opponentTeam.get(i);
        	fpctSum += dao.getFpct();
   		}
        
        
        fpctSum = 0;	// �ʱ�ȭ
        for(int i = 0; i < fpctAvgLeft_opponentTeam.size() ; i++) {
        	analysisDAO dao = fpctAvgLeft_opponentTeam.get(i);
        	fpctSum += dao.getFpct();
   		}
       
        
        fpctSum = 0;	// �ʱ�ȭ
        for(int i = 0; i < fpctAvgUnder_opponentTeam.size() ; i++) {
        	analysisDAO dao = fpctAvgUnder_opponentTeam.get(i);
        	fpctSum += dao.getFpct();
   		}
        
        
        
	}
	
	/** Ÿ�� ��¼�, �϶��� - ops */
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
	                    if(hit22.getOps() > hit21.getOps()) {       // �츮�� ops ��¼� Ÿ��
	                        analysisDAO dao = new analysisDAO();
	                        dao.setTeamName(hit22.getTeamName());
	                        dao.setName(hit22.getName());
	                        dao.setOps22(hit22.getOps());
	                        dao.setOps21(hit21.getOps());
	                        hitterUptrend_myTeam.add(dao);
	                    }
	                    if(hit22.getOps() < hit21.getOps()) {       // �츮�� ops �϶��� Ÿ��
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
	
	
	
	/** ���� ��¼�, �϶��� - whip */
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
	                    if(pit22.getWhip() > pit21.getWhip()) {       // �츮�� Whip ��¼� ����
	                        analysisDAO dao = new analysisDAO();
	                        dao.setTeamName(pit22.getTeamName());
	                        dao.setName(pit22.getName());
	                        dao.setWhip22(pit22.getWhip());
	                        dao.setWhip21(pit21.getWhip());
	                        pitcherUptrend_myTeam.add(dao);
	                    }
	                    if(pit22.getWhip() < pit21.getWhip()) {       // �츮�� Whip �϶��� ����
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
	
	
	/** ��Ÿ�� ��� ���� ���� */
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
	        
	        if (pitch.getTeamName().equals(myTeam) && pitch.getHitterType().equals("��Ÿ��")) {
	            try {
	                analysisDAO dao = new analysisDAO();
	                
	                dao.setTeamName(pitch.getTeamName());
	                dao.setName(pitch.getName());
	                dao.setAverage(pitch.getAvg());

	                weakLeftPitcher_myTeam.add(dao);
	            } catch (NullPointerException e) {
	            }
	        }

	        if (pitch.getTeamName().equals(opponentTeam) && pitch.getHitterType().equals("��Ÿ��")) {
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
	/** ������ ��� ���� Ÿ�� */
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
	        
	        if (hitter.getTeamName().equals(myTeam) && hitter.getPitcherType().equals("������") && hitter.getAb() > 20) {
	            try {
	                analysisDAO dao = new analysisDAO();
	                
	                dao.setTeamName(hitter.getTeamName());
	                dao.setName(hitter.getName());
	                dao.setAverage(hitter.getAverage());
	                
	                weakLeftHand_myTeam.add(dao);
	            } catch (NullPointerException e) { 
	            }
	        }
	        if (hitter.getTeamName().equals(opponentTeam) && hitter.getPitcherType().equals("������") && hitter.getAb() > 20) {
	            try {
	                analysisDAO dao = new analysisDAO();
	                
	                dao.setTeamName(hitter.getTeamName());
	                dao.setName(hitter.getName());
	                dao.setAverage(hitter.getAverage());
	                
	                weakLeftHand_opponentTeam.add(dao);
	            } catch (NullPointerException e) { 
	            }
	        }
	        
	        if (hitter.getTeamName().equals(myTeam) && hitter.getPitcherType().equals("���") && hitter.getAb() > 10) {
	            try {
	                analysisDAO dao = new analysisDAO();
	                
	                dao.setTeamName(hitter.getTeamName());
	                dao.setName(hitter.getName());
	                dao.setAverage(hitter.getAverage());
	                
	                weakUnderHand_myTeam.add(dao);
	            } catch (NullPointerException e) { 
	            }
	        }
	        if (hitter.getTeamName().equals(opponentTeam) && hitter.getPitcherType().equals("���") && hitter.getAb() > 10) {
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
	
	/**���� �߾� ����ɷ�*/
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

	        if (def.getTeamName().equals(myTeam) && (def.getPosition().equals("���ݼ�") || def.getPosition().equals("2���") || def.getPosition().equals("�߰߼�"))) {
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

	        if (def.getTeamName().equals(opponentTeam) && (def.getPosition().equals("���ݼ�") || def.getPosition().equals("2���") || def.getPosition().equals("�߰߼�"))) {
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

	        if (def.getTeamName().equals(myTeam) && (def.getPosition().equals("���ݼ�") || def.getPosition().equals("2���") || def.getPosition().equals("�߰߼�"))) {
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

	        if (def.getTeamName().equals(opponentTeam) && (def.getPosition().equals("���ݼ�") || def.getPosition().equals("2���") || def.getPosition().equals("�߰߼�"))) {
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

	        if (def.getTeamName().equals(myTeam) && (def.getPosition().equals("���ݼ�") || def.getPosition().equals("2���") || def.getPosition().equals("�߰߼�"))) {
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

	        if (def.getTeamName().equals(opponentTeam) && (def.getPosition().equals("���ݼ�") || def.getPosition().equals("2���") || def.getPosition().equals("�߰߼�"))) {
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