package control;

import modelo.Servidor;

public class HiloActualizarChat implements Runnable{
	
	/// Atributos
	private Servidor server;
	private Ejecutable exe;
	
	/// Constructor
	public HiloActualizarChat(Ejecutable exe, Servidor server){
		this.server = server;
		this.exe = exe;
	}
	
	public void run(){
		while(exe.isOnChat()){
				exe.actualizarChatActual();
			try{
				Thread.sleep(3000);
			} 
			catch (InterruptedException e){
			}
		}
	}
}
