package com.mobilesutra.feetrackerclass.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/*import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;*/
import com.google.android.gms.common.api.GoogleApiClient;
import com.mobilesutra.feetrackerclass.R;
import com.mobilesutra.feetrackerclass.model.DTOfaq;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rasika on 5/21/2016.
 */
public class faq extends AppCompatActivity {


    RecyclerAdapter recyclerAdapter = null;
    Context context = null;
    LinearLayoutManager linearLayoutManager = null;
    RecyclerView recycler_view = null;
    private static final String LOG_TAG = "ActivityFaq";
    ArrayList<DTOfaq> rowItems = new ArrayList<>();
    String arr_questions[] = new String[]{"1. How to start using the App ?",
            "2. I have dance class . The timing for the class is from 5 to 9 PM.  I do not know what to enter in standard and batch.",
            "3. I have 400 students in my class. Do I need to enter all 400 students in Mobile ?",
            "4. Can I upload fees details using excel file ?",
            "5. Can I delete the records when the coaching is complete ?",
            "6. Is my data secure ?",
            "7. How to pay for licence copy of the App ?" ,
            "8. What is contact information for support staff ?"};
    String arr_answers[] = new String[]{"Ans : First go to class profile and enter all branches , all standards or levels  , all batches and all subjects or styles.Then go to \"Define Fees\" page and enter fees for each subject. Then add students in Student Management page.",
            "Ans : You can enter the level (intermediate or advance etc) . You can also enter NA (Not applicable) . But some entry is must.",
            "Ans : You can upload the excel file from server. Using restore feature , all students records can be bought in the App. Contact sbsupport@mobilesutra.com for further help.",
            "Ans : Yes. Contact sbsupport@mobilesutra.com for further help.",
            "Ans : Yes. Go to Define Fee section in class profile page and delete the the corresponding batch. This will delete all students and their fees and attendance records ." ,
            "Ans : All the records are kept in encrypted format on our server. We do not make use of your data for any purpose except to resolve technical issues you face.",
            "Ans : Check the email you received at the time of registration for pricing details. Contact sbsupport@mobilesutra.com for different payment options.",
            "Ans : Contact 9890211198"};
    Typeface roboto_light, roboto_reguler;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private ImageView menuList,menu_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        context = this;
        initComponents();
        initiComponentListners();
        bindComponentData();

    }

    private void initComponents() {

        TextView heading = (TextView) findViewById(R.id.txt_user_heading);
        heading.setText("FAQ");

        menuList = (ImageView)findViewById(R.id.menuList);
        menuList.setVisibility(View.INVISIBLE);
        menu_add = (ImageView)findViewById(R.id.menu_add);
        menu_add.setVisibility(View.INVISIBLE);
        heading.setTypeface(roboto_reguler);

        for(int i=0; i< arr_questions.length ; i++)
        {
            DTOfaq faq = new DTOfaq(arr_questions[i],arr_answers[i]);
            rowItems.add(faq);
        }

        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        linearLayoutManager = new LinearLayoutManager(context);
        recycler_view.setLayoutManager(linearLayoutManager);
        recycler_view.setHasFixedSize(true);

    }

    private void initiComponentListners() {

    }

    private void bindComponentData() {

      //  List rowItems = RVFApp.db.get_notice_list();

        if (recyclerAdapter == null) {
            recyclerAdapter = new RecyclerAdapter(rowItems);
            recycler_view.setAdapter(recyclerAdapter);
        } else {

            recyclerAdapter.rowItems.clear();
            recyclerAdapter.rowItems.addAll(rowItems);
            recyclerAdapter.notifyDataSetChanged();
        }
    }




    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


        List<DTOfaq> rowItems = null;

        public RecyclerAdapter(List<DTOfaq> rowItems) {
            this.rowItems = rowItems;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            RecyclerView.ViewHolder vh;
            final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row_faq, viewGroup, false);
            vh = new MyViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   /* Intent intent = new Intent();
                    String SCHEME = "package";
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts(SCHEME, "com.mobilesutra.rvf", null);
                    intent.setData(uri);
                    startActivity(intent);*/
                }
            });
            return vh;
        }


        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
            DTOfaq item = rowItems.get(position);
            ((MyViewHolder) holder).txt_quetions.setText(item.getStr_question());
            ((MyViewHolder) holder).txt_answer.setText(item.getStr_answer());

        }

        @Override
        public int getItemCount() {
            return rowItems == null ? 0 : rowItems.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView txt_quetions, txt_answer;
            CardView cardView = null;

            public MyViewHolder(View v) {
                super(v);
                txt_quetions = (TextView) v.findViewById(R.id.txt_quetion);
                txt_answer = (TextView) v.findViewById(R.id.txt_answer);

                cardView = (CardView) v.findViewById(R.id.cardView);
            }
        }
    }

    @Override
    public void onBackPressed() {

        Intent i = new Intent(faq.this,ActivityDashboard.class);
        startActivity(i);
        finish();
    }
}
