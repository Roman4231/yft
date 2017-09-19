package com.yft.admin.myapplication.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.yft.admin.myapplication.CreateCustomTraining;
import com.yft.admin.myapplication.MainActivity;
import com.yft.admin.myapplication.R;
import com.yft.admin.myapplication.TrainingDetails;
import com.yft.admin.myapplication.classes.TrainingClass;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class ChooseTraining extends android.support.v4.app.Fragment implements BillingProcessor.IBillingHandler{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<String> listItems;
    View v;
    ListView myList;
    SharedPreferences sPref;
    Button imLeft;
    Button imRight;
    int isDumbbellON;
    private OnFragmentInteractionListener mListener;
    boolean isPurchased;
    public BillingProcessor bp;

    public ChooseTraining() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentChooseTraining.
     */
    // TODO: Rename and change types and number of parameters
    public static ChooseTraining newInstance(String param1, String param2) {
        ChooseTraining fragment = new ChooseTraining();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bp = new BillingProcessor(this.getActivity(), "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsUb2mFKKNxEQMY82jKePiGXnbheZEu1MJBQhBANIAWq01XT6NYuuU0C3DCiq45GIu2lI/0ERz2P4KmLsYZCnBorDozWBW191KguDxi/NFv6OI1muQlRv+RIJslO1qu1nVCIuabtRWKxhiEQeo45P0Ib9U0pWN2V5YUDSakL6mUAMCjdDQVmo5A2vKUXLJr1t5jnGYYm7PylODe82fDDAFHktwbEGkhQ7YZm5/C2FYyB5BxhQ7K6KnuJC3tfay1pUh+lsbEBPQ91GShPkpwzfANZEe6FMetN8gnLNpmf3ZcDWhW0l7oGjSg3j/ic2xxzBY+ULqXNx1hDRuxIRnKCsQwIDAQAB", this);
        sPref=getActivity().getSharedPreferences("MyDateBase",getActivity().MODE_PRIVATE);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        sPref=getActivity().getSharedPreferences("MyDateBase",getActivity().MODE_PRIVATE);
        isPurchased=bp.isPurchased("create_custom_training");
       // checkPurchase();
        v=inflater.inflate(R.layout.fragment_choose_training,null);
        isDumbbellON=0;
        imLeft=(Button) v.findViewById(R.id.choose_training_imageViewLeft);
        imRight=(Button) v.findViewById(R.id.choose_training_imageViewRight);
        imLeft.setOnClickListener(myOnClickListener);
        imRight.setOnClickListener(myOnClickListener);
        return v;
    }
/*
    void checkPurchase(){
        SharedPreferences.Editor editor=sPref.edit();
        editor.putBoolean("isPurchased",isPurchased);
        editor.commit();
    }*/

    View.OnClickListener myOnClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.choose_training_imageViewLeft:
                    isDumbbellON=1;
                    imLeft.setBackgroundResource(R.drawable.ic_choose_training_slice_left_dark);
                    imRight.setBackgroundResource(R.drawable.ic_choose_training_slice_right_light);
                    isCheckCorrect();
                    Toast.makeText(getActivity(), "Dumbbell On", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.choose_training_imageViewRight:
                    isDumbbellON=0;
                    isCheckCorrect();
                    imLeft.setBackgroundResource(R.drawable.ic_choose_training_slice_left_light);
                    imRight.setBackgroundResource(R.drawable.ic_choose_training_slice_right_dark);
                    Toast.makeText(getActivity(), "Dumbbell Off", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    void isCheckCorrect(){
        int q=sPref.getInt("ChoosedTraining",0);
        if(q>2){
            return;
        }
        int n=sPref.getInt("IsDumbbellOn",-1);
        if(((InteractiveArrayAdapter)myList.getAdapter()).checked!=null) {
            if (n != isDumbbellON) {

                ((InteractiveArrayAdapter) myList.getAdapter()).checked
                        .setImageResource(R.drawable.ic_radiobutton_unchecked);
            } else {
                ((InteractiveArrayAdapter) myList.getAdapter()).checked
                        .setImageResource(R.drawable.ic_radiobutton_checked);
            }
        }
    }

    public void updateList(){
        int n=0;
        n=sPref.getInt("numberOfCustomTrainings",0);
        listItems=new ArrayList<String>();
        listItems.add("Top Training");
        listItems.add("Full Body");
        listItems.add("Bottom Training");


        for(int i=0;i<n;i++){
            listItems.add(TrainingClass.getTrainingFromJson(sPref.getString("Training"+i," ")).name);
        }
        listItems.add("Custom Training");

        myList=(ListView)v.findViewById(R.id.chooseTrainingLV);

        myList.setAdapter(new InteractiveArrayAdapter(getActivity(), listItems));
    }

    @Override
    public void onResume(){
        super.onResume();
        updateList();
    }
    // TODO: Rename method, update argument and hook method into UI event


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
/*        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

//////adapter

    public class InteractiveArrayAdapter extends ArrayAdapter<String> {
        private final Activity context;
        public ImageButton checked;
        private final ArrayList<String> names;
        private SharedPreferences sPref;
        private ViewGroup parent;
        View rowView;
        ViewHolder holder;
        public InteractiveArrayAdapter(Activity context, ArrayList<String> names) {
            super(context, R.layout.rowbuttonlayout, names);
            this.context = context;
            this.names = names;
        }

        // Класс для сохранения во внешний класс и для ограничения доступа
// из потомков класса
        class ViewHolder {
            public Button cancelBtn;
            public ImageButton myRb;
            public ImageButton imBtn;
            public TextView textView;
        }

        @Override
        public View getView(final int position,final View convertView,final ViewGroup parent) {
// ViewHolder буферизирует оценку различных полей шаблона элемента\



            this.parent=parent;
// Очищает сущетсвующий шаблон, если параметр задан
// Работает только если базовый шаблон для всех классов один и тот же
            rowView = convertView;

            if (rowView == null) {
                newView(position);
            } else {
                holder = (ViewHolder) rowView.getTag();
                if(holder.textView.getText().toString()!=names.get(position)){
                    newView(position);
                }
            }

            SharedPreferences sPref = context.getSharedPreferences("MyDateBase",MODE_PRIVATE);
            if(position!=(names.size()-1)) {
                if (sPref.getInt("ChoosedTraining", -1) != position||sPref.getInt("IsDumbbellOn", -1)!=isDumbbellON) {
                    holder.myRb.setImageResource(R.drawable.ic_radiobutton_unchecked);
                } else {
                    holder.myRb.setImageResource(R.drawable.ic_radiobutton_checked);

                    if (((ImageButton) rowView.findViewById(R.id.chTrMyRB)) != checked) {
                        checked = (ImageButton) rowView.findViewById(R.id.chTrMyRB);
                    }
                }
            }
            holder.textView.setText(names.get(position));

            return rowView;
        }

        private void delete(String name){
            int pos=-1;
            pos=listItems.indexOf(name)-3;
            listItems.remove(name);
            if(pos==-1)return;
            sPref=context.getSharedPreferences("MyDateBase",MODE_PRIVATE);
            int n=sPref.getInt("numberOfCustomTrainings",0);
            SharedPreferences.Editor editor=sPref.edit();
            for(int i=pos;i<n;i++){
                editor.putString("Training"+i,sPref.getString("Training"+(i+1),""));
            }
            editor.putInt("numberOfCustomTrainings",n-1);

            if(pos==sPref.getInt("ChoosedTraining",0)){
                editor.putInt("ChoosedTraining",0);
                editor.putInt("ChosedLevel",1);
            }else{
                if(pos<sPref.getInt("ChoosedTraining",0)){
                    editor.putInt("ChoosedTraining",sPref.getInt("ChoosedTraining",1)-1);
                }
            }
            editor.commit();



            InteractiveArrayAdapter.this.notifyDataSetChanged();
        }

        private void newView(final int position){
            holder =new InteractiveArrayAdapter.ViewHolder();
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.rowbuttonlayout, null, true);
            switch (position){
                case 0:
                    ((LinearLayout)rowView.findViewById(R.id.chTrItemLayout)).setBackgroundResource(R.drawable.choose_training_top);
                    break;
                case 1:
                    ((LinearLayout)rowView.findViewById(R.id.chTrItemLayout)).setBackgroundResource(R.drawable.choose_training_full);
                    break;
                case 2:
                    ((LinearLayout)rowView.findViewById(R.id.chTrItemLayout)).setBackgroundResource(R.drawable.choose_training_bottom);
                    break;
                default:
                    if(position==(names.size()-1)){
                        ((LinearLayout)rowView.findViewById(R.id.chTrItemLayout)).setBackgroundResource(R.drawable.choose_training_custom);
                    }else{
                        SharedPreferences sPref = context.getSharedPreferences("MyDateBase", MODE_PRIVATE);
                        int image=TrainingClass.getTrainingFromJson(sPref.getString("Training"+(position-3),"")).image;
                        ((LinearLayout)rowView.findViewById(R.id.chTrItemLayout)).setBackgroundResource(image);
                    }
            }
            holder.cancelBtn=(Button) rowView.findViewById(R.id.rowButtonLayoutCancel);
            holder.textView = (TextView) rowView.findViewById(R.id.chooseTrTextView);
            holder.myRb = (ImageButton) rowView.findViewById(R.id.chTrMyRB);
            holder.imBtn = (ImageButton) rowView.findViewById(R.id.chTrImageButton);
            sPref=getActivity().getSharedPreferences("MyDateBase",getActivity().MODE_PRIVATE);
            if(position==sPref.getInt("ChoosedTraining", -1))checked=holder.myRb;
            if(position!=(names.size()-1)) {
                holder.myRb.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        String name=((TextView)((LinearLayout)(v.getParent().getParent())).findViewById(R.id.chooseTrTextView)).getText().toString();
                        int pos=names.indexOf(name);
                        SharedPreferences sPref = context.getSharedPreferences("MyDateBase", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sPref.edit();
                        if (pos!= sPref.getInt("ChoosedTraining", -1) && checked != null) {
                            checked.setImageResource(R.drawable.ic_radiobutton_unchecked);
                            editor.putInt("ChosedLevel",1);
                        }
                        if(pos>2){
                            editor.putInt("ChosedLevel",-1);
                        }
                        editor.putInt("IsDumbbellOn",isDumbbellON);
                        editor.putInt("ChoosedTraining", pos);
                        editor.commit();
                        holder.myRb.setImageResource(R.drawable.ic_radiobutton_checked);
                        checked = holder.myRb;
                        InteractiveArrayAdapter.this.notifyDataSetChanged();
                    }
                });
                if(position>2&&position!=(names.size()-1)){
                    holder.cancelBtn.setVisibility(View.VISIBLE);
                }
                holder.cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delete(((TextView) ((RelativeLayout)v.getParent()).findViewById(R.id.chooseTrTextView)).getText().toString());
                    }
                });

                holder.imBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String info=position+""+isDumbbellON;
                        Intent intent = new Intent(context, TrainingDetails.class);
                        intent.putExtra("TrainingInfo", info);
                        context.startActivity(intent);
                    }
                });
            }else {
                int icon;
                if(isPurchased){
                    icon=R.drawable.ic_choose_training_plus;
                }else{
                    icon=R.drawable.ic_lock_outline_black;
                }
                holder.imBtn.setImageResource(icon);
                holder.myRb.setVisibility(View.GONE);
                holder.imBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            bp.purchase(ChooseTraining.this.getActivity(), "create_custom_training");
                    }
                });
            }
            rowView.setTag(holder);}
    }


    @Override
    public void onProductPurchased(String productId, TransactionDetails details) {
        Intent intent = new Intent(this.getActivity(), CreateCustomTraining.class);
        startActivity(intent);
    }

    @Override
    public void onPurchaseHistoryRestored() {
    }

    @Override
    public void onBillingError(int errorCode, Throwable error) {
    }

    @Override
    public void onBillingInitialized() {
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onDestroy() {
        if (bp != null) {
            bp.release();
        }
        super.onDestroy();
    }
}