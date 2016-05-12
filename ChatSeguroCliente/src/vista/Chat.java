package vista;

import java.awt.Container;
import java.awt.TextField;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

public class Chat extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	/// Atributos
	private JPanel contentPane;
	private JButton btnEnviar;
	private TextField textFieldMensaje;
	private JTextPane textPane;
	private JScrollPane scrollPaneChat;
	
	/// Constructor
	public Chat() {
		setResizable(false);
		setTitle("Chat Seguro Cliente");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		btnEnviar = new JButton("Enviar");
		btnEnviar.setBounds(348, 374, 92, 23);
		contentPane.add(btnEnviar);
		textFieldMensaje = new TextField();
		textFieldMensaje.setBounds(77, 311, 363, 58);
		contentPane.add(textFieldMensaje);
		scrollPaneChat = new JScrollPane();
		scrollPaneChat.setBounds(77, 40, 363, 250);
		contentPane.add(scrollPaneChat);	
		textPane = new JTextPane();
		scrollPaneChat.setViewportView(textPane);
	}
	
	public Container getContentPane() {
		return contentPane;
	}
	public JButton getBtnEnviar() {
		return btnEnviar;
	}
	public JTextPane getTextPane() {
		return textPane;
	}
	public void setTextPane(JTextPane textPane) {
		this.textPane = textPane;
	}
	public TextField getTextFieldMensaje() {
		return textFieldMensaje;
	}
	public void setTextFieldMensaje(TextField textFieldMensaje) {
		this.textFieldMensaje = textFieldMensaje;
	}
}
