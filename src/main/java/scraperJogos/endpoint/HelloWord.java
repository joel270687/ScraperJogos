package scraperJogos.endpoint;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Joel on 22/07/2022.
 */
@RestController
public class HelloWord {
    @GetMapping
    public String primeiraPagina(){
        return "Ola mundo!";
    }

}
