#include <jni.h>
#include <string>
#include <array>
#include <memory>
#include <cstdio>

// 🔹 Dichiarazioni delle API native (se disponibili nelle lib .a/.so)
extern "C" {
    // ipotetiche funzioni esportate da libmevacoind / miner
    int start_mining(int threads);
    int stop_mining();
    long get_balance();
}

extern "C" JNIEXPORT jlong JNICALL
Java_com_tuonome_myapp_MevaCoinNative_getBalance(JNIEnv* env, jobject) {
    try {
        #ifdef USE_NATIVE_API
            // Usa API nativa
            return get_balance();
        #else
            // Fallback: esegue binario
            const char* cmd = "/data/data/com.tuonome.myapp/files/mevacoind --get-balance";
            std::array<char, 128> buffer;
            std::string result;

            std::unique_ptr<FILE, decltype(&pclose)> pipe(popen(cmd, "r"), pclose);
            if (!pipe) return 0;

            while (fgets(buffer.data(), buffer.size(), pipe.get()) != nullptr) {
                result += buffer.data();
            }

            return std::stol(result);
        #endif
    } catch (...) {
        return 0;
    }
}

extern "C" JNIEXPORT jint JNICALL
Java_com_tuonome_myapp_MevaCoinNative_startMining(JNIEnv* env, jobject, jint threads) {
    try {
        #ifdef USE_NATIVE_API
            return start_mining(threads);
        #else
            std::string cmd = "/data/data/com.tuonome.myapp/files/mevacoind --start-mining " + std::to_string(threads);
            system(cmd.c_str());
            return threads;
        #endif
    } catch (...) {
        return 0;
    }
}

extern "C" JNIEXPORT jint JNICALL
Java_com_tuonome_myapp_MevaCoinNative_stopMining(JNIEnv* env, jobject) {
    try {
        #ifdef USE_NATIVE_API
            return stop_mining();
        #else
            system("/data/data/com.tuonome.myapp/files/mevacoind --stop-mining");
            return 1;
        #endif
    } catch (...) {
        return 0;
    }
}