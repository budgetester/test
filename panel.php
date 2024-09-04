<?php
session_start();

// Sprawdzenie, czy użytkownik jest zalogowany
if (!isset($_SESSION['uzytkownik_id'])) {
    header("Location: index.php"); // Przekierowanie do strony głównej, jeśli użytkownik nie jest zalogowany
    exit();
}

// Połączenie z bazą danych
$conn = new mysqli('localhost', 'root', '', 'test2');

// Sprawdzenie połączenia
if ($conn->connect_error) {
    die("Błąd połączenia: " . $conn->connect_error);
}

// Pobranie ID zalogowanego użytkownika
$uzytkownik_id = $_SESSION['uzytkownik_id'];

// Wyświetlanie kursów, na które zapisał się użytkownik wraz ze statusem opłacenia
echo "<h2>Twoje zapisane kursy i status płatności:</h2>";

$sql = "SELECT k.nazwa, p.status 
        FROM platnosci p
        JOIN kursy k ON p.kurs_id = k.id
        WHERE p.uzytkownik_id = '$uzytkownik_id'";

$result = $conn->query($sql);

if ($result->num_rows > 0) {
    while ($row = $result->fetch_assoc()) {
        echo "<p>Kurs: " . $row['nazwa'] . " - Status płatności: " . $row['status'] . "</p>";
    }
} else {
    echo "<p>Nie jesteś zapisany na żadne kursy.</p>";
}

// Obsługa zapisu na kurs (tylko dla zalogowanych użytkowników)
if (isset($_POST['action']) && $_POST['action'] == 'zapisz_sie_na_kurs') {
    $kurs_id = $_POST['kurs_id'];

    // Sprawdzenie, czy kurs istnieje
    $kursCheck = $conn->query("SELECT * FROM kursy WHERE id = '$kurs_id'");
    if ($kursCheck->num_rows > 0) {
        // Dodanie wpisu do tabeli platnosci
        $sql = "INSERT INTO platnosci (uzytkownik_id, kurs_id, status)
                VALUES ('$uzytkownik_id', '$kurs_id', 'nie zaplacono')";
        if ($conn->query($sql) === TRUE) {
            echo "Zapisano na kurs.";
        } else {
            echo "Błąd podczas zapisywania na kurs: " . $conn->error;
        }
    } else {
        echo "Wybrany kurs nie istnieje.";
    }
}

// Wyświetlanie kursów dostępnych do zapisu
echo "<h2>Dostępne kursy:</h2>";
$result = $conn->query("SELECT * FROM kursy");
while ($row = $result->fetch_assoc()) {
    echo "<p>Kurs: " . $row['nazwa'] . "<br>Opis: " . $row['opis'] . "<br>Cena: " . $row['cena'] . " zł</p>";
}

?>

<!-- Formularz zapisu na kurs (tylko dla zalogowanych użytkowników) -->
<h2>Zapisz się na kurs</h2>
<form method="POST" action="panel.php">
    <input type="hidden" name="action" value="zapisz_sie_na_kurs">
    <select name="kurs_id" required>
        <?php
        // Pobieranie kursów do wyboru
        $result = $conn->query("SELECT * FROM kursy");
        while ($row = $result->fetch_assoc()) {
            echo "<option value='" . $row['id'] . "'>" . $row['nazwa'] . " - " . $row['cena'] . " zł</option>";
        }
        ?>
    </select><br><br>
    <input type="submit" value="Zapisz się">
</form>

<!-- Dodaj opcję wylogowania -->
<form method="POST" action="wyloguj.php">
    <input type="submit" value="Wyloguj się">
</form>
