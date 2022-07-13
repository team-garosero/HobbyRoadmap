package com.garosero.android.hobbyroadmap.syllabus;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.garosero.android.hobbyroadmap.R;
import com.garosero.android.hobbyroadmap.data.ModuleClassItem;
import com.garosero.android.hobbyroadmap.data.TilItem;
import com.garosero.android.hobbyroadmap.main.helper.CastHelper;
import com.garosero.android.hobbyroadmap.main.til.TilItemFragment;
import com.garosero.android.hobbyroadmap.main.til.TilWriteActivity;
import com.garosero.android.hobbyroadmap.network.request.ApiRequest;
import com.garosero.android.hobbyroadmap.network.response.TilResponse;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SyllabusChildAdapter extends RecyclerView.Adapter<SyllabusChildAdapter.ViewHolder>{
    HashMap<String, ModuleClassItem> moduleClassMap;
    ArrayList<String> classCd;
    private String LClassID;
    private String MClassID;
    private String SClassID;
    private String subClassId;

    public SyllabusChildAdapter(ArrayList<String> classCd, HashMap<String, ModuleClassItem> moduleClassMap){
        this.classCd = classCd;
        try {
            this.LClassID = classCd.get(0);
            this.MClassID = classCd.get(1);
            this.SClassID = classCd.get(2);
            this.subClassId = classCd.get(3);

            this.moduleClassMap = moduleClassMap;

        } catch (Exception e){
            e.fillInStackTrace();
        }
    }

    @NonNull
    @Override
    public SyllabusChildAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyler_course_child, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SyllabusChildAdapter.ViewHolder holder, int position) {
        final int[] colorList = {R.color.roadmap_blue_1, R.color.roadmap_blue_2};

        holder.courseTitle.setText(this.moduleClassMap.get(String.valueOf(position)).getName());

        int i = (position % 6) < 3 ? 0 : 1;
        holder.color.setBackgroundTintList(holder.color.getResources().getColorStateList(colorList[i]));

        if(position == this.moduleClassMap.size()-1){ // 마지막 아이템이면 dashline invisioble
            holder.dashLine.setVisibility(View.INVISIBLE);
        }

        holder.recycler_cardView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                // go to til write
                Intent intent = new Intent(holder.itemView.getContext(), TilWriteActivity.class);
                intent.putExtra(TilWriteActivity.Companion.getLCLASSID(), LClassID);
                intent.putExtra(TilWriteActivity.Companion.getMCLASSID(), MClassID);
                intent.putExtra(TilWriteActivity.Companion.getSCLASSID(), SClassID);
                intent.putExtra(TilWriteActivity.Companion.getSUBCLASSID(), subClassId);
                intent.putExtra(TilWriteActivity.Companion.getMODULENAME(), moduleClassMap.get(String.valueOf(position)).getName());
                intent.putExtra(TilWriteActivity.Companion.getMODULEDESC(), moduleClassMap.get(String.valueOf(position)).getText()); //desc?
                intent.putExtra(TilWriteActivity.Companion.getMODULENUM(), moduleClassMap.get(String.valueOf(position)).getModuleNum());

                holder.itemView.getContext().startActivity(intent);
                Log.e("syllabusChildAdapter", "click card view");
            }
        });

        if(moduleNameSet.contains(this.moduleClassMap.get(String.valueOf(position)).getName())) {
            holder.status.setBackgroundResource(R.drawable.syll_check_box_taken);
        }

    }

    @Override
    public int getItemCount() {
        return this.moduleClassMap.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView color, status, dashLine; // status background = taken? iv_syllabus_taken : iv_syllabus_not_taken
        TextView courseTitle, xp;
        CardView recycler_cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            color = itemView.findViewById(R.id.iv_color);
            courseTitle = itemView.findViewById(R.id.tv_course_title);
            status = itemView.findViewById(R.id.iv_status_color);
            xp = itemView.findViewById(R.id.tv_xp);
            dashLine = itemView.findViewById(R.id.iv_dashline);
            recycler_cardView = itemView.findViewById(R.id.recyclerView_CardView);
        }
    }

    private ArrayList<String> moduleNameSet = new ArrayList<>();
    @SuppressLint("NotifyDataSetChanged")
    public void submitData(Map<String, TilResponse> tilItems){
        moduleNameSet.clear();
        String curerntUser = "";
        try{
           curerntUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        } catch(Exception e){
            Log.e("SyllChild","no currentuser");
            curerntUser = "M8mYC1eUs6RqEUrxTj7mARW3dK72";
        }
        for (String key : tilItems.keySet()){
            TilItem item = CastHelper.Companion.tilresponseToTilitem(Objects.requireNonNull(tilItems.get(key)));

            // filter
            if (item.getLClassId().equals(LClassID) && item.getMClassId().equals(MClassID) &&
                    item.getSClassId().equals(SClassID) && item.getSubClassId().equals(subClassId) &&
                    item.getUid().equals(curerntUser)) {
                moduleNameSet.add(item.getModuleName());
            } // end if

        }
        notifyDataSetChanged();
    }
}
