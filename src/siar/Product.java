package siar;


public class Product {
	private int nmec;
	private String n_uti;
	private String senha;
	private String dta_desativo;
	private String email;
    private byte[]imagem;
    
    public Product() {};
    
    
    public Product(int Nmec, String N_uti, String Senha,String Dta_desativo,String Email,byte[]Imagem)
    {
    	this.nmec = Nmec;
    	this.n_uti = N_uti;
    	this.senha = Senha;
    	this.dta_desativo = Dta_desativo;
    	this.email = Email;
    	this.imagem = Imagem;
    }
    
    public int getNmec()
    {
    	return nmec;
    }
    
    public void setNmec(int Nmec)
    {
       this.nmec = Nmec;
    }
    
    public String getN_uti()
    {
    	return n_uti;
    }
    
    public void setN_uti(String N_uti)
    {
       this.n_uti = N_uti;
    }
    
    public String getSenha()
    {
    	return senha;
    }
    
    public void setSenha(String Senha)
    {
       this.senha = Senha;
    }
    
    public String getDta_desativo()
    {
    	return dta_desativo;
    }
    
    public void setDta_desativo(String Dta_desativo)
    {
    	this.dta_desativo = Dta_desativo;
    }
    
    public String getEmail()
    {
    	return email;
    }
    
    public void setEmail(String Email)
    {
    	this.email = Email;
    }
     
    public byte[] getMyImage()
    {
    	return imagem;
    }
}
