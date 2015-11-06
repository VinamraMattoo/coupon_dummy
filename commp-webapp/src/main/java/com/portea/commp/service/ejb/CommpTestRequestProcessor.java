package com.portea.commp.service.ejb;

import javax.ejb.Local;

@Local
public interface CommpTestRequestProcessor {

    /**
     * Creates a batch of test SmsQueue objects. This method attempts to create SMSs of various
     *
     * @param count the number of SMS to be created
     */
    void createSmsBatch(int count);

}
