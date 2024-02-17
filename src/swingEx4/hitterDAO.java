package swingEx4;

public class hitterDAO {
    private String name;
    private String teamName;
    private float average;
    private int g;
    private int pa;
    private int ab;
    private int r;
    private int h;
    private int doubleBase;
    private int tripleBase;
    private int hr;
    private int tb;
    private int rbi;
    private int sac;
    private int sf;
    private int bb;
    private int ibb;
    private int hbp;
    private int so;
    private int gdp;
    private float slg;
    private float obp;
    private float ops;
    private int mh;
    private float risp;
    private float phba;
    private int xbh;
    private int go;
    private int ao;
    private float goao;
    private int gwrbi;
    private float bbk;
    private float ppa;
    private float isop;
    private float xr;
    private float gpa;
    private String opponent;
    private String pitcherType;
    
    public double[] getStats() {
        double[] stats = new double[34];
        stats[0] = this.getAverage();
        stats[1] = this.getG();
        stats[2] = this.getPa();
        stats[3] = this.getAb();
        stats[4] = this.getR();
        stats[5] = this.getH();
        stats[6] = this.getDoubleBase();
        stats[7] = this.getTripleBase();
        stats[8] = this.getHr();
        stats[9] = this.getTb();
        stats[10] = this.getRbi();
        stats[11] = this.getSac();
        stats[12] = this.getSf();
        stats[13] = this.getBb();
        stats[14] = this.getIbb();
        stats[15] = this.getHbp();
        stats[16] = this.getSo();
        stats[17] = this.getGdp();
        stats[18] = this.getSlg();
        stats[19] = this.getObp();
        stats[20] = this.getOps();
        stats[21] = this.getMh();
        stats[22] = this.getRisp();
        stats[23] = this.getPhba();
        stats[24] = this.getXbh();
        stats[25] = this.getGo();
        stats[26] = this.getAo();
        stats[27] = this.getGoao();
        stats[28] = this.getGwrbi();
        stats[29] = this.getBbk();
        stats[30] = this.getPpa();
        stats[31] = this.getIsop();
        stats[32] = this.getXr();
        stats[33] = this.getGpa();
        return stats;
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
    
    public float getAverage() {
        return average;
      }

    public void setAverage(float average) {
      this.average = average;
    }
    
    public int getG() {
    		return g;
    }

    public void setG(int g) {
    		this.g = g;
 	}
    
    public int getPa() {
        return pa;
      }

      public void setPa(int pa) {
        this.pa = pa;
      }

      public int getAb() {
        return ab;
      }

      public void setAb(int ab) {
        this.ab = ab;
      }

      public int getR() {
    	    return r;
    	  }

    	  public void setR(int r) {
    	    this.r = r;
    	  }

    	  public int getH() {
    	    return h;
    	  }

    	  public void setH(int h) {
    	    this.h = h;
    	  }

    	  public int getDoubleBase() {
    	    return doubleBase;
    	  }

    	  public void setDoubleBase(int doubleBase) {
    	    this.doubleBase = doubleBase;
    	  }

    	  public int getTripleBase() {
    	    return tripleBase;
    	  }

    	  public void setTripleBase(int tripleBase) {
    	    this.tripleBase = tripleBase;
    	  }

    	  public int getHr() {
    	    return hr;
    	  }

    	  public void setHr(int hr) {
    	    this.hr = hr;
    	  }

    	  public int getTb() {
    	    return tb;
    	  }

    	  public void setTb(int tb) {
    	    this.tb = tb;
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

          public int getSf() {
              return sf;
          }

          public void setSf(int sf) {
              this.sf = sf;
          }

          public int getBb() {
              return bb;
          }

          public void setBb(int bb) {
              this.bb = bb;
          }

          public int getIbb() {
              return ibb;
          }

          public void setIbb(int ibb) {
              this.ibb = ibb;
          }

          public int getHbp() {
              return hbp;
          }

          public void setHbp(int hbp) {
              this.hbp = hbp;
          }

          public int getSo() {
              return so;
          }

          public void setSo(int so) {
              this.so = so;
          }

          public int getGdp() {
              return gdp;
          }

          public void setGdp(int gdp) {
              this.gdp = gdp;
          }

          public float getSlg() {
              return slg;
          }

          public void setSlg(float slg) {
              this.slg = slg;
          }

          public float getObp() {
              return obp;
          }

          public void setObp(float obp) {
              this.obp = obp;
          }

          public float getOps() {
              return ops;
          }

          public void setOps(float ops) {
              this.ops = ops;
          }

          public int getMh() {
              return mh;
          }

          public void setMh(int mh) {
              this.mh = mh;
          }

          public float getRisp() {
              return risp;
          }

          public void setRisp(float risp) {
              this.risp = risp;
          }

          public float getPhba() {
              return phba;
          }

          public void setPhba(float phba) {
              this.phba = phba;
          }

          public int getXbh() {
              return xbh;
          }

          public void setXbh(int xbh) {
              this.xbh = xbh;
          }

          public int getGo() {
              return go;
          }

          public void setGo(int go) {
              this.go = go;
          }

          public int getAo() {
              return ao;
          }

          public void setAo(int ao) {
              this.ao = ao;
          }

          public float getGoao() {
              return goao;
          }

          public void setGoao(float goao) {
              this.goao = goao;
          }

          public int getGwrbi() {
              return gwrbi;
          }

          public void setGwrbi(int gwrbi) {
              this.gwrbi = gwrbi;
          }

          public float getBbk() {
              return bbk;
          }

          public void setBbk(float bbk) {
              this.bbk = bbk;
          }

          public float getPpa() {
              return ppa;
          }

          public void setPpa(float ppa) {
              this.ppa = ppa;
          }

          public float getIsop() {
              return isop;
          }

          public void setIsop(float isop) {
              this.isop = isop;
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
          
       public String getOpponent() {
           return opponent;
       }

       public void setOpponent(String opponent) {
           this.opponent = opponent;
       }
       
       public String getPitcherType() {
           return pitcherType;
       }

       public void setPitcherType(String pitcherType) {
           this.pitcherType = pitcherType;
       }
          
}