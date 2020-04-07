package respuestas;

public class Url 
{
	private String linkRelativo;
	private String datos;
	private String linkFisico;
	
	private Url() {}
	private Url(Url url) 
	{
		this.linkRelativo = url.linkRelativo;
		this.datos = url.datos;
		this.linkFisico = url.linkFisico;
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
	
	public static class Builder
	{
		private Url url;
		
		public Builder(String linkRelativo, String linkFisico)
		{
			this.url = new Url();
			this.url.setLinkRelativo(linkRelativo);
			this.url.setLinkFisico(linkFisico);
		}
		
		public Builder agregarDatos(String datos)
		{
			this.url.setDatos(datos);
			return this;
		}
		
		public Url build()
		{
			return new Url(url);
		}
	}
}


