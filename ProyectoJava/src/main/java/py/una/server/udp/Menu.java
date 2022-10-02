package py.una.server.udp;

import java.util.Scanner;

import py.una.entidad.Nis;
import py.una.entidad.Operacion;

import static py.una.entidad.NisJSON.objetoString;


public class Menu {

    public static void menuError(String error){
        System.err.println(error);
    }

    public static Operacion clienteMenu(){
        System.out.println("1 - Registrar consumo NIS");
        System.out.println("2 - Enviar orden conexion");
        System.out.println("3 - Enviar orden desconexion");
        System.out.println("4 - Listar NIS activos");
        System.out.println("5 - Listar NIS inactivos");
        System.out.println("Cualquier otro caracter para salir");
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
        String cuerpo = null;
        Nis datos = new Nis();

        switch (input) {
            case 1:

                System.out.println("Ingrese los datos del Nis");
                System.out.println("NIS: ");
                datos.setId_nis(Long.parseLong(sc.next()));
                System.out.println("Estado: ");
                datos.setEstado( Integer.parseInt(sc.next()));

                cuerpo = objetoString(datos);
                break;
            case 2:
                int valor = 0;
                System.out.println("Ingrese el id del Nis a desconectar:");
                System.out.println("NIS: ");
                datos.setId_nis(Long.parseLong(sc.next()));
                datos.setEstado(valor);

                cuerpo = objetoString(datos);
                break;
            case 3:

                System.out.println("Ingrese el id del Nis a Conectar:");
                System.out.println("NIS: ");
                datos.setId_nis(Long.parseLong(sc.next()));
                datos.setEstado(1);

                cuerpo = objetoString(datos);
                break;
            case 4:
                cuerpo = "1";
                break;
            case 5:
                cuerpo = "0";
                break;
            default:
                System.exit(0);
        }
        return new Operacion(input,cuerpo);
    }

    public static void menuRespuesta(Operacion respuesta){
        System.out.println();
        System.out.println(respuesta.cuerpo);
        System.out.println();
    }

}