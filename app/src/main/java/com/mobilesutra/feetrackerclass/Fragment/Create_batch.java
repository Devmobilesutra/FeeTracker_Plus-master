package com.mobilesutra.feetrackerclass.Fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobilesutra.feetrackerclass.MyApplication;
import com.mobilesutra.feetrackerclass.R;
import com.mobilesutra.feetrackerclass.model.SubjectModel;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Create_batch.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Create_batch#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Create_batch extends Fragment {
    ListView listView = null;
    Context context = null;
    static String str = "", key_str = "key_str";
    Typeface roboto_light, roboto_reguler;

    List<SubjectModel> arrayDTO = null;
    RecyclerView recycler_view = null;
    RecyclerAdapter recyclerAdapter = null;
    ArrayList<HashMap<String, String>> subs = new ArrayList<HashMap<String, String>>();
    ArrayList<SubjectModel> rowItems1 = new ArrayList<SubjectModel>();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Create_batch.
     */
    // TODO: Rename and change types and number of parameters
    public static Create_batch newInstance(String param1, String param2) {
        Create_batch fragment = new Create_batch();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putCharSequence(key_str, param1);
        str = param1;
        fragment.setArguments(args);
        Log.d("tag", "--" + args);
        Log.d("tag", "--" + param1);
        return fragment;
    }

    public Create_batch() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        Log.d("tag3", "oncreate");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);


        }

        if (bundle != null) {
            str = bundle.getCharSequence(key_str).toString();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = container.getContext();
        Log.d("tag3", "onCreateView");
        Bundle bundle = getArguments();
        if (bundle != null) {
            str = bundle.getCharSequence(key_str).toString();
        }
        roboto_light = Typeface.createFromAsset(context.getAssets(), "fonts/robotocondensed-light.ttf");
        roboto_reguler = Typeface.createFromAsset(context.getAssets(), "fonts/robrtocondensed-regular.ttf");

        View view = inflater.inflate(R.layout.fragment_create_batch, container, false);

//        listView = (ListView) view.findViewById(R.id.listview);
        recycler_view = (RecyclerView) view.findViewById(R.id.listview);
        arrayDTO = new ArrayList<SubjectModel>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recycler_view.setLayoutManager(linearLayoutManager);
        recycler_view.setHasFixedSize(true);
        refreshData();
        bindComponentData();

        return view;
    }

    public void bindComponentData() {
        subs = MyApplication.dbo.getSubDate1(MyApplication.get_session("classid"));
        Log.i("tag2", "Audioname 1" + subs);
        rowItems1 = new ArrayList<SubjectModel>();

        int records = subs.size();
        if (subs.size() == 0) {
//            Toast.makeText(context,"Empty data",Toast.LENGTH_SHORT).show();
        } else {
            for (int i = 0; i < records; i++) {

                SubjectModel item = new SubjectModel(subs.get(i).get("BId"), subs.get(i).get("Rid"), subs.get(i).get("flag"), subs.get(i).get("SId"), subs.get(i).get("SuId"), subs.get(i).get("BaId"));//f.toString()

                rowItems1.add(item);
            }

            recyclerAdapter = new RecyclerAdapter(rowItems1);
            recycler_view.setAdapter(recyclerAdapter);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void refreshData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            str = bundle.getCharSequence(key_str).toString();
        }
        bindComponentData();
        Log.d("tag2", "refreshData called->" + str);
//        Toast.makeText(context,"RefreshData->"+str,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("tag3", "onDetach");
        mListener = null;
    }

    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


        List<SubjectModel> rowItems;


        public RecyclerAdapter(List<SubjectModel> rowItems) {
            this.rowItems = rowItems;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            RecyclerView.ViewHolder vh;
            final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.add_list_define_class, viewGroup, false);
            vh = new MyViewHolder(view);

            return vh;
        }


        public void Remove(int pos) {
            rowItems.remove(pos);
            notifyItemRemoved(pos);
            notifyItemRangeChanged(0, rowItems.size());
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

            SubjectModel row_pos = rowItems.get(position);
            String branch2 = MyApplication.dbo.getBranchName(row_pos.getBranch());
            String stand2 = MyApplication.dbo.getStandardName(row_pos.getStand());
            String batch2 = MyApplication.dbo.getbatchhhName(row_pos.getBatch());
            String subj2 = MyApplication.dbo.getSubjjjname(row_pos.getSubjid());


            final String flag = row_pos.getFlag();

            if (flag.equals("N")) {
                ((MyViewHolder) holder).rel_main.setBackgroundResource(R.color.update_text_color);
                ((MyViewHolder) holder).active_word.setText("INACTIVE");


            } else {
                ((MyViewHolder) holder).active_word.setText("ACTIVE");
                ((MyViewHolder) holder).rel_main.setBackgroundResource(R.color.white);
            }

            ((MyViewHolder) holder).branch.setText(branch2);
            ((MyViewHolder) holder).stand.setText(stand2);
            ((MyViewHolder) holder).batch.setText(batch2);
            ((MyViewHolder) holder).subj.setText(subj2);
            ((MyViewHolder) holder).autoid.setText(row_pos.getRid());

            ((MyViewHolder) holder).active.setTag(position);
            ((MyViewHolder) holder).inactive.setTag(position);

            ((MyViewHolder) holder).active.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyApplication.dbo.updateFlagActive(((MyViewHolder) holder).autoid.getText().toString());

                    int pos = (int) ((ImageView) v).getTag();
                    SubjectModel row_pos = rowItems.get(pos);
                    row_pos.setFlag("Y");
                    notifyItemChanged(pos);
                    //   Toast.makeText(context, "Batch is active", Toast.LENGTH_SHORT).show();
                    // bindComponentData();


                }
            });
            ((MyViewHolder) holder).inactive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) ((ImageView) v).getTag();
                    SubjectModel row_pos = rowItems.get(pos);
                    Boolean checkdata = MyApplication.dbo.checkStudent(MyApplication.get_session("classid"), row_pos.getBranch(), row_pos.getStand(), row_pos.getBatch(), row_pos.getSubjid());
