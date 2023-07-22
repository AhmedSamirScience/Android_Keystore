package com.khales.preference_datastore.encryptionAndDecryptionByKeyStore.keyStoreIntegration.models

/**
 * All these parameters are employed in generating the key required for the encryption and decryption procedures.
 */
data class KeyModelRequest(
    val algorithm: String /*= KeyProperties.KEY_ALGORITHM_AES*/,
    val blockMode: String/* = KeyProperties.BLOCK_MODE_CBC*/,
    val padding: String /*= KeyProperties.ENCRYPTION_PADDING_PKCS7*/,
    val keyAliasName: String ,
    val provider: String ,
)