package vttp.ssf.practice_test.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.JsonObject;
import vttp.ssf.practice_test.model.Product;
import vttp.ssf.practice_test.repository.MapRepository;

@Service
public class ProductService {
    
    @Autowired
    private MapRepository mapRepo;
    
    public List<Product> getProducts() {
        return mapRepo.getAllProducts();
    }

    public Product updateProduct(int id) {
        Product p = mapRepo.getProductById(id);
        if(p.getStock() > p.getBuy()) {
            p.setBuy(p.getBuy() + 1);
            mapRepo.updateProduct(p);
            return p;
        }
        return null;
    }
}
