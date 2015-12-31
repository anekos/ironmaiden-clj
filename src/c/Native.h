#include <jni.h> //Java Native Interface header

#ifndef _Included_Native
#define _Included_Native
#ifdef __cplusplus
extern "C" {
#endif
  JNIEXPORT void JNICALL Java_ironmaiden_Native_hello(JNIEnv *, jobject);
#ifdef __cplusplus
}
#endif
#endif
