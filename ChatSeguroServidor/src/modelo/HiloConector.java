package modelo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.KeyAgreement;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.DHParameterSpec;

import control.Ejecutable;
import control.HiloEscucharChat;

public class HiloConector implements Runnable {
	
	/// Atributos
	private Servidor server;
	private Ejecutable exe;
	private boolean close;
	
	/// Constructor
	public HiloConector (Servidor server, Ejecutable exe){
		this.server = server;
		this.exe = exe;
		close = false;
	}
	
	///Metodos
	public void connect() 
	{
		while(!close)
		{
			try 
			{
				Socket actualSock = server.getServer().accept();
				DataInputStream entrada = new DataInputStream(actualSock.getInputStream());
				DataOutputStream salida = new DataOutputStream(actualSock.getOutputStream());
				String recibirCliente = entrada.readUTF();
				if(recibirCliente.equals("Hola")){
					salida.writeUTF("Ok");
					
				}
				recibirCliente= entrada.readUTF();
				if(recibirCliente.equals("iniciar")){
						salida.writeUTF("Ok");
				}
				String txt = entrada.readUTF();
				if(txt.equals("chat"))
				{
					Cliente client = new Cliente(actualSock, entrada, salida);
					server.agregarEstudianteLinea1(client);
					HiloEscucharChat hilo = new HiloEscucharChat(exe, client);
					Thread hiloEst = new Thread(hilo);
					hiloEst.start();
				}
			} 
			
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				close = true;
			}
			
		}
	}
	
	public void doDiffieHellman(DataInputStream entrada, DataOutputStream salida) throws IOException {
		//Se reciben los parametros G,P y L del cliente
		String paramMessage = entrada.readUTF();
		System.out.println("ParamMessageS:" + paramMessage);
		String [] params = paramMessage.split(";");
		String sg = params[0];
		String sp = params[1];
		String sl = params[2];
		BigInteger g = new BigInteger(sg);
		BigInteger p = new BigInteger(sp);
		int l = new Integer(sl);
		//Se recibe la clave publica del cliente
		String publicKey = entrada.readUTF();
		System.out.println("PublicKey del cliente: " + publicKey);
		byte [] clientKey = publicKey.getBytes();
		//Se procede a crear la llave publica y privada del servidor
		String algorithm = "DH";
		try {
			KeyPairGenerator keyG = KeyPairGenerator.getInstance(algorithm);
			DHParameterSpec dhSpec = new DHParameterSpec(p, g, l);
			keyG.initialize(dhSpec);
			KeyPair pair = keyG.generateKeyPair();
			PublicKey pk = pair.getPublic(); 
			byte [] bpk = pk.getEncoded();
			String spk = bpk.toString();
			//Se envia la llave publica al cliente
			salida.writeUTF(spk);
			//Se procede con la negociacion de clave y la generacion de la clave secreta
			KeyAgreement ka = KeyAgreement.getInstance(algorithm);
			PrivateKey ppk = pair.getPrivate();
			ka.init(ppk);
			KeyFactory kf = KeyFactory.getInstance(algorithm);
			X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(clientKey);
			PublicKey cpk = kf.generatePublic(x509EncodedKeySpec);
			ka.doPhase(cpk, true);
			
			SecretKey sk = ka.generateSecret("AES");
			
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void close()
	{
		close = true;
	}
	
	public void run() {
		// TODO Auto-generated method stub
		connect();
	}
}
