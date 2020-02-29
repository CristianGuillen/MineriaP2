public class Regla {
    private String[] antecedente;
    private String[] consecuente;
    private float confianza;

    public String[] getAntecedente() {
        return antecedente;
    }

    public void setAntecedente(String[] antecedente) {
        this.antecedente = antecedente;
    }

    public String[] getConsecuente() {
        return consecuente;
    }

    public void setConsecuente(String[] consecuente) {
        this.consecuente = consecuente;
    }

    public float getConfianza() {
        return confianza;
    }

    public void setConfianza(float confianza) {
        this.confianza = confianza;
    }

    public Regla(String[] antecedente, String[] consecuente, float confianza) {
        this.antecedente = antecedente;
        this.consecuente = consecuente;
        this.confianza = confianza;
    }
}
