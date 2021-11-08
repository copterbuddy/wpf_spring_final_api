package com.example.wallet_transfer_service.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

import org.springframework.beans.factory.annotation.Autowired;

import io.grpc.netty.shaded.io.netty.util.internal.StringUtil;

import com.example.wallet_transfer_service.model.Customer;
import com.example.wallet_transfer_service.utils.EnumUtil;

public class CustomerRepositoryCustomImpl implements CustomerRepositoryCustom {

    @Autowired
    EntityManager em;

    @Autowired
    EnumUtil enumUtil;

    public List<Customer> searchCustomers(String searchText, String searchType) {
        List<Customer> response = null;

        String[] searchArray = null;
        Boolean isOneText = true;
        List<String> tempSearchList = new ArrayList<>();
        List<String> searchList = new ArrayList<>();

        if (searchType.equals("name")) {

            // TODO: CheckWhiteSpace
            Boolean isCheckWhiteSpace = false;
            for (int i = 0; i < searchText.length(); i++) {
                if (Character.isWhitespace(searchText.charAt(i))) {
                    isCheckWhiteSpace = true;
                    break;
                }
            }

            // TODO: Conver TO List && Delete Prefix
            if (isCheckWhiteSpace) {
                searchArray = searchText.split("\\s+");
                tempSearchList = Arrays.asList(searchArray);
                isOneText = false;
                for (String item : tempSearchList) {
                    if (!item.equals("นาย") && !item.equals("นาง") && !item.equals("นางสาว") && !item.equals("นางสาว")
                            && !item.equals("ด.ช.") && !item.equals("ด.ญ.") && !item.equals("เด็กชาย")
                            && !item.equals("เด็กหญิง")) {
                        searchList.add(item);
                    }
                }
            } else {
                isOneText = true;
                searchList.add(searchText);
            }

        } else {
            searchList.add(searchText);
        }

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();

        Root<Customer> customer = cq.from(Customer.class);

        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtil.isNullOrEmpty(searchText) && !StringUtil.isNullOrEmpty(searchType)) {
            if (!searchType.equals("name")) {
                predicates.add(cb.equal(customer.get(searchType), searchText));
            } else {
                if (isOneText) {
                    var name = cb.like(customer.get("firstnameTh"), "%" + searchText + "%");
                    var lastname = cb.like(customer.get("lastnameTh"), "%" + searchText + "%");
                    predicates.add(cb.or(name, lastname));
                } else {
                    if (searchList.size() == 2) {
                        var name = cb.like(customer.get("firstnameTh"), "%" + searchList.get(0) + "%");
                        var lastname = cb.like(customer.get("lastnameTh"), "%" + searchList.get(1) + "%");
                        predicates.add(cb.and(name, lastname));
                    }
                }
            }
        }

        cq.select(customer).where(predicates.toArray(new Predicate[0]));

        TypedQuery<Customer> query = em.createQuery(cq);

        response = query.getResultList();

        return response;
    }
}
