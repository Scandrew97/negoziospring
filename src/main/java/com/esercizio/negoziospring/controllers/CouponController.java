package com.esercizio.negoziospring.controllers;

import com.esercizio.negoziospring.entities.Coupon;
import com.esercizio.negoziospring.entities.Sottocategoria;
import com.esercizio.negoziospring.repository.CouponRepository;
import com.esercizio.negoziospring.repository.SottocategorieRepository;
import com.esercizio.negoziospring.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200/")
public class CouponController {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private SottocategorieRepository sottocategorieRepository;

    @GetMapping("/admin/couponbysottocategoria/{codiceSottocategoria}")
    public ResponseEntity<List<Coupon>> findCouponByCodiceSottocategoria(@PathVariable int codiceSottocategoria){
        List<Coupon> response = couponRepository
                .findAllBySottocategoria_CodiceSottocategoria(codiceSottocategoria)
                .orElse(null);
        return Utility.checkIfCouponResponseEntityNotFund(response);
    }

    @GetMapping("/adminuser/couponbycodicesconto/{codiceSconto}")
    public ResponseEntity<Coupon> findCouponByCodiceSconto(@PathVariable String codiceSconto){
        Coupon response = couponRepository.findByCodiceSconto(codiceSconto).orElse(null);
        return Utility.checkIfCouponResponseEntityNotFund(response);

    }

    @GetMapping("/admin/couponbyidsottocategoriaidcoupon/{codiceSottocategoria}/{codiceCoupon}")
    public ResponseEntity<Coupon> findCouponByIdAndCodiceSottocateegoria(@PathVariable int codiceSottocategoria,
                                                                         @PathVariable int codiceCoupon){
        Coupon response = couponRepository
                .findByCodiceIdentificativoAndSottocategoria_CodiceSottocategoria(codiceCoupon,codiceSottocategoria)
                .orElse(null);
        return Utility.checkIfCouponResponseEntityNotFund(response);
    }

    @PostMapping("/admin/coupon/{codiceSottocategoria}")
    public ResponseEntity<Coupon> createNewCoupon(@RequestBody Coupon creato,
                                                  @PathVariable int codiceSottocategoria){
        Coupon checkIfExists=couponRepository.findByCodiceSconto(creato.getCodiceSconto()).orElse(null);
        if(checkIfExists!=null){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Sottocategoria sottocategoria= sottocategorieRepository.findById(codiceSottocategoria).orElse(null);
        if (sottocategoria==null){
            return ResponseEntity.notFound().build();
        }
        creato.setSottocategoria(sottocategoria);
        return ResponseEntity.ok(couponRepository.save(creato));
    }

    @PutMapping("/admin/coupon/{codiceCoupon}")
    public ResponseEntity<Coupon> updateCoupon(@RequestBody Coupon modificato,
                                               @PathVariable int codiceCoupon){
        Coupon checkIfExists= couponRepository.findById(codiceCoupon).orElse(null);
        if (checkIfExists==null){
            return ResponseEntity.notFound().build();
        }
        modificato.setSottocategoria(sottocategorieRepository
                .findById(checkIfExists.getSottocategoria().getCodiceSottocategoria()).get());
        modificato.setCodiceIdentificativo(checkIfExists.getCodiceIdentificativo());
        return ResponseEntity.ok(couponRepository.save(modificato));
    }

    @DeleteMapping("/admin/coupon/{codiceCoupon}")
    public ResponseEntity<Void> deleteCoupon(@PathVariable int codiceCoupon){
        Coupon checkIfExists= couponRepository.findById(codiceCoupon).orElse(null);
        if (checkIfExists==null){
            return ResponseEntity.notFound().build();
        }
        couponRepository.deleteById(codiceCoupon);
        return ResponseEntity.accepted().build();
    }

}
