package com.khales.preference_datastore

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import java.util.*

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("setting")

val KEY_NAME = stringPreferencesKey( "key_name")

class MainActivity : AppCompatActivity() {

    var cipherText : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val cryptoData = CryptoData()


        val textData = findViewById<TextView>(R.id.text)
        val inputEditText = findViewById<EditText>(R.id.data)
        val saveBu = findViewById<Button>(R.id.save)
        val readBu = findViewById<Button>(R.id.read)


         saveBu.setOnClickListener{
             val bytes = inputEditText.text.toString().toByteArray()
             cipherText =   android.util.Base64.encodeToString(cryptoData.encrypt(bytes= bytes), 0)

             inputEditText.setText(cipherText)
         }

        readBu.setOnClickListener{

           // val originalText = cryptoData.decrypt( Base64.getDecoder.decode(cipherText))?.decodeToString()
            val originalText = cryptoData.decrypt( android.util.Base64.decode(cipherText, android.util.Base64.DEFAULT))?.decodeToString()
            inputEditText.setText(originalText)
        }

    }

    private fun saveName (name:String )
    {

        lifecycleScope.launch {
            dataStore.edit {
                it[KEY_NAME]
            }
        }

    }

   /* private fun read Name (name:String )
    {

    }*/
}