//                    Toast.makeText(context,"7"+checkdata,Toast.LENGTH_SHORT).show();
                    if (!checkdata) {
                        row_pos.setFlag("N");
                        notifyItemChanged(pos);
                        MyApplication.dbo.updateFlagInActive(((MyViewHolder) holder).autoid.getText().toString());
                        // Toast.makeText(context, "Batch is Inactive", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, getResources().getString(R.string.error_inactive), Toast.LENGTH_SHORT).show();
                    }
//
                    // bindComponentData();

                }
            });


        }

        @Override
        public int getItemCount() {
            return rowItems == null ? 0 : rowItems.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView branch;
            TextView active_word;
            TextView batch;
            TextView subj;
            TextView stand;
            TextView autoid;
            ImageView active;
            ImageView inactive;
            RelativeLayout rel_main;

            TextView batch_txt, branch_txt, stand_txt, subj_txt;


            public MyViewHolder(View convertView) {
                super(convertView);

                branch = (TextView) convertView.findViewById(R.id.edit_branch_name);
                stand = (TextView) convertView.findViewById(R.id.edit_Stand_name);
                batch = (TextView) convertView.findViewById(R.id.edit_batch_name);

                subj = (TextView) convertView.findViewById(R.id.edit_Sub_name);
                active_word = (TextView) convertView.findViewById(R.id.active_word);
                autoid = (TextView) convertView.findViewById(R.id.auto_id);
                active = (ImageView) convertView.findViewById(R.id.active);
                inactive = (ImageView) convertView.findViewById(R.id.inactive);
                rel_main = (RelativeLayout) convertView.findViewById(R.id.rel_main);

                branch_txt = (TextView) convertView.findViewById(R.id.text_branch_name);
                stand_txt = (TextView) convertView.findViewById(R.id.text_Stand_name);
                batch_txt = (TextView) convertView.findViewById(R.id.text_batch_name);

                subj_txt = (TextView) convertView.findViewById(R.id.text_subj_name);

                branch.setTypeface(roboto_reguler);
                stand.setTypeface(roboto_reguler);
                batch.setTypeface(roboto_reguler);
                subj.setTypeface(roboto_reguler);

                branch_txt.setTypeface(roboto_reguler);
                batch_txt.setTypeface(roboto_reguler);


            }


        }


    }

    public class ListviewAdapter extends BaseAdapter implements View.OnClickListener {
        public ArrayList<HashMap<String, String>> list;
        Activity activity;

        public ListviewAdapter(Activity activity, ArrayList<HashMap<String, String>> list) {
            super();
            this.activity = activity;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public void onClick(View view) {

        }

        private class ViewHolder {


            TextView branch;
            TextView active_word;
            TextView batch;
            TextView subj;
            TextView stand;
            TextView autoid;
            ImageView active;
            ImageView inactive;
            RelativeLayout rel_main;

            TextView batch_txt, branch_txt, stand_txt, subj_txt;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final ViewHolder viewHolder;
            LayoutInflater inflater = activity.getLayoutInflater();
            convertView = null;

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.add_list_define_class, null);
                viewHolder = new ViewHolder();
                viewHolder.branch = (TextView) convertView.findViewById(R.id.edit_branch_name);
                viewHolder.stand = (TextView) convertView.findViewById(R.id.edit_Stand_name);
                viewHolder.batch = (TextView) convertView.findViewById(R.id.edit_batch_name);

                viewHolder.subj = (TextView) convertView.findViewById(R.id.edit_Sub_name);
                viewHolder.active_word = (TextView) convertView.findViewById(R.id.active_word);
                viewHolder.autoid = (TextView) convertView.findViewById(R.id.auto_id);
                viewHolder.active = (ImageView) convertView.findViewById(R.id.active);
                viewHolder.inactive = (ImageView) convertView.findViewById(R.id.inactive);
                viewHolder.rel_main = (RelativeLayout) convertView.findViewById(R.id.rel_main);

                viewHolder.branch_txt = (TextView) convertView.findViewById(R.id.text_branch_name);
                viewHolder.stand_txt = (TextView) convertView.findViewById(R.id.text_Stand_name);
                viewHolder.batch_txt = (TextView) convertView.findViewById(R.id.text_batch_name);

                viewHolder.subj_txt = (TextView) convertView.findViewById(R.id.text_subj_name);

                viewHolder.branch.setTypeface(roboto_reguler);
                viewHolder.stand.setTypeface(roboto_reguler);
                viewHolder.batch.setTypeface(roboto_reguler);
                viewHolder.subj.setTypeface(roboto_reguler);

                viewHolder.branch_txt.setTypeface(roboto_reguler);
                viewHolder.stand_txt.setTypeface(roboto_reguler);
                viewHolder.batch_txt.setTypeface(roboto_reguler);
                viewHolder.subj_txt.setTypeface(roboto_reguler);
                viewHolder.active_word.setTypeface(roboto_reguler);

                convertView.setTag(viewHolder);


                //ic_btn_speak_now


                HashMap<String, String> map = list.get(position);
                Log.d("tag", "map-value" + map.size());
                String b = MyApplication.dbo.getBranchName(map.get("BId"));
                String s = MyApplication.dbo.getStandardName(map.get("SId"));
                String bt = MyApplication.dbo.getbatchhhName(map.get("BaId"));
                String su = MyApplication.dbo.getSubjjjname(map.get("SuId"));
                final String flag = map.get("flag");

                if (flag.equals("N")) {
                    viewHolder.rel_main.setBackgroundResource(R.color.update_text_color);
                    viewHolder.active_word.setText("INACTIVE");


                } else
                    viewHolder.active_word.setText("ACTIVE");
                viewHolder.branch.setText(b.toString());
                viewHolder.stand.setText(s.toString());
                viewHolder.batch.setText(bt.toString());
                viewHolder.subj.setText(su.toString());
                viewHolder.autoid.setText(map.get("Rid"));

                viewHolder.active.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyApplication.dbo.updateFlagActive(viewHolder.autoid.getText().toString());
                        Toast.makeText(activity, "", Toast.LENGTH_SHORT).show();
                        bindComponentData();


                    }
                });
                viewHolder.inactive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyApplication.dbo.updateFlagInActive(viewHolder.autoid.getText().toString());
                        Toast.makeText(activity, "Batch is Inactive", Toast.LENGTH_SHORT).show();
                        bindComponentData();

                    }
                });

            } else {
                viewHolder = (ViewHolder) convertView.getTag();

            }
            return convertView;

        }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
