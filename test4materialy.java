
import java.io.*;
import java.sql.*;

public class test4materialy
{
 public static void main(String[] args)
 {
  try
  {
   //Rozwi�zanie problemu polskich znak�w w konsoli systemu
   PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out,"Cp852"), true);

   //Za�adowanie sterownika
   Class.forName("org.sqlite.JDBC");
   pw.println("Za�adowano sterownik.");

   //Nawi�zanie po��czenia z baz� danych SQLite znajduj�c� si� w pliku filmy.db
   Connection connection = DriverManager.getConnection("jdbc:sqlite:filmy.db");
   pw.println("Utworzono po��czenie z baz�.");

   //Utworzenie obiektu pozwalaj�cego wysy�a� polecenia do bazy danych
   Statement statement = connection.createStatement();
   pw.println("Utworzono obiekt do wysy�ania polece� do bazy.");

   //Utworzenie tabeli "Wykonawca"
   int resultInt1 = statement.executeUpdate("CREATE TABLE IF NOT EXISTS Wykonawca (Id INT NOT NULL PRIMARY KEY, Nazwisko VARCHAR(50) NOT NULL UNIQUE, Rok_urodzenia INT DEFAULT '0')");
   pw.println("Wys�ano polecenie do bazy -> Utworzono tabel� Wykonawca.");

   //Wype�nienie danymi tabeli "Wykonawca" w bazie danych
   resultInt1 = statement.executeUpdate("INSERT or REPLACE INTO Wykonawca VALUES (1, 'Maryla Rodowicz', 1945)");
   resultInt1 = statement.executeUpdate("INSERT or REPLACE INTO Wykonawca VALUES (2, 'Zbigniew Wodecki', 1950)");
   resultInt1 = statement.executeUpdate("INSERT or REPLACE INTO Wykonawca VALUES (3, 'Micha� Bajor', 1957)");
   resultInt1 = statement.executeUpdate("INSERT or REPLACE INTO Wykonawca VALUES (4, 'Edyta G�rniak', 1972)");
   pw.println("Wys�ano polecenie do bazy -> Wype�niono danymi tabel� Wykonawca.");

   //Utworzenie tabeli "Kompozytor"
   int resultInt2 = statement.executeUpdate("CREATE TABLE IF NOT EXISTS Kompozytor (Id INT NOT NULL PRIMARY KEY, Nazwisko VARCHAR(50) NOT NULL UNIQUE, Rok_urodzenia INT DEFAULT '0')");
   pw.println("Wys�ano polecenie do bazy -> Utworzono tabel� Kompozytor.");

   //Wype�nienie danymi tabeli "Kompozytor" w bazie danych
   resultInt2 = statement.executeUpdate("INSERT or REPLACE INTO Kompozytor VALUES (1, 'Andrzej Korzy�ski', 1940)");
   resultInt2 = statement.executeUpdate("INSERT or REPLACE INTO Kompozytor VALUES (2, 'W�odzimierz Korcz', 1943)");
   resultInt2 = statement.executeUpdate("INSERT or REPLACE INTO Kompozytor VALUES (3, 'Stanis�aw Syrewicz', 1946)");
   resultInt2 = statement.executeUpdate("INSERT or REPLACE INTO Kompozytor VALUES (4, 'Wojciech Trzci�ski', 1949)");
   pw.println("Wys�ano polecenie do bazy -> Wype�niono danymi tabel� Kompozytor.");
   
   //Utworzenie tabeli "Piosenka"
   int resultInt3 = statement.executeUpdate("CREATE TABLE IF NOT EXISTS Piosenka (Id INT NOT NULL PRIMARY KEY, Tytu� VARCHAR(50) NOT NULL UNIQUE, Rok_premiery INT DEFAULT '0', Wykonawca VARCHAR(50) NOT NULL UNIQUE, Kompozytor VARCHAR(50) NOT NULL UNIQUE)");
   pw.println("Wys�ano polecenie do bazy -> Utworzono tabel� Piosenka.");

   //Wype�nienie danymi tabeli "Piosenka" w bazie danych
   resultInt3 = statement.executeUpdate("INSERT or REPLACE INTO Piosenka VALUES (1, 'Pie�� ciszy', 1976, 'Zbigniew Wodecki', 'Wojciech Trzci�ski')");
   resultInt3 = statement.executeUpdate("INSERT or REPLACE INTO Piosenka VALUES (2, 'Gimnastyka', 1983, 'Maryla Rodowicz', 'Andrzej Korzy�ski')");
   resultInt3 = statement.executeUpdate("INSERT or REPLACE INTO Piosenka VALUES (3, 'To nie ja', 1994, 'Edyta G�rniak', 'Stanis�aw Syrewicz')");
   resultInt3 = statement.executeUpdate("INSERT or REPLACE INTO Piosenka VALUES (4, 'Ogrzej mnie', 1999, 'Micha� Bajor', 'W�odzimierz Korcz')");
   pw.println("Wys�ano polecenie do bazy -> Wype�niono danymi tabel� Piosenka.");

   //Odczytanie danych z tabeli "Wykonawca" w kolejno�ci rosn�cej
   ResultSet resultSet1 = statement.executeQuery("SELECT * FROM Wykonawca ORDER BY Nazwisko ASC");
   pw.println();
   pw.println("Informacja o wykonawcach umieszczonych w bazie:");
   while(resultSet1.next())
   {
    String name = resultSet1.getString("Nazwisko");
    int year = resultSet1.getInt("Rok_urodzenia");
    pw.print(' ' + name + " urodzi�/-�a si� w ");
    pw.print(year);
    pw.println(" roku.");
   }
   
   //Odczytanie danych z tabeli "Kompozytor" w kolejno�ci rosn�cej
   ResultSet resultSet2 = statement.executeQuery("SELECT * FROM Kompozytor ORDER BY Nazwisko ASC");
   pw.println();
   pw.println("Informacja o kompozytorach umieszczonych w bazie:");
   while(resultSet2.next())
   {
    String name = resultSet2.getString("Nazwisko");
    int year = resultSet2.getInt("Rok_urodzenia");
    pw.print(' ' + name + " urodzi� si� w ");
    pw.print(year);
    pw.println(" roku.");
   }  
   
   //Odczytanie danych z tabeli "Piosenka" w kolejno�ci rosn�cej
   ResultSet resultSet3 = statement.executeQuery("SELECT * FROM Piosenka ORDER BY Tytu� ASC");
   pw.println();
   pw.println("Informacja o piosenkach umieszczonych w bazie:");
   while(resultSet3.next())
   {
    String name = resultSet3.getString("Tytu�");
    int year = resultSet3.getInt("Rok_premiery");
    pw.print(' ' + name + " zosta�a wydana w ");
    pw.print(year);
    pw.println(" roku.");
   }  

   //Zamkni�cie po��czenia z baz�
   connection.close();
   pw.println("Zamkni�to po��czenie z baz�.");
  }
  catch(UnsupportedEncodingException uee)
  {
   System.err.print("UnsupportedEncoding Exception: ");
   System.err.println(uee.getMessage());
  }
  catch(ClassNotFoundException cnfe)
  {
   System.err.print("ClassNotFound Exception: ");
   System.err.println(cnfe.getMessage());
  }
   catch(SQLException sqle)
  {
   System.err.print("SQL Exception: ");
   System.err.println(sqle.getMessage());
  }
  catch(Exception e)
  {
   System.out.print("Exception: ");
   System.out.println(e.getMessage());
  }
 }
}