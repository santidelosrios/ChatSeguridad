package modelo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;

import control.Ejecutable;
import control.HiloEscucharChat;

public class HiloConector implements Runnable {
	
	/// Atributos
	private Servidor server;
	private Ejecutable exe;
	private boolean close;
	
	/// Constructor
	public HiloConector (Servidor server, Ejecutable exe){
		this.server = server;
		this.exe = exe;
		close = false;
	}
	
	///Metodos
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
				if(recibirCliente.equals("iniciar")){
						salida.writeUTF("Ok");
				}
				String txt = entrada.readUTF();
				if(txt.equals("chat"))
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
	
	public void generateKeyPair() {
		
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
