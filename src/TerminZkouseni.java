import java.time.LocalDate;

public class TerminZkouseni {
    private String tema;
    private int znamka;
    private LocalDate datum;

    public TerminZkouseni(String tema, int znamka, LocalDate datum) {
        this.tema = tema;
        this.znamka = znamka;
        this.datum = datum;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public int getZnamka() {
        return znamka;
    }

    public void setZnamka(int znamka) {
        this.znamka = znamka;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }
    public void dnesniDatum(LocalDate datum){
        this.datum = LocalDate.now();
    }

}
