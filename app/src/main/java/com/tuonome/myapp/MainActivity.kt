package com.tuonome.myapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var tvBalance: TextView
    private lateinit var tvAddress: TextView
    private lateinit var btnSend: Button
    private lateinit var btnReceive: Button
    private lateinit var rvTransactions: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvBalance = findViewById(R.id.tvBalance)
        tvAddress = findViewById(R.id.tvAddress)
        btnSend = findViewById(R.id.btnSend)
        btnReceive = findViewById(R.id.btnReceive)
        rvTransactions = findViewById(R.id.rvTransactions)

        // Lista di esempio transazioni
        val transactions = listOf(
            "Ricevuto 100 MVC da MxXy...",
            "Inviato 50 MVC a MzZa...",
            "Ricevuto 25 MVC da MqQs..."
        )

        rvTransactions.layoutManager = LinearLayoutManager(this)
        rvTransactions.adapter = TransactionAdapter(transactions)

        btnSend.setOnClickListener {
            // TODO: apri schermata per inviare MVC
        }
        btnReceive.setOnClickListener {
            // TODO: mostra QR code con indirizzo
        }
    }
}