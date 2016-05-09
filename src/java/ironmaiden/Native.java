package ironmaiden;

public class Native {
  public static native void hello();
  public static native int sendEvent(int fd, int type, int code, int value);
  public static native int newUInputDevice();
  public static native int destroyUInputDevice(int fd);
}
