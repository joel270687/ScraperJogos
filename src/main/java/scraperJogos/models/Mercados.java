package scraperJogos.models;


import java.util.List;

public class Mercados {
    String nome;
    List<Odds> odds;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Odds> getOdds() {
        return odds;
    }

    public void setOdds(List<Odds> odds) {
        this.odds = odds;
    }
}
