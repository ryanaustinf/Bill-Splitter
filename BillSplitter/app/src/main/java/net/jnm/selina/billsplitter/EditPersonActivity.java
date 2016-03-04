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

public class EditPersonActivity extends AppCompatActivity {
    private static ArrayList<Meal> LIST = null;
    private Button backButton;
    private Button saveButton;
    private Button deleteButton;
    private EditText nameField;
    private EditText cashField;
    private RecyclerView mealView;

    private Person p;
    private MealCheckAdapter mca;
    private ArrayList<Meal> meals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_person);

        p = MainActivity.instance.getActive();

        backButton = (Button)findViewById(R.id.backButton);
        saveButton = (Button)findViewById(R.id.saveButton);
        deleteButton = (Button)findViewById(R.id.deleteButton);
        nameField = (EditText)findViewById(R.id.nameField);
        cashField = (EditText)findViewById(R.id.cashField);
        mealView = (RecyclerView)findViewById(R.id.mealView);
        meals = new ArrayList<Meal>();

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
                        double cash = Double.parseDouble(cashField.getText().toString());
                        if( cash < 0 ) {
                            Toast.makeText(getBaseContext(),"Cash must be a non-negative value"
                                    ,Toast.LENGTH_LONG).show();
                        } else {
                            p.reset();
                            for(Meal m: meals) {
                                p.addPayment(m);
                            }
                            p.setName(name);
                            p.setCashTendered(cash);
                            finish();
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
                MainActivity.instance.deleteActive();
                finish();
            }
        });

        nameField.setText(p.getName());
        cashField.setText(p.getCashTendered() + "");

        MealAdapter ma = MainActivity.instance.getMealAdapter();

        if( LIST == null ) {
            for (int i = 0; i < ma.getItemCount(); i++) {
                if (p.hasMeal(ma.get(i).getName())) {
                    meals.add(ma.get(i));
                }
            }
        } else {
            meals = LIST;
        }

        mca = new MealCheckAdapter(p,ma, new OnClickListener() {
            @Override
            public void onClick(Meal m, boolean selected) {
                if( selected ) {
                    if( meals.indexOf(m) == -1 ) {
                        meals.add(m);
                    }
                } else {
                    if( meals.indexOf(m) != -1 ) {
                        meals.remove(m);
                    }
                }
            }
        });
        mca.setMeals(meals);

        mealView.setAdapter(mca);
        mealView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LIST = meals;
    }

    public interface OnClickListener {
        public void onClick(Meal m, boolean selected);
    }
}
