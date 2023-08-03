package siar;

public class Feriado 
{
		private String codf;
		private String dta_feriado;
		private String desc_fe;
	    
	    public Feriado() {};
	    
	    
	    public Feriado(String Codf, String Dta_feriado, String Desc_fe)
	    {
	    	this.codf = Codf;
	    	this.dta_feriado = Dta_feriado;
	    	this.desc_fe = Desc_fe;
	    }
	    
	    public String getCodf()
	    {
	    	return codf;
	    }
	    
	    public void setNmec(String Codf)
	    {
	       this.codf = Codf;
	    }
	    
	    public String getDta_feriado()
	    {
	    	return dta_feriado;
	    }
	    
	    public void setN_uti(String Dta_feriado)
	    {
	       this.dta_feriado = Dta_feriado;
	    }
	    
	    public String getDesc_fe()
	    {
	    	return desc_fe;
	    }
	    
	    public void setSenha(String Desc_fe)
	    {
	       this.desc_fe = Desc_fe;
	    }
}
