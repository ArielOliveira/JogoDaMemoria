package com.arielado.jogodamemoria;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Sorteio {
	String[] pares = new String[] {"par", "ÍMPAR", "PAre", "Vá", "viSH", "FAil",
			"Fá", "FuI", "SeR", "VER", "FaT", "GO!",
			" ", "ExE", "ROM", "RIM", "RAM", "fLy",
			"CHAT", "KeY", "TER", "SkY", "FaLL", 
			"SIM", "好的", "中文", "用", "你好"};

	Map<String, Boolean> palavraSorteada;
	int minimoPares;
	int count;

	public static Random r;
	public Sorteio(int minimoPares, long seed) {
		r = new Random(seed);
		this.minimoPares = minimoPares;
		palavraSorteada = new HashMap<String, Boolean>();
		count = 0;

		for (int i = 0; i < pares.length; i++) {
			palavraSorteada.put(pares[i], false);
		}
	}
	
	public String proximaPalavra() {
		String parSorteado = pares[(random())];
		while(palavraSorteada.get(parSorteado) && count < minimoPares) {
			parSorteado = pares[(random())];
		}
		count++;
		palavraSorteada.replace(parSorteado, true);
		return parSorteado;
	}

	public boolean getWordStatus(String word) {
		return palavraSorteada.get(word);
	}

	public int random() {
	        return r.nextInt(pares.length);
	    }
	
}
