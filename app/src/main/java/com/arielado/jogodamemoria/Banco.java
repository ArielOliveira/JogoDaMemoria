package com.arielado.jogodamemoria;

import java.util.ArrayList;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;

public class Banco extends AppCompatActivity {
	
	
	private String player, tempo;
	private int erros, acertos, pontos;
	private SQLiteDatabase bancoDados;
	private Cursor cursor;
	public static final String SCORES_TABLE = "Scores";
	public static final String NOME = "Nome";
	public static final String ERROS = "Erros";
	public static final String ACERTOS = "Acertos";
	public static final String TEMPO = "Tempo";
	public static final String PONTOS = "Pontos";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		switch (intent.getAction()) {
		case "inserir":
			ArrayList<String> dados = intent.getStringArrayListExtra("dados");
			setarBanco(dados.get(0), Integer.parseInt(dados.get(1)),
					Integer.parseInt(dados.get(2)), dados.get(3), 
					Integer.parseInt(dados.get(4)));
			criarBanco();
			inserirDados();
			fecharBanco();
			finish();
			break;
		case "ranking":
			mostrarRanking();
			break;
		case "fechar":
			fecharBanco();
			finish();
			break;
		}
	}
	

	public void criarBanco() {
		
		String nomeBanco = "db_scores";
		try {
			bancoDados = openOrCreateDatabase(nomeBanco,
					SQLiteDatabase.CREATE_IF_NECESSARY, null);
			String sql = "CREATE TABLE IF NOT EXISTS " + SCORES_TABLE +"" +
					"(" +
					" '" + NOME + "' TEXT," +
					" '" + ERROS + "' TEXT," +
					" '" + ACERTOS +"' TEXT," +
					" '" + TEMPO + "' TEXT," +
				   	 " " + PONTOS + " INT)";
			bancoDados.execSQL(sql);
		} catch (Exception e) {}
		
	}	
	
	public void fecharBanco() {
		bancoDados.close();
		if (cursor != null) {
		cursor.close();
		}
	}
	
	public void mensagem(String mensagem) {
	    	Toast.makeText(getApplicationContext(), mensagem, Toast.LENGTH_SHORT).show();
	    }

	public void inserirDados() {
		try {
			String sql = "INSERT INTO  Scores(Nome, Erros, Acertos, Tempo, Pontos)"
					+ "VALUES('" + this.player + "'" + " , '" + String.valueOf(this.erros)
					+ "' , " + "'" + String.valueOf(this.acertos) + "' , '" + this.tempo + "'  "  
					+ " , " +	" " + this.pontos + ")";

			bancoDados.execSQL(sql);

		} catch (Exception erro) {
			mensagem("Problemas no ranking: " + erro.getLocalizedMessage());
		}
		
	}
	
	
	public void mostrarRanking() {
		setContentView(R.layout.activity_ranking);
		TableLayout tabela = (TableLayout)findViewById(R.id.tbTabela);
		
			criarBanco();
			cursor = bancoDados.rawQuery("SELECT * FROM " + SCORES_TABLE + "  ORDER BY  " + PONTOS + " DESC ", null);
			if (cursor != null) {
				cursor.moveToFirst();	
			}
		
		try {
		 while (!cursor.isAfterLast()) {
			 TableRow.LayoutParams rowParams = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			 
			 TableRow table = new TableRow(this);
			 table.setLayoutParams(rowParams);
			 
			 TextView score1 = new TextView(this);
			 score1.setLayoutParams(rowParams);
			 score1.setText(cursor.getString(cursor.getColumnIndex("Nome")));
			 table.addView(score1);
			 
			 TextView score2 = new TextView(this);
			 score2.setLayoutParams(rowParams);
			 score2.setText(cursor.getString(cursor.getColumnIndex("Pontos")));
			 table.addView(score2);
			 
			 TextView score3 = new TextView(this);
			 score3.setLayoutParams(rowParams);
			 score3.setText(cursor.getString(cursor.getColumnIndex("Erros")));
			 table.addView(score3);
			 
			 TextView score4 = new TextView(this);
			 score4.setLayoutParams(rowParams);
			 score4.setText(cursor.getString(cursor.getColumnIndex("Tempo")));
			 table.addView(score4);

			 tabela.addView(table);
			 cursor.moveToNext();
		 }
		} catch (Exception e) {
			mensagem("Erro ao exibir ranking: "+e.getLocalizedMessage());
			cursor.moveToFirst();
		}
		
		
	}

	public void finalizarRanking(View v) {
		fecharBanco();
		finish();
	}

	public void setarBanco(String player, int acertos, int erros, String tempo, int pontos) {
		this.player = player;
		this.acertos = acertos;
		this.erros = erros;
		this.tempo = tempo;
		this.pontos = pontos;
	}
}


