package control;

import modelo.Cliente;

public class HiloEscucharChat implements Runnable{
	
	///Atributos
	private Ejecutable exe;
	private Cliente clien;
	
	///Constructor
	public HiloEscucharChat(Cliente clien, Ejecutable exe){
		this.clien = clien;
		this.exe = exe;
	}
	
	public void run() {
		while(exe.onChat){
			clien.leerChat();
			try {
				Thread.sleep(1000);
			} 
			catch(InterruptedException e){
			}
		}
		
	}
}
