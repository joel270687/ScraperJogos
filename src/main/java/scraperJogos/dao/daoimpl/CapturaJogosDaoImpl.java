package scraperJogos.dao.daoimpl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import scraperJogos.dao.CapturaJogosDao;
import scraperJogos.dao.CapturaMercadoJogoDao;
import scraperJogos.models.Jogos;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CapturaJogosDaoImpl implements CapturaJogosDao {
    private static CapturaMercadoJogoDao capturaMercadoJogoDao = new CapturaMercadoJogoDaoImpl();
    @Override
    public List<Jogos> capturaJogosDoDia(String url) throws IOException, ParseException {
        //Conectando na url usando o Jsoup
        Document doc = Jsoup.connect(url).get(); //pegga toda a página html
        //pegando titulo por Id
        //Element titulo = doc.getElementById("conteudo_tituloCampeonato");
        System.out.println("Capturando jogos da : " + url);

        //pegando os jogos (a tabela) por ID
        Element eventListContainer = doc.getElementsByClass("eventlistContainer").first();
        Elements listaElementsJogos = eventListContainer.getElementsByClass("cardItem cardItem3");
        //listaTodos.addAll(lista1);
        //listaTodos.addAll(lista2);

        List<Jogos> listaJogos = new ArrayList<>();
        for(Element lista : listaElementsJogos){
            String dataJogo = lista.getElementsByClass("date").first().text();
            String horaJogo = lista.getElementsByClass("hour").first().text();

            DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
            final DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.ENGLISH);

            Jogos jogo = new Jogos();
            jogo.setCasa(lista.getElementsByClass("nameTeam").first().text());
            jogo.setLogoCasa(lista.getElementsByClass("logoTeam").first().text());
            jogo.setFora(lista.getElementsByClass("nameTeam").last().text());
            jogo.setLogoFora(lista.getElementsByClass("logoTeam").last().text());
            jogo.setData(null);
            jogo.setLink(lista.getElementsByClass("totalOutcomes-button").first()
                    .select("a[href]").attr("abs:href"));
            listaJogos.add(jogo);
        }
     return listaJogos;
    }

    @Override
    public void imprimeJogosDoDia(List<Jogos> lista) {
        System.out.println("\n\nJogos do dia\n");
        int contador=0;
            for (Jogos j : lista) {
                if(contador<=2) {//imprimindo somente os 2 primeiros jogos
                    System.out.println(j.getCasa());
                    System.out.println(j.getFora());
                    System.out.println(j.getData());
                    System.out.println(j.getLink());
                    capturaMercadoJogoDao.imprimeMercado(j.getMercados());
                    contador++;
                }
        }
        System.out.println("\n\nQuantidade de Jogos do dia: "+contador+"\n");
    }

    private String retornaTimeCasa(String nome){ return nome.substring(0, nome.indexOf(" x ")); }

    private String retornaTimeFora(String nome){
        return nome.substring(nome.indexOf(" x ")+3);
    }

}
