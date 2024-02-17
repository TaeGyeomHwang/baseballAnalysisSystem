package swingEx4;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.lang.NullPointerException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import swingEx4.recommendationStrategyAnalysis.HitterTrend;
import swingEx4.recommendationStrategyAnalysis.PitcherTrend;
import swingEx4.recommendationStrategyAnalysis.WeakLeftPitcher;

public class rosterAnalysis {
    
		ArrayList<lineUpDAO> lineUp = new ArrayList<lineUpDAO>(Arrays.asList(new lineUpDAO(), new lineUpDAO(), new lineUpDAO(),
   	   			new lineUpDAO(), new lineUpDAO(), new lineUpDAO(), new lineUpDAO(), new lineUpDAO(), new lineUpDAO()));
	
		private static rosterAnalysis instance = null;
		
		public static synchronized rosterAnalysis getInstance(String myTeam, String opponentTeam, String pitcherType) throws SQLException {	//	synchronized ���� �ν��Ͻ� ������ ������κ��� �̷���� >> ��Ƽ������� �����ؼ� ���ջ��� �ذ�
	        if (instance == null) {
	            instance = new rosterAnalysis(myTeam, opponentTeam, pitcherType);
	        }
	        return instance;
	    }
		public rosterAnalysis () {}
		
	   	ArrayList <String> list = new ArrayList <String> ();										// �� ������ ����Ÿ�ڸ� ��� ����Ʈ
	   	
