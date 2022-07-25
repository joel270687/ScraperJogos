package scraperJogos.dao;

import scraperJogos.models.Casa;
import scraperJogos.models.Gatilho;
import scraperJogos.models.JuncaoJogos;

import java.util.List;

/**
 * Created by Joel on 25/05/2022.
 */
public interface ProcuraSureBetsDao {
    void verificaOddEntreDuasCasas(Casa casa1, Casa casa2);
    List<JuncaoJogos> verificaSureBetPorGatilho(List<JuncaoJogos> jncJogos, List<Gatilho> gat);
    List<JuncaoJogos> criaListaAllJogos(List<Casa> listaCasas);
    List<JuncaoJogos> atualizaOddsAllJogosJuntandoCasas(List<Casa> listaCasas, List<JuncaoJogos> listaAllJogos);

}