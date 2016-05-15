package modelo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
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
		doDiffieHellman();
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
	public void doDiffieHellman() {
		String algorithm = "DH"; 
		try {
			//Se genera una signatura de 1024-bits para el algoritmo Diffie Hellman -- código adaptado de: https://examples.javacodegeeks.com/core-java/security/diffie-helman-key-pair-generation-and-parameters/
			KeyPairGenerator keyG = KeyPairGenerator.getInstance(algorithm);
			keyG.initialize(1024);
			KeyPair pair = keyG.generateKeyPair();
			//Se va a generar los parametros G,P y L y se envian al servidor -- código adaptado de: http://docstore.mik.ua/orelly/java-ent/security/ch13_07.htm
			//Class dh = Class.forName("javax.crypto.spec.DHParameterSpec");
			DHParameterSpec dhspecs = ((DHPublicKey) pair.getPublic()).getParams();
			BigInteger g = dhspecs.getG();
			BigInteger p = dhspecs.getP();
			int l = dhspecs.getL();
			String sg = g.toString();
			String sp = p.toString();
			String sl = String.valueOf(l);
			String paramMessage = sp + ";" + sp + ";" + sl;
			System.out.println("ParamMessageC:" + paramMessage);
			escribirTCP(paramMessage);
			//Se genera la clave publica del cliente y se envia al servidor
			PublicKey pk = pair.getPublic(); 
			byte[] clientKey = pk.getEncoded(); 
			escribirTCP(clientKey.toString());
			System.out.println("Public KeyC: " + clientKey.toString());
			//Se procede a hacer la negociacion de clave
			KeyAgreement ka = KeyAgreement.getInstance(algorithm);
			PrivateKey ppk = pair.getPrivate();
			ka.init(ppk);
			//Ahora se debe leer la clave publica del servidor para generar la llave secreta
			String spk = lecturaTCP();
			byte[] bspk = spk.getBytes();
			KeyFactory kf = KeyFactory.getInstance(algorithm);
			X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(bspk);
			PublicKey spublickey = kf.generatePublic(x509EncodedKeySpec);
			ka.doPhase(spublickey, true);
			SecretKey sk = ka.generateSecret("AES");
			
		} catch (NoSuchAlgorithmException nsa) {
			// TODO Auto-generated catch block
			System.out.println("No existe el algoritmo");
			nsa.printStackTrace();
		} 
		//catch (ClassNotFoundException cne){
		//	System.out.println("No existe la clase");
		//}
		catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
