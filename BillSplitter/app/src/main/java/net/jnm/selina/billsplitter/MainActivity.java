package net.jnm.selina.billsplitter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static MainActivity instance = null;
    private static MealAdapter MA = null;
    private static PersonAdapter PA = null;

    private Button mealButton;
    private Button addButton;
    private EditText personNameField;
    private RecyclerView personView;

    private MealAdapter ma;
    private PersonAdapter pa;

    private Person activePerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;

        mealButton = (Button)findViewById(R.id.mealButton);
        addButton = (Button)findViewById(R.id.addButton);
        personNameField = (EditText)findViewById(R.id.personNameField);
        personView = (RecyclerView)findViewById(R.id.personView);

        ma = MA;
        pa = PA;
        if( ma == null && pa == null ) {
            pa = new PersonAdapter(new OnClickListener() {
                @Override
                public void click(Person p) {
                    activePerson = p;
                    Intent editPerson = new Intent(getBaseContext(), EditPersonActivity.class);
                    startActivity(editPerson);
                }
            });
            ma = new MealAdapter();
        } else {
            pa.setListener(new OnClickListener() {
                @Override
                public void click(Person p) {
                    activePerson = p;
                    Intent editPerson = new Intent(getBaseContext(), EditPersonActivity.class);
                    startActivity(editPerson);
                }
            });
        }
        personView.setAdapter(pa);
        personView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        mealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mealIntent = new Intent(getBaseContext(),ManageMealsActivity.class);
                startActivity(mealIntent);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = personNameField.getText().toString();
                if(name.length() > 0 ){
                    pa.addPerson(name);
                    personNameField.setText("");
                } else {
                    Toast.makeText(getBaseContext(),"Please input a name",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MA = ma;
        PA = pa;
    }

    public void deleteActive() {
        activePerson.reset();
        pa.deletePerson(activePerson.getName());
    }

    public Person getActive() {
        return activePerson;
    }

    public MealAdapter getMealAdapter() {
        return ma;
    }

    public PersonAdapter getPersonAdapter() {
        return pa;
    }

    public void clearMeal(Meal m) {
        for(int i = 0; i < pa.getItemCount(); i++ ) {
            pa.get(i).removePayment(m);
        }
    }

    public Person[] getPersonsFor(Meal m) {
        return pa.getPersonsFor(m);
    }

    public void updatePerson(String oldName, String newName, double cash) {
//        Log.i("MainActivity", "Updating " + oldName + " to " + newName + " with Php" + cash);
        pa.updatePerson(oldName, newName, cash);
    }

    @Override
    protected void onResume() {
        super.onResume();
        pa.updateGUI();
    }

    public interface OnClickListener {
        public void click(Person p);
    }
}
