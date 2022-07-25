package scraperJogos.dao;

import scraperJogos.models.Jogos;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface CapturaJogosDao {
    List<Jogos> capturaJogosDoDia(String url) throws IOException, ParseException;
    void imprimeJogosDoDia(List<Jogos> lista);
}
