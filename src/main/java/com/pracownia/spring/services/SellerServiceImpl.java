package com.pracownia.spring.services;

import com.pracownia.spring.entities.Product;
import com.pracownia.spring.entities.Seller;
import com.pracownia.spring.repositories.ProductRepository;
import com.pracownia.spring.repositories.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Iterable<Seller> listAllSellers() {
        return sellerRepository.findAll();
    }

    @Override
    public Optional<Seller> getSellerById(Integer id) {
        return sellerRepository.findById(id);
    }

    @Override
    public Seller saveSeller(Seller seller) {
        return sellerRepository.save(seller);
    }

    @Override
    public void deleteSeller(Integer id) {
        sellerRepository.deleteById(id);
    }

    @Override
    public List<Seller> getByName(String name) {
        return sellerRepository.findByName(name);
    }

    @Override
    public Integer getNumberOfProducts(Integer id) {
        return sellerRepository.countProductsById(id);
    }

    @Override
    public Optional<Seller> getBestSeller() {
        double max = 0;
        int maxId = 0;
        Iterable<Seller> sellers = sellerRepository.findAll();
        for(Seller s : sellers) {
            double sum = 0.0;
            List<Product> products = sellerRepository.getProductsById(s.getId());
            for(Product pid : products) {
                sum += pid.getPrice().doubleValue();
            }
            if (sum > max) {
                max = sum;
                maxId = s.getId();
            }
        }
        return sellerRepository.findById(maxId);
    }

}
