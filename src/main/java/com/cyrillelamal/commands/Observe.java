package com.cyrillelamal.commands;

import lombok.Getter;
import org.hid4java.HidDevice;
import org.hid4java.HidManager;
import org.hid4java.HidServices;

import java.util.Arrays;

@Getter
public class Observe implements Runnable {
    private final Integer vendorId;
    private final Integer productId;
    private final String serialNumber;

    private final HidServices services;

    public Observe(final Integer vendorId, final Integer productId, final String serialNumber) {
        this.vendorId = vendorId;
        this.productId = productId;
        this.serialNumber = serialNumber;

        this.services = HidManager.getHidServices();
    }

    @Override
    public void run() {
        if (null == this.getVendorId() || null == this.getProductId()) {
            return;
        }

        final HidDevice device = this.getServices().getHidDevice(
                this.getVendorId(),
                this.getProductId(),
                this.getSerialNumber()
        );

        if (null == device) {
            System.out.println(this.getNotFoundMessage());
            System.err.println(this.getNotFoundMessage());
            return;
        }

        System.out.println("Observe: " + device);

        final var buffer = new byte[256];
        while (device.isOpen()) {
            final int read = device.read(buffer);
            if (read < 0) {
                break; // error
            } else if (read > 0) {
                System.out.println(Arrays.toString(buffer));
            }
        }

        System.out.println(this.getNotFoundMessage());
    }

    protected String getNotFoundMessage() {
        return "Device not found (%d, %d, %s)%n".formatted(
                this.getVendorId(),
                this.getProductId(),
                this.getSerialNumber()
        );
    }
}
