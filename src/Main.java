import java.util.Scanner;

public class Main {
    // Definizione del catalogo come Albero Binario di Ricerca (BST)
    // Sfrutta il polimorfismo: la struttura accetta 'ElementoCatalogo' e tutte le sue sottoclassi (Libro, Film, Videogioco)
    static BST<ElementoCatalogo> catalogo = new BST<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // Popola l'albero con dati predefiniti
        inserimentoElementiBase();

        menu();
    }

    public static void menu() {
        Scanner sc = new Scanner(System.in);
        int scelta = -1;

        while (scelta != 0) {
            do {
                System.out.println("--- Menu ---\n");
                System.out.println("0. exit\n1. diagnostica\n2. aggiungi un elemento\n3. ricerca di un elemento\n4. stampa catalogo\n5. stampa grafica\n6. stampa il primo elemento\n7. stampa l'ultimo elemento\n8. elimina elemento");

                scelta = sc.nextInt();
                pulisciTerminale(); // funziona solo su terminale
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
		    case 5:
			catalogo.stampaGrafica();
			break;
                    case 6:
			System.out.println(catalogo.getFirst());
			break;
		    case 7:
			System.out.println(catalogo.getLast());
			break;
		    case 8:
			catalogo.stampaGrafica();
			eliminazioneElemento();//crea un elemento che poi verrà eliminato dal BST
			catalogo.stampaGrafica();
			break;
		    default:				
                        break;
                }
            } while (scelta < 0 || scelta > 8);

        }
    }
	
    public static void eliminazioneElemento(){
	Scanner sc=new Scanner(System.in);
	System.out.print("id da eliminare: ");
	int id=sc.nextInt();
	catalogo.elimina(new ElementoCatalogo("",0,id));
    }

    //Chiede all'utente l'ID da cercare e richiama il metodo di ricerca
    public static void ricercaElemento() {
        Scanner sc = new Scanner(System.in);
        System.out.print("id da cercare: ");
        int id = sc.nextInt();
        ricerca(id);
    }


    //Procedura guidata per creare e inserire un nuovo oggetto nel catalogo.
    //Uso del polimorfismo nella creazione delle istanze.
    public static void inserimentoNuovoElemento() {
        Scanner sc = new Scanner(System.in);
        System.out.println("quale elemento si desidera aggiungere?\n1. libro\n2. film\n3. videogioco");

        int scelta = -1;
        while (scelta < 1 || scelta > 3) {
            scelta = sc.nextInt();
        }

        sc.nextLine(); // Svuota il buffer (non so che significa ma va fatto, non è il primo progetto in cui provo a capi)
        System.out.println("Inserisci i dati per il nuovo elemento: ");
        System.out.print("titolo: ");
        String titolo = sc.nextLine();
        System.out.print("anno pubblicazione: ");
        int annoPubblicazione = sc.nextInt();
        System.out.print("id: ");
        int id = sc.nextInt();

        sc.nextLine(); // Svuota di nuovo il buffer
        String parametro;

        // In base alla scelta, istanzia la sottoclasse specifica e la aggiunge al BST, salva anche in un bool se l'aggiunta è andata a buon fine
        boolean successo=false;
	if (scelta == 1) {
            System.out.print("autore: ");
            parametro = sc.nextLine();
            successo=catalogo.inserisci(new Libro(titolo, annoPubblicazione, id, parametro));
	/*la funzione inserisci restituisce un boolean, true se è stato inserito con successo, false se c'è stato un problema
        *questo risultato viene salvato in "successo"
	*/
	} else if (scelta == 2) {
            System.out.print("regista: ");
            parametro = sc.nextLine();
            catalogo.inserisci(new Film(titolo, annoPubblicazione, id, parametro));
        } else {
            System.out.print("piattaforma: ");
            parametro = sc.nextLine();
            catalogo.inserisci(new Videogioco(titolo, annoPubblicazione, id, parametro));
        }
	//qui in base a "successo" stampa l'esito dell'operazione
	if(successo){
	    System.out.println("elemento inserito con successo");
	}else{System.out.println("impossibile inserire un elemento con questo id");}
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
        System.out.println("--- Inserimento elementi base nel BST ---");

        // Array polimorfico contenente diversi tipi di media
        ElementoCatalogo[] nuoviElementi = {
                new Libro("Il Nome della Rosa",1980, 600, "Umberto Eco"),
                new Film("Interstellar",2014, 250, "Christopher Nolan"),
                new Videogioco("Elden Ring",2022, 800, "FromSoftware"),
                new Libro("Cronache del Ghiaccio e del Fuoco",1996, 100, "G.R.R. Martin"),
                new Film("The Matrix", 1999,350, "Lana e Lilly Wachowski"),
                new Videogioco("Super Mario Bros",1985, 650, "Nintendo"),
                new Libro("Il Vecchio e il Mare",1952, 900, "Ernest Hemingway"),
                new Film("Parasite",2019, 450, "Bong Joon-ho"),
                new Videogioco("Cyberpunk 2077",2020 , 150 ,"CD Projekt Red"),
                new Libro("Dune", 1965 ,750 ,"Frank Herbert")
        };

        // Ciclo per inserire ogni elemento dell'array nell'albero binario
        for (ElementoCatalogo e : nuoviElementi) {
            System.out.println(e.toString());
            catalogo.inserisci(e);
        }

        System.out.println("\nsono stati inseriti con successo: " + nuoviElementi.length + " elementi");
    }

    //Esegue qualche test
    public static void diagnostica() {
        System.out.println("--- Diagnostica ---\n");

        // Test 1: La stampa in ordine deve mostrare gli ID dal più piccolo al più grande
        System.out.println("Visualizzazione ordinata per ID:");
        catalogo.stampaInOrdine();

        // Test 2: Verifica il funzionamento della ricerca per un ID noto
        System.out.println("\n--- Test di Ricerca ---");

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
                // Comando specifico per il prompt linu-macOs
		new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            System.err.println("errore durante la pulizia del terminale");
        }
    }
}
