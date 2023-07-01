package tpTurismo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Ofertador {

	private Scanner scanner = null;
	private List<Usuario> listaUsuarios;
	private List<Atraccion> listaAtracciones;
	private List<Promocion> listaPromociones;
	private List<Promocion> promocionesPreferidas;
	private List<Promocion> promocionesRestantes;
	private List<Atraccion> atraccionesPreferidas;
	private List<Atraccion> atraccionesRestantes;

	public Ofertador() {

		this.listaUsuarios = new ArrayList<Usuario>();
		try {
			listaUsuarios = Archivo.leerArchivoUsuarios();
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.listaAtracciones = new ArrayList<Atraccion>();
		try {
			listaAtracciones = Archivo.leerArchivoAtracciones();
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.listaPromociones = new ArrayList<Promocion>();
		try {
			listaPromociones = Archivo.leerArchivoPromociones(listaAtracciones);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void ofertarAtracciones(Usuario usuario, List<Atraccion> listaAtracciones, List<Promocion> listaPromociones,
			List<Atraccion> atraccionesAOfertar, Itinerario itinerario) {

		for (Atraccion atraccion : atraccionesAOfertar) {

			int indiceAtraccionAOfertar = listaAtracciones.indexOf(atraccion);

			if (listaAtracciones.get(indiceAtraccionAOfertar).getCupo() > 0
					&& usuario.consultarPresupuesto() >= atraccion.getCosto()
					&& usuario.consultarTiempo() >= atraccion.getDuracion()
					&& itinerario.puedeOfertarAtraccion(atraccion)) {

				System.out.println("Atraccion: " + atraccion.getNombreAtraccion());
				System.out.println("-Duracion: " + atraccion.getDuracion() + "hs");
				System.out.println("-Precio: " + atraccion.getCosto() + " monedas de oro");

				System.out.println("¿Acepta la atraccion? (S/N)");

				String opcion = scanner.next();

				while (!opcion.equalsIgnoreCase("S") && !opcion.equalsIgnoreCase("N")) {
					System.out.println("Opción inválida. Por favor, ingresa 'S' o 'N'.");
					System.out.print("¿Acepta la atraccion? (S/N)");
					opcion = scanner.next();
				}

				if (opcion.equalsIgnoreCase("S")) { // Si acepto agrego al itinerario, actualizo cupo de la
													// atraccion

					System.out.println("Atraccion Aceptada");

					itinerario.agregarAtraccion(atraccion);

					usuario.actualizarPresupuestoYTiempo(atraccion.getCosto(), atraccion.getDuracion());

					// Actualizo cupo de la lista original de atracciones
					listaAtracciones.get(indiceAtraccionAOfertar).actualizarCupo();

					// Actualizo cupo de las promociones que contengan la atraccion
					for (Promocion promo : listaPromociones) {
						if (promo.atraccionesIncluidas.contains(atraccion)) {

							int indiceAtraccionContenida = promo.atraccionesIncluidas.indexOf(atraccion);
							promo.atraccionesIncluidas.get(indiceAtraccionContenida).actualizarCupo();
						}
					}

				} else {
					System.out.println("Atraccion rechazada");
				}

			} else {
				continue;
			}

			System.out.println("---------------------------------------------------------------------");
		}
	}

	private void ofertarPromociones(Usuario usuario, List<Atraccion> listaAtracciones, List<Promocion> listaPromociones,
			List<Promocion> promocionesAOfertar, Itinerario itinerario) {

		for (Promocion promo : promocionesAOfertar) {

			int indicePromoAOfertar = listaPromociones.indexOf(promo);

			if (listaPromociones.get(indicePromoAOfertar).tieneCupo()
					&& usuario.consultarPresupuesto() >= promo.calculoPrecioFinal()
					&& usuario.consultarTiempo() >= promo.calculoTiempoTotal()
					&& itinerario.puedeOfertarPromocion(promo)) {

				System.out.println("Promocion: " + promo.nombrePromocion);

				System.out.println("-Atracciones incluidas: ");
				promo.imprimirAtracciones();
				System.out.println();
				System.out.println("-Duracion: " + promo.consultarTiempoTotal() + "hs");
				System.out.println("Precio original: " + promo.consultarPrecioInicial() + " monedas de oro");
				System.out.println("Precio final: " + promo.consultarPrecioFinal() + " monedas de oro");

				System.out.println("¿Acepta la promocion? (S/N)");

				String opcion = scanner.next();

				while (!opcion.equalsIgnoreCase("S") && !opcion.equalsIgnoreCase("N")) {
					System.out.println("Opción inválida. Por favor, ingresa 'S' o 'N'.");
					System.out.print("¿Acepta la promocion? (S/N)");
					opcion = scanner.next();
				}

				if (opcion.equalsIgnoreCase("S")) { // si acepto agrego al itinerario, actualizo cupo de la promo
					System.out.println("Promocion Aceptada");

					itinerario.agregarPromocion(promo);
					usuario.actualizarPresupuestoYTiempo(promo.consultarPrecioFinal(), promo.consultarTiempoTotal());
					// actualizo cupo de promo y atracciones
					listaPromociones.get(indicePromoAOfertar).actualizarCupo();

					List<Atraccion> atraccionesIncluidas = new ArrayList<Atraccion>();
					atraccionesIncluidas = listaPromociones.get(indicePromoAOfertar).consultarAtraccionesIncluidas();

					// actualiza cupo de las atracciones que se anoto
					for (Atraccion atraccion : atraccionesIncluidas) {
						int indice = listaAtracciones.indexOf(atraccion);
						listaAtracciones.get(indice).actualizarCupo();
					}

				} else {
					System.out.println("Promocion rechazada");
				}

			} else {
				continue;
			}
			System.out.println("---------------------------------------------------------------------");

		}
	}

	private void generarPromosPreferidasYRestantes(Usuario usuario, List<Promocion> listaPromociones,
			List<Promocion> promocionesPreferidas, List<Promocion> promocionesRestantes) {
		for (Promocion promocion : listaPromociones) {
			if (usuario.consultarAtraccionPreferida().equals(promocion.consultarTipoAtraccion())) {

				promocionesPreferidas.add(promocion);
			} else {
				promocionesRestantes.add(promocion);
			}

		}
		Collections.sort(promocionesPreferidas, new PromocionComparator());
		Collections.sort(promocionesRestantes, new PromocionComparator());
	}

	private void generarAtraccionesPreferidasYRestantes(Usuario usuario, List<Atraccion> listaAtracciones,
			List<Atraccion> atraccionesPreferidas, List<Atraccion> atraccionesRestantes) {
		for (Atraccion atraccion : listaAtracciones) {
			if (usuario.consultarAtraccionPreferida().equals(atraccion.getTipoAtraccion())) {
				atraccionesPreferidas.add(atraccion);
			} else {
				atraccionesRestantes.add(atraccion);
			}
		}
		Collections.sort(atraccionesPreferidas, new AtraccionComparator());
		Collections.sort(atraccionesRestantes, new AtraccionComparator());
	}

	public void recorrerUsuariosYOfertar() {

		scanner = new Scanner(System.in);

		for (Usuario usuario : this.listaUsuarios) {
			System.out.println();

			atraccionesPreferidas = new ArrayList<Atraccion>();
			atraccionesRestantes = new ArrayList<Atraccion>();
			generarAtraccionesPreferidasYRestantes(usuario, this.listaAtracciones, atraccionesPreferidas,
					atraccionesRestantes);

			promocionesPreferidas = new ArrayList<Promocion>();
			promocionesRestantes = new ArrayList<Promocion>();
			generarPromosPreferidasYRestantes(usuario, this.listaPromociones, promocionesPreferidas,
					promocionesRestantes);

			Itinerario itinerario = new Itinerario();

			System.out.println("---------------------------------------------------------------------");
			System.out.println();
			System.out.println("¡Bienvenido/a a la Tierra Media!");
			System.out.println();
			System.out.println("---------------------------------------------------------------------");

			System.out.println("Nombre de Usuario: " + usuario.consultarNombre());
			System.out.println();
			System.out.println("---------------------------------------------------------------------");

			ofertarPromociones(usuario, this.listaAtracciones, this.listaPromociones, promocionesPreferidas,
					itinerario);

			ofertarAtracciones(usuario, this.listaAtracciones, this.listaPromociones, atraccionesPreferidas,
					itinerario);

			ofertarPromociones(usuario, this.listaAtracciones, this.listaPromociones, promocionesRestantes, itinerario);

			ofertarAtracciones(usuario, this.listaAtracciones, this.listaPromociones, atraccionesRestantes, itinerario);

			itinerario.mostrarItinerario();

			itinerario.grabarItinerario(usuario);

		}
		scanner.close();
	}

}