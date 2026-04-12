import java.util.Scanner;

public class Main {
    // Definizione del catalogo come Albero Binario di Ricerca (BST)
    // Sfrutta il polimorfismo: la struttura accetta 'ElementoCatalogo' e tutte le sue sottoclassi (Libro, Film, Videogioco)
    static BST<ElementoCatalogo> catalogo = new BST<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // Popola l'albero con dati predefiniti
        inserimentoElementiBase();

        IO.print("premere ENTER per passare al menu");
        sc.nextLine();

        menu();
    }

    public static void menu() {
        Scanner sc = new Scanner(System.in);
        int scelta = -1;

        while (scelta != 0) {
            do {
                IO.println("--- Menu ---\n");
                IO.println("0. exit\n1. diagnostica\n2. aggiungi un elemento\n3. ricerca di un elemento\n4. stampa catalogo");

                scelta = sc.nextInt();
                switch (scelta) {
                    case 1:
                        diagnostica(); // Esegue test di controllo sul BST
                        break;
                    case 2:
                        inserimentoNuovoElemento();
                        break;
                    case 3:
                        ricercaElemento(); // Cerca un elemento specifico
                        break;
                    case 4:
                        catalogo.stampaInOrdine();
                        break;
                    default:
                        break;
                }
                pulisciTerminale(); // funziona solo su terminale
            } while (scelta < 0 || scelta > 4);
        }
    }

    //Chiede all'utente l'ID da cercare e richiama il metodo di ricerca
    public static void ricercaElemento() {
        Scanner sc = new Scanner(System.in);
        IO.print("id da cercare: ");
        int id = sc.nextInt();
        ricerca(id);
    }


    //Procedura guidata per creare e inserire un nuovo oggetto nel catalogo.
    //Uso del polimorfismo nella creazione delle istanze.
    public static void inserimentoNuovoElemento() {
        Scanner sc = new Scanner(System.in);
        IO.println("quale elemento si desidera aggiungere?\n1. libro\n2. film\n3. videogioco");

        int scelta = -1;
        while (scelta < 1 || scelta > 3) {
            scelta = sc.nextInt();
        }

        sc.nextLine(); // Svuota il buffer (non so che significa ma va fatto, non è il primo progetto in cui provo a capi)
        IO.println("Inserisci i dati per il nuovo elemento: ");
        IO.print("titolo: ");
        String titolo = sc.nextLine();
        IO.print("anno pubblicazione: ");
        int annoPubblicazione = sc.nextInt();
        IO.print("id: ");
        int id = sc.nextInt();

        sc.nextLine(); // Svuota di nuovo il buffer
        String parametro;

        // In base alla scelta, istanzia la sottoclasse specifica e la aggiunge al BST
        if (scelta == 1) {
            IO.print("autore: ");
            parametro = sc.nextLine();
            catalogo.inserisci(new Libro(titolo, annoPubblicazione, id, parametro));
        } else if (scelta == 2) {
            IO.print("regista: ");
            parametro = sc.nextLine();
            catalogo.inserisci(new Film(titolo, annoPubblicazione, id, parametro));
        } else {
            IO.print("piattaforma: ");
            parametro = sc.nextLine();
            catalogo.inserisci(new Videogioco(titolo, annoPubblicazione, id, parametro));
        }
    }

    //Overloading del metodo ricerca: permette di cercare passando solo l'ID o un elemento
    public static void ricerca(int id) {
        // Viene creato un oggetto "dummy" perché il BST confronta oggetti tramite compareTo
        catalogo.cerca(new ElementoCatalogo("", 0, id));
    }
    public static void ricerca(ElementoCatalogo elemento) {
        catalogo.cerca(elemento);
    }

    //Popola il catalogo con 10 elementi
    public static void inserimentoElementiBase() {
        IO.println("--- Inserimento elementi base nel BST ---");

        // Array polimorfico contenente diversi tipi di media
        ElementoCatalogo[] nuoviElementi = {
                new Libro("Il Nome della Rosa", 1980, 600, "Umberto Eco"),
                new Film("Interstellar", 2014, 250, "Christopher Nolan"),
                new Videogioco("Elden Ring", 2022, 800, "FromSoftware"),
                new Libro("Cronache del Ghiaccio e del Fuoco", 1996, 100, "G.R.R. Martin"),
                new Film("The Matrix", 1999, 350, "Lana e Lilly Wachowski"),
                new Videogioco("Super Mario Bros", 1985, 650, "Nintendo"),
                new Libro("Il Vecchio e il Mare", 1952, 900, "Ernest Hemingway"),
                new Film("Parasite", 2019, 450, "Bong Joon-ho"),
                new Videogioco("Cyberpunk 2077", 2020, 150, "CD Projekt Red"),
                new Libro("Dune", 1965, 750, "Frank Herbert")
        };

        // Ciclo per inserire ogni elemento dell'array nell'albero binario
        for (ElementoCatalogo e : nuoviElementi) {
            IO.println(e.toString());
            catalogo.inserisci(e);
        }

        IO.println("\nsono stati inseriti con successo: " + nuoviElementi.length + " elementi");
    }

    //Esegue qualche test
    public static void diagnostica() {
        IO.println("--- Diagnostica ---\n");

        // Test 1: La stampa in ordine deve mostrare gli ID dal più piccolo al più grande
        IO.println("Visualizzazione ordinata per ID:");
        catalogo.stampaInOrdine();

        // Test 2: Verifica il funzionamento della ricerca per un ID noto
        IO.println("\n--- Test di Ricerca ---");
        int idDaCercare = 350;
        ElementoCatalogo finto = new ElementoCatalogo("", 0, idDaCercare);
        ricerca(finto);

        // Test 3: Verifica il comportamento in caso di elemento non presente
        int idInesistente = 999;
        ricerca(idInesistente);
    }

    //pulisce lo schermo (funziona solo da terminale)
    public static void pulisciTerminale() {
        try {
            String sistemaOperativo = System.getProperty("os.name");

            if (sistemaOperativo.contains("Windows")) {
                // Comando specifico per il prompt di MS-DOS/Windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // Sequenza escape ANSI per sistemi basati su Unix (Linux, macOS)
                IO.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.err.println("errore durante la pulizia del terminale");
        }
    }
}