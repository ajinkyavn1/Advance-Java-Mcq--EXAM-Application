package com.gpj.InformationTechnology;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vikasojha.quizbee.R;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {

    private Context mCtx;
    private List<Product> productList;

    public ProductsAdapter(Context mCtx, List<Product> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductViewHolder(
                LayoutInflater.from(mCtx).inflate(R.layout.layout_product, parent, false)
        );
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);

        holder.textViewName.setText("Name-:"+product.getName());
        holder.textViewBrand.setText("Enrollment-: "+product.getEnrollment());
        holder.textViewDesc.setText("Subject-: " + product.getSubject());
        holder.cs.setText("Subject-: " + product.getcs());
        holder.textViewQty2.setText("Obtail marks-: "+product.getObtainMarks());
        holder.textViewQty.setText("Wrong: " + product.getWrong());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName, cs,textViewBrand, textViewDesc, textViewPrice, textViewQty,textViewQty2;

        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textview_name);
            textViewBrand = itemView.findViewById(R.id.textview_brand);
            textViewDesc = itemView.findViewById(R.id.textview_desc);
            textViewPrice = itemView.findViewById(R.id.cs);
            textViewQty = itemView.findViewById(R.id.textview_quantity);
            textViewQty2 = itemView.findViewById(R.id.textview_quantity2);


        }
    }
}