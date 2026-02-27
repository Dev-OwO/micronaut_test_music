package micronaut_test_music.controller;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.views.View;

@Controller
public class HomeController {

    @Get("/")
    @View("index")
    public void index() {
        
    }
}
