package com.cyrillelamal;

import com.cyrillelamal.commands.Detect;
import com.cyrillelamal.commands.Observe;
import lombok.Getter;
import lombok.Setter;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.Arrays;
import java.util.concurrent.Callable;

@Command(name = "device-info", version = DeviceInfo.VERSION)
@Getter
@Setter
public class DeviceInfo implements Callable<Integer> {
    public static final String VERSION = "1.1.0";

    @Option(names = {"-v", "--vendor-id"}, description = "Vendor id, e.g. \"0x45e\"")
    private String vendorId = null;
    @Option(names = {"-p", "--product-id"}, description = "Product id, e.g. \"0x28e\"")
    private String productId = null;
    @Option(names = {"-s", "--serial"}, description = "Serial number")
    private String serialNumber = null;

    @Option(names = {"-V", "--version"}, versionHelp = true, description = "This help")
    private boolean versionInfoRequested = false;
    @Option(names = {"-h", "--help"}, usageHelp = true, description = "Version number")
    private boolean usageHelpRequested = false;

    public static void main(String[] args) {
        var cmd = new CommandLine(new DeviceInfo());
        int code = cmd.execute(args);
        System.exit(code);
    }

    @Override
    public Integer call() {
        try {
            Arrays.stream(this.getCommands())
                    .map(Thread::new)
                    .forEach(Thread::start);

            System.out.println("Use Ctrl+C to exit");

            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return 1;
        }

        return 0;
    }

    protected Runnable[] getCommands() throws NumberFormatException {
        return new Runnable[]{
                new Detect(),
                new Observe(this.getVendorId(), this.getProductId(), this.getSerialNumber())
        };
    }

    protected Integer getVendorId() throws NumberFormatException {
        if (null == this.vendorId) {
            return null;
        }

        try {
            return Integer.decode(this.vendorId);
        } catch (NumberFormatException e) {
            final var msg = "Bad vendor id \"%s\"%n".formatted(this.vendorId);
            System.out.println(msg);
            System.err.println(msg);
            throw e;
        }
    }

    protected Integer getProductId() throws NumberFormatException {
        if (null == this.productId) {
            return null;
        }

        try {
            return Integer.decode(this.productId);
        } catch (NumberFormatException e) {
            final var msg = "Bad product id \"%s\"%n".formatted(this.productId);
            System.out.println(msg);
            System.err.println(msg);
            throw e;
        }
    }
}
