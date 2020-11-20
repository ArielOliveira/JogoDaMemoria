package com.arielado.jogodamemoria;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {
	Transparente transp;		
	Thread contador;
	Handler MyHandler;
	List<String> data = new ArrayList<>();
	String par[];
	long seed;
	List<Button> botoes = new ArrayList<>();
	Pessoa pessoa;
	View view, view2;
	boolean acerto, run = false;
	Sorteio s;
	int clique = 0, difficulty = 1, numeroBotoes;
	String clique1, clique2, acaoBanco;
	Button bbtSair, btIniciar, btOpcoes, btRanking;
	TextView tvAcertos, tvErros, tvTempo;
	
	@Override
	 protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyHandler = new Handler();
        numeroBotoes = 24;
        par = new String[numeroBotoes/2];
        if (savedInstanceState != null) {
        	pessoa = (Pessoa)savedInstanceState.getSerializable("pessoa");
        	run = savedInstanceState.getBoolean("run");
        	List<String> isEnabled = new ArrayList<String>();
        	isEnabled = savedInstanceState.getStringArrayList("isEnabled");
        	seed = savedInstanceState.getLong("seed");
			s = new Sorteio(numeroBotoes/2, seed);
        	iniciarJogo(true);
        	if (run) {
        		clique = 1;
        		for (int i = 0; i < botoes.size(); i++) {
        			if (isEnabled.get(i).equals("0")) {
        				botoes.get(i).setEnabled(false);
        			} else {
        				botoes.get(i).setTextColor(Color.TRANSPARENT);
        			}
        		}
        	}
        } else {
			seed = new Random().nextLong();
			s = new Sorteio(numeroBotoes/2, seed);
        	difficulty = getIntent().getIntExtra("difficulty", 1);
        	pessoa = novaPessoa();
        	run = false;
        	iniciarJogo(false);
        }
    }
	
	@Override
	protected void onPause() {
		super.onPause();
		if (contador != null) {
			contador.interrupt();
			contador = null;
			run = true;
		}
		if (transp != null) {
			transp.note();
			transp = null;
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if (run) {
			contador = new Thread(new Contador(pessoa, tvTempo, MyHandler, difficulty));
			contador.start();
		}
	}
	
	public Pessoa novaPessoa() {
		return new Pessoa();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle estadoSalvo) {
		//botoes.clear();
		//inicializarBotoes();

		List<String> isEnabled = new ArrayList<String>();
		String enabled;
		for (Button botao : botoes) {
			if (!botao.isEnabled()) {
				enabled = "0";
				isEnabled.add(enabled);
			} else {
				enabled = "1";
				isEnabled.add(enabled);
			}
		}
		botoes.clear();
		estadoSalvo.putSerializable("pessoa", pessoa);
		estadoSalvo.putStringArrayList("isEnabled",(ArrayList<String>) isEnabled);
		estadoSalvo.putLong("seed", seed);
		estadoSalvo.putBoolean("run", run);
		
		super.onSaveInstanceState(estadoSalvo);
	}
	
	public void inicializarBotoes(Boolean fromSave) {
		LinearLayout buttonLayout = (LinearLayout)findViewById(R.id.buttonLayout);

		int rowNumber = numeroBotoes/6;
		int columnNumber = numeroBotoes/4;
		int count = 0;

		for (int z = 0; z < par.length; z++)
			par[z] = s.proximaPalavra();

		for (int i = 0; i < rowNumber; i++) {
			LinearLayout buttonRow = new LinearLayout(this);
			buttonRow.setLayoutParams(buttonLayout.getLayoutParams());
			buttonRow.setOrientation(LinearLayout.HORIZONTAL);
			buttonLayout.addView(buttonRow);
			for (int j = 0; j < columnNumber; j++) {
				Button button = new Button(this);
				button.setLayoutParams(new LayoutParams(
						176,
						LayoutParams.WRAP_CONTENT));
				button.setText(par[count]);
				button.setId(i);
				button.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						jogar(v);
					}
				});
				botoes.add(button);
				buttonRow.addView(button);
				count = (count+1)%par.length;
				if (count == 0)
						Collections.shuffle(Arrays.asList(par), new Random(seed));
			}
		}
	}
	public synchronized void jogar(View v) {
		if (clique == 0) {
			if (contador != null) {
				if (contador.isAlive()) {
					contador.interrupt();
					contador = null;
				}
			} else {
				difficulty = 0;
				contador = new Thread(new Contador(pessoa, tvTempo, MyHandler, difficulty));
				contador.start();
			}
		
				for (Button botao : botoes) {
					botao.setTextColor(Color.TRANSPARENT);
				}
		    	
		    	clique1 = ((Button)v).getText().toString();
		    	((Button)v).setTextColor(Color.BLACK);
				view = v;
		    	clique = 2;
		    	run = true;
			
		}	else if (clique == 1){
				if ( transp != null) {transp.note();}
					clique1 = ((Button)v).getText().toString();
					((Button)v).setTextColor(Color.BLACK);
					view = v;
					clique++;
		
		}	else if (clique == 2) {
				if (view != v) {
					clique2 = ((Button)v).getText().toString();
					((Button)v).setTextColor(Color.BLACK);
					view2 = v;
					clique = 1;	
					verificarAcerto();
				
				if (acerto == true) {
					((Button)view2).setEnabled(false);
					((Button)view).setEnabled(false);	
					} else {	
						transp = new Transparente();
						transp.start();
					}
				}
			}						

		if (pessoa.getAcertos() == 0) {
			mensagem("Jogo Acabou!");
			terminarJogo();
		}
	}

	public void iniciarJogo(Boolean fromSave) {
		setContentView(R.layout.activity_game);

		tvAcertos = (TextView)findViewById(R.id.tvAcertos);
		tvAcertos.setText(getString(R.string.restantes)+": "+pessoa.getAcertos());
		tvTempo = (TextView)findViewById(R.id.tvTempo);
		tvTempo.setText(pessoa.getFinalTempo());
		tvErros = (TextView)findViewById(R.id.tvErros);
		tvErros.setText(getString(R.string.erros)+": "+pessoa.getErros());

		inicializarBotoes(fromSave);
		if (difficulty > 1) {
			contador = new Thread(new Contador(pessoa, tvTempo, MyHandler, difficulty));
			contador.start();
		}
	}
	
    public void verificarAcerto() {
	    	if (clique1 == clique2) {
	    		pessoa.somarAcertos();
	    		tvAcertos.setText(getString(R.string.restantes)+": "+pessoa.getAcertos());
	    		acerto = true;
	    	} else {
	    		pessoa.somarErros();
	    		tvErros.setText(getString(R.string.erros)+": "+pessoa.getErros());
	    		acerto = false;
	    	}
	    }
	
    public void zerarValores() {	
	    	pessoa.zerar();
	    	botoes.clear();
	    	data.clear();
	    	clique = 0;
	    	contador = null;
	    	transp = null;
	    }
	           		
	public void mensagem(String mensagem) {
		    	Toast.makeText(getApplicationContext(), mensagem, Toast.LENGTH_SHORT).show();
		    }
	
	public void terminarJogo() {
		if (contador.isAlive()) {
		contador.interrupt();
		}
		List<String> dados = new ArrayList<String>();
		dados.add(pessoa.getPlayer());
		dados.add(String.valueOf(pessoa.getAcertos()));
		dados.add(String.valueOf(pessoa.getErros()));
		dados.add(pessoa.getFinalTempo());
		dados.add(String.valueOf(pessoa.pontuacaoFinal()));
		Intent data = new Intent();
		data.putStringArrayListExtra("dados", (ArrayList<String>) dados);
		setResult(RESULT_OK, data);
		zerarValores();
		finish();
	}


private class Transparente extends Thread {
	View v;
	View v2;
		  @Override
		  public synchronized void run() {  
			  try {
				  	v = view;
				  	v2 = view2;
					wait(400);
					} catch (InterruptedException e) {}
		        MyHandler.post(new transparencia());
		 
		  }
		  public synchronized void note () {
			  notifyAll();
		  } 
		  	class transparencia implements Runnable {		  
			  @Override
			  public synchronized void run() {
				  ((Button)v).setTextColor(Color.TRANSPARENT);
				  ((Button)v2).setTextColor(Color.TRANSPARENT);	
			  } 
		  	}
		 }

}
