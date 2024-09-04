import java.sql.*;
import java.io.File;

public class test
{
	public static void main(String[] args)
	{
		String fileName = "filmy.db";
        System.out.println("Working on db file: "+fileName);

		//usuwamy stary plik zeby latwo sie testowalo
		File f = new File (fileName);
        System.out.println("Deleting old file...");
		f.delete();
		
		Connection conn = null;

		try
		{
	        System.out.println("Creating new file...");
	        //ladujemy sterownik i podlaczamy sie do pliku, jesli plik nie istnieje, to tworzymy
			conn = DriverManager.getConnection("jdbc:sqlite:"+fileName);
            
            Statement stmt = conn.createStatement();
            String sql = "";

	        System.out.println("Creating db schema...");

			//uruchamiamy po kolei polecenia tworzace schemat bazy
            sql = "CREATE TABLE IF NOT EXISTS Aktorka (Id INTEGER PRIMARY KEY AUTOINCREMENT, "+
            "Nazwisko TEXT, "+
            "'Rok urodzenia' DATE);";
	        System.out.println(" > executing sql: "+sql);
            stmt.execute(sql);
            
            sql = "CREATE TABLE IF NOT EXISTS Rezyser (Id INTEGER PRIMARY KEY AUTOINCREMENT, "+
            "Nazwisko TEXT, "+
            "'Rok urodzenia' DATE);";
	        System.out.println(" > executing sql: "+sql);
            stmt.execute(sql);
            
            sql = "CREATE TABLE IF NOT EXISTS Film (Id INTEGER PRIMARY KEY AUTOINCREMENT, "+
            "Tytul TEXT, "+
            "'Rok produkcji' DATE, "+
            "Rezyser INTEGER NOT NULL, "+
            "Aktorka INTEGER NOT NULL,"+
            "FOREIGN KEY (Rezyser) REFERENCES Rezyser (Id), "+
            "FOREIGN KEY (Aktorka) REFERENCES Aktorka (Id));";
            
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

