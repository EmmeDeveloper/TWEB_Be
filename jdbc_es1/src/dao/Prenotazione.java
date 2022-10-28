package dao;

import java.time.LocalDate;
import java.time.LocalTime;

public class Prenotazione {

	// ha più senso salvare gli oggetti o semplicemente i relativi ID?
	private int id;
	private Utente utente;
	private Corso corso;
	private Docente docente;
	private LocalDate data;
	private int ora;

	public Prenotazione(int id, Utente utente, Corso corso, Docente docente, LocalDate data, int ora) {
		this.id = id;
		this.utente = utente;
		this.corso = corso;
		this.docente = docente;
		this.data = data;
		this.ora = ora;
	}

	public Prenotazione(Utente utente, Corso corso, Docente docente, LocalDate data, int ora) {
		this.utente = utente;
		this.corso = corso;
		this.docente = docente;
		this.data = data;
		this.ora = ora;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public Corso getCorso() {
		return corso;
	}

	public void setCorso(Corso corso) {
		this.corso = corso;
	}

	public Docente getDocente() {
		return docente;
	}

	public void setDocente(Docente docente) {
		this.docente = docente;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public int getOra() {
		return ora;
	}

	public void setOra(int ora) {
		this.ora = ora;
	}

	@Override
	public String toString() {
		return "";
	}
}
