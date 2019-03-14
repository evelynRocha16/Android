package br.senai.sp.agenda;

import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

import br.senai.sp.mascara.Mask;
import br.senai.sp.modelo.Contato;

import android.content.Context;

public class CadastroContatoHelper {

    private TextInputLayout layoutNome;
    private TextInputLayout layoutEndereco;
    private TextInputLayout layoutTelefone;
    private TextInputLayout layoutEmail;
    private TextInputLayout layoutLinkedin;

    private EditText txtNome;
    private EditText txtEndereco;
    private EditText txtTelefone;
    private EditText txtEmail;
    private EditText txtLinkedin;
    private Contato contato;

    public CadastroContatoHelper(CadastroContatoActivity activity){
        txtNome = activity.findViewById(R.id.txt_nome);
        txtEndereco = activity.findViewById(R.id.txt_endereco);
        txtTelefone = activity.findViewById(R.id.txt_telefone);
        txtEmail = activity.findViewById(R.id.txt_email);
        txtLinkedin = activity.findViewById(R.id.txt_linkedin);

        layoutNome = activity.findViewById(R.id.layout_txt_nome);
        layoutEndereco = activity.findViewById(R.id.layout_txt_endereco);
        layoutTelefone = activity.findViewById(R.id.layout_txt_telefone);
        layoutEmail = activity.findViewById(R.id.layout_txt_email);
        layoutLinkedin = activity.findViewById(R.id.layout_txt_linkedin);

        txtTelefone.addTextChangedListener(Mask.insert("(##)#####-####",txtTelefone));

        contato = new Contato();
    }

    public Contato getContatos(){

        contato.setNome(txtNome.getText().toString());
        contato.setEndereco(txtEndereco.getText().toString());
        contato.setTelefone(txtTelefone.getText().toString());
        contato.setEmail(txtEmail.getText().toString());
        contato.setLinkedin(txtLinkedin.getText().toString());

        return contato;
    }

    public void preencherFormulario(Contato contato) {
        txtNome.setText(contato.getNome());
        txtEndereco.setText(contato.getEndereco());
        txtTelefone.setText(contato.getTelefone());
        txtEmail.setText(contato.getEmail());
        txtLinkedin.setText(contato.getLinkedin());
        this.contato = contato;
    }

    public boolean validar(Context activity){
        boolean validado = true;

        if(txtNome.getText().toString().isEmpty()){
            layoutNome.setErrorEnabled(true);
            layoutNome.setError("Por favor digite o nome");

            validado = false;
        }
        else{
            layoutNome.setErrorEnabled(false);
        }

        if(txtEndereco.getText().toString().isEmpty()){
            layoutEndereco.setErrorEnabled(true);
            layoutEndereco.setError("Por favor digite o endereco");

            validado = false;
        }
        else{
            layoutEndereco.setErrorEnabled(false);
        }

        if(txtTelefone.getText().toString().isEmpty()){
            layoutTelefone.setErrorEnabled(true);
            layoutTelefone.setError("Por favor digite o telefone");

            validado = false;
        }else if(!txtTelefone.getText().toString().matches("^(\\([0-9]{2}\\)9[0-9]{4}-[0-9]{4})$")){
            layoutTelefone.setErrorEnabled(true);
            layoutTelefone.setError("Digite o telefone corretamente");

            validado = false;
        }
        else{
            layoutTelefone.setErrorEnabled(false);
        }

        if(txtEmail.getText().toString().isEmpty()){
            layoutEmail.setErrorEnabled(true);
            layoutEmail.setError("Por favor digite o e-mail");

            validado = false;
        }else if(!txtEmail.getText().toString().matches("^([a-z][a-z0-9._]+@([a-z0-9]+\\.)([a-z]+\\.?)+[a-z])$")){
            layoutEmail.setErrorEnabled(true);
            layoutEmail.setError("Digite o e-mail corretamente");

            validado = false;
        }
        else{
            layoutEmail.setErrorEnabled(false);
        }

        return validado;
    }
}
