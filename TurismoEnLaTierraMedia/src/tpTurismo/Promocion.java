package tpTurismo;

import java.util.List;

public abstract class Promocion {

	protected String nombrePromocion;
	protected List<Atraccion> atraccionesIncluidas;
	protected int tipoPromocion;
	private int precioInicial;

	public Promocion(String nombrePromocion, List<Atraccion> atraccionesIncluidas, int tipoPromocion) {

		this.nombrePromocion = nombrePromocion;
		this.atraccionesIncluidas = atraccionesIncluidas;
		this.tipoPromocion = tipoPromocion;
		this.precioInicial = calculoPrecioInicial();
	}
	
	public String getNombrePromocion() {
		return this.nombrePromocion;
	}
	
	public List<Atraccion> getAtraccionesIncluidas() {
		return atraccionesIncluidas;
	}
	
	public void imprimirAtracciones() {
		for (Atraccion atraccion : this.atraccionesIncluidas) {
			System.out.print(atraccion.consultarNombreAtraccion() + ", ");
		}

	}

	public String consultarTipoAtraccion() {
		return this.getAtraccionesIncluidas().get(0).consultarTipoAtraccion();
	}
	
	private int calculoPrecioInicial() {
		int precio = 0;
		for (Atraccion elem : this.atraccionesIncluidas) {
			precio += elem.consultarCosto();
		}
		return precio;
	}

	protected float calculoTiempoTotal() {
		float tiempo = 0;
		for (Atraccion elem : this.atraccionesIncluidas) {
			tiempo += elem.consultarDuracionEnHs();
		}
		return tiempo;
	}

	protected abstract int calculoPrecioFinal();

	
	public boolean tieneCupo() {
		for (Atraccion atraccion : this.atraccionesIncluidas) {
			if (atraccion.consultarCupo() == 0) {
				return false;
			}
		}

		if (this instanceof PromocionAxB) {
			for (Atraccion atraccion : ((PromocionAxB) this).atraccionesGratis) {
				if (atraccion.consultarCupo() == 0) {
					return false;
				}
			}
		}

		return true;
	}

	public boolean actualizarCupo() {
		for (Atraccion atraccion : this.atraccionesIncluidas) {
			if (atraccion.consultarCupo() == 0) {
				return false;
			}
			atraccion.actualizarCupo();
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "Promocion [nombrePromocion=" + nombrePromocion + ", tipoAtraccion=" + tipoPromocion + ", precioInicial="
				+ precioInicial + ", precioFinal=" + calculoPrecioFinal() + " Tiempo total= " + calculoTiempoTotal()
				+ "]";
	}

}
