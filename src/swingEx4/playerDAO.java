package swingEx4;

import java.util.ArrayList;
import java.util.Date;

public class playerDAO {
     private int backNumber;
     private String name;
     private String teamName;
     private String position;
     private String physical;
     private String career;
     private String condition;
     
     private Date d = new Date();	//date 타입 생성 한 후,
     private String birthdate = d.toString();	//date 타입을 string타입으로 형변환하고 가져옴

     public int getBackNumber() {
            return backNumber;
     }

     public void setBackNumber(int backNumber) {
            this.backNumber = backNumber;
     }

     public String getName() {
            return name;
     }

     public void setName(String name) {
            this.name = name;
     }

     public String getTeamName() {
            return teamName;
     }

     public void setTeamName(String teamName) {
            this.teamName = teamName;
     }
        
     public String getPosition() {
            return position;
     }

     public void setPosition(String position) {
            this.position = position;
     }
     
     public String getBirthdate() {
            return birthdate;
     }

     public void setBirthdate(String birthdate) {
            this.birthdate = birthdate;
     }

     public String getPhysical() {
            return physical;
     }

     public void setPhysical(String physical) {
            this.physical = physical;
     }

     public String getCareer() {
            return career;
     }

     public void setCareer(String career) {
            this.career = career;
     }

	public String getCondition() {
			return condition;
	}
	
	public void setCondition(String condition) {
        	this.condition = condition;
	}
	
}