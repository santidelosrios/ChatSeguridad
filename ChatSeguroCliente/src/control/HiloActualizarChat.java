package control;
import modelo.Cliente;
public class HiloActualizarChat implements Runnable{
	
	/// Atributos
	private Ejecutable exe;	
	private Cliente clien;
		
	///Constructor
	public HiloActualizarChat(Cliente clien, Ejecutable exe){
		this.exe = exe;
		this.clien = clien;
	}
	
	public void run(){
		while(exe.onChat){
			exe.principal.actualizarChat(clien.chat);
			try {
				Thread.sleep(3000);
			} 
			catch(InterruptedException e){
			}
		}
	}
}
