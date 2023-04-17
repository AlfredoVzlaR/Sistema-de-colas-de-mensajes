/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.productor;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import entidad.Pelicula;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;
/**
 *
 * @author jvale
 */
public class Productor {
    static String QUEUE_NAME = "hello";
    
    static Scanner tec = new Scanner(System.in);
    static String nombre;
    static String director;
    static String productora;
    static int duracion;
    static int condicion=1;
    
    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost("localhost");

        try {

            Connection connection = (Connection) factory.newConnection();

            Channel channel = connection.createChannel();

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            Pelicula pelicula;
            
            while(condicion!=0){
                
                
                
                System.out.print("Ingrese el nombre de la pelicula: ");
                nombre = tec.nextLine();
                System.out.print("Ingrese el director de la pelicula: ");
                director = tec.nextLine();
                System.out.print("Ingrese la productora de la pelicula: ");
                productora = tec.nextLine();
                System.out.print("Ingrese la duración de la pelicula: ");
                duracion = tec.nextInt();
                
                pelicula = new Pelicula(nombre, director, productora, duracion);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream out = new ObjectOutputStream(bos);
                out.writeObject(pelicula);
                out.flush();
                byte[] serializedPelicula = bos.toByteArray();
                
                channel.basicPublish("", QUEUE_NAME, null, serializedPelicula);

                channel.close();
                connection.close();
                
                System.out.println("Ingresa 0 para finalizar: ");
                condicion = tec.nextInt();
                
            }
            
            

            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
