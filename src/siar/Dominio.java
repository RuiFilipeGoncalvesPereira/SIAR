package siar;

public class Dominio {
	
	private int valor;
	private String dominio;
	private String  desc_dom;
	
    public Dominio() {};
    
    
    public Dominio(int valor, String dominio, String desc_dom)
    {
    	this.valor = valor;
    	this.dominio = dominio;
    	this.desc_dom = desc_dom;
    }


	public int getValor() {
		return valor;
	}


	public void setValor(int valor) {
		this.valor = valor;
	}


	public String getDominio() {
		return dominio;
	}


	public void setDominio(String dominio) {
		this.dominio = dominio;
	}


	public String getDesc_dom() {
		return desc_dom;
	}


	public void setDesc_dom(String desc_dom) {
		this.desc_dom = desc_dom;
	}

}
