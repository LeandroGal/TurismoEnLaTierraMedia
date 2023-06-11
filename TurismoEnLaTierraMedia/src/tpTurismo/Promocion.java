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

	@Override
	public String toString() {
		return "Promocion [nombrePromocion = " + nombrePromocion + ", tipoAtraccion = " + tipoPromocion + ", precioInicial = "
				+ precioInicial + ", precioFinal = " + calculoPrecioFinal() + " Tiempo total = " + calculoTiempoTotal()
				+ "]";
	}

	public float consultarTiempoTotal() {
		return this.calculoTiempoTotal();
	}

	public int consultarPrecioFinal() {
		return this.calculoPrecioFinal();
	}
	
	public List<Atraccion> consultarAtraccionesIncluidas() {
		return atraccionesIncluidas;
	}
	
	public String consultarNombrePromocion() {
		return this.nombrePromocion;
	}
	
	public int consultarPrecioInicial() {
		return this.precioInicial;
	}
	

	public String consultarTipoAtraccion() {
		return this.consultarAtraccionesIncluidas().get(0).consultarTipoAtraccion();
	}

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

	public void imprimirAtracciones() {
		for (Atraccion atraccion : this.atraccionesIncluidas) {
			System.out.print(atraccion.consultarNombreAtraccion() + ", ");
		}

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
	
}
