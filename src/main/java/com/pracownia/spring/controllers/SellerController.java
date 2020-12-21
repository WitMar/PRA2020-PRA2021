package com.pracownia.spring.controllers;

import com.pracownia.spring.entities.Seller;
import com.pracownia.spring.services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class SellerController {

    @Autowired
    private SellerService sellerService;

    @GetMapping(value = "/sellers", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Seller> list(Model model) {
        return sellerService.listAllSellers();
    }

    // Only for redirect!
    @ApiIgnore
    @DeleteMapping(value = "/sellers", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Seller> redirect(Model model) {
        return sellerService.listAllSellers();
    }

    @PostMapping(value = "/seller")
    public ResponseEntity<Seller> create(@RequestBody @Validated(Seller.class) @NonNull Seller seller) {
        sellerService.saveSeller(seller);
        return ResponseEntity.ok().body(seller);
    }

    @PutMapping(value = "/seller")
    public ResponseEntity<Void> edit(@RequestBody Seller seller) {
            Optional<Seller> sellerFromData = sellerService.getSellerById(seller.getId());
            if(Objects.nonNull(sellerFromData)) {
                sellerService.saveSeller(seller);
                return new ResponseEntity<>(HttpStatus.CREATED);
            } else
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/seller/{id}", method = RequestMethod.DELETE)
    public RedirectView delete(HttpServletResponse response, @PathVariable Integer id) {
        sellerService.deleteSeller(id);
        return new RedirectView("/api/sellers", true);
    }

    @RequestMapping(value = "/seller/{name}", method = RequestMethod.GET)
    public List<Seller> getByName(@PathVariable String name) {
        return sellerService.getByName(name);
    }

    @RequestMapping(value = "/seller/products/{id}", method = RequestMethod.GET)
    public Integer getProductsSize(@PathVariable Integer id) {
        return sellerService.getNumberOfProducts(id);
    }

    @RequestMapping(value = "/seller/best", method = RequestMethod.GET)
    public Optional<Seller> getBestSeller() {
        return sellerService.getBestSeller();
    }

    @GetMapping(value = "/seller/{id}", produces = MediaType.APPLICATION_ATOM_XML_VALUE)
    @ResponseBody
    public Seller getByPublicId(@PathVariable("id") Integer publicId) {
        return sellerService.getSellerById(publicId).get();
    }
}
