package com.arielado.jogodamemoria;



import java.io.Serializable;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Button;

public class OpcoesFragment extends Fragment {
	
	onOptionsItemsSelected itemSelected;
	View radioView;
	
	public interface onOptionsItemsSelected {
		public void onItemSelected(View v);
		public void onOptionsResult(View v);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (savedInstanceState != null) {
			
		}
	}
	
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	
		try {
			itemSelected = (onOptionsItemsSelected) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ "Needs to implement onOptionsItemSelected");
		}
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceSate) {
		View view = inflater.inflate(R.layout.fragment_options, container, false);
		
		RadioButton rdEasy = (RadioButton)view.findViewById(R.id.radioEasy);
		rdEasy.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onRadioClick(v);
				
			}
		});
		
		RadioButton rdMedium = (RadioButton)view.findViewById(R.id.radioMedium);
		rdMedium.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onRadioClick(v);
				
			}
		});
		
		RadioButton rdHard = (RadioButton)view.findViewById(R.id.radioHard);
		rdHard.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onRadioClick(v);
				
			}
		});
		
		Button btApply = (Button)view.findViewById(R.id.btApply);
		btApply.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onApplyClick(v);
				
			}
		});	
		return view;
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (radioView != null) {
			
		}
		 
	}
	
	public void onRadioClick(View v) {
		itemSelected.onItemSelected(v);
		radioView = (RadioButton) v;
	}
	
	public void onApplyClick(View v) {
		itemSelected.onOptionsResult(v);
	}
	

	
	

	

}
