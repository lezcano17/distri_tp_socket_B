package py.una.entidad;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Nis {

	Long id_nis;
	Integer estado;

	public Nis() {

	}

	/**
	 * @param id_nis
	 * @param estado
	 */
	public Nis(Long id_nis, Integer estado) {
		super();
		this.id_nis = id_nis;
		this.estado = estado;
	}
	public Nis(String str) throws Exception {
		JSONParser parser = new JSONParser();

		Object obj = parser.parse(str.trim());
		JSONObject jsonObject = (JSONObject) obj;

		this.id_nis = (Long) jsonObject.get("id_nis");;
		this.estado = Integer.parseInt(jsonObject.get("estado").toString());
	}

	public Long getId_nis() {
		return id_nis;
	}

	public void setId_nis(Long id_nis) {
		this.id_nis = id_nis;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}
}
