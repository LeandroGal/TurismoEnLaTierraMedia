package tpTurismo;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class OfertadorTest {

	@Test
	public void promocionesTest() {   // INCOMPLETO

		Usuario usuarioFrodo = new Usuario("Frodo Bolson", "Aventura", 64, 7f);

		List<Atraccion> listaAtracciones = new ArrayList<Atraccion>();
		List<Promocion> listaPromociones = new ArrayList<Promocion>();

		try {
			listaPromociones = Archivo.leerArchivoPromociones(listaAtracciones);
		} catch (IOException e) {

			e.printStackTrace();
		}
		try {
			listaAtracciones = Archivo.leerArchivoAtracciones();
		} catch (IOException e) {

			e.printStackTrace();
		}

		List<Promocion> promocionesPreferidasUsuarioFrodoObtenidas = new ArrayList<Promocion>();
		List<Promocion> promocionesRestantesUsuarioFrodoObtenidas = new ArrayList<Promocion>();
		// List<Atraccion> atraccionesPreferidasUsuarioFrodoObtenidas = new
		// ArrayList<Atraccion>();
		// List<Atraccion> atraccionesRestantesUsuarioFrodoObtenidas = new
		// ArrayList<Atraccion>();

		List<Promocion> promocionesPreferidasUsuarioFrodoEsperadas = new ArrayList<Promocion>();
		List<Promocion> promocionesRestantesUsuarioFrodoEsperadas = new ArrayList<Promocion>();
		// List<Atraccion> atraccionesPreferidasUsuarioFrodoEsperadas = new
		// ArrayList<Atraccion>();
		// List<Atraccion> atraccionesRestantesUsuarioFrodoEsperadas = new
		// ArrayList<Atraccion>();

		// generarPromosPreferidasYRestantes
		for (Promocion promocion : listaPromociones) {
			if (usuarioFrodo.getTipoAtraccionPreferida().equals(promocion.getTipoAtraccion())) {

				promocionesPreferidasUsuarioFrodoObtenidas.add(promocion);
			} else {
				promocionesRestantesUsuarioFrodoObtenidas.add(promocion);
			}

		}

		Collections.sort(promocionesPreferidasUsuarioFrodoObtenidas, new PromocionComparator());
		Collections.sort(promocionesRestantesUsuarioFrodoObtenidas, new PromocionComparator());

		List<Atraccion> atraccionesIncluidas = new ArrayList<Atraccion>();
		List<Atraccion> atraccionesIncluidas2 = new ArrayList<Atraccion>();

		Atraccion atraccion1 = new Atraccion("Lothlorien", 21, 1.5f, 4, "Aventura");
		Atraccion atraccion2 = new Atraccion("Bosque Negro", 19, 3, 1, "Aventura");
		Atraccion atraccion3 = new Atraccion("Mordor", 18, 1, 4, "Aventura");
		Atraccion atraccion4 = new Atraccion("Moria", 22, 2.5f, 6, "Aventura");

		atraccionesIncluidas.add(atraccion1);
		atraccionesIncluidas.add(atraccion2);
		atraccionesIncluidas.add(atraccion3);

		atraccionesIncluidas2.add(atraccion4);
		atraccionesIncluidas2.add(atraccion3);

		PromocionAbsoluta promoAbsoluta = new PromocionAbsoluta("Pack Super Aventura", atraccionesIncluidas, 2, 34);
		PromocionPorcentual promoPorcentual = new PromocionPorcentual("Pack Aventura", atraccionesIncluidas2, 1, 25);

		promocionesPreferidasUsuarioFrodoEsperadas.add(promoAbsoluta);
		promocionesPreferidasUsuarioFrodoEsperadas.add(promoPorcentual);

		boolean obtenido = false;

		if (promocionesPreferidasUsuarioFrodoEsperadas.size() == promocionesPreferidasUsuarioFrodoObtenidas.size()) {
			
			
			
			for (int i = 0; i < promocionesPreferidasUsuarioFrodoEsperadas.size(); i++) {

				if (promocionesPreferidasUsuarioFrodoEsperadas.get(i)
						.calculoPrecioFinal() == promocionesPreferidasUsuarioFrodoObtenidas.get(i).calculoPrecioFinal()
						&& promocionesPreferidasUsuarioFrodoEsperadas.get(i)
								.calculoTiempoTotal() == promocionesPreferidasUsuarioFrodoObtenidas.get(i)
										.calculoTiempoTotal()) {
					
					obtenido=true;

				}else {
					obtenido = false;
				}
			}

		}

		Assert.assertTrue(obtenido);
	}

}