package com.portea.cpnen.dao;

import com.portea.cpnen.domain.Referrer;
import com.portea.dao.Dao;

import java.util.List;

public interface ReferrerDao extends Dao<Integer, Referrer> {

    List<Referrer> getReferrers(Integer offset, Integer limit);

    List<Referrer> getReferrersByBrandAndType(Integer brandId, String referrerType, Integer offset, Integer limit);

}
