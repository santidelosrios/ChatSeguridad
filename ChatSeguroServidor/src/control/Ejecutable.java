package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import modelo.Cliente;
import modelo.Servidor;
import vista.Chat;
import vista.Principal;

public class Ejecutable {
	// ---------------------------------------------------------------------
		// Atributos
		// ---------------------------------------------------------------------

		private Principal principalServidor;
		private Chat ventanaChat;
		private Servidor server;
		private boolean onChat;
		private Thread hiloChat;
		private int chatActual;
		public HiloActualizarChat hiloChatAct;
		


		// ---------------------------------------------------------------------
		// Constructor
		// ---------------------------------------------------------------------

		public Ejecutable() {
			principalServidor = new Principal();
			principalServidor.setVisible(true);

			chatActual = 0;
			server = new Servidor(this);
			onChat = false;

			hiloChatAct = new HiloActualizarChat(this, server);
			
			escucharBotonPreguntas();

		}
		
		// ---------------------------------------------------------------------
		// Metodos Linea 1
		// ---------------------------------------------------------------------

		

		public void escucharBotonPreguntas() {
			principalServidor.getBtnPreguntas().addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							// ArrayList<Estudiante> estudiantes = new
							// ArrayList<Estudiante>();
							ventanaChat = new Chat();
							ventanaChat.setVisible(true);
							onChat = true;
							System.out.println("viendo chat: " + onChat);

							hiloChat = new Thread(hiloChatAct);
							hiloChat.start();
							
							escucharCerrarChat();
							escucharBotonEnviarChat();
						}
					});
		}

		public void escucharCerrarChat() {
			ventanaChat
					.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
			ventanaChat.addWindowListener(new WindowListener() {
				public void windowOpened(WindowEvent arg0) {
					// TODO Auto-generated method stub
				}
				public void windowIconified(WindowEvent arg0) {
					// TODO Auto-generated method stub
				}
				public void windowDeiconified(WindowEvent arg0) {
					// TODO Auto-generated method stub
				}
				public void windowDeactivated(WindowEvent arg0) {
					// TODO Auto-generated method stub
				}
				public void windowClosing(WindowEvent arg0) {
					// TODO Auto-generated method stub
					ventanaChat.setVisible(false);
					onChat = false;
					System.out.println("viendo chat: " + onChat);
					hiloChat.interrupt();
				}
				public void windowClosed(WindowEvent arg0) {
					// TODO Auto-generated method stub
				}
				public void windowActivated(WindowEvent arg0) {
					// TODO Auto-generated method stub
				}
			});
		}

		public void actualizarChatActual() {
			if(chatActual!=-1){
				ventanaChat.actualizarChat(server.getLineaAtencion1().get(chatActual).getChat());
			}
		}

		
		
		public void escucharBotonEnviarChat()
		{
			ventanaChat.getBtnEnviar().addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					String texto = ventanaChat.getTextFieldMensaje().getText();
					ventanaChat.getTextFieldMensaje().setText("");
					
					if(chatActual>-1)
					{
						server.getLineaAtencion1().get(chatActual).responderLinea1(texto);
						actualizarChatActual();
					}
				}
			});
		}

		

		// ---------------------------------------------------------------------
		// Main
		// ---------------------------------------------------------------------

		public static void main(String[] args) {
			Ejecutable eje = new Ejecutable();
		}


		// ---------------------------------------------------------------------
		// Gets and Sets
		// ---------------------------------------------------------------------

		public Chat getVentanaChat() {
			return ventanaChat;
		}

		public void setVentanaChat(Chat ventanaChat) {
			this.ventanaChat = ventanaChat;
		}

		public boolean isOnChat() {
			return onChat;
		}

		public void setOnChat(boolean onChat) {
			this.onChat = onChat;
		}

}
