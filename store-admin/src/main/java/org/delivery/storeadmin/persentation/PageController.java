package org.delivery.storeadmin.persentation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("")
public class PageController {
    @RequestMapping(path = {"/","/main"})
    public ModelAndView main() {
        log.info("/ or /main");
        return new ModelAndView("main");
    }
    @RequestMapping("/order")
    public ModelAndView order() {
        return new ModelAndView("order/order");
    }
}
