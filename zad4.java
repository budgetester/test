import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;


class zielinski_zad4 extends Frame implements ActionListener {
		TextArea tfInput = new TextArea("SELECT Film.Id, Film.Tytul, Film.'Rok produkcji', Rezyser.Nazwisko, Aktorka.Nazwisko "+
		"FROM Film INNER JOIN Rezyser ON Film.Rezyser = Rezyser.id INNER JOIN Aktorka ON Film.Aktorka = Aktorka.Id;");	
		Button b=new Button("Execute SQL!");
		TextArea tfOutput = new TextArea();  
    
    	public zielinski_zad4() {

	    tfInput.setBounds(50,50, 400,200);  
	    tfOutput.setBounds(50,50, 400,400);  
		add(tfInput, "North");
		add(b, "South");
		add(tfOutput, "Center");
		b.addActionListener(this);  

		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
				System.exit(0);
			}
		});
		
	}

	public static void main(String args[]) {
		System.out.println("Starting Cw8...");
		zielinski_zad4 mainFrame = new zielinski_zad4();
		mainFrame.setSize(800, 400);
		mainFrame.setTitle("Cw8");
		mainFrame.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e)
	{  
		try
		{
			Socket s = new Socket("localhost", 4441);
			OutputStreamWriter osw = new OutputStreamWriter(s.getOutputStream(), "UTF-8");
			InputStreamReader isr = new InputStreamReader(s.getInputStream(), "UTF-8");

			String str = tfInput.getText() + "\n";
			osw.write(str, 0, str.length());
			osw.flush();

			BufferedReader br = new BufferedReader(isr);
			String out = "";
			
			StringBuilder content = new StringBuilder();
    
		    while ((str = br.readLine()) != null) {
		        content.append(str);
				content.append(System.lineSeparator());
		    }

	
			tfOutput.setText(content.toString());  
		}
		catch (UnknownHostException e1)
		{
			System.out.println(e1.getMessage());
		}
		catch (IOException e2)
		{
			System.out.println(e2.getMessage());
		}
	}  
}
