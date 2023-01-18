package MediDATA;
import java.io.*;
import java.util.Scanner;
import java.security.NoSuchAlgorithmException;

public class login {
    public static String wybranyTyp;
    public static String nazwa;

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("LOGOWANIE\nWybierz jedną z opcji: ");
        System.out.println("1. Lekarz");
        System.out.println("2. Pielęgniarka");
        String choice = s.nextLine();
        switch (choice) {
            case "1" : wybranyTyp = "lekarz"; break;
            case "2" : wybranyTyp = "pielęgniarka"; break;
            default : {
                System.out.println("Nieprawidłowa opcja!");
                System.out.println("Naciśnij ENTER, aby kontynuować...");
                s.nextLine();
                System.exit(-1);
            }
        }
        System.out.println("Podaj login: ");
        String username = s.nextLine();
        System.out.println("Podaj hasło: ");
        String pass = s.nextLine();
        try {
            String hash = SHA256.hash_str(SHA256.hashuj(pass));
            BufferedReader br = new BufferedReader(new FileReader("acc"));
            String line;
            boolean acc_exists = false;
            while ((line = br.readLine()) != null) {
                if (line.equals(wybranyTyp +":" + username + ":" + hash)) {
                    acc_exists = true;
                    nazwa = username;
                    System.out.println("Pomyślnie zalogowano.");
                    baza.edytujBaze(username, wybranyTyp);
                }
            }
            if (!acc_exists) {
                System.out.println("Nieprawidłowe dane logowania!");
                System.out.println("Naciśnij ENTER, aby kontynuować...");
                s.nextLine();
                System.exit(-1);
            }
        } catch (NoSuchAlgorithmException wyjatek) {
            System.out.println("Wystąpił niespodziewany błąd. Nieznany algorytm szyfrowania: " + wyjatek);
            System.out.println("Naciśnij ENTER, aby kontynuować...");
            s.nextLine();
            System.exit(-1);
        } catch (FileNotFoundException e) {
            System.out.println("Nie odnaleziono pliku acc. Upewnij się, że nie został on zmodyfikowany ręcznie ani nie został przeniesiony.");
            System.out.println("Naciśnij ENTER, aby kontynuować...");
            s.nextLine();
            System.exit(-1);
        } catch (IOException e) {
            System.out.println("Wystąpił błąd z plikiem acc. Upewnij się, że posiadasz odpowiednie uprawnienia.");
            System.out.println("Naciśnij ENTER, aby kontynuować...");
            s.nextLine();
            System.exit(-1);
        }
    }
}

