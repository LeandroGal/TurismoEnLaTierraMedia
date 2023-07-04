package tpTurismo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Ofertador {

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

	private void ofertarAtracciones(Usuario usuario, List<Atraccion> atraccionesAOfertar, Itinerario itinerario) {

		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);

		for (Atraccion atraccion : atraccionesAOfertar) {

			int indiceAtraccionAOfertar = this.listaAtracciones.indexOf(atraccion);

			if (this.listaAtracciones.get(indiceAtraccionAOfertar).getCupo() > 0
					&& usuario.getPresupuesto() >= atraccion.getCosto()
					&& usuario.getTiempoDisponible() >= atraccion.getDuracion()
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
					for (Promocion promo : this.listaPromociones) {
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

	private void ofertarPromociones(Usuario usuario, List<Promocion> promocionesAOfertar, Itinerario itinerario) {

		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);

		for (Promocion promo : promocionesAOfertar) {

			int indicePromoAOfertar = this.listaPromociones.indexOf(promo);

			if (this.listaPromociones.get(indicePromoAOfertar).tieneCupo()
					&& usuario.getPresupuesto() >= promo.calculoPrecioFinal()
					&& usuario.getTiempoDisponible() >= promo.calculoTiempoTotal()
					&& itinerario.puedeOfertarPromocion(promo)) {

				System.out.println("Promocion: " + promo.nombrePromocion);

				System.out.println("-Atracciones incluidas: ");
				promo.imprimirAtracciones();
				System.out.println();
				System.out.println("-Duracion: " + promo.getTiempoTotal() + "hs");
				System.out.println("Precio original: " + promo.getPrecioInicial() + " monedas de oro");
				System.out.println("Precio final: " + promo.getPrecioFinal() + " monedas de oro");

				System.out.println("¿Acepta la promocion? (S/N)");

				String opcion = scanner.next();

				while (!opcion.equalsIgnoreCase("S") && !opcion.equalsIgnoreCase("N")) {
					System.out.println("Opción inválida. Por favor, ingresa 'S' o 'N'.");
					System.out.print("¿Acepta la atraccion? (S/N)");
					opcion = scanner.next();
				}

				if (opcion.equalsIgnoreCase("S")) { // si acepto agrego al itinerario, actualizo cupo de la promo
					System.out.println("Promocion Aceptada");

					itinerario.agregarPromocion(promo);
					usuario.actualizarPresupuestoYTiempo(promo.getPrecioFinal(), promo.getTiempoTotal());
					// actualizo cupo de promo y atracciones
					this.listaPromociones.get(indicePromoAOfertar).actualizarCupo();

					List<Atraccion> atraccionesIncluidas = new ArrayList<Atraccion>();
					atraccionesIncluidas = this.listaPromociones.get(indicePromoAOfertar).getAtraccionesIncluidas();

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

	private void generarPromosPreferidasYRestantes(Usuario usuario) {
		for (Promocion promocion : this.listaPromociones) {
			if (usuario.getTipoAtraccionPreferida().equals(promocion.getTipoAtraccion())) {

				this.promocionesPreferidas.add(promocion);
			} else {
				this.promocionesRestantes.add(promocion);
			}

		}
		Collections.sort(promocionesPreferidas, new PromocionComparator());
		Collections.sort(promocionesRestantes, new PromocionComparator());
	}

	private void generarAtraccionesPreferidasYRestantes(Usuario usuario) {
		for (Atraccion atraccion : this.listaAtracciones) {
			if (usuario.getTipoAtraccionPreferida().equals(atraccion.getTipoAtraccion())) {
				this.atraccionesPreferidas.add(atraccion);
			} else {
				this.atraccionesRestantes.add(atraccion);
			}
		}
		Collections.sort(atraccionesPreferidas, new AtraccionComparator());
		Collections.sort(atraccionesRestantes, new AtraccionComparator());
	}

	public void recorrerUsuariosYOfertar() {

		Scanner scanner = new Scanner(System.in);

		this.atraccionesPreferidas = new ArrayList<Atraccion>();
		this.atraccionesRestantes = new ArrayList<Atraccion>();

		this.promocionesPreferidas = new ArrayList<Promocion>();
		this.promocionesRestantes = new ArrayList<Promocion>();

		for (Usuario usuario : this.listaUsuarios) {
			System.out.println();

			generarAtraccionesPreferidasYRestantes(usuario);

			generarPromosPreferidasYRestantes(usuario);

			Itinerario itinerario = new Itinerario();

			System.out.println("---------------------------------------------------------------------");
			System.out.println();
			System.out.println("¡Bienvenido/a a la Tierra Media!");
			System.out.println();
			System.out.println("---------------------------------------------------------------------");

			System.out.println("Nombre de Usuario: " + usuario.getNombreApellido());
			System.out.println();
			System.out.println("---------------------------------------------------------------------");

			ofertarPromociones(usuario, this.promocionesPreferidas, itinerario);

			ofertarAtracciones(usuario, this.atraccionesPreferidas, itinerario);

			ofertarPromociones(usuario, this.promocionesRestantes, itinerario);

			ofertarAtracciones(usuario, this.atraccionesRestantes, itinerario);

			itinerario.mostrarItinerario();

			itinerario.grabarItinerario(usuario);

		}
		scanner.close();
	}

}