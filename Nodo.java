public class Nodo<T extends Comparable<T>> {
    T valore;
    Nodo<T> sinistro;
    Nodo<T> destro;

    public Nodo(T valore){
        this.valore=valore;
        sinistro=null;
        destro=null;
    }

    public T getValore() {return valore;}
    public Nodo<T> getSinistro() {return sinistro;}
    public Nodo<T> getDestro() {return destro;}
    public void setValore(T valore) {this.valore = valore;}
    public void setSinistro(Nodo<T> sinistro) {this.sinistro = sinistro;}
    public void setDestro(Nodo<T> destro) {this.destro = destro;}
}