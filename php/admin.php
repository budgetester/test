<?php
session_start();

// Połączenie z bazą danych
$conn = new mysqli('localhost', 'root', '', 'test2');

// Sprawdzenie połączenia
if ($conn->connect_error) {
    die("Błąd połączenia: " . $conn->connect_error);
}

// Obsługa rejestracji użytkownika na kurs
if (isset($_POST['action']) && $_POST['action'] == 'register') {
    $imie = $_POST['imie'];
    $nazwisko = $_POST['nazwisko'];
    $rok_urodzenia = $_POST['rok_urodzenia'];
    $login = $_POST['login'];
    $haslo = password_hash($_POST['haslo'], PASSWORD_DEFAULT);
    $kurs_id = $_POST['kurs_id']; // Id kursu, na który użytkownik się zapisuje

    // Rejestracja użytkownika
    $sql = "INSERT INTO uzytkownicy (imie, nazwisko, rok_urodzenia, login, haslo, typ)
            VALUES ('$imie', '$nazwisko', '$rok_urodzenia', '$login', '$haslo', 'uczestnik')";
    
    if ($conn->query($sql) === TRUE) {
        $uzytkownik_id = $conn->insert_id;  // Pobranie ID nowego użytkownika
        
        // Dodanie wpisu do tabeli platnosci
        $sql2 = "INSERT INTO platnosci (uzytkownik_id, kurs_id, status)
                 VALUES ('$uzytkownik_id', '$kurs_id', 'nie zaplacono')";
        if ($conn->query($sql2) === TRUE) {
            echo "Rejestracja i zapisanie na kurs zakończone pomyślnie.";
        } else {
            echo "Błąd podczas zapisywania na kurs.";
        }
    } else {
        echo "Błąd: " . $sql . "<br>" . $conn->error;
    }
}

// Obsługa logowania
if (isset($_POST['action']) && $_POST['action'] == 'login') {
    $login = $_POST['login'];
    $haslo = $_POST['haslo'];

    $sql = "SELECT * FROM uzytkownicy WHERE login='$login'";
    $result = $conn->query($sql);

    if ($result->num_rows > 0) {
        $row = $result->fetch_assoc();
        if (password_verify($haslo, $row['haslo'])) {
            $_SESSION['uzytkownik_id'] = $row['id'];
            $_SESSION['typ'] = $row['typ'];
            echo "Zalogowano pomyślnie jako " . $row['typ'] . ".";
        } else {
            echo "Błędne hasło.";
        }
    } else {
        echo "Użytkownik nie istnieje.";
    }
}

// Wyświetlanie dostępnych kursów
$result = $conn->query("SELECT * FROM kursy");
echo "<h2>Dostępne kursy:</h2>";
echo "<form method='POST' action='index.php'>";
while ($row = $result->fetch_assoc()) {
    echo "<input type='radio' name='kurs_id' value='".$row['id']."'> " . $row['nazwa'] . " - " . $row['opis'] . " - " . $row['cena'] . " zł<br>";
}
?>

<!-- Formularz rejestracji -->
<h2>Rejestracja</h2>
<form method="POST" action="index.php">
    <input type="hidden" name="action" value="register">
    Imię: <input type="text" name="imie" required><br>
    Nazwisko: <input type="text" name="nazwisko" required><br>
    Rok urodzenia: <input type="number" name="rok_urodzenia" required><br>
    Login: <input type="text" name="login" required><br>
    Hasło: <input type="password" name="haslo" required><br>
    <input type="submit" value="Zarejestruj się">
</form>

<!-- Formularz logowania -->
<h2>Logowanie</h2>
<form method="POST" action="index.php">
    <input type="hidden" name="action" value="login">
    Login: <input type="text" name="login" required><br>
    Hasło: <input type="password" name="haslo" required><br>
    <input type="submit" value="Zaloguj">
</form>

<?php
// Sprawdzanie statusu płatności po zalogowaniu uczestnika
if (isset($_SESSION['uzytkownik_id']) && $_SESSION['typ'] == 'uczestnik') {
    $uzytkownik_id = $_SESSION['uzytkownik_id'];

    // Pobieranie statusu płatności
    $sql = "SELECT k.nazwa, p.status FROM platnosci p
            JOIN kursy k ON p.kurs_id = k.id
            WHERE p.uzytkownik_id = '$uzytkownik_id'";
    $result = $conn->query($sql);

    echo "<h2>Twoje kursy i status płatności:</h2>";
    while ($row = $result->fetch_assoc()) {
        echo "Kurs: " . $row['nazwa'] . " - Status płatności: " . $row['status'] . "<br>";
    }
}
?>
