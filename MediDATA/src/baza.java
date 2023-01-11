package MediDATA;

import java.io.*;
import java.util.Scanner;

import static MediDATA.login.*;

public class baza {
    public static void edytujBaze(String nazwa, String typ) throws IOException
            {

            Scanner s = new Scanner(System.in);
            System.out.println("Witaj " + nazwa + "!");
            System.out.println("Typ konta: " + typ);
            String choice = "0";

            System.out.println("Wybierz opcję:");

            while (choice != "1" && choice != "2" && choice != "3" && choice != "4")
            {
            System.out.println("1. Dodaj pacjenta");
            System.out.println("2. Usuń pacjenta");
            System.out.println("3. Edytuj dane pacjenta");
            System.out.println("4. Wyjście");

            choice = s.nextLine();

            switch(choice)
            {
            case "1": {
            if(!typ.equals("pielęgniarka")) {
            System.out.println("Niewystarczające uprawnienia. Jeśli myślisz, że to błąd, skontaktuj się z administratorem.");
            System.out.println("Naciśnij ENTER, aby kontynuować...");
            s.nextLine();
            System.exit(0);
            }
            dodajPacjenta2();
            break;
            }

            case "2" : {
            if(!typ.equals("pielęgniarka")) {
            System.out.println("Niewystarczające uprawnienia. Jeśli myślisz, że to błąd, skontaktuj się z administratorem.");
            System.out.println("Naciśnij ENTER, aby kontynuować...");
            s.nextLine();
            System.exit(0);
            }
            usunPacjenta();
            break;
            }

            case "3": {

            }// Tutaj Oskar musisz dać przejście do swojego menu które musisz stworzyć.
            case "4" : {
            System.exit(0);
            }
    default: System.out.println("Nieprawidłowa opcja!"); break;
            }

            }

            }
    public static void saveToFile(String file, String text,boolean reset)
            {
            try
            {
            File f = new File(file);
            FileWriter fw = new FileWriter(f,reset);
            PrintWriter pw = new PrintWriter(fw);

            pw.println(text);                 			// Metoda, która służy do dopisywania tekstu do istniejącego pliku tekstowego
            pw.close();
            }
            catch(IOException e)
            {
            System.out.println("Błąd metody saveToFile!");
            }
            }
    public static void dodajPacjenta2() throws IOException
            {
            Scanner s = new Scanner(System.in);

            System.out.println("Podaj imię pacjenta:");
            String imie = s.nextLine();
            System.out.println("Podaj nazwisko pacjenta:");
            String nazwisko = s.nextLine();
            System.out.println("Podaj datę urodzenia pacjenta:");
            String data = s.nextLine();
            System.out.println("Podaj pesel pacjenta:");
            String pesel = s.nextLine();
            System.out.println("Podaj miejscowość i adres zamieszkania pacjenta:");
            String adres = s.nextLine();
            System.out.println("Podaj numer telefonu pacjenta:");
            String telefon = s.nextLine();

            saveToFile("pacjenci",pesel + ";" + data + ";" + imie + ";" + nazwisko + ";" + adres + ";" + telefon + ";" + ";",true);

            System.out.println("Dodano pacjenta!");

            edytujBaze(nazwa, wybranyTyp);
            }
    public static void removeRecord(String filepath,int deleteLine)
            {
            String tempFile = "temp.txt";
            File oldFile = new File(filepath);
            File newFile = new File(tempFile);

            int line = 0;
            String currentLine;

            try
            {
            FileWriter fw = new FileWriter(tempFile,true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            FileReader fr = new FileReader(filepath);
            BufferedReader br = new BufferedReader(fr);

            while((currentLine = br.readLine()) != null) //Metoda służąca do usuwania konkretnej linii z pliku tekstowego
            {
            line++;

            if(deleteLine != line)
            {
            pw.println(currentLine);
            }
            }
            pw.flush();
            pw.close();
            fr.close();
            br.close();
            bw.close();
            fw.close();

            oldFile.delete();
            File dump = new File(filepath);
            newFile.renameTo(dump);
            }
            catch(Exception e)
            {
            System.out.println(e);
            }
            }
    public static int ileLinii(String filepath) throws IOException
            {

            BufferedReader reader = new BufferedReader(new FileReader(filepath));

            int lines = 0;

            while (reader.readLine() != null) lines++;   //Metoda służąca do zliczania wszystkich linii w pliku tekstowym

            reader.close();

            return lines;


            }
    public static int ktoraLinia(String filepath, String pesel) throws IOException
            {

            BufferedReader br = new BufferedReader(new FileReader(filepath));

            int obecna = 0;
            int szukana = 0;

            String line;

            while ((line = br.readLine()) != null)  // Metoda służąca do znalezienia konkretnej linii w której znajduje się podane słowo
            {
            if(line.indexOf(pesel) >= 0)
            {
            szukana = obecna;
            }
            obecna++;
            }
            br.close();

            return szukana;
            }
    public static void usunPacjenta() throws IOException{
            Scanner s = new Scanner(System.in);

            System.out.println("Jak chcesz odszukać pacjenta, którego chcesz usunąć z bazy danych?");

            System.out.println("1. Pesel");
            System.out.println("2. Imię i nazwisko");
            System.out.println("3. Wróć do menu");

            int choice = 0;

            while(choice !=1 && choice !=2) {
            choice = s.nextInt();
            switch(choice) {
            case 1 : usunPoPeselu(); break;
            case 2 : usunPoImieniu(); break;
            case 3 : edytujBaze(nazwa, wybranyTyp); break;
    default : System.out.println("Niepoprawna opcja!"); break;
            }
            }

            }
    public static void usunPoPeselu() throws IOException
            {
            Scanner s = new Scanner(System.in);

            System.out.println("Podaj pesel:");

            String pesel = s.nextLine();

            int linie = ileLinii("pacjenci");

            int szukana_linia = ktoraLinia("pacjenci",pesel); //Metoda korzystająca z poprzednich metod służąca do usunięcia po peselu

            removeRecord("pacjenci",szukana_linia + 1);

            System.out.println("Usunięto pacjenta z bazy!");

            edytujBaze(nazwa, wybranyTyp);
            }
    public static void usunPoImieniu() throws IOException{
            Scanner s = new Scanner(System.in);

            System.out.println("Podaj imię pacjenta:");

            String imie = s.nextLine();

            System.out.println("Podaj nazwisko pacjenta:");  // Metoda korzystająca z poprzednich metod służąca do usunięcia po imieniu i nazwisku

            String nazwisko = s.nextLine();

            int linie = ileLinii("pacjenci");
            int szukana_linia = ktoraLinia("pacjenci",imie+";"+nazwisko);

            removeRecord("pacjenci",szukana_linia+1);

            System.out.println("Usunięto pacjenta z bazy!");

            edytujBaze(nazwa, wybranyTyp);
            }


}