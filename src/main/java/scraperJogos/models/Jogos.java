package scraperJogos.models;

import java.util.Date;
import java.util.List;

/**
 * Created by Joel on 12/05/2022.
 */
public class Jogos {
    String casa;
    String fora;
    Date data;
    String link;
    List<Mercados> mercados;

    public String getCasa() {
        return casa;
    }

    public void setCasa(String casa) {
        this.casa = casa;
    }

    public String getFora() {
        return fora;
    }

    public void setFora(String fora) {
        this.fora = fora;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public List<Mercados> getMercados() {
        return mercados;
    }

    public void setMercados(List<Mercados> mercados) {
        this.mercados = mercados;
    }
}