		public rosterAnalysis(String myTeam, String opponentTeam, String pitcherType) throws SQLException {
			
        ArrayList<playerDAO> playerList = DatabaseManager.getInstance().getPlayerList();			// �������� �ν��Ͻ� ������
        //MyTableModel model = new MyTableModel(playerList);
        //MyTableModel.printTableModel(model); 
        
        ArrayList<hitterDAO> hitterList22 = DatabaseManager.getInstance().getHitterList(2022);		// 22���� Ÿ�ڱ�� �ν��Ͻ� ������
        ArrayList<hitterDAO> hitterList20 = DatabaseManager.getInstance().getHitterList(2020);		// 20���� Ÿ�ڱ�� �ν��Ͻ� ������
        ArrayList<pitcherDAO> pitcherList21 = DatabaseManager.getInstance().getPitcherList(2021);		// 21���� ������� �ν��Ͻ� ������
        ArrayList<defenseDAO> defenseList22 = DatabaseManager.getInstance().getDefenseList(2022);		// 22���� ������ �ν��Ͻ� ������
        ArrayList<runnerDAO> runnerList22 = DatabaseManager.getInstance().getRunnerList(2022);		// 19���� �ַ��� �ν��Ͻ� ������
        ArrayList<teamDAO> teamList = DatabaseManager.getInstance().getTeamList();					// ����� �ν��Ͻ� ������
        ArrayList<pitcherDAO> pitcherList22 = DatabaseManager.getInstance().getPitcherList(2022);
        
        
        // �߰� - ��� ��Ȳ�� Ŭ���� 
        ArrayList<hitterDAO> hitterOpponentList22 = DatabaseManager.getInstance().getHitterOpponentList(2022);		// 22���� ������� Ÿ�ڱ�� �ν��Ͻ� ������
        ArrayList<hitterDAO> hitterTypeList22 = DatabaseManager.getInstance().getHitterTypeList(2022);				// 22���� ���������� Ÿ�ڱ�� �ν��Ͻ� ������
        ArrayList<pitcherDAO> pitcherOpponentList22 = DatabaseManager.getInstance().getPitcherOpponentList(2022);		// 22���� ������� ������� �ν��Ͻ� ������
        ArrayList<pitcherDAO> pitcherTypeList22 = DatabaseManager.getInstance().getPitcherOpponentList(2022);			// 22���� ���������� ������� �ν��Ͻ� ������
        
    
        	/** ������ ���� ������ ���ϱ� (��Ÿ ����) */
        
		   	int tmp1 = 0, tmp2 = 0, tmp3 = 0, tmp4 = 0, tmp5 = 0, tmp6 = 0, tmp7 = 0, tmp8 = 0, tmp9 = 0;								// Ÿ�ڼ��߸�� �ִپ�Ÿ ���ϴ� ����
		   	float tmp10 = 0, tmp11 = 0, tmp12 = 0, tmp13 = 0, tmp14 = 0;	int tmp15 = 0, tmp16 = 0, tmp17 = 0, tmp18 = 0, tmp19 = 0;	// ������� �ִ��̴�,Ȧ��,���̺� ���ϴº���
		   	
		   	// 
		   	String leftFielder = ""; String centerFielder = ""; String rightFielder = "";  String designatedHitter = "";
		   	String firstBaseman = ""; String secondBaseman = ""; String thirdBaseman = ""; String shortStop = ""; String catcher = "";	
		   	String startingPitcher1 = "";	String startingPitcher2 = "";	String startingPitcher3 = "";	String startingPitcher4 = "";	String startingPitcher5 = "";
		   	String closer = "";		String relief1 = "";	String relief2 = "";	String relief3 = "";	String relief4 = "";	String relief5 = "";
		   	
		   	//ArrayList <String> list = new ArrayList <String> ();										// �� ������ ����Ÿ�ڸ� ��� ����Ʈ
		   	ArrayList <String> pitchingList = new ArrayList <String> ();								// �������(����5, �߰�4, ������1)�� ��� ����Ʈ
		   	ArrayList <String> subList = new ArrayList <String> (Arrays.asList("","","","","","",""));	// ���� ���߼����� ���ܵ� �ĺ�Ÿ�� ����Ʈ
		   	
		   	
	     	    for(int i = 0; i < hitterOpponentList22.size() ; i++) {
	     	    	hitterDAO dao = hitterOpponentList22.get(i);												// ���� - 22�� Ÿ�ڱ�� -> 22�� ������� Ÿ�ڱ��
	     	    	if (dao.getTeamName().equals(myTeam) && dao.getOpponent().equals(opponentTeam)) {			// ���� : myTeam, ������� : opponentTeam
	     	    		try {
		     	    		for(int j = 0; j < defenseList22.size() ; j++) {
		     	    			defenseDAO dao1 = defenseList22.get(j);
		     	    			
			        	   		if(dao1.getPosition().equals("���ݼ�") && dao1.getName().equals(dao.getName())) {					// defense�� hitter ���� && ������ ���ݼ�
			        	   			if (dao.getH() > tmp1 && dao1.getG() > 30) {												// ��Ÿ�� ���� ����, 30��� �̻� ����														// 30��� �̻� ����
			        	   				tmp1 = dao.getH();
			        	   				shortStop = dao.getName();
			        	   			}
		        	   			}
			        	   	}
		        	   		
	     	    		}
	     	    		catch(NullPointerException e) { 
	     	    		}
	     	    	}
	     	    }
	     	    
    	   		list.add(0, shortStop);		// ���ݼ� list�� �߰�
    	   		
		        
	     	  	for(int i = 0; i < hitterOpponentList22.size() ; i++) {
	     	    	hitterDAO dao = hitterOpponentList22.get(i);
	     	    	if (dao.getTeamName().equals(myTeam) && dao.getOpponent().equals(opponentTeam)) {
	     	    		try {
		     	    		for(int j = 0; j < defenseList22.size() ; j++) {
		     	    			defenseDAO dao1 = defenseList22.get(j);
		     	    			
			        	   		if(dao1.getPosition().equals("�߰߼�") && dao1.getName().equals(dao.getName())) {					// defense�� hitter ���� && ������ �߰߼�
			        	   			if (dao.getH() > tmp2 && dao1.getG() > 10) {												// ��Ÿ�� ���� ����, 30��� �̻� ����														// 30��� �̻� ����
			        	   				
			        	   					if(!list.contains(dao.getName())) {
			        	   						tmp2 = dao.getH();
			        	   						centerFielder = dao.getName();
			        	   					}
			        	   				
			        	   			}
		        	   			}
			        	   	}
	     	    		}
		     	    	catch(NullPointerException e) { 
		     	    	}
		        	}
	     	    }
	        	list.add(1, centerFielder);		// �߰߼� list�� �߰�
		     	 
	        	for(int i = 0; i < hitterOpponentList22.size() ; i++) {
	     	    	hitterDAO dao = hitterOpponentList22.get(i);
	     	    	if (dao.getTeamName().equals(myTeam) && dao.getOpponent().equals(opponentTeam)) {
	     	    		try {
			        	   	for(int j = 0; j < defenseList22.size() ; j++) {
			     	    		defenseDAO dao1 = defenseList22.get(j);
			     	    		
				        	   	if(dao1.getPosition().equals("2���") && dao1.getName().equals(dao.getName())) {					// defense�� hitter ���� && ������ 2���
				        	   		if (dao.getH() > tmp3 && dao1.getG() > 30) {												// ��Ÿ�� ���� ����, 30��� �̻� ����														// 30��� �̻� ����
				        	   				
				        	   				if(!list.contains(dao.getName())) {
				        	   					tmp3 = dao.getH();
				        	   					secondBaseman = dao.getName();
				        	   					}
				        	   			
				        	   		}
			        	   		}
			        	   	}
			        	}
			        	catch(NullPointerException e) { 
			     	    }
	     	    	}
	     	    }
			    list.add(2, secondBaseman);			// 2��� list�� �߰�
			       
			    for(int i = 0; i < hitterOpponentList22.size() ; i++) {
	     	    	hitterDAO dao = hitterOpponentList22.get(i);
	     	    	if (dao.getTeamName().equals(myTeam) && dao.getOpponent().equals(opponentTeam)) {
		     	    		try {
			        	   		
			        	    for(int j = 0; j < defenseList22.size() ; j++) {
				     	    	defenseDAO dao1 = defenseList22.get(j);
					        	if(dao1.getPosition().equals("3���") && dao1.getName().equals(dao.getName())) {					// defense�� hitter ���� && ������ 3���
					        	   	if (dao.getH() > tmp4 && dao1.getG() > 30) {													// ��Ÿ�� ���� ����, 30��� �̻� ����														// 30��� �̻� ����
					        	   				
					        	   			if(!list.contains(dao.getName())) {
					        	   				tmp4 = dao.getH();
					        	   				thirdBaseman = dao.getName();
					        	   				}
					        	   	}
				        	   	}
				        	}
			        	    }catch(NullPointerException e) { 
				     	    }
		     	    	}
		     	    }		
			        list.add(3, thirdBaseman);		// 3��� list�� �߰�
			        
			        for(int i = 0; i < hitterOpponentList22.size() ; i++) {
		     	    	hitterDAO dao = hitterOpponentList22.get(i);
		     	    	if (dao.getTeamName().equals(myTeam) && dao.getOpponent().equals(opponentTeam)) {
		     	    		try {	   	
			        	    for(int j = 0; j < defenseList22.size() ; j++) {
				     	    	defenseDAO dao1 = defenseList22.get(j);
					        	if(dao1.getPosition().equals("���ͼ�") && dao1.getName().equals(dao.getName())) {					// defense�� hitter ���� && ������ ���ͼ�
					        	   	if (dao.getH() > tmp5 && dao1.getG() > 30) {													// ��Ÿ�� ���� ����, 30��� �̻� ����														// 30��� �̻� ����
					        	   				
					        	   			if(!list.contains(dao.getName())) {
					        	   				tmp5 = dao.getH();
					        	   				rightFielder = dao.getName();
					        	   				}
					        	   	}
				        	   	}
				        	}
			        	    }catch(NullPointerException e) { 
				     	    }
		     	    	}
		     	   	}
			        list.add(4, rightFielder);		// ���ͼ� list�� �߰�
			        	
			        for(int i = 0; i < hitterOpponentList22.size() ; i++) {
		     	    	hitterDAO dao = hitterOpponentList22.get(i);
		     	    	if (dao.getTeamName().equals(myTeam) && dao.getOpponent().equals(opponentTeam)) {
			     	    	try {
			        	    for(int j = 0; j < defenseList22.size() ; j++) {
				     	    	defenseDAO dao1 = defenseList22.get(j);
					        	if(dao1.getPosition().equals("���ͼ�") && dao1.getName().equals(dao.getName())) {					// defense�� hitter ���� && ������ ���ͼ�
					        	   	if (dao.getH() > tmp6 && dao1.getG() > 30) {													// ��Ÿ�� ���� ����, 30��� �̻� ����														// 30��� �̻� ����
					        	   				
					        	   			if(!list.contains(dao.getName())) {		// list�� �̹� �ִ� ������ ���� -> �ߺ��Ǵ� ���� ����
					        	   				tmp6 = dao.getH();
					        	   				leftFielder = dao.getName();
					        	   				}
					        	   		
					        	   	}
				        	   	}
				        	}
			        	    }catch(NullPointerException e) { 
				     	    }
			     	    }
			     	}
			        list.add(5, leftFielder);		// list�� ���ͼ� �߰�
			        
			        for(int i = 0; i < hitterOpponentList22.size() ; i++) {
		     	    	hitterDAO dao = hitterOpponentList22.get(i);
		     	    	if (dao.getTeamName().equals(myTeam) && dao.getOpponent().equals(opponentTeam)) {
			     	    		try {	   	
			     	    		for(int j = 0; j < defenseList22.size() ; j++) {
				     	    	defenseDAO dao1 = defenseList22.get(j);
					        	if(dao1.getPosition().equals("1���") && dao1.getName().equals(dao.getName())) {					// defense�� hitter ���� && ������ 1���
					        	   	if (dao.getH() > tmp7 && dao1.getG() > 30) {													// ��Ÿ�� ���� ����, 30��� �̻� ����														// 30��� �̻� ����
					        	   				
					        	   			if(!list.contains(dao.getName())) {
					        	   				tmp7 = dao.getH();
					        	   				firstBaseman = dao.getName();
					        	   				}
					        	   		
					        	   	}
				        	   	}
				        	}
			     	    		}catch(NullPointerException e) { 
				     	    }
			     	    }
			     	}
			     	    		
			        list.add(6, firstBaseman);		//list�� 1��� �߰�
			        for(int i = 0; i < hitterOpponentList22.size() ; i++) {
		     	    	hitterDAO dao = hitterOpponentList22.get(i);
		     	    	if (dao.getTeamName().equals(myTeam) && dao.getOpponent().equals(opponentTeam)) {
			     	    		try {	   	
			        	    for(int j = 0; j < defenseList22.size() ; j++) {
				     	    	defenseDAO dao1 = defenseList22.get(j);
					        	if(dao1.getPosition().equals("����") && dao1.getName().equals(dao.getName())) {					// defense�� hitter ���� && ������ ����
					        	   	if (dao.getH() > tmp8 && dao1.getG() > 30) {													// ��Ÿ�� ���� ����, 30��� �̻� ����														// 30��� �̻� ����
					        	   				
					        	   			if (!list.contains(dao.getName())) {
					        	   				tmp8 = dao.getH();
					        	   				catcher = dao.getName();
					        	   				}
					        	   		
					        	   	}
				        	   	}
				        	}
			     	    	}catch(NullPointerException e) { 
				     	    }
			     	    }
			     	}
			        list.add(7, catcher);				//list�� ���� �߰�
			        
			        for(int i = 0; i < hitterOpponentList22.size() ; i++) {
		     	    	hitterDAO dao = hitterOpponentList22.get(i);
		     	    	if (dao.getTeamName().equals(myTeam) && dao.getOpponent().equals(opponentTeam)) {
			     	    		try {
			     	    		for(int j = 0; j < defenseList22.size() ; j++) {
				     	    	defenseDAO dao1 = defenseList22.get(j);
					        	if(dao1.getName().equals(dao.getName())) {													// defense�� hitter ����
					        	   	if (dao.getH() > tmp9 && dao1.getG() > 30) {											// ��Ÿ�� ���� ����, 30��� �̻� ����														// 30��� �̻� ����
					        	   				
					        	   			if(!list.contains(dao.getName())) {
					        	   				tmp9 = dao.getH();
					        	   				designatedHitter = dao.getName();
					        	   				}
					        	   		
					        	   	}
				        	   	}
				        	}
			     	    	}catch(NullPointerException e) { 
			     	    		
			     	    	}
			     	   	}
			     	}
			        list.add(8, designatedHitter);		//list�� ����Ÿ�� �߰�
			        
			        for(int i = 0; i < pitcherList22.size() ; i++) {
		     	    	pitcherDAO dao = pitcherList22.get(i);						// pitcherOpponentList ���������� ��,�����,���� �� �ٽ����� ��� ���� �Ǿ�Ÿ,��Ȩ�� �� �����͹ۿ� x
			     	    	try {
					        	 if (dao.getTeamName().equals(myTeam) && dao.getIp() > tmp10) {			// �� : myTeam && �̴� �ִ�
					        	   		if(!pitchingList.contains(dao.getName())) {						// pitchingList�� �̹� �ִ¼��� ���� -> ���� �ߺ� ����
					        	   			tmp10 = dao.getIp();
					        	   			startingPitcher1 = dao.getName();
					        	   			}
					        	   	}
			     	    	}catch(NullPointerException e) { 
			     	    		
			     	    	}
			     	}
			        pitchingList.add(0, startingPitcher1);												// pitchingList�� 1���� �߰�
			        
			        for(int i = 0; i < pitcherList22.size() ; i++) {
		     	    	pitcherDAO dao = pitcherList22.get(i);
			     	    		try {
					        	   	if (dao.getTeamName().equals(myTeam) && dao.getIp() > tmp11) {		// ���� �̴׸��� ����	 
					        	   			if(!pitchingList.contains(dao.getName())) {					
					        	   				tmp11 = dao.getIp();
					        	   				startingPitcher2 = dao.getName();
					        	   				}
					        	   	}
			     	    	}catch(NullPointerException e) { 
			     	    		
			     	    	}
			     	}
			        pitchingList.add(1, startingPitcher2);												// pitchingList�� 2���� �߰�
			        
			        for(int i = 0; i < pitcherList22.size() ; i++) {
		     	    	pitcherDAO dao = pitcherList22.get(i);
			     	    		try {
					        	   	if (dao.getTeamName().equals(myTeam) && dao.getIp() > tmp12) {		//���� �̴��� ���� ����		
					        	   			if(!pitchingList.contains(dao.getName())) {
					        	   				tmp12 = dao.getIp();
					        	   				startingPitcher3 = dao.getName();
					        	   				}
					        	   	}
			     	    	}catch(NullPointerException e) { 
			     	    		
			     	    	}
			     	}
			        pitchingList.add(2, startingPitcher3);												// pitchingList�� 3���� �߰�					
			        
			        for(int i = 0; i < pitcherList22.size() ; i++) {
		     	    	pitcherDAO dao = pitcherList22.get(i);
			     	    		try {
					        	   	if (dao.getTeamName().equals(myTeam) && dao.getIp() > tmp13) {		// ���� �̴��� ���� ����
					        	   			if(!pitchingList.contains(dao.getName())) {
					        	   				tmp13 = dao.getIp();
					        	   				startingPitcher4 = dao.getName();
					        	   				}
					        	   	}
			     	    	}catch(NullPointerException e) { 
			     	    		
			     	    	}
			     	}
			        pitchingList.add(3, startingPitcher4);												// pitchingList�� 4���� �߰�
			        
			        for(int i = 0; i < pitcherList22.size() ; i++) {
		     	    	pitcherDAO dao = pitcherList22.get(i);
			     	    		try {
					        	   	if (dao.getTeamName().equals(myTeam) && dao.getIp() > tmp14) {		// ���� �̴׸��� ����
					        	   			if(!pitchingList.contains(dao.getName())) {
					        	   				tmp14 = dao.getIp();
					        	   				startingPitcher5 = dao.getName();
					        	   				}
					        	   	}
			     	    	}catch(NullPointerException e) { 
			     	    		
			     	    	}
			     	}
			        pitchingList.add(4, startingPitcher5);												// pitchingList�� 5���� �߰�
			        
			        for(int i = 0; i < pitcherList22.size() ; i++) {
		     	    	pitcherDAO dao = pitcherList22.get(i);
			     	    		try {
					        	   	if (dao.getTeamName().equals(myTeam) && dao.getSv() > tmp15) {		// ������ ���� ���� save�� ���� ����
					        	   			if(!pitchingList.contains(dao.getName())) {
					        	   				tmp15 = dao.getSv();
					        	   				closer = dao.getName();
					        	   				}
					        	   	}
			     	    	}catch(NullPointerException e) { 
			     	    		
			     	    	}
			     	}
			        pitchingList.add(5, closer);														// pitchingList�� ���������� �߰�
			        
			        for(int i = 0; i < pitcherList22.size() ; i++) {
		     	    	pitcherDAO dao = pitcherList22.get(i);
			     	    		try {
					        	   	if (dao.getTeamName().equals(myTeam) && dao.getHld() > tmp16) {		// Ȧ�尡 ������ ���帹�� ����
					        	   			if(!pitchingList.contains(dao.getName())) {
					        	   				tmp16 = dao.getHld();
					        	   				relief1 = dao.getName();
					        	   				}
					        	   	}
			     	    	}catch(NullPointerException e) { 
			     	    		
			     	    	}
			     	}
			        pitchingList.add(6, relief1);														// pitchingList�� �߰�����1 �߰�
			        for(int i = 0; i < pitcherList22.size() ; i++) {
		     	    	pitcherDAO dao = pitcherList22.get(i);
			     	    		try {
					        	   	if (dao.getTeamName().equals(myTeam) && dao.getHld() > tmp17) {		// �״��� Ȧ�尡 ��������
					        	   			if(!pitchingList.contains(dao.getName())) {
					        	   				tmp17 = dao.getHld();
					        	   				relief2 = dao.getName();
					        	   				}
					        	   	}
			     	    	}catch(NullPointerException e) { 
			     	    		
			     	    	}
			     	}
			        pitchingList.add(7, relief2);														// pitchingList�� �߰�����2 �߰�
			        for(int i = 0; i < pitcherList22.size() ; i++) {
		     	    	pitcherDAO dao = pitcherList22.get(i);
			     	    		try {
					        	   	if (dao.getTeamName().equals(myTeam) && dao.getHld() > tmp18) {		// �״��� Ȧ�帹�� ����
					        	   			if(!pitchingList.contains(dao.getName())) {
					        	   				tmp18 = dao.getHld();
					        	   				relief3 = dao.getName();
					        	   				}
					        	   	}
			     	    	}catch(NullPointerException e) { 
			     	    		
			     	    	}
			     	}
			        pitchingList.add(8, relief3);														// pitchingList�� �߰�����3 �߰�
			        for(int i = 0; i < pitcherList22.size() ; i++) {
		     	    	pitcherDAO dao = pitcherList22.get(i);
			     	    		try {
					        	   	if (dao.getTeamName().equals(myTeam) && dao.getHld() > tmp19) {		// �״��� Ȧ�帹�� ����
					        	   			if(!pitchingList.contains(dao.getName())) {
					        	   				tmp19 = dao.getHld();
					        	   				relief4 = dao.getName();
					        	   				}
					        	   	}
			     	    	}catch(NullPointerException e) { 
			     	    		
			     	    	}
			     	}
			        pitchingList.add(9, relief4);		
			        
			     /** subList(�ĺ�����) ����	!! -> ���� - 20���� �����ϴ� ������ ������ �ش��ϴ� ���� ����� �ȵ� -> �ĺ������� 30��⿡�� 20���� ���� */
			        
			     int Tmp1 = 0, Tmp2 = 0, Tmp3 = 0, Tmp4 = 0, Tmp5 = 0, Tmp6 = 0, Tmp7 = 0;					// �ĺ����� ���� ���� �ִپ�Ÿ ��� ����
			     String outFielder1 = ""; String outFielder2 = "";	String subCatcher = "";	String subFirstBaseman = "";
			     String subShortStop = ""; String subSecondBaseman = "";	String subThirdBaseman = "";
			     
			     String [] subPosition =new String[] {"���ݼ�","�ܾ߼�1","�ܾ߼�2","2���","3���","1���","����"};		// ���߿� println �� �� ���� ��������� ��� �迭
			     
			     for(int i = 0; i < hitterList22.size() ; i++) {
		     	    	hitterDAO dao = hitterList22.get(i);								// �ĺ������� ���, ������� ��� ������� ���߿� ���� ������ hitterList22 �������� ����
		     	    	if (dao.getTeamName().equals(myTeam)) {
		     	    		try {
			     	    		for(int j = 0; j < defenseList22.size() ; j++) {
			     	    			defenseDAO dao1 = defenseList22.get(j);
				        	   		if(dao1.getPosition().equals("���ݼ�") && dao1.getName().equals(dao.getName())) {					// defense�� hitter ���� && ������ ���ݼ�
				        	   			if (dao.getH() > Tmp1 && dao.getG() > 20) {													// ��Ÿ�� ���� ����, 20��� �̻� ����														// 30��� �̻� ����
				        	   				
				        	   					if(!list.contains(dao.getName())) {			// �̹� list(���߼�����)�� �����ϴ� ���� ���� -> ���� �ߺ� ����
				        	   						Tmp1 = dao.getH();
				        	   						subShortStop = dao.getName();
				        	   					}
				        	   			}
			        	   			}
				        	   	}
		     	    		}
			     	    	catch(NullPointerException e) { 
			     	    	}
			        	}
		     	    }
		        subList.set(0, subShortStop);				// �������ݼ� subList�� �߰�
		        
		        for(int i = 0; i < hitterList22.size() ; i++) {
	     	    	hitterDAO dao = hitterList22.get(i);
	     	    	if (dao.getTeamName().equals(myTeam)) {
	     	    		try {
		     	    		for(int j = 0; j < playerList.size() ; j++) {
		     	    			playerDAO dao1 = playerList.get(j);														// �ܾ��ĺ� ������ �������� ���о��� "�ܾ߼�"�� ������� ����
			        	   		if(dao1.getPosition().equals("�ܾ߼�") && dao1.getName().equals(dao.getName())) {			// hitter�� player ���� && ������ �ܾ߼�
			        	   			if (dao.getH() > Tmp2 && dao.getG() > 20) {											// ��Ÿ�� ���� ����, 20��� �̻� ����														// 30��� �̻� ����
			        	   				
			        	   					if(!list.contains(dao.getName()) && !subList.contains(dao.getName())) {
			        	   						Tmp2 = dao.getH();
			        	   						outFielder1 = dao.getName();
			        	   					}
			        	   			}
		        	   			}
			        	   	}
	     	    		}
		     	    	catch(NullPointerException e) { 
		     	    	}
		        	}
	     	    }
	        	subList.set(1, outFielder1);				// �ܾ߼�1 subList�� �߰�
	        	
	        	for(int i = 0; i < hitterList22.size() ; i++) {
	     	    	hitterDAO dao = hitterList22.get(i);
	     	    	if (dao.getTeamName().equals(myTeam)) {
	     	    		try {
		     	    		for(int j = 0; j < playerList.size() ; j++) {
		     	    			playerDAO dao1 = playerList.get(j);
			        	   		if(dao1.getPosition().equals("�ܾ߼�") && dao1.getName().equals(dao.getName())) {				// hitter�� player ���� && ������ �ܾ߼�
			        	   			if (dao.getH() > Tmp3 && dao.getG() > 20) {												// ��Ÿ�� ���� ����, 20��� �̻� ����														// 30��� �̻� ����
			        	   				
			        	   					if(!list.contains(dao.getName()) && !subList.contains(dao.getName())) {
			        	   						Tmp3 = dao.getH();
			        	   						outFielder2 = dao.getName();
			        	   					}
			        	   			}
		        	   			}
			        	   	}
	     	    		}
		     	    	catch(NullPointerException e) { 
		     	    	}
		        	}
	     	    }
	        	subList.set(2, outFielder2);				// �ܾ߼�2 subList�� �߰�
	        	
	        	for(int i = 0; i < hitterList22.size() ; i++) {
	     	    	hitterDAO dao = hitterList22.get(i);
	     	    	if (dao.getTeamName().equals(myTeam)) {
	     	    		try {
		     	    		for(int j = 0; j < defenseList22.size() ; j++) {
		     	    			defenseDAO dao1 = defenseList22.get(j);
			        	   		if(dao1.getPosition().equals("2���") && dao1.getName().equals(dao.getName())) {					// defense�� hitter ���� && ������ 2���
			        	   			if (dao.getH() > Tmp4 && dao.getG() > 20) {													// ��Ÿ�� ���� ����, 20��� �̻� ����														// 30��� �̻� ����
			        	   				
			        	   					if(!list.contains(dao.getName()) && !subList.contains(dao.getName())) {
			        	   						Tmp4 = dao.getH();
			        	   						subSecondBaseman = dao.getName();
			        	   					}
			        	   			}
		        	   			}
			        	   	}
	     	    		}
		     	    	catch(NullPointerException e) { 
		     	    	}
		        	}
	     	    }
	        	subList.set(3, subSecondBaseman);			// ����2��� subList�� �߰�
	        	
	        	for(int i = 0; i < hitterList22.size() ; i++) {
	     	    	hitterDAO dao = hitterList22.get(i);
	     	    	if (dao.getTeamName().equals(myTeam)) {
	     	    		try {
		     	    		for(int j = 0; j < defenseList22.size() ; j++) {
		     	    			defenseDAO dao1 = defenseList22.get(j);
			        	   		if(dao1.getPosition().equals("3���") && dao1.getName().equals(dao.getName())) {					// defense�� hitter ���� && ������ 3���
			        	   			if (dao.getH() > Tmp5 && dao.getG() > 20) {													// ��Ÿ�� ���� ����, 20��� �̻� ����														// 30��� �̻� ����
			        	   				
			        	   					if(!list.contains(dao.getName())) {
			        	   						Tmp5 = dao.getH();
			        	   						subThirdBaseman = dao.getName();
			        	   					}
			        	   			}
		        	   			}
			        	   	}
	     	    		}
		     	    	catch(NullPointerException e) { 
		     	    	}
		        	}
	     	    }
	        	subList.set(4, subThirdBaseman);				// ����3��� subList�� �߰�
	        	
	        	for(int i = 0; i < hitterList22.size() ; i++) {
	     	    	hitterDAO dao = hitterList22.get(i);
	     	    	if (dao.getTeamName().equals(myTeam)) {
	     	    		try {
		     	    		for(int j = 0; j < defenseList22.size() ; j++) {
		     	    			defenseDAO dao1 = defenseList22.get(j);
			        	   		if(dao1.getPosition().equals("1���") && dao1.getName().equals(dao.getName())) {					// defense�� hitter ���� && ������ 1���
			        	   			if (dao.getH() > Tmp6 && dao.getG() > 20) {													// ��Ÿ�� ���� ����, 20��� �̻� ����														// 30��� �̻� ����
			        	   				
			        	   					if(!list.contains(dao.getName())) {
			        	   						Tmp6 = dao.getH();
			        	   						subFirstBaseman = dao.getName();
			        	   					}
			        	   			}
		        	   			}
			        	   	}
	     	    		}
		     	    	catch(NullPointerException e) { 
		     	    	}
		        	}
	     	    }
	        	subList.set(5, subFirstBaseman);						// ����1��� subList�� �߰�
		        
	        	for(int i = 0; i < hitterList22.size() ; i++) {
	     	    	hitterDAO dao = hitterList22.get(i);
	     	    	if (dao.getTeamName().equals(myTeam)) {
	     	    		try {
		     	    		for(int j = 0; j < defenseList22.size() ; j++) {
		     	    			defenseDAO dao1 = defenseList22.get(j);
			        	   		if(dao1.getPosition().equals("����") && dao1.getName().equals(dao.getName())) {					// defense�� hitter ���� && ������ ����
			        	   			if (dao.getH() > Tmp7 && dao.getG() > 20) {													// ��Ÿ�� ���� ����, 20��� �̻� ����														// 30��� �̻� ����
			        	   				
			        	   					if(!list.contains(dao.getName())) {
			        	   						Tmp7 = dao.getH();
			        	   						subCatcher = dao.getName();
			        	   					}
			        	   			}
		        	   			}
			        	   	}
	     	    		}
		     	    	catch(NullPointerException e) { 
		     	    	}
		        	}
	     	    }
	        	subList.set(6, subCatcher);						// �������� subList�� �߰�
	        	  
	        	
       	   		/** ���񼱹߸�� �������� Ÿ�� ���ϱ� */
       	   							
	        	//--> (h + 2b + 3b *2 + hr *3 / Ÿ��)
       	   		// �ְ� �����			--> (��Ÿ + ����(bb) + �籸(hbp)) / (Ÿ�� + ���� + �籸 + ����(����ö���))
       	   		
       	   		ArrayList <lineUpDAO> lineUp = new ArrayList<lineUpDAO>(Arrays.asList(new lineUpDAO(), new lineUpDAO(), new lineUpDAO(),
       	   			new lineUpDAO(), new lineUpDAO(), new lineUpDAO(), new lineUpDAO(), new lineUpDAO(), new lineUpDAO()));
       	   		
       	   		
       	   		ArrayList <lineUpDAO> MaxSb = new ArrayList<lineUpDAO>();
       	   		ArrayList <lineUpDAO> MaxHr = new ArrayList<lineUpDAO>();
       	   		ArrayList <lineUpDAO> MaxRbi = new ArrayList<lineUpDAO>();
       	   		ArrayList <lineUpDAO> MaxSac = new ArrayList<lineUpDAO>();
       	   		ArrayList <lineUpDAO> MaxSlg1 = new ArrayList<lineUpDAO>();
       	   		ArrayList <lineUpDAO> MaxSlg2 = new ArrayList<lineUpDAO>();
       	   		ArrayList <lineUpDAO> MaxSlg3 = new ArrayList<lineUpDAO>();
       	   		ArrayList <lineUpDAO> MaxObp = new ArrayList<lineUpDAO>();
       	   		ArrayList <lineUpDAO> others = new ArrayList<lineUpDAO>();
       	   		
       	   		HashSet<String> selectedPlayers = new HashSet<>();

       	   		for(int j = 0; j < list.size(); j++) {
		   			String dao1 = list.get(j);
		   			
	   				try {
	   					for(int k = 0; k < runnerList22.size(); k++) {
	   						runnerDAO run = runnerList22.get(k);
	   						for(int i = 0; i < defenseList22.size(); i ++) {
	   							defenseDAO def = defenseList22.get(i);
	   						
	   							if (dao1.equals(run.getName()) && dao1.equals(def.getName())) {					// runnerList22�� defenseStart(���񼱹�) ����
	   								lineUpDAO dao = new lineUpDAO();
	   								
	   								dao.setName(run.getName());
	   								dao.setSb(run.getSb());
	   								dao.setPosition(def.getPosition());
	   								dao.setFpct(def.getFpct());
	   								
	   								MaxSb.add(dao);
	   							}
	   						}
	   					}
	   				}catch(NullPointerException e) { 
     	    		}
	   			}
       	   		Collections.sort(MaxSb, new Comparator<lineUpDAO>() {				
                @Override
                public int compare(lineUpDAO p1, lineUpDAO p2) {
                    return p2.getSb() - p1.getSb();
                }
       	   		});
       	   		selectedPlayers.add(MaxSb.get(0).getName());
       	   		lineUp.set(0, MaxSb.get(0));			// 1��Ÿ��
	   			
       	   		for(int j = 0; j < list.size(); j++) {
       	   			String dao1 = list.get(j);
       	   			
       	   			try {	
	   				for(int i = 0; i < hitterTypeList22.size() ; i++) {
	   		   			hitterDAO hit = hitterTypeList22.get(i);				// ���������� Ÿ�ڱ�� �ҷ���
	   					
	   		   			for(int k = 0; k < defenseList22.size(); k++) {
	   						defenseDAO def = defenseList22.get(k);
	   		   				if (dao1.equals(hit.getName()) && hit.getPitcherType().equals(pitcherType)) {	// ���� && �������� : pitcherType
	   							if (dao1.equals(def.getName())) {						// 9�� �� ���� Ȩ���� ���� ����
	   								for(int l = 0; l < lineUp.size(); l++) {
	   									if (!selectedPlayers.contains(dao1)) {
	   										lineUpDAO dao = new lineUpDAO();
	   									
	   										dao.setName(hit.getName());
	   										dao.setHr(hit.getHr());
	   										dao.setPosition(def.getPosition());
	   										dao.setFpct(def.getFpct());
	   									
	   										MaxHr.add(dao);
	   									}
	   								}
	   							}
	   						}
	   					}
       	   			}
       	   			}catch(NullPointerException e) { 
     	    		}
       	   		}
       	   	Collections.sort(MaxHr, new Comparator<lineUpDAO>() {				
                @Override
                public int compare(lineUpDAO p1, lineUpDAO p2) {
                    return p2.getHr() - p1.getHr();
                }
       	   		});
       	   	selectedPlayers.add(MaxHr.get(0).getName());
	   		lineUp.set(3, MaxHr.get(0));	// 4��Ÿ��
       	   	
	   			for(int j = 0; j < list.size(); j++) {
       	   			String dao1 = list.get(j);
       	   			
       	   			try {	
	   				for(int i = 0; i < hitterTypeList22.size() ; i++) {
	   		   			hitterDAO hit = hitterTypeList22.get(i);				// ���������� Ÿ�ڱ�� �ҷ���
	   					
	   		   			for(int k = 0; k < defenseList22.size(); k++) {
	   						defenseDAO def = defenseList22.get(k);
	   		   				if (dao1.equals(hit.getName()) && hit.getPitcherType().equals(pitcherType)) {	// ���� && �������� : pitcherType
	   							if (dao1.equals(def.getName())) {						// 9�� �� ���� Ȩ���� ���� ����
	   								for(int l = 0; l < lineUp.size(); l++) {
	   									if (!selectedPlayers.contains(dao1)) {
	   									lineUpDAO dao = new lineUpDAO();
	   									
	   									dao.setName(hit.getName());
	   									dao.setRbi(hit.getRbi());
	   									dao.setPosition(def.getPosition());
	   									dao.setFpct(def.getFpct());
	   									
	   									MaxRbi.add(dao);
	   								}
	   							}
	   							}
	   						}
	   					}
       	   			}
       	   			}catch(NullPointerException e) { 
     	    		}
       	   		}
	   			Collections.sort(MaxRbi, new Comparator<lineUpDAO>() {				
	                @Override
	                public int compare(lineUpDAO p1, lineUpDAO p2) {
	                    return p2.getRbi() - p1.getRbi();
	                }
	       	   		});
	   			selectedPlayers.add(MaxRbi.get(0).getName());
	   			lineUp.set(2, MaxRbi.get(0));		// 3��Ÿ��
   					
	   			for(int j = 0; j < list.size(); j++) {
       	   			String dao1 = list.get(j);
       	   			
       	   			try {	
	   				for(int i = 0; i < hitterTypeList22.size() ; i++) {
	   		   			hitterDAO hit = hitterTypeList22.get(i);				// ���������� Ÿ�ڱ�� �ҷ���
	   					
	   		   			for(int k = 0; k < defenseList22.size(); k++) {
	   						defenseDAO def = defenseList22.get(k);
	   		   				if (dao1.equals(hit.getName()) && hit.getPitcherType().equals(pitcherType)) {	// ���� && �������� : pitcherType
	   							if (dao1.equals(def.getName())) {						// 9�� �� ���� Ȩ���� ���� ����
	   								for(int l = 0; l < lineUp.size(); l++) {
	   									if (!selectedPlayers.contains(dao1)) {
	   									lineUpDAO dao = new lineUpDAO();
	   									
	   									dao.setName(hit.getName());
	   									dao.setSac(hit.getSac());
	   									dao.setPosition(def.getPosition());
	   									dao.setFpct(def.getFpct());
	   									
	   									MaxSac.add(dao);
	   								}
	   							}
	   							}
	   						}
	   					}
       	   			}
       	   			}catch(NullPointerException e) { 
     	    		}
       	   		}
	   			Collections.sort(MaxSac, new Comparator<lineUpDAO>() {				
	                @Override
	                public int compare(lineUpDAO p1, lineUpDAO p2) {
	                    return p2.getSac() - p1.getSac();
	                }
	       	   		});
	   			selectedPlayers.add(MaxSac.get(0).getName());
	   			lineUp.set(1, MaxSac.get(0));		//2��Ÿ��
	   				
	   			for(int j = 0; j < list.size(); j++) {
       	   			String dao1 = list.get(j);
       	   			
       	   			try {	
	   				for(int i = 0; i < hitterTypeList22.size() ; i++) {
	   		   			hitterDAO hit = hitterTypeList22.get(i);				// ���������� Ÿ�ڱ�� �ҷ���
	   					
	   		   			for(int k = 0; k < defenseList22.size(); k++) {			//(h + 2b + 3b *2 + hr *3 / Ÿ��)
	   						defenseDAO def = defenseList22.get(k);
	   		   				if (dao1.equals(hit.getName()) && hit.getPitcherType().equals(pitcherType)) {	// ���� && �������� : pitcherType
	   							if (dao1.equals(def.getName())) {						// 9�� �� ���� Ȩ���� ���� ����
	   								for(int l = 0; l < lineUp.size(); l++) {
	   									if (!selectedPlayers.contains(dao1)) {
	   									lineUpDAO dao = new lineUpDAO();
	   									
	   									dao.setName(hit.getName());
	   									dao.setSlg((float)(hit.getH() + (hit.getDoubleBase()) + (hit.getTripleBase())*2 + hit.getHr()*3)/ (float)hit.getAb()  );
	   									dao.setPosition(def.getPosition());
	   									dao.setFpct(def.getFpct());
	   									
	   									MaxSlg1.add(dao);
	   								}
	   							}
	   		   				}
	   						}
	   					}
       	   			}
       	   			}catch(NullPointerException e) { 
     	    		}
       	   		}
	   			Collections.sort(MaxSlg1, new Comparator<lineUpDAO>() {		
	   	            @Override
	   	            public int compare(lineUpDAO p1, lineUpDAO p2) {
	   	                return Double.compare(p2.getSlg(), p1.getSlg());
	   	            }
	   	        });
	   			selectedPlayers.add(MaxSlg1.get(0).getName());
	   			lineUp.set(4, MaxSlg1.get(0));		// 5��Ÿ��
	   			
	   			for(int j = 0; j < list.size(); j++) {
       	   			String dao1 = list.get(j);
       	   			
       	   			try {	
	   				for(int i = 0; i < hitterTypeList22.size() ; i++) {
	   		   			hitterDAO hit = hitterTypeList22.get(i);				// ���������� Ÿ�ڱ�� �ҷ���
	   					
	   		   			for(int k = 0; k < defenseList22.size(); k++) {
	   						defenseDAO def = defenseList22.get(k);
	   		   				if (dao1.equals(hit.getName()) && hit.getPitcherType().equals(pitcherType)) {	// ���� && �������� : pitcherType
	   							if (dao1.equals(def.getName())) {						// 9�� �� ���� Ȩ���� ���� ����
	   								for(int l = 0; l < lineUp.size(); l++) {
	   									if (!selectedPlayers.contains(dao1)) {
	   									lineUpDAO dao = new lineUpDAO();
	   									
	   									dao.setName(hit.getName());
	   									dao.setSlg((float)(hit.getH() + (hit.getDoubleBase()) + (hit.getTripleBase())*2 + hit.getHr()*3)/ (float)hit.getAb()  );
	   									dao.setPosition(def.getPosition());
	   									dao.setFpct(def.getFpct());
	   									
	   									MaxSlg2.add(dao);
	   								}
	   							}
	   							}
	   						}
	   					}
       	   			}
       	   			}catch(NullPointerException e) { 
     	    		}
       	   		}
	   			Collections.sort(MaxSlg2, new Comparator<lineUpDAO>() {		
	   	            @Override
	   	            public int compare(lineUpDAO p1, lineUpDAO p2) {
	   	                return Double.compare(p2.getSlg(), p1.getSlg());
	   	            }
	   	        });
	   			selectedPlayers.add(MaxSlg2.get(0).getName());
	   			lineUp.set(5, MaxSlg2.get(0));		// 6��Ÿ��
	   				
	   			for(int j = 0; j < list.size(); j++) {
       	   			String dao1 = list.get(j);
       	   			
       	   			try {	
	   				for(int i = 0; i < hitterTypeList22.size() ; i++) {
	   		   			hitterDAO hit = hitterTypeList22.get(i);				// ���������� Ÿ�ڱ�� �ҷ���
	   					
	   		   			for(int k = 0; k < defenseList22.size(); k++) {
	   						defenseDAO def = defenseList22.get(k);
	   		   				if (dao1.equals(hit.getName()) && hit.getPitcherType().equals(pitcherType)) {	// ���� && �������� : pitcherType
	   							if (dao1.equals(def.getName())) {						// 9�� �� ���� Ȩ���� ���� ����
	   								for(int l = 0; l < lineUp.size(); l++) {
	   									if (!selectedPlayers.contains(dao1)) {
	   									lineUpDAO dao = new lineUpDAO();
	   									
	   									dao.setName(hit.getName());
	   									dao.setSlg((float)(hit.getH() + (hit.getDoubleBase()) + (hit.getTripleBase())*2 + hit.getHr()*3)/ (float)hit.getAb()  );
	   									dao.setPosition(def.getPosition());
	   									dao.setFpct(def.getFpct());
	   									
	   									MaxSlg3.add(dao);
	   								}
	   							}
	   							}
	   						}
	   					}
       	   			}
       	   			}catch(NullPointerException e) { 
     	    		}
       	   		}
	   			Collections.sort(MaxSlg3, new Comparator<lineUpDAO>() {		
	   	            @Override
	   	            public int compare(lineUpDAO p1, lineUpDAO p2) {
	   	                return Double.compare(p2.getSlg(), p1.getSlg());
	   	            }
	   	        });
	   			selectedPlayers.add(MaxSlg3.get(0).getName());
	   			lineUp.set(6, MaxSlg3.get(0));		// 7��Ÿ��
	   			
	   			for(int j = 0; j < list.size(); j++) {
       	   			String dao1 = list.get(j);
       	   			
       	   			try {	
	   				for(int i = 0; i < hitterTypeList22.size() ; i++) {
	   		   			hitterDAO hit = hitterTypeList22.get(i);				// ���������� Ÿ�ڱ�� �ҷ���
	   					
	   		   			for(int k = 0; k < defenseList22.size(); k++) {
	   						defenseDAO def = defenseList22.get(k);
	   		   				if (dao1.equals(hit.getName()) && hit.getPitcherType().equals(pitcherType)) {	// ���� && �������� : pitcherType
	   							if (dao1.equals(def.getName())) {						// 9�� �� ���� Ȩ���� ���� ����
	   								for(int l = 0; l < lineUp.size(); l++) {
	   									if (!selectedPlayers.contains(dao1)) {
	   									lineUpDAO dao = new lineUpDAO();
	   									
	   									dao.setName(hit.getName());
	   									dao.setObp((float)(hit.getH() + hit.getBb() + hit.getHbp())/(float)(hit.getAb() + hit.getBb() + hit.getHbp()));
	   									dao.setPosition(def.getPosition());
	   									dao.setFpct(def.getFpct());
	   									
	   									MaxObp.add(dao);
	   								}
	   							}
	   							}
	   						}
	   					}
       	   			}
       	   			}catch(NullPointerException e) { 
     	    		}
       	   		}
	   			Collections.sort(MaxObp, new Comparator<lineUpDAO>() {		
	   	            @Override
	   	            public int compare(lineUpDAO p1, lineUpDAO p2) {
	   	                return Double.compare(p2.getObp(), p1.getObp());
	   	            }
	   	        });
	   			selectedPlayers.add(MaxObp.get(0).getName());
	   			lineUp.set(8, MaxObp.get(0));		// 9��Ÿ��
	   				//(��Ÿ + ����(bb) + �籸(hbp)) / (Ÿ�� + ���� + �籸 + ����(����ö���))
	   				
	   			for(int j = 0; j < list.size(); j++) {
	       	   		String dao1 = list.get(j);
	       	   			
	       	   		try {	
		   				for(int i = 0; i < hitterTypeList22.size() ; i++) {
		   		   			hitterDAO hit = hitterTypeList22.get(i);				// ���������� Ÿ�ڱ�� �ҷ���
		   		   			if (dao1.equals(hit.getName()) && hit.getPitcherType().equals(pitcherType)) {	// ���� && �������� : pitcherType
		   							
		   		   			for(int l = 0; l < lineUp.size(); l++) {
								if (!selectedPlayers.contains(dao1)) {
		   							lineUpDAO dao = new lineUpDAO();
		   									
		   							dao.setName(hit.getName());
		   							dao.setH(hit.getH());
		   							dao.setPosition("����Ÿ��");
		   									
		   							others.add(dao);
		   						}
		   		   			}
		   					}
	       	   			}
	       	   			}catch(NullPointerException e) { 
	     	    		}
	       	   		}
		   		lineUp.set(7, others.get(0));		// 8��Ÿ��
	   			
	   				
	   			
       	   		
       	   		
	     	    System.out.println("\n" + "NC�� ���� ���ξ�" + "\n");
	     	    System.out.println("���ݼ�: " +  shortStop + "\t" + "��Ÿ����: " + tmp1);
		   		System.out.println("�߰߼�: " +  centerFielder + "\t" + "��Ÿ����: " + tmp2);
		   		System.out.println("2���: " +  secondBaseman + "\t" + "��Ÿ����: " + tmp3);
		   		System.out.println("3���: " +  thirdBaseman + "\t" + "��Ÿ����: " + tmp4);
		   		System.out.println("���ͼ�: " +  rightFielder + "\t" + "��Ÿ����: " + tmp5);
		   		System.out.println("���ͼ�: " +  leftFielder + "\t" + "��Ÿ����: " + tmp6);
		   		System.out.println("1���: " +  firstBaseman + "\t" + "��Ÿ����: " + tmp7);
		   		System.out.println("����: " +  catcher + "\t" + "��Ÿ����: " + tmp8);
		   		System.out.println("����Ÿ��: " +  designatedHitter + "\t" + "��Ÿ����: " + tmp9 + "\t");
		   		System.out.println("��������: " + startingPitcher1 + "\t" + "�̴׼�: " + tmp10);
		   		
		   		
//		   		System.out.println("���� �� ���񼱹� ����");
//		   		for(int i = 0; i < list.size() ; i++) {
//		   			System.out.println(list.get(i));
//		        }
		   		System.out.println("Ÿ�� ���� �� ���߸��");
		   		for(int i = 0; i < lineUp.size() ; i++) {
		     	   	lineUpDAO dao = lineUp.get(i);
		     	   	System.out.println(i+1 + "��Ÿ�� "+ "\t" + dao.getPosition() + "\t" + dao.getName());
		        }
		   		System.out.println("�������� : " + (pitchingList.get(0)));
		   		
		   		System.out.println("\n" + "���꼱�� ���");
		   		for(int i = 0; i < subList.size(); i++) {
		   			System.out.println(subPosition[i] + "\t" + subList.get(i));
		   		}
		   		System.out.println("\n" + "���� �����");
		   		for(int i = 1; i < pitchingList.size(); i++) {
		   			if (i < 5) 
		   				System.out.println("��� ���� : " + pitchingList.get(i));
		   			if (i > 5 && i <= 9)
		   				System.out.println("�߰����� : " + pitchingList.get(i));
		   			if (i == 5)
		   				System.out.println("���������� : " + pitchingList.get(i));
		   		}
		}
		public ArrayList<String> aList(String myTeam, String opponentTeam, String pitcherType) throws SQLException {

	   		ArrayList<defenseDAO> defenseList22 = DatabaseManager.getInstance().getDefenseList(2022);		// 22���� ������ �ν��Ͻ� ������
	        	        
	        ArrayList<hitterDAO> hitterOpponentList22 = DatabaseManager.getInstance().getHitterOpponentList(2022);		// 22���� ������� Ÿ�ڱ�� �ν��Ͻ� ������
	       	        
	    
	        	/** ������ ���� ������ ���ϱ� (��Ÿ ����) */
	        
			   	int tmp1 = 0, tmp2 = 0, tmp3 = 0, tmp4 = 0, tmp5 = 0, tmp6 = 0, tmp7 = 0, tmp8 = 0, tmp9 = 0;								// Ÿ�ڼ��߸�� �ִپ�Ÿ ���ϴ� ����
			   		   	
			   	// 
			   	String leftFielder = ""; String centerFielder = ""; String rightFielder = "";  String designatedHitter = "";
			   	String firstBaseman = ""; String secondBaseman = ""; String thirdBaseman = ""; String shortStop = ""; String catcher = "";	
			   		   	
			   	
		     	    for(int i = 0; i < hitterOpponentList22.size() ; i++) {
		     	    	hitterDAO dao = hitterOpponentList22.get(i);												// ���� - 22�� Ÿ�ڱ�� -> 22�� ������� Ÿ�ڱ��
		     	    	if (dao.getTeamName().equals(myTeam) && dao.getOpponent().equals(opponentTeam)) {			// ���� : myTeam, ������� : opponentTeam
		     	    		try {
			     	    		for(int j = 0; j < defenseList22.size() ; j++) {
			     	    			defenseDAO dao1 = defenseList22.get(j);
			     	    			
				        	   		if(dao1.getPosition().equals("���ݼ�") && dao1.getName().equals(dao.getName())) {					// defense�� hitter ���� && ������ ���ݼ�
				        	   			if (dao.getH() > tmp1 && dao1.getG() > 30) {												// ��Ÿ�� ���� ����, 30��� �̻� ����														// 30��� �̻� ����
				        	   				tmp1 = dao.getH();
				        	   				shortStop = dao.getName();
				        	   			}
			        	   			}
				        	   	}
			        	   		
		     	    		}
		     	    		catch(NullPointerException e) { 
		     	    		}
		     	    	}
		     	    }
		     	    
	    	   		list.add(0, shortStop);		// ���ݼ� list�� �߰�
	    	   		
			        
		     	  	for(int i = 0; i < hitterOpponentList22.size() ; i++) {
		     	    	hitterDAO dao = hitterOpponentList22.get(i);
		     	    	if (dao.getTeamName().equals(myTeam) && dao.getOpponent().equals(opponentTeam)) {
		     	    		try {
			     	    		for(int j = 0; j < defenseList22.size() ; j++) {
			     	    			defenseDAO dao1 = defenseList22.get(j);
			     	    			
				        	   		if(dao1.getPosition().equals("�߰߼�") && dao1.getName().equals(dao.getName())) {					// defense�� hitter ���� && ������ �߰߼�
				        	   			if (dao.getH() > tmp2 && dao1.getG() > 10) {												// ��Ÿ�� ���� ����, 30��� �̻� ����														// 30��� �̻� ����
				        	   				
				        	   					if(!list.contains(dao.getName())) {
				        	   						tmp2 = dao.getH();
				        	   						centerFielder = dao.getName();
				        	   					}
				        	   				
				        	   			}
			        	   			}
				        	   	}
		     	    		}
			     	    	catch(NullPointerException e) { 
			     	    	}
			        	}
		     	    }
		        	list.add(1, centerFielder);		// �߰߼� list�� �߰�
			     	 
		        	for(int i = 0; i < hitterOpponentList22.size() ; i++) {
		     	    	hitterDAO dao = hitterOpponentList22.get(i);
		     	    	if (dao.getTeamName().equals(myTeam) && dao.getOpponent().equals(opponentTeam)) {
		     	    		try {
				        	   	for(int j = 0; j < defenseList22.size() ; j++) {
				     	    		defenseDAO dao1 = defenseList22.get(j);
				     	    		
					        	   	if(dao1.getPosition().equals("2���") && dao1.getName().equals(dao.getName())) {					// defense�� hitter ���� && ������ 2���
					        	   		if (dao.getH() > tmp3 && dao1.getG() > 30) {												// ��Ÿ�� ���� ����, 30��� �̻� ����														// 30��� �̻� ����
					        	   				
					        	   				if(!list.contains(dao.getName())) {
					        	   					tmp3 = dao.getH();
					        	   					secondBaseman = dao.getName();
					        	   					}
					        	   			
					        	   		}
				        	   		}
				        	   	}
				        	}
				        	catch(NullPointerException e) { 
				     	    }
		     	    	}
		     	    }
				    list.add(2, secondBaseman);			// 2��� list�� �߰�
				       
				    for(int i = 0; i < hitterOpponentList22.size() ; i++) {
		     	    	hitterDAO dao = hitterOpponentList22.get(i);
		     	    	if (dao.getTeamName().equals(myTeam) && dao.getOpponent().equals(opponentTeam)) {
			     	    		try {
				        	   		
				        	    for(int j = 0; j < defenseList22.size() ; j++) {
					     	    	defenseDAO dao1 = defenseList22.get(j);
						        	if(dao1.getPosition().equals("3���") && dao1.getName().equals(dao.getName())) {					// defense�� hitter ���� && ������ 3���
						        	   	if (dao.getH() > tmp4 && dao1.getG() > 30) {													// ��Ÿ�� ���� ����, 30��� �̻� ����														// 30��� �̻� ����
						        	   				
						        	   			if(!list.contains(dao.getName())) {
						        	   				tmp4 = dao.getH();
						        	   				thirdBaseman = dao.getName();
						        	   				}
						        	   	}
					        	   	}
					        	}
				        	    }catch(NullPointerException e) { 
					     	    }
			     	    	}
			     	    }		
				        list.add(3, thirdBaseman);		// 3��� list�� �߰�
				        
				        for(int i = 0; i < hitterOpponentList22.size() ; i++) {
			     	    	hitterDAO dao = hitterOpponentList22.get(i);
			     	    	if (dao.getTeamName().equals(myTeam) && dao.getOpponent().equals(opponentTeam)) {
			     	    		try {	   	
				        	    for(int j = 0; j < defenseList22.size() ; j++) {
					     	    	defenseDAO dao1 = defenseList22.get(j);
						        	if(dao1.getPosition().equals("���ͼ�") && dao1.getName().equals(dao.getName())) {					// defense�� hitter ���� && ������ ���ͼ�
						        	   	if (dao.getH() > tmp5 && dao1.getG() > 30) {													// ��Ÿ�� ���� ����, 30��� �̻� ����														// 30��� �̻� ����
						        	   				
						        	   			if(!list.contains(dao.getName())) {
						        	   				tmp5 = dao.getH();
						        	   				rightFielder = dao.getName();
						        	   				}
						        	   	}
					        	   	}
					        	}
				        	    }catch(NullPointerException e) { 
					     	    }
			     	    	}
			     	   	}
				        list.add(4, rightFielder);		// ���ͼ� list�� �߰�
				        	
				        for(int i = 0; i < hitterOpponentList22.size() ; i++) {
			     	    	hitterDAO dao = hitterOpponentList22.get(i);
			     	    	if (dao.getTeamName().equals(myTeam) && dao.getOpponent().equals(opponentTeam)) {
				     	    	try {
				        	    for(int j = 0; j < defenseList22.size() ; j++) {
					     	    	defenseDAO dao1 = defenseList22.get(j);
						        	if(dao1.getPosition().equals("���ͼ�") && dao1.getName().equals(dao.getName())) {					// defense�� hitter ���� && ������ ���ͼ�
						        	   	if (dao.getH() > tmp6 && dao1.getG() > 30) {													// ��Ÿ�� ���� ����, 30��� �̻� ����														// 30��� �̻� ����
						        	   				
						        	   			if(!list.contains(dao.getName())) {		// list�� �̹� �ִ� ������ ���� -> �ߺ��Ǵ� ���� ����
						        	   				tmp6 = dao.getH();
						        	   				leftFielder = dao.getName();
						        	   				}
						        	   		
						        	   	}
					        	   	}
					        	}
				        	    }catch(NullPointerException e) { 
					     	    }
				     	    }
				     	}
				        list.add(5, leftFielder);		// list�� ���ͼ� �߰�
				        
				        for(int i = 0; i < hitterOpponentList22.size() ; i++) {
			     	    	hitterDAO dao = hitterOpponentList22.get(i);
			     	    	if (dao.getTeamName().equals(myTeam) && dao.getOpponent().equals(opponentTeam)) {
				     	    		try {	   	
				     	    		for(int j = 0; j < defenseList22.size() ; j++) {
					     	    	defenseDAO dao1 = defenseList22.get(j);
						        	if(dao1.getPosition().equals("1���") && dao1.getName().equals(dao.getName())) {					// defense�� hitter ���� && ������ 1���
						        	   	if (dao.getH() > tmp7 && dao1.getG() > 30) {													// ��Ÿ�� ���� ����, 30��� �̻� ����														// 30��� �̻� ����
						        	   				
						        	   			if(!list.contains(dao.getName())) {
						        	   				tmp7 = dao.getH();
						        	   				firstBaseman = dao.getName();
						        	   				}
						        	   		
						        	   	}
					        	   	}
					        	}
				     	    		}catch(NullPointerException e) { 
					     	    }
				     	    }
				     	}
				     	    		
				        list.add(6, firstBaseman);		//list�� 1��� �߰�
				        for(int i = 0; i < hitterOpponentList22.size() ; i++) {
			     	    	hitterDAO dao = hitterOpponentList22.get(i);
			     	    	if (dao.getTeamName().equals(myTeam) && dao.getOpponent().equals(opponentTeam)) {
				     	    		try {	   	
				        	    for(int j = 0; j < defenseList22.size() ; j++) {
					     	    	defenseDAO dao1 = defenseList22.get(j);
						        	if(dao1.getPosition().equals("����") && dao1.getName().equals(dao.getName())) {					// defense�� hitter ���� && ������ ����
						        	   	if (dao.getH() > tmp8 && dao1.getG() > 30) {													// ��Ÿ�� ���� ����, 30��� �̻� ����														// 30��� �̻� ����
						        	   				
						        	   			if (!list.contains(dao.getName())) {
						        	   				tmp8 = dao.getH();
						        	   				catcher = dao.getName();
						        	   				}
						        	   		
						        	   	}
					        	   	}
					        	}
				     	    	}catch(NullPointerException e) { 
					     	    }
				     	    }
				     	}
				        list.add(7, catcher);				//list�� ���� �߰�
				        
				        for(int i = 0; i < hitterOpponentList22.size() ; i++) {
			     	    	hitterDAO dao = hitterOpponentList22.get(i);
			     	    	if (dao.getTeamName().equals(myTeam) && dao.getOpponent().equals(opponentTeam)) {
				     	    		try {
				     	    		for(int j = 0; j < defenseList22.size() ; j++) {
					     	    	defenseDAO dao1 = defenseList22.get(j);
						        	if(dao1.getName().equals(dao.getName())) {													// defense�� hitter ����
						        	   	if (dao.getH() > tmp9 && dao1.getG() > 30) {											// ��Ÿ�� ���� ����, 30��� �̻� ����														// 30��� �̻� ����
						        	   				
						        	   			if(!list.contains(dao.getName())) {
						        	   				tmp9 = dao.getH();
						        	   				designatedHitter = dao.getName();
						        	   				}
						        	   		
						        	   	}
					        	   	}
					        	}
				     	    	}catch(NullPointerException e) { 
				     	    		
				     	    	}
				     	   	}
				     	}
				        list.add(8, designatedHitter);		//list�� ����Ÿ�� �߰�
				        
				        return list;
		}
		public ArrayList<lineUpDAO> normalList(String myTeam, String opponentTeam, ArrayList<hitterDAO> hitterOpponentList22, ArrayList<defenseDAO> defenseList22, ArrayList<runnerDAO> runnerList22) {
		    ArrayList<lineUpDAO> list = new ArrayList<>();
		    

		    String[] positions = {"���ݼ�", "�߰߼�", "2���", "3���", "���ͼ�", "���ͼ�", "1���", "����", "����Ÿ��"};

		    for (int p = 0; p < positions.length; p++) {
		        String position = positions[p];
		        ArrayList<lineUpDAO> topPlayers = new ArrayList<>();
		        for (int rank = 0; rank < 4; rank++) {
		            topPlayers.add(new lineUpDAO("", position, -1, rank + 1));
		        }

		        for (int i = 0; i < hitterOpponentList22.size(); i++) {
		            hitterDAO hitter = hitterOpponentList22.get(i);
		            if (hitter.getTeamName().equals(myTeam) && hitter.getOpponent().equals(opponentTeam)) {
		                for (int j = 0; j < defenseList22.size(); j++) {
		                    defenseDAO defender = defenseList22.get(j);
		                    if (p < 8) {
		                    	if (defender.getPosition().equals(position) && defender.getName().equals(hitter.getName()) && defender.getG() > 25) {

		                            for (int r = 0; r < 4; r++) {
		                                if (hitter.getH() > topPlayers.get(r).getH() && !containsPlayer(list, hitter.getName())) {
		                                    topPlayers.add(r, new lineUpDAO(hitter.getName(), position, hitter.getH(), r + 1));
		                                    topPlayers.remove(r + 1);
		                                    break;
		                                }
		                            }
		                        }
		                    } else {
		                        if (defender.getName().equals(hitter.getName()) && defender.getG() > 25) {
		                            for (int r = 0; r < 4; r++) {
		                                if (hitter.getH() > topPlayers.get(r).getH() && !containsPlayer(list, hitter.getName())) {
		                                    topPlayers.add(r, new lineUpDAO(hitter.getName(), "����Ÿ��", hitter.getH(), r + 1));
		                                    topPlayers.remove(r + 1); 
		                                    break;
		                                }
		                            }
		                        }
		                    }
		                }
		            }
		        }

		        for (lineUpDAO player : topPlayers) {
		            if (player.getName().isEmpty()) {
		                player.setRank(4);
		            }
		        }
		        
		        list.addAll(topPlayers);
		    }
		    ArrayList<lineUpDAO> rankOneList = new ArrayList<>();
		    for (lineUpDAO player : list) {
		        if (player.getRank() == 1) {
		            rankOneList.add(player);
		        }
		    }
		    
		    Comparator<lineUpDAO> hrComparator = (player1, player2) -> {
		        int hr1 = (int) getPlayerStat(player1, hitterOpponentList22, "HR");
		        int hr2 = (int) getPlayerStat(player2, hitterOpponentList22, "HR");
		        return Integer.compare(hr2, hr1);
		    };

		    Comparator<lineUpDAO> rbiComparator = (player1, player2) -> {
		        int rbi1 = (int) getPlayerStat(player1, hitterOpponentList22, "RBI");
		        int rbi2 = (int) getPlayerStat(player2, hitterOpponentList22, "RBI");
		        return Integer.compare(rbi2, rbi1);
		    };

		    Comparator<lineUpDAO> sbComparator = (player1, player2) -> {
		        int sb1 = (int) getSBStat(player1, runnerList22);
		        int sb2 = (int) getSBStat(player2, runnerList22);
		        return Integer.compare(sb2, sb1);
		    };

		    Comparator<lineUpDAO> sacComparator = (player1, player2) -> {
		        int sac1 = (int) getPlayerStat(player1, hitterOpponentList22, "SAC");
		        int sac2 = (int) getPlayerStat(player2, hitterOpponentList22, "SAC");
		        return Integer.compare(sac2, sac1);
		    };

		    Comparator<lineUpDAO> slgComparator = (player1, player2) -> {
		        double slg1 = (int) getPlayerStat(player1, hitterOpponentList22, "SLG");
		        double slg2 = (int) getPlayerStat(player2, hitterOpponentList22, "SLG" );
		        return Double.compare(slg2, slg1);
		    };

		    Comparator<lineUpDAO> obpComparator = (player1, player2) -> {
		        double obp1 = getPlayerStat(player1, hitterOpponentList22, "OBP");
		        double obp2 = getPlayerStat(player2, hitterOpponentList22, "OBP");
		        return Double.compare(obp2, obp1);
		    };

		    Comparator<lineUpDAO> hComparator = (player1, player2) -> {
		        int h1 = (int) getPlayerStat(player1, hitterOpponentList22, "H");
		        int h2 = (int) getPlayerStat(player2, hitterOpponentList22, "H");
		        return Integer.compare(h2, h1);
		    };
		    Set<Integer> assignedLineUps = new HashSet<>();

		    List<lineUpDAO> remainingPlayers = new ArrayList<>(rankOneList);

		    setPlayerLineUp(list, remainingPlayers.stream().max(sbComparator).orElse(null), 1, assignedLineUps);
		    remainingPlayers = remainingPlayers.stream().filter(p -> p.getLineUp() == 0).collect(Collectors.toList());
		    setPlayerLineUp(list, remainingPlayers.stream().max(sacComparator).orElse(null), 2, assignedLineUps);
		    remainingPlayers = remainingPlayers.stream().filter(p -> p.getLineUp() == 0).collect(Collectors.toList());
		    setPlayerLineUp(list, remainingPlayers.stream().max(rbiComparator).orElse(null), 3, assignedLineUps);
		    remainingPlayers = remainingPlayers.stream().filter(p -> p.getLineUp() == 0).collect(Collectors.toList());
		    setPlayerLineUp(list, remainingPlayers.stream().max(hrComparator).orElse(null), 4, assignedLineUps);
		    remainingPlayers = remainingPlayers.stream().filter(p -> p.getLineUp() == 0).collect(Collectors.toList());
		    remainingPlayers = remainingPlayers.stream().filter(p -> p.getLineUp() == 0).collect(Collectors.toList());
		    setPlayerLineUp(list, remainingPlayers.stream().max(slgComparator).orElse(null), 5, assignedLineUps);
		    remainingPlayers = remainingPlayers.stream().filter(p -> p.getLineUp() == 0).collect(Collectors.toList());
		    setPlayerLineUp(list, remainingPlayers.stream().max(slgComparator).orElse(null), 6, assignedLineUps);
		    remainingPlayers = remainingPlayers.stream().filter(p -> p.getLineUp() == 0).collect(Collectors.toList());
		    setPlayerLineUp(list, remainingPlayers.stream().max(slgComparator).orElse(null), 7, assignedLineUps);
		    remainingPlayers = remainingPlayers.stream().filter(p -> p.getLineUp() == 0).collect(Collectors.toList());
		    setPlayerLineUp(list, remainingPlayers.stream().max(hComparator).orElse(null), 8, assignedLineUps);
		    remainingPlayers = remainingPlayers.stream().filter(p -> p.getLineUp() == 0).collect(Collectors.toList());
		    setPlayerLineUp(list, remainingPlayers.stream().max(obpComparator).orElse(null), 9, assignedLineUps);

		    return list;
		}
		
