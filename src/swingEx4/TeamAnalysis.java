package swingEx4;

import java.util.HashMap;
import java.util.List;

public class TeamAnalysis {
    // getter setter ����


	public HashMap<String, TeamAnalysis> analyzeTeams(List<hitterDAO> hitters, List<defenseDAO> defensePlayers) {
	    HashMap<String, TeamAnalysis> teamAnalysisData = new HashMap<>();
	
	    for (hitterDAO hitter : hitters) {
	        String teamName = hitter.getTeamName();
	        TeamAnalysis analysis = teamAnalysisData.get(teamName);
	
	        if (analysis == null) {
	            analysis = new TeamAnalysis();
	            teamAnalysisData.put(teamName, analysis);
	        }
	
	        // Ÿ�� ��踦 ������� �м� �ʵ� ������Ʈ
	    }
	
	    for (defenseDAO defensePlayer : defensePlayers) {
	        String teamName = defensePlayer.getTeamName();
	        TeamAnalysis analysis = teamAnalysisData.get(teamName);
	
	        if (analysis == null) {
	            analysis = new TeamAnalysis();
	            teamAnalysisData.put(teamName, analysis);
	        }
	
	        // ���� ���� ��踦 ������� �м� �ʵ� ������Ʈ
	    }
	
	    // �ʿ��� ��� �� �м� �����Ϳ� ���� ���� ��� ����
	
	    return teamAnalysisData;
	}
}