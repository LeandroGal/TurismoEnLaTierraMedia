package tpTurismo;

import java.util.ArrayList;
import java.util.List;

public class PromocionAxB extends Promocion {

	protected List<Atraccion> atraccionesGratis = new ArrayList<Atraccion>();

	public PromocionAxB(String nombrePromocion, List<Atraccion> atraccionesIncluidas, int tipoPromocion,
			List<Atraccion> atraccionesGratis) {

		super(nombrePromocion, atraccionesIncluidas, tipoPromocion);
		this.atraccionesGratis.addAll(atraccionesGratis);

	}

	public List<Atraccion> consultarAtraccionesGratis() {
		return this.atraccionesGratis;
	}

	@Override
	protected int calculoPrecioFinal() {
		int precio = 0;
		for (Atraccion atraccion : this.atraccionesIncluidas) {
			precio += atraccion.consultarCosto();
		}

		return precio;
	}

	@Override
	protected float calculoTiempoTotal() {
		float tiempo = 0;
		for (Atraccion elem : this.atraccionesIncluidas) {
			tiempo += elem.consultarDuracionEnHs();
		}

		for (Atraccion elem2 : this.atraccionesGratis) {
			tiempo += elem2.consultarDuracionEnHs();
		}

		return tiempo;
	}

	@Override
	public String toString() {
		return super.toString() + "PromocionAxB [atraccionesGratis = " + atraccionesGratis + "]";
	}

	@Override
	public void imprimirAtracciones() {
		for (Atraccion atraccion : this.atraccionesIncluidas) {
			System.out.print(atraccion.consultarNombreAtraccion() + ", ");
		}
		System.out.println();
		System.out.println("Atracciones gratis: ");
		for (Atraccion atraccion : this.atraccionesGratis) {
			System.out.print(atraccion.consultarNombreAtraccion() + ", ");
		}

	}

}
