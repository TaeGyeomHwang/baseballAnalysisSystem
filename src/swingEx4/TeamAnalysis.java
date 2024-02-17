package swingEx4;

import java.util.HashMap;
import java.util.List;

public class TeamAnalysis {
    // getter setter 설정


	public HashMap<String, TeamAnalysis> analyzeTeams(List<hitterDAO> hitters, List<defenseDAO> defensePlayers) {
	    HashMap<String, TeamAnalysis> teamAnalysisData = new HashMap<>();
	
	    for (hitterDAO hitter : hitters) {
	        String teamName = hitter.getTeamName();
	        TeamAnalysis analysis = teamAnalysisData.get(teamName);
	
	        if (analysis == null) {
	            analysis = new TeamAnalysis();
	            teamAnalysisData.put(teamName, analysis);
	        }
	
	        // 타자 통계를 기반으로 분석 필드 업데이트
	    }
	
	    for (defenseDAO defensePlayer : defensePlayers) {
	        String teamName = defensePlayer.getTeamName();
	        TeamAnalysis analysis = teamAnalysisData.get(teamName);
	
	        if (analysis == null) {
	            analysis = new TeamAnalysis();
	            teamAnalysisData.put(teamName, analysis);
	        }
	
	        // 수비 선수 통계를 기반으로 분석 필드 업데이트
	    }
	
	    // 필요한 경우 팀 분석 데이터에 대한 최종 계산 수행
	
	    return teamAnalysisData;
	}
}