package siar;

public class Ementas {
	
	private String dta_desativo;
	private String ref;
	private String pr;
	private int cod_ref;
	private int cod_prato;

    
    public Ementas() {};
    
    public Ementas(String dta_desativo, String ref, String pr ,int cod_ref,int cod_prato)
    {
    	this.dta_desativo = dta_desativo;
    	this.cod_ref = cod_ref;
    	this.cod_prato = cod_prato;
    	this.ref = ref;
    	this.pr = pr;
    	//this.descricao = descricao;
    }

	public String getDta_desativo() {
		return dta_desativo;
	}

	public void setDta_desativo(String dta_desativo) {
		this.dta_desativo = dta_desativo;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getPr() {
		return pr;
	}

	public void setPr(String pr) {
		this.pr = pr;
	}

	public int getCod_ref() {
		return cod_ref;
	}

	public void setCod_ref(int cod_ref) {
		this.cod_ref = cod_ref;
	}

	public int getCod_prato() {
		return cod_prato;
	}

	public void setCod_prato(int cod_prato) {
		this.cod_prato = cod_prato;
	}

	/*public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}*/
    
    


    
   
}
