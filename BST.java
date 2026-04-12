public class BST <T extends Comparable<T>>{
    private Nodo<T> radice;

    public BST(){
        radice =null;
    }

    //se la radice è uguale a null allora l'albero è vuoro
    public boolean isEmpty(){
        return radice == null;
    }

    //metodo pubblico per inserire
    public void inserisci(T valore){
        //fa partire la ricorsività aggiornando radice cosi da scendere a ogni passo
        radice=inserisciRicorsivo(radice,valore);
    }

    //metodo privato che puo essere richiamato solo dal metodo pubblico
    private Nodo<T> inserisciRicorsivo(Nodo<T> corrente, T valore){

        //se arriviamo a una foglia creiamo il nuovo nodo
        if(corrente==null){
            System.out.println("elemento inserito con successo");
            return new Nodo<>(valore);
        }

        //salva il compareTo tra valore e corrente in una variabile
        int cmp=valore.compareTo(corrente.valore);

        //navighiamo l'albero in base a valore(che sarebbe un elemento del catalogo)
        if(cmp<0){
            //se il nuovo valore è minore scendi a sinistra
            corrente.sinistro=inserisciRicorsivo(corrente.sinistro,valore);
        }else if(cmp>0){
            //se il nuovo valore è maggiore scendi a destra
            corrente.destro=inserisciRicorsivo(corrente.destro,valore);
        }

        // Ritorna il nodo corrente (non modificato)
        return corrente;
    }

    //metodo publico per cercare un valore
    public Nodo<T> cerca(T valore) {
        System.out.println("ricerca in corso...");
        return cercaRicorsivo(radice, valore);
    }

    //metodo privato di ricerca con ricorsività
    private Nodo<T> cercaRicorsivo(Nodo<T> corrente, T valore) {
        //se arriviamo a null il valore non è presente nell'albero
        if(corrente==null){
            System.out.println("elemento non presente nell'albero");
            return null;
        }

        //stessa storia di prima
        int cmp=valore.compareTo(corrente.valore);

        //se il compare da zero significa che abbiamo trovato l'elemento
        if(cmp==0){
            System.out.println("elemento trovato");
            System.out.println(corrente.valore);
            return corrente;
        }

        //se cmp è minore di zero scende a sinistra altrimenti a destra
        return cmp<0 ? cercaRicorsivo(corrente.sinistro,valore) : cercaRicorsivo(corrente.destro,valore);
    }

    //dal più piccolo al più grande
    public void stampaInOrdine(){
        System.out.println("--- Elenco ordinato ---");
        stampaInOrdineRicorsiva(radice);
        System.out.println();//va a capo a fine elenco
    }

    private void stampaInOrdineRicorsiva(Nodo<T> corrente){
        if(corrente==null){return;} //se corrente è null siamo arrivati a una foglia

        //Questa funzione partendo dalla root fa 3 cose:
        //1. visita ricorsivamente tutta la parte sinistra
        stampaInOrdineRicorsiva(corrente.sinistro);

        //2. stampa il nodo corrente
        System.out.println(corrente.valore+" ");

        //3. visita ricorsivamente tutta la parte a destra
        stampaInOrdineRicorsiva(corrente.destro);

    }

    public void stampaGrafica() {
        stampaGrafica(radice, "", true);
    }

    private void stampaGrafica(Nodo<T> nodo, String prefix, boolean isTail) {
        if (nodo == null) return;

        System.out.println(prefix + (isTail ? "└────── " : "├────── ") + nodo.valore);

        if (nodo.sinistro != null || nodo.destro != null) {
            if (nodo.destro != null) {
                stampaGrafica(nodo.destro, prefix + (isTail ? "        " : "│       "), nodo.sinistro == null);
            }
            if (nodo.sinistro != null) {
                stampaGrafica(nodo.sinistro, prefix + (isTail ? "        " : "│       "), true);
            }
        }
    }   	
}
