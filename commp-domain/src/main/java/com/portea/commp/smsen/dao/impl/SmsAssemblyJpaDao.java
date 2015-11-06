package com.portea.commp.smsen.dao.impl;

import com.portea.commp.smsen.dao.SmsAssemblyDao;
import com.portea.commp.smsen.domain.SmsAssembly;
import com.portea.dao.JpaDao;
import com.portea.dao.impl.BaseJpaDao;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@JpaDao
@Dependent
public class SmsAssemblyJpaDao extends BaseJpaDao<Long, SmsAssembly> implements SmsAssemblyDao {

    public SmsAssemblyJpaDao() {
        super(SmsAssembly.class);
    }

    @Override
    @PersistenceContext(name = "commpPU")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<SmsAssembly> getNextBatchFromAssembly(int seconds, int resultSize) {
        Query query = entityManager.createNamedQuery("getNextBatchFromAssembly");
        query.setMaxResults(resultSize);

        LocalDateTime ldt = LocalDateTime.now();
        ldt.plusSeconds((long)seconds);

        Date date = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());

        query.setParameter("scheduledTime", date);

        return query.getResultList();
    }
}
