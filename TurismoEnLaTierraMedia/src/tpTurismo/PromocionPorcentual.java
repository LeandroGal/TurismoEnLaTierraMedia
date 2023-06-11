package tpTurismo;

import java.util.List;

public class PromocionPorcentual extends Promocion {

	private float porcentaje;

	public PromocionPorcentual(String nombrePromocion, List<Atraccion> atraccionesIncluidas, int tipoPromocion,
			int porcentaje) {
		super(nombrePromocion, atraccionesIncluidas, tipoPromocion);

		this.porcentaje = porcentaje;
	}

	@Override
	protected int calculoPrecioFinal() {

		float precio = 0;

		for (Atraccion atraccion : this.atraccionesIncluidas) {
			precio += atraccion.consultarCosto();
		}

		return (int) (precio * (1 - (this.porcentaje / 100)));
	}

	@Override
	public String toString() {
		return super.toString() + "PromocionPorcentual [porcentaje = " + porcentaje + "]";
	}

}
