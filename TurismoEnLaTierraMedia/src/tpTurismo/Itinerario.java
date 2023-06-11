package tpTurismo;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
		this.totalAPagar += promo.calculoPrecioFinal();
		this.tiempoTotal += promo.consultarTiempoTotal();

	}

	public void agregarAtraccion(Atraccion atraccion) {
		this.atraccionesAceptadas.add(atraccion);
		this.totalAPagar += atraccion.consultarCosto();
		this.tiempoTotal += atraccion.consultarDuracionEnHs();
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
		atraccionesIncluidas = promo.consultarAtraccionesIncluidas();

		for (Atraccion atraccionIncluida : atraccionesIncluidas) {
			if (this.atraccionesAceptadas.contains(atraccionIncluida)) {
				return false;
			}
		}

		if (promo instanceof PromocionAxB) {
			List<Atraccion> atraccionesGratisIncluidas = new ArrayList<Atraccion>();
			atraccionesGratisIncluidas = ((PromocionAxB) promo).consultarAtraccionesGratis();

			for (Atraccion atraccionGratis : atraccionesGratisIncluidas) {
				if (this.atraccionesAceptadas.contains(atraccionGratis)) {
					return false;
				}
			}

		}

		List<Atraccion> atraccionesPromoItinerario = new ArrayList<Atraccion>();
		for (Promocion promocionAcept : this.promocionesAceptadas) {
			atraccionesPromoItinerario = promocionAcept.consultarAtraccionesIncluidas();

			for (Atraccion atraccionIncluida : atraccionesIncluidas) {
				if (atraccionesPromoItinerario.contains(atraccionIncluida)) {
					return false;
				}
			}

		}

		return true;
	}

	public boolean puedeOfertarAtraccion(Atraccion atraccion) {
		if (this.atraccionesAceptadas.contains(atraccion)) {
			return false;
		}

		List<Atraccion> atraccionesEnPromo = new ArrayList<Atraccion>();
		for (Promocion promocionAceptada : this.promocionesAceptadas) {

			atraccionesEnPromo = promocionAceptada.consultarAtraccionesIncluidas();
			if (atraccionesEnPromo.contains(atraccion)) {
				return false;
			}
		}

		return true;
	}

	public void mostrarItinerario() {
		System.out.println();
		System.out.println("Itinerario final");
		System.out.println("------------------");
		System.out.println("Promociones: ");
		System.out.println("-------------");
		for (Promocion promo : this.promocionesAceptadas) {
			System.out.println("Nombre Promocion: " + promo.consultarNombrePromocion());
			System.out.println("Duracion: " + promo.calculoTiempoTotal());
			System.out.println("Atracciones incluidas: ");

			List<Atraccion> atraccionesIncluidas = new ArrayList<Atraccion>();
			atraccionesIncluidas = promo.consultarAtraccionesIncluidas();

			for (Atraccion atraccion : atraccionesIncluidas) {
				System.out.println("- " + atraccion.imprimeAtraccion());
			}

			if (promo instanceof PromocionAxB) {
				PromocionAxB promo2 = (PromocionAxB) promo;
				System.out.println(" -Atracciones Gratis: ");
				List<Atraccion> atraccionesGratis = new ArrayList<Atraccion>();
				atraccionesGratis = promo2.consultarAtraccionesGratis();

				for (Atraccion atraccionGratis : atraccionesGratis) {
					System.out.println("- " + atraccionGratis.imprimeAtraccion());
				}

			}

		}

		System.out.println("Atracciones: ");
		System.out.println("-------------");
		for (Atraccion atraccion : this.atraccionesAceptadas) {
			System.out.println("- " + atraccion.imprimeAtraccion());
		}
		System.out.println("Precio final a abonar: " + this.totalAPagar + " monedas de oro");
		System.out.println("Duracion total del recorrido: " + this.tiempoTotal + " hs");

	}

	public void grabarItinerario(Usuario usuario) {
		FileWriter file = null;
		PrintWriter printWriter = null;

		try {
			file = new FileWriter("archivos/out/Itinerario" + usuario.consultarNombre() + ".out");

			printWriter = new PrintWriter(file);

			printWriter.println("Itinerario final");
			printWriter.println("------------------");
			printWriter.println("Usuario: " + usuario.consultarNombre());

			printWriter.println("Promociones:");
			for (Promocion promo : this.promocionesAceptadas) {
				printWriter.println("Nombre Promocion: " + promo.consultarNombrePromocion());
				printWriter.println("Duracion: " + promo.calculoTiempoTotal());
				printWriter.println("Atracciones incluidas: ");

				List<Atraccion> atraccionesIncluidas = new ArrayList<Atraccion>();
				atraccionesIncluidas = promo.consultarAtraccionesIncluidas();

				for (Atraccion atraccion : atraccionesIncluidas) {
					printWriter.println("- " + atraccion.imprimeAtraccion());
				}

				if (promo instanceof PromocionAxB) {
					PromocionAxB promo2 = (PromocionAxB) promo;
					printWriter.println(" -Atracciones Gratis: ");
					List<Atraccion> atraccionesGratis = new ArrayList<Atraccion>();
					atraccionesGratis = promo2.consultarAtraccionesGratis();

					for (Atraccion atraccionGratis : atraccionesGratis) {
						printWriter.println("- " + atraccionGratis.imprimeAtraccion());
					}

				}

			}

			printWriter.println("Atracciones: ");
			printWriter.println("-------------");
			for (Atraccion atraccion : this.atraccionesAceptadas) {
				printWriter.println("- " + atraccion.imprimeAtraccion());
			}
			printWriter.println("Precio final a abonar: " + this.totalAPagar + " monedas de oro");
			printWriter.println("Duracion total del recorrido: " + this.tiempoTotal + " hs");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (file != null) {
				try {
					file.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

}
