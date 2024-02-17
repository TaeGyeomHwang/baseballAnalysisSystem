package swingEx4;

public class lineUpDAO {
	private String name;
    private String teamName;
    private String position;
	private int sb;
	private int hr;
	private int h;
	private float slg;
	private int rbi;
	private int sac;
	private float obp;
	private float ip;
	private int sv;
	private int hld;
	private float fpct;
	private int lineup;
	private int rank;
	
	
	public lineUpDAO() {}
	public lineUpDAO(String name, String position, int h, int rank) {
        this.name = name;
        this.position = position;
        this.h = h;
        this.rank = rank;
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
	public int getSb() {
        return sb;
    }

    public void setSb(int sb) {
        this.sb = sb;
    }
    public int getHr() {
	    return hr;
	}

	public void setHr(int hr) {
	    this.hr = hr;
	}
	public int getH() {
	    return h;
	}

	public void setH(int h) {
	    this.h = h;
	}
	public float getSlg() {
        return slg;
    }

    public void setSlg(float slg) {
        this.slg = slg;
    }
    public int getRbi() {
	    return rbi;
	}

	public void setRbi(int rbi) {
	    this.rbi = rbi;
	}
	public int getSac() {
        return sac;
    }

    public void setSac(int sac) {
        this.sac = sac;
    }
    public float getObp() {
        return obp;
    }

    public void setObp(float obp) {
        this.obp = obp;
    }
    public float getIp() {
        return ip;
    }

    public void setIp(float ip) {
        this.ip = ip;
    }
    public int getSv() {
        return sv;
    }

    public void setSv(int sv) {
        this.sv = sv;
    }
    public int getHld() {
        return hld;
    }

    public void setHld(int hld) {
        this.hld = hld;
    }
    public float getFpct() {
    	return fpct;
    }

    public void setFpct(float fpct) {
    	this.fpct = fpct;
    }
    
    public int getLineUp() {
        return lineup;
    }

    public void setLineUp(int lineup) {
        this.lineup = lineup;
    }
    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
