package scraperJogos.dao.daoimpl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import scraperJogos.dao.CapturaMercadoJogoDao;
import scraperJogos.models.Mercados;
import scraperJogos.models.Odds;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CapturaMercadoJogoDaoImpl implements CapturaMercadoJogoDao {
    @Override
    public List<Mercados> findMercado(String url) throws IOException {
        Document doc = Jsoup.connect(url).get(); //pegga toda a página
        Elements market = doc.getElementsByClass("eventdetail-market"); //pega todas os mercados
        List<Mercados> mercadosDoJogo = new ArrayList<>();
        for(Element mk : market){
            Mercados mercado = new Mercados();
            mercado.setNome(mk.getElementsByClass("name").first().text());
            mercado.setOdds(retornaOddsByMercado(mk));
            mercadosDoJogo.add(mercado);
        }
        return mercadosDoJogo;
    }

    private List<Odds> retornaOddsByMercado(Element market){
        Elements marketBody = market.getElementsByClass("eventdetail-optionItem");
        List<Odds> listOdds = new ArrayList<>();
        for(Element mk : marketBody){
            Odds novoOdd = new Odds();
            novoOdd.setId(mk.getElementsByClass("name").first().text());
            novoOdd.setValor(Float.valueOf(mk.getElementsByClass("odd").first().text().replace(",",".")));
            listOdds.add(novoOdd);
        }
        return listOdds;
    }

    @Override
    public void imprimeMercado(List<Mercados> lista) {
        for (Mercados ls : lista) {
            System.out.println(ls.getNome());
            for (Odds odd : ls.getOdds()) {
                System.out.println(odd.getId() + " -> " + odd.getValor());
            }
        }
    }
}
