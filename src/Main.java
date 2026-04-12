// Creiamo l'albero di elementi del catalogo
// Grazie al polimorfismo, può contenere Libri, Film e Videogiochi
static BST<ElementoCatalogo> catalogo = new BST<>();

void main() {
    Scanner sc = new Scanner(System.in);

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
                    diagnostica();
                    break;
                case 2:
                    inserimentoNuovoElemento();
                    break;
                case 3:
                    ricercaElemento();
                    break;
                case 4:
                    catalogo.stampaInOrdine();
                    break;
                default:
                    break;
            }
            pulisciTerminale();
        } while (scelta < 0 || scelta > 4);
    }
}

public static void ricercaElemento() {
    Scanner sc = new Scanner(System.in);

    IO.print("id da cercare: ");
    int id = sc.nextInt();
    ricerca(id);
}

public static void inserimentoNuovoElemento() {
    Scanner sc = new Scanner(System.in);
    IO.println("quale elemento si desidera aggiungere?\n1. libro\n2. film\n3. videogioco");
    int scelta = -1;
    while (scelta < 0 || scelta > 3) {
        scelta = sc.nextInt();
    }
    sc.nextLine();
    IO.println("Inserisci i dati per il nuovo elemento: ");
    IO.print("titolo: ");
    String titolo = sc.nextLine();
    IO.print("anno pubblicazione: ");
    int annoPubblicazione = sc.nextInt();
    IO.print("id: ");
    int id = sc.nextInt();

    sc.nextLine();
    String parametro;
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

//ricerca nel BST con overriding
public static void ricerca(int id) {
    catalogo.cerca(new ElementoCatalogo("", 0, id));
}

public static void ricerca(ElementoCatalogo elemento) {
    catalogo.cerca(elemento);
}

public static void inserimentoElementiBase() {
    IO.println("--- Inserimento elementi base nel BST ---");

    // Creazione di un array con 10 nuovi elementi misti
    // I prezzi/ID sono scelti per creare una struttura bilanciata o variata nel BST
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

    // Iterazione dell'array per l'inserimento nel catalogo
    for (ElementoCatalogo e : nuoviElementi) {
        IO.println(e);
        catalogo.inserisci(e);
    }

    IO.println("\nsono stati inseriti con successo: " + nuoviElementi.length + " elementi");
}

public static void diagnostica() {
    IO.println("--- Diagnostica ---\n");
    // Test 1: Stampa ordinata
    // Poiché il compareTo usa l'ID, la stampa sarà in ordine crescente di ID
    catalogo.stampaInOrdine();

    // Test 2: Ricerca di un elemento esistente
    IO.println("\n--- Test di Ricerca ---");
    int idDaCercare = 350;
    // Creiamo un oggetto "finto" con lo stesso ID per testare la ricerca
    // (Il BST usa il compareTo, quindi basta che l'ID corrisponda)
    ElementoCatalogo finto = new ElementoCatalogo("", 0, idDaCercare);
    ricerca(finto);

    // Test 3: Ricerca di un elemento inesistente
    int idInesistente = 999;
    ricerca(idInesistente);
}

public static void pulisciTerminale() {
    try {
        String sistemaOperativo = System.getProperty("os.name");

        if (sistemaOperativo.contains("Windows")) {
            // esegue il comando 'cls' su windows
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } else {
            // utilizza le sequenze di escape ansi per unix/linux/macos
            IO.print("\033[H\033[2J");
            System.out.flush();
        }
    } catch (Exception e) {
        // gestisce eventuali errori di esecuzione
        System.err.println("errore durante la pulizia del terminale");
    }
}