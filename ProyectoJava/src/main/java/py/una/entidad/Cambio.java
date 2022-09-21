package py.una.entidad;

public class Cambio {

	Long id_entidad;
	Double compra;
	Double venta;

	public Cambio() {

	}

	/**
	 * @param id_entidad
	 * @param compra
	 * @param venta
	 */
	public Cambio(Long id_entidad, Double compra, Double venta) {
		super();
		this.id_entidad = id_entidad;
		this.compra = compra;
		this.venta = venta;
	}

	public Long getId_entidad() {
		return id_entidad;
	}

	public void setId_entidad(Long id_entidad) {
		this.id_entidad = id_entidad;
	}

	public Double getCompra() {
		return compra;
	}

	public void setCompra(Double compra) {
		this.compra = compra;
	}

	public Double getVenta() {
		return venta;
	}

	public void setVenta(Double venta) {
		this.venta = venta;
	}

}
