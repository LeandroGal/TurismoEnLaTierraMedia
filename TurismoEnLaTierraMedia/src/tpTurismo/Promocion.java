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
			precio += elem.getCosto();
		}
		return precio;
	}

	protected float calculoTiempoTotal() {
		float tiempo = 0;
		for (Atraccion elem : this.atraccionesIncluidas) {
			tiempo += elem.getDuracion();
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

	public float getTiempoTotal() {
		return this.calculoTiempoTotal();
	}

	public int getPrecioFinal() {
		return this.calculoPrecioFinal();
	}
	
	public List<Atraccion> getAtraccionesIncluidas() {
		return atraccionesIncluidas;
	}
	
	public String getNombrePromocion() {
		return this.nombrePromocion;
	}
	
	public int getPrecioInicial() {
		return this.precioInicial;
	}
	

	public String getTipoAtraccion() {
		return this.getAtraccionesIncluidas().get(0).getTipoAtraccion();
	}

	public boolean tieneCupo() {
		for (Atraccion atraccion : this.atraccionesIncluidas) {
			if (atraccion.getCupo() == 0) {
				return false;
			}
		}

		if (this instanceof PromocionAxB) {
			for (Atraccion atraccion : ((PromocionAxB) this).atraccionesGratis) {
				if (atraccion.getCupo() == 0) {
					return false;
				}
			}
		}

		return true;
	}

	public void imprimirAtracciones() {
		for (Atraccion atraccion : this.atraccionesIncluidas) {
			System.out.print(atraccion.getNombreAtraccion() + ", ");
		}

	}

	public boolean actualizarCupo() {
		for (Atraccion atraccion : this.atraccionesIncluidas) {
			if (atraccion.getCupo() == 0) {
				return false;
			}
			atraccion.actualizarCupo();
		}
		return true;
	}
	
}
