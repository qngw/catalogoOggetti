public class Libro extends ElementoCatalogo{
    private String autore;

    public Libro(String titolo, int annoPublicazione, int id, String autore) {
        super(titolo, annoPublicazione, id);
        this.autore = autore;
    }
    public Libro(){super();}

    public String getAutore() { return autore; }
    public void setAutore(String autore) { this.autore = autore; }

    @Override
    public String toString() {
        return super.toString()+", autore: "+autore;
    }
    
}

