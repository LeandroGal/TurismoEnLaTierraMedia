package tpTurismo;

import java.util.ArrayList;
import java.util.List;

public class Itinerario {

	private List<Promocion> promocionesAceptadas;
	private List<Atraccion> atraccionesAceptadas;
	private int totalAPagar;
	private float tiempoTotal;

	public Itinerario() {

		this.promocionesAceptadas = new ArrayList<Promocion>();
		this.atraccionesAceptadas = new ArrayList<Atraccion>();
		this.totalAPagar = 0;
		this.tiempoTotal = 0;
	}

	public void agregarPromocion(Promocion promo) {
		this.promocionesAceptadas.add(promo);
	}

	public List<Promocion> getPromocionesAceptadas() {
		return promocionesAceptadas;
	}

	public List<Atraccion> getAtraccionesAceptadas() {
		return atraccionesAceptadas;
	}

	public int getTotalAPagar() {
		return totalAPagar;
	}

	public float getTiempoTotal() {
		return tiempoTotal;
	}

	public boolean puedeOfertarPromocion(Promocion promo) {

		List<Atraccion> atraccionesIncluidas = new ArrayList<Atraccion>();
		atraccionesIncluidas = promo.getAtraccionesIncluidas();

		for (Atraccion atraccionIncluida : atraccionesIncluidas) {
			if (this.atraccionesAceptadas.contains(atraccionIncluida)) {
				return false;
			}
		}

		List<Atraccion> atraccionesPromoItinerario = new ArrayList<Atraccion>();
		for (Promocion promocionAcept : this.promocionesAceptadas) {
			atraccionesPromoItinerario = promocionAcept.getAtraccionesIncluidas();

			for (Atraccion atraccionIncluida : atraccionesIncluidas) {
				if (atraccionesPromoItinerario.contains(atraccionIncluida)) {
					return false;
				}
			}

		}

		return true;
	}

}
