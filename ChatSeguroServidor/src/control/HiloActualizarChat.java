package control;

import modelo.Servidor;

public class HiloActualizarChat implements Runnable{
	// ---------------------------------------------------------------------
		// Atributos
		// ---------------------------------------------------------------------

		private Ejecutable exe;
		private Servidor server;
		
		// ---------------------------------------------------------------------
		// Constructor
		// ---------------------------------------------------------------------

		public HiloActualizarChat(Ejecutable exe, Servidor server) {
			// TODO Auto-generated constructor stub
			this.exe = exe;
			this.server = server;
		}

		
		// ---------------------------------------------------------------------
		// Run
		// ---------------------------------------------------------------------

		public void run() 
		{
			// TODO Auto-generated method stub
			while(exe.isOnChat())
			{
					exe.actualizarChatActual();
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
				}
			}
		}
		
}
