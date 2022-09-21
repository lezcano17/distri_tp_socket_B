package py.una.bd;

import java.sql.SQLException;
import java.util.List;

import py.una.entidad.Cambio;

public class TestCambioDAO {

	public static void main(String args[]) throws SQLException {

		CambioDAO pdao = new CambioDAO();

		pdao.insertar(new Cambio(200L, 6750.0, 6880.0));
		pdao.insertar(new Cambio(201L, 6760.0, 6890.0));
		pdao.insertar(new Cambio(202L, 6730.0, 6870.0));
		pdao.insertar(new Cambio(203L, 6780.0, 6890.0));

		pdao.actualizar(new Cambio(202L, 6740.0, 6880.0));

		pdao.borrar(203L);

		List<Cambio> lista = pdao.seleccionar();

		for (Cambio c : lista) {
			System.out.println(c.getId_entidad() + " " + c.getCompra() + " " + c.getVenta());
		}
	}

}
