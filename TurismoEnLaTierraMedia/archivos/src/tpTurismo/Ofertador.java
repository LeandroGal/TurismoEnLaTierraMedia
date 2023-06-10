package tpTurismo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Ofertador {

//	private float precioFinal;
//	private float precioInicial;
//	private float tiempo;
//	private List<Atraccion> atracciones;

	public static void main(String[] args) throws IOException {

		List<Usuario> listaUsuarios = new ArrayList<Usuario>();
		List<Atraccion> listaAtracciones = new ArrayList<Atraccion>();
		List<Promocion> listaPromociones = new ArrayList<Promocion>();

		try {
			listaUsuarios = Archivo.leerArchivoUsuarios();
			listaAtracciones = Archivo.leerArchivoAtracciones();
			listaPromociones = Archivo.leerArchivoPromociones(listaAtracciones);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Scanner scanner = new Scanner(System.in);

		for (Usuario usuario : listaUsuarios) {
			System.out.println(usuario);
			// String preferida = usuario.consultarAtraccionPreferida();

			List<Atraccion> atraccionesPreferidas = new ArrayList<Atraccion>();
			List<Atraccion> atraccionesRestantes = new ArrayList<Atraccion>();

			for (Atraccion atraccion : listaAtracciones) {
				if (usuario.consultarAtraccionPreferida().equals(atraccion.consultarTipoAtraccion())) {
					atraccionesPreferidas.add(atraccion);
				} else {
					atraccionesRestantes.add(atraccion);
				}
			}
			Collections.sort(atraccionesPreferidas, new AtraccionComparator());
			Collections.sort(atraccionesRestantes, new AtraccionComparator());

			// PAQUETES
			List<Promocion> promocionesPreferidas = new ArrayList<Promocion>();
			List<Promocion> promocionesRestantes = new ArrayList<Promocion>();

			for (Promocion promocion : listaPromociones) {
				if (usuario.consultarAtraccionPreferida().equals(promocion.consultarTipoAtraccion())) {

					promocionesPreferidas.add(promocion);
				} else {
					promocionesRestantes.add(promocion);
				}

			}
			Collections.sort(promocionesPreferidas, new PromocionComparator());
			Collections.sort(promocionesRestantes, new PromocionComparator());

			Itinerario itinerario = new Itinerario();

			// mientras recorro y acepta: resto presupuesto, tiempo,
			// cupo atraccion y en caso de ser promo restar de todas
			// por promo debo consultar cupo de todas atracciones incluidas
			// traer menor cupo de las atracciones incluidas

			// resto de las listas que traje de los archivos.

			System.out.println("Bienvenido/a a la Tierra Media");
			System.out.println("-----------------------------------------------------------------");

			System.out.println("Nombre de Usuario: " + usuario.consultarNombre());
			System.out.println();

			// for por promo preferida
			for (Promocion promo : promocionesPreferidas) {

				int indicePromoAOfertar = listaPromociones.indexOf(promo);
				// listaPromociones.get(indicePromoAOfertar);

				if (listaPromociones.get(indicePromoAOfertar).tieneCupo()
						&& usuario.consultarPresupuesto() >= promo.calculoPrecioFinal()
						&& usuario.consultarTiempo() >= promo.calculoTiempoTotal()
						&& itinerario.puedeOfertarPromocion(promo)) {

					System.out.println("Promocion: ");

					System.out.println("-Atracciones incluidas: ");
					promo.imprimirAtracciones();
					System.out.println();
					System.out.println("-Duracion: " + promo.consultarTiempoTotal() + " Hs");
					System.out.println("Precio original: " + promo.consultarPrecioInicial());
					System.out.println("Precio final: " + promo.consultarPrecioFinal());

					System.out.println("Acepta la promocion? (S/N )");

					String opcion = scanner.next();

					while (!opcion.equalsIgnoreCase("S") && !opcion.equalsIgnoreCase("N")) {
						System.out.println("Opción inválida. Por favor, ingresa 'S' o 'N'.");
						System.out.print("¿Acepta sugerencia? (S/N): ");
						opcion = scanner.next();
					}

					if (opcion.equalsIgnoreCase("S")) { // si acepto agrego al itinerario, actualizo cupo de la promo
						System.out.println("Promocion Aceptada");

						itinerario.agregarPromocion(promo);
						usuario.actualizarPresupuestoYTiempo(promo.consultarPrecioFinal(),
								promo.consultarTiempoTotal());
						// actualizo cupo de promo y atracciones
						listaPromociones.get(indicePromoAOfertar).actualizarCupo();

						List<Atraccion> atraccionesIncluidas = new ArrayList<Atraccion>();
						atraccionesIncluidas = listaPromociones.get(indicePromoAOfertar).getAtraccionesIncluidas();

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
				System.out.println("-----------------------------------------------------------------");

			}

			// for por atraccion preferida
			for (Atraccion atraccion : atraccionesPreferidas) {

				int indiceAtraccionAOfertar = listaAtracciones.indexOf(atraccion);

				if (listaAtracciones.get(indiceAtraccionAOfertar).consultarCupo() > 0
						&& usuario.consultarPresupuesto() >= atraccion.consultarCosto()
						&& usuario.consultarTiempo() >= atraccion.consultarDuracionEnHs()
						&& itinerario.puedeOfertarAtraccion(atraccion)) {

					System.out.println("Atraccion");
					System.out.println("Nombre: " + atraccion.consultarNombreAtraccion());
					System.out.println("-Duracion: " + atraccion.consultarDuracionEnHs());
					System.out.println("-Precio: " + atraccion.consultarCosto());

					System.out.println("Acepta la atraccion? (S/N )");

					String opcion = scanner.next();

					while (!opcion.equalsIgnoreCase("S") && !opcion.equalsIgnoreCase("N")) {
						System.out.println("Opción inválida. Por favor, ingresa 'S' o 'N'.");
						System.out.print("¿Acepta sugerencia? (S/N): ");
						opcion = scanner.next();
					}

					if (opcion.equalsIgnoreCase("S")) { // si acepto agrego al itinerario, actualizo cupo de la
														// atraccion
						System.out.println("Atraccion Aceptada");

						itinerario.agregarAtraccion(atraccion);

						usuario.actualizarPresupuestoYTiempo(atraccion.consultarCosto(),
								atraccion.consultarDuracionEnHs());

						// actualizo cupo de la lista original de atracciones
						listaAtracciones.get(indiceAtraccionAOfertar).actualizarCupo();

						// actualizo cupo de las promociones que contengan la atraccion
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

				System.out.println("-----------------------------------------------------------------");
			}

			// for promo restante

			for (Promocion promo : promocionesRestantes) {

				int indicePromoAOfertar = listaPromociones.indexOf(promo);
				// listaPromociones.get(indicePromoAOfertar);

				if (listaPromociones.get(indicePromoAOfertar).tieneCupo()
						&& usuario.consultarPresupuesto() >= promo.calculoPrecioFinal()
						&& usuario.consultarTiempo() >= promo.calculoTiempoTotal()
						&& itinerario.puedeOfertarPromocion(promo)) {

					System.out.println("Promocion: ");

					System.out.println("-Atracciones incluidas: ");
					promo.imprimirAtracciones();
					System.out.println();
					System.out.println("-Duracion: " + promo.consultarTiempoTotal() + " Hs");
					System.out.println("Precio original: " + promo.consultarPrecioInicial());
					System.out.println("Precio final: " + promo.consultarPrecioFinal());

					System.out.println("Acepta la promocion? (S/N )");

					String opcion = scanner.next();

					while (!opcion.equalsIgnoreCase("S") && !opcion.equalsIgnoreCase("N")) {
						System.out.println("Opción inválida. Por favor, ingresa 'S' o 'N'.");
						System.out.print("¿Acepta sugerencia? (S/N): ");
						opcion = scanner.next();
					}

					if (opcion.equalsIgnoreCase("S")) { // si acepto agrego al itinerario, actualizo cupo de la promo
						System.out.println("Promocion Aceptada");

						itinerario.agregarPromocion(promo);
						usuario.actualizarPresupuestoYTiempo(promo.consultarPrecioFinal(),
								promo.consultarTiempoTotal());
						// actualizo cupo de promo y atracciones
						listaPromociones.get(indicePromoAOfertar).actualizarCupo();

						List<Atraccion> atraccionesIncluidas = new ArrayList<Atraccion>();
						atraccionesIncluidas = listaPromociones.get(indicePromoAOfertar).getAtraccionesIncluidas();

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
				System.out.println("-----------------------------------------------------------------");

			}

			// for promo atraccion restante

			for (Atraccion atraccion : atraccionesRestantes) {

				int indiceAtraccionAOfertar = listaAtracciones.indexOf(atraccion);

				if (listaAtracciones.get(indiceAtraccionAOfertar).consultarCupo() > 0
						&& usuario.consultarPresupuesto() >= atraccion.consultarCosto()
						&& usuario.consultarTiempo() >= atraccion.consultarDuracionEnHs()
						&& itinerario.puedeOfertarAtraccion(atraccion)) {

					System.out.println("Atraccion");
					System.out.println("Nombre: " + atraccion.consultarNombreAtraccion());
					System.out.println("-Duracion: " + atraccion.consultarDuracionEnHs());
					System.out.println("-Precio: " + atraccion.consultarCosto());

					System.out.println("Acepta la atraccion? (S/N )");

					String opcion = scanner.next();

					while (!opcion.equalsIgnoreCase("S") && !opcion.equalsIgnoreCase("N")) {
						System.out.println("Opción inválida. Por favor, ingresa 'S' o 'N'.");
						System.out.print("¿Acepta sugerencia? (S/N): ");
						opcion = scanner.next();
					}

					if (opcion.equalsIgnoreCase("S")) { // si acepto agrego al itinerario, actualizo cupo de la
														// atraccion
						System.out.println("Atraccion Aceptada");

						itinerario.agregarAtraccion(atraccion);

						usuario.actualizarPresupuestoYTiempo(atraccion.consultarCosto(),
								atraccion.consultarDuracionEnHs());

						// actualizo cupo de la lista original de atracciones
						listaAtracciones.get(indiceAtraccionAOfertar).actualizarCupo();

						// actualizo cupo de las promociones que contengan la atraccion
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

				System.out.println("----------------------------------------------------------------");
			}

			// mostrar itinerario
			itinerario.mostrarItinerario();
			itinerario.grabarItinerario(usuario);

			// break; // corta en el primero
		}
		scanner.close();
	}

}
