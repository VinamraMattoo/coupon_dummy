package com.portea.commp.web.rapi.domain;

import com.portea.commp.web.rapi.exception.IncompleteRequestException;
import com.portea.commp.web.rapi.exception.InvalidRequestException;
import com.portea.commp.web.rapi.exception.MultipleGatewaySamePriorityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.BadRequestException;
import java.util.*;

public class CRUDGroupGatewayMappingVo {

    private static final Logger LOG = LoggerFactory.getLogger(CRUDGroupGatewayMappingVo.class);
    private Integer groupId;
    List<GatewayPriorityMapping> gatewayPriorityList;

    public CRUDGroupGatewayMappingVo() {
    }

    public List<GatewayPriorityMapping> getGatewayPriorityList() {
        return gatewayPriorityList;
    }

    public void setGatewayPriorityList(List<GatewayPriorityMapping> gatewayPriorityList) {
        this.gatewayPriorityList = gatewayPriorityList;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public void validate() {
        if (groupId == null) {
            LOG.debug("Group id is null");
            throw new IncompleteRequestException("groupId");
        }
        else if (gatewayPriorityList == null) {
            LOG.debug("GatewayPriorityList is null");
            throw new IncompleteRequestException("gatewayPriorityList");
        }
        Map<Integer, GatewayPriorityMapping> priorityMap = new HashMap<>();

        gatewayPriorityList.forEach(gatewayPriorityMapping -> {
            gatewayPriorityMapping.validate(gatewayPriorityList.indexOf(gatewayPriorityMapping));

            Integer priority = gatewayPriorityMapping.getPriority();

            if (priority != null) {
                GatewayPriorityMapping prevMapping = priorityMap.put(priority, gatewayPriorityMapping);
                if (prevMapping != null) {
                    LOG.debug("Given gatewayPriorityList contains same priority for more than one mapping: " + gatewayPriorityList.toString());
                    throw new MultipleGatewaySamePriorityException(prevMapping.getGatewayId(),
                            gatewayPriorityMapping.getGatewayId(), priority);
                }
            }
        });
    }

    public static class GatewayPriorityMapping {
        private Integer gatewayId;
        private Integer priority;

        public GatewayPriorityMapping() {
        }

        public Integer getGatewayId() {
            return gatewayId;
        }

        public void setGatewayId(Integer gatewayId) {
            this.gatewayId = gatewayId;
        }

        public Integer getPriority() {
            return priority;
        }

        public void setPriority(Integer priority) {
            this.priority = priority;
        }

        public void validate(Integer index) {
            if (gatewayId == null) {
                LOG.debug("Gateway id is null");
                throw new IncompleteRequestException("gatewayPriorityList["+index+"].gatewayId");
            }
            if (priority != null && priority <= 0) {
                LOG.debug("Priority should be positive integer");
                throw new InvalidRequestException("gatewayPriorityList["+index+"].priority", priority);
            }
        }

        @Override
        public String toString() {
            return "GatewayPriorityMapping{" +
                    "gatewayId=" + gatewayId +
                    ", priority=" + priority +
                    '}';
        }
    }
}
