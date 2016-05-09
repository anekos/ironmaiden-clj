package ironmaiden;

import java.io.Closeable;


public class Native {
  public static native void hello();

  private static native int sendEvent(int fd, int type, int code, int value);
  private static native int newUInputDevice();
  private static native int destroyUInputDevice(int fd);
  private static native int setupInputDevice(String path);
  private static native void closeInputDevice(int fd);


  class UInputDevice implements Closeable {
    private int fd = 0;

    UInputDevice() {
      fd = newUInputDevice();
    }

    public void close() {
      destroyUInputDevice(fd);
      fd = 0;
    }

    public int sendEvent(int type, int code, int value) {
      return Native.sendEvent(fd, type, code, value);
    }
  }

  class InputDevice implements Closeable {
    private int fd = 0;

    InputDevice(String path) {
      fd = setupInputDevice(path);
    }

    public void close() {
      closeInputDevice(fd);
      fd = 0;
    }
  }
}
