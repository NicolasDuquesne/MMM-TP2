package projet.istic.fr.tp2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class Affiche extends AppCompatActivity {
    //Définition de variable correspondant aux clés pour identifier les valeurs de l'intent (cas sans objet parcelable)
    final String LASTNAME = "lastName";
    final String FIRSTNAME = "firstName";
    final String DATE = "date";
    final String CITY = "city";

    //Définition de variable correspondant à la clé pour identifier les valeurs de l'intent (cas avec objet parcelable)
    final String PERSON = "Person";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affiche);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Récupération de l'intent
        Intent affiche = getIntent();

        //Récupération des champs de l'activité
        TextView textLastName = (TextView) findViewById(R.id.textLastName);
        TextView textFirstName = (TextView) findViewById(R.id.textFirstName);
        TextView textDate = (TextView) findViewById(R.id.textDate);
        TextView textCity = (TextView) findViewById(R.id.textCity);

        //Set des valeurs de l'intent dans les champs de l'activité (partie sans Parcelable)
        /*textLastName.setText(affiche.getStringExtra(LASTNAME));
        textFirstName.setText(affiche.getStringExtra(FIRSTNAME));
        textDate.setText(affiche.getStringExtra(DATE));
        textCity.setText(affiche.getStringExtra(CITY));*/

        //Récupération de l'objet Parcelable et get sur les valeurs (partie avec Parcelable)
        Bundle bundlePerson = affiche.getExtras();
        Person p = bundlePerson.getParcelable(PERSON);
        String affLastName = p.getLastName();
        String affFirstName = p.getFirstName();
        String affDate = p.getDate();
        String affCity = p.getCity();

        //Set des valeurs de l'intent dans les champs de l'activité (partie avec Parcelable)
        textLastName.setText(affLastName);
        textFirstName.setText(affFirstName);
        textDate.setText(affDate);
        textCity.setText(affCity);

    }
}

