<?php
session_start();

// Jeśli użytkownik jest zalogowany, przekieruj go do panelu
if (isset($_SESSION['uzytkownik_id'])) {
    header("Location: panel.php");
    exit();
}

// Połączenie z bazą danych
$conn = new mysqli('localhost', 'root', '', 'test2');

// Sprawdzenie połączenia
if ($conn->connect_error) {
    die("Błąd połączenia: " . $conn->connect_error);
}

// Obsługa rejestracji użytkownika
if (isset($_POST['action']) && $_POST['action'] == 'register') {
    $imie = $_POST['imie'];
    $nazwisko = $_POST['nazwisko'];
    $rok_urodzenia = $_POST['rok_urodzenia'];
    $login = $_POST['login'];
    $haslo = password_hash($_POST['haslo'], PASSWORD_DEFAULT);

    // Rejestracja użytkownika
    $sql = "INSERT INTO uzytkownicy (imie, nazwisko, rok_urodzenia, login, haslo, typ)
            VALUES ('$imie', '$nazwisko', '$rok_urodzenia', '$login', '$haslo', 'uczestnik')";
    
    if ($conn->query($sql) === TRUE) {
        echo "Rejestracja zakończona pomyślnie. Teraz możesz się zalogować.";
    } else {
        echo "Błąd: " . $sql . "<br>" . $conn->error;
    }
}

// Obsługa logowania użytkownika
if (isset($_POST['action']) && $_POST['action'] == 'login') {
    $login = $_POST['login'];
    $haslo = $_POST['haslo'];

    $sql = "SELECT * FROM uzytkownicy WHERE login='$login'";
    $result = $conn->query($sql);

    if ($result->num_rows > 0) {
        $row = $result->fetch_assoc();
        if (password_verify($haslo, $row['haslo'])) {
            // Ustawienie sesji i przekierowanie do panelu użytkownika
            $_SESSION['uzytkownik_id'] = $row['id'];
            $_SESSION['typ'] = $row['typ'];
            header("Location: panel.php");
            exit();
        } else {
            echo "Błędne hasło.";
        }
    } else {
        echo "Użytkownik nie istnieje.";
    }
}

// Wyświetlanie kursów dla niezalogowanych użytkowników
$result = $conn->query("SELECT * FROM kursy");
echo "<h2>Dostępne kursy:</h2>";
while ($row = $result->fetch_assoc()) {
    echo "<p>Kurs: " . $row['nazwa'] . "<br>Opis: " . $row['opis'] . "<br>Cena: " . $row['cena'] . " zł</p>";
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
