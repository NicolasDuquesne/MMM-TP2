package projet.istic.fr.tp2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class ListViewPerson extends AppCompatActivity{

    private ListView maListViewPerso;
    private SimpleAdapter mListAdapter;
    private ArrayList<HashMap<String, String>> listItem;

    private SimpleCursorAdapter dataAdapter;

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
        setContentView(R.layout.activity_list_view_person);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/


        //Récupération de la listview créée dans le fichier content_list_view_person.xml
        maListViewPerso = (ListView) findViewById(R.id.listViewCustomer);

        //Création de la ArrayList qui nous permettra de remplire la listView
        listItem = new ArrayList<HashMap<String, String>>();

        //On déclare la HashMap qui contiendra les informations pour un item
        HashMap<String, String> map;

        //Création d'une HashMap pour insérer les informations du premier item de notre listView
        map = new HashMap<String, String>();
        //on insère un élément firstname que l'on récupérera dans le textView firsname créé dans le fichier item.xml
        map.put("firstname", "Charles");
        //on insère un élément lastname que l'on récupérera dans le textView lastname créé dans le fichier item.xml
        map.put("lastname", "Dupont");
        //on insère un élément date que l'on récupérera dans le  textView date créé dans le fichier item.xml
        map.put("date", "01/01/1970");
        //on insère un élément city que l'on récupérera dans le  textView city créé dans le fichier item.xml
        map.put("city", "Rennes");
        //enfin on ajoute cette hashMap dans la arrayList
        listItem.add(map);

        //On refait la manip plusieurs fois avec des données différentes pour former les items de notre ListView

        map = new HashMap<String, String>();
        map.put("firstname", "Jacques");
        map.put("lastname", "Dupont");
        map.put("date", "15/03/1985");
        map.put("city", "Rennes");
        listItem.add(map);

        //Récupération de l'intent
        Intent ajout = getIntent();

        //Création d'un SimpleAdapter qui se chargera de mettre les items présent dans notre list (listItem) dans la vue item
        mListAdapter = new SimpleAdapter (this.getBaseContext(), listItem, R.layout.item,
                new String[] {"firstname", "lastname", "date", "city"}, new int[] {R.id.firstNameItem, R.id.lastNameItem, R.id.dateItem, R.id.cityItem});

        //On attribue à notre listView l'adapter que l'on vient de créer
        maListViewPerso.setAdapter(mListAdapter);

        maListViewPerso.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                //on récupère la HashMap contenant les infos de notre item (titre, description, img)
                HashMap<String, String> map = (HashMap<String, String>) maListViewPerso.getItemAtPosition(position);
                //on créer une boite de dialogue
                AlertDialog.Builder adb = new AlertDialog.Builder(ListViewPerson.this);
                //on attribut un titre à notre boite de dialogue
                adb.setTitle("Sélection Client");
                //on insère un message à notre boite de dialogue, et ici on affiche le titre de l'item cliqué
                adb.setMessage("Votre choix : "+map.get("firstname"));
                //on indique que l'on veut le bouton ok à notre boite de dialogue
                adb.setPositiveButton("Ok", null);
                //on affiche la boite de dialogue
                adb.show();
            }

        });

        //action lorsque l'on clique sur Nouveau client
        View.OnClickListener newCustomerOnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Création d'un Intent pour appeler l'activité main
                Intent iMain = new Intent(ListViewPerson.this, MainActivity.class);
                startActivity(iMain);
            }
        };

        Button newCustomer = (Button) findViewById(R.id.newCustomer);
        newCustomer.setOnClickListener(newCustomerOnClick);

    }

    public void addItem(View v) {
        HashMap<String, String> map;
        map = new HashMap<String, String>();
        map.put("firstname", "");
        map.put("lastname", "");
        map.put("date", "");
        map.put("city", "");
        listItem.add(map);
        mListAdapter.notifyDataSetChanged();


    }
}
