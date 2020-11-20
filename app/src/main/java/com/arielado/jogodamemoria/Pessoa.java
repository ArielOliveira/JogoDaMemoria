package com.arielado.jogodamemoria;

import java.io.Serializable;

public class Pessoa implements Serializable {

	private String Player = "Player", tempo;
	private int  erros = 0, acertos = 12, 
			 pontos = 100, minutes = 0, seconds = 0;
	
	
	

	public synchronized int getMinutes() {	
		return minutes;
	}
	
	public synchronized int getStartingSecs() {
		return seconds;
	}

	public synchronized int getSeconds() {
		try {
			wait();
		} catch (InterruptedException e) {}
		return seconds;
	}
	
	public synchronized String getFinalTempo() {
		if (this.seconds < 10) {
			tempo = this.minutes+":"+"0"+this.seconds;
		} else {
			tempo = this.minutes+":"+this.seconds;
		}
		return tempo;
	}
	
	public String getTempo() {
		return tempo;
	}

	public synchronized void setMinutes() {
		this.minutes++;
	}

	public synchronized void setSeconds() {
		this.seconds++;
		if (this.seconds == 60) {
			this.seconds = 0;
			setMinutes();
		}
		notifyAll();
	}

	public synchronized int getPontos() {
		try {
			wait();
		} catch (InterruptedException e) {}
		return pontos;
	}
	
	public synchronized int getFinalPontos() {
		return pontos;
	}

	public synchronized void setPontos() {
		this.pontos--;
	}
	

	public int getAcertos() {
		return acertos;
	}

	public String getPlayer() {
		return Player;
	}

	public void setPlayer(String player) {
		Player = player;
	}

	public int getErros() {
		return erros;
	}

	public void somarErros() {
		this.erros++;
	}

	public void somarAcertos() {
		this.acertos--;
	}

	public void setLogin(String player) {
		Player = player;
	}


	public String getLogin() {
		return Player;
	}
	
	public void zerar() {
		this.pontos = 100;
		this.minutes = 0;
		this.seconds = 0;
		this.acertos = 12;
		this.erros = 0;
		this.tempo = "0:00";
	}
	
	public synchronized int pontuacaoFinal() {
		return this.pontos -= this.erros;
	}





}
