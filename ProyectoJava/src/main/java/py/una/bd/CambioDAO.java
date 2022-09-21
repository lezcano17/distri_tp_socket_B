package py.una.bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import py.una.entidad.Cambio;

public class CambioDAO {

    /**
     * 
     * @param condiciones
     * @return
     */
    public List<Cambio> seleccionar() {
        String query = "SELECT id_entidad, compra, venta FROM cambio";

        List<Cambio> lista = new ArrayList<Cambio>();

        Connection conn = null;
        try {
            conn = Bd.connect();
            ResultSet rs = conn.createStatement().executeQuery(query);

            while (rs.next()) {
                Cambio c = new Cambio();
                c.setId_entidad(null);
                c.setCompra(null);
                c.setVenta(null);

                lista.add(c);
            }

        } catch (SQLException ex) {
            System.out.println("Error en la seleccion: " + ex.getMessage());
        } finally {
            try {
                conn.close();
            } catch (Exception ef) {
                System.out.println("No se pudo cerrar la conexion a BD: " + ef.getMessage());
            }
        }
        return lista;

    }

    public long insertar(Cambio c) throws SQLException {

        String SQL = "INSERT INTO cambio(id_entidad, compra, venta) "
                + "VALUES(?,?,?)";

        long id = 0;
        Connection conn = null;

        try {
            conn = Bd.connect();
            PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setLong(1, c.getId_entidad());
            pstmt.setDouble(2, c.getCompra());
            pstmt.setDouble(3, c.getVenta());

            int affectedRows = pstmt.executeUpdate();
            // check the affected rows
            if (affectedRows > 0) {
                // get the ID back
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error en la insercion: " + ex.getMessage());
        } finally {
            try {
                conn.close();
            } catch (Exception ef) {
                System.out.println("No se pudo cerrar la conexion a BD: " + ef.getMessage());
            }
        }

        return id;

    }

    public long actualizar(Cambio c) throws SQLException {

        String SQL = "UPDATE cambio SET id_entidad= ? , compra= ? WHERE id_entidad = ? ";

        long id = 0;
        Connection conn = null;

        try {
            conn = Bd.connect();
            PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setLong(1, c.getId_entidad());
            pstmt.setDouble(2, c.getCompra());
            pstmt.setDouble(3, c.getVenta());

            int affectedRows = pstmt.executeUpdate();
            // check the affected rows
            if (affectedRows > 0) {
                // get the ID back
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error en la actualizacion: " + ex.getMessage());
        } finally {
            try {
                conn.close();
            } catch (Exception ef) {
                System.out.println("No se pudo cerrar la conexion a BD: " + ef.getMessage());
            }
        }
        return id;
    }

    public long borrar(long id_entidad) throws SQLException {

        String SQL = "DELETE FROM cambio WHERE id_entidad = ? ";

        long id = 0;
        Connection conn = null;

        try {
            conn = Bd.connect();
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setLong(1, id_entidad);

            int affectedRows = pstmt.executeUpdate();
            // check the affected rows
            if (affectedRows > 0) {
                // get the ID back
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error en la eliminación: " + ex.getMessage());
        } finally {
            try {
                conn.close();
            } catch (Exception ef) {
                System.out.println("No se pudo cerrar la conexion a BD: " + ef.getMessage());
            }
        }
        return id;
    }

}
