package vista;

import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Principal extends JFrame{
	
	/// Atributos
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnChat;
	public Chat chat;

	/// Constructor
	public Principal() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("Chat Seguro");
		chat = new Chat();
		btnChat = new JButton("Iniciar");
		btnChat.setBounds(145, 59, 150, 23);
		contentPane.add(btnChat);
	}

	public void actualizarChat(String Mensajes){
		chat.getTextPane().setText(Mensajes);
	}
	
	public Container getContentPane(){
		return contentPane;
	}
	
	public JButton getBtnChat(){
		return btnChat;
	}
	
	public Chat getChat(){
		return chat;
	}
}
