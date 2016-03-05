package net.jnm.selina.billsplitter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ryana on 3/4/2016.
 */
public class PersonCheckAdapter extends RecyclerView.Adapter<PersonCheckAdapter.PersonCheckHolder> {
    private PersonAdapter pa;
    private boolean[] status;
    private EditMealActivity.OnClickListener listener;

    public PersonCheckAdapter(PersonAdapter pa, EditMealActivity.OnClickListener listener) {
        this.pa = pa;
        this.listener = listener;
        status = new boolean[pa.getItemCount()];
    }

    public void setList(ArrayList<Person> list) {
        for(int i = 0; i < pa.getItemCount(); i++) {
            status[i] = false;
            for(Person p : list) {
                if( p == pa.get(i)) {
                    status[i] = true;
                    break;
                }
            }
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

        public PersonCheckHolder(final View itemView) {
            super(itemView);
            personCheckBox = (CheckBox)itemView.findViewById(R.id.mealCheckBox);
            ((TextView)itemView.findViewById(R.id.priceLabel)).setText("");
            personCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(p,personCheckBox.isChecked());
                    status[getAdapterPosition()] = personCheckBox.isChecked();
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
