package com.example.provaexercicio2;

import com.example.provaexercicio2.dao.ContatoDAO;
import com.example.provaexercicio2.model.Contato;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ContatoFormActivity extends Activity {

	EditText edtNome, edtTelefone;
	Button btnSalvarContato;
	Contato contato = new Contato();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contato_form);
		
		edtNome = (EditText) findViewById(R.id.edtNome);
		edtTelefone = (EditText) findViewById(R.id.edtTelefone);
		
		Intent i = getIntent();
		final Contato contatoSelecionado = (Contato) i.getSerializableExtra("contato");
		
		if (contatoSelecionado != null) {
			edtNome.setText(contatoSelecionado.getNome());
			edtTelefone.setText(contatoSelecionado.getTelefone());
		}
		
		btnSalvarContato = (Button) findViewById(R.id.btnSalvarContato);
		btnSalvarContato.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				contato.setNome(edtNome.getText().toString());
				contato.setTelefone(edtTelefone.getText().toString());
				
				ContatoDAO dao = new ContatoDAO(ContatoFormActivity.this);
				
				if (contatoSelecionado == null) {
					dao.salva(contato);
				}else{
					contato.setId(contatoSelecionado.getId());
					dao.alterar(contato);
				}
				
				finish();
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contato_form, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
