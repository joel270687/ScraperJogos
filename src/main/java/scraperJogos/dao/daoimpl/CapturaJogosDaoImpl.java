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
import java.util.*;

public class CapturaJogosDaoImpl implements CapturaJogosDao {
    private static CapturaMercadoJogoDao capturaMercadoJogoDao = new CapturaMercadoJogoDaoImpl();
    @Override
    public List<Jogos> capturaJogosDoDia(String url) throws IOException, ParseException {
        //Conectando na url usando o Jsoup
        Document doc = Jsoup.connect(url).get(); //pegga toda a página html
        System.out.println("Capturando jogos da : " + url);

        //pegando os jogos (a tabela) por Class
        Element eventListContainer = doc.getElementsByClass("eventlistContainer").first();
        Elements listaElementsJogos = eventListContainer.getElementsByClass("cardItem cardItem3");

        List<Jogos> listaJogos = new ArrayList<>();
        for(Element lista : listaElementsJogos){
            String dataJogo = lista.getElementsByClass("date").first().text();
            String horaJogo = lista.getElementsByClass("hour").first().text();

            Jogos jogo = new Jogos();
            jogo.setCasa(lista.getElementsByClass("nameTeam").first().text());
            jogo.setLogoCasa(lista.select("img").first().absUrl("src"));
            jogo.setFora(lista.getElementsByClass("nameTeam").last().text());
            jogo.setLogoFora(lista.select("img").last().absUrl("src"));
            jogo.setData(retornaDataMontada(dataJogo,horaJogo));
            jogo.setLink(lista.getElementsByClass("totalOutcomes-button").first()
                    .select("a[href]").attr("abs:href"));
            jogo.setMercados(capturaMercadoJogoDao.findMercado(jogo.getLink()));
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

    private enum Meses {
        jan("01"), fev("02"), mar("03"), abr("04"), mai("05"), jun("06"),
        jul("07"), ago("08"), set("09"), out("10"), nov("11"), dez("12");

        private String mesNumeral;

        private Meses(String mesNumeral){
            this.mesNumeral = mesNumeral;
        }

        public String getMesNumeral(){
            return this.mesNumeral;
        }
    }

    private Date retornaDataMontada(String dataJogo, String horaJogo) throws ParseException {
        String dia = dataJogo.substring(0,2);
        String mes = Meses.valueOf(dataJogo.substring(3,6)).getMesNumeral();
        String ano = String.valueOf(new Date().getYear()+1900); //para aparecer o ano corretamente em string
        String hora = horaJogo.substring(0,2);
        String minuto = horaJogo.substring(3,5);
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        Date dataCompleta = formatter.parse(dia + "/"+mes+"/"+ano+" "+hora+":"+minuto);
        return dataCompleta;
    }

}
