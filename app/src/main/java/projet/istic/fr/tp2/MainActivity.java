package projet.istic.fr.tp2;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends Activity implements
        LoaderManager.LoaderCallbacks<Cursor>{

    private ListView maListViewPerso;
    private SimpleAdapter mListAdapter;
    private ArrayList<HashMap<String, String>> listItem;

    private SimpleCursorAdapter dataAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayListView();

        //Récupération de la listview créée dans le fichier content_main.xml
/*        maListViewPerso = (ListView) findViewById(R.id.listViewCustomer);

        //Création de la ArrayList qui nous permettra de remplir la listView
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

        });*/

        Button add = (Button) findViewById(R.id.newCustomer);
        add.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // starts a new Intent to add a Country
                Intent personEdit = new Intent(getBaseContext(), PersonEdit.class);
                Bundle bundle = new Bundle();
                bundle.putString("mode", "add");
                personEdit.putExtras(bundle);
                startActivity(personEdit);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        //Starts a new or restarts an existing Loader in this manager
        getLoaderManager().restartLoader(0, null, this);
    }

    private void displayListView() {

        // The desired columns to be bound
        String[] columns = new String[] {
                PersonDB.KEY_FIRSTNAME,
                PersonDB.KEY_LASTNAME,
                PersonDB.KEY_DATE,
                PersonDB.KEY_CITY
        };

        // the XML defined views which the data will be bound to
        int[] to = new int[] {
                R.id.firstNameItem,
                R.id.lastNameItem,
                R.id.dateItem,
                R.id.cityItem,
        };

        // create an adapter from the SimpleCursorAdapter
        dataAdapter = new SimpleCursorAdapter(
                this,
                R.layout.item,
                null,
                columns,
                to,
                0);

        // get reference to the ListView
        ListView listView = (ListView) findViewById(R.id.listViewCustomer);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);
        //Ensures a loader is initialized and active.
        getLoaderManager().initLoader(0, null, this);


        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view,
                                    int position, long id) {
                // Get the cursor, positioned to the corresponding row in the result set
                Cursor cursor = (Cursor) listView.getItemAtPosition(position);

                // display the selected country
                String countryCode =
                        cursor.getString(cursor.getColumnIndexOrThrow(PersonDB.KEY_FIRSTNAME));
                Toast.makeText(getApplicationContext(),
                        countryCode, Toast.LENGTH_SHORT).show();

                String rowId =
                        cursor.getString(cursor.getColumnIndexOrThrow(PersonDB.KEY_ROWID));

                // starts a new Intent to update/delete a Country
                // pass in row Id to create the Content URI for a single row
                Intent personEdit = new Intent(getBaseContext(), PersonEdit.class);
                Bundle bundle = new Bundle();
                bundle.putString("mode", "update");
                bundle.putString("rowId", rowId);
                personEdit.putExtras(bundle);
                startActivity(personEdit);

            }
        });

    }

    // This is called when a new Loader needs to be created.
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                PersonDB.KEY_ROWID,
                PersonDB.KEY_FIRSTNAME,
                PersonDB.KEY_LASTNAME,
                PersonDB.KEY_DATE,
                PersonDB.KEY_CITY};
        CursorLoader cursorLoader = new CursorLoader(this,
                MyContentProvider.CONTENT_URI, projection, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Swap the new cursor in.  (The framework will take care of closing the
        // old cursor once we return.)
        dataAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // This is called when the last Cursor provided to onLoadFinished()
        // above is about to be closed.  We need to make sure we are no
        // longer using it.
        dataAdapter.swapCursor(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
