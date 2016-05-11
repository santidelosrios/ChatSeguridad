package modelo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import control.Ejecutable;

public class Servidor {
	public final static int PUERTO = 3000;
	public final static String IP = "localhost";
	
	// ---------------------------------------------------------------------
	// Atributos
	// ---------------------------------------------------------------------

	private ServerSocket server;
	private int clienteActual;
	private boolean salirChat;
	
	private ArrayList<Cliente> lineaAtencion1;
	
	private HiloConector hiloLogin;
	

	// ---------------------------------------------------------------------
	// Constructor
	// ---------------------------------------------------------------------

	public Servidor(Ejecutable ejecutable)
	{
		clienteActual = -1;
		lineaAtencion1 = new ArrayList<Cliente>();
		
		hiloLogin = new HiloConector(this,ejecutable);
		Thread hilo = new Thread(hiloLogin);
		
		salirChat = true;
		
		try 
		{
			server = new ServerSocket(PUERTO);
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		hilo.start();

	}
	// ---------------------------------------------------------------------
	// Metodos
	// ---------------------------------------------------------------------

	public void agregarEstudianteLinea1(Cliente estudiante)
	{
		lineaAtencion1.add(estudiante);
	}
	
	
	
	
	// ---------------------------------------------------------------------
	// Gets and Sets
	// ---------------------------------------------------------------------
	

	public ServerSocket getServer() {
		return server;
	}

	public void setServer(ServerSocket server) {
		this.server = server;
	}

	public ArrayList<Cliente> getLineaAtencion1() {
		return lineaAtencion1;
	}

	public void setLineaAtencion1(ArrayList<Cliente> lineaAtencion1) {
		this.lineaAtencion1 = lineaAtencion1;
	}

	
	
	
	
	// -------------------------------------------------------
	// Main
	// -------------------------------------------------------
}
