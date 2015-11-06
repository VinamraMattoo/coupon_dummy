package com.portea.commp.smsen.engine;

import com.portea.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/dsms"})
public class DirectSmsRequestProcessor extends HttpServlet {

    private static Logger LOG = LoggerFactory.getLogger(DirectSmsRequestProcessor.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        LogUtil.entryTrace("doPost", LOG);

        handleRequest(req, resp);

        LogUtil.exitTrace("doPost", LOG);
    }

    private void handleRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        LogUtil.entryTrace("handleRequest", LOG);

        // TODO Handle the request for queuing the SMS

        LogUtil.exitTrace("handleRequest", LOG);
    }
}
