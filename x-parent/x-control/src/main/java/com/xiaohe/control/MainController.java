package com.xiaohe.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * TODO
 *
 * @author xiezhaohe
 * @version V1.0
 * @since 2019-02-28 20:48
 */
@Controller
@RequestMapping("/")
public class MainController {

    @RequestMapping("/")
    public String main() {
        return "redirect:public/loginPage";
    }
}
