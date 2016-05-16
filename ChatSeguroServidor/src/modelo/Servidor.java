package modelo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import control.Ejecutable;

public class Servidor {
	
	/// Constantes
	public final static String IP = "localhost";
	public final static int PUERTO = 3000;
	public final static int PUERTO_KEY = 4000;
	
	/// Atributos
	private ServerSocket server;
	private ServerSocket serverKey;
	private ArrayList<Cliente> clientes;
	private HiloConector hiloLogin;

	/// Constructor
	public Servidor(Ejecutable ejecutable){
		try{
			server = new ServerSocket(PUERTO);
			serverKey = new ServerSocket(PUERTO_KEY);
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		clientes = new ArrayList<Cliente>();
		hiloLogin = new HiloConector(this,ejecutable);
		Thread hilo = new Thread(hiloLogin);
		hilo.start();
	}
	// ---------------------------------------------------------------------
	// Metodos
	// ---------------------------------------------------------------------

	public void agregarEstudianteLinea1(Cliente estudiante)
	{
		clientes.add(estudiante);
	}

	public ServerSocket getServer() {
		return server;
	}
	public ServerSocket getServerKey(){
		return serverKey; 
	}

	public void setServer(ServerSocket server) {
		this.server = server;
	}

	public ArrayList<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(ArrayList<Cliente> clientes) {
		this.clientes = clientes;
	}

	
}
