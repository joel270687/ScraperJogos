package scraperJogos.models;

import java.util.List;

/**
 * Created by Joel on 26/05/2022.
 */
public class JuncaoMercados {
    String nomeCasa;
    String urlCasa;
    List<Mercados> mercadoCasa;

    public String getNomeCasa() {
        return nomeCasa;
    }

    public void setNomeCasa(String nomeCasa) {
        this.nomeCasa = nomeCasa;
    }

    public String getUrlCasa() {
        return urlCasa;
    }

    public void setUrlCasa(String urlCasa) {
        this.urlCasa = urlCasa;
    }

    public List<Mercados> getMercadoCasa() {
        return mercadoCasa;
    }

    public void setMercadoCasa(List<Mercados> mercadoCasa) {
        this.mercadoCasa = mercadoCasa;
    }
}
