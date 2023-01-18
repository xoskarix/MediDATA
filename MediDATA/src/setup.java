package MediDATA;


import java.io.*;
import java.util.Scanner;
import java.security.NoSuchAlgorithmException;


// hasło bezpieczeństwa: Ps9!kmLn5

public class setup {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        Scanner s = new Scanner(System.in);
        System.out.println("Witaj w kreatorze konfiguracji MediDATA!");
        System.out.println("Wybierz jedną z dostępnych opcji: ");
        System.out.println("UWAGA: JEŚLI JEST TO TWOJE PIERWSZE KORZYSTANIE Z SYSTEMU, WYBIERZ OPCJĘ NR 1");
        System.out.println("1. Utwórz konto administratora");
        System.out.println("2. Edytuj konta pracowników");
        int choice = 0;
        while(choice !=1 && choice != 2) {
            choice = s.nextInt();
            switch (choice) {
                case 1 : utworzAdm(); break;
                case 2 : edytujKonta(); break;
                default : System.out.println("Nieprawidłowa opcja!"); break;
            }
        }
    }
    public static void utworzAdm() {
        Scanner s = new Scanner(System.in);
        System.out.println("Podaj hasło bezpieczeństwa, które otrzymałeś wraz z mailem potwierdzającym zakup produktu.");
        String pass = s.nextLine();
        try {
            String hash = SHA256.hash_str(SHA256.hashuj(pass));
            if(hash.equals("c58827c3a132a36765ffda3e6ffbf411f4a0c89ba13c830144f5d8f0ffb71df7")) { // sprawdza czy hash podanego hasla zgadza sie z haslem bezpieczenstwa
                System.out.println("Hasło poprawne.");
                File konta = new File("acc");
                if (konta.createNewFile()) { // w wypadku gdyby plik z kontami nie istnial w folderze
                    System.out.println("Nie znaleziono pliku acc. Upewnij się, że nie został on zmodyfikowany ręcznie ani nie został przeniesiony.");
                    System.out.println("Utworzono nowy plik acc.");
                }
                // samo tworzenie konta
                System.out.println("Podaj nową nazwę administratora: ");
                String new_username = s.nextLine();
                BufferedReader br = new BufferedReader(new FileReader("acc"));
                String line;
                while((line = br.readLine()) != null) {
                    if(line.contains(new_username)) {
                        System.out.println("Istnieje już użytkownik o takiej nazwie. Aplikacja zakończy działanie.");
                        System.out.println("Naciśnij ENTER, aby kontynuować...");
                        s.nextLine();
                        System.exit(0);
                    }
                }
                System.out.println("Podaj nowe hasło: ");
                String pass_hash = SHA256.hash_str(SHA256.hashuj(s.nextLine()));
                BufferedWriter writer = new BufferedWriter(new FileWriter("acc", true));
                writer.append("\n"+"admin:"+new_username+":"+pass_hash);
                writer.close();
                System.out.println("Pomyślnie dodano nowe konto administratora.");
                System.out.println("Naciśnij ENTER, aby kontynuować...");
                s.nextLine();
                System.exit(0);

            } else {
                System.out.println("Niepoprawne hasło bezpieczeństwa. Aplikacja zakończy działanie.");
                System.out.println("Naciśnij ENTER, aby kontynuować...");
                s.nextLine();
                System.exit(-1);
            }
        } catch(NoSuchAlgorithmException wyjatek) {
            System.out.println("Wystąpił niespodziewany błąd. Nieznany algorytm szyfrowania: " + wyjatek);
            System.out.println("Naciśnij ENTER, aby kontynuować...");
            s.nextLine();
            System.exit(-1);
        } catch (IOException e) {
            System.out.println("Wystąpił błąd z plikiem acc. Upewnij się, że posiadasz odpowiednie uprawnienia.");
            System.out.println("Naciśnij ENTER, aby kontynuować...");
            System.out.println(e);
//            s.nextLine();
//            System.exit(-1);
        }

    }
    public static void edytujKonta() {

        Scanner s = new Scanner(System.in);
        System.out.println("Podaj login konta administratora: ");
        String username = s.nextLine();
        System.out.println("Podaj hasło: ");
        String pass = s.nextLine();
        try {
            String hash = SHA256.hash_str(SHA256.hashuj(pass));
            BufferedReader br = new BufferedReader(new FileReader("acc"));
            String line;
            boolean acc_exists = false;
            while((line = br.readLine()) != null) {
                if(line.equals("admin:"+username+":"+hash)) {
                    acc_exists = true;
                    System.out.println("Pomyślnie zalogowano.");
                    System.out.println("Jaki typ konta chcesz dodać? (lekarz/pielęgniarka)");
                    String typ = s.nextLine();
                    if(!typ.equals("lekarz") && !typ.equals("pielęgniarka")) {
                        System.out.println("Nieprawidłowy typ konta.");
                        System.out.println("Naciśnij ENTER, aby kontynuować...");
                        s.nextLine();
                        System.exit(-1);
                    }
                    System.out.println("Podaj nazwę użytkownika:");
                    String new_username = s.nextLine();
                    while((line = br.readLine()) != null) {
                        if(line.contains(new_username)) {
                            System.out.println("Istnieje już użytkownik o takiej nazwie. Aplikacja zakończy działanie.");
                            System.out.println("Naciśnij ENTER, aby kontynuować...");
                            s.nextLine();
                            System.exit(0);
                        }
                    }
                    System.out.println("Podaj nowe hasło: ");
                    String pass_hash = SHA256.hash_str(SHA256.hashuj(s.nextLine()));
                    BufferedWriter writer = new BufferedWriter(new FileWriter("acc", true));
                    writer.append("\n"+typ+":"+new_username+":"+pass_hash);
                    writer.close();
                    System.out.println("Pomyślnie dodano nowe konto.");
                    System.out.println("Naciśnij ENTER, aby kontynuować...");
                    s.nextLine();
                    System.exit(0);


                }
            }
            if(!acc_exists) System.out.println("Nieprawidłowa nazwa użytkownika lub hasło.");
            System.out.println("Naciśnij ENTER, aby kontynuować...");
            s.nextLine();
            System.exit(-1);


        } catch(NoSuchAlgorithmException wyjatek) {
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
