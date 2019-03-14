package br.senai.sp.agenda;

import android.content.DialogInterface;
import android.support.design.widget.TextInputLayout;
import android.support.transition.Visibility;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.content.Intent;

import android.widget.Button;

import android.net.Uri;

import java.util.List;

import br.senai.sp.dao.ContatoDAO;
import br.senai.sp.modelo.Contato;

public class CadastroContatoActivity extends AppCompatActivity {

    CadastroContatoHelper helper;

    private Button btnLigar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_contato);

        helper = new CadastroContatoHelper(this);

        Intent intent = getIntent();

        final Contato contato = (Contato) intent.getSerializableExtra("contato");

        if(contato != null){
            helper.preencherFormulario(contato);
        }

        btnLigar = findViewById(R.id.bt_ligar);

        if(contato == null){
            btnLigar.setVisibility(View.INVISIBLE);
        }

        btnLigar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chamada = new Intent(Intent.ACTION_DIAL);

                chamada.setData(Uri.parse("tel:" + contato.getTelefone()));

                startActivity(chamada);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_cadastro_contatos, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final Contato contato = helper.getContatos();

        final ContatoDAO dao = new ContatoDAO(this);

        switch (item.getItemId()) {


            case R.id.menu_save:

                if (helper.validar(this) == true) {
                    if (contato.getId() == 0) {
                        dao.salvar(contato);

                        dao.close();

                        Toast.makeText(this, "Contato gravado com sucesso!!", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        dao.atualizar(contato);

                        dao.close();

                        Toast.makeText(this, "Contato gravado com sucesso!!", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }


                break;

            case R.id.menu_del:

                if(contato.getId() != 0){
                    AlertDialog.Builder caixa = new AlertDialog.Builder(this);

                    caixa.setTitle("Excluir contato");
                    caixa.setMessage("Confirma a exclusão do contato " + contato.getNome() + " ?");

                    caixa.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dao.excluir(contato);
                            dao.close();

                            finish();

                            Toast.makeText(CadastroContatoActivity.this,"Contato excluído com sucesso!", Toast.LENGTH_LONG).show();
                        }
                    });

                    caixa.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    caixa.create().show();
                }


                break;

            case android.R.id.home:
                finish();

                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
