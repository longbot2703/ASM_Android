package nguyen.hoanglong;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nguyen.hoanglong.adapter.HomeAdapter;
import nguyen.hoanglong.model.Item;
import nguyen.hoanglong.network.APIManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    TextView tvIconPhrase,tvTemperature;
    RecyclerView recyclerView;
    List<Item> list;
    HomeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getListData();

        list = new ArrayList<>();
        adapter = new HomeAdapter(this,list);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);

        recyclerView = findViewById(R.id.rvTemperature);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void getListData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIManager.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIManager service = retrofit.create(APIManager.class);
        service.getListData().enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if(response.body() != null){
                    list = response.body();
                    adapter.reloadData(list);
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {

            }
        });
    }
}