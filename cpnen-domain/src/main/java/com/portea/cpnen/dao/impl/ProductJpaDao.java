package com.portea.cpnen.dao.impl;

import com.portea.cpnen.dao.ProductDao;
import com.portea.cpnen.domain.Product;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Arrays;
import java.util.List;

@JpaDao
@Dependent
public class ProductJpaDao extends BaseJpaDao<Integer, Product> implements ProductDao {

    public ProductJpaDao() {
        super(Product.class);
    }

    @Override
    @PersistenceContext(name = "cpnenPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private void validateProduct(int prodId) {

    }

    @Override
    public List<Product> getProductsForIds(Integer[] productIds) {
        List<Integer> prodIdList = Arrays.asList(productIds);
        Query query = entityManager.createNamedQuery("getProductsForIds", Product.class).setParameter("ids", prodIdList);
        List<Product> list = query.getResultList();
        return list;
    }

    @Override
    public Integer getCountOfProducts() {
        Query totalSizeQuery = entityManager.createNamedQuery("totalProdCount");
        Integer count = ((Long) totalSizeQuery.getSingleResult()).intValue();
        return count;
    }

    @Override
    public Integer getParentProdId(Integer productId) {
        Query query = entityManager.createNamedQuery("getParentProdID").setParameter("id", productId);
        List list = query.getResultList();
        Integer parentId = (Integer) list.get(0);
        return parentId;
    }

}
