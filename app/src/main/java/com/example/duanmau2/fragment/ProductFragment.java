package com.example.duanmau2.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau2.Adapter.ProductAdapter;
import com.example.duanmau2.DAO.SanPhamDAO;
import com.example.duanmau2.Model.Product;
import com.example.duanmau2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class ProductFragment extends Fragment {

    private RecyclerView recyclerProduct;
    private FloatingActionButton floatAdd;

    private SanPhamDAO sanPhamDAO;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);

        recyclerProduct = view.findViewById(R.id.recyclerProduct);
        floatAdd = view.findViewById(R.id.floatAdd);

        sanPhamDAO = new SanPhamDAO(getContext());
        loadData();

        floatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogAdd();
            }
        });

        return view;
    }

    private void loadData(){
        ArrayList<Product> list = sanPhamDAO.getDS();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerProduct.setLayoutManager(linearLayoutManager);
        ProductAdapter adapter = new ProductAdapter(getContext(), list, sanPhamDAO);
        recyclerProduct.setAdapter(adapter);
    }

    private void showDialogAdd(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Xử lí chức năng

        EditText edtTenSP = view.findViewById(R.id.edtTenSP);
        EditText edtGiaSP = view.findViewById(R.id.edtGiaSP);
        EditText edtSL = view.findViewById(R.id.edtSL);
        Button btnThemSP = view.findViewById(R.id.btnThemSP);
        Button btnHuy = view.findViewById(R.id.btnHuy);

        btnThemSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tensp = edtTenSP.getText().toString();
                String giasp = edtGiaSP.getText().toString();
                String sl = edtSL.getText().toString();

                if(tensp.length() == 0 || giasp.length() == 0 || sl.length() == 0){
                    Toast.makeText(getContext(), "Hãy nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    Product product = new Product(tensp, Integer.parseInt(giasp), Integer.parseInt(sl));
                    boolean check = sanPhamDAO.themSPMoi(product);
                    if (check){
                        Toast.makeText(getContext(), "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                        loadData();
                        alertDialog.dismiss();
                    }else{
                        Toast.makeText(getContext(), "Thêm sản phẩm thất bại", Toast.LENGTH_SHORT).show();
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
}
