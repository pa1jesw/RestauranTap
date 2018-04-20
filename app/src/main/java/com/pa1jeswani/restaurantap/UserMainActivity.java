package com.pa1jeswani.restaurantap;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.pa1jeswani.restaurantap.adapters.RestaurantAdapter;
import com.pa1jeswani.restaurantap.model.Restaurant;
import com.stone.vega.library.VegaLayoutManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

//import io.fabric.sdk.android.services.common.SafeToast;

public class UserMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FloatingSearchView mSearchView;
    private RecyclerView mRestaurantRecyclerView;
    private RestaurantAdapter mAdapter;
    private ArrayList<Restaurant> mRestaurantCollection;
    double ulat,ulong;
    private Uri builtUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent iid = getIntent();
        ulong = iid.getDoubleExtra("ulong",0.0);
        ulat = iid.getDoubleExtra("ulat",0.0);
        //Toast.makeText(this, ""+ulong+"\n"+ulat, Toast.LENGTH_SHORT).show();
        mSearchView = findViewById(R.id.user_search_bar);

        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {

            }

            @Override
            public void onSearchAction(String currentQuery) {
                String query = currentQuery;
                //Toast.makeText(UserMainActivity.this, "q bfr"+query+ulat+ulong, Toast.LENGTH_SHORT).show();
                builtUri=Uri.parse("https://developers.zomato.com/api/v2.1/search?q="+query);
                mRestaurantCollection.clear();
                new FetchDataTask().execute();
                //findresults(query,ulat,ulong);

            }
        });

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mSearchView.attachNavigationDrawerToMenuButton(drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        init();

        new FetchDataTask().execute();

    }

    private void findresults(String query, double ulat, double ulong) {
    }

    private void init() {
        mRestaurantRecyclerView = (RecyclerView) findViewById(R.id.restaurant_recycler);
        mRestaurantRecyclerView.setLayoutManager(new VegaLayoutManager());
        //mRestaurantRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRestaurantRecyclerView.setHasFixedSize(true);
        mRestaurantCollection = new ArrayList<>();
        mAdapter = new RestaurantAdapter(mRestaurantCollection, this);
        mRestaurantRecyclerView.setAdapter(mAdapter);
        builtUri = Uri.parse("https://developers.zomato.com/api/v2.1/search?lat=" +ulat+
                "&lon=" +ulong+"&count=30");
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public class FetchDataTask extends AsyncTask<Void, Void, Void> {
        private String mZomatoString;
        @Override
        protected Void doInBackground(Void... voids) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            URL url;
            try{
                url = new URL(builtUri.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("user-key", "3efa586269dd61502418ca794c6c1d7a");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    //Nothing to do
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }

                mZomatoString = buffer.toString();
                JSONObject jsonObject = new JSONObject(mZomatoString);

                //Toast.makeText(UserMainActivity.this,
                  //      "Response Got", Toast.LENGTH_SHORT).show();

                JSONArray restaurantsArray = jsonObject.getJSONArray("restaurants"); //fetching rest jsonarray

                //list = new ArrayList<>();
                for (int i = 0; i < restaurantsArray.length(); i++) {

                    Log.v("BRAD_", i + "");
                    String name;
                    String address;
                    String currency;
                    String cuisine;
                    String imageUrl;
                    long lon;
                    long lat;
                    long cost;
                    float rating;

                    JSONObject jRestaurant = (JSONObject) restaurantsArray.get(i);
                    jRestaurant = jRestaurant.getJSONObject("restaurant");
                    JSONObject jLocattion = jRestaurant.getJSONObject("location");
                    JSONObject jRating = jRestaurant.getJSONObject("user_rating");


                    name = jRestaurant.getString("name");
                    address = jLocattion.getString("address");
                    lat = jLocattion.getLong("latitude");
                    lon = jLocattion.getLong("longitude");
                    currency = jRestaurant.getString("currency");
                    cuisine = jRestaurant.getString("cuisines");
                    cost = jRestaurant.getInt("average_cost_for_two");
                    imageUrl = jRestaurant.getString("featured_image");
                    rating = (float) jRating.getDouble("aggregate_rating");

                    Restaurant restaurant = new Restaurant();
                    restaurant.setName(name);
                    restaurant.setAddress(address);
                    restaurant.setLatitude(lat);
                    restaurant.setLongitude(lon);
                    restaurant.setCurrency(currency);
                    restaurant.setCost(String.valueOf(cost));
                    restaurant.setImageUri(imageUrl);
                    restaurant.setRating(String.valueOf(rating));
                    restaurant.setCuisine(cuisine);
                    mRestaurantCollection.add(restaurant);

                }//end of try

        } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {

                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                   //     Toast.makeText(UserMainActivity.this,
                     //           "Error closing stream"+e, Toast.LENGTH_LONG).show();
                        Log.e("err","not found");
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            mAdapter.notifyDataSetChanged();
        }
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_settings) {
            // Handle the settings action
            Toast.makeText(this,
                    "User RW restricted Error \n Firebase Server Issue/Down", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_logout) {

            Toast.makeText(this,
                    "Logging Out", Toast.LENGTH_SHORT).show();
            Intent i= new Intent(this,LoginAct.class);
            i.putExtra("type","user");
            startActivity(i);
        }
         else if (id == R.id.nav_booking) {


        }

        /*} else if (id == R.id.nav_manage) {
*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
