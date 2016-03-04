package net.jnm.selina.billsplitter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class ManageMealsActivity extends AppCompatActivity {
    public static ManageMealsActivity instance;
    private MealAdapter ma;

    private Button backButton;
    private Button addButton;
    private RecyclerView mealView;

    private Meal activeMeal;
    private PersonCheckAdapter pca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_meals);
        instance = this;

        ma = MainActivity.instance.getMealAdapter();
        ma.setOnClickListener(new OnClickListener() {
            @Override
            public void click(Meal m) {
                //send intent to EditMEalActivity
                Log.i("ManageMealsActivity", "Edit " + m.getName());
                activeMeal = m;
                Intent editMeal = new Intent(getBaseContext(),EditMealActivity.class);
                startActivity(editMeal);
            }
        });

        backButton = (Button)findViewById(R.id.backButton);
        addButton = (Button)findViewById(R.id.addButton);
        mealView = (RecyclerView)findViewById(R.id.mealView);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddMealDialog amd = new AddMealDialog();
                amd.show(getFragmentManager(), "");
            }
        });

        mealView.setAdapter(ma);
        mealView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        ma.updateGUI();
    }

    public Meal getActive() {
        return activeMeal;
    }

    public void deleteActive() {
        ma.removeMeal(activeMeal.getName());
        MainActivity.instance.clearMeal(activeMeal);
    }

    public void addMeal(String name, double unitPrice, int quantity) {
        ma.addMeal(name,unitPrice,quantity);
    }

    public interface OnClickListener {
        public void click(Meal m);
    }
}
