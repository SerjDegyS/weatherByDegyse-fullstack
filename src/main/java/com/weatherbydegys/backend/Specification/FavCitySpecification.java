package com.weatherbydegys.backend.Specification;

import com.weatherbydegys.backend.model.FavCity;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class FavCitySpecification {
    public static Specification<FavCity> favCityNameStartBy(final String nameStart) {
        return new Specification<FavCity>() {
            @Override
            public Predicate toPredicate(Root<FavCity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(root.get("name"), nameStart+"%");
            }
        };
    }
}
