package py.una;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

import py.una.bd.NisDAO;
import py.una.entidad.Nis;
import py.una.entidad.NisJSON;
import py.una.entidad.Operacion;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.File;
import java.io.FileWriter;
public class UDPServer {

    public static NisDAO ndao = new NisDAO();

    public static void main(String[] args) {
        int puertoServidor = 9876;
        if (args.length == 1) {
            puertoServidor = Integer.parseInt(args[0]);
        } else if (args.length > 1) {
            System.err.println("ERROR en args");
            System.exit(1);
        }

        BufferedWriter bw = null;
        FileWriter fw = null;
        try {
            //1) Creamos el socket Servidor de Datagramas (UDP)
            DatagramSocket serverSocket = new DatagramSocket(puertoServidor);
            System.out.println("Servidor Sistemas Distribuidos - UDP");

            //2) buffer de datos a enviar y recibir
            byte[] receiveData = new byte[1024];
            byte[] sendData = new byte[1024];

            File file = new File("log.txt");
            // Si el archivo no existe, se crea!
            if (!file.exists()) {
                file.createNewFile();
            }

            //3) Servidor siempre esperando
            while (true) {
                fw = new FileWriter(file.getAbsoluteFile(), true);
                bw = new BufferedWriter(fw);
                try {
                    receiveData = new byte[1024];
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    System.out.println("Esperando a algun cliente... ");

                    // 4) Receive LLAMADA BLOQUEANTE
                    serverSocket.receive(receivePacket);
                    System.out.println("________________________________________________");
                    System.out.println("Aceptamos un paquete");

                    // Datos recibidos e Identificamos quien nos envio
                    String datoRecibido = new String(receivePacket.getData());
                    datoRecibido = datoRecibido.trim();
                    System.out.println("DatoRecibido: " + datoRecibido);


                    Operacion recibidoCliente = new Operacion(datoRecibido);
                    Operacion enviarCliente = procesarOperacion(recibidoCliente);


                    sendData = enviarCliente.toJSON().getBytes();


                    // Enviando response
                    InetAddress IPAddress = receivePacket.getAddress();
                    int port = receivePacket.getPort();

                    //registrando datos para files > fecha-hora/tipo operacion
                    DateTimeFormatter fechaHora = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

                    int tipoOperacion = enviarCliente.tipo_operacion;

                    String nombreOperacion = "";
                    switch (tipoOperacion) {
                        case 1:
                            nombreOperacion = "registrar_consumo";
                            break;
                        case 2:
                            nombreOperacion = "conexion_suministro";
                            break;
                        case 3:
                            nombreOperacion = "desconexion_suministro";
                            break;
                        case 4:
                            nombreOperacion = "lista_activos";
                            break;
                        case 5:
                            nombreOperacion = " lista_inactivos";
                    }


                    System.out.println("IP Origen: " + IPAddress + ":" + puertoServidor + " IP Destino: " + IPAddress + ":" + port);
                    String datosLog = "" + fechaHora.format(LocalDateTime.now()) + " IP Origen: " + IPAddress + ":" + puertoServidor + " IP Destino: " + IPAddress + ":" + port + "Tipo Operacion " + nombreOperacion + "\n";
                    bw.append(datosLog);
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                    serverSocket.send(sendPacket);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        //Cierra instancias de FileWriter y BufferedWriter
                        if (bw != null)
                            bw.close();
                        if (fw != null)
                            fw.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }

    public static Operacion procesarOperacion(Operacion datoRecibido) throws Exception {
        int tipo_operacion = datoRecibido.tipo_operacion;
        String cuerpo = datoRecibido.cuerpo;
        Nis aux;
        Nis aux2;

        switch (tipo_operacion) {
            case 1:
                ndao.insertar(new Nis(cuerpo));

                return new Operacion(1, "Operacion completada con exito");
            case 2:
                System.out.println("Llego");
                aux = NisJSON.stringObjeto(datoRecibido.cuerpo);
                System.out.println(aux.getId_nis());
                aux2 = ndao.seleccionarPorIdNis(aux.getId_nis());


                if (aux2 != null && aux2.getEstado() == 1) {
                    ndao.actualizar(aux);
                    return new Operacion(2, "Operacion completada con exito");
                }
                return new Operacion(2, "La operacion no se ha podido realizar");
            case 3:
                aux = NisJSON.stringObjeto(datoRecibido.cuerpo);
                aux2 = ndao.seleccionarPorIdNis(aux.getId_nis());
                if (aux2 != null && aux2.getEstado() == 0) {
                    ndao.actualizar(aux);
                    return new Operacion(3, "Operacion completada con exito");
                }
                return new Operacion(3, "La operacion no se ha podido realizar");
            case 4:
                String estado = cuerpo;
                String lista = "La lista de activos es: ";
                ArrayList<Long> aux3 = ndao.seleccionarPorEstado(Integer.parseInt(estado));
                for (int i = 0; i < aux3.size(); i++) {
                    lista = lista + "\n Id_Nis: " + aux3.get(i);
                }

                return new Operacion(4, lista);

            case 5:
                String estado1 = cuerpo;
                String lista1 = "La lista de inactivos es: ";
                System.out.println("" + Integer.parseInt(estado1));
                ArrayList<Long> aux4 = ndao.seleccionarPorEstado(Integer.parseInt(estado1));
                for (int i = 0; i < aux4.size(); i++) {
                    lista1 = lista1 + "\n Id_Nis:" + aux4.get(i);
                }

                return new Operacion(5, lista1);


            default:
                break;
        }
        return null;
    }
}

