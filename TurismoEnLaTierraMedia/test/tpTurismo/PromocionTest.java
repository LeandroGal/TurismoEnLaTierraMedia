package tpTurismo;

import org.junit.Assert;
import org.junit.Test;
import java.util.List;

import java.util.ArrayList;

public class PromocionTest {

	@Test
	public void ConCupoPromocionAbsoluta() {

		List<Atraccion> atraccionesIncluidas = new ArrayList<Atraccion>();

		Atraccion atraccion1 = new Atraccion("Lothlorien", 21, 1.5f, 4, "Aventura");
		Atraccion atraccion2 = new Atraccion("Bosque Negro", 19, 3, 1, "Aventura");
		Atraccion atraccion3 = new Atraccion("Mordor", 18, 1, 4, "Aventura");

		atraccionesIncluidas.add(atraccion1);
		atraccionesIncluidas.add(atraccion2);
		atraccionesIncluidas.add(atraccion3);

		PromocionAbsoluta promoAbsoluta = new PromocionAbsoluta("Pack Super Aventura", atraccionesIncluidas, 2, 34);

		boolean esperado = true;
		boolean obtenido = promoAbsoluta.tieneCupo();

		Assert.assertEquals(esperado, obtenido);

	}

	@Test
	public void ActualizaCupoPromocionAbsoluta() {

		List<Atraccion> atraccionesIncluidas = new ArrayList<Atraccion>();

		Atraccion atraccion1 = new Atraccion("Lothlorien", 21, 1.5f, 4, "Aventura");
		Atraccion atraccion2 = new Atraccion("Bosque Negro", 19, 3, 1, "Aventura");
		Atraccion atraccion3 = new Atraccion("Mordor", 18, 1, 4, "Aventura");

		atraccionesIncluidas.add(atraccion1);
		atraccionesIncluidas.add(atraccion2);
		atraccionesIncluidas.add(atraccion3);

		PromocionAbsoluta promoAbsoluta = new PromocionAbsoluta("Pack Super Aventura", atraccionesIncluidas, 2, 34);

		boolean obtenido = promoAbsoluta.actualizarCupo();
		boolean esperado = true;

		Assert.assertEquals(esperado, obtenido);

	}
	
	@Test
	public void SinCupoPromocionAbsoluta() {

		List<Atraccion> atraccionesIncluidas = new ArrayList<Atraccion>();

		Atraccion atraccion1 = new Atraccion("Lothlorien", 21, 1.5f, 4, "Aventura");
		Atraccion atraccion2 = new Atraccion("Bosque Negro", 19, 3, 1, "Aventura");
		Atraccion atraccion3 = new Atraccion("Mordor", 18, 1, 4, "Aventura");

		atraccionesIncluidas.add(atraccion1);
		atraccionesIncluidas.add(atraccion2);
		atraccionesIncluidas.add(atraccion3);

		PromocionAbsoluta promoAbsoluta = new PromocionAbsoluta("Pack Super Aventura", atraccionesIncluidas, 2, 34);

		promoAbsoluta.actualizarCupo();
		
		boolean obtenido = promoAbsoluta.actualizarCupo();
		boolean esperado = false; // Bosque negro quedo con 0 cupos
		
		Assert.assertEquals(esperado, obtenido);

	}
	
	@Test
	public void ConCupoGratuitasPromocionAxB() {
		
		List<Atraccion> atraccionesIncluidas = new ArrayList<Atraccion>();
		List<Atraccion> atraccionesGratuitas = new ArrayList<Atraccion>();
		
		Atraccion atraccion1 = new Atraccion("Delagua", 21, 1.5f, 7, "Banquete");
		Atraccion atraccion2 = new Atraccion("Hobbiton", 19, 3.5f, 5, "Banquete");
		Atraccion atraccion3 = new Atraccion("Rivendel", 19, 1.5f, 1, "Banquete");
		
		atraccionesIncluidas.add(atraccion1);
		atraccionesIncluidas.add(atraccion2);
		atraccionesGratuitas.add(atraccion3);
		
		PromocionAxB promoAxB = new PromocionAxB("Pack Super Banquete",atraccionesIncluidas,3,atraccionesGratuitas);
		
		
		boolean obtenido = promoAxB.actualizarCupo();
		boolean esperado = true; //Rivendel quedara con 0, se puede anotar 1 vez
		
		Assert.assertEquals(esperado, obtenido);
	}
	
