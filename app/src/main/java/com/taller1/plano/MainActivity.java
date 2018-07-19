package com.taller1.plano;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.taller1.plano.Adapter.ViviendaAdapter;
import com.taller1.plano.model.Empleado;
import com.taller1.plano.model.Servicio;
import com.taller1.plano.model.Tuberia;
import com.taller1.plano.model.Vivienda;
import com.taller1.plano.utils.Constantes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements  NavigationView.OnNavigationItemSelectedListener,
                    DesingFragment.OnFragmentInteractionListener,
                    ViviendaFragment.OnFragmentInteractionListener,
                    ListViviendaFragment.OnFragmentInteractionListener,
                    CotizacionFragment.OnFragmentInteractionListener{

    private Empleado usuario = new Empleado(0, "", "", "", "", "", "");
    private Tuberia tuberia = new Tuberia(0, "", "", 0);
    private Servicio servicio = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        this.inicializar();
    }
    private void inicializar(){
        servicio = new Servicio(getApplicationContext());

        servicio.getEmpleados(new Response.Listener<JSONArray>(){
            @Override
            public void onResponse(JSONArray response) {
                try{

                    ArrayList<Empleado> empleados = new ArrayList<Empleado>();

                    for (int i = 0; i< response.length(); i++){
                        JSONObject empleado = response.getJSONObject(i);
                        Log.i(Constantes.TAG, "item : " + empleado.toString());

                        usuario = new Empleado(
                                empleado.getInt(Constantes.EMPL_PK),
                                empleado.getString(Constantes.EMPL_NOMBRE),
                                empleado.getString(Constantes.EMPL_APELLIDO),
                                empleado.getString(Constantes.EMPL_CI),
                                empleado.getString(Constantes.EMPL_TELEFONO),
                                empleado.getString(Constantes.EMPL_EMAIL),
                                empleado.getString(Constantes.EMPL_PASSWORD)
                                );
                        return;

                    }
                }catch(JSONException e){
                    Log.i(Constantes.TAG, "erro JSON GETEmpleados : " + e.getMessage());
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(Constantes.TAG, "getEmpleados error : " + error.getMessage());
            }
        });


        servicio.getTuberias(new Response.Listener<JSONArray>(){
            @Override
            public void onResponse(JSONArray response) {
                try{

                    for (int i = 0; i< response.length(); i++){
                        JSONObject t = response.getJSONObject(i);
                        Log.i(Constantes.TAG, "tuberias : " + tuberia.toString());

                        tuberia = new Tuberia(
                                            t.getInt(Constantes.TUBE_PK),
                                            t.getString(Constantes.TUBE_MEDIDA),
                                            t.getString(Constantes.TUBE_DESCRIPCION),
                                            (float) t.getDouble(Constantes.TUBE_PRECIO)
                                    );
                        return;
                    }

                }catch(JSONException e){
                    Log.i(Constantes.TAG, "erro JSON GETTuberias : " + e.getMessage());
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(Constantes.TAG, "getTuberias error : " + error.getMessage());
            }
        });

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

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

        Fragment fragment = null;
        boolean fragmentoSelect = false;

        if (id == R.id.nav_camera) {
            //fragment = new DesingFragment();
            //fragmentoSelect = true;
        } else if (id == R.id.nav_gallery) {
            fragment = new ViviendaFragment();
            fragmentoSelect = true;
        } else if (id == R.id.nav_slideshow) {
            fragment = new ListViviendaFragment();
            Bundle bundle = new Bundle();
            Gson gson = new Gson();
            bundle.putString("EMPLEADO", gson.toJson(usuario));
            bundle.putString("TUBERIA", gson.toJson(tuberia));
            bundle.putString("TIPO_LISTA", Constantes.TIPO_PLANO);
            fragment.setArguments(bundle);
            fragmentoSelect = true;
        } else if (id == R.id.nav_manage) {
            Log.i(Constantes.TAG, "entre a cotizaciones");
            fragment = new ListViviendaFragment();
            Bundle bundle = new Bundle();
            Gson gson = new Gson();
            bundle.putString("EMPLEADO", gson.toJson(usuario));
            bundle.putString("TUBERIA", gson.toJson(tuberia));
            bundle.putString("TIPO_LISTA", Constantes.TIPO_COTIZACION);
            fragment.setArguments(bundle);
            fragmentoSelect = true;

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        if(fragmentoSelect){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contenedor, fragment)
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
