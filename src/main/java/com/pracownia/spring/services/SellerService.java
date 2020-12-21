package com.pracownia.spring.services;

import com.pracownia.spring.entities.Seller;

import java.util.List;
import java.util.Optional;

public interface SellerService {

    Iterable<Seller> listAllSellers();

    Optional<Seller> getSellerById(Integer id);

    Seller saveSeller(Seller seller);

    void deleteSeller(Integer id);

    List<Seller> getByName(String name);

    Integer getNumberOfProducts(Integer id);

    Optional<Seller> getBestSeller();
}
