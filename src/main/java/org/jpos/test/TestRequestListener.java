package org.jpos.test;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISORequestListener;
import org.jpos.iso.ISOSource;

import java.io.IOException;

public class TestRequestListener implements ISORequestListener {

    public TestRequestListener() {
        super();
    }

    public boolean process (ISOSource source, ISOMsg m) {
        try {
            m.setResponseMTI ();
            m.set (39, "00");
            source.send (m);
        } catch (ISOException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}

