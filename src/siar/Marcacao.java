package siar;

public class Marcacao {
	
	private int num_mec;
	private String dta_ref;
	private String des_ref;
	private String des_pr;
	private String dta_des;
	private String dta_reg;
	private String verec;
	private int cod_ref;
	private int cod_pr;
	
	public Marcacao() {}

	public Marcacao(int num_mec, String dta_ref, String des_ref, String des_pr, String dta_des, String dta_reg,String verec, int cod_ref, int cod_pr) {
		this.num_mec = num_mec;
		this.dta_ref = dta_ref;
		this.des_ref = des_ref;
		this.des_pr = des_pr;
		this.dta_des = dta_des;
		this.dta_reg = dta_reg;
		this.verec = verec;
		this.cod_ref = cod_ref;
		this.cod_pr = cod_pr;
	}

	public int getNum_mec() {
		return num_mec;
	}

	public String getDta_ref() {
		return dta_ref;
	}

	public String getDes_ref() {
		return des_ref;
	}

	public String getDes_pr() {
		return des_pr;
	}

	public String getDta_des() {
		return dta_des;
	}

   public String getDta_reg() {
		return dta_reg;
	}
   
	public String getVerec() {
		return verec;
	}

	public int getCod_ref() {
		return cod_ref;
	}

	public int getCod_pr() {
		return cod_pr;
	}
	
}
