package com.khales.preference_datastore.encryptionAndDecryptionByKeyStore

import com.khales.preference_datastore.encryptionAndDecryptionByKeyStore.keyStoreIntegration.models.KeyModelResponse
import java.util.function.BiFunction
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec

/**
 * This class was constructed following the principles of Functional Module Cohesion.
 */
class DecryptionModule : BiFunction<String, KeyModelResponse, String> {

    /**
     * In this function, I perform decryption by passing whatever encrypt as bytes and
     */
    override fun apply(data: String , keyModelResponse: KeyModelResponse): String {
        /**
         * We will use a cipher for the decryption process.
         *
         * Note: A cipher is a built-in Java code used for performing decryption.
         */
        val cipher = Cipher.getInstance(keyModelResponse.transformation)
        cipher.init(Cipher.ENCRYPT_MODE,keyModelResponse.secretKey )


        /**
         * Note: android.util.Base64.decode(data, android.util.Base64.DEFAULT) i use this to avoid compiling greater than 21
         */
        val dataInBytes =  android.util.Base64.decode(data, android.util.Base64.DEFAULT)
        val iv = dataInBytes.copyOfRange(0, cipher.blockSize)
        val dataAfterBlockSize = dataInBytes.copyOfRange(cipher.blockSize, dataInBytes.size)

        /**
         * Now you send IV that you take out of the encryption text in (bytes)
         */
        cipher.init (Cipher.DECRYPT_MODE, keyModelResponse.secretKey, IvParameterSpec(iv))

        /**
         * transfer byte data to string
         *
         */
        return cipher.doFinal(dataAfterBlockSize).decodeToString()
    }

}