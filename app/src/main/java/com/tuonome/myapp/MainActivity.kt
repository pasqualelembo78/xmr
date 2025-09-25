package com.tuonome.myapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var tvBalance: TextView
    private lateinit var tvAddress: TextView
    private lateinit var btnSend: Button
    private lateinit var btnReceive: Button
    private lateinit var btnInfo: Button
    private lateinit var rvTransactions: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Collega le view
        tvBalance = findViewById(R.id.tvBalance)
        tvAddress = findViewById(R.id.tvAddress)
        btnSend = findViewById(R.id.btnSend)
        btnReceive = findViewById(R.id.btnReceive)
        btnInfo = findViewById(R.id.btnInfo)
        rvTransactions = findViewById(R.id.rvTransactions)

        // Saldo di esempio
        val balance = MevaCoinNative.getBalance()
        tvBalance.text = "$balance MVC"

        // Lista di esempio transazioni
        val transactions = listOf(
            "Ricevuto 100 MVC da MxXy...",
            "Inviato 50 MVC a MzZa...",
            "Ricevuto 25 MVC da MqQs..."
        )
        rvTransactions.layoutManager = LinearLayoutManager(this)
        rvTransactions.adapter = TransactionAdapter(transactions)

        // Pulsanti
        btnSend.setOnClickListener {
            Toast.makeText(this, "Apri schermata invio MVC", Toast.LENGTH_SHORT).show()
        }

        btnReceive.setOnClickListener {
            Toast.makeText(this, "Mostra QR code con indirizzo", Toast.LENGTH_SHORT).show()
        }

        btnInfo.setOnClickListener {
            val output = runMevacoindHelp()
            Toast.makeText(this, output.take(200), Toast.LENGTH_LONG).show()
        }
    }

    // Esegue il binario reale mevacoind con "--help"
    private fun runMevacoindHelp(): String {
        return try {
            val binName = "mevacoind" // binario reale in assets
            val binary = File(filesDir, binName)

            if (!binary.exists()) {
                assets.open(binName).use { input ->
                    binary.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }
                binary.setExecutable(true)
            }

            val process = ProcessBuilder(binary.absolutePath, "--help")
                .redirectErrorStream(true)
                .start()

            val result = process.inputStream.bufferedReader().use { it.readText() }
            process.waitFor()
            result
        } catch (e: Exception) {
            e.printStackTrace()
            "Errore esecuzione: ${e.message}"
        }
    }
}