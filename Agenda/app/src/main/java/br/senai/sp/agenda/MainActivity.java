package br.senai.sp.agenda;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.content.Intent;
import android.view.MenuInflater;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.List;

import br.senai.sp.dao.ContatoDAO;
import br.senai.sp.modelo.Contato;

public class MainActivity extends AppCompatActivity {

    private ListView listaContatos;
    private Button btnNovoContato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaContatos = findViewById(R.id.lista_contatos);

        btnNovoContato = findViewById(R.id.bt_novo_contato);

        btnNovoContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abrirCadastro = new Intent(MainActivity.this, CadastroContatoActivity.class);

                startActivity(abrirCadastro);


            }
        });


        registerForContextMenu(listaContatos);

        listaContatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contato contato = (Contato) listaContatos.getItemAtPosition(position);

                Intent cadastro = new Intent(MainActivity.this, CadastroContatoActivity.class);
                cadastro.putExtra("contato", contato);
                startActivity(cadastro);
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.menu_context_lista_contatos, menu);


        super.onCreateContextMenu(menu, v, menuInfo);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {

        final ContatoDAO dao = new ContatoDAO(MainActivity.this);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Contato contato = (Contato) listaContatos.getItemAtPosition(info.position);



        AlertDialog.Builder caixa = new AlertDialog.Builder(this);

        caixa.setTitle("Excluir contato");
        caixa.setMessage("Confirma a exclusão do contato " + contato.getNome() + " ?");

        caixa.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dao.excluir(contato);
                dao.close();

                CarregarContatos();

                Toast.makeText(MainActivity.this, contato.getNome() +  " excluído com sucesso!", Toast.LENGTH_LONG).show();
            }
        });

        caixa.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        caixa.create().show();


        return super.onContextItemSelected(item);
    }

    @Override
    protected void onResume() {

        CarregarContatos();

        super.onResume();
    }

    private void CarregarContatos(){

        ContatoDAO dao = new ContatoDAO(this);

        List<Contato> contatos = dao.getContatos();

        dao.close();

        ArrayAdapter<Contato> adapterContato = new ArrayAdapter<Contato>(this, android.R.layout.simple_list_item_1, contatos);

        listaContatos.setAdapter(adapterContato);

    }
}
