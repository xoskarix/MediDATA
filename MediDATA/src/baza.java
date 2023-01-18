package MediDATA;

import java.io.*;
import java.util.Scanner;

import static MediDATA.login.*;
import static MediDATA.login.nazwa;

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

                    System.out.println("Podaj numer PESEL pacjenta, u którego chcesz zmienić dane.");
                    String zmieniany_pesel;
                    zmieniany_pesel = s.nextLine();

                    System.out.println("Jakie dane chcesz zmienić?");
                    System.out.println("1. Zmiana adresu");
                    System.out.println("2. Zmiana numeru telefonu");
                    System.out.println("3. Dodawanie lub podgląd historii lecznia");
                    System.out.println("4. Dodawanie leków");
                    System.out.println("5. Powrót do menu");

                    String choice3;
                    choice3 = s.nextLine();

                    switch(choice3)
                    {
                            case "1": {
                                    if(!typ.equals("pielęgniarka")) {
                                            System.out.println("Niewystarczające uprawnienia. Jeśli myślisz, że to błąd, skontaktuj się z administratorem.");
                                            System.out.println("Naciśnij ENTER, aby kontynuować...");
                                            s.nextLine();
                                            edytujBaze(nazwa, wybranyTyp);
                                    }
                                    int linia_danych = ktoraLinia("pacjenci", zmieniany_pesel);

                                    String dane = Czytaj_linie(linia_danych);

                                    String lista_danych[] = dane.split(";");

                                    System.out.println("Podaj nowy adres: ");

                                    lista_danych[4] = s.nextLine();

                                    usun_edycja(lista_danych);
                                    edycja_danych(lista_danych);
                                    edytujBaze(nazwa,wybranyTyp);
                                    break;
                            }
                            case "2":{
                                    if(!typ.equals("pielęgniarka")) {
                                            System.out.println("Niewystarczające uprawnienia. Jeśli myślisz, że to błąd, skontaktuj się z administratorem.");
                                            System.out.println("Naciśnij ENTER, aby kontynuować...");
                                            s.nextLine();
                                            edytujBaze(nazwa, wybranyTyp);
                                    }
                                    int linia_danych = ktoraLinia("pacjenci", zmieniany_pesel);

                                    String dane = Czytaj_linie(linia_danych);

                                    String lista_danych[] = dane.split(";");

                                    System.out.println("Podaj nowy numer telefonu: ");

                                    lista_danych[5] = s.nextLine();
                                    usun_edycja(lista_danych);
                                    edycja_danych(lista_danych);
                                    edytujBaze(nazwa,wybranyTyp);
                                    break;
                            }
                            case "3":{
                                    if(!typ.equals("lekarz")) {
                                            System.out.println("Niewystarczające uprawnienia. Jeśli myślisz, że to błąd, skontaktuj się z administratorem.");
                                            System.out.println("Naciśnij ENTER, aby kontynuować...");
                                            s.nextLine();
                                            edytujBaze(nazwa,wybranyTyp);
                                    }
                                    System.out.println("Wybierz opcję:");
                                    System.out.println("1. Nowa choroba");
                                    System.out.println("2. Zakończenie leczenia");
                                    System.out.println("3. Wyświetl historię leczenia");

                                    String choice4 = s.nextLine();

                                    switch(choice4) {
                                            case "1": {
                                                    int linia_danych = ktoraLinia("pacjenci", zmieniany_pesel);

                                                    String dane = Czytaj_linie(linia_danych);

                                                    String lista_danych[] = dane.split(";");
                                                    System.out.println("Podaj nazwę choroby: ");
                                                    String nowa_choroba = s.nextLine();
                                                    System.out.println("Podaj datę rozpoznania chorby: ");
                                                    String data_rozpoznania = s.nextLine();
                                                    lista_danych[6] = data_rozpoznania + " - trwa -> " + nowa_choroba + "/";
                                                    usun_edycja(lista_danych);
                                                    edycja_danych(lista_danych);
                                                    break;
                                            }
                                            case "2": {
                                                    int linia_danych = ktoraLinia("pacjenci", zmieniany_pesel);

                                                    String dane = Czytaj_linie(linia_danych);

                                                    String lista_danych[] = dane.split(";");
                                                    System.out.println("Dla której choroby chcesz wprowadzić zmianę: ");

                                                    String choroby = lista_danych[6];
                                                    String choroby_części[] = choroby.split("/");
                                                    for(int i = 0;i<choroby_części.length;i++)
                                                    System.out.println(i+1 +". "+ choroby_części[i]);
                                                    String numer_choroby = s.nextLine();
                                                    int choroba = Integer.parseInt(numer_choroby);
                                                    String wybrana_choroba = choroby_części[choroba-1];
                                                    String wybrana_części[] = wybrana_choroba.split(" ");
                                                    System.out.println("Podaj datę zakończenia leczenia: ");
                                                    String data_zakończenia = s.nextLine();
                                                    wybrana_części[2] = data_zakończenia;
                                                    String łączenie1 = "";
                                                    for(int i=0;i<wybrana_części.length;i++)
                                                            łączenie1 = łączenie1+wybrana_części[i] + " ";
                                                    choroby_części[choroba-1] = łączenie1;
                                                    String łączenie2 = "";
                                                    for(int i =0; i<choroby_części.length;i++)
                                                            łączenie2 = łączenie2 + choroby_części[i]+"/";
                                                    lista_danych[6] = łączenie2;
                                                    usun_edycja(lista_danych);
                                                    edycja_danych(lista_danych);
                                                    edytujBaze(nazwa,wybranyTyp);
                                                           break;

                                            }
                                            case "3":{
                                                    int linia_danych = ktoraLinia("pacjenci", zmieniany_pesel);

                                                    String dane = Czytaj_linie(linia_danych);

                                                    String lista_danych[] = dane.split(";");
                                                    System.out.println(lista_danych[6]);
                                                    System.out.println("Aby powrócić do menu kliknij przycisk enter.");
                                                    String choice5 = s.nextLine();
                                                    edytujBaze(nazwa, wybranyTyp);

                                            }
                                            } break;
                                    }
                            case "4":{
                                    if(!typ.equals("lekarz")) {
                                            System.out.println("Niewystarczające uprawnienia. Jeśli myślisz, że to błąd, skontaktuj się z administratorem.");
                                            System.out.println("Naciśnij ENTER, aby kontynuować...");
                                            s.nextLine();
                                            edytujBaze(nazwa, wybranyTyp);
                                    }
                                    int linia_danych = ktoraLinia("pacjenci", zmieniany_pesel);

                                    String dane = Czytaj_linie(linia_danych);

                                    String lista_danych[] = dane.split(";");
                                    System.out.println("Podaj nazwę i parametry dodawanego leku: ");

                                    lista_danych[7] = lista_danych[7] + s.nextLine();
                                    edycja_danych(lista_danych);
                                    break;
                            }
                            case "5":{
                                    edytujBaze(nazwa, wybranyTyp);
                                    break;
                            }

                    }

            }
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
            String historia_leczenia = " ";
            String historia_leków = " ";

            saveToFile("pacjenci",pesel + ";" + data + ";" + imie + ";" + nazwisko + ";" + adres + ";" + telefon + ";" + historia_leczenia + ";" + historia_leków + ";" + ";",true);

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
    public static String Czytaj_linie(int a) throws FileNotFoundException{
            File plik = new File("pacjenci"); // obiekt przechowywujący dane z pliku tekstowego
            Scanner in = new Scanner(plik); //odczyt danych
            String zdanie;
            for (int i = 0; i < a; i++)
                    zdanie = in.nextLine();
            String zdanie_docelowe = in.nextLine();
            return zdanie_docelowe;
    }
        public static void edycja_danych(String lista[]) throws IOException
        {
                String imie = lista[2];
                String nazwisko = lista[3];
                String data = lista[1];
                String pesel = lista[0];
                String adres = lista[4];
                String telefon = lista[5];
                String historia_leczenia = lista[6];
                String historia_leków = lista[7];

                saveToFile("pacjenci",pesel + ";" + data + ";" + imie + ";" + nazwisko + ";" + adres + ";" + telefon + ";" + historia_leczenia + ";" + historia_leków + ";" + ";",true);

                System.out.println("Edycja danych pacjenta przebiegła pomyślnie!");
        }
        public static void usun_edycja(String lista[]) throws IOException{
                String pesel = lista[0];
                int szukana_linia = ktoraLinia("pacjenci",pesel);
                removeRecord("pacjenci",szukana_linia+1);
        }

}