		public ArrayList<lineUpDAO> leftList(String myTeam, String opponentTeam, ArrayList<hitterDAO> hitterOpponentList22, ArrayList<defenseDAO> defenseList22, ArrayList<hitterDAO> hitterTypeList22, ArrayList<runnerDAO> runnerList22) {
		    ArrayList<lineUpDAO> list = normalList(myTeam, opponentTeam, hitterOpponentList22, defenseList22, runnerList22);

		    String[] positions = {"���ݼ�", "�߰߼�", "2���", "3���", "���ͼ�", "���ͼ�", "1���", "����", "����Ÿ��"};

		    for (String position : positions) {
		        for (int rank = 1; rank <= 4; rank++) {
		            int maxHits = -1;
		            lineUpDAO maxHitPlayer = null;

		            for (hitterDAO hitterType : hitterTypeList22) {
		                if (hitterType.getPitcherType().equals("������")) {
		                    for (lineUpDAO player : list) {
		                        if (player.getName().equals(hitterType.getName()) && player.getPosition().equals(position) && player.getRank() == rank) {
		                            if (hitterType.getH() > maxHits) {
		                                maxHits = hitterType.getH();
		                                maxHitPlayer = player;
		                            }
		                        }
		                    }
		                }
		            }

		            if (maxHitPlayer != null) {
		                maxHitPlayer.setH(maxHits);
		            }
		        }
		    }    ArrayList<lineUpDAO> rankOneList = new ArrayList<>();
		    for (lineUpDAO player : list) {
		        if (player.getRank() == 1) {
		            rankOneList.add(player);
		        }
		    }
		    
		    Comparator<lineUpDAO> hrComparator = (player1, player2) -> {
		        int hr1 = (int) getPlayerStat(player1, hitterOpponentList22, "HR");
		        int hr2 = (int) getPlayerStat(player2, hitterOpponentList22, "HR");
		        return Integer.compare(hr2, hr1);
		    };

		    Comparator<lineUpDAO> rbiComparator = (player1, player2) -> {
		        int rbi1 = (int) getPlayerStat(player1, hitterOpponentList22, "RBI");
		        int rbi2 = (int) getPlayerStat(player2, hitterOpponentList22, "RBI");
		        return Integer.compare(rbi2, rbi1);
		    };

		    Comparator<lineUpDAO> sbComparator = (player1, player2) -> {
		        int sb1 = (int) getSBStat(player1, runnerList22);
		        int sb2 = (int) getSBStat(player2, runnerList22);
		        return Integer.compare(sb2, sb1);
		    };

		    Comparator<lineUpDAO> sacComparator = (player1, player2) -> {
		        int sac1 = (int) getPlayerStat(player1, hitterOpponentList22, "SAC");
		        int sac2 = (int) getPlayerStat(player2, hitterOpponentList22, "SAC");
		        return Integer.compare(sac2, sac1);
		    };

		    Comparator<lineUpDAO> slgComparator = (player1, player2) -> {
		        double slg1 = (int) getPlayerStat(player1, hitterOpponentList22, "SLG");
		        double slg2 = (int) getPlayerStat(player2, hitterOpponentList22, "SLG" );
		        return Double.compare(slg2, slg1);
		    };

		    Comparator<lineUpDAO> obpComparator = (player1, player2) -> {
		        double obp1 = getPlayerStat(player1, hitterOpponentList22, "OBP");
		        double obp2 = getPlayerStat(player2, hitterOpponentList22, "OBP");
		        return Double.compare(obp2, obp1);
		    };

		    Comparator<lineUpDAO> hComparator = (player1, player2) -> {
		        int h1 = (int) getPlayerStat(player1, hitterOpponentList22, "H");
		        int h2 = (int) getPlayerStat(player2, hitterOpponentList22, "H");
		        return Integer.compare(h2, h1);
		    };
		    Set<Integer> assignedLineUps = new HashSet<>();

		    List<lineUpDAO> remainingPlayers = new ArrayList<>(rankOneList);

		    setPlayerLineUp(list, remainingPlayers.stream().max(sbComparator).orElse(null), 1, assignedLineUps);
		    remainingPlayers = remainingPlayers.stream().filter(p -> p.getLineUp() == 0).collect(Collectors.toList());
		    setPlayerLineUp(list, remainingPlayers.stream().max(sacComparator).orElse(null), 2, assignedLineUps);
		    remainingPlayers = remainingPlayers.stream().filter(p -> p.getLineUp() == 0).collect(Collectors.toList());
		    setPlayerLineUp(list, remainingPlayers.stream().max(rbiComparator).orElse(null), 3, assignedLineUps);
		    remainingPlayers = remainingPlayers.stream().filter(p -> p.getLineUp() == 0).collect(Collectors.toList());
		    setPlayerLineUp(list, remainingPlayers.stream().max(hrComparator).orElse(null), 4, assignedLineUps);
		    remainingPlayers = remainingPlayers.stream().filter(p -> p.getLineUp() == 0).collect(Collectors.toList());
		    remainingPlayers = remainingPlayers.stream().filter(p -> p.getLineUp() == 0).collect(Collectors.toList());
		    setPlayerLineUp(list, remainingPlayers.stream().max(slgComparator).orElse(null), 5, assignedLineUps);
		    remainingPlayers = remainingPlayers.stream().filter(p -> p.getLineUp() == 0).collect(Collectors.toList());
		    setPlayerLineUp(list, remainingPlayers.stream().max(slgComparator).orElse(null), 6, assignedLineUps);
		    remainingPlayers = remainingPlayers.stream().filter(p -> p.getLineUp() == 0).collect(Collectors.toList());
		    setPlayerLineUp(list, remainingPlayers.stream().max(slgComparator).orElse(null), 7, assignedLineUps);
		    remainingPlayers = remainingPlayers.stream().filter(p -> p.getLineUp() == 0).collect(Collectors.toList());
		    setPlayerLineUp(list, remainingPlayers.stream().max(hComparator).orElse(null), 8, assignedLineUps);
		    remainingPlayers = remainingPlayers.stream().filter(p -> p.getLineUp() == 0).collect(Collectors.toList());
		    setPlayerLineUp(list, remainingPlayers.stream().max(obpComparator).orElse(null), 9, assignedLineUps);
		    return list;
		}
		
