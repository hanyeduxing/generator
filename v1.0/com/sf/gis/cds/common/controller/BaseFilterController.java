package com.sf.gis.cds.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

@Controller
public class BaseFilterController {

    @ApiIgnore
    @RequestMapping("/**/{path:[^\\\\.]+}")
    public String hello(){
        return "forward:/";
    }

}
