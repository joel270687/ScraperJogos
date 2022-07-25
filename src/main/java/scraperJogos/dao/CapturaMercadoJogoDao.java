package scraperJogos.dao;

import scraperJogos.models.Mercados;

import java.io.IOException;
import java.util.List;

/**
 * Created by Joel on 16/05/2022.
 */
public interface CapturaMercadoJogoDao {
    List<Mercados> findMercado(String url) throws IOException;
    void imprimeMercado(List<Mercados> lista);
}
