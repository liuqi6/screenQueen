#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring

JNICALL
Java_com_liuqi_screenqueen_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "我的王，欢迎您";
    return env->NewStringUTF(hello.c_str());
}
