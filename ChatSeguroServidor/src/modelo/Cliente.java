package modelo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Cliente {
	private Socket socket;
	
	private DataInputStream entrada;
	private DataOutputStream salida;
	
	
	private String chat;
	
	// ---------------------------------------------------------------------
	// Constructor
	// ---------------------------------------------------------------------

	public Cliente(Socket socket,DataInputStream entrada, DataOutputStream salida)
	{
		this.socket = socket;
		
		this.entrada = entrada;
		this.salida = salida;

		chat = "";
		
	}
	
	// ---------------------------------------------------------------------
	// Metodos
	// ---------------------------------------------------------------------

	public void escribirSocket(String escritura)
	{
		try {
			salida.writeUTF(escritura);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String leerSocket()
	{
		String resultado = "";
		
		try {
			resultado = entrada.readUTF();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultado;
	}
	
	public void cerrarSocket()
	{
		try {
			entrada.close();
			salida.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run() {
		// TODO Auto-generated method stub
		escucharLinea1();
	}
	// ---------------------------------------------------------------------
	// Protocolos de Linea 1
	// ---------------------------------------------------------------------

	public void responderLinea1(String respuesta)
	{
		chat+="Servidor: "+respuesta+ "\n";
		escribirSocket(respuesta);
		
	}
	public boolean escucharLinea1()
	{
		String txtRcpt = leerSocket();
		
		boolean resultado = (txtRcpt.equals("terminar"))?true:false;
		
		chat = (resultado)?"El cliente dejo la conversacion!":chat +"Cliente : "+txtRcpt+ "\n";
		
		if(resultado)
		{
			cerrarSocket();
		}
		
		return resultado;
	}
	
	
	// ---------------------------------------------------------------------
	// Gets and Sets
	// ---------------------------------------------------------------------

	

	
	
	public String getChat() {
		return chat;
	}

	public void setChat(String chat) {
		this.chat = chat;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return chat;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public DataInputStream getEntrada() {
		return entrada;
	}

	public void setEntrada(DataInputStream entrada) {
		this.entrada = entrada;
	}

	public DataOutputStream getSalida() {
		return salida;
	}

	public void setSalida(DataOutputStream salida) {
		this.salida = salida;
	}
}
