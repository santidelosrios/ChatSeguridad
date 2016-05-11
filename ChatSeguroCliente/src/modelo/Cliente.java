package modelo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Cliente {
	
	public final static int PUERTO = 3000;
	public static final String IP = "localhost";
	//
	// ------------------Atributos-------------------
	//
	public static Socket socket;
	private DataOutputStream salidaTCP;
	private DataInputStream entradaTCP;
	public boolean sigueChat;
	public String textoEnviado;
	public String textoRecibido;
	public String ack;
	public String linea;
	
	public String chat;
	// ------------------Constructor-------------------
	public Cliente() {
		sigueChat = true;
		textoRecibido = "";
		textoEnviado = "";
		linea = "No linea";
		chat = "";
		
	}
	//
	// ------------------Metodos-------------------
	//

	public void abrirSocket() {
		try {
			socket = new Socket(IP, PUERTO);
			salidaTCP = new DataOutputStream(socket.getOutputStream());
			entradaTCP = new DataInputStream(socket.getInputStream());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void escribirTCP(String escritura) {
		try {
			salidaTCP.writeUTF(escritura);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String lecturaTCP() {
		String respuesta = "";

		try {
			respuesta = entradaTCP.readUTF();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return respuesta;
	}

	public void cerrarSocketTCP() {
		try {
			entradaTCP.close();
			salidaTCP.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public boolean loginServidor(String opcion) {
		abrirSocket();
		boolean resultadoLogin = false;
		escribirTCP("Hola");
		String cdn = lecturaTCP();
		if (cdn.equals("Ok")) {
			escribirTCP("login");
			ack = lecturaTCP();
			if (ack.equals("Ok")) {
				resultadoLogin = true;
			}
		}
		escribirTCP(opcion);
		
		boolean cerroSocket = false;
		
		if(!resultadoLogin && !cerroSocket)
		{
			cerrarSocketTCP();
		}
		return resultadoLogin;
	}

	

	// ---------------------------------------------------------------------
	// Protocolo Linea 1
	// ---------------------------------------------------------------------

	public void escribirChat(String pregunta)
	{
		escribirTCP(pregunta);
		chat += "Cliente: "+pregunta+"\n";
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
	
	
	
	//
	// GETTERS AND SETTERS
	//

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

	public String getLinea() {
		return linea;
	}

	public void setLinea(String linea) {
		this.linea = linea;
	}
}
