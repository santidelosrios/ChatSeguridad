package control;


import modelo.Cliente;

public class HiloActualizarChat implements Runnable{
	// ---------------------------------------------------------------------
		// Atributos
		// ---------------------------------------------------------------------
		
		private Cliente clien;
		private Ejecutable exe;
		
		// ---------------------------------------------------------------------
		// Constructor
		// ---------------------------------------------------------------------

		public HiloActualizarChat(Cliente clien, Ejecutable exe) 
		{
			this.clien = clien;
			this.exe = exe;
		}
		
		// ---------------------------------------------------------------------
		// Run
		// ---------------------------------------------------------------------

		
		public void run() 
		{
			// TODO Auto-generated method stub
			while(exe.onChat)
			{
				exe.principal.actualizarChat(clien.chat);
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
				}
			}
		}
}
