package com.khales.preference_datastore.encryptionAndDecryptionByKeyStore.keyStoreIntegration.models

import javax.crypto.SecretKey

data class KeyModelResponse(
    val transformation: String,
    val secretKey :SecretKey
)
