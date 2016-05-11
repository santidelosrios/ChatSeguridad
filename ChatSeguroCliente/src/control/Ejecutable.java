package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import modelo.Cliente;
import vista.Principal;

public class Ejecutable {

	// ---------------------------------------------------------------------
	// Atributos
	// ---------------------------------------------------------------------

	public static Cliente client;
	public static Principal principal;

	public static boolean onChat;

	private HiloActualizarChat hiloActualizar;
	private Thread hiloChat;
	
	private HiloEscucharChat hiloEscuchar;
	private Thread hiloServidor;

	// ---------------------------------------------------------------------
	// Constructor
	// ---------------------------------------------------------------------

	public Ejecutable() {
		// TODO Auto-generated constructor stub
		// Inicializo los objetos
		client = new Cliente();
		principal = new Principal();
		// Adiciono los listeners a la ventana principal
		// escuchaMiVentana();
		// Hago visible la ventana principal
		principal.setVisible(true);
		// LLamo al protocolo

		hiloActualizar = new HiloActualizarChat(client, this);
		
		hiloEscuchar = new HiloEscucharChat(client, this);

		escuchaLinea1();
	}
	// ---------------------------------------------------------------------
	// Metodos Linea 1
	// ---------------------------------------------------------------------

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

	public void escuchaLinea1() {
		principal.getBtnChat().addActionListener(
				new ActionListener() {
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
	
	public void escucharBotonEnviar()
	{
		principal.chat.getBtnEnviar().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String pregunta = principal.chat.getTextFieldMensaje().getText();
				principal.chat.getTextFieldMensaje().setText("");
				client.escribirChat(pregunta);
			}
		});
	}

	
	
	// ---------------------------------------------------------------------
	// Main
	// ---------------------------------------------------------------------

	public static void main(String[] args) {
		Ejecutable ejecutable = new Ejecutable();
	}
}
