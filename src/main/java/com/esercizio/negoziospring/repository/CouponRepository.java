package com.esercizio.negoziospring.repository;

import com.esercizio.negoziospring.entities.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon,Integer> {

    public Optional<List<Coupon>> findAllBySottocategoria_CodiceSottocategoria(int codiceSottocategoria);

    public Optional<Coupon> findByCodiceIdentificativoAndSottocategoria_CodiceSottocategoria(int codiceIdentificativo, int codiceSottocategoria);

    public Optional<Coupon> findByCodiceSconto(String codiceSconto);
}
