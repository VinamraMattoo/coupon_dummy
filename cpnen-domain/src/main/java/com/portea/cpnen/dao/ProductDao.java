package com.portea.cpnen.dao;

import com.portea.cpnen.domain.Product;
import com.portea.dao.Dao;

import java.util.List;

public interface ProductDao extends Dao<Integer, Product> {

    List<Product> getProductsForIds(Integer[] productIds);

    Integer getCountOfProducts();

    Integer getParentProdId(Integer productId);

}
