package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import modelo.Cliente;
import vista.Principal;

public class Ejecutable {

	/// Atributos
	public static Principal principal;
	public static Cliente client;
	private HiloActualizarChat hiloActualizar;
	private HiloEscucharChat hiloEscuchar;
	private Thread hiloChat;
	private Thread hiloServidor;
	public static boolean onChat;

	/// Constructor
	public Ejecutable() {
		principal = new Principal();
		client = new Cliente();
		hiloActualizar = new HiloActualizarChat(client, this);
		hiloEscuchar = new HiloEscucharChat(client, this);
		principal.setVisible(true);
		escucharChat();
	}

	/// Metodos
	public void escucharCerrarChat() {
		principal.chat.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		principal.chat.addWindowListener(new WindowListener() {
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
				principal.chat.setVisible(false);
				onChat = false;
				client.cerrarChat();
				hiloServidor.interrupt();
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

	public void escucharBotonEnviar() {
		principal.chat.getBtnEnviar().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String pregunta = principal.chat.getTextFieldMensaje().getText();
				principal.chat.getTextFieldMensaje().setText("");
				client.escribirChat(pregunta);
			}
		});
	}

	public void escucharChat() {
		principal.getBtnChat().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				client.loginServidor("chat");
				principal.chat.setVisible(true);
				escucharCerrarChat();
				onChat = true;
				hiloChat = new Thread(hiloActualizar);
				hiloChat.start();
				hiloServidor = new Thread(hiloEscuchar);
				hiloServidor.start();
				escucharBotonEnviar();
			}
		});
	}

	public static void main(String[] args) {
		Ejecutable ejecutable = new Ejecutable();
	}
}
