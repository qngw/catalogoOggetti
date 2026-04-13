
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

    public void inserisci(Nodo<T> nodo){
	radice=inserisciRicorsivo(radice,nodo);
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

    //metodo per inserire nodi con overwriting (usato per eliminare nodi)
    private Nodo<T> inserisciRicorsivo(Nodo<T> corrente, Nodo<T> nodo){
	if(corrente==null){
	    return nodo;
	}
	
	int cmp=nodo.valore.compareTo(corrente.valore);

	if(cmp<0){corrente.sinistro=inserisciRicorsivo(corrente.sinistro,nodo);}
	else if(cmp>0){corrente.destro=inserisciRicorsivo(corrente.destro,nodo);}

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
        if(cmp==0||corrente.valore.equals(valore)){
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

    //restituisce primo elemento
    public T getFirst(){
    	return getFirstRicorsivo(radice.sinistro,radice);
    } 
    private T getFirstRicorsivo(Nodo<T> corrente,Nodo <T> precedente){
	if(corrente!=null){
	    return getFirstRicorsivo(corrente.sinistro,corrente);
        }//se (andando solo a sinistra) un nodo non ha nessun nodo sinistro è il più piccolo
   	
	return precedente.valore;
	
   }
    public void elimina(T valore){
	radice=eliminaRicorsivo(radice,valore);
    }

    //elimina l'emento cercandolo sia tramite id che tramite oggetto
    private Nodo<T> eliminaRicorsivo(Nodo<T> corrente,T valore){

	if(corrente==null) return null;
	
	int cmp=valore.compareTo(corrente.valore);

	if(cmp<0){corrente.sinistro=eliminaRicorsivo(corrente.sinistro,valore);}
	else if(cmp>0){corrente.destro=eliminaRicorsivo(corrente.destro,valore);}
	else{
	    //caso con un figlio: viene sostituito dal suo figlio
	    if(corrente.sinistro==null) return corrente.destro;
	    if(corrente.destro==null) return corrente.sinistro;
	
	    //caso con due figli: prendi il piu piccolo di destra
	    corrente.valore=valoreMinimo(corrente.destro);
	
	    //elimina il successore
	    corrente.destro=eliminaRicorsivo(corrente.destro,corrente.valore);
	    System.out.println("eliminazione avvenuta con success");
    	}
	return corrente;
    }
    private T valoreMinimo(Nodo<T> nodo){
	T min=nodo.valore;
	while(nodo.sinistro!=null){
	    min=nodo.sinistro.valore;
	    nodo=nodo.sinistro;
	}
	return min;
    }

    //restituise l'ultimo elemento
    public T getLast(){
   	return getLastRicorsivo(radice.destro,radice);
    }
    private T getLastRicorsivo(Nodo<T> corrente,Nodo<T> precedente){
	if(corrente!=null){
	    return getLastRicorsivo(corrente.destro,corrente);
	}//se (andando solo a destra) un nodo non ha nessun nodo destro è il più grande

	return precedente.valore;
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
