package nguyen.hoanglong.network;

import java.util.List;

import nguyen.hoanglong.model.Item;
import retrofit2.Call;
import retrofit2.http.GET;

public interface APIManager {
    String KEY = "MJf5nn5adHwtggJHJkTROvfniAG6WEG5";
    String SERVER_URL = "http://dataservice.accuweather.com/forecasts/v1/hourly/12hour/";
    String URI = "353412?apikey=" +KEY+ "&language=vi-vn&metric=true";

    @GET(URI)
    Call<List<Item>> getListData();
}
