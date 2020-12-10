package com.example.myapplication.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.model.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHoler> {

    String url = "https://5fd227ffb485ea0016eef8c6.mockapi.io/api/v1/user";
    private List<User> userList;
    private Activity activity;

    public UserAdapter(List<User> userList, Activity activity) {
        this.userList = userList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public UserViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item,parent,false);
        return new UserViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHoler holder, int position) {
        User user = userList.get(position);
        holder.textView.setText(user.getLastName());
        holder.textView2.setText(user.getFirstName());
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteApi(url,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class UserViewHoler extends RecyclerView.ViewHolder{
        private TextView textView;
        private TextView textView2;
        private ImageButton imageButton;

        public UserViewHoler(@NonNull View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.tvItemStudentId);
            textView2 = itemView.findViewById(R.id.tvItemStudentFullname);
            imageButton = itemView.findViewById(R.id.btnItemStudentDelete);


        }
    }


    private void DeleteApi(String url,int pos){
        StringRequest stringRequest = new StringRequest(
                Request.Method.DELETE,
                url + '/' + String.valueOf(pos+1),
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(activity, "Successfully", Toast.LENGTH_SHORT).show();
                userList.remove(pos);
                notifyDataSetChanged();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, "Error by Post data!", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
    }
}
