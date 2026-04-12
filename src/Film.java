public class Film extends ElementoCatalogo{
    private String regista;

    public Film(String titolo, int annoPublicazione, int id, String piattaforma) {
        super(titolo, annoPublicazione, id);
        this.regista = piattaforma;
    }
    public Film(){super();}

    public String getRegista() { return regista; }
    public void setRegista(String autore) { this.regista = autore; }

    @Override
    public String toString() {
        return super.toString()+", piattaforma: "+ regista;
    }
}
