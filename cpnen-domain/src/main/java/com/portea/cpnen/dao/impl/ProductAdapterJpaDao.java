package com.portea.cpnen.dao.impl;

import com.portea.cpnen.dao.ProductAdapterDao;
import com.portea.cpnen.domain.ProductAdapter;
import com.portea.cpnen.domain.ProductType;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@JpaDao
@Dependent
public class ProductAdapterJpaDao extends BaseJpaDao<Integer, ProductAdapter> implements ProductAdapterDao {

    public ProductAdapterJpaDao() {
        super(ProductAdapter.class);
    }

    @Override
    @PersistenceContext(name = "cpnenPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public ProductAdapter getProductAdapter(Integer productId, ProductType type) throws NoResultException{
        Query query = entityManager.createNamedQuery("getProductAdapter",ProductAdapter.class);
        query.setParameter("productId", productId);
        query.setParameter("type", type);
        return (ProductAdapter) query.getSingleResult();
    }

    @Override
    public List<Object[]> getProducts(Integer offset, Integer limit) { // TODO :: read query from configuration file.
        Query query = entityManager.createNativeQuery(
                "SELECT * FROM (SELECT id,name,'SERVICE' as type " +
                        "FROM services " +
                        "WHERE sub_service = 0 AND deleted = 0 " +
                "UNION " +
                        "SELECT id,name,'PACKAGE' as type " +
                        "FROM packages " +
                        "WHERE deleted = 0" +
                        ")AS `product` ORDER BY name ASC");

        if(limit != null){
            query.setFirstResult(offset);
            query.setMaxResults(limit);
        }

        List list = query.getResultList();
        return list;
    }

    @Override
    public Long getProductCount() {
        Query query = entityManager.createNamedQuery("getProductsCount");
        return (Long) query.getSingleResult();
    }

    @Override
    public List<ProductAdapter> getProductAdapters(List<Integer> productIds, ProductType productType) {
        Query query = entityManager.createNamedQuery("getProductAdapters", ProductAdapter.class);
        query.setParameter("ids", productIds);
        query.setParameter("type", productType);
        return query.getResultList();
    }

    @Override
    public ProductAdapter create(Integer productId, ProductType productType, Date date, String name) {
        ProductAdapter productAdapter = new ProductAdapter();
        productAdapter.setProductId(productId);
        productAdapter.setProductType(productType);

        productAdapter.setCreatedOn(new Date());
        productAdapter.setName(name);
        return create(productAdapter);
    }
}
