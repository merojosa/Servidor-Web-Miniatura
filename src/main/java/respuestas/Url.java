package respuestas;

public class Url 
{
	private String linkRelativo;
	private String datos;
	private String linkFisico;
	
	public Url(String linkRelativo, String datos, String linkFisico) 
	{
		this.linkRelativo = linkRelativo;
		this.datos = datos;
		this.linkFisico = linkFisico;
	}

	public String getLinkRelativo() 
	{
		return linkRelativo;
	}

	public void setLinkRelativo(String linkRelativo) 
	{
		this.linkRelativo = linkRelativo;
	}

	public String getDatos() 
	{
		return datos;
	}

	public void setDatos(String datos) 
	{
		this.datos = datos;
	}

	public String getLinkFisico()
	{
		return linkFisico;
	}

	public void setLinkFisico(String linkFisico)
	{
		this.linkFisico = linkFisico;
	}
}
