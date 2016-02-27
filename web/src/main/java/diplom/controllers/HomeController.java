package diplom.controllers;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * Created on 27.02.2016.
 */
@Controller
@RequestMapping(value = "/")
public class HomeController {
    @RequestMapping(value = "/")
    public String index(){
        return "index";
    }
}
