package com.portea.commp.web.rapi.ejb;

import com.portea.commp.web.rapi.domain.*;

import javax.ejb.Local;
import java.util.List;

@Local
public interface CommpWebRequestProcessor {

    void evictCache(String fqClassName);

    void evictAllCache();

    List<SmsGroupVo> getSmsGroups();

    List<SmsTypeVo> getSmsTypes();

    void updateSmsGroup(Integer userId, Integer smsGroupId, CoolingPeriodUpdateVo coolingPeriodUpdateVo);

    void updateSmsType(Integer userId, Integer smsTypeId, CoolingPeriodUpdateVo coolingPeriodUpdateVo);

    List<GroupGatewayMappingVo> getGroupGatewayMapping();

    void crudGroupGatewayMapping(Integer userId, CRUDGroupGatewayMappingVo CRUDGroupGatewayMappingVo);

    List<SmsSenderVo> getSmsSenders();

    Integer createSmsSender(Integer userId, SmsSenderCreateReq smsSenderCreateReq);

    void deactivateSmsSender(Integer userId, Integer smsSenderId);

    void resetPassword(Integer userId, Integer smsSenderId, String password);

    List<TargetConfigValueVo> getTargetConfigValues();

    void updateTargetConfigValue(Integer userId, TargetConfigUpdateValueReq targetConfigUpdateValueReq);

    List<SmsGatewayVo> getGateways();

    void reactivateSmsSender(Integer userId, Integer smsSenderId);

    List<SmsSourceUsageData> getSmsSourceUsage();

    List<SmsGatewayUsageData> getSmsGatewayUsage();

    List<DailySentSmsData> getDailySmsStatus(Integer days);

    List<DailySentSmsTypeData> getDailySmsType(Integer days);
}
