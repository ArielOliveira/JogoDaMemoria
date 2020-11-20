package com.arielado.jogodamemoria;


import java.util.ArrayList;
import java.util.List;

import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.Toast;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity implements MenuFragment.OuvinteBotao, OpcoesFragment.onOptionsItemsSelected {
	
	OpcoesFragment options;
	private static final int RESULTADO_FINAL = 1;
	View radioView;
	private int difficulty;
	List<String> data = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if (findViewById(R.id.fragment_container) != null) {
			
			if (savedInstanceState != null) {
				if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
					getSupportActionBar().setDisplayHomeAsUpEnabled(true);
					}
				return;
				}
				
			}
			
			
			MenuFragment menuFrag = new MenuFragment();
			
			getSupportFragmentManager().beginTransaction()
			.add(R.id.fragment_container, menuFrag).commit();
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)  {
		super.onActivityResult(requestCode, requestCode, data);
		if (requestCode == RESULTADO_FINAL) {
			if (resultCode == RESULT_OK) {
				this.data = data.getStringArrayListExtra("dados");
				Intent banco = new Intent(this, Banco.class);
				banco.setAction("inserir");
				banco.putStringArrayListExtra("dados", (ArrayList<String>) this.data);
				startActivity(banco);
			}
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case android.R.id.home:
				getSupportFragmentManager().popBackStack();
				getSupportActionBar().setDisplayHomeAsUpEnabled(false);
				getSupportActionBar().setHomeButtonEnabled(false);
				return false;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public void onBotoesMenu(View v) {
		switch (v.getId())  {
		case  R.id.botaoJogar:
			Intent game = new Intent(this, GameActivity.class);
			game.putExtra("difficulty", difficulty);
			startActivityForResult(game, RESULTADO_FINAL);
			break;
		case R.id.botaoOpcoes:
			options = new OpcoesFragment();
			getSupportFragmentManager().beginTransaction()
			.replace(R.id.fragment_container, options)
			.addToBackStack(null).commit();
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			break;
		case R.id.botaoRanking:
			Intent banco = new Intent(this, Banco.class);
			banco.setAction("ranking");
			startActivity(banco);
			break;
			
		
		}
	}
	
	
	
	public void mensagem(String mensagem) {
		Toast.makeText(getApplicationContext(), mensagem, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onItemSelected(View v) {
		radioView = v;
		
	}

	@Override
	public void onOptionsResult(View v) {
		if (radioView != null) {
			boolean checked = ((RadioButton)radioView).isChecked();
		
			switch(radioView.getId()) {
			case R.id.radioEasy:
				if (checked) {
					difficulty = 1;
					}
			break;
			case R.id.radioMedium:
				if (checked) {
					difficulty = 2;
				}
			break;
			case R.id.radioHard:
				if (checked) {
					difficulty = 3;
				}
			break;
			}
			mensagem(""+difficulty);
			getFragmentManager().popBackStack();	
		} else {
			mensagem("null");
		}
		
	}

}
