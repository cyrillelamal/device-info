import org.hid4java.HidManager;
import org.hid4java.HidServices;
import org.hid4java.HidServicesListener;
import org.hid4java.event.HidServicesEvent;

public class Detector implements HidServicesListener, Runnable {
    private final HidServices services;

    private boolean stopped = false;

    public Detector() {
        this.services = HidManager.getHidServices();
    }

    /**
     * Observe infinitely connected HID devices.
     */
    public void observe() {
        this.getServices().addHidServicesListener(this);

        System.out.println("Observing");

        while (!this.isStopped()) ;
    }

    @Override
    public void hidDeviceAttached(HidServicesEvent event) {
        System.out.println(Cli.Colors.GREEN + "Attached: " + event.toString() + Cli.Colors.RESET);
    }

    @Override
    public void hidDeviceDetached(HidServicesEvent event) {
        System.out.println(Cli.Colors.BLUE + "Detached: " + event.toString() + Cli.Colors.RESET);
    }

    @Override
    public void hidFailure(HidServicesEvent event) {
        var msg = Cli.Colors.RED + "Error: " + event.toString() + Cli.Colors.RESET;
        System.out.println(msg);
        System.err.println(msg);
    }

    @Override
    public void run() {
        this.observe();
    }

    /**
     * Check if the detector is topped.
     *
     * @return true, if the detector is topped.
     */
    public boolean isStopped() {
        return this.stopped;
    }

    /**
     * Stop the detector.
     * A new detector must be created each time you stop it.
     */
    public void stop() {
        this.stopped = true;
    }

    public HidServices getServices() {
        return services;
    }
}
