package dao;

import java.time.LocalDate;
import java.time.LocalTime;

public class Prenotazione {

	// ha più senso salvare gli oggetti o semplicemente i relativi ID?
	private int id;
	private Utente utente;
	private Corso corso;
	private Docente docente;
	private String data, ora;

	public Prenotazione(int id, Utente utente, Corso corso, Docente docente, String data, String ora) {
		this.id = id;
		this.utente = utente;
		this.corso = corso;
		this.docente = docente;
		this.data = data;
		this.ora = ora;
	}

	public Prenotazione(Utente utente, Corso corso, Docente docente, String data, String ora) {
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

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getOra() {
		return ora;
	}

	public void setOra(String ora) {
		this.ora = ora;
	}

	@Override
	public String toString() {
		return "";
	}
}
