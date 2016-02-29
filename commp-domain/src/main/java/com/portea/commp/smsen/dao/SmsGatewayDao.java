package com.portea.commp.smsen.dao;

import com.portea.commp.smsen.domain.SmsGateway;
import com.portea.dao.Dao;

import javax.persistence.NoResultException;
import java.util.List;

public interface SmsGatewayDao extends Dao<Integer,SmsGateway> {

    SmsGateway find(String gatewayName) throws NoResultException;

    List<SmsGateway> getGateways();
}
