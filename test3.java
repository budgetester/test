import java.io.*;
import java.net.*;
import java.sql.*;

//server dla pobierania danych z bazy sqlite
public class test3
{
	public static void main(String[] args)
	{
		String fileName = "filmy.db";
        System.out.println("Working on db file: "+fileName);
		Connection conn = null;
		Statement stmt = null;
		
		try
		{
	        System.out.println("Connecting to file...");
			conn = DriverManager.getConnection("jdbc:sqlite:"+fileName);
            stmt = conn.createStatement();
        }
		catch (SQLException e)
		{
			System.out.println(e.getMessage());
		}

		try
		{
			ServerSocket ss = new ServerSocket(4441);
        	System.out.println("Listening on port 4441.");
			while(true)
			{
				Socket s = ss.accept();
				BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
				OutputStreamWriter osw = new OutputStreamWriter(s.getOutputStream(), "UTF-8");
				String cmd = br.readLine();
				System.out.println("> recv: "+cmd);
          
		        System.out.println(" > executing sql: "+cmd);
				String response = "";
				        
		        try
		        {
           			ResultSet rs = stmt.executeQuery(cmd);
					ResultSetMetaData rsmd = rs.getMetaData();
					int columnsNumber = rsmd.getColumnCount();					
					
					while (rs.next()) {
						for (int i = 1; i <= columnsNumber; i++) {
					        if (i > 1) response += ",  ";
        					response += rs.getString(i);
					    }
                        response += "\n\r";
            		}
				}
				catch (SQLException e)
				{
					System.out.println(e.getMessage());
					response = e.getMessage();
				}
		        System.out.println(" > sending response: "+response);
				osw.write(response, 0, response.length());
				osw.flush();
					
				br.close();
				s.close();
			}
		}
		catch (IOException e)
		{
			System.out.println(e.getMessage());
		}

		try
		{
			conn.close();
		}
		catch (SQLException e)
		{
			System.out.println(e.getMessage());
		}

	}
}
