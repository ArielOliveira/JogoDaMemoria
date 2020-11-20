package com.arielado.jogodamemoria;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;



public class MenuFragment extends Fragment {
	
	OuvinteBotao ouvinte;
	
	
	public interface OuvinteBotao {
		public void onBotoesMenu(View v);
		
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		try {
			ouvinte = (OuvinteBotao) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
			+ "Implementar OuvinteBotao");
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_main_menu, container, false);
		
		final Button bt1 = (Button)view.findViewById(R.id.botaoJogar);
		bt1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				botoesMenu(v);
			}
		});
		final Button bt2 = (Button)view.findViewById(R.id.botaoOpcoes);
		bt2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				botoesMenu(v);
			}
		});
		final Button bt3 = (Button)view.findViewById(R.id.botaoRanking);
		bt3.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				botoesMenu(v);
			}
		});
		
		return view;
	}
	
	public void botoesMenu(View v) {
		ouvinte.onBotoesMenu(v);
	}

}

