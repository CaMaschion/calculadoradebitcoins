package com.example.calculadoradebitcoin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.bloco_cotacao.*
import kotlinx.android.synthetic.main.bloco_entrada.*
import kotlinx.android.synthetic.main.bloco_saida.*
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

        btn_calcular.setOnClickListener {
            calcular()
        }
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

    fun calcular(){

        if(txt_valor.text.isEmpty()){
            txt_valor.error = "Preencha um valor"
            return
        }
        val valor_digitado = txt_valor.text.toString()
            .replace(",", ".")
            .toDouble()

        val resultado = if (cotacaoBitcoin > 0 )valor_digitado / cotacaoBitcoin else 0.0

        //atualizando a textview com o resultado formatado com 8 casas decimais
        txt_qtd_bitcoins.text = "%.8f".format(resultado)
    }
}
