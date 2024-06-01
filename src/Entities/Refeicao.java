package Entities;

public class Refeicao {
	
	private int cod_refeicao;
	private String desc_refeicao;
	private String dta_reg;
	
	public Refeicao() {}

	public Refeicao(int cod_refeicao, String desc_refeicao, String dta_reg) {
		this.cod_refeicao = cod_refeicao;
		this.desc_refeicao = desc_refeicao;
		this.dta_reg = dta_reg;
	}

	public int getCod_refeicao() {
		return cod_refeicao;
	}

	public String getDesc_refeicao() {
		return desc_refeicao;
	}

	public void setDesc_refeicao(String desc_refeicao) {
		this.desc_refeicao = desc_refeicao;
	}

	public String getDta_reg() {
		return dta_reg;
	}
}
