package control;

import modelo.Cliente;

public class HiloEscucharChat implements Runnable{
	// ---------------------------------------------------------------------
		// Atributos
		// ---------------------------------------------------------------------
		
		private Cliente clien;
		private Ejecutable exe;
		
		// ---------------------------------------------------------------------
		// Constructor
		// ---------------------------------------------------------------------
		
		public HiloEscucharChat(Cliente clien, Ejecutable exe) 
		{
			// TODO Auto-generated constructor stub
			this.clien = clien;
			this.exe = exe;
		}
		

		// ---------------------------------------------------------------------
		// Run
		// ---------------------------------------------------------------------

		public void run() {
			// TODO Auto-generated method stub
			while(exe.onChat)
			{
				clien.leerChat();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
				}
			}
			
		}
}
