#include <stdlib.h>
#include <string.h>
#include <stdio.h>

#include <fcntl.h>
#include <linux/input.h>
#include <linux/uinput.h>
#include <signal.h>
#include <unistd.h>

#include "Native.h"


#define die(str, args...) do { perror(str); exit(EXIT_FAILURE); } while(0)


JNIEXPORT void JNICALL Java_ironmaiden_Native_hello(JNIEnv *env, jclass klass) {
  printf("Hell O C!\n");
  return;
}

JNIEXPORT jint JNICALL Java_ironmaiden_Native_sendEvent
(JNIEnv *env, jclass klass,
 jint fd, jint type, jint code, jint value) {

  struct input_event ev;
  memset(&ev, 0, sizeof(struct input_event));

  gettimeofday(&ev.time, NULL);

  ev.type = type;
  ev.code = code;
  ev.value = value;

  return write(fd, &ev, sizeof(ev)) < 0;
}

void ioctl_set (int fd, int set, int value) {
  if (ioctl(fd, set, value) < 0)
    die("error: ioctl set");
}

void setup_uinput_device (int fd) {
  ioctl_set(fd, UI_SET_EVBIT, EV_KEY);
  ioctl_set(fd, UI_SET_EVBIT, EV_REL);

  ioctl(fd, UI_SET_EVBIT, EV_KEY);
  ioctl(fd, UI_SET_KEYBIT, BTN_LEFT);
  ioctl(fd, UI_SET_KEYBIT, BTN_RIGHT);

  {
    int bit;
    for (bit = 0; bit < KEY_CNT; bit++)
      ioctl_set(fd, UI_SET_KEYBIT, bit);

    for (bit = 0; bit < REL_CNT; bit++)
      ioctl_set(fd, UI_SET_RELBIT, bit);
  }
}

void create_uinput_device (int fd) {
  struct uinput_user_dev uidev;
  memset(&uidev, 0, sizeof(uidev));

  snprintf(uidev.name, UINPUT_MAX_NAME_SIZE, "IronMaiden");
  uidev.id.bustype = BUS_USB;
  uidev.id.vendor  = 0xDEAD;
  uidev.id.product = 0xBEEF;
  uidev.id.version = 1;

  if (write(fd, &uidev, sizeof(uidev)) < 0)
    die("create_uinput_device: write");

  if (ioctl(fd, UI_DEV_CREATE) < 0)
    die("create_uinput_device: ioctl");
}

JNIEXPORT jint JNICALL Java_ironmaiden_Native_newUInputDevice
(JNIEnv *env, jclass klass) {
  int fd = open("/dev/uinput", O_WRONLY | O_NONBLOCK);
  create_uinput_device(fd);
  setup_uinput_device(fd);
  return fd;
}

JNIEXPORT jint JNICALL Java_ironmaiden_Native_destroyUInputDevice
(JNIEnv *env, jclass klass, jint fd) {
  if (ioctl(fd, UI_DEV_DESTROY) < 0)
    die("destroy_uinput_device: ioctl");
  close(fd);
}

void setup_input_device (int fd) {
  ioctl(fd, EVIOCGRAB, 1);
}

JNIEXPORT jint JNICALL Java_ironmaiden_Native_setupInputDevice
(JNIEnv *env, jclass klass, jstring _path) {
  const char *path = (*env)->GetStringUTFChars(env, _path, NULL);
  int fd = open(path, O_RDONLY);
  (*env)->ReleaseStringUTFChars(env, _path, path);
  setup_input_device(fd);
  return fd;
}

JNIEXPORT void JNICALL Java_ironmaiden_Native_closeInputDevice
(JNIEnv *env, jclass klass, jint fd) {
  close(fd);
}
