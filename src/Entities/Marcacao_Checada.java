package Entities;

public class Marcacao_Checada {
	
	private int num_mec;
	private String nome;
	private String dta_ref;
	private String des_ref;
	private String des_pr;
	private String dta_reg;
	private int cod_ref;
	private int cod_pr;
	
	public Marcacao_Checada() {}

	public Marcacao_Checada(int num_mec, String nome, String dta_ref, String des_ref, String des_pr,  String dta_reg,
			 int cod_ref, int cod_pr) {
		super();
		this.num_mec = num_mec;
		this.nome = nome;
		this.dta_ref = dta_ref;
		this.des_ref = des_ref;
		this.des_pr = des_pr;
		this.dta_reg = dta_reg;
		this.cod_ref = cod_ref;
		this.cod_pr = cod_pr;
	}

	public int getNum_mec() {
		return num_mec;
	}

	public void setNum_mec(int num_mec) {
		this.num_mec = num_mec;
	}

	public String getDta_ref() {
		return dta_ref;
	}

	public void setDta_ref(String dta_ref) {
		this.dta_ref = dta_ref;
	}

	public String getDes_ref() {
		return des_ref;
	}

	public void setDes_ref(String des_ref) {
		this.des_ref = des_ref;
	}

	public String getDes_pr() {
		return des_pr;
	}

	public void setDes_pr(String des_pr) {
		this.des_pr = des_pr;
	}

	public String getDta_reg() {
		return dta_reg;
	}

	public void setDta_reg(String dta_reg) {
		this.dta_reg = dta_reg;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getCod_ref() {
		return cod_ref;
	}

	public void setCod_ref(int cod_ref) {
		this.cod_ref = cod_ref;
	}

	public int getCod_pr() {
		return cod_pr;
	}

	public void setCod_pr(int cod_pr) {
		this.cod_pr = cod_pr;
	}
	
	

}
