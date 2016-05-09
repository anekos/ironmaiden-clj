package ironmaiden;

import java.io.Closeable;


public class Native {
  public static native void hello();

  private static native int sendEvent(int fd, int type, int code, int value);
  private static native int newUInput();
  private static native int destroyUInput(int fd);
  private static native int setupDevice(String path);
  private static native void closeDevice(int fd);


  class UInput implements Closeable {
    private int fd = 0;

    UInput() {
      fd = newUInput();
    }

    public void close() {
      destroyUInput(fd);
      fd = 0;
    }

    public int sendEvent(int type, int code, int value) {
      return Native.sendEvent(fd, type, code, value);
    }
  }

  class Device implements Closeable {
    private int fd = 0;

    Device(String path) {
      fd = setupDevice(path);
    }

    public void close() {
      closeDevice(fd);
      fd = 0;
    }
  }
}
