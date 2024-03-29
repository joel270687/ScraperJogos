package scraperJogos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import scraperJogos.dao.CapturaJogosDao;
import scraperJogos.dao.PopulaDados;
import scraperJogos.dao.ProcuraSureBetsDao;
import scraperJogos.dao.daoimpl.CapturaJogosDaoImpl;
import scraperJogos.dao.daoimpl.PopulaDadosImpl;
import scraperJogos.dao.daoimpl.ProcuraSureBetsDaoImpl;
import scraperJogos.models.Casa;
import scraperJogos.models.Gatilho;
import scraperJogos.models.Jogos;
import scraperJogos.models.JuncaoJogos;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ScraperMain {
    private static PopulaDados populaDados = new PopulaDadosImpl();
    private static ProcuraSureBetsDao procuraSureBets = new ProcuraSureBetsDaoImpl();
    private static final String URL_INICIO = "https://";
    private static final String URL_MEIO = "/sistema_v2/usuarios/simulador/desktop/jogos.aspx?idesporte=102&idcampeonato=";

    public static void main(String[] args) throws IOException, ParseException {

        String idCampeonato = "574926"; //separado por dia de cada semana
        String[] idCasas = {"sportbet.club"};//casas que quero comparar as odds
        List<Casa> casas = populaDados.montaListaByCasaAposta(idCasas,idCampeonato);

        //for(Casa cs : casas) {
        //    List<Jogos> pegaSomenteImparPar = procuraSureBets.separaPorGatilho(cs.getJogosCasa(),null);
        //    System.out.println("teste");
        //}

        List<JuncaoJogos> allJogosSeparados = new ArrayList<>();
        List<JuncaoJogos> allJogosSeparadosComOdds = new ArrayList<>();
        allJogosSeparados = procuraSureBets.criaListaAllJogos(casas);
        allJogosSeparadosComOdds = procuraSureBets.atualizaOddsAllJogosJuntandoCasas(casas,allJogosSeparados);
        allJogosSeparados.clear();

        Collections.sort(allJogosSeparadosComOdds, Comparator.comparing(JuncaoJogos::getData)); //ordena por data

        /*allJogosSeparadosComOdds.forEach(date -> {
            System.out.println(date.data);
        });*/

        List<Gatilho> gatilhos = new ArrayList<>();

        /*Gatilho ultimogol = new Gatilho();
        ultimogol.setGatilhoMercado("Quem Marca o Último Gol?");
        ultimogol.setItemMercado(new String[]{"Casa","Fora"});
        gatilhos.add(ultimogol);*/

        /*Gatilho exato = new Gatilho();
        exato.setGatilhoMercado("Casa - Marca em ambos os tempos?");
        exato.setItemMercado(new String[]{"não","sim"});
        gatilhos.add(exato);*/

        Gatilho vencedor = new Gatilho();
        vencedor.setGatilhoMercado("Quem Marca o Último Gol?");
        vencedor.setItemMercado(new String[]{"Casa","Fora"});
        gatilhos.add(vencedor);





        procuraSureBets.verificaSureBetPorGatilho(allJogosSeparadosComOdds,gatilhos);
        //procuraSureBets.verificaOddEntreDuasCasas(casas.get(1),casas.get(2));

        //converterToJson(jogos);
        //capturaJogosDao.imprimeJogosDoDia(jogos);
    }

    //Bonus: método para converter um objeto em um Json
    private static void converterToJson(List<Jogos> jogos){
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(jogos);
            System.out.println("Objeto em JSON: " + json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
