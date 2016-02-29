package com.portea.commp.web.rapi.service;

import com.portea.commp.web.rapi.domain.*;
import com.portea.commp.web.rapi.ejb.CommpWebRequestProcessor;
import com.portea.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("web/rws")
public class CommpWebRestService {
    private static final Logger LOG = LoggerFactory.getLogger(CommpWebRestService.class);

    @EJB
    private CommpWebRequestProcessor commpWebReqProcessor;

    //=======================Evict Cache===========================

    @PUT
    @Path("entity/{name}/evictCache")
    public Response evictCache(@PathParam("name") String fqClassName) {
        LogUtil.entryTrace("evictCache", LOG);

        commpWebReqProcessor.evictCache(fqClassName);

        LogUtil.exitTrace("evictCache", LOG);
        return Response.status(Response.Status.OK).build();
    }

    @PUT
    @Path("entities/evictCache")
    public Response evictAllCache() {
        LogUtil.entryTrace("evictAllCache", LOG);

        commpWebReqProcessor.evictAllCache();

        LogUtil.exitTrace("evictAllCache", LOG);
        return Response.status(Response.Status.OK).build();
    }

    //=======================Common Config===========================

    @GET
    @Produces("application/json")
    @Path("targetConfigParam/list")
    public List<TargetConfigValueVo> getTargetConfigValues() {

        LogUtil.entryTrace("getTargetConfigValues", LOG);

        List<TargetConfigValueVo> targetConfigValueVos = commpWebReqProcessor.getTargetConfigValues();

        LogUtil.exitTrace("getTargetConfigValues", LOG);

        return targetConfigValueVos;
    }

    @PUT
    @Consumes("application/json")
    @Path("targetConfigValue")
    public Response updateTargetConfigValue(@Context HttpServletRequest request,
                                            TargetConfigUpdateValueReq targetConfigUpdateValueReq) {

        LogUtil.entryTrace("updateTargetConfigValue", LOG);

        HttpSession session = request.getSession(false);

        Integer userId = (Integer) session.getAttribute("userId");
        commpWebReqProcessor.updateTargetConfigValue(userId, targetConfigUpdateValueReq);

        LogUtil.exitTrace("updateTargetConfigValue", LOG);

        return Response.status(Response.Status.OK).build();
    }

    //=======================Sms Group & Sms Type===========================

    @GET
    @Produces("application/json")
    @Path("smsGroup/list")
    public List<SmsGroupVo> getSmsGroups() {

        LogUtil.entryTrace("getSmsGroups", LOG);

        List<SmsGroupVo> smsGroupVos = commpWebReqProcessor.getSmsGroups();

        LogUtil.entryTrace("getSmsGroups", LOG);

        return smsGroupVos;
    }

    @GET
    @Produces("application/json")
    @Path("smsType/list")
    public List<SmsTypeVo> getSmsTypes() {

        LogUtil.entryTrace("getSmsTypes", LOG);

        List<SmsTypeVo> smsTypeVos = commpWebReqProcessor.getSmsTypes();

        LogUtil.entryTrace("getSmsTypes", LOG);

        return smsTypeVos;
    }

    @PUT
    @Consumes("application/json")
    @Path("smsGroup/{id}")
    public Response updateSmsGroup(@Context HttpServletRequest request,
                                   @PathParam("id") Integer id, CoolingPeriodUpdateVo coolingPeriodUpdateVo) {

        LogUtil.entryTrace("updateSmsGroup", LOG);

        HttpSession session = request.getSession(false);

        Integer userId = (Integer) session.getAttribute("userId");
        commpWebReqProcessor.updateSmsGroup(userId, id, coolingPeriodUpdateVo);

        LogUtil.entryTrace("updateSmsGroup", LOG);

        return Response.status(Response.Status.OK).build();
    }

    @PUT
    @Consumes("application/json")
    @Path("smsType/{id}")
    public Response updateSmsType(@Context HttpServletRequest request,
                                  @PathParam("id") Integer id, CoolingPeriodUpdateVo coolingPeriodUpdateVo) {

        LogUtil.entryTrace("updateSmsType", LOG);

        HttpSession session = request.getSession(false);

        Integer userId = (Integer) session.getAttribute("userId");
        commpWebReqProcessor.updateSmsType(userId, id, coolingPeriodUpdateVo);

        LogUtil.exitTrace("updateSmsType", LOG);

        return Response.status(Response.Status.OK).build();
    }

    //=======================Gateway Mapping===========================

    @GET
    @Produces("application/json")
    @Path("gateway/list")
    public List<SmsGatewayVo> getGateways() {
        LogUtil.entryTrace("getGateways", LOG);

        List<SmsGatewayVo> gatewayVos = commpWebReqProcessor.getGateways();

        LogUtil.exitTrace("getGateways", LOG);
        return gatewayVos;
    }

    @GET
    @Produces("application/json")
    @Path("group/gateway/mapping")
    public List<GroupGatewayMappingVo> getGroupGatewayMapping() {

        LogUtil.entryTrace("getGroupGatewayMapping", LOG);

        List<GroupGatewayMappingVo> groupGatewayMappingVos = commpWebReqProcessor.getGroupGatewayMapping();

        LogUtil.exitTrace("getGroupGatewayMapping", LOG);
        return groupGatewayMappingVos;
    }