		public ArrayList<lineUpDAO> underList(String myTeam, String opponentTeam, ArrayList<hitterDAO> hitterOpponentList22, ArrayList<defenseDAO> defenseList22, ArrayList<hitterDAO> hitterTypeList22, ArrayList<runnerDAO> runnerList22) {
		    ArrayList<lineUpDAO> list = normalList(myTeam, opponentTeam, hitterOpponentList22, defenseList22, runnerList22);

		    String[] positions = {"���ݼ�", "�߰߼�", "2���", "3���", "���ͼ�", "���ͼ�", "1���", "����", "����Ÿ��"};

		    for (String position : positions) {
		        for (int rank = 1; rank <= 4; rank++) {
		            int maxHits = -1;
		            lineUpDAO maxHitPlayer = null;

		            for (hitterDAO hitterType : hitterTypeList22) {
		                if (hitterType.getPitcherType().equals("���")) {
		                    for (lineUpDAO player : list) {
		                        if (player.getName().equals(hitterType.getName()) && player.getPosition().equals(position) && player.getRank() == rank) {
		                            if (hitterType.getH() > maxHits) {
		                                maxHits = hitterType.getH();
		                                maxHitPlayer = player;
		                            }
		                        }
		                    }
		                }
		            }

		            if (maxHitPlayer != null) {
		                maxHitPlayer.setH(maxHits);
		            }
		        }
		    }    ArrayList<lineUpDAO> rankOneList = new ArrayList<>();
		    for (lineUpDAO player : list) {
		        if (player.getRank() == 1) {
		            rankOneList.add(player);
		        }
		    }
		    
		    Comparator<lineUpDAO> hrComparator = (player1, player2) -> {
		        int hr1 = (int) getPlayerStat(player1, hitterOpponentList22, "HR");
		        int hr2 = (int) getPlayerStat(player2, hitterOpponentList22, "HR");
		        return Integer.compare(hr2, hr1);
		    };

		    Comparator<lineUpDAO> rbiComparator = (player1, player2) -> {
		        int rbi1 = (int) getPlayerStat(player1, hitterOpponentList22, "RBI");
		        int rbi2 = (int) getPlayerStat(player2, hitterOpponentList22, "RBI");
		        return Integer.compare(rbi2, rbi1);
		    };

		    Comparator<lineUpDAO> sbComparator = (player1, player2) -> {
		        int sb1 = (int) getSBStat(player1, runnerList22);
		        int sb2 = (int) getSBStat(player2, runnerList22);
		        return Integer.compare(sb2, sb1);
		    };

		    Comparator<lineUpDAO> sacComparator = (player1, player2) -> {
		        int sac1 = (int) getPlayerStat(player1, hitterOpponentList22, "SAC");
		        int sac2 = (int) getPlayerStat(player2, hitterOpponentList22, "SAC");
		        return Integer.compare(sac2, sac1);
		    };

		    Comparator<lineUpDAO> slgComparator = (player1, player2) -> {
		        double slg1 = (int) getPlayerStat(player1, hitterOpponentList22, "SLG");
		        double slg2 = (int) getPlayerStat(player2, hitterOpponentList22, "SLG" );
		        return Double.compare(slg2, slg1);
		    };

		    Comparator<lineUpDAO> obpComparator = (player1, player2) -> {
		        double obp1 = getPlayerStat(player1, hitterOpponentList22, "OBP");
		        double obp2 = getPlayerStat(player2, hitterOpponentList22, "OBP");
		        return Double.compare(obp2, obp1);
		    };

		    Comparator<lineUpDAO> hComparator = (player1, player2) -> {
		        int h1 = (int) getPlayerStat(player1, hitterOpponentList22, "H");
		        int h2 = (int) getPlayerStat(player2, hitterOpponentList22, "H");
		        return Integer.compare(h2, h1);
		    };
		    Set<Integer> assignedLineUps = new HashSet<>();

		    List<lineUpDAO> remainingPlayers = new ArrayList<>(rankOneList);

		    setPlayerLineUp(list, remainingPlayers.stream().max(sbComparator).orElse(null), 1, assignedLineUps);
		    remainingPlayers = remainingPlayers.stream().filter(p -> p.getLineUp() == 0).collect(Collectors.toList());
		    setPlayerLineUp(list, remainingPlayers.stream().max(sacComparator).orElse(null), 2, assignedLineUps);
		    remainingPlayers = remainingPlayers.stream().filter(p -> p.getLineUp() == 0).collect(Collectors.toList());
		    setPlayerLineUp(list, remainingPlayers.stream().max(rbiComparator).orElse(null), 3, assignedLineUps);
		    remainingPlayers = remainingPlayers.stream().filter(p -> p.getLineUp() == 0).collect(Collectors.toList());
		    setPlayerLineUp(list, remainingPlayers.stream().max(hrComparator).orElse(null), 4, assignedLineUps);
		    remainingPlayers = remainingPlayers.stream().filter(p -> p.getLineUp() == 0).collect(Collectors.toList());
		    remainingPlayers = remainingPlayers.stream().filter(p -> p.getLineUp() == 0).collect(Collectors.toList());
		    setPlayerLineUp(list, remainingPlayers.stream().max(slgComparator).orElse(null), 5, assignedLineUps);
		    remainingPlayers = remainingPlayers.stream().filter(p -> p.getLineUp() == 0).collect(Collectors.toList());
		    setPlayerLineUp(list, remainingPlayers.stream().max(slgComparator).orElse(null), 6, assignedLineUps);
		    remainingPlayers = remainingPlayers.stream().filter(p -> p.getLineUp() == 0).collect(Collectors.toList());
		    setPlayerLineUp(list, remainingPlayers.stream().max(slgComparator).orElse(null), 7, assignedLineUps);
		    remainingPlayers = remainingPlayers.stream().filter(p -> p.getLineUp() == 0).collect(Collectors.toList());
		    setPlayerLineUp(list, remainingPlayers.stream().max(hComparator).orElse(null), 8, assignedLineUps);
		    remainingPlayers = remainingPlayers.stream().filter(p -> p.getLineUp() == 0).collect(Collectors.toList());
		    setPlayerLineUp(list, remainingPlayers.stream().max(obpComparator).orElse(null), 9, assignedLineUps);

		    return list;
		}
		private boolean containsPlayer(ArrayList<lineUpDAO> list, String playerName) {
		    for (lineUpDAO player : list) {
		        if (player.getName().equals(playerName)) {
		            return true;
		        }
		    }
		    return false;
		}
		private lineUpDAO getMaxStatPlayer(ArrayList<lineUpDAO> list, ArrayList<hitterDAO> hitterStats, ArrayList<runnerDAO> runnerList22, String stat) {
		    lineUpDAO maxPlayer = null;
		    double maxStat = -1;
		    for (lineUpDAO player : list) {
		        if (player.getLineUp() == 0) {
		            for (hitterDAO hitter : hitterStats) {
		                if (player.getName().equals(hitter.getName())) {
		                    double currentStat = 0;
		                    switch (stat) {
		                        case "SB":
		                            for (runnerDAO runner : runnerList22) {
		                                if (player.getName().equals(runner.getName())) {
		                                    currentStat = runner.getSb();
		                                }
		                            }
		                            break;
		                        case "HR":
		                            currentStat = hitter.getHr();
		                            break;
		                        case "RBI":
		                            currentStat = hitter.getRbi();
		                            break;
		                        case "SAC":
		                            currentStat = hitter.getSac();
		                            break;
		                        case "SLG":
		                            currentStat = (hitter.getH() + hitter.getDoubleBase() + 2 * hitter.getTripleBase() + 3 * hitter.getHr()) / (double) hitter.getAb();
		                            break;
		                        case "OBP":
		                            currentStat = hitter.getObp();
		                            break;
		                        case "H":
		                            currentStat = hitter.getH();
		                            break;
		                    }
		                    if (currentStat > maxStat) {
		                        maxStat = currentStat;
		                        maxPlayer = player;
		                    }
		                }
		            }
		        }
		    }
		    return maxPlayer;
		}

