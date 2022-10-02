package py.una.bd;

import java.sql.SQLException;
import java.util.List;

import py.una.entidad.Nis;

public class TestNisDAO {

	public static void main(String args[]) throws SQLException {

		NisDAO pdao = new NisDAO();

		pdao.insertar(new Nis(200L, 0));
		pdao.insertar(new Nis(201L, 1));
		pdao.insertar(new Nis(202L, 1));
		pdao.insertar(new Nis(203L, 1));

		pdao.actualizar(new Nis(202L, 0));

		pdao.borrar(203L);

		List<Nis> lista = pdao.seleccionar();

		for (Nis c : lista) {
			System.out.println(c.getId_nis() + " " + c.getEstado());
		}
	}

}
