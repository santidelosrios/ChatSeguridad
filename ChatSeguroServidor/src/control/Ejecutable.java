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
	
	///Atributos
	private Servidor server;
	private Principal principalServidor;
	private Chat ventanaChat;
	public HiloActualizarChat hiloChatAct;
	private Thread hiloChat;
	private int chatActual;
	private boolean onChat;

	/// constructor
	public Ejecutable() {
		principalServidor = new Principal();
		principalServidor.setVisible(true);
		server = new Servidor(this);
		hiloChatAct = new HiloActualizarChat(this, server);
		chatActual = 0;
		onChat = false;
		escucharBotonIniciar();
	}
	
	///Metodos
	public void escucharCerrarChat() {
		ventanaChat
				.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		ventanaChat.addWindowListener(new WindowListener(){
			public void windowOpened(WindowEvent arg0){
				// TODO Auto-generated method stub
			}
			public void windowIconified(WindowEvent arg0){
				// TODO Auto-generated method stub
			}
			public void windowDeiconified(WindowEvent arg0){
				// TODO Auto-generated method stub
			}
			public void windowDeactivated(WindowEvent arg0){
				// TODO Auto-generated method stub
			}
			public void windowClosing(WindowEvent arg0){
				// TODO Auto-generated method stub
				ventanaChat.setVisible(false);
				onChat = false;
				hiloChat.interrupt();
			}
			public void windowClosed(WindowEvent arg0){
				// TODO Auto-generated method stub
			}
			public void windowActivated(WindowEvent arg0){
				// TODO Auto-generated method stub
			}
		});
	}

	public void actualizarChatActual(){
		if(chatActual!=-1){
			ventanaChat.actualizarChat(server.getClientes().get(chatActual).getChat());
		}
	}

	public void escucharBotonEnviarChat(){
		ventanaChat.getBtnEnviar().addActionListener(new ActionListener(){				
			public void actionPerformed(ActionEvent arg0){
				// TODO Auto-generated method stub
				String texto = ventanaChat.getTextFieldMensaje().getText();
				ventanaChat.getTextFieldMensaje().setText("");
				if(chatActual>-1){
					server.getClientes().get(chatActual).responderChat(texto);
					actualizarChatActual();
				}
			}
		});
	}
	
	public void escucharBotonIniciar(){
		principalServidor.getBtnIniciar().addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent arg0){
						ventanaChat = new Chat();
						ventanaChat.setVisible(true);
						onChat = true;
						hiloChat = new Thread(hiloChatAct);
						hiloChat.start();
						escucharCerrarChat();
						escucharBotonEnviarChat();
					}
				});
	}
	
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

	public static void main(String[] args) {
		Ejecutable eje = new Ejecutable();
	}
}
