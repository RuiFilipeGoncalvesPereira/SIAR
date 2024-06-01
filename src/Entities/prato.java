package Entities;

public class prato {
	
	private int cod_prato;
	private String desc_prato;
	private String dta_reg;
	
	public prato() {}

	public prato(int cod_prato, String desc_prato, String dta_reg) {
		this.cod_prato = cod_prato;
		this.desc_prato = desc_prato;
		this.dta_reg = dta_reg;
	}

	public int getCod_prato() {
		return cod_prato;
	}

	public String getDesc_prato() {
		return desc_prato;
	}

	public void setDesc_prato(String desc_prato) {
		this.desc_prato = desc_prato;
	}

	public String getDta_reg() {
		return dta_reg;
	}
	
}
