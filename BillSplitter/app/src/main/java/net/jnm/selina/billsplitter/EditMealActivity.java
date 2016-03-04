package net.jnm.selina.billsplitter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class EditMealActivity extends AppCompatActivity {
    private static ArrayList<Person> LIST;

    private Button backButton;
    private Button saveButton;
    private Button deleteButton;
    private EditText nameField;
    private EditText priceField;
    private EditText quantityField;
    private RecyclerView personView;

    private Meal m;
    private ArrayList<Person> persons;
    private PersonCheckAdapter pca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_meal);

        m = ManageMealsActivity.instance.getActive();

        backButton = (Button)findViewById(R.id.backButton);
        saveButton = (Button)findViewById(R.id.saveButton);
        deleteButton = (Button)findViewById(R.id.deleteButton);
        nameField = (EditText)findViewById(R.id.nameField);
        priceField = (EditText)findViewById(R.id.priceField);
        quantityField = (EditText)findViewById(R.id.quantityField);
        personView = (RecyclerView)findViewById(R.id.personView);

        if( LIST == null ) {
            persons = new ArrayList<Person>();
            Person[] list = MainActivity.instance.getPersonsFor(m);
            for (Person p : list) {
                persons.add(p);
            }
        } else {
            persons = LIST;
        }

        nameField.setText(m.getName());
        priceField.setText(m.getPrice() + "");
        quantityField.setText(m.getQuantity() + "");

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameField.getText().toString();
                if( name.length() > 0 ) {
                    try {
                        double price = Double.parseDouble(priceField.getText().toString());
                        if( price < 0 ) {
                            Toast.makeText(getBaseContext(), "Cash must be a non-negative value"
                                    , Toast.LENGTH_LONG).show();
                        } else {
                            try {
                                int quantity = Integer.parseInt(quantityField.getText().toString());

                                if( quantity > 0 ) {
                                    MainActivity.instance.clearMeal(m);
                                    for (Person p : persons) {
                                        p.addPayment(m);
                                    }
                                    m.setName(name);
                                    m.setPrice(price);
                                    m.setQuantity(quantity);
                                    finish();
                                } else {
                                    Toast.makeText(getBaseContext(),"Quantity must be a positive integer."
                                            ,Toast.LENGTH_LONG).show();
                                }
                            } catch(NumberFormatException nfe) {
                                Toast.makeText(getBaseContext(),"Quantity must be a positive integer."
                                        ,Toast.LENGTH_LONG).show();
                            }
                        }
                    } catch( NumberFormatException nfe) {
                        Toast.makeText(getBaseContext(),"Cash must be a non-negative value"
                                ,Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getBaseContext(),"Please enter a name",Toast.LENGTH_LONG).show();
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManageMealsActivity.instance.deleteActive();
                finish();
            }
        });

        pca = new PersonCheckAdapter(MainActivity.instance.getPersonAdapter()
                                        , new OnClickListener() {
            @Override
            public void onClick(Person p, boolean checked) {
                if( checked ) {
                    if( persons.indexOf(p) == -1 ) {
                        persons.add(p);
                    }
                } else {
                    if( persons.indexOf(p) != -1 ) {
                        persons.remove(p);
                    }
                }
            }
        });
        pca.setList(persons);
        personView.setAdapter(pca);
        personView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LIST = persons;
    }

    public interface OnClickListener {
        public void onClick(Person p, boolean checked);
    }
}
