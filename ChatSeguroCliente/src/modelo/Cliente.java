package modelo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;

public class Cliente {
	
	/// Constantes
	public static final String IP = "localhost";
	public final static int PUERTO = 3000;
	
	/// Atributos
	public static Socket socket;
	private DataOutputStream salidaTCP;
	private DataInputStream entradaTCP;
	public String chat;
	public String textoEnviado;
	public String textoRecibido;
	public String ack;
	public boolean sigueChat;

	/// Constructor
	public Cliente() {
		sigueChat = true;
		textoRecibido = "";
		textoEnviado = "";
		chat = "";
		
	}
	
	/// Metodos
	public void abrirSocket(){
		try {
			socket = new Socket(IP, PUERTO);
			salidaTCP = new DataOutputStream(socket.getOutputStream());
			entradaTCP = new DataInputStream(socket.getInputStream());
		} catch (UnknownHostException e){
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
	}

	public void cerrarSocketTCP(){
		try {
			entradaTCP.close();
			salidaTCP.close();
			socket.close();
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public void escribirTCP(String escritura){
		try {
			salidaTCP.writeUTF(escritura);
		} catch (IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String lecturaTCP(){
		String respuesta = "";
		try {
			respuesta = entradaTCP.readUTF();
		} catch (IOException e){
			e.printStackTrace();
		}
		return respuesta;
	}
	
	public void escribirChat(String texto)
	{
		escribirTCP(texto);
		chat += "Cliente: "+texto+"\n";
	}
	
	public void leerChat()
	{
		String lectura = lecturaTCP();
		chat += "Servidor: "+lectura+"\n";
	}
	
	public void cerrarChat()
	{
		escribirTCP("terminar");
		chat = "";
		cerrarSocketTCP();
	}
	
	public boolean loginServidor(String chat){
		abrirSocket();
		boolean coneccion = false;
		escribirTCP("Hola");
		String cdn = lecturaTCP();
		if (cdn.equals("Ok")){
			escribirTCP("iniciar");
			ack = lecturaTCP();
			if (ack.equals("Ok")){
				coneccion = true;
			}
		}
		escribirTCP(chat);
		if(!coneccion){
			cerrarSocketTCP();
		}
		return coneccion;
	}
	public void generateKeyPair() {
		String algorithm = "DH"; 
		try {
			//Se genera una signatura de 1024-bits para el algoritmo Diffie Hellman -- código adaptado de: https://examples.javacodegeeks.com/core-java/security/diffie-helman-key-pair-generation-and-parameters/
			KeyPairGenerator keyG = KeyPairGenerator.getInstance(algorithm);
			keyG.initialize(1024);
			KeyPair pair = keyG.generateKeyPair();
			//Se va a generar los parametros G,P y L -- código adaptado de: http://docstore.mik.ua/orelly/java-ent/security/ch13_07.htm
			//Class dh = Class.forName("javax.crypto.spec.DHParameterSpec");
			DHParameterSpec dhspecs = ((DHPublicKey) pair.getPublic()).getParams();
			BigInteger g = dhspecs.getG();
			BigInteger p = dhspecs.getP();
			int l = dhspecs.getL();
			//Se genera la clave publica del cliente
			PublicKey pk = pair.getPublic(); 
			byte[] clientKey = pk.getEncoded(); 
			
			
		} catch (NoSuchAlgorithmException nsa) {
			// TODO Auto-generated catch block
			System.out.println("No existe el algoritmo");
			nsa.printStackTrace();
		} 
		//catch (ClassNotFoundException cne){
		//	System.out.println("No existe la clase");
		//}
	}
	
	public String getTextoEnviado() {
		return textoEnviado;
	}

	public void setTextoEnviado(String textoEnviado) {
		this.textoEnviado = textoEnviado;
	}

	public boolean isSigueChat() {
		return sigueChat;
	}

	public void setSigueChat(boolean sigueChat) {
		this.sigueChat = sigueChat;
	}

	public String getTextoRecibido() {
		return textoRecibido;
	}

	public void setTextoRecibido(String textoRecibido) {
		this.textoRecibido = textoRecibido;
	}
}
