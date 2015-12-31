#include <stdio.h>
#include "Native.h"


JNIEXPORT void JNICALL Java_ironmaiden_Native_hello(JNIEnv *env, jclass klass) {
  printf("Hell O C!\n");
  return;
}
