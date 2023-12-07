import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ListControl {
    private List<Schüler> schüler;
    private String eingabe;
    private Scanner scanner;

    public ListControl() {
        schüler = new List<Schüler>();
        scanner = new Scanner(System.in);
        cleanConsole();
        start();
    }

    private void anweisung() {
        System.out.println(
                "# - Liste ausgeben | * - erstes Element löschen | < - Programm beenden | I - Import der Datei \"import.txt\"");
        System.out.print(">> ");
    }

    private void cleanConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void start() {
        anweisung();
        while (true) {
            eingabe = scanner.next();
            cleanConsole();
            switch (eingabe.charAt(0)) {
                case '#':
                    ausgabe();
                    break;
                case '*':
                    loeschen();
                    break;
                case '<':
                    ende();
                    break;
                case 'I':
                    importFile();
                    break;
                default:
                    einfuegen();
                    break;
            }
            anweisung();
        }
    }

    private void ausgabe() {
        schüler.toFirst();
        if (schüler.getContent() == null) {
            System.out.print("Die Liste ist leer.");
        }
        while (schüler.hasAccess()) {
            System.out.print(schüler.getContent().getName() + " | ");
            schüler.next();
        }
        System.out.println();
    }

    private void ende() {
        System.out.println("ENDE");
        System.exit(0);
    }

    private void loeschen() {
        schüler.toFirst();
        while (schüler.hasAccess()) {
            schüler.remove();
            schüler.next();
            ausgabe();
        }
    }

    private void importFile() {
        schüler.toLast();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("src/import.txt"));
            String line = reader.readLine();
            while (line != null) {
                schüler.append(new Schüler(line));
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("File not found");
        }
        System.out.println("Import abgeschlossen...");
        ausgabe();
    }

    private void einfuegen() {
        schüler.toFirst();
        while (schüler.hasAccess()) {
            scanner = new Scanner(System.in);
            schüler.append(new Schüler(eingabe));
            ausgabe();
        }
    }

    public static void main(String[] args) {
        new ListControl();
    }
}