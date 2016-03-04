package net.jnm.selina.billsplitter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.util.ArrayList;

/**
 * Created by ryana on 3/4/2016.
 */
public class PersonCheckAdapter extends RecyclerView.Adapter<PersonCheckAdapter.PersonCheckHolder> {
    private PersonAdapter pa;
    private boolean[] status;
    private EditMealActivity.OnClickListener listener;

    public PersonCheckAdapter(Meal m, PersonAdapter pa, EditMealActivity.OnClickListener listener) {
        Person[] people = MainActivity.instance.getPersonsFor(m);
        ArrayList<Person> persons = new ArrayList<Person>();
        for( Person p: people) {
            persons.add(p);
        }
        this.pa = pa;
        this.listener = listener;
        status = new boolean[pa.getItemCount()];
        for(int i = 0; i < pa.getItemCount(); i++ ) {
            status[i] = pa.get(i).hasMeal(m.getName());
        }
    }

    @Override
    public PersonCheckHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_check,null);

        return new PersonCheckHolder(v);
    }

    @Override
    public void onBindViewHolder(PersonCheckHolder holder, int position) {
        holder.setPerson(pa.get(position),status[position]);
    }

    @Override
    public int getItemCount() {
        return pa.getItemCount();
    }

    public class PersonCheckHolder extends RecyclerView.ViewHolder {
        private Person p;
        private CheckBox personCheckBox;

        public PersonCheckHolder(View itemView) {
            super(itemView);
            personCheckBox = (CheckBox)itemView.findViewById(R.id.mealCheckBox);
            personCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(p,personCheckBox.isChecked());
                }
            });
        }

        public void setPerson(Person p, boolean selected) {
            this.p = p;
            personCheckBox.setText(p.getName());
            personCheckBox.setChecked(selected);
        }
    }
}
