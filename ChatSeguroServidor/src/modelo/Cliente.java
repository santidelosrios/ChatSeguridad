package modelo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Cliente {
	
	/// Atributos
	private Socket socket;
	private DataInputStream entrada;
	private DataOutputStream salida;
	private String chat;
	
	/// Constructor
	public Cliente(Socket socket,DataInputStream entrada, DataOutputStream salida){
		this.socket = socket;
		this.entrada = entrada;
		this.salida = salida;
		chat = "";	
	}
	
	/// Metodos
	public void escribirSocket(String texto){
		try{
			salida.writeUTF(texto);
		} 
		catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public String leerSocket(){
		String texto = "";
		try{
			texto = entrada.readUTF();
		} catch (IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return texto;
	}
	
	public void cerrarSocket(){
		try{
			entrada.close();
			salida.close();
			socket.close();
		} 
		catch (IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean escucharChat()
	{
		String txtRcpt = leerSocket();
		boolean finalizar = (txtRcpt.equals("terminar"))?true:false;
		chat = (finalizar)?"El cliente dejo la conversacion.":chat +"Cliente : "+txtRcpt+ "\n";
		if(finalizar){
			cerrarSocket();
		}
		return finalizar;
	}
	
	public void responderChat(String texto)
	{
		chat+="Servidor: "+texto+ "\n";
		escribirSocket(texto);
		
	}
	
	public void run(){
		// TODO Auto-generated method stub
		escucharChat();
	}
	
	public String getChat() {
		return chat;
	}

	public void setChat(String chat) {
		this.chat = chat;
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
