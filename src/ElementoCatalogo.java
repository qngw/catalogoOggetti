public class ElementoCatalogo implements Comparable<ElementoCatalogo>{
    private String titolo;
    private int annoPubblicazione;
    private int id; //codice identificativo

    public ElementoCatalogo(String titolo, int annoPubblicazione, int id) {
        this.titolo = titolo;
        this.annoPubblicazione = annoPubblicazione;
        this.id = id;
    }

    public ElementoCatalogo() {
    }

    public String getTitolo() {return titolo;}
    public int getAnnoPubblicazione() {return annoPubblicazione;}
    public int getId() {return id;}
    public void setTitolo(String titolo) {this.titolo = titolo;}
    public void setAnnoPubblicazione(int annoPubblicazione) {this.annoPubblicazione = annoPubblicazione;}
    public void setId(int id) {this.id = id;}

    @Override
    public int compareTo(ElementoCatalogo o) {
        if(this.id < o.id){
            return -1;
        }else if(this.id > o.id){
            return 1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "id: "+id+", titolo: "+titolo+", annoPublicazione: "+annoPubblicazione;
    }

}
