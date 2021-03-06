package me.hhhaiai.refcore;

import android.util.Base64;

import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestDecode {

    @Test
    public void decode() throws IOException, IOException {
        String dex =
                "ZGV4CjAzNQCi6VP1NL4ulKqUHo8KwXD8+Hq2zYqVqDtoBQAAcAAAAHhWNBIAAAAAAAAAALAEAAAaAAAAcAAAAA4AAADYAAAAAwAAABABAAAAAAAAAAAAAAUAAAA0AQAAAQAAAFwBAADsAwAAfAEAAKACAACoAgAAswIAALYCAAC+AgAAwwIAAN8CAADyAgAAFgMAADkDAABbAwAAbwMAAIMDAACyAwAAzgMAANEDAADlAwAA+gMAAA8EAAAoBAAAMQQAAEQEAABQBAAAWAQAAHAEAAB3BAAAAwAAAAUAAAAGAAAABwAAAAgAAAAJAAAACgAAAAsAAAAMAAAADQAAAA4AAAAPAAAAEAAAABEAAAAEAAAABgAAAJACAAAEAAAACQAAAJgCAAAOAAAACgAAAAAAAAAAAAIAAAAAAAAAAgAZAAAAAgABABQAAAAGAAIAAAAAAAkAAAAWAAAAAAAAAAEAAAAGAAAAAAAAAAEAAAB4AgAAoQQAAAAAAAABAAAAkwQAAAEAAQABAAAAfQQAAAQAAABwEAMAAAAOAAkAAAADAAAAggQAAGYAAAASCBIlEhcSBhwAAgAaARQAI1ILABwDBwBNAwIGHAMLAE0DAgduMAIAEAIMAhwAAgAaARMAI3MLABwEBwBNBAMGbjACABADDAAjcQwAGgMSAE0DAQZuMAQAgAEMAB8AAgAjUQwAGgMVAE0DAQZNCAEHbjAEAAIBDAEfAQkAI1MMABoEFwBNBAMGI3QLABwFDQBNBQQGTQQDB24wBAACAwwAHwAJACNiDABuMAQAgQIMASNyDAAjcw0AGgQCAE0EAwZNAwIGbjAEABACDgAAAAAAAAAAAAEAAAAAAAAAAQAAAHwBAAACAAAABgAMAAIAAAAHAAsABjxpbml0PgAJSGlkZS5qYXZhAAFMAAZMSGlkZTsAA0xMTAAaTGRhbHZpay9hbm5vdGF0aW9uL1Rocm93czsAEUxqYXZhL2xhbmcvQ2xhc3M7ACJMamF2YS9sYW5nL0lsbGVnYWxBY2Nlc3NFeGNlcHRpb247ACFMamF2YS9sYW5nL05vU3VjaE1ldGhvZEV4Y2VwdGlvbjsAIExqYXZhL2xhbmcvTnVsbFBvaW50ZXJFeGNlcHRpb247ABJMamF2YS9sYW5nL09iamVjdDsAEkxqYXZhL2xhbmcvU3RyaW5nOwAtTGphdmEvbGFuZy9yZWZsZWN0L0ludm9jYXRpb25UYXJnZXRFeGNlcHRpb247ABpMamF2YS9sYW5nL3JlZmxlY3QvTWV0aG9kOwABVgASW0xqYXZhL2xhbmcvQ2xhc3M7ABNbTGphdmEvbGFuZy9PYmplY3Q7ABNbTGphdmEvbGFuZy9TdHJpbmc7ABdkYWx2aWsuc3lzdGVtLlZNUnVudGltZQAHZm9yTmFtZQARZ2V0RGVjbGFyZWRNZXRob2QACmdldFJ1bnRpbWUABmludm9rZQAWc2V0SGlkZGVuQXBpRXhlbXB0aW9ucwAFdmFsdWUABHdvcmsABAAHDgAGAAdKARIPARoP4QEUD2nTAAIBARgcBBgEGAgYAxgFAAACAACBgASEAwEJnAMADwAAAAAAAAABAAAAAAAAAAEAAAAaAAAAcAAAAAIAAAAOAAAA2AAAAAMAAAADAAAAEAEAAAUAAAAFAAAANAEAAAYAAAABAAAAXAEAAAMQAAABAAAAfAEAAAEgAAACAAAAhAEAAAYgAAABAAAAeAIAAAEQAAACAAAAkAIAAAIgAAAaAAAAoAIAAAMgAAACAAAAfQQAAAQgAAABAAAAkwQAAAAgAAABAAAAoQQAAAAQAAABAAAAsAQAAA==";

        byte[] bytes = android.util.Base64.decode(dex, Base64.NO_WRAP);
        FileOutputStream fos = new FileOutputStream(new File("/sdcard/a.dex"));
        fos.write(bytes);
    }
}
