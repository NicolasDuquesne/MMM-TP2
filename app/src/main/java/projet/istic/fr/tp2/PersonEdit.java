package projet.istic.fr.tp2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class PersonEdit extends AppCompatActivity implements OnClickListener {

    private Button save, delete;
    private String mode;
    private EditText firstName, lastName, date, city;
    private String id;
    private boolean phoneAdded = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // get the values passed to the activity from the calling activity
        // determine the mode - add, update or delete
        if (this.getIntent().getExtras() != null) {
            Bundle bundle = this.getIntent().getExtras();
            mode = bundle.getString("mode");
        }

        // get references to the buttons and attach listeners
        save = (Button) findViewById(R.id.valider);
        save.setOnClickListener(this);
        delete = (Button) findViewById(R.id.supprimer);
        delete.setOnClickListener(this);

        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        date = (EditText) findViewById(R.id.date);
        city = (EditText) findViewById(R.id.city);

        // if in add mode disable the delete option
        if (mode.trim().equalsIgnoreCase("add")) {
            delete.setEnabled(false);
        }
        // get the rowId for the specific country
        else {
            Bundle bundle = this.getIntent().getExtras();
            id = bundle.getString("rowId");
            loadCountryInfo();
        }

    }

    public void onClick(View v) {

        // get values from the input text fields
        String myFirstName = firstName.getText().toString();
        String myLastName = lastName.getText().toString();
        String myDate = date.getText().toString();
        String myCity = city.getText().toString();

        // check for blanks
        if (myFirstName.trim().equalsIgnoreCase("")) {
            Toast.makeText(getBaseContext(), "Please ENTER firstname", Toast.LENGTH_LONG).show();
            return;
        }

        // check for blanks
        if (myLastName.trim().equalsIgnoreCase("")) {
            Toast.makeText(getBaseContext(), "Please ENTER lastname", Toast.LENGTH_LONG).show();
            return;
        }

        // check for blanks
        if (myDate.trim().equalsIgnoreCase("")) {
            Toast.makeText(getBaseContext(), "Please ENTER date", Toast.LENGTH_LONG).show();
            return;
        }

        // check for blanks
        if (myCity.trim().equalsIgnoreCase("")) {
            Toast.makeText(getBaseContext(), "Please ENTER city", Toast.LENGTH_LONG).show();
            return;
        }


        switch (v.getId()) {
            case R.id.valider:
                ContentValues values = new ContentValues();
                values.put(PersonDB.KEY_FIRSTNAME, myFirstName);
                values.put(PersonDB.KEY_LASTNAME, myLastName);
                values.put(PersonDB.KEY_DATE, myDate);
                values.put(PersonDB.KEY_CITY, myCity);

                // insert a record
                if (mode.trim().equalsIgnoreCase("add")) {
                    getContentResolver().insert(MyContentProvider.CONTENT_URI, values);
                }
                // update a record
                else {
                    Uri uri = Uri.parse(MyContentProvider.CONTENT_URI + "/" + id);
                    getContentResolver().update(uri, values, null, null);
                }
                finish();
                break;

            case R.id.supprimer:
                // delete a record
                Uri uri = Uri.parse(MyContentProvider.CONTENT_URI + "/" + id);
                getContentResolver().delete(uri, null, null);
                finish();
                break;

            // More buttons go here (if any) ...

        }
    }

    // based on the rowId get all information from the Content Provider
    // about that country
    private void loadCountryInfo() {

        String[] projection = {
                PersonDB.KEY_ROWID,
                PersonDB.KEY_FIRSTNAME,
                PersonDB.KEY_LASTNAME,
                PersonDB.KEY_DATE,
                PersonDB.KEY_CITY};
        Uri uri = Uri.parse(MyContentProvider.CONTENT_URI + "/" + id);
        Cursor cursor = getContentResolver().query(uri, projection, null, null,
                null);
        if (cursor != null) {
            cursor.moveToFirst();
            String myFirstName = cursor.getString(cursor.getColumnIndexOrThrow(PersonDB.KEY_FIRSTNAME));
            String myLastName = cursor.getString(cursor.getColumnIndexOrThrow(PersonDB.KEY_LASTNAME));
            String myDate = cursor.getString(cursor.getColumnIndexOrThrow(PersonDB.KEY_DATE));
            String myCity = cursor.getString(cursor.getColumnIndexOrThrow(PersonDB.KEY_CITY));
            firstName.setText(myFirstName);
            lastName.setText(myLastName);
            date.setText(myDate);
            city.setText(myCity);
        }


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
}