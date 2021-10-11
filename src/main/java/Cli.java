public class Cli {
    /**
     * Concatenate these strings with yours to obtain a colorful output.
     */
    public enum Colors {
        RESET("\u001B[0m"),
        RED("\u001B[31m"),
        GREEN("\u001B[32m"),
        BLUE("\u001B[34m");

        private final String ansi;

        Colors(final String ansi) {
            this.ansi = ansi;
        }

        public String getAnsi() {
            return this.ansi;
        }

        @Override
        public String toString() {
            return this.getAnsi();
        }
    }
}
