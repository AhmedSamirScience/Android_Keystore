package com.khales.preference_datastore

import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

/**
 * this class is used for encryption and decryption
 *
 * keyStore initialized here in this class
 */
class CryptoData {

    companion object {
        /**
         * this algorithm that i use for the encryption process and this algorithm called AES
         */
        private const val ALGORITHM= KeyProperties.KEY_ALGORITHM_AES

        /**
         * this block Mode is for the encryption
         */
        private const val BLOCK_MODE= KeyProperties.BLOCK_MODE_CBC

        /**
         * this padding is also for the encryption
         */
        private const val PADDING= KeyProperties.ENCRYPTION_PADDING_PKCS7

        /**
         * this encryption process through transformation
         */
        private const val TRANSFORMATION= "$ALGORITHM/$BLOCK_MODE/$PADDING"

        private const val  KEY_ALIAS_NAME= "secret"
    }

    /**
     * this key is used for encryption and also for decryption
     */
    private val keyStore = KeyStore.getInstance("AndroidKeyStore").apply {
        load(null)
    }

    /**
     * when i need the key i will not create key but i can get the key that i have been created before
     */
    private fun getKey(): SecretKey{
        /**
         * i will get the key by name because when i create the key i give the name of this key and for sure
         * if it doesn't return or didn't find the key then i will create new one
         *
         * Note: protParam = set to (null) -> means that i want the default parameter of key store
         */
        val existingKey = keyStore.getEntry(KEY_ALIAS_NAME, null ) as? KeyStore.SecretKeyEntry

        return existingKey?.secretKey ?: createKey()
    }


    /**
     * we know create the secret key by getting instance of chosen ALGORITHM of KeyGenerator
     *
     * note this key i will use for encryption or decryption
     */
    private fun createKey () : SecretKey {
        return KeyGenerator.getInstance(ALGORITHM).apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                init(
                    KeyGenParameterSpec.Builder(
                        KEY_ALIAS_NAME,
                        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT).setBlockModes(BLOCK_MODE)
                        .setEncryptionPaddings(PADDING)
                        .build()
                    )
            }

        }.generateKey()
    }


    /**
     * i will do encryption process by using cipher
     *
     * cipher is built in code in java used for doing an encryption and decryption process
     */
    private val cipher = Cipher.getInstance(TRANSFORMATION)

    /**
     * in this function i do an encryption by pass what ever i want to encrypt but in bytes (even it was string or other type
     *
     *  iv means Initial Vector
     *  please note that iv i used for encrypt and decrypt
     *
     *  why we use IV in encryption and decryption?
     *  because everytime i use this function (encryption) will return different encrypted text (even for the same text that i want to encrypt) to avoid
     *  making one encryption technique that produce the same text for the same input(text that i want to encrypt)
     *
     *  note you will notice that i return in encrypt function i return (iv + encrypted) and i saved these together because IV i used for encryption and decryption
     */
    fun encrypt (bytes : ByteArray): ByteArray?{
        cipher.init(Cipher.ENCRYPT_MODE,getKey())
        val iv = cipher.iv
        val encrypted = cipher.doFinal(bytes)
        return iv + encrypted
    }

    /**
     * you will notice that in the decryption process firstly i take out the IV from the encryption text to make a separation the data and IV
     */
    fun decrypt (bytes : ByteArray): ByteArray?{
        val iv = bytes.copyOfRange(0, cipher.blockSize)
        val data = bytes.copyOfRange(cipher.blockSize, bytes.size)

        cipher.init (Cipher.DECRYPT_MODE, getKey(), IvParameterSpec(iv)) // now you send IV that you take out of the encryption text in (bytes)
        return cipher.doFinal(data)
    }


    /**
     * this key instead of what we did but why we don't use this key because we set a password and we will take this password by using some api
     * but it is not the best way for doing like this technique because we sometimes forget the password key or the key shared with another one
     * then it will not be the good way
     *
     * if you put the password inside the code you will put a risk if someone do a reverse engineer then it will get the password form your code
     *
     */
   // val keySpec = SecretKeySpec("123456".toByteArray(Charset.UTF_8), "AES")

}