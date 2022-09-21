package py.una.server.udp;

import java.io.*;
import java.net.*;

import py.una.bd.CambioDAO;
import py.una.entidad.Cambio;
import py.una.entidad.CambioJSON;

public class UDPServer {
	
	
    public static void main(String[] a){
        
        // Variables
        int puertoServidor = 9876;
        CambioDAO pdao = new CambioDAO();
        
        try {
            //1) Creamos el socket Servidor de Datagramas (UDP)
            DatagramSocket serverSocket = new DatagramSocket(puertoServidor);
			System.out.println("Servidor Sistemas Distribuidos - UDP ");
			
            //2) buffer de datos a enviar y recibir
            byte[] receiveData = new byte[1024];
            byte[] sendData = new byte[1024];

			
            //3) Servidor siempre esperando
            while (true) {

                receiveData = new byte[1024];

                DatagramPacket receivePacket =
                        new DatagramPacket(receiveData, receiveData.length);


                System.out.println("Esperando a algun cliente... ");

                // 4) Receive LLAMADA BLOQUEANTE
                serverSocket.receive(receivePacket);
				
				System.out.println("________________________________________________");
                System.out.println("Aceptamos un paquete");

                // Datos recibidos e Identificamos quien nos envio
                String datoRecibido = new String(receivePacket.getData());
                datoRecibido = datoRecibido.trim();
                System.out.println("DatoRecibido: " + datoRecibido );
                Cambio c = CambioJSON.stringObjeto(datoRecibido);

                InetAddress IPAddress = receivePacket.getAddress();

                int port = receivePacket.getPort();

                System.out.println("De : " + IPAddress + ":" + port);
                System.out.println("Cambio Recibido: " + c.getId_entidad() + ", " + c.getCompra() + " " + c.getVenta());
                
                try {
                	pdao.insertar(c);
                	System.out.println("Cotización insertada exitosamente en la Base de datos");
                }catch(Exception e) {
                	System.out.println("Cotización NO insertada en la Base de datos, razón: " + e.getLocalizedMessage());
                }
                
                sendData = CambioJSON.objetoString(c).getBytes();
                DatagramPacket sendPacket =
                        new DatagramPacket(sendData, sendData.length, IPAddress,port);

                serverSocket.send(sendPacket);

            }

        } catch (Exception ex) {
        	ex.printStackTrace();
            System.exit(1);
        }

    }
}  

