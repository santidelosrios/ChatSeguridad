package modelo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import control.Ejecutable;
import control.HiloEscucharChat;

public class HiloConector implements Runnable {
	private Servidor server;
	private boolean close;
	private Ejecutable exe;
	
	// -------------------------------------------------------------------------
	// Constructor
	// ------------------------------------------------------------------------

	public HiloConector (Servidor server, Ejecutable exe) {
		// TODO Auto-generated constructor stub
		this.server = server;
		close = false;
		this.exe = exe;
	}
	// -------------------------------------------------------------------------
	// Metodos
	// ------------------------------------------------------------------------

	public void connect() 
	{
		while(!close)
		{
			try 
			{
				Socket actualSock = server.getServer().accept();
				DataInputStream entrada = new DataInputStream(actualSock.getInputStream());
				DataOutputStream salida = new DataOutputStream(actualSock.getOutputStream());
				String recibirCliente = entrada.readUTF();
				if(recibirCliente.equals("Hola")){
					salida.writeUTF("Ok");
				}
				recibirCliente= entrada.readUTF();
				if(recibirCliente.equals("login")){
						salida.writeUTF("Ok");
				}
				System.out.println("termine de logear");
				String opcion = entrada.readUTF();
				if(opcion.equals("chat"))
				{
					Cliente client = new Cliente(actualSock, entrada, salida);
					server.agregarEstudianteLinea1(client);
					HiloEscucharChat hilo = new HiloEscucharChat(exe, client);
					Thread hiloEst = new Thread(hilo);
					hiloEst.start();
				}
			} 
			
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				close = true;
			}
			
		}
	}
	
	public void close()
	{
		close = true;
	}
	
	public void run() {
		// TODO Auto-generated method stub
		connect();
	}
}
