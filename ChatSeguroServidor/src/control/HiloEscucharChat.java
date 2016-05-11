package control;

import modelo.Cliente;

public class HiloEscucharChat implements Runnable{
	private Ejecutable exe;
	private Cliente client;
	
	
	// ---------------------------------------------------------------------
	// Constructor
	// ---------------------------------------------------------------------

	public HiloEscucharChat(Ejecutable exe, Cliente client) {
		// TODO Auto-generated constructor stub
		this.exe = exe;
		this.client = client;
	}
	
	// ---------------------------------------------------------------------
	// RUN
	// ---------------------------------------------------------------------

	public void run() {
		// TODO Auto-generated method stub
		boolean terminar = false;
		while(!terminar)
		{
			terminar = client.escucharLinea1();
		}
		
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		}
	}
}
