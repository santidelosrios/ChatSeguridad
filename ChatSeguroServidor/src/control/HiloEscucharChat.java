package control;

import modelo.Cliente;

public class HiloEscucharChat implements Runnable{
	
	/// Atributos
	private Cliente client;
	private Ejecutable exe;
	
	/// Constructor
	public HiloEscucharChat(Ejecutable exe, Cliente client) {
		this.client = client;
		this.exe = exe;
	}
	
	public void run(){
		boolean terminar = false;
		while(!terminar){
			terminar = client.escucharChat();
		}
		try {
			Thread.sleep(3000);
		}
		catch (InterruptedException e){
		}
	}
}