		private void setPlayerLineUp(ArrayList<lineUpDAO> list, lineUpDAO player, int lineUp, Set<Integer> assignedLineUps) {
		    if (player != null && !assignedLineUps.contains(lineUp)) {
		        for (lineUpDAO p : list) {
		            if (p.getName().equals(player.getName())) {
		                p.setLineUp(lineUp);
		                assignedLineUps.add(lineUp);
		                break;
		            }
		        }
		    }
		}


		private double getPlayerStat(lineUpDAO player, ArrayList<hitterDAO> hitterList, String stat) {
		    double result = 0;
		    for (hitterDAO hitter : hitterList) {
		        if (player.getName().equals(hitter.getName())) {
		            switch (stat) {
		                case "HR":
		                    result = hitter.getHr();
		                    break;
		                case "RBI":
		                    result = hitter.getRbi();
		                    break;
		                case "SAC":
		                    result = hitter.getSac();
		                    break;
		                case "SLG":
		                    result = (hitter.getH() + hitter.getDoubleBase() + 2 * hitter.getTripleBase() + 3 * hitter.getHr()) / (double) hitter.getAb();
		                    break;
		                case "OBP":
		                    result = hitter.getObp();
		                    break;
		                case "H":
		                    result = hitter.getH();
		                    break;
		            }
		        }
		    }
		    return result;
		}

