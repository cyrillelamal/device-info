import org.hid4java.HidDevice;

public class Reader implements Runnable {
    private final HidDevice device;

    private boolean updated = false;
    private byte[] data = new byte[1024];

    /**
     * Create a new device reader.
     *
     * @param device the observed HID device.
     */
    public Reader(HidDevice device) {
        this.device = device;
    }

    /**
     * Listen infinitely the device.
     */
    public void listen() {
        var buffer = new byte[1024];

        while (this.getDevice().isOpen()) {
            int read = device.read(buffer);
            if (read < 0) {
                break;
            } else if (read > 0) {
                this.updated = true;
                this.data = buffer.clone();
            }
        }
    }

    @Override
    public void run() {
        this.listen();
    }

    /**
     * Check if the device has received any data.
     *
     * @return true, if a new portion of data has been received.
     */
    public boolean isUpdated() {
        return this.updated;
    }

    /**
     * Get the received data.
     *
     * @return the received data.
     */
    public byte[] getData() {
        this.updated = false;

        return this.data.clone();
    }

    public HidDevice getDevice() {
        return this.device;
    }
}
