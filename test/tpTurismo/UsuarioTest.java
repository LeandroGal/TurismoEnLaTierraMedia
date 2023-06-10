package tpTurismo;

import org.junit.Assert;
import org.junit.Test;

public class UsuarioTest {

	@Test
	public void ActualizarConPresupuestoYTiempo() {
		
		Usuario usuario = new Usuario("Frodo Bolson","Aventura",64,7f);
		
		boolean esperado=true;
		boolean obtenido=usuario.actualizarPresupuestoYTiempo(20, 2);
		
		Assert.assertEquals(esperado, obtenido);
		
	}
	
	@Test
	public void ActualizarSinPresupuestoYTiempo() {
		
		Usuario usuario = new Usuario("Frodo Bolson","Aventura",19,7f);
		
		boolean esperado=false;
		boolean obtenido=usuario.actualizarPresupuestoYTiempo(20, 2);
		
		Assert.assertEquals(esperado, obtenido);
		
	}

}
