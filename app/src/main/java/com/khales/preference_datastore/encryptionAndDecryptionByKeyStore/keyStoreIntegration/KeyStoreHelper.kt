package com.khales.preference_datastore.encryptionAndDecryptionByKeyStore.keyStoreIntegration

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import com.khales.preference_datastore.encryptionAndDecryptionByKeyStore.keyStoreIntegration.models.KeyModelRequest
import com.khales.preference_datastore.encryptionAndDecryptionByKeyStore.keyStoreIntegration.models.KeyModelResponse
import java.security.KeyStore
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

/**
 * This class was constructed following the principles of Communicational Module Cohesion.
 */
class KeyStoreHelper(keyModelRequest: KeyModelRequest) {

    private val _algorithm :String = keyModelRequest.algorithm
    private val _blockMode :String = keyModelRequest.blockMode
    private val _padding :String = keyModelRequest.padding
    private val _keyAliasName= keyModelRequest.keyAliasName
    private val _provider= keyModelRequest.provider

    /**
     * When the key is needed, I won't create a new one; instead, I can retrieve the previously generated key by the _keyAliasName.
     *
     * Note: If the key is not found, then I will generate a new one.
     */
    fun getKey(): KeyModelResponse {
        val keyStore = KeyStore.getInstance(_provider).apply {
            load(null)
        }
        /**
         * protParam (second param in keyStore.getEntry) = Setting it to (null) -> indicates that i want the default parameter of key store.
         */
        val existingKey = keyStore.getEntry(_keyAliasName, null ) as? KeyStore.SecretKeyEntry
        val secretKey =existingKey?.secretKey ?: this.createKey()

        /**
         * this encryption process through transformation compination
         */
        return KeyModelResponse(secretKey = secretKey, transformation = "${_algorithm}/$_blockMode/$_padding" )
    }

    /**
     * Now, we create the secret key by obtaining an instance of the selected ALGORITHM from the KeyGenerator.
     *
     * Please take note that this key will be utilized for encryption or decryption purposes.
     */
    private fun createKey () : SecretKey {
        return KeyGenerator.getInstance(_algorithm).apply {
                init(
                    KeyGenParameterSpec.Builder(_keyAliasName, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(_blockMode)
                    .setEncryptionPaddings(_padding)
                    .build()
                )
        }.generateKey()
    }
}