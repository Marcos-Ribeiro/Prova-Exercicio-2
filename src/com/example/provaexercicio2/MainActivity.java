package com.example.provaexercicio2;

import java.util.ArrayList;
import java.util.List;

import com.example.provaexercicio2.dao.ContatoDAO;
import com.example.provaexercicio2.model.Contato;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends Activity {

	ListView listaContatos;
	Button btnNovoContato;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		listaContatos = (ListView) findViewById(R.id.listaContatos);
		btnNovoContato = (Button) findViewById(R.id.btnNovoContato);
		btnNovoContato.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, ContatoFormActivity.class);
				startActivity(i);
			}
		});
		
		listaContatos.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				deleteAlerta((Contato) parent.getItemAtPosition(position));
				return false;
			}
		});
		
		listaContatos.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent i = new Intent(MainActivity.this, ContatoFormActivity.class);
				i.putExtra("contato", (Contato) parent.getItemAtPosition(position));
				startActivity(i);
			}
		});
		
	}

	private void populaLista() {
		ContatoDAO dao = new ContatoDAO(this);
		
		List<Contato> lista = new ArrayList<Contato>();
		
		lista = dao.getLista();
		
		dao.close();
		
		ArrayAdapter<Contato> adapter = new ArrayAdapter<Contato>(this, 
				android.R.layout.simple_list_item_1, lista);
		
		listaContatos.setAdapter(adapter);
	}

	@Override
	protected void onResume() {
		populaLista();
		super.onResume();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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

	public void deleteAlerta(final Contato contato){
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setTitle("Excluir");
        builder.setMessage("Deseja mesmo excluir esse item?");
        builder.setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               ContatoDAO dao = new ContatoDAO(MainActivity.this);
               dao.deletar(contato);
               
               populaLista();
            }
        });
        builder.setNeutralButton("Cancelar", null);

        AlertDialog dialog = builder.create();
        dialog.show();

	}
}
