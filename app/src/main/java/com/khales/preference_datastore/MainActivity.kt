package com.khales.preference_datastore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.khales.preference_datastore.encryptionAndDecryptionByKeyStore.EncryptionDecryptionManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val decryptionData = findViewById<TextView>(R.id.text)
        val inputEditText = findViewById<EditText>(R.id.dataEditText)
        val encryptBtn = findViewById<Button>(R.id.encrypt)
        val decryptBtn = findViewById<Button>(R.id.decrypt)

        encryptBtn.setOnClickListener{
            if(inputEditText.text.toString().equals(""))
                Toast.makeText(this , "Enter data for doing the encryption process", Toast.LENGTH_SHORT).show()
            else
                inputEditText.setText(EncryptionDecryptionManager().encryptData(data = inputEditText.text.toString(),keyModelResponse= EncryptionDecryptionManager().createKey() ))
         }

        decryptBtn.setOnClickListener{
            if(inputEditText.text.toString().equals(""))
                Toast.makeText(this , "Enter data for doing the encryption process before doing decryption", Toast.LENGTH_SHORT).show()
            else
                decryptionData.text = EncryptionDecryptionManager().decryptData(data = inputEditText.text.toString(),keyModelResponse= EncryptionDecryptionManager().createKey() )
        }
    }
}