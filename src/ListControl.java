import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
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
                "# - Liste ausgeben | * - erstes Element löschen | < - Programm beenden | I - Import der Datei \"import.txt\" | C - Aktuelles Element | F - Erstes Element | L - Letztes Element | N - Nächstes Element ausgeben | P - Vorheriges Element ausgeben | E - Export der Liste");
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
                case 'C':
                    currentElement();
                    break;
                case 'F':
                    firstElement();
                    break;
                case 'L':
                    lastElemnt();
                    break;
                case 'N':
                    nextElement();
                    break;
                case 'P':
                    previousElement();
                    break;
                case 'E':
                    exportFile();
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
        schüler.toFirst();
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

    private void exportFile() {
        try {
            PrintWriter writer = new PrintWriter("src/export.txt");
            schüler.toFirst();
            while (schüler.hasAccess()) {
                writer.println(schüler.getContent().getName());
                schüler.next();
            }
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        System.out.println("Export abgeschlossen...");
    }

    private void einfuegen() {
        schüler.toLast();
        scanner = new Scanner(System.in);
        schüler.append(new Schüler(eingabe));
        ausgabe();
    }

    // gibt den Namen des aktuellen Schülers aus
    private void currentElement() {
        System.out.println((schüler.hasAccess()) ? schüler.getContent().getName() : "Kein Elemnt vorhanden");
    }

    // gibt den Namen des ersten Schülers aus
    private void firstElement() {
        schüler.toFirst();
        System.out.println(schüler.getContent().getName());
    }

    // gibt den Namen des letzten Schülers aus
    private void lastElemnt() {
        schüler.toLast();
        System.out.println(schüler.getContent().getName());
    }

    // gibt den Namen des nächsten Schülers aus
    private void nextElement() {
        schüler.next();
        currentElement();
    }

    // gibt den Namen des vorherigen Schülers aus
    private void previousElement() {
        int bisEnde = 0;
        int gesamt = 0;
        while (schüler.hasAccess()) {
            bisEnde++;
            schüler.next();
        }
        schüler.toFirst();
        while (schüler.hasAccess()) {
            gesamt++;
            schüler.next();
        }
        schüler.toFirst();
        for (int i = 0; i < gesamt - bisEnde - 1; i++) {
            schüler.next();
        }
        currentElement();
    }

    public static void main(String[] args) {
        new ListControl();
    }
}