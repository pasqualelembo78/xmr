cmake_minimum_required(VERSION 3.18.1)
project("mevacoin-native")

# Libreria JNI che fa da bridge con la tua app
add_library(MevaCoinJNI SHARED MevaCoinJNI.cpp)

# Percorso alle librerie statiche precompilate (.a)
set(MEVACOIN_PREBUILT_DIR ${CMAKE_SOURCE_DIR}/prebuilt/${ANDROID_ABI})

# Importa le statiche
add_library(mevacoind STATIC IMPORTED)
set_target_properties(mevacoind PROPERTIES IMPORTED_LOCATION ${MEVACOIN_PREBUILT_DIR}/libmevacoind.a)

add_library(miner STATIC IMPORTED)
set_target_properties(miner PROPERTIES IMPORTED_LOCATION ${MEVACOIN_PREBUILT_DIR}/libminer.a)

add_library(xkrwallet STATIC IMPORTED)
set_target_properties(xkrwallet PROPERTIES IMPORTED_LOCATION ${MEVACOIN_PREBUILT_DIR}/libxkrwallet.a)

add_library(wallet_api STATIC IMPORTED)
set_target_properties(wallet_api PROPERTIES IMPORTED_LOCATION ${MEVACOIN_PREBUILT_DIR}/libwallet-api.a)

add_library(mevacoin_service STATIC IMPORTED)
set_target_properties(mevacoin_service PROPERTIES IMPORTED_LOCATION ${MEVACOIN_PREBUILT_DIR}/libmevacoin-service.a)

# Libreria log di Android
find_library(log-lib log)

# Linka tutto insieme
target_link_libraries(
    MevaCoinJNI
    mevacoind
    miner
    xkrwallet
    wallet_api
    mevacoin_service
    ${log-lib}
)