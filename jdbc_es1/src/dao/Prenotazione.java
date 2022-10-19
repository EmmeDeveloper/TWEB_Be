package dao;

import java.time.LocalDate;
import java.time.LocalTime;

public class Prenotazione {

	// ha più senso salvare gli oggetti o semplicemente i relativi ID?
	private int id, id_utente, id_corso, id_docente;
	private LocalDate data;
	private LocalTime ora;

	public Prenotazione(int id, int id_utente, int id_corso, int id_docente, LocalDate data, LocalTime ora) {
		this.id = id;
		this.id_utente = id_utente;
		this.id_corso = id_corso;
		this.id_docente = id_docente;
		this.data = data;
		this.ora = ora;
	}

	public Prenotazione(int id_utente, int id_corso, int id_docente, LocalDate data, LocalTime ora) {
		this.id_utente = id_utente;
		this.id_corso = id_corso;
		this.id_docente = id_docente;
		this.data = data;
		this.ora = ora;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_utente() {
		return id_utente;
	}

	public void setId_utente(int id_utente) {
		this.id_utente = id_utente;
	}

	public int getId_corso() {
		return id_corso;
	}

	public void setId_corso(int id_corso) {
		this.id_corso = id_corso;
	}

	public int getId_docente() {
		return id_docente;
	}

	public void setId_docente(int id_docente) {
		this.id_docente = id_docente;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public LocalTime getOra() {
		return ora;
	}

	public void setOra(LocalTime ora) {
		this.ora = ora;
	}

	@Override
	public String toString() {
		return "";
	}
}
