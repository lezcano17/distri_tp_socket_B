package py.una.bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import py.una.entidad.Nis;

public class NisDAO {

    /**
     * @param condiciones
     * @return
     */
    public List<Nis> seleccionar() {
        String query = "SELECT id_nis, estado FROM  nis";

        List<Nis> lista = new ArrayList<Nis>();

        Connection conn = null;
        try {
            conn = Bd.connect();
            ResultSet rs = conn.createStatement().executeQuery(query);

            while (rs.next()) {
                Nis c = new Nis();
                c.setId_nis(null);
                c.setEstado(null);

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

    public Nis seleccionarPorIdNis(Long id) {
        String SQL = "SELECT id_nis, estado FROM nis WHERE id_nis = ? ";

        Nis lista = new Nis();

        Connection conn = null;
        try {
            conn = Bd.connect();
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setLong(1, id);

            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                Nis m = new Nis();
                m.setId_nis(rs.getLong(1));
                m.setEstado(rs.getInt(2));
                lista=m;
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

    public ArrayList<Long> seleccionarPorEstado(int estado) {
        String SQL = "SELECT id_nis FROM nis WHERE estado = ? ";


        List<Long> lista = new ArrayList<Long>();

        Connection conn = null;
        try
        {
            conn = Bd.connect();
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, estado);
            System.out.println(pstmt.toString());


            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                Long m;
                m = rs.getLong(1);

                lista.add(m);
            }

        } catch (SQLException ex) {
            System.out.println("Error en la seleccion: " + ex.getMessage());
        }
        finally  {
            try{
                conn.close();
            }catch(Exception ef){
                System.out.println("No se pudo cerrar la conexion a BD: "+ ef.getMessage());
            }
        }
        return (ArrayList<Long>) lista;

    }


    public long insertar(Nis c) throws SQLException {

        String SQL = "INSERT INTO nis(id_nis, estado) "
                + "VALUES(?,?)";

        long id = 0;
        Connection conn = null;

        try {
            conn = Bd.connect();
            PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setLong(1, c.getId_nis());
            pstmt.setDouble(2, c.getEstado());

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

    public long actualizar(Nis c) throws SQLException {

        String SQL = "UPDATE nis SET id_nis= ? , estado= ? WHERE id_nis = ? ";

        long id = 0;
        Connection conn = null;

        try {
            conn = Bd.connect();
            PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setLong(1, c.getId_nis());
            pstmt.setDouble(2, c.getEstado());
            pstmt.setDouble(3, c.getId_nis());

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

    public long borrar(long id_nis) throws SQLException {

        String SQL = "DELETE FROM nis WHERE id_nis = ? ";

        long id = 0;
        Connection conn = null;

        try {
            conn = Bd.connect();
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setLong(1, id_nis);

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
