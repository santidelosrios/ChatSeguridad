package vista;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Principal extends JFrame {
	
	/// Atributos
	private JPanel contentPane;
	private JButton btnPreguntas;
	
	/// Constructor
	public Principal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 270);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("Chat seguro");
		btnPreguntas = new JButton("Iniciar Chat");
		btnPreguntas.setBounds(132, 75, 161, 23);
		contentPane.add(btnPreguntas);
	}

	public JPanel getContentPane() {
		return contentPane;
	}

	public JButton getBtnIniciar() {
		return btnPreguntas;
	}

}
