package com.example.projectakhir_diandra.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectakhir_diandra.ListMasyarakatActivity;
import com.example.projectakhir_diandra.R;
import com.example.projectakhir_diandra.db.DbHelper;
import com.example.projectakhir_diandra.model.Masyarakat;
import com.example.projectakhir_diandra.model.UpdateActivity;

import java.io.Serializable;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MasyarakatAdapter extends
        RecyclerView.Adapter<MasyarakatAdapter.MasyarakatViewHolder>{
    private ArrayList<Masyarakat> listMasyarakat = new ArrayList<>();
    private Activity activity;
    private DbHelper dbHelper;
    public MasyarakatAdapter(Activity activity) {
        this.activity = activity;
        dbHelper = new DbHelper(activity);
    }

    public void setListMasyarakat(ArrayList<Masyarakat> masyarakatArrayList) {
    }

    public class MasyarakatViewHolder extends RecyclerView.ViewHolder {
        final TextView txtNIK, txtNama; //inisialisasi dari textview pada NIK dan nama
        final Button btnUbah, btnHapus; //inisialisasi dari tombol ubah dan hapus
        public MasyarakatViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNIK = itemView.findViewById(R.id.txt_item_nik); //berdasarkan pada file item_list
            txtNama = itemView.findViewById(R.id.txt_item_nama);//berdasarkan pada file item_list
            btnUbah = itemView.findViewById(R.id.btn_ubah); //berdasarkan pada file item_list
            btnHapus = itemView.findViewById(R.id.btn_hapus); //berdasarkan pada file item_list
        }
    }
    public ArrayList<Masyarakat> getListStudents() {
        return listMasyarakat;
    }
    public void setListStudents(ArrayList<Masyarakat> listNotes) {
        if (listNotes.size() > 0) {
            this.listMasyarakat.clear();
        }
        this.listMasyarakat.addAll(listNotes);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MasyarakatViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                   int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_masyarakat,
                        parent, false);
        return new MasyarakatViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MasyarakatViewHolder holder,
                                 @SuppressLint("RecyclerView") int position) {
        holder.txtNIK.setText(listMasyarakat.get(position).getNik());
        holder.txtNama.setText(listMasyarakat.get(position).getNama());
        holder.btnUbah.setOnClickListener((View v) -> {
            Intent intent = new Intent(activity, UpdateActivity.class);
            intent.putExtra("data", (Serializable)
                    listMasyarakat.get(position));
            activity.startActivity(intent);
        });
        holder.btnHapus.setOnClickListener((View v) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle("Konfirmasi hapus");
            builder.setMessage("Apakah yakin akan dihapus?");
            builder.setPositiveButton("YA", new
                    DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dbHelper.deleteUser(listMasyarakat.get(position).getId());
                            Toast.makeText(activity, "Hapus berhasil!",
                                    Toast.LENGTH_SHORT).show();
                            Intent myIntent = new Intent(activity,
                                    ListMasyarakatActivity.class);
                            activity.startActivity(myIntent);
                            activity.finish();
                        }
                    });
            builder.setNegativeButton("TIDAK", new
                    DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        });
    }
    @Override
    public int getItemCount() {
        return listMasyarakat.size();
    }
}