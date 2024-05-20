package siar;

public class Parametro {
	private int cod_param;
	private String valor_param;
	private String desc_param;
	
    public Parametro() {};
    
    
    public Parametro(int cod_param, String valor_param, String desc_param)
    {
    	this.cod_param = cod_param;
    	this.valor_param = valor_param;
    	this.desc_param = desc_param;
    }


	public int getCod_param() {
		return cod_param;
	}

	public String getValor_param() {
		return valor_param;
	}


	public void setValor_param(String valor_param) {
		this.valor_param = valor_param;
	}


	public String getDesc_param() {
		return desc_param;
	}


	public void setDesc_param(String desc_param) {
		this.desc_param = desc_param;
	}
    
    


}
