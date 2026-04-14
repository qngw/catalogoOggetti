public class Videogioco extends ElementoCatalogo{
    private String piattaforma;

    public Videogioco(String titolo, int annoPublicazione, int id, String piattaforma) {
        super(titolo, annoPublicazione, id);
        this.piattaforma = piattaforma;
    }
    public Videogioco(){super();}

    public String getPiattaforma() { return piattaforma; }
    public void setPiattaforma(String autore) { this.piattaforma = autore; }

    @Override
    public String toString() {
        return super.toString()+" | piattaforma: "+piattaforma;
    }

}