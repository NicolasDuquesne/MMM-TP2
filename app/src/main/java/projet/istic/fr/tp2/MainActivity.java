package projet.istic.fr.tp2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private boolean phoneAdded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Récupération de l'intent
        Intent main = getIntent();

        //
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Bouton en bas à droite avec logo email (pas utile)
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        //action lorsque l'on clique sur Valider
        View.OnClickListener validerOnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Récupération des valeurs des champs du formulaire
                EditText lastName = (EditText) findViewById(R.id.lastName);
                EditText firstName = (EditText) findViewById(R.id.firstName);
                EditText date = (EditText) findViewById(R.id.date);
                EditText city = (EditText) findViewById(R.id.city);

                //Popup affichant les données (question 6, partie 1.1)
                /*String conf = "Vos données ont bien été enregistrées " + lastName.getText().toString() + " " + firstName.getText().toString() +
                        ", né(e) le " + date.getText().toString() + " à " + city.getText().toString();

                Toast.makeText(getApplicationContext(),
                        conf, Toast.LENGTH_SHORT).show();*/

                //Création d'un Intent vers Affiche (TP1)
                //Intent iAffiche = new Intent(MainActivity.this, Affiche.class);

                //Création d'un Intent vers ListViewPerson
                Intent iListView = new Intent(MainActivity.this, ListViewPerson.class);

                //Envoi des valeurs avec une clé pour les identifier
                /*iAffiche.putExtra("lastName", lastName.getText().toString());
                iAffiche.putExtra("firstName", firstName.getText().toString());
                iAffiche.putExtra("date", date.getText().toString());
                iAffiche.putExtra("city", city.getText().toString());*/

                //Création d'un objet parcelable Person
                /*Person p = new Person(lastName.getText().toString(),
                        firstName.getText().toString(),
                        date.getText().toString(),
                        city.getText().toString());*/
                //Envoi de l'objet vers Affiche avec une clé pour l'identifier
                //iAffiche.putExtra("Person", p);

                //Envoi de l'objet vers ListViewPerson avec une clé pour l'identifier
                //iListView.putExtra("Person", p);

                //Lancement de l'Intent iAffiche
                //startActivity(iAffiche);

                //Lancement de l'Intent iListViewPerson
                startActivity(iListView);

            }
        };

        Button valider = (Button) findViewById(R.id.valider);
        valider.setOnClickListener(validerOnClick);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        //Action de l'option "Vider le formulaire"
        if (id == R.id.action_empty) {
            EditText lastName = (EditText) findViewById(R.id.lastName);
            EditText firstName = (EditText) findViewById(R.id.firstName);
            EditText date = (EditText) findViewById(R.id.date);
            EditText city = (EditText) findViewById(R.id.city);
            lastName.setText("");
            firstName.setText("");
            date.setText("");
            city.setText("");
        }

        //Action de l'option "Ajout Téléphone"
        if (id == R.id.action_addPhone) {
            if(!phoneAdded) {
                LinearLayout main = (LinearLayout) findViewById(R.id.layout);
                EditText phone = new EditText(getApplicationContext());
                phone.setText("Téléphone");
                phone.setBackgroundColor(R.color.colorPrimaryDark);
                main.addView(phone);
                item.setEnabled(false);
                phoneAdded = true;
            }
        }

        //Action de l'option "Wikipedia"
        if (id == R.id.action_wiki) {
            EditText city = (EditText) findViewById(R.id.city);
            Intent web = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://fr.wikipedia.org/wiki/"
                            + (city.getText())));
            startActivity(web);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
