#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_(JNIEnv* env, jobject /* this */) {
    std::string hello = "Ciao dal C++!";
    return env->NewStringUTF(hello.c_str());
}
