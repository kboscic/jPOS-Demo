package org.jpos.qtest;

import org.jpos.q2.Q2;
import org.jpos.q2.QBean;

public interface TestQBeanMBean extends QBean {
    public void setTickInterval(long tickInterval) ;
    public long getTickInterval() ;
    public void setServer(Q2 server);
}