    @POST
    @Consumes("application/json")
    @Path("group/gateway")
    public Response crudGroupGatewayMapping(@Context HttpServletRequest request,
                                            CRUDGroupGatewayMappingVo CRUDGroupGatewayMappingVo) {

        LogUtil.entryTrace("crudGroupGatewayMapping", LOG);

        HttpSession session = request.getSession(false);

        Integer userId = (Integer) session.getAttribute("userId");
        commpWebReqProcessor.crudGroupGatewayMapping(userId, CRUDGroupGatewayMappingVo);

        LogUtil.exitTrace("crudGroupGatewayMapping", LOG);

        return Response.status(Response.Status.OK).build();
    }

    //=======================Sms Sender===========================

    @GET
    @Produces("application/json")
    @Path("smsSender/list")
    public List<SmsSenderVo> getSmsSenders() {

        LogUtil.entryTrace("getSmsSenders", LOG);

        List<SmsSenderVo> smsSenderVos = commpWebReqProcessor.getSmsSenders();

        LogUtil.exitTrace("getSmsSenders", LOG);

        return smsSenderVos;
    }

    @POST
    @Produces("application/json")
    @Path("smsSender")
    public Integer createSmsSender(@Context HttpServletRequest request, SmsSenderCreateReq smsSenderCreateReq) {

        LogUtil.entryTrace("createSmsSender", LOG);

        HttpSession session = request.getSession(false);

        Integer userId = (Integer) session.getAttribute("userId");
        Integer id = commpWebReqProcessor.createSmsSender(userId, smsSenderCreateReq);

        LogUtil.exitTrace("createSmsSender", LOG);

        return id;
    }

    @PUT
    @Path("smsSender/{id}/deactivate")
    public Response deactivateSmsSender(@Context HttpServletRequest request, @PathParam("id") Integer smsSenderId) {

        LogUtil.entryTrace("deactivateSmsSender", LOG);

        HttpSession session = request.getSession(false);

        Integer userId = (Integer) session.getAttribute("userId");
        commpWebReqProcessor.deactivateSmsSender(userId, smsSenderId);

        LogUtil.exitTrace("deactivateSmsSender", LOG);

        return Response.status(Response.Status.OK).build();
    }

    @PUT
    @Path("smsSender/{id}/resetPassword/{password}")
    public Response resetPassword(@Context HttpServletRequest request,
                                  @PathParam("id") Integer smsSenderId, @PathParam("password") String password) {

        LogUtil.entryTrace("resetPassword", LOG);

        HttpSession session = request.getSession(false);

        Integer userId = (Integer) session.getAttribute("userId");
        commpWebReqProcessor.resetPassword(userId, smsSenderId, password);

        LogUtil.exitTrace("resetPassword", LOG);

        return Response.status(Response.Status.OK).build();
    }

    @PUT
    @Path("smsSender/{id}/reactivate")
    public Response reactivateSmsSender(@Context HttpServletRequest request, @PathParam("id") Integer smsSenderId) {
        LogUtil.entryTrace("reactivateSmsSender", LOG);

        HttpSession session = request.getSession(false);

        Integer userId = (Integer) session.getAttribute("userId");
        commpWebReqProcessor.reactivateSmsSender(userId, smsSenderId);

        LogUtil.exitTrace("reactivateSmsSender", LOG);

        return Response.status(Response.Status.OK).build();
    }

    //=======================Dashboard===========================

    @GET
    @Path("sms/source/usage")
    @Produces("application/json")
    public List<SmsSourceUsageData> getSmsSourceUsage() {
        LogUtil.entryTrace("getSmsSourceUsage", LOG);

        List<SmsSourceUsageData> smsSourceUsage = commpWebReqProcessor.getSmsSourceUsage();

        LogUtil.exitTrace("getSmsSourceUsage", LOG);
        return smsSourceUsage;
    }

    @GET
    @Path("sms/gateway/usage")
    @Produces("application/json")
    public List<SmsGatewayUsageData> getSmsGatewayUsage() {
        LogUtil.entryTrace("getSmsGatewayUsage", LOG);

        List<SmsGatewayUsageData> smsGatewayUsage = commpWebReqProcessor.getSmsGatewayUsage();

        LogUtil.entryTrace("getSmsGatewayUsage", LOG);
        return smsGatewayUsage;
    }

    @GET
    @Path("sms/daily/status")
    @Produces("application/json")
    public List<DailySentSmsData> getDailySmsStatus(@QueryParam("days") Integer days) {
        LogUtil.entryTrace("getDailySmsStatus", LOG);

        List<DailySentSmsData> smsUsage = commpWebReqProcessor.getDailySmsStatus(days);

        LogUtil.exitTrace("getDailySmsStatus", LOG);
        return smsUsage;
    }

    @GET
    @Path("sms/daily/smsType")
    @Produces("application/json")
    public List<DailySentSmsTypeData> getDailySmsFailure(@QueryParam("days") Integer days) {
        LogUtil.entryTrace("getDailySmsType", LOG);

        List<DailySentSmsTypeData> smsFailures = commpWebReqProcessor.getDailySmsType(days);

        LogUtil.exitTrace("getDailySmsType", LOG);
        return smsFailures;
    }
}
