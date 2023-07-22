package com.khales.preference_datastore.encryptionAndDecryptionByKeyStore

import android.security.keystore.KeyProperties
import com.khales.preference_datastore.encryptionAndDecryptionByKeyStore.keyStoreIntegration.KeyStoreHelper
import com.khales.preference_datastore.encryptionAndDecryptionByKeyStore.keyStoreIntegration.models.KeyModelRequest
import com.khales.preference_datastore.encryptionAndDecryptionByKeyStore.keyStoreIntegration.models.KeyModelResponse

class EncryptionDecryptionManager {

     fun createKey (): KeyModelResponse
    {
        return  KeyStoreHelper(KeyModelRequest(algorithm= KeyProperties.KEY_ALGORITHM_AES ,blockMode= KeyProperties.BLOCK_MODE_CBC,
            padding=KeyProperties.ENCRYPTION_PADDING_PKCS7 ,keyAliasName="secretYasta", provider="AndroidKeyStore")).getKey()
    }

    fun encryptData (data :String, keyModelResponse: KeyModelResponse) : String
    {
        return EncryptionModule().apply(data,keyModelResponse)
    }

    fun decryptData (data :String, keyModelResponse: KeyModelResponse) : String
    {
        return DecryptionModule().apply(data,keyModelResponse)
    }

}