package py.una.cliente;


import java.io.*;
import java.net.*;

import py.una.entidad.Operacion;

import static py.una.Menu.*;

class UDPClient {
    public static boolean salir = false;

    public static void main(String a[]) throws Exception {



        // Datos necesario
        String direccionServidor = "127.0.0.1";
        int puertoServidor = 9876;

        if (a.length > 0) {
            direccionServidor = a[0];
        }

        InetAddress IPAddress = InetAddress.getByName(direccionServidor);
        System.out.println("Intentando conectar a = " + IPAddress + ":" + puertoServidor +  " via UDP...");
        while(!salir){
            try {
                DatagramSocket clientSocket = new DatagramSocket();

                byte[] sendData = new byte[1024];
                byte[] receiveData = new byte[1024];

                //ESPERANDO AL CLIENTE
                Operacion enviarCliente = clienteMenu();
                System.out.println(enviarCliente);

                if(enviarCliente.cuerpo == null){
                    UDPClient.salir = true;
                    System.exit(0);
                }

                String datoPaquete = enviarCliente.toJSON();
                sendData = datoPaquete.getBytes();
                System.out.println("Enviar " + datoPaquete + " al servidor. ("+ sendData.length + " bytes)");

                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, puertoServidor);
                clientSocket.send(sendPacket);

                DatagramPacket receivePacket =  new DatagramPacket(receiveData, receiveData.length);

                System.out.println("Esperamos si viene la respuesta.");

                //Vamos a hacer una llamada BLOQUEANTE entonces establecemos un timeout maximo de espera
                clientSocket.setSoTimeout(10000);

                try {
                    // ESPERAMOS LA RESPUESTA, BLOQUENTE
                    clientSocket.receive(receivePacket);

                    String respuesta = new String(receivePacket.getData());


                    InetAddress returnIPAddress = receivePacket.getAddress();
                    int port = receivePacket.getPort();

                    System.out.println("Respuesta desde =  " + returnIPAddress + ":" + port);

                    Operacion respuestaObj = new Operacion(respuesta);

                    menuRespuesta(respuestaObj);



                } catch (SocketTimeoutException ste) {

                    menuError("TimeOut: El paquete udp se asume perdido.");
                }
                clientSocket.close();
            } catch (UnknownHostException ex) {
                menuError(ex.toString());
            } catch (IOException ex) {
                menuError(ex.toString());
            }
        }
    }
}

