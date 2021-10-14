package com.cyrillelamal.commands;

import org.hid4java.HidManager;
import org.hid4java.HidServices;
import org.hid4java.HidServicesListener;
import org.hid4java.event.HidServicesEvent;

public class Detect implements Runnable, HidServicesListener {
    private final HidServices services;

    public Detect() {
        this.services = HidManager.getHidServices();
    }

    @Override
    public void run() {
        System.out.println("Detecting devices");

        this.services.addHidServicesListener(this);
    }

    @Override
    public void hidDeviceAttached(final HidServicesEvent event) {
        System.out.println("Attached: " + event.getHidDevice().toString());
    }

    @Override
    public void hidDeviceDetached(final HidServicesEvent event) {
        System.out.println("Detached: " + event.getHidDevice().toString());
    }

    @Override
    public void hidFailure(final HidServicesEvent event) {
        System.out.println("Fail: " + event.toString());
    }
}
