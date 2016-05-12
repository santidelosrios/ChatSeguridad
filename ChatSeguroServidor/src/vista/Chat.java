package vista;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

public class Chat extends JFrame{
	
	/// Atributos
	private JButton btnEnviar;
	private JTextField textFieldMensaje;
	private JTextPane textPane;
	private JScrollPane scrollPaneChat;
	private JPanel contentPane;
	
	/// Constructor
	public Chat() {
	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	setBounds(100, 80, 560, 486);
	setTitle("Chat Seguro");
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(contentPane);
	contentPane.setLayout(null);
	btnEnviar = new JButton("Enviar");
	btnEnviar.setBounds(348, 374, 92, 23);
	add(btnEnviar);
	textFieldMensaje = new JTextField();
	textFieldMensaje.setBounds(77, 311, 363, 58);
	add(textFieldMensaje);
	textFieldMensaje.setColumns(10);
	scrollPaneChat = new JScrollPane();
	scrollPaneChat.setBounds(77, 40, 363, 250);
	add(scrollPaneChat);
	textPane = new JTextPane();
	scrollPaneChat.setViewportView(textPane);
	}
	
	public void actualizarChat(String chat)
	{
		textPane.setText(chat);
	}

	public JButton getBtnEnviar() {
		return btnEnviar;
	}

	public void setBtnEnviar(JButton btnEnviar) {
		this.btnEnviar = btnEnviar;
	}

	public JTextField getTextFieldMensaje() {
		return textFieldMensaje;
	}

	public void setTextFieldMensaje(JTextField textFieldMensaje) {
		this.textFieldMensaje = textFieldMensaje;
	}
	
}
