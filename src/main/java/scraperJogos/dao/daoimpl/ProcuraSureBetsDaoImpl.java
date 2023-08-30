package scraperJogos.dao.daoimpl;

import scraperJogos.dao.ProcuraSureBetsDao;
import scraperJogos.models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Joel on 25/05/2022.
 */
public class ProcuraSureBetsDaoImpl implements ProcuraSureBetsDao {
    public void verificaOddEntreDuasCasas(Casa casa1, Casa casa2){
        for(Jogos jgs1 : casa1.getJogosCasa()){
            for(Jogos jgs2 : casa2.getJogosCasa()){

                if(jgs1.getCasa().equals(jgs2.getCasa())){//se o mesmo jogo
                    System.out.println("\n"+jgs1.getCasa() + " (X) " + jgs1.getFora());
                    for(Mercados mr1:jgs1.getMercados()){
                        for(Mercados mr2:jgs2.getMercados()){

                            if(mr1.getNome().equals(mr2.getNome())){//se o mesmo mercado
                                System.out.println(mr1.getNome());
                                for(Odds odd1 :mr1.getOdds()){
                                    for(Odds odd2 : mr2.getOdds()){

                                        if(odd1.getId().equals(odd2.getId())){//se o mesmo id odd
                                            if(odd1.getValor()!=odd2.getValor()){
                                                System.out.println(odd1.getId() + " : " + odd1.getValor() + " <--> " + odd2.getValor());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public List<JuncaoJogos> verificaSureBetPorGatilho(List<JuncaoJogos> jncJogos, List<Gatilho> gat){
        List<JuncaoJogos> listaVerificada = new ArrayList<>();
        for(JuncaoJogos jncJogo : jncJogos){//para cada jogo
            int mostraJogo=0;
            for(JuncaoMercados jncMercado : jncJogo.getMercadosPorCasas()){//para cada lista de mercados por casa
                int mostraUrlCasa = 0;
                for(Mercados mrc : jncMercado.getMercadoCasa()){//para cada mercado
                        //começa os gatilhos
                        int mostraNomeGatilho=0;
                        for(Gatilho gt : gat){
                            if(gt.getGatilhoMercado().equals(mrc.getNome())) {
                                if(mostraJogo<=0){
                                    //System.out.println("\n\n\n"+jncJogo.getCasa() + "  (X)  " + jncJogo.getFora()
                                      //      + "      ==>  " + new SimpleDateFormat("HH:mm").format(jncJogo.getData()));
                                }
                                mostraJogo++;
                                if(mostraUrlCasa<=0){
                                    //System.out.println(jncMercado.getUrlCasa());
                                }
                                mostraUrlCasa++;
                                for(String idGat : gt.getItemMercado()){
                                    for(Odds odd : mrc.getOdds()){
                                        if(odd.getId().equals(idGat)){
                                            if(mostraNomeGatilho<=0){
                                                //System.out.println("---  "+mrc.getNome()+"  ---");
                                            }
                                            mostraNomeGatilho++;
                                            //System.out.println(odd.getId() + " -> " + odd.getValor());
                                            listaVerificada.add(jncJogo);//TESTE de RETORNO
                                        }
                                    }
                                }
                            }
                        }
                }
            }
        }
        return listaVerificada;
    }

    @Override
    public List<JuncaoJogos> criaListaAllJogos(List<Casa> listaCasas) {
        List<JuncaoJogos> listaJogosSeparados = new ArrayList<>();

        for(Casa cs : listaCasas){//criar lista com jogos existentes nas casas informadas
            for(Jogos jg : cs.getJogosCasa()){
                if(!existeJogoNaLista(jg, listaJogosSeparados)){//se não existe cria novo jogo
                    JuncaoJogos juncaoJogos = new JuncaoJogos();
                    juncaoJogos.setCasa(jg.getCasa());
                    juncaoJogos.setLogoCasa(jg.getLogoCasa());
                    juncaoJogos.setFora(jg.getFora());
                    juncaoJogos.setLogoFora(jg.getLogoFora());
                    juncaoJogos.setData(jg.getData());

                    listaJogosSeparados.add(juncaoJogos);
                }
            }
        }
        return listaJogosSeparados;
    }

    @Override
    public List<JuncaoJogos> atualizaOddsAllJogosJuntandoCasas(List<Casa> listaCasas, List<JuncaoJogos> listaAllJogos) {
        List<JuncaoJogos> listaAllJogosComOdds = new ArrayList<>();
        for(JuncaoJogos jnc : listaAllJogos){
            JuncaoJogos jncJogoComOdds = new JuncaoJogos();
            jncJogoComOdds.setCasa(jnc.getCasa());
            jncJogoComOdds.setFora(jnc.getFora());
            jncJogoComOdds.setData(jnc.getData());
            List<JuncaoMercados> jncMercado = new ArrayList<>();
            for(Casa cs : listaCasas){
                for(Jogos jg : cs.getJogosCasa()){
                    if(jg.getCasa().equals(jnc.getCasa())){
                        JuncaoMercados mercadoDesseJogoNessaCasa = new JuncaoMercados();
                        mercadoDesseJogoNessaCasa.setNomeCasa(cs.getNomeCasa());
                        mercadoDesseJogoNessaCasa.setUrlCasa(jg.getLink());
                        mercadoDesseJogoNessaCasa.setMercadoCasa(jg.getMercados());
                        jncMercado.add(mercadoDesseJogoNessaCasa);
                    }
                }
                jncJogoComOdds.setMercadosPorCasas(jncMercado);
            }
            listaAllJogosComOdds.add(jncJogoComOdds);
        }
        return listaAllJogosComOdds;
    }

    @Override
    public List<Jogos> separaPorGatilho(List<Jogos> listJogos, List<Gatilho> gatilhos) {
        List<Jogos> tempJogosList = new ArrayList<>();

        Gatilho gatilho = new Gatilho();
        gatilho.setGatilhoMercado("Ímpar/Par");
        gatilho.setItemMercado(new String[]{"ímpar","par"});

        for(Jogos jg : listJogos){
            List<Mercados> tempMercados = jg.getMercados().stream()
                    .filter( (Mercados m) -> m.getNome().equals( gatilho.getGatilhoMercado()))
                    .collect(Collectors.toList());
            Jogos tempJogos = new Jogos();
            tempJogos.setCasa(jg.getCasa());
            tempJogos.setFora(jg.getFora());
            tempJogos.setData(jg.getData());
            tempJogos.setMercados(tempMercados);
            tempJogosList.add(tempJogos);
        }
        return tempJogosList;
    }

    @Override
    public List<JuncaoJogos> filtraByGatilho(List<JuncaoJogos> listJogos, Gatilho gatilho) {

        gatilho = new Gatilho();
        gatilho.setGatilhoMercado("Ímpar/Par");
        gatilho.setItemMercado(new String[]{"ímpar","par"});

        List<JuncaoJogos> juncaoJogosTempList = new ArrayList<>();

        for(JuncaoJogos jj : listJogos){
            JuncaoJogos tempJJ = new JuncaoJogos();
            tempJJ.setCasa(jj.getCasa());
            tempJJ.setLogoCasa(jj.getLogoCasa());
            tempJJ.setFora(jj.getFora());
            tempJJ.setLogoFora(jj.getLogoFora());
            tempJJ.setData(jj.getData());
            for(JuncaoMercados jm : jj.getMercadosPorCasas()){
                List<Mercados> tempMercados = jm.getMercadoCasa().stream()
                        .filter( (Mercados m) -> m.getNome().equals( "Ímpar/Par"))
                        .collect(Collectors.toList());
                JuncaoMercados tempJM = new JuncaoMercados();
                tempJM.setUrlCasa(jm.getUrlCasa());
                tempJM.setNomeCasa(jm.getNomeCasa());
                tempJM.setMercadoCasa(tempMercados);
            }
        }
        return null;
    }


    private boolean existeJogoNaLista(Jogos jogo, List<JuncaoJogos> listaJogosSeparados) {
        for(JuncaoJogos jnc: listaJogosSeparados){
            if(jnc.getCasa().equals(jogo.getCasa()))
                return true;
        }
        return false;
    }

}