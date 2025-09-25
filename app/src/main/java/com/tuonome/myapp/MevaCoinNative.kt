package com.tuonome.myapp

object MevaCoinNative {

    init {
        // Carica la libreria JNI che fa da bridge
        System.loadLibrary("MevaCoinJNI")
        // Le altre librerie vengono già linkate tramite CMake se necessario
        // System.loadLibrary("mevacoind")
        // System.loadLibrary("miner")
        // System.loadLibrary("xkrwallet")
        // System.loadLibrary("wallet-api")
        // System.loadLibrary("mevacoin-service")
    }

    // Mining
    external fun startMining(threads: Int): Int
    external fun stopMining(): Int

    // Wallet
    external fun getBalance(): Long
    external fun getAddress(): String

    // Transazioni
    external fun send(address: String, amount: Long): Int
}