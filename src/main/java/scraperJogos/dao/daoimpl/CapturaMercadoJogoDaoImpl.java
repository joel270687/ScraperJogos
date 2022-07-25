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
        Document doc = Jsoup.connect(url).get(); //pegga toda a página com as odds do jogo html
        Element tbody = doc.getElementsByTag("tbody").first(); //pega a tabela com as odss
        Elements tr = tbody.getElementsByTag("tr"); //pega todas as tags tr
        List<Mercados> mercadosDoJogo = new ArrayList<>();
        List<Odds> OddsDoJogo = new ArrayList<>();
        Mercados mercado = new Mercados();

        for(Element ls : tr) {
            Element th1 = ls.getElementsByClass("th_1").first();
            Element th2 = ls.getElementsByClass("th_2").first();

            if (th1 != null && th2 == null) {//achou um novo tipo de mercado
                if(mercado.getNome()!=null){
                   mercado.setOdds(OddsDoJogo);
                   mercadosDoJogo.add(mercado);
                   mercado = new Mercados();
                   OddsDoJogo = new ArrayList<>();
                }
                mercado.setNome(th1.text());
                //System.out.println(mercado.getNome());
            }else if(th1 != null && th2 != null){//achou novo odd
                Odds novoOdd = new Odds();
                novoOdd.setId(th1.text());
                String str = th2.text().replaceAll(",", ".");
                if(str.equals("")){str = "0.00";}
                novoOdd.setValor(Double.parseDouble(str));
                OddsDoJogo.add(novoOdd);
                //System.out.println(novoOdd.getId()+" --> "+novoOdd.getValor());
                if(ls.elementSiblingIndex()==tr.size()-1){//so entra pra salvar quando for a ultima linha do elemento
                    mercado.setOdds(OddsDoJogo);
                    mercadosDoJogo.add(mercado);
                    mercado = new Mercados();
                    OddsDoJogo = new ArrayList<>();
                }
            }
        }
        return mercadosDoJogo;
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
