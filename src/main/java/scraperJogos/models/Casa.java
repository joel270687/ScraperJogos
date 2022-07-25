package scraperJogos.models;

import java.util.List;

/**
 * Created by Joel on 25/05/2022.
 */
public class Casa {
    String urlCompleta;
    String nomeCasa;
    List<Jogos> jogosCasa;

    public String getUrlCompleta() {
        return urlCompleta;
    }

    public void setUrlCompleta(String urlCompleta) {
        this.urlCompleta = urlCompleta;
    }

    public String getNomeCasa() {
        return nomeCasa;
    }

    public void setNomeCasa(String nomeCasa) {
        this.nomeCasa = nomeCasa;
    }

    public List<Jogos> getJogosCasa() {
        return jogosCasa;
    }

    public void setJogosCasa(List<Jogos> jogosCasa) {
        this.jogosCasa = jogosCasa;
    }
}
