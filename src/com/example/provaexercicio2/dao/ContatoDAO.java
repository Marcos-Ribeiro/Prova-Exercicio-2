package com.example.provaexercicio2.dao;

import java.util.ArrayList;
import java.util.List;

import com.example.provaexercicio2.model.Contato;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ContatoDAO extends SQLiteOpenHelper{
	
	private static final String TABELA = "contato";
	private static final String DATABASE = "Contatos";
	private static final int VERSAO = 1;
	
	public ContatoDAO(Context context){
		super(context, DATABASE, null, VERSAO);
	}

	
	@Override
	public void onCreate(SQLiteDatabase db) {
		String ddl = "CREATE TABLE "
				+ TABELA
				+ " (id INTEGER PRIMARY KEY,"
				+ "nome TEXT,"
				+ "telefone TEXT);";
		
		db.execSQL(ddl);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		String dll = "DROP TABLE IF EXISTS " + TABELA;
		db.execSQL(dll);
		
		this.onCreate(db);
	}

	public void salva(Contato contato){
		ContentValues values = toContentValues(contato);
		
		getWritableDatabase().insert(TABELA, null, values);
}

	private ContentValues toContentValues(Contato contato) {
		ContentValues values = new ContentValues();
		values.put("nome", contato.getNome());
		values.put("telefone", contato.getTelefone());
		
		return values;
	}
	
	public void alterar(Contato contato) {
		ContentValues values = toContentValues(contato);
		
		String[] args = { contato.getId().toString()};
		getWritableDatabase().update(TABELA, values, "id=?", args);
	}
	
	
	public List<Contato> getLista(){
		
		String[] colunas = {"id", "nome", "telefone"};
		try{
		Cursor cursor = getWritableDatabase().query(TABELA, colunas, null, null, null, null, null);
		
		ArrayList<Contato> contatos = new ArrayList<Contato>();
		
		while(cursor.moveToNext()){
			Contato contato = new Contato();
			
			contato.setId(cursor.getLong(0));
			contato.setNome(cursor.getString(1));
			contato.setTelefone(cursor.getString(2));
			
			contatos.add(contato);
		}
		return contatos;
		}catch (Exception e){
			System.out.println(e.getMessage());;
			return null;
		}
		
	}
	
	public void deletar(Contato contato) {
		
		String[] args = { contato.getId().toString()};
		getWritableDatabase().delete(TABELA, "id=?", args);
		
	}	
		
}
