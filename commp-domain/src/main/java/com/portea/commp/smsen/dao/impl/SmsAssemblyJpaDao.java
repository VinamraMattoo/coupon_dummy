package com.portea.commp.smsen.dao.impl;

import com.portea.commp.smsen.dao.SmsAssemblyDao;
import com.portea.commp.smsen.domain.SmsAssembly;
import com.portea.commp.smsen.domain.SmsPrimaryProcessingState;
import com.portea.commp.smsen.domain.SmsSecondaryProcessingState;
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
import java.util.concurrent.TimeUnit;

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
    public List<SmsAssembly> getNextBatchFromAssembly(int milliSec, int resultSize) {
        Long seconds = TimeUnit.MILLISECONDS.toSeconds(milliSec);
        Query query = entityManager.createNativeQuery(
                "SELECT * FROM `smsen_sms_assembly` WHERE scheduled_time < " +
                        "DATE_ADD(CONVERT_TZ(now(), (SELECT @@time_zone), scheduled_time_zone), INTERVAL "+
                        seconds+" SECOND) AND primary_processing_status = 'CREATED_FOR_SUBMISSION' " +
                        "AND secondary_processing_status IS NULL ", SmsAssembly.class);
        query.setMaxResults(resultSize);

        return query.getResultList();
    }

    @Override
    public void updateSmsStatus(List<Long> smsAssemblyIds, SmsPrimaryProcessingState primaryProcessingState,
                                SmsSecondaryProcessingState secondaryProcessingState) {
        Query query = entityManager.createNamedQuery("updateSmsAssemblyStatus");
        query.setParameter("ids", smsAssemblyIds);

        query.setParameter("primaryStatus", primaryProcessingState);
        query.setParameter("secondaryStatus", secondaryProcessingState);
        query.executeUpdate();
    }

    @Override
    public List<SmsAssembly> getNextSmsBatchForStatusCheck(Integer limit) {
        Query query = entityManager.createNamedQuery("getNextStatusBatchFromAssembly");
        query.setMaxResults(limit);

        query.setParameter("submissionCompleted", SmsPrimaryProcessingState.SUBMISSION_COMPLETED);
        query.setParameter("statusCheckCompleted", SmsPrimaryProcessingState.STATUS_CHECK_COMPLETED);
        query.setParameter("successfulSubmission", SmsSecondaryProcessingState.SUCCESSFUL_SUBMISSION);
        query.setParameter("readyForStatusCheck", SmsSecondaryProcessingState.READY_FOR_STATUS_CHECK);

        return query.getResultList();
    }

}
