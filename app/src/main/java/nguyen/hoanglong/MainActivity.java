package nguyen.hoanglong;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

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
    TextView tvIconPhrase,tvTemperature,tvTemperatureNow;
    RecyclerView recyclerView;
    List<Item> list;
    HomeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tvIconPhrase = findViewById(R.id.tvIconPhrase);
        tvTemperature = findViewById(R.id.tvTemperature);
        tvTemperatureNow = findViewById(R.id.tvTemperatureNow);

        getListData();

        list = new ArrayList<>();
        adapter = new HomeAdapter(MainActivity.this,list);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this,RecyclerView.HORIZONTAL,false);

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
                    Item fistdata = response.body().get(0);
                    tvTemperatureNow.setText(fistdata.getTemperature().getValue().toString() + " °");
                    tvIconPhrase.setText(fistdata.getIconPhrase());
                    adapter.reloadData(list);
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                tvTemperatureNow.setText("Lỗi");
            }
        });
    }
}