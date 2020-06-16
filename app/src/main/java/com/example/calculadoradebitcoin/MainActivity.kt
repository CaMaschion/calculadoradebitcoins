package com.example.calculadoradebitcoin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.bloco_cotacao.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import java.net.URL
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    val API_URL = "https://www.mercadobitcoin.net/api/BTC/ticker"
    private var cotacaoBitcoin: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buscarCotacao()
    }

    fun buscarCotacao() {

        doAsync {
            //acessar a API e buscar seu resultado
            val resposta = URL(API_URL).readText()

            //acessando a cotação da String em JSON
            cotacaoBitcoin = JSONObject(resposta).getJSONObject("ticker").getDouble("last")

            //formatação em moeda
            val bitcoin = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
            val cotacaoFormatada = bitcoin.format(cotacaoBitcoin)

            uiThread {
                //atualizando a tela com a cotacao atual
                txt_cotacao.text = cotacaoFormatada
            }
        }
    }
}
