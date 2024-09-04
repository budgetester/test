import java.sql.*;

public class test2
{
	public static void main(String[] args)
	{
		String fileName = "filmy.db";
        System.out.println("Working on db file: "+fileName);
		Connection conn = null;

		try
		{
	        System.out.println("Connecting to file...");
			conn = DriverManager.getConnection("jdbc:sqlite:"+fileName);
            
            Statement stmt = conn.createStatement();
            String sql = "";
            
            sql = "INSERT INTO Aktorka (Nazwisko, 'Rok urodzenia') VALUES ('Michelle Pfeiffer', 1958), ('Radha Mitchell', 1973), "+
            		"('Miranda Otto', 1967), ('Amy Adams', 1974), ('Sigourney Weaver', 1949);";
	        System.out.println(" > executing sql: "+sql);
            stmt.execute(sql);
            
            sql = "INSERT INTO Rezyser (Nazwisko, 'Rok urodzenia') VALUES ('Richard Donner',1930), ('Petter Naess', 1960), "+
            		"('RObert Zemeckis', 1952), ('Peter Jackson', 1961), ('James Cameron', 1954);"; 
	        System.out.println(" > executing sql: "+sql);
            stmt.execute(sql);
            
            
            sql = "INSERT INTO Film (Tytul, 'Rok produkcji', Rezyser, Aktorka) VALUES ('Zakleta w sokola', 1985, 1, 1), "+
            "('Zaklete serca', 2005, 2, 2), ('Co kryje prawda', 2000, 3, 1), ('Wladca pierscieni - Powrot Krola', 2003, 4, 3), "+
            "('Aliens', 1986, 5, 5);";
	        System.out.println(" > executing sql: "+sql);
            stmt.execute(sql);
        }
		catch (SQLException e)
		{
			System.out.println(e.getMessage());
		}
		finally
		{
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
}
