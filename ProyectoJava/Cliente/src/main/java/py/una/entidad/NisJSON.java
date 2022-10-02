package py.una.entidad;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class NisJSON {

    public static void main(String[] args) throws Exception {
        NisJSON representacion = new NisJSON();

        System.out.println("Ejemplo de uso 1: pasar de objeto a string");
        Nis c = new Nis();
        c.setEstado(0);
        c.setId_nis(123456L);

        String r1 = representacion.objetoString(c);
        System.out.println(r1);

        System.out.println("\n*************************************************************************");
        System.out.println("\nEjemplo de uso 2: pasar de string a objeto");
        String un_string = "{\"id_nis\":123123,\"estado\":\0\"}";

        Nis r2 = representacion.stringObjeto(un_string);
        System.out.println(r2.id_nis + " " + r2.estado);
    }

    public static String objetoString(Nis c) {

        JSONObject obj = new JSONObject();
        obj.put("id_nis", c.getId_nis());
        obj.put("estado", c.getEstado());

        JSONArray list = new JSONArray();

        return obj.toJSONString();
    }

    public static Nis stringObjeto(String str) throws Exception {
        Nis c = new Nis();
        JSONParser parser = new JSONParser();

        Object obj = parser.parse(str.trim());
        JSONObject jsonObject = (JSONObject) obj;

        Long id_nis = (Long) jsonObject.get("id_nis");
        c.setId_nis(id_nis);

        c.setEstado(Integer.parseInt((String)jsonObject.get("estado").toString()));

        return c;
    }

}
