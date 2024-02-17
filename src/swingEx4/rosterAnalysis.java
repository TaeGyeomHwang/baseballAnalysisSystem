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
		
		public static synchronized rosterAnalysis getInstance(String myTeam, String opponentTeam, String pitcherType) throws SQLException {	//	synchronized 통해 인스턴스 생성이 스레드로부터 이루어짐 >> 멀티스레드로 동작해서 경합상태 해결
	        if (instance == null) {
	            instance = new rosterAnalysis(myTeam, opponentTeam, pitcherType);
	        }
	        return instance;
	    }
		public rosterAnalysis () {}
		
	   	ArrayList <String> list = new ArrayList <String> ();										// 각 포지션 선발타자를 담는 리스트
	   	
		public rosterAnalysis(String myTeam, String opponentTeam, String pitcherType) throws SQLException {
			
        ArrayList<playerDAO> playerList = DatabaseManager.getInstance().getPlayerList();			// 선수정보 인스턴스 얻어오기
        //MyTableModel model = new MyTableModel(playerList);
        //MyTableModel.printTableModel(model); 
        
        ArrayList<hitterDAO> hitterList22 = DatabaseManager.getInstance().getHitterList(2022);		// 22시즌 타자기록 인스턴스 얻어오기
        ArrayList<hitterDAO> hitterList20 = DatabaseManager.getInstance().getHitterList(2020);		// 20시즌 타자기록 인스턴스 얻어오기
        ArrayList<pitcherDAO> pitcherList21 = DatabaseManager.getInstance().getPitcherList(2021);		// 21시즌 투수기록 인스턴스 얻어오기
        ArrayList<defenseDAO> defenseList22 = DatabaseManager.getInstance().getDefenseList(2022);		// 22시즌 수비기록 인스턴스 얻어오기
        ArrayList<runnerDAO> runnerList22 = DatabaseManager.getInstance().getRunnerList(2022);		// 19시즌 주루기록 인스턴스 얻어오기
        ArrayList<teamDAO> teamList = DatabaseManager.getInstance().getTeamList();					// 팀기록 인스턴스 얻어오기
        ArrayList<pitcherDAO> pitcherList22 = DatabaseManager.getInstance().getPitcherList(2022);
        
        
        // 추가 - 경기 상황별 클래스 
        ArrayList<hitterDAO> hitterOpponentList22 = DatabaseManager.getInstance().getHitterOpponentList(2022);		// 22시즌 상대팀별 타자기록 인스턴스 얻어오기
        ArrayList<hitterDAO> hitterTypeList22 = DatabaseManager.getInstance().getHitterTypeList(2022);				// 22시즌 투수유형별 타자기록 인스턴스 얻어오기
        ArrayList<pitcherDAO> pitcherOpponentList22 = DatabaseManager.getInstance().getPitcherOpponentList(2022);		// 22시즌 상대팀별 투수기록 인스턴스 얻어오기
        ArrayList<pitcherDAO> pitcherTypeList22 = DatabaseManager.getInstance().getPitcherOpponentList(2022);			// 22시즌 투수유형별 투수기록 인스턴스 얻어오기
        
    
        	/** 선수별 수비 포지션 정하기 (안타 기준) */
        
		   	int tmp1 = 0, tmp2 = 0, tmp3 = 0, tmp4 = 0, tmp5 = 0, tmp6 = 0, tmp7 = 0, tmp8 = 0, tmp9 = 0;								// 타자선발명단 최다안타 정하는 변수
		   	float tmp10 = 0, tmp11 = 0, tmp12 = 0, tmp13 = 0, tmp14 = 0;	int tmp15 = 0, tmp16 = 0, tmp17 = 0, tmp18 = 0, tmp19 = 0;	// 투수명단 최다이닝,홀드,세이브 정하는변수
		   	
		   	// 
		   	String leftFielder = ""; String centerFielder = ""; String rightFielder = "";  String designatedHitter = "";
		   	String firstBaseman = ""; String secondBaseman = ""; String thirdBaseman = ""; String shortStop = ""; String catcher = "";	
		   	String startingPitcher1 = "";	String startingPitcher2 = "";	String startingPitcher3 = "";	String startingPitcher4 = "";	String startingPitcher5 = "";
		   	String closer = "";		String relief1 = "";	String relief2 = "";	String relief3 = "";	String relief4 = "";	String relief5 = "";
		   	
		   	//ArrayList <String> list = new ArrayList <String> ();										// 각 포지션 선발타자를 담는 리스트
		   	ArrayList <String> pitchingList = new ArrayList <String> ();								// 투수명단(선발5, 중간4, 마무리1)을 담는 리스트
		   	ArrayList <String> subList = new ArrayList <String> (Arrays.asList("","","","","","",""));	// 수비 선발선수에 제외된 후보타자 리스트
		   	
		   	
	     	    for(int i = 0; i < hitterOpponentList22.size() ; i++) {
	     	    	hitterDAO dao = hitterOpponentList22.get(i);												// 변경 - 22년 타자기록 -> 22년 상대팀별 타자기록
	     	    	if (dao.getTeamName().equals(myTeam) && dao.getOpponent().equals(opponentTeam)) {			// 팀명 : myTeam, 상대팀명 : opponentTeam
	     	    		try {
		     	    		for(int j = 0; j < defenseList22.size() ; j++) {
		     	    			defenseDAO dao1 = defenseList22.get(j);
		     	    			
			        	   		if(dao1.getPosition().equals("유격수") && dao1.getName().equals(dao.getName())) {					// defense와 hitter 조인 && 포지션 유격수
			        	   			if (dao.getH() > tmp1 && dao1.getG() > 30) {												// 안타가 가장 많은, 30경기 이상 출전														// 30경기 이상 출전
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
	     	    
    	   		list.add(0, shortStop);		// 유격수 list에 추가
    	   		
		        
	     	  	for(int i = 0; i < hitterOpponentList22.size() ; i++) {
	     	    	hitterDAO dao = hitterOpponentList22.get(i);
	     	    	if (dao.getTeamName().equals(myTeam) && dao.getOpponent().equals(opponentTeam)) {
	     	    		try {
		     	    		for(int j = 0; j < defenseList22.size() ; j++) {
		     	    			defenseDAO dao1 = defenseList22.get(j);
		     	    			
			        	   		if(dao1.getPosition().equals("중견수") && dao1.getName().equals(dao.getName())) {					// defense와 hitter 조인 && 포지션 중견수
			        	   			if (dao.getH() > tmp2 && dao1.getG() > 10) {												// 안타가 가장 많은, 30경기 이상 출전														// 30경기 이상 출전
			        	   				
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
	        	list.add(1, centerFielder);		// 중견수 list에 추가
		     	 
	        	for(int i = 0; i < hitterOpponentList22.size() ; i++) {
	     	    	hitterDAO dao = hitterOpponentList22.get(i);
	     	    	if (dao.getTeamName().equals(myTeam) && dao.getOpponent().equals(opponentTeam)) {
	     	    		try {
			        	   	for(int j = 0; j < defenseList22.size() ; j++) {
			     	    		defenseDAO dao1 = defenseList22.get(j);
			     	    		
				        	   	if(dao1.getPosition().equals("2루수") && dao1.getName().equals(dao.getName())) {					// defense와 hitter 조인 && 포지션 2루수
				        	   		if (dao.getH() > tmp3 && dao1.getG() > 30) {												// 안타가 가장 많은, 30경기 이상 출전														// 30경기 이상 출전
				        	   				
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
			    list.add(2, secondBaseman);			// 2루수 list에 추가
			       
			    for(int i = 0; i < hitterOpponentList22.size() ; i++) {
	     	    	hitterDAO dao = hitterOpponentList22.get(i);
	     	    	if (dao.getTeamName().equals(myTeam) && dao.getOpponent().equals(opponentTeam)) {
		     	    		try {
			        	   		
			        	    for(int j = 0; j < defenseList22.size() ; j++) {
				     	    	defenseDAO dao1 = defenseList22.get(j);
					        	if(dao1.getPosition().equals("3루수") && dao1.getName().equals(dao.getName())) {					// defense와 hitter 조인 && 포지션 3루수
					        	   	if (dao.getH() > tmp4 && dao1.getG() > 30) {													// 안타가 가장 많은, 30경기 이상 출전														// 30경기 이상 출전
					        	   				
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
			        list.add(3, thirdBaseman);		// 3루수 list에 추가
			        
			        for(int i = 0; i < hitterOpponentList22.size() ; i++) {
		     	    	hitterDAO dao = hitterOpponentList22.get(i);
		     	    	if (dao.getTeamName().equals(myTeam) && dao.getOpponent().equals(opponentTeam)) {
		     	    		try {	   	
			        	    for(int j = 0; j < defenseList22.size() ; j++) {
				     	    	defenseDAO dao1 = defenseList22.get(j);
					        	if(dao1.getPosition().equals("우익수") && dao1.getName().equals(dao.getName())) {					// defense와 hitter 조인 && 포지션 우익수
					        	   	if (dao.getH() > tmp5 && dao1.getG() > 30) {													// 안타가 가장 많은, 30경기 이상 출전														// 30경기 이상 출전
					        	   				
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
			        list.add(4, rightFielder);		// 우익수 list에 추가
			        	
			        for(int i = 0; i < hitterOpponentList22.size() ; i++) {
		     	    	hitterDAO dao = hitterOpponentList22.get(i);
		     	    	if (dao.getTeamName().equals(myTeam) && dao.getOpponent().equals(opponentTeam)) {
			     	    	try {
			        	    for(int j = 0; j < defenseList22.size() ; j++) {
				     	    	defenseDAO dao1 = defenseList22.get(j);
					        	if(dao1.getPosition().equals("좌익수") && dao1.getName().equals(dao.getName())) {					// defense와 hitter 조인 && 포지션 좌익수
					        	   	if (dao.getH() > tmp6 && dao1.getG() > 30) {													// 안타가 가장 많은, 30경기 이상 출전														// 30경기 이상 출전
					        	   				
					        	   			if(!list.contains(dao.getName())) {		// list에 이미 있는 선수는 제외 -> 중복되는 선수 제거
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
			        list.add(5, leftFielder);		// list에 좌익수 추가
			        
			        for(int i = 0; i < hitterOpponentList22.size() ; i++) {
		     	    	hitterDAO dao = hitterOpponentList22.get(i);
		     	    	if (dao.getTeamName().equals(myTeam) && dao.getOpponent().equals(opponentTeam)) {
			     	    		try {	   	
			     	    		for(int j = 0; j < defenseList22.size() ; j++) {
				     	    	defenseDAO dao1 = defenseList22.get(j);
					        	if(dao1.getPosition().equals("1루수") && dao1.getName().equals(dao.getName())) {					// defense와 hitter 조인 && 포지션 1루수
					        	   	if (dao.getH() > tmp7 && dao1.getG() > 30) {													// 안타가 가장 많은, 30경기 이상 출전														// 30경기 이상 출전
					        	   				
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
			     	    		
			        list.add(6, firstBaseman);		//list에 1루수 추가
			        for(int i = 0; i < hitterOpponentList22.size() ; i++) {
		     	    	hitterDAO dao = hitterOpponentList22.get(i);
		     	    	if (dao.getTeamName().equals(myTeam) && dao.getOpponent().equals(opponentTeam)) {
			     	    		try {	   	
			        	    for(int j = 0; j < defenseList22.size() ; j++) {
				     	    	defenseDAO dao1 = defenseList22.get(j);
					        	if(dao1.getPosition().equals("포수") && dao1.getName().equals(dao.getName())) {					// defense와 hitter 조인 && 포지션 포수
					        	   	if (dao.getH() > tmp8 && dao1.getG() > 30) {													// 안타가 가장 많은, 30경기 이상 출전														// 30경기 이상 출전
					        	   				
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
			        list.add(7, catcher);				//list에 포수 추가
			        
			        for(int i = 0; i < hitterOpponentList22.size() ; i++) {
		     	    	hitterDAO dao = hitterOpponentList22.get(i);
		     	    	if (dao.getTeamName().equals(myTeam) && dao.getOpponent().equals(opponentTeam)) {
			     	    		try {
			     	    		for(int j = 0; j < defenseList22.size() ; j++) {
				     	    	defenseDAO dao1 = defenseList22.get(j);
					        	if(dao1.getName().equals(dao.getName())) {													// defense와 hitter 조인
					        	   	if (dao.getH() > tmp9 && dao1.getG() > 30) {											// 안타가 가장 많은, 30경기 이상 출전														// 30경기 이상 출전
					        	   				
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
			        list.add(8, designatedHitter);		//list에 지명타자 추가
			        
			        for(int i = 0; i < pitcherList22.size() ; i++) {
		     	    	pitcherDAO dao = pitcherList22.get(i);						// pitcherOpponentList 쓰려했으나 승,방어율,삼진 등 핵심적인 기록 없이 피안타,피홈런 등 데이터밖에 x
			     	    	try {
					        	 if (dao.getTeamName().equals(myTeam) && dao.getIp() > tmp10) {			// 팀 : myTeam && 이닝 최다
					        	   		if(!pitchingList.contains(dao.getName())) {						// pitchingList에 이미 있는선수 제외 -> 선수 중복 방지
					        	   			tmp10 = dao.getIp();
					        	   			startingPitcher1 = dao.getName();
					        	   			}
					        	   	}
			     	    	}catch(NullPointerException e) { 
			     	    		
			     	    	}
			     	}
			        pitchingList.add(0, startingPitcher1);												// pitchingList에 1선발 추가
			        
			        for(int i = 0; i < pitcherList22.size() ; i++) {
		     	    	pitcherDAO dao = pitcherList22.get(i);
			     	    		try {
					        	   	if (dao.getTeamName().equals(myTeam) && dao.getIp() > tmp11) {		// 다음 이닝많은 선수	 
					        	   			if(!pitchingList.contains(dao.getName())) {					
					        	   				tmp11 = dao.getIp();
					        	   				startingPitcher2 = dao.getName();
					        	   				}
					        	   	}
			     	    	}catch(NullPointerException e) { 
			     	    		
			     	    	}
			     	}
			        pitchingList.add(1, startingPitcher2);												// pitchingList에 2선발 추가
			        
			        for(int i = 0; i < pitcherList22.size() ; i++) {
		     	    	pitcherDAO dao = pitcherList22.get(i);
			     	    		try {
					        	   	if (dao.getTeamName().equals(myTeam) && dao.getIp() > tmp12) {		//다음 이닝이 많은 선수		
					        	   			if(!pitchingList.contains(dao.getName())) {
					        	   				tmp12 = dao.getIp();
					        	   				startingPitcher3 = dao.getName();
					        	   				}
					        	   	}
			     	    	}catch(NullPointerException e) { 
			     	    		
			     	    	}
			     	}
			        pitchingList.add(2, startingPitcher3);												// pitchingList에 3선발 추가					
			        
			        for(int i = 0; i < pitcherList22.size() ; i++) {
		     	    	pitcherDAO dao = pitcherList22.get(i);
			     	    		try {
					        	   	if (dao.getTeamName().equals(myTeam) && dao.getIp() > tmp13) {		// 다음 이닝이 많은 선수
					        	   			if(!pitchingList.contains(dao.getName())) {
					        	   				tmp13 = dao.getIp();
					        	   				startingPitcher4 = dao.getName();
					        	   				}
					        	   	}
			     	    	}catch(NullPointerException e) { 
			     	    		
			     	    	}
			     	}
			        pitchingList.add(3, startingPitcher4);												// pitchingList에 4선발 추가
			        
			        for(int i = 0; i < pitcherList22.size() ; i++) {
		     	    	pitcherDAO dao = pitcherList22.get(i);
			     	    		try {
					        	   	if (dao.getTeamName().equals(myTeam) && dao.getIp() > tmp14) {		// 다음 이닝많은 선수
					        	   			if(!pitchingList.contains(dao.getName())) {
					        	   				tmp14 = dao.getIp();
					        	   				startingPitcher5 = dao.getName();
					        	   				}
					        	   	}
			     	    	}catch(NullPointerException e) { 
			     	    		
			     	    	}
			     	}
			        pitchingList.add(4, startingPitcher5);												// pitchingList에 5선발 추가
			        
			        for(int i = 0; i < pitcherList22.size() ; i++) {
		     	    	pitcherDAO dao = pitcherList22.get(i);
			     	    		try {
					        	   	if (dao.getTeamName().equals(myTeam) && dao.getSv() > tmp15) {		// 팀에서 가장 많은 save를 가진 선수
					        	   			if(!pitchingList.contains(dao.getName())) {
					        	   				tmp15 = dao.getSv();
					        	   				closer = dao.getName();
					        	   				}
					        	   	}
			     	    	}catch(NullPointerException e) { 
			     	    		
			     	    	}
			     	}
			        pitchingList.add(5, closer);														// pitchingList에 마무리투수 추가
			        
			        for(int i = 0; i < pitcherList22.size() ; i++) {
		     	    	pitcherDAO dao = pitcherList22.get(i);
			     	    		try {
					        	   	if (dao.getTeamName().equals(myTeam) && dao.getHld() > tmp16) {		// 홀드가 팀에서 가장많은 선수
					        	   			if(!pitchingList.contains(dao.getName())) {
					        	   				tmp16 = dao.getHld();
					        	   				relief1 = dao.getName();
					        	   				}
					        	   	}
			     	    	}catch(NullPointerException e) { 
			     	    		
			     	    	}
			     	}
			        pitchingList.add(6, relief1);														// pitchingList에 중간계투1 추가
			        for(int i = 0; i < pitcherList22.size() ; i++) {
		     	    	pitcherDAO dao = pitcherList22.get(i);
			     	    		try {
					        	   	if (dao.getTeamName().equals(myTeam) && dao.getHld() > tmp17) {		// 그다음 홀드가 많은선수
					        	   			if(!pitchingList.contains(dao.getName())) {
					        	   				tmp17 = dao.getHld();
					        	   				relief2 = dao.getName();
					        	   				}
					        	   	}
			     	    	}catch(NullPointerException e) { 
			     	    		
			     	    	}
			     	}
			        pitchingList.add(7, relief2);														// pitchingList에 중간계투2 추가
			        for(int i = 0; i < pitcherList22.size() ; i++) {
		     	    	pitcherDAO dao = pitcherList22.get(i);
			     	    		try {
					        	   	if (dao.getTeamName().equals(myTeam) && dao.getHld() > tmp18) {		// 그다음 홀드많은 선수
					        	   			if(!pitchingList.contains(dao.getName())) {
					        	   				tmp18 = dao.getHld();
					        	   				relief3 = dao.getName();
					        	   				}
					        	   	}
			     	    	}catch(NullPointerException e) { 
			     	    		
			     	    	}
			     	}
			        pitchingList.add(8, relief3);														// pitchingList에 중간계투3 추가
			        for(int i = 0; i < pitcherList22.size() ; i++) {
		     	    	pitcherDAO dao = pitcherList22.get(i);
			     	    		try {
					        	   	if (dao.getTeamName().equals(myTeam) && dao.getHld() > tmp19) {		// 그다음 홀드많은 선수
					        	   			if(!pitchingList.contains(dao.getName())) {
					        	   				tmp19 = dao.getHld();
					        	   				relief4 = dao.getName();
					        	   				}
					        	   	}
			     	    	}catch(NullPointerException e) { 
			     	    		
			     	    	}
			     	}
			        pitchingList.add(9, relief4);		
			        
			     /** subList(후보선수) 세팅	!! -> 수정 - 20경기로 세팅하니 경기수가 딸려서 해당하는 선수 출력이 안됨 -> 후보선수만 30경기에서 20경기로 하향 */
			        
			     int Tmp1 = 0, Tmp2 = 0, Tmp3 = 0, Tmp4 = 0, Tmp5 = 0, Tmp6 = 0, Tmp7 = 0;					// 후보선수 선정 위해 최다안타 담는 변수
			     String outFielder1 = ""; String outFielder2 = "";	String subCatcher = "";	String subFirstBaseman = "";
			     String subShortStop = ""; String subSecondBaseman = "";	String subThirdBaseman = "";
			     
			     String [] subPosition =new String[] {"유격수","외야수1","외야수2","2루수","3루수","1루수","포수"};		// 나중에 println 할 시 서브 포지션출력 담는 배열
			     
			     for(int i = 0; i < hitterList22.size() ; i++) {
		     	    	hitterDAO dao = hitterList22.get(i);								// 후보선수의 경우, 상대팀별 기록 고려없이 선발에 못든 선수를 hitterList22 바탕으로 구성
		     	    	if (dao.getTeamName().equals(myTeam)) {
		     	    		try {
			     	    		for(int j = 0; j < defenseList22.size() ; j++) {
			     	    			defenseDAO dao1 = defenseList22.get(j);
				        	   		if(dao1.getPosition().equals("유격수") && dao1.getName().equals(dao.getName())) {					// defense와 hitter 조인 && 포지션 유격수
				        	   			if (dao.getH() > Tmp1 && dao.getG() > 20) {													// 안타가 가장 많은, 20경기 이상 출전														// 30경기 이상 출전
				        	   				
				        	   					if(!list.contains(dao.getName())) {			// 이미 list(선발수비명단)에 존재하는 선수 제외 -> 선수 중복 방지
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
		        subList.set(0, subShortStop);				// 서브유격수 subList에 추가
		        
		        for(int i = 0; i < hitterList22.size() ; i++) {
	     	    	hitterDAO dao = hitterList22.get(i);
	     	    	if (dao.getTeamName().equals(myTeam)) {
	     	    		try {
		     	    		for(int j = 0; j < playerList.size() ; j++) {
		     	    			playerDAO dao1 = playerList.get(j);														// 외야후보 선수는 상세포지션 구분없이 "외야수"인 선수들로 구성
			        	   		if(dao1.getPosition().equals("외야수") && dao1.getName().equals(dao.getName())) {			// hitter와 player 조인 && 포지션 외야수
			        	   			if (dao.getH() > Tmp2 && dao.getG() > 20) {											// 안타가 가장 많은, 20경기 이상 출전														// 30경기 이상 출전
			        	   				
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
	        	subList.set(1, outFielder1);				// 외야수1 subList에 추가
	        	
	        	for(int i = 0; i < hitterList22.size() ; i++) {
	     	    	hitterDAO dao = hitterList22.get(i);
	     	    	if (dao.getTeamName().equals(myTeam)) {
	     	    		try {
		     	    		for(int j = 0; j < playerList.size() ; j++) {
		     	    			playerDAO dao1 = playerList.get(j);
			        	   		if(dao1.getPosition().equals("외야수") && dao1.getName().equals(dao.getName())) {				// hitter와 player 조인 && 포지션 외야수
			        	   			if (dao.getH() > Tmp3 && dao.getG() > 20) {												// 안타가 가장 많은, 20경기 이상 출전														// 30경기 이상 출전
			        	   				
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
	        	subList.set(2, outFielder2);				// 외야수2 subList에 추가
	        	
	        	for(int i = 0; i < hitterList22.size() ; i++) {
	     	    	hitterDAO dao = hitterList22.get(i);
	     	    	if (dao.getTeamName().equals(myTeam)) {
	     	    		try {
		     	    		for(int j = 0; j < defenseList22.size() ; j++) {
		     	    			defenseDAO dao1 = defenseList22.get(j);
			        	   		if(dao1.getPosition().equals("2루수") && dao1.getName().equals(dao.getName())) {					// defense와 hitter 조인 && 포지션 2루수
			        	   			if (dao.getH() > Tmp4 && dao.getG() > 20) {													// 안타가 가장 많은, 20경기 이상 출전														// 30경기 이상 출전
			        	   				
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
	        	subList.set(3, subSecondBaseman);			// 서브2루수 subList에 추가
	        	
	        	for(int i = 0; i < hitterList22.size() ; i++) {
	     	    	hitterDAO dao = hitterList22.get(i);
	     	    	if (dao.getTeamName().equals(myTeam)) {
	     	    		try {
		     	    		for(int j = 0; j < defenseList22.size() ; j++) {
		     	    			defenseDAO dao1 = defenseList22.get(j);
			        	   		if(dao1.getPosition().equals("3루수") && dao1.getName().equals(dao.getName())) {					// defense와 hitter 조인 && 포지션 3루수
			        	   			if (dao.getH() > Tmp5 && dao.getG() > 20) {													// 안타가 가장 많은, 20경기 이상 출전														// 30경기 이상 출전
			        	   				
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
	        	subList.set(4, subThirdBaseman);				// 서브3루수 subList에 추가
	        	
	        	for(int i = 0; i < hitterList22.size() ; i++) {
	     	    	hitterDAO dao = hitterList22.get(i);
	     	    	if (dao.getTeamName().equals(myTeam)) {
	     	    		try {
		     	    		for(int j = 0; j < defenseList22.size() ; j++) {
		     	    			defenseDAO dao1 = defenseList22.get(j);
			        	   		if(dao1.getPosition().equals("1루수") && dao1.getName().equals(dao.getName())) {					// defense와 hitter 조인 && 포지션 1루수
			        	   			if (dao.getH() > Tmp6 && dao.getG() > 20) {													// 안타가 가장 많은, 20경기 이상 출전														// 30경기 이상 출전
			        	   				
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
	        	subList.set(5, subFirstBaseman);						// 서브1루수 subList에 추가
		        
	        	for(int i = 0; i < hitterList22.size() ; i++) {
	     	    	hitterDAO dao = hitterList22.get(i);
	     	    	if (dao.getTeamName().equals(myTeam)) {
	     	    		try {
		     	    		for(int j = 0; j < defenseList22.size() ; j++) {
		     	    			defenseDAO dao1 = defenseList22.get(j);
			        	   		if(dao1.getPosition().equals("포수") && dao1.getName().equals(dao.getName())) {					// defense와 hitter 조인 && 포지션 포수
			        	   			if (dao.getH() > Tmp7 && dao.getG() > 20) {													// 안타가 가장 많은, 20경기 이상 출전														// 30경기 이상 출전
			        	   				
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
	        	subList.set(6, subCatcher);						// 서브포수 subList에 추가
	        	  
	        	
       	   		/** 수비선발명단 바탕으로 타순 정하기 */
       	   							
	        	//--> (h + 2b + 3b *2 + hr *3 / 타수)
       	   		// 최고 출루율			--> (안타 + 볼넷(bb) + 사구(hbp)) / (타수 + 볼넷 + 사구 + 생략(희생플라이))
       	   		
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
	   						
	   							if (dao1.equals(run.getName()) && dao1.equals(def.getName())) {					// runnerList22와 defenseStart(수비선발) 조인
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
       	   		lineUp.set(0, MaxSb.get(0));			// 1번타자
	   			
       	   		for(int j = 0; j < list.size(); j++) {
       	   			String dao1 = list.get(j);
       	   			
       	   			try {	
	   				for(int i = 0; i < hitterTypeList22.size() ; i++) {
	   		   			hitterDAO hit = hitterTypeList22.get(i);				// 투수유형별 타자기록 불러옴
	   					
	   		   			for(int k = 0; k < defenseList22.size(); k++) {
	   						defenseDAO def = defenseList22.get(k);
	   		   				if (dao1.equals(hit.getName()) && hit.getPitcherType().equals(pitcherType)) {	// 조인 && 투수유형 : pitcherType
	   							if (dao1.equals(def.getName())) {						// 9명 중 가장 홈런이 많은 선수
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
	   		lineUp.set(3, MaxHr.get(0));	// 4번타자
       	   	
	   			for(int j = 0; j < list.size(); j++) {
       	   			String dao1 = list.get(j);
       	   			
       	   			try {	
	   				for(int i = 0; i < hitterTypeList22.size() ; i++) {
	   		   			hitterDAO hit = hitterTypeList22.get(i);				// 투수유형별 타자기록 불러옴
	   					
	   		   			for(int k = 0; k < defenseList22.size(); k++) {
	   						defenseDAO def = defenseList22.get(k);
	   		   				if (dao1.equals(hit.getName()) && hit.getPitcherType().equals(pitcherType)) {	// 조인 && 투수유형 : pitcherType
	   							if (dao1.equals(def.getName())) {						// 9명 중 가장 홈런이 많은 선수
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
	   			lineUp.set(2, MaxRbi.get(0));		// 3번타자
   					
	   			for(int j = 0; j < list.size(); j++) {
       	   			String dao1 = list.get(j);
       	   			
       	   			try {	
	   				for(int i = 0; i < hitterTypeList22.size() ; i++) {
	   		   			hitterDAO hit = hitterTypeList22.get(i);				// 투수유형별 타자기록 불러옴
	   					
	   		   			for(int k = 0; k < defenseList22.size(); k++) {
	   						defenseDAO def = defenseList22.get(k);
	   		   				if (dao1.equals(hit.getName()) && hit.getPitcherType().equals(pitcherType)) {	// 조인 && 투수유형 : pitcherType
	   							if (dao1.equals(def.getName())) {						// 9명 중 가장 홈런이 많은 선수
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
	   			lineUp.set(1, MaxSac.get(0));		//2번타자
	   				
	   			for(int j = 0; j < list.size(); j++) {
       	   			String dao1 = list.get(j);
       	   			
       	   			try {	
	   				for(int i = 0; i < hitterTypeList22.size() ; i++) {
	   		   			hitterDAO hit = hitterTypeList22.get(i);				// 투수유형별 타자기록 불러옴
	   					
	   		   			for(int k = 0; k < defenseList22.size(); k++) {			//(h + 2b + 3b *2 + hr *3 / 타수)
	   						defenseDAO def = defenseList22.get(k);
	   		   				if (dao1.equals(hit.getName()) && hit.getPitcherType().equals(pitcherType)) {	// 조인 && 투수유형 : pitcherType
	   							if (dao1.equals(def.getName())) {						// 9명 중 가장 홈런이 많은 선수
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
	   			lineUp.set(4, MaxSlg1.get(0));		// 5번타자
	   			
	   			for(int j = 0; j < list.size(); j++) {
       	   			String dao1 = list.get(j);
       	   			
       	   			try {	
	   				for(int i = 0; i < hitterTypeList22.size() ; i++) {
	   		   			hitterDAO hit = hitterTypeList22.get(i);				// 투수유형별 타자기록 불러옴
	   					
	   		   			for(int k = 0; k < defenseList22.size(); k++) {
	   						defenseDAO def = defenseList22.get(k);
	   		   				if (dao1.equals(hit.getName()) && hit.getPitcherType().equals(pitcherType)) {	// 조인 && 투수유형 : pitcherType
	   							if (dao1.equals(def.getName())) {						// 9명 중 가장 홈런이 많은 선수
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
	   			lineUp.set(5, MaxSlg2.get(0));		// 6번타자
	   				
	   			for(int j = 0; j < list.size(); j++) {
       	   			String dao1 = list.get(j);
       	   			
       	   			try {	
	   				for(int i = 0; i < hitterTypeList22.size() ; i++) {
	   		   			hitterDAO hit = hitterTypeList22.get(i);				// 투수유형별 타자기록 불러옴
	   					
	   		   			for(int k = 0; k < defenseList22.size(); k++) {
	   						defenseDAO def = defenseList22.get(k);
	   		   				if (dao1.equals(hit.getName()) && hit.getPitcherType().equals(pitcherType)) {	// 조인 && 투수유형 : pitcherType
	   							if (dao1.equals(def.getName())) {						// 9명 중 가장 홈런이 많은 선수
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
	   			lineUp.set(6, MaxSlg3.get(0));		// 7번타자
	   			
	   			for(int j = 0; j < list.size(); j++) {
       	   			String dao1 = list.get(j);
       	   			
       	   			try {	
	   				for(int i = 0; i < hitterTypeList22.size() ; i++) {
	   		   			hitterDAO hit = hitterTypeList22.get(i);				// 투수유형별 타자기록 불러옴
	   					
	   		   			for(int k = 0; k < defenseList22.size(); k++) {
	   						defenseDAO def = defenseList22.get(k);
	   		   				if (dao1.equals(hit.getName()) && hit.getPitcherType().equals(pitcherType)) {	// 조인 && 투수유형 : pitcherType
	   							if (dao1.equals(def.getName())) {						// 9명 중 가장 홈런이 많은 선수
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
	   			lineUp.set(8, MaxObp.get(0));		// 9번타자
	   				//(안타 + 볼넷(bb) + 사구(hbp)) / (타수 + 볼넷 + 사구 + 생략(희생플라이))
	   				
	   			for(int j = 0; j < list.size(); j++) {
	       	   		String dao1 = list.get(j);
	       	   			
	       	   		try {	
		   				for(int i = 0; i < hitterTypeList22.size() ; i++) {
		   		   			hitterDAO hit = hitterTypeList22.get(i);				// 투수유형별 타자기록 불러옴
		   		   			if (dao1.equals(hit.getName()) && hit.getPitcherType().equals(pitcherType)) {	// 조인 && 투수유형 : pitcherType
		   							
		   		   			for(int l = 0; l < lineUp.size(); l++) {
								if (!selectedPlayers.contains(dao1)) {
		   							lineUpDAO dao = new lineUpDAO();
		   									
		   							dao.setName(hit.getName());
		   							dao.setH(hit.getH());
		   							dao.setPosition("지명타자");
		   									
		   							others.add(dao);
		   						}
		   		   			}
		   					}
	       	   			}
	       	   			}catch(NullPointerException e) { 
	     	    		}
	       	   		}
		   		lineUp.set(7, others.get(0));		// 8번타자
	   			
	   				
	   			
       	   		
       	   		
	     	    System.out.println("\n" + "NC의 선발 라인업" + "\n");
	     	    System.out.println("유격수: " +  shortStop + "\t" + "안타개수: " + tmp1);
		   		System.out.println("중견수: " +  centerFielder + "\t" + "안타개수: " + tmp2);
		   		System.out.println("2루수: " +  secondBaseman + "\t" + "안타개수: " + tmp3);
		   		System.out.println("3루수: " +  thirdBaseman + "\t" + "안타개수: " + tmp4);
		   		System.out.println("우익수: " +  rightFielder + "\t" + "안타개수: " + tmp5);
		   		System.out.println("좌익수: " +  leftFielder + "\t" + "안타개수: " + tmp6);
		   		System.out.println("1루수: " +  firstBaseman + "\t" + "안타개수: " + tmp7);
		   		System.out.println("포수: " +  catcher + "\t" + "안타개수: " + tmp8);
		   		System.out.println("지명타자: " +  designatedHitter + "\t" + "안타개수: " + tmp9 + "\t");
		   		System.out.println("선발투수: " + startingPitcher1 + "\t" + "이닝수: " + tmp10);
		   		
		   		
//		   		System.out.println("정렬 전 수비선발 선수");
//		   		for(int i = 0; i < list.size() ; i++) {
//		   			System.out.println(list.get(i));
//		        }
		   		System.out.println("타순 정렬 후 선발명단");
		   		for(int i = 0; i < lineUp.size() ; i++) {
		     	   	lineUpDAO dao = lineUp.get(i);
		     	   	System.out.println(i+1 + "번타자 "+ "\t" + dao.getPosition() + "\t" + dao.getName());
		        }
		   		System.out.println("선발투수 : " + (pitchingList.get(0)));
		   		
		   		System.out.println("\n" + "서브선수 명단");
		   		for(int i = 0; i < subList.size(); i++) {
		   			System.out.println(subPosition[i] + "\t" + subList.get(i));
		   		}
		   		System.out.println("\n" + "투수 대기명단");
		   		for(int i = 1; i < pitchingList.size(); i++) {
		   			if (i < 5) 
		   				System.out.println("대기 선발 : " + pitchingList.get(i));
		   			if (i > 5 && i <= 9)
		   				System.out.println("중간계투 : " + pitchingList.get(i));
		   			if (i == 5)
		   				System.out.println("마무리투수 : " + pitchingList.get(i));
		   		}
		}
		public ArrayList<String> aList(String myTeam, String opponentTeam, String pitcherType) throws SQLException {

	   		ArrayList<defenseDAO> defenseList22 = DatabaseManager.getInstance().getDefenseList(2022);		// 22시즌 수비기록 인스턴스 얻어오기
	        	        
	        ArrayList<hitterDAO> hitterOpponentList22 = DatabaseManager.getInstance().getHitterOpponentList(2022);		// 22시즌 상대팀별 타자기록 인스턴스 얻어오기
	       	        
	    
	        	/** 선수별 수비 포지션 정하기 (안타 기준) */
	        
			   	int tmp1 = 0, tmp2 = 0, tmp3 = 0, tmp4 = 0, tmp5 = 0, tmp6 = 0, tmp7 = 0, tmp8 = 0, tmp9 = 0;								// 타자선발명단 최다안타 정하는 변수
			   		   	
			   	// 
			   	String leftFielder = ""; String centerFielder = ""; String rightFielder = "";  String designatedHitter = "";
			   	String firstBaseman = ""; String secondBaseman = ""; String thirdBaseman = ""; String shortStop = ""; String catcher = "";	
			   		   	
			   	
		     	    for(int i = 0; i < hitterOpponentList22.size() ; i++) {
		     	    	hitterDAO dao = hitterOpponentList22.get(i);												// 변경 - 22년 타자기록 -> 22년 상대팀별 타자기록
		     	    	if (dao.getTeamName().equals(myTeam) && dao.getOpponent().equals(opponentTeam)) {			// 팀명 : myTeam, 상대팀명 : opponentTeam
		     	    		try {
			     	    		for(int j = 0; j < defenseList22.size() ; j++) {
			     	    			defenseDAO dao1 = defenseList22.get(j);
			     	    			
				        	   		if(dao1.getPosition().equals("유격수") && dao1.getName().equals(dao.getName())) {					// defense와 hitter 조인 && 포지션 유격수
				        	   			if (dao.getH() > tmp1 && dao1.getG() > 30) {												// 안타가 가장 많은, 30경기 이상 출전														// 30경기 이상 출전
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
		     	    
	    	   		list.add(0, shortStop);		// 유격수 list에 추가
	    	   		
			        
		     	  	for(int i = 0; i < hitterOpponentList22.size() ; i++) {
		     	    	hitterDAO dao = hitterOpponentList22.get(i);
		     	    	if (dao.getTeamName().equals(myTeam) && dao.getOpponent().equals(opponentTeam)) {
		     	    		try {
			     	    		for(int j = 0; j < defenseList22.size() ; j++) {
			     	    			defenseDAO dao1 = defenseList22.get(j);
			     	    			
				        	   		if(dao1.getPosition().equals("중견수") && dao1.getName().equals(dao.getName())) {					// defense와 hitter 조인 && 포지션 중견수
				        	   			if (dao.getH() > tmp2 && dao1.getG() > 10) {												// 안타가 가장 많은, 30경기 이상 출전														// 30경기 이상 출전
				        	   				
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
		        	list.add(1, centerFielder);		// 중견수 list에 추가
			     	 
		        	for(int i = 0; i < hitterOpponentList22.size() ; i++) {
		     	    	hitterDAO dao = hitterOpponentList22.get(i);
		     	    	if (dao.getTeamName().equals(myTeam) && dao.getOpponent().equals(opponentTeam)) {
		     	    		try {
				        	   	for(int j = 0; j < defenseList22.size() ; j++) {
				     	    		defenseDAO dao1 = defenseList22.get(j);
				     	    		
					        	   	if(dao1.getPosition().equals("2루수") && dao1.getName().equals(dao.getName())) {					// defense와 hitter 조인 && 포지션 2루수
					        	   		if (dao.getH() > tmp3 && dao1.getG() > 30) {												// 안타가 가장 많은, 30경기 이상 출전														// 30경기 이상 출전
					        	   				
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
				    list.add(2, secondBaseman);			// 2루수 list에 추가
				       
				    for(int i = 0; i < hitterOpponentList22.size() ; i++) {
		     	    	hitterDAO dao = hitterOpponentList22.get(i);
		     	    	if (dao.getTeamName().equals(myTeam) && dao.getOpponent().equals(opponentTeam)) {
			     	    		try {
				        	   		
				        	    for(int j = 0; j < defenseList22.size() ; j++) {
					     	    	defenseDAO dao1 = defenseList22.get(j);
						        	if(dao1.getPosition().equals("3루수") && dao1.getName().equals(dao.getName())) {					// defense와 hitter 조인 && 포지션 3루수
						        	   	if (dao.getH() > tmp4 && dao1.getG() > 30) {													// 안타가 가장 많은, 30경기 이상 출전														// 30경기 이상 출전
						        	   				
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
				        list.add(3, thirdBaseman);		// 3루수 list에 추가
				        
				        for(int i = 0; i < hitterOpponentList22.size() ; i++) {
			     	    	hitterDAO dao = hitterOpponentList22.get(i);
			     	    	if (dao.getTeamName().equals(myTeam) && dao.getOpponent().equals(opponentTeam)) {
			     	    		try {	   	
				        	    for(int j = 0; j < defenseList22.size() ; j++) {
					     	    	defenseDAO dao1 = defenseList22.get(j);
						        	if(dao1.getPosition().equals("우익수") && dao1.getName().equals(dao.getName())) {					// defense와 hitter 조인 && 포지션 우익수
						        	   	if (dao.getH() > tmp5 && dao1.getG() > 30) {													// 안타가 가장 많은, 30경기 이상 출전														// 30경기 이상 출전
						        	   				
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
				        list.add(4, rightFielder);		// 우익수 list에 추가
				        	
				        for(int i = 0; i < hitterOpponentList22.size() ; i++) {
			     	    	hitterDAO dao = hitterOpponentList22.get(i);
			     	    	if (dao.getTeamName().equals(myTeam) && dao.getOpponent().equals(opponentTeam)) {
				     	    	try {
				        	    for(int j = 0; j < defenseList22.size() ; j++) {
					     	    	defenseDAO dao1 = defenseList22.get(j);
						        	if(dao1.getPosition().equals("좌익수") && dao1.getName().equals(dao.getName())) {					// defense와 hitter 조인 && 포지션 좌익수
						        	   	if (dao.getH() > tmp6 && dao1.getG() > 30) {													// 안타가 가장 많은, 30경기 이상 출전														// 30경기 이상 출전
						        	   				
						        	   			if(!list.contains(dao.getName())) {		// list에 이미 있는 선수는 제외 -> 중복되는 선수 제거
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
				        list.add(5, leftFielder);		// list에 좌익수 추가
				        
				        for(int i = 0; i < hitterOpponentList22.size() ; i++) {
			     	    	hitterDAO dao = hitterOpponentList22.get(i);
			     	    	if (dao.getTeamName().equals(myTeam) && dao.getOpponent().equals(opponentTeam)) {
				     	    		try {	   	
				     	    		for(int j = 0; j < defenseList22.size() ; j++) {
					     	    	defenseDAO dao1 = defenseList22.get(j);
						        	if(dao1.getPosition().equals("1루수") && dao1.getName().equals(dao.getName())) {					// defense와 hitter 조인 && 포지션 1루수
						        	   	if (dao.getH() > tmp7 && dao1.getG() > 30) {													// 안타가 가장 많은, 30경기 이상 출전														// 30경기 이상 출전
						        	   				
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
				     	    		
				        list.add(6, firstBaseman);		//list에 1루수 추가
				        for(int i = 0; i < hitterOpponentList22.size() ; i++) {
			     	    	hitterDAO dao = hitterOpponentList22.get(i);
			     	    	if (dao.getTeamName().equals(myTeam) && dao.getOpponent().equals(opponentTeam)) {
				     	    		try {	   	
				        	    for(int j = 0; j < defenseList22.size() ; j++) {
					     	    	defenseDAO dao1 = defenseList22.get(j);
						        	if(dao1.getPosition().equals("포수") && dao1.getName().equals(dao.getName())) {					// defense와 hitter 조인 && 포지션 포수
						        	   	if (dao.getH() > tmp8 && dao1.getG() > 30) {													// 안타가 가장 많은, 30경기 이상 출전														// 30경기 이상 출전
						        	   				
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
				        list.add(7, catcher);				//list에 포수 추가
				        
				        for(int i = 0; i < hitterOpponentList22.size() ; i++) {
			     	    	hitterDAO dao = hitterOpponentList22.get(i);
			     	    	if (dao.getTeamName().equals(myTeam) && dao.getOpponent().equals(opponentTeam)) {
				     	    		try {
				     	    		for(int j = 0; j < defenseList22.size() ; j++) {
					     	    	defenseDAO dao1 = defenseList22.get(j);
						        	if(dao1.getName().equals(dao.getName())) {													// defense와 hitter 조인
						        	   	if (dao.getH() > tmp9 && dao1.getG() > 30) {											// 안타가 가장 많은, 30경기 이상 출전														// 30경기 이상 출전
						        	   				
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
				        list.add(8, designatedHitter);		//list에 지명타자 추가
				        
				        return list;
		}
		public ArrayList<lineUpDAO> normalList(String myTeam, String opponentTeam, ArrayList<hitterDAO> hitterOpponentList22, ArrayList<defenseDAO> defenseList22, ArrayList<runnerDAO> runnerList22) {
		    ArrayList<lineUpDAO> list = new ArrayList<>();
		    

		    String[] positions = {"유격수", "중견수", "2루수", "3루수", "우익수", "좌익수", "1루수", "포수", "지명타자"};

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
		                                    topPlayers.add(r, new lineUpDAO(hitter.getName(), "지명타자", hitter.getH(), r + 1));
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

		    String[] positions = {"유격수", "중견수", "2루수", "3루수", "우익수", "좌익수", "1루수", "포수", "지명타자"};

		    for (String position : positions) {
		        for (int rank = 1; rank <= 4; rank++) {
		            int maxHits = -1;
		            lineUpDAO maxHitPlayer = null;

		            for (hitterDAO hitterType : hitterTypeList22) {
		                if (hitterType.getPitcherType().equals("좌투수")) {
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

		    String[] positions = {"유격수", "중견수", "2루수", "3루수", "우익수", "좌익수", "1루수", "포수", "지명타자"};

		    for (String position : positions) {
		        for (int rank = 1; rank <= 4; rank++) {
		            int maxHits = -1;
		            lineUpDAO maxHitPlayer = null;

		            for (hitterDAO hitterType : hitterTypeList22) {
		                if (hitterType.getPitcherType().equals("언더")) {
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
		        player.setPosition(j == 0 ? "선발투수" : "대기 선발");
		        pitchingList.add(player);
		    }

		    for (String pitcherName : new String[]{closer, relief1, relief2, relief3, relief4}) {
		        if (!pitcherName.isEmpty()) {
		            lineUpDAO player = new lineUpDAO();
		            player.setName(pitcherName);

		            if (pitcherName.equals(closer)) {
		                player.setPosition("마무리 투수");
		            } else {
		                player.setPosition("중간계투");
		            }

		            pitchingList.add(player);
		        }
		    }

		    return pitchingList;
		}
	    

		public static void main(String[] args) throws SQLException {
			ArrayList<defenseDAO> defenseList22 = DatabaseManager.getInstance().getDefenseList(2022);		// 22시즌 수비기록 인스턴스 얻어오기
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
	        String awayteam = "키움";
	        
			//rosterAnalysis startRoster = new rosterAnalysis(hometeam, awayteam, "우투수");
			
//			recommendationStrategyAnalysis analysis = new recommendationStrategyAnalysis("NC", "삼성");
	        
//			ArrayList<lineUpDAO> lineUpRight_myTeam = startRoster.normalList(hometeam, awayteam, hitterOpponentList22, defenseList22, runnerList22);
//			for (lineUpDAO player : lineUpRight_myTeam) {
//	            System.out.println("Player Name: " + player.getName() + ", Position: " + player.getPosition() + ", rank: " + player.getRank() + ", hit: " + player.getH() + ", 타순: " + player.getLineUp());
//	        }
//			System.out.println("---------------------------------------------------------------");
//			ArrayList<lineUpDAO> lineUpPitcher_myTeam = startRoster.createPitchingList(pitcherList22,hometeam);
//			for (lineUpDAO player : lineUpPitcher_myTeam) {
//	            System.out.println("Player Name: " + player.getName() + ", Position: " + player.getPosition());
//	        }
//			ArrayList<lineUpDAO> lineUpLeft_myTeam = startRoster.leftList(hometeam, awayteam, hitterOpponentList22, defenseList22,hitterTypeList22, runnerList22);
//			for (lineUpDAO player : lineUpLeft_myTeam) {
//	            System.out.println("Player Name: " + player.getName() + ", Position: " + player.getPosition() + ", rank: " + player.getRank() + ", hit: " + player.getH()+ ", 타순: " + player.getLineUp());
//	        }
//			System.out.println("---------------------------------------------------------------");
//			ArrayList<lineUpDAO> lineUpUnder_myTeam = startRoster.underList(hometeam, awayteam, hitterOpponentList22, defenseList22,hitterTypeList22, runnerList22);
//			for (lineUpDAO player : lineUpUnder_myTeam) {
//	            System.out.println("Player Name: " + player.getName() + ", Position: " + player.getPosition() + ", rank: " + player.getRank() + ", hit: " + player.getH()+ ", 타순: " + player.getLineUp());
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