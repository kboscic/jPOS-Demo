package org.jpos.qtest;

import org.jpos.iso.*;
import org.jpos.iso.channel.ASCIIChannel;
import org.jpos.iso.packager.ISO87APackager;
import org.jpos.q2.Q2;
import org.jpos.q2.QBean;
import org.jpos.util.*;

public class TestQBean implements TestQBeanMBean, Runnable {

    volatile int state;
    long tickInterval = 1000;
    Log log;
    Q2 server;

    public TestQBean() {
        super();
        state = -1;
        log = Log.getLog(Q2.LOGGER_NAME, "qtest");
        log.info ("constructor");
    }

    public void init () {
        log.info("init");
        state = STARTING;
    }
    public void start() {
        log.info ("start");
        state = STARTED;
        new Thread(this).start();
    }
    public void stop () {
        log.info ("stop");
        state = STOPPING;
    }
    public void destroy () {
        log.info ("destroy");
        state = STOPPED;
    }
    public void setTickInterval (long tickInterval) {
        this.tickInterval = tickInterval;
    }
    public long getTickInterval () {
        return tickInterval;
    }
    public void run () {
        try {
            Logger logger = new Logger();
            logger.addListener(new SimpleLogListener(System.out));
            ISOChannel channel = new ASCIIChannel("localhost", 10000, new ISO87APackager());
            ((LogSource) channel).setLogger(logger, "test-channel");
            channel.connect();
            ISOMsg m = new ISOMsg();
            m.setMTI("0800");
            m.set(3, "000000");
            m.set(41, "00000001");
            m.set(70, "301");
            System.out.println("Request: " + m);
            channel.send(m);
            System.out.println("Sent!");
            ISOMsg r = channel.receive();
            System.out.println("Response: " + r);
            channel.disconnect();
            Thread.sleep(2000);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public int getState () {
        return state;
    }
    public String getStateAsString () {
        return state >= 0 ? stateString[state] : "Unknown";
    }
    private boolean running() {
        return state == QBean.STARTING || state == QBean.STARTED;
    }

    public void setServer(Q2 server) {
        this.server = server;
    }
}