	@Test
	public void SinCupoGratuitasPromocionAxB() {
		
		List<Atraccion> atraccionesIncluidas = new ArrayList<Atraccion>();
		List<Atraccion> atraccionesGratuitas = new ArrayList<Atraccion>();
		
		Atraccion atraccion1 = new Atraccion("Delagua", 21, 1.5f, 7, "Banquete");
		Atraccion atraccion2 = new Atraccion("Hobbiton", 19, 3.5f, 5, "Banquete");
		Atraccion atraccion3 = new Atraccion("Rivendel", 19, 1.5f, 0, "Banquete");
		
		atraccionesIncluidas.add(atraccion1);
		atraccionesIncluidas.add(atraccion2);
		atraccionesGratuitas.add(atraccion3);
		
		PromocionAxB promoAxB = new PromocionAxB("Pack Super Banquete",atraccionesIncluidas,3,atraccionesGratuitas);
		
		
		boolean obtenido = promoAxB.tieneCupo();
		boolean esperado = false; //Rivendel tiene 0 cupo
		
		Assert.assertEquals(esperado, obtenido);
	}
	
	
	@Test
	public void calculoTiempoPromocionAxB() {
		
		List<Atraccion> atraccionesIncluidas = new ArrayList<Atraccion>();
		List<Atraccion> atraccionesGratuitas = new ArrayList<Atraccion>();
		
		Atraccion atraccion1 = new Atraccion("Delagua", 21, 1.5f, 7, "Banquete");
		Atraccion atraccion2 = new Atraccion("Hobbiton", 19, 3.5f, 5, "Banquete");
		Atraccion atraccion3 = new Atraccion("Rivendel", 19, 1.5f, 1, "Banquete");
		
		atraccionesIncluidas.add(atraccion1);
		atraccionesIncluidas.add(atraccion2);
		atraccionesGratuitas.add(atraccion3);
		
		PromocionAxB promoAxB = new PromocionAxB("Pack Super Banquete",atraccionesIncluidas,3,atraccionesGratuitas);
		
		float esperado = 6.5f;
		float obtenido = promoAxB.calculoTiempoTotal();
		
		Assert.assertEquals(esperado, obtenido,0);
		
	}
	@Test
	public void calculoTiempoPromocionAbsoluta() {
		
		List<Atraccion> atraccionesIncluidas = new ArrayList<Atraccion>();

		Atraccion atraccion1 = new Atraccion("Lothlorien", 21, 1.5f, 4, "Aventura");
		Atraccion atraccion2 = new Atraccion("Bosque Negro", 19, 3, 1, "Aventura");
		Atraccion atraccion3 = new Atraccion("Mordor", 18, 1, 4, "Aventura");

		atraccionesIncluidas.add(atraccion1);
		atraccionesIncluidas.add(atraccion2);
		atraccionesIncluidas.add(atraccion3);

		PromocionAbsoluta promoAbsoluta = new PromocionAbsoluta("Pack Super Aventura", atraccionesIncluidas, 2, 34);
		
		
		float esperado = 5.5f;
		float obtenido = promoAbsoluta.calculoTiempoTotal();
		
		Assert.assertEquals(esperado, obtenido,0);
		
	}
	
	@Test
	public void calculoPrecioFinalPromocionAbsoluta() {
		List<Atraccion> atraccionesIncluidas = new ArrayList<Atraccion>();

		Atraccion atraccion1 = new Atraccion("Lothlorien", 21, 1.5f, 4, "Aventura");
		Atraccion atraccion2 = new Atraccion("Bosque Negro", 19, 3, 1, "Aventura");
		Atraccion atraccion3 = new Atraccion("Mordor", 18, 1, 4, "Aventura");

		atraccionesIncluidas.add(atraccion1);
		atraccionesIncluidas.add(atraccion2);
		atraccionesIncluidas.add(atraccion3);

		PromocionAbsoluta promoAbsoluta = new PromocionAbsoluta("Pack Super Aventura", atraccionesIncluidas, 2, 34);
		
		
		int esperado = 34;
		int obtenido = promoAbsoluta.calculoPrecioFinal();
		
		Assert.assertEquals(esperado, obtenido,0);
	}
	
	@Test
	public void calculoPrecioFinalPromocionAxB() {

		List<Atraccion> atraccionesIncluidas = new ArrayList<Atraccion>();
		List<Atraccion> atraccionesGratuitas = new ArrayList<Atraccion>();
		
		Atraccion atraccion1 = new Atraccion("Delagua", 21, 1.5f, 7, "Banquete");
		Atraccion atraccion2 = new Atraccion("Hobbiton", 19, 3.5f, 5, "Banquete");
		Atraccion atraccion3 = new Atraccion("Rivendel", 19, 1.5f, 1, "Banquete");
		
		atraccionesIncluidas.add(atraccion1);
		atraccionesIncluidas.add(atraccion2);
		atraccionesGratuitas.add(atraccion3);
		
		PromocionAxB promoAxB = new PromocionAxB("Pack Super Banquete",atraccionesIncluidas,3,atraccionesGratuitas);
		
		
		int esperado = 40;
		int obtenido = promoAxB.calculoPrecioFinal();
		
		Assert.assertEquals(esperado, obtenido,0);
	}
	
	@Test
	public void calculoPrecioFinalPromocionPorcentual() {

		List<Atraccion> atraccionesIncluidas = new ArrayList<Atraccion>();
		
		Atraccion atraccion1 = new Atraccion("Mordor", 18, 1f, 4, "Aventura");
		Atraccion atraccion2 = new Atraccion("Moria", 22, 2.5f, 6, "Aventura");
		
		atraccionesIncluidas.add(atraccion1);
		atraccionesIncluidas.add(atraccion2);
		
		PromocionPorcentual promoPorcentual = new PromocionPorcentual("Pack Aventura",atraccionesIncluidas,1,25);
		
		
		int esperado = 30;
		int obtenido = promoPorcentual.calculoPrecioFinal();
		
		Assert.assertEquals(esperado, obtenido,0);
	}
	
	
	
	

}
