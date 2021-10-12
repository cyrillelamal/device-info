package com.cyrillelamal;

import org.hid4java.HidDevice;
import org.hid4java.HidManager;
import org.hid4java.HidServices;
import org.hid4java.HidServicesListener;
import org.hid4java.event.HidServicesEvent;
import picocli.CommandLine;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "device-info",
        description = "You can use the application in 2 ways:\n" +
                "1. Run the application without any options to detect attached and detached devices in real-time." +
                "2. Run the application with vendor-id and product-id options to observe the specified device.",
        version = DeviceInfo.VERSION
)
public class DeviceInfo implements Callable<Integer> {
    public static final String VERSION = "1.0.0";

    @CommandLine.Option(names = {"-v", "--vendor-id"}, description = "vendor id, e.g. \"0x45e\"")
    private Optional<String> vendorId; // int
    @CommandLine.Option(names = {"-p", "--product-id"}, description = "product id, e.g. \"0x28e\"")
    private Optional<String> productId; // int
    @CommandLine.Option(names = {"-s", "--serial"}, description = "serial number")
    private Optional<String> serialNumber; // ?String

    @CommandLine.Option(names = {"-V", "--version"}, versionHelp = true, description = "display version info")
    private boolean versionInfoRequested = false;
    @CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "display this help message")
    private boolean usageHelpRequested = false;

    public static void main(String[] args) {
        var cmd = new CommandLine(new DeviceInfo());
        int code = cmd.execute(args);
        System.exit(code);
    }

    @Override
    public Integer call() throws InterruptedException {
        final HidServices services = HidManager.getHidServices();

        final var detect = new Thread(() -> {
            System.out.println("Detecting HIDs");
            System.out.println("Use Ctrl-C to exit");

            services.addHidServicesListener(new HidServicesListener() {
                @Override
                public void hidDeviceAttached(final HidServicesEvent event) {
                    System.out.println("Attached: " + event.toString());
                }

                @Override
                public void hidDeviceDetached(final HidServicesEvent event) {
                    System.out.println("Detached: " + event.toString());
                }

                @Override
                public void hidFailure(final HidServicesEvent event) {
                    System.out.println("Fail: " + event.toString());
                }
            });

            while (true) ;
        });

        final var observe = new Thread(() -> {
            if (this.vendorId.isPresent() && this.productId.isPresent()) {
                int vendorId;
                try {
                    vendorId = Integer.decode(this.vendorId.get());
                } catch (NumberFormatException e) {
                    System.out.printf("Invalid vendorId: %s\n", e.getMessage());
                    return;
                }
                int productId;
                try {
                    productId = Integer.decode(this.productId.get());
                } catch (NumberFormatException e) {
                    System.out.printf("Invalid vendorId: %s", e.getMessage());
                    return;
                }
                final var serialNumber = this.serialNumber.orElse(null);

                while (true) {
                    final HidDevice device = services.getHidDevice(vendorId, productId, serialNumber);

                    if (device == null) {
                        System.out.printf("Cannot find device [vendorId=%x, productId=%x, serialNumber=%s]\n", vendorId, productId, serialNumber);
                        continue;
                    }

                    System.out.println("Observing: " + device);

                    final var buffer = new byte[256];
                    while (device.isOpen()) {
                        final int read = device.read(buffer);
                        if (read < 0) {
                            break;
                        } else if (read > 0) {
                            System.out.println(Arrays.toString(buffer));
                        }
                    }
                }
            }
        });

        detect.start();
        observe.start();

        detect.join();
        observe.join();

        return 0;
    }
}
