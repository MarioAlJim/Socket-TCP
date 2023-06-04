/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculadorasocketstcp.servidor;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 *
 * @author Frost
 */
public class Servidor {
    
    public static void main(String args[]){
        String expresion;
        String resultado;
        try{
            ServerSocket welcomeSocket = new ServerSocket(5555);
            while(true) {
                System.out.println("Servidor en ejecución... esperando conexión...");
                Socket connectionSocket = welcomeSocket.accept();
                System.out.println("Conexión aceptada...");
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                expresion = inFromClient.readLine();
                System.out.println("Expresión recibida: "+expresion);
                resultado = resolverExpresion(expresion);
                System.out.println("Resultado: "+resultado);
                if(resultado!=null){
                    DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                    outToClient.writeBytes(resultado + "\n");
                }else{
                    DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                    outToClient.writeBytes("La expresion no se pudo resolver..." + "\n");
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            System.out.println("Server stopped...");
        }
    }
    
    public static String resolverExpresion(String expresion){
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine se = scriptEngineManager.getEngineByName("JavaScript");
        String resultado = null;
        //System.out.println(expresion);
        try{
            expresion.replaceAll("\\n", "");
            resultado = se.eval(expresion).toString();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return resultado;
    }
    
}
