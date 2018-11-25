#include <jni.h>
#include <string>
#include <vector>
extern "C" JNIEXPORT jstring JNICALL
Java_com_gape_cyandr_gapeandroid_gape_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";


    return env->NewStringUTF(hello.c_str());
}
