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


		public String getCodf() {
			return codf;
		}

		public String getDta_feriado() {
			return dta_feriado;
		}


		public void setDta_feriado(String dta_feriado) {
			this.dta_feriado = dta_feriado;
		}


		public String getDesc_fe() {
			return desc_fe;
		}
	    

}