		private double getSBStat(lineUpDAO player, ArrayList<runnerDAO> runnerList) {
		    double result = 0;
		    for (runnerDAO runner : runnerList) {
		        if (player.getName().equals(runner.getName())) {
		            result = runner.getSb();
		        }
		    }
		    return result;
		}
		
		public ArrayList<lineUpDAO> createPitchingList(ArrayList<pitcherDAO> pitcherList22, String myTeam) {
		    ArrayList<lineUpDAO> pitchingList = new ArrayList<>();
		    String[] startingPitchers = new String[5];
		    String closer = "", relief1 = "", relief2 = "", relief3 = "", relief4 = "";
		    double[] tmpStartingPitchers = new double[5];
		    double tmp15 = 0, tmp16 = 0, tmp17 = 0, tmp18 = 0, tmp19 = 0;

		    for (int j = 0; j < 5; j++) {
		        double maxIP = -1;
		        for (int i = 0; i < pitcherList22.size(); i++) {
		            pitcherDAO dao = pitcherList22.get(i);

		            if (dao.getTeamName().equals(myTeam) && !Arrays.asList(startingPitchers).contains(dao.getName())) {
		                if (dao.getIp() > maxIP) {
		                    maxIP = dao.getIp();
		                    startingPitchers[j] = dao.getName();
		                    tmpStartingPitchers[j] = maxIP;
		                }
		            }
		        }
		    }

		    for (int i = 0; i < pitcherList22.size(); i++) {
		        pitcherDAO dao = pitcherList22.get(i);

		        if (dao.getTeamName().equals(myTeam)) {
		            if (dao.getSv() > tmp15 && !Arrays.asList(startingPitchers).contains(dao.getName())) {
		                tmp15 = dao.getSv();
		                closer = dao.getName();
		            }

		            if (dao.getHld() > tmp16 && !Arrays.asList(startingPitchers, closer).contains(dao.getName())) {
		                tmp16 = dao.getHld();
		                relief1 = dao.getName();
		            }

		            if (dao.getHld() > tmp17 && !Arrays.asList(startingPitchers, closer, relief1).contains(dao.getName())) {
		                tmp17 = dao.getHld();
		                relief2 = dao.getName();
		            }

		            if (dao.getHld() > tmp18 && !Arrays.asList(startingPitchers, closer, relief1, relief2).contains(dao.getName())) {
		                tmp18 = dao.getHld();
		                relief3 = dao.getName();
		            }

		            if (dao.getHld() > tmp19 && !Arrays.asList(startingPitchers, closer, relief1, relief2, relief3).contains(dao.getName())) {
		                tmp19 = dao.getHld();
		                relief4 = dao.getName();
		            }
		        }
		    }

		    for (int j = 0; j < startingPitchers.length; j++) {
		        lineUpDAO player = new lineUpDAO();
		        player.setName(startingPitchers[j]);
		        player.setPosition(j == 0 ? "��������" : "��� ����");
		        pitchingList.add(player);
		    }

		    for (String pitcherName : new String[]{closer, relief1, relief2, relief3, relief4}) {
		        if (!pitcherName.isEmpty()) {
		            lineUpDAO player = new lineUpDAO();
		            player.setName(pitcherName);

		            if (pitcherName.equals(closer)) {
		                player.setPosition("������ ����");
		            } else {
		                player.setPosition("�߰�����");
		            }

		            pitchingList.add(player);
		        }
		    }

		    return pitchingList;
		}
	    

