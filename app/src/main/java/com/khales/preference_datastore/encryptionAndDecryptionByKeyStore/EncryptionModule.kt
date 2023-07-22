package com.khales.preference_datastore.encryptionAndDecryptionByKeyStore

import com.khales.preference_datastore.encryptionAndDecryptionByKeyStore.keyStoreIntegration.models.KeyModelResponse
import java.util.function.BiFunction
import javax.crypto.Cipher

/**
 * This class was constructed following the principles of Functional Module Cohesion.
 */
 class EncryptionModule : BiFunction<String, KeyModelResponse, String> {

    /**
     * In this function, I perform encryption by passing whatever I want to encrypt as bytes, whether it's a string or any other data type.
     */
    override fun apply(data : String, keyModelResponse: KeyModelResponse): String {

        /**
        * We will use a cipher for the encryption process.
        *
        * Note: A cipher is a built-in Java code used for performing encryption.
        */
        val cipher = Cipher.getInstance(keyModelResponse.transformation)
        cipher.init(Cipher.ENCRYPT_MODE,keyModelResponse.secretKey )

        /**
         *  I use IV for encryption process. (iv) -> means Initial Vector.
         *
         *  why we use IV in encryption and decryption?
         *  because everytime i use this function (encryption) will return different encrypted text (even for the same text that i want to encrypt) to avoid
         *  making one encryption technique that produce the same text for the same input(text that i want to encrypt)
         *
         *  note you will notice that i return in encrypt function i return (iv + encrypted) and i saved these together because IV i used for encryption and decryption
         */
        val iv = cipher.iv
        val encrypted = cipher.doFinal(data.toByteArray())

        /**
         * transfer byte data to string
         */
        return android.util.Base64.encodeToString(iv + encrypted, 0)
    }
}