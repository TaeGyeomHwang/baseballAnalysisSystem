package swingEx4;

public class analysisDAO  {
	private String name;
    private String teamName;
    private String position;
    
    
    private int g;
    private int teamW;
    private int teamL;
    private float wpct;			// 팀
    
	private int w;
	private float era;
	private int so;
	private int sv;
	private float whip;			// 투수
	private float whip22;
	private float whip21;
	private String hitterType;
	private String opponent;
	
	private float average;
	private float ops22;
	private float ops21;


	private int rbi;
	private int hr;
	private float ops;
	private float xr;
	private float gpa;
	private float phba;			// 타자
	private int ab;
	
	private int sb;
	private float sbPer;		// 주루
	
	private float csPer;		// 수비
	private double fpct;
	
	public int getG() {
        return g;
    }
    public void setG(int g) {
        this.g = g;
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
	public int getW() {
        return w;
    }
    public void setW(int w) {
        this.w = w;
    }
	public float getEra() {
        return era;
    }
    public void setEra(float era) {
        this.era = era;
    }
    public int getSo() {
        return so;
    }
    public void setSo(int so) {
        this.so = so;
    }
    public int getSv() {
        return sv;
    }
    public void setSv(int sv) {
        this.sv = sv;
    }
    public float getWhip() {
        return whip;
    }
    public void setWhip(float whip) {
        this.whip = whip;
    }
    public float getWhip22() {
        return whip22;
    }
    public void setWhip22(float whip22) {
        this.whip22 = whip22;
    }
    public float getWhip21() {
        return whip21;
    }
    public void setWhip21(float whip21) {
        this.whip21 = whip21;
    }
    public float getAverage() {
        return average;
    }
    public void setAverage(float average) {
      this.average = average;
    }
    public float getOps22() {
        return ops22;
    }
    public void setOps22(float ops22) {
      this.ops22 = ops22;
    }
    public float getOps21() {
        return ops21;
    }
    public void setOps21(float ops21) {
      this.ops21 = ops21;
    }
    public int getRbi() {
	    return rbi;
	}
	public void setRbi(int rbi) {
	    this.rbi = rbi;
	}
	public int getHr() {
	    return hr;
	}
	public void setHr(int hr) {
	    this.hr = hr;
	}
	public float getOps() {
        return ops;
    }
    public void setOps(float ops) {
        this.ops = ops;
    }
    public float getXr() {
        return xr;
    }
    public void setXr(float xr) {
        this.xr = xr;
    }
    public float getGpa() {
        return gpa;
    }
    public void setGpa(float gpa) {
    	this.gpa = gpa;
    }
    public float getPhba() {
        return phba;
    }
    public void setPhba(float phba) {
        this.phba = phba;
    }
    public int getSb() {
        return sb;
    }
    public void setSb(int sb) {
        this.sb = sb;
    }
    public float getSbPer() {
        return sbPer;
    }
    public void setSbPer(float sbPer) {
        this.sbPer = sbPer;
    }
    public float getCsPer() {
    	return csPer;
	}
	public void setCsPer(float csPer) {
    	this.csPer = csPer;
	}
	public double getFpct() {
    	return fpct;
    }
    public void setFpct(double avgFpct) {
    	this.fpct = avgFpct;
    }
    public int getTeamW() {
        return teamW;
    }

    public void setTeamW(int teamW) {
        this.teamW = teamW;
    }

    public int getTeamL() {
        return teamL;
    }

    public void setTeamL(int teamL) {
        this.teamL = teamL;
    }

    public float getWpct() {
        return wpct;
    }

    public void setWpct(float wpct) {
        this.wpct = wpct;
    }
    public String getOpponent() {
		return opponent;
    }

    public void setOpponent(String opponent) {
    	this.opponent = opponent;
    }
    
    public String getHitterType() {
        return hitterType;
    }
    
    public void setHitterType(String hitterType) {
        this.hitterType = hitterType;
    }
    public int getAb() {
        return ab;
      }

      public void setAb(int ab) {
        this.ab = ab;
      }
    
}
