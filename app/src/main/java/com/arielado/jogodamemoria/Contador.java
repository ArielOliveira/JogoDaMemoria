package com.arielado.jogodamemoria;

import android.os.Handler;
import android.widget.TextView;


public class Contador implements Runnable {
	
	private Pessoa pessoa;
	private Handler threadHandler;
	private int difficulty;
	private String time;
	private TextView tempo;


		public Contador(Pessoa pessoa, TextView tempo, Handler threadHandler, int difficulty) {
			this.pessoa = pessoa;
			this.tempo = tempo;
			this.threadHandler = threadHandler;
			this.difficulty = difficulty;
		}


	@Override  
	public void run() {
		int minutes = 0;
		int seconds;
		if (difficulty == 2) {
			seconds = 20;
			try {
				while ((!Thread.interrupted()) || (seconds > 0)) {
					threadHandler.post(new InnerThread());
					if (seconds >= 10) {
						time = minutes+":"+seconds;
					} else {
						time = minutes+":"+"0"+seconds;
					}
					Thread.sleep(1000);
					seconds--;
				}
			} catch (InterruptedException e) {}
				
			
		} else if (difficulty == 3) {
			seconds = 10;
			try {
				while ((!Thread.interrupted()) || (seconds > 0)) {
				threadHandler.post(new InnerThread());
				if (seconds >= 10) {
					time = minutes+":"+seconds;
				} else {
					time = minutes+":"+"0"+seconds;
				}
				Thread.sleep(1000);
				seconds--;
				}
			} catch (InterruptedException e) {}
		}
		
		try {
			minutes = pessoa.getMinutes();
			seconds = pessoa.getStartingSecs();
			while (!Thread.interrupted()) {
				Thread.sleep(1000);
				pessoa.setSeconds();
				pessoa.setPontos();
				seconds++;
				if (seconds == 60) {
					seconds = 0;
					minutes++;
				}
				if (seconds < 10) {
					time = minutes+":"+"0"+seconds;
				} else {
					time = minutes+":"+seconds;
				}
				threadHandler.post(new InnerThread());
			}
		} catch (InterruptedException e) {}
	}
	
	class InnerThread implements Runnable {
		
		@Override
		public void run() {
			tempo.setText(time);
		}
	}
	

}
