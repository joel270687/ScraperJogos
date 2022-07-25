package scraperJogos.endpoint;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import scraperJogos.dao.CapturaJogosDao;
import scraperJogos.dao.ProcuraSureBetsDao;
import scraperJogos.dao.daoimpl.CapturaJogosDaoImpl;
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

/**
 * Created by Joel on 25/07/2022.
 */
@RestController
@RequestMapping("scraper")
public class ScraperWebJogosEndpoint {
    private static CapturaJogosDao capturaJogosDao = new CapturaJogosDaoImpl();
    private static ProcuraSureBetsDao procuraSureBets = new ProcuraSureBetsDaoImpl();
    private static final String URL_INICIO = "https://";
    private static final String URL_MEIO = "/simulador/jogos.aspx?idesporte=102&idcampeonato=";

    @GetMapping(path = "quemmarcaultimogol/{idDia}")
    public ResponseEntity<?> quemMarcaUltimoGol(@PathVariable("idDia") String idDia) throws IOException, ParseException {
        String idCampeonato = idDia; //separado por dia de cada semana
        String[] idCasas = {"alphabet365.net","sportbet365.club","sportbet.club"};//casas que quero comparar as odds
        List<Casa> casas = new ArrayList<>();

        for(String idCasa: idCasas){
            Casa casaTemp = new Casa();
            casaTemp.setNomeCasa(idCasa);
            casaTemp.setUrlCompleta(URL_INICIO + idCasa + URL_MEIO + idCampeonato);

            List<Jogos> jogos = new ArrayList<>();
            jogos = capturaJogosDao.capturaJogosDoDia(casaTemp.getUrlCompleta());
            casaTemp.setJogosCasa(jogos);
            casas.add(casaTemp);
        }

        List<JuncaoJogos> allJogosSeparados = new ArrayList<>();
        List<JuncaoJogos> allJogosSeparadosComOdds = new ArrayList<>();
        allJogosSeparados = procuraSureBets.criaListaAllJogos(casas);
        allJogosSeparadosComOdds = procuraSureBets.atualizaOddsAllJogosJuntandoCasas(casas,allJogosSeparados);
        allJogosSeparados.clear();

        Collections.sort(allJogosSeparadosComOdds, Comparator.comparing(JuncaoJogos::getData)); //ordena por data


        List<Gatilho> gatilhos = new ArrayList<>();

        Gatilho vencedor = new Gatilho();
        vencedor.setGatilhoMercado("Quem Marca o Último Gol?");
        vencedor.setItemMercado(new String[]{"Casa","Fora"});
        gatilhos.add(vencedor);

        return new ResponseEntity<>(procuraSureBets.verificaSureBetPorGatilho(allJogosSeparadosComOdds,gatilhos), HttpStatus.OK);
    }


    @GetMapping(path = "listAll/{idDia}")
    public String juncaoDosJogosDasCasas (@PathVariable("idDia") String idDia){
        return "Vai pegar os jogos das 3 casas com o idDia: "+idDia;
    }

}
