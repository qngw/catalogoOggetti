public class Nodo<T extends Comparable<T>> {
    T valore;
    Nodo<T> sinistro;
    Nodo<T> destro;

    public Nodo(T valore){
        this.valore=valore;

    }
}