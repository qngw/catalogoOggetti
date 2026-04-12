public class ElementoCatalogo implements Comparable<ElementoCatalogo>{
    String titolo;
    int annoPublicazione;
    int id; //codice identificativo

    public ElementoCatalogo(String titolo, int annoPublicazione, int id) {
        this.titolo = titolo;
        this.annoPublicazione = annoPublicazione;
        this.id = id;
    }

    public ElementoCatalogo() {
    }

    public String getTitolo() {return titolo;}
    public int getAnnoPublicazione() {return annoPublicazione;}
    public int getId() {return id;}
    public void setTitolo(String titolo) {this.titolo = titolo;}
    public void setAnnoPublicazione(int annoPublicazione) {this.annoPublicazione = annoPublicazione;}
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
        return "titolo: "+titolo+", annoPublicazione: "+annoPublicazione+", id: "+id;
    }

}