		public static void main(String[] args) throws SQLException {
			ArrayList<defenseDAO> defenseList22 = DatabaseManager.getInstance().getDefenseList(2022);		// 22���� ������ �ν��Ͻ� ������
	        ArrayList<hitterDAO> hitterOpponentList22 = DatabaseManager.getInstance().getHitterOpponentList(2022);
	        ArrayList<hitterDAO> hitterTypeList22 = DatabaseManager.getInstance().getHitterTypeList(2022);
	        ArrayList<runnerDAO> runnerList22 = DatabaseManager.getInstance().getRunnerList(2022);
	        ArrayList<pitcherDAO> pitcherList22 = DatabaseManager.getInstance().getPitcherList(2022);
	        ArrayList<pitcherDAO> pitcherList21 = DatabaseManager.getInstance().getPitcherList(2021);
	        ArrayList<pitcherDAO> pitcherTypeList22 = DatabaseManager.getInstance().getPitcherTypeList(2022);
	        ArrayList<pitcherDAO> pitcherTypeList21 = DatabaseManager.getInstance().getPitcherTypeList(2021);
	        ArrayList<hitterDAO> hitterList22 = DatabaseManager.getInstance().getHitterList(2022);
	        ArrayList<hitterDAO> hitterList21 = DatabaseManager.getInstance().getHitterList(2021);
		    
	        String hometeam = "NC";
	        String awayteam = "Ű��";
	        
			//rosterAnalysis startRoster = new rosterAnalysis(hometeam, awayteam, "������");
			
//			recommendationStrategyAnalysis analysis = new recommendationStrategyAnalysis("NC", "�Ｚ");
	        
//			ArrayList<lineUpDAO> lineUpRight_myTeam = startRoster.normalList(hometeam, awayteam, hitterOpponentList22, defenseList22, runnerList22);
//			for (lineUpDAO player : lineUpRight_myTeam) {
//	            System.out.println("Player Name: " + player.getName() + ", Position: " + player.getPosition() + ", rank: " + player.getRank() + ", hit: " + player.getH() + ", Ÿ��: " + player.getLineUp());
//	        }
//			System.out.println("---------------------------------------------------------------");
//			ArrayList<lineUpDAO> lineUpPitcher_myTeam = startRoster.createPitchingList(pitcherList22,hometeam);
//			for (lineUpDAO player : lineUpPitcher_myTeam) {
//	            System.out.println("Player Name: " + player.getName() + ", Position: " + player.getPosition());
//	        }
//			ArrayList<lineUpDAO> lineUpLeft_myTeam = startRoster.leftList(hometeam, awayteam, hitterOpponentList22, defenseList22,hitterTypeList22, runnerList22);
//			for (lineUpDAO player : lineUpLeft_myTeam) {
//	            System.out.println("Player Name: " + player.getName() + ", Position: " + player.getPosition() + ", rank: " + player.getRank() + ", hit: " + player.getH()+ ", Ÿ��: " + player.getLineUp());
//	        }
//			System.out.println("---------------------------------------------------------------");
//			ArrayList<lineUpDAO> lineUpUnder_myTeam = startRoster.underList(hometeam, awayteam, hitterOpponentList22, defenseList22,hitterTypeList22, runnerList22);
//			for (lineUpDAO player : lineUpUnder_myTeam) {
//	            System.out.println("Player Name: " + player.getName() + ", Position: " + player.getPosition() + ", rank: " + player.getRank() + ", hit: " + player.getH()+ ", Ÿ��: " + player.getLineUp());
//	        }

	        recommendationStrategyAnalysis analysis = new recommendationStrategyAnalysis(hometeam, awayteam);
	        
	        HitterTrend hitterTrendMyTeam = analysis.getHitterTrendMyTeam(hitterList22, hitterList21, hometeam);
	        ArrayList<analysisDAO> uptrendList1 = hitterTrendMyTeam.getUptrend();
	        System.out.println("Player Name: " + uptrendList1.get(0).getName() + ", Ops22: " + uptrendList1.get(0).getOps22() + ", Ops21: " + uptrendList1.get(0).getOps21());
	        
	        WeakLeftPitcher leftWeakStrongPitcherOpponentTeam = analysis.getWeakLeftPitchers(pitcherTypeList22,hometeam,awayteam);
	        ArrayList<analysisDAO> myteam = leftWeakStrongPitcherOpponentTeam.getWeakLeftPitcher_myTeam();
	        for (analysisDAO player : myteam) {
	            System.out.println("Player Name: " + player.getName() + ", avg: " + player.getAverage() );
	        }
		}
}