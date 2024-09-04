
import java.io.*;
import java.sql.*;

public class test4materialy
{
 public static void main(String[] args)
 {
  try
  {
   //Rozwi¹zanie problemu polskich znaków w konsoli systemu
   PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out,"Cp852"), true);

   //Za³adowanie sterownika
   Class.forName("org.sqlite.JDBC");
   pw.println("Za³adowano sterownik.");

   //Nawi¹zanie po³¹czenia z baz¹ danych SQLite znajduj¹c¹ siê w pliku filmy.db
   Connection connection = DriverManager.getConnection("jdbc:sqlite:filmy.db");
   pw.println("Utworzono po³¹czenie z baz¹.");

   //Utworzenie obiektu pozwalaj¹cego wysy³aæ polecenia do bazy danych
   Statement statement = connection.createStatement();
   pw.println("Utworzono obiekt do wysy³ania poleceñ do bazy.");

   //Utworzenie tabeli "Wykonawca"
   int resultInt1 = statement.executeUpdate("CREATE TABLE IF NOT EXISTS Wykonawca (Id INT NOT NULL PRIMARY KEY, Nazwisko VARCHAR(50) NOT NULL UNIQUE, Rok_urodzenia INT DEFAULT '0')");
   pw.println("Wys³ano polecenie do bazy -> Utworzono tabelê Wykonawca.");

   //Wype³nienie danymi tabeli "Wykonawca" w bazie danych
   resultInt1 = statement.executeUpdate("INSERT or REPLACE INTO Wykonawca VALUES (1, 'Maryla Rodowicz', 1945)");
   resultInt1 = statement.executeUpdate("INSERT or REPLACE INTO Wykonawca VALUES (2, 'Zbigniew Wodecki', 1950)");
   resultInt1 = statement.executeUpdate("INSERT or REPLACE INTO Wykonawca VALUES (3, 'Micha³ Bajor', 1957)");
   resultInt1 = statement.executeUpdate("INSERT or REPLACE INTO Wykonawca VALUES (4, 'Edyta Górniak', 1972)");
   pw.println("Wys³ano polecenie do bazy -> Wype³niono danymi tabelê Wykonawca.");

   //Utworzenie tabeli "Kompozytor"
   int resultInt2 = statement.executeUpdate("CREATE TABLE IF NOT EXISTS Kompozytor (Id INT NOT NULL PRIMARY KEY, Nazwisko VARCHAR(50) NOT NULL UNIQUE, Rok_urodzenia INT DEFAULT '0')");
   pw.println("Wys³ano polecenie do bazy -> Utworzono tabelê Kompozytor.");

   //Wype³nienie danymi tabeli "Kompozytor" w bazie danych
   resultInt2 = statement.executeUpdate("INSERT or REPLACE INTO Kompozytor VALUES (1, 'Andrzej Korzyñski', 1940)");
   resultInt2 = statement.executeUpdate("INSERT or REPLACE INTO Kompozytor VALUES (2, 'W³odzimierz Korcz', 1943)");
   resultInt2 = statement.executeUpdate("INSERT or REPLACE INTO Kompozytor VALUES (3, 'Stanis³aw Syrewicz', 1946)");
   resultInt2 = statement.executeUpdate("INSERT or REPLACE INTO Kompozytor VALUES (4, 'Wojciech Trzciñski', 1949)");
   pw.println("Wys³ano polecenie do bazy -> Wype³niono danymi tabelê Kompozytor.");
   
   //Utworzenie tabeli "Piosenka"
   int resultInt3 = statement.executeUpdate("CREATE TABLE IF NOT EXISTS Piosenka (Id INT NOT NULL PRIMARY KEY, Tytu³ VARCHAR(50) NOT NULL UNIQUE, Rok_premiery INT DEFAULT '0', Wykonawca VARCHAR(50) NOT NULL UNIQUE, Kompozytor VARCHAR(50) NOT NULL UNIQUE)");
   pw.println("Wys³ano polecenie do bazy -> Utworzono tabelê Piosenka.");

   //Wype³nienie danymi tabeli "Piosenka" w bazie danych
   resultInt3 = statement.executeUpdate("INSERT or REPLACE INTO Piosenka VALUES (1, 'Pieœñ ciszy', 1976, 'Zbigniew Wodecki', 'Wojciech Trzciñski')");
   resultInt3 = statement.executeUpdate("INSERT or REPLACE INTO Piosenka VALUES (2, 'Gimnastyka', 1983, 'Maryla Rodowicz', 'Andrzej Korzyñski')");
   resultInt3 = statement.executeUpdate("INSERT or REPLACE INTO Piosenka VALUES (3, 'To nie ja', 1994, 'Edyta Górniak', 'Stanis³aw Syrewicz')");
   resultInt3 = statement.executeUpdate("INSERT or REPLACE INTO Piosenka VALUES (4, 'Ogrzej mnie', 1999, 'Micha³ Bajor', 'W³odzimierz Korcz')");
   pw.println("Wys³ano polecenie do bazy -> Wype³niono danymi tabelê Piosenka.");

   //Odczytanie danych z tabeli "Wykonawca" w kolejnoœci rosn¹cej
   ResultSet resultSet1 = statement.executeQuery("SELECT * FROM Wykonawca ORDER BY Nazwisko ASC");
   pw.println();
   pw.println("Informacja o wykonawcach umieszczonych w bazie:");
   while(resultSet1.next())
   {
    String name = resultSet1.getString("Nazwisko");
    int year = resultSet1.getInt("Rok_urodzenia");
    pw.print(' ' + name + " urodzi³/-³a siê w ");
    pw.print(year);
    pw.println(" roku.");
   }
   
   //Odczytanie danych z tabeli "Kompozytor" w kolejnoœci rosn¹cej
   ResultSet resultSet2 = statement.executeQuery("SELECT * FROM Kompozytor ORDER BY Nazwisko ASC");
   pw.println();
   pw.println("Informacja o kompozytorach umieszczonych w bazie:");
   while(resultSet2.next())
   {
    String name = resultSet2.getString("Nazwisko");
    int year = resultSet2.getInt("Rok_urodzenia");
    pw.print(' ' + name + " urodzi³ siê w ");
    pw.print(year);
    pw.println(" roku.");
   }  
   
   //Odczytanie danych z tabeli "Piosenka" w kolejnoœci rosn¹cej
   ResultSet resultSet3 = statement.executeQuery("SELECT * FROM Piosenka ORDER BY Tytu³ ASC");
   pw.println();
   pw.println("Informacja o piosenkach umieszczonych w bazie:");
   while(resultSet3.next())
   {
    String name = resultSet3.getString("Tytu³");
    int year = resultSet3.getInt("Rok_premiery");
    pw.print(' ' + name + " zosta³a wydana w ");
    pw.print(year);
    pw.println(" roku.");
   }  

   //Zamkniêcie po³¹czenia z baz¹
   connection.close();
   pw.println("Zamkniêto po³¹czenie z baz¹.");
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