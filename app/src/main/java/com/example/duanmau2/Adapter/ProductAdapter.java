package com.example.duanmau2.Adapter;

import static java.security.AccessController.getContext;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau2.DAO.SanPhamDAO;
import com.example.duanmau2.Model.Product;
import com.example.duanmau2.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Product> list;

    private SanPhamDAO sanPhamDAO;

    public ProductAdapter(Context context, ArrayList<Product> list, SanPhamDAO sanPhamDAO) {
        this.context = context;
        this.list = list;
        this.sanPhamDAO = sanPhamDAO;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtName.setText(list.get(position).getTensp());

        NumberFormat formatter = new DecimalFormat("#,###");
        double myNumber = list.get(position).getGiaban();
        String formattedNumber = formatter.format(myNumber);
        holder.txtPrice.setText(formattedNumber + " VND");

        holder.txtQuantity.setText("SL: " + String.valueOf(list.get(position).getSoluong()));

        //Chỉnh sửa
        holder.txtEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogEdit(list.get(holder.getAdapterPosition()));
            }
        });

        //Xóa sản phảm
        holder.txtDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogDel(list.get(holder.getAdapterPosition()).getTensp(), list.get(holder.getAdapterPosition()).getMasp());
            }
        });
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtName, txtPrice, txtQuantity, txtEdit, txtDel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName =itemView.findViewById(R.id.txtName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
            txtEdit  = itemView.findViewById(R.id.txtEdit);
            txtDel = itemView.findViewById(R.id.txtDel);
        }
    }

    private void showDialogEdit(Product product){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_edit, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        EditText edtTenSP = view.findViewById(R.id.edtTenSP);
        EditText edtGiaSP = view.findViewById(R.id.edtGiaSP);
        EditText edtSL = view.findViewById(R.id.edtSL);
        Button btnEditSP = view.findViewById(R.id.btnEditSP);
        Button btnHuy = view.findViewById(R.id.btnHuy);

        edtTenSP.setText(product.getTensp());
        edtGiaSP.setText(String.valueOf(product.getGiaban()));
        edtSL.setText(String.valueOf(product.getSoluong()));

        btnEditSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int masp = product.getMasp();
                String tensp = edtTenSP.getText().toString();
                String giasp = edtGiaSP.getText().toString();
                String sl = edtSL.getText().toString();

                if(tensp.length() == 0 || giasp.length() == 0 || sl.length() == 0){
                    Toast.makeText(context, "Hãy nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    Product productChinhSua = new Product(masp, tensp, Integer.parseInt(giasp), Integer.parseInt(sl));
                    boolean check = sanPhamDAO.ChinhSuaSP(productChinhSua);
                    if (check){
                        Toast.makeText(context, "Chỉnh sửa sản phẩm thành công", Toast.LENGTH_SHORT).show();
                        LoadData();
                        alertDialog.dismiss();
                    }else{
                        Toast.makeText(context, "Chỉnh sửa sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    private void showDialogDel (String tensp, int masp){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông Báo");
        builder.setMessage("Bạn có muốn xóa sản phảm \"" + tensp + "\" không?");
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                boolean check = sanPhamDAO.XoaSP(masp);
                if (check){
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    LoadData();
                }else {
                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });


        builder.setNegativeButton("Hủy", null);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void LoadData(){
        list.clear();
        list =sanPhamDAO.getDS();
        notifyDataSetChanged();
    }
}
