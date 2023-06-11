package tpTurismo;

import java.util.List;

public class PromocionAbsoluta extends Promocion {

	private int precioAbsoluto;

	public PromocionAbsoluta(String nombrePromocion, List<Atraccion> atraccionesIncluidas, int tipoPromocion,
			int precio) {
		super(nombrePromocion, atraccionesIncluidas, tipoPromocion);

		this.precioAbsoluto = precio;
	}

	@Override
	protected int calculoPrecioFinal() {

		return this.precioAbsoluto;
	}

	@Override
	public String toString() {
		return super.toString() + "PromocionAbsoluta [precioAbsoluto = " + precioAbsoluto + "]";
	}

}
