package com.qf.beat.team.index.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.best.team.producttype.TProductTypeApi;
import com.qf.entity.TProductType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class IndexController {

    @Reference
    private TProductTypeApi productTypeApi;

    @RequestMapping("getType")
    public String getType(Model model){
        List<TProductType> types = productTypeApi.getAll();
        System.out.println(types);
        model.addAttribute("types", types);
        return "index";
    }

}
