package com.portea.cpnen.dao;

import com.portea.cpnen.domain.ProductAdapter;
import com.portea.cpnen.domain.ProductType;
import com.portea.dao.Dao;

import javax.persistence.NoResultException;
import java.util.Date;
import java.util.List;

public interface ProductAdapterDao extends Dao<Integer, ProductAdapter> {

    ProductAdapter getProductAdapter(Integer productId, ProductType type) throws NoResultException;

    /**
     * Returns a list of object array which has product data from service (doesn't include sub-service)
     * and package (which is not marked as deleted). By default the results are sorted by name in ascending order.
     */
    List<Object[]> getProducts(Integer offset, Integer limit);

    /**
     * Returns the total count of services (doesn't include sub-service) and packages (which is not marked as deleted).
     */
    Long getProductCount();

    List<ProductAdapter> getProductAdapters(List<Integer> productIds, ProductType productType);

    ProductAdapter create(Integer productId, ProductType productType, Date date, String name);
}
