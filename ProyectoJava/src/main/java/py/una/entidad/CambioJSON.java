package py.una.entidad;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class CambioJSON {

    public static void main(String[] args) throws Exception {
        CambioJSON representacion = new CambioJSON();

        System.out.println("Ejemplo de uso 1: pasar de objeto a string");
        Cambio c = new Cambio();
        c.setCompra(6770.0);
        c.setVenta(6980.0);
        c.setId_entidad(123456L);

        String r1 = representacion.objetoString(c);
        System.out.println(r1);

        System.out.println("\n*************************************************************************");
        System.out.println("\nEjemplo de uso 2: pasar de string a objeto");
        String un_string = "{\"id_entidad\":123123,\"compra\":\6750\",\"venta\":\"6890.0\"]}";

        Cambio r2 = representacion.stringObjeto(un_string);
        System.out.println(r2.id_entidad + " " + r2.compra + " " + r2.venta);
    }

    public static String objetoString(Cambio c) {

        JSONObject obj = new JSONObject();
        obj.put("id_entidad", c.getId_entidad());
        obj.put("compra", c.getCompra());
        obj.put("venta", c.getVenta());

        JSONArray list = new JSONArray();

        return obj.toJSONString();
    }

    public static Cambio stringObjeto(String str) throws Exception {
        Cambio c = new Cambio();
        JSONParser parser = new JSONParser();

        Object obj = parser.parse(str.trim());
        JSONObject jsonObject = (JSONObject) obj;

        Long id_entidad = (Long) jsonObject.get("id_entidad");
        c.setId_entidad(id_entidad);
        c.setCompra((Double) jsonObject.get("compra"));
        c.setVenta((Double) jsonObject.get("venta"));

        JSONArray msg = (JSONArray) jsonObject.get("asignaturas");
        return c;
    }

}
