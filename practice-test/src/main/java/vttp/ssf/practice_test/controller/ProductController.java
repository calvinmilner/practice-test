package vttp.ssf.practice_test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vttp.ssf.practice_test.model.Product;
import vttp.ssf.practice_test.service.ProductService;

@Controller
@RequestMapping
public class ProductController {
    
    @Autowired
    private ProductService prodServ;

    @GetMapping("/products")
    public String getProducts(Model model) {
        List<Product> productList = prodServ.getProducts();
        model.addAttribute("products", productList);
        return "products";
    }

    @PostMapping("/buy")
    public ModelAndView postBuy(@RequestParam int id) {
        ModelAndView mav = new ModelAndView();
        Product p = prodServ.updateProduct(id);
        if(p == null) {
            mav.setViewName("unavailable");
        } else {
            mav.addObject("product", p);
            mav.setViewName("bought");
        }

        return mav;

    }
}
