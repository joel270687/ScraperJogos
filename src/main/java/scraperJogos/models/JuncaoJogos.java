package scraperJogos.models;

import java.util.Date;
import java.util.List;

/**
 * Created by Joel on 26/05/2022.
 */
public class JuncaoJogos {
    String casa;
    String fora;
    Date data;
    List<JuncaoMercados> mercadosPorCasas;

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

    public List<JuncaoMercados> getMercadosPorCasas() {
        return mercadosPorCasas;
    }

    public void setMercadosPorCasas(List<JuncaoMercados> mercadosPorCasas) {
        this.mercadosPorCasas = mercadosPorCasas;
    }
}
