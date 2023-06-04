/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculadorasocketstcp.cliente;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 *
 * @author Frost
 */
public class Cliente {
    public static void main(String args[]){
        String expresion = "";
        String resultado = "";
        boolean salir = false;
        do{
            try{
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Escribe <salir para terminar> una expresión matemática: ");
                expresion = in.readLine();
                if(expresion.compareToIgnoreCase("salir")!=0){
                    Socket socket = new Socket("localhost", 5555);
                    DataOutputStream outserver = new DataOutputStream(socket.getOutputStream());
                    BufferedReader inserver = new BufferedReader(new
                                                  InputStreamReader(socket.getInputStream()));
                    
                    outserver.writeBytes(expresion + "\n");
                    resultado = inserver.readLine();
                    System.out.println("Resultado: " + resultado);
                    socket.close();
                }else{
                    salir = true;
                }
            }catch(Exception ex){
                ex.printStackTrace();
                salir = true;
            }
        }while(!salir);
    }
}
