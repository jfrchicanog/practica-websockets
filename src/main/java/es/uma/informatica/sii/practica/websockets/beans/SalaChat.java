package es.uma.informatica.sii.practica.websockets.beans;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

@Named
@ViewScoped
public class SalaChat implements Serializable {
	private static final long serialVersionUID = 1L;
	private String nombre;
	
	@Inject
	private UriInfo uriInfo;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void salirChat() {
		nombre=null;
	}
	
	public String getWebSocketUrl() {
        UriBuilder uriBuilder = uriInfo.getBaseUriBuilder().path("chat").scheme("ws");
		return uriBuilder.build().toString();
	}

}
