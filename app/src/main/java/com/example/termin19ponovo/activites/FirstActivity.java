package com.example.termin19ponovo.activites;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.termin19ponovo.R;
import com.example.termin19ponovo.adapters.DrawerAdapter;
import com.example.termin19ponovo.dialogs.AboutDialog;
import com.example.termin19ponovo.fragments.DetailFragment;
import com.example.termin19ponovo.fragments.MasterFragment;
import com.example.termin19ponovo.model.NavigationItem;

import java.util.ArrayList;

public class FirstActivity extends AppCompatActivity implements MasterFragment.OnItemSelectedListener,DetailFragment.OnSpinnerSelected {
    private MasterFragment masterFragment = null;
    private DetailFragment detailFragment = null;
    private int position = 0;

    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;
    private RelativeLayout drawerPane;
    private CharSequence drawerTitle;
    private ArrayList<NavigationItem> drawerItems = new ArrayList<NavigationItem>();

    // Attributes used by Dialog
    private AlertDialog dialog;

    // Attributes representing the activity's state
    private boolean landscapeMode = false; // Is the device in the landscape mode?




    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            if (position == 0) {
               /* final List<String> listaJela = JeloProvider.getJelaNames();
                final ListView listView = findViewById(R.id.lista_1);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_list_item_1,listaJela);
                listView.setAdapter(adapter); */ // zanemari , nije dovrseno, ostavi prazno ili ovako.
            } else if (position == 1) {
                Intent settings = new Intent(FirstActivity.this, SettingsActivity.class);
                startActivity(settings);
            } else if (position == 2) {
                if (dialog == null) {
                    dialog = new AboutDialog(FirstActivity.this).prepareDialog();
                } else {
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
                dialog.show();
            }

            drawerList.setItemChecked(position, true);
            setTitle(drawerItems.get(position).getTitle());
            drawerLayout.closeDrawer(drawerPane);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);


        if (savedInstanceState !=null) {
            position = savedInstanceState.getInt("position",position);
        }

        masterFragment = new MasterFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.master_view,masterFragment);
        ft.commit();


        if (findViewById(R.id.detail_view) != null) {
            landscapeMode = true;
            fragmentManager.popBackStack();

            if (detailFragment == null) {
                detailFragment = new DetailFragment();
                detailFragment.setContent(position);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.detail_view,detailFragment);
                fragmentTransaction.commit();
            }

        }




        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_drawer);
            actionBar.setHomeButtonEnabled(true);
            actionBar.show();

        }

        drawerItems.add(new NavigationItem("Prikazi Sva Jela","Prikazuje Sva Jela", R.drawable.ic_action_product));
        drawerItems.add(new NavigationItem("Prikazi Sve Kategorije","Prikazuje sve Kategorije, Meni",R.drawable.ic_action_product));
        drawerItems.add(new NavigationItem("Podesavanja","Podesavanja za automatsku sync",R.drawable.ic_action_settings));
        drawerItems.add(new NavigationItem("O aplikaciji","Moguca Porudzbina",R.drawable.ic_action_about));

        DrawerAdapter adapter = new DrawerAdapter(this,drawerItems);
        drawerList = findViewById(R.id.navList);
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
        drawerList.setAdapter(adapter);

        drawerTitle = getTitle();
        drawerLayout =findViewById(R.id.drawerLayout);
        drawerPane = findViewById(R.id.drawer_pane);

        drawerToggle = new ActionBarDrawerToggle(
                this,                           /* host Activity */
                drawerLayout,                   /* DrawerLayout object */
                toolbar,
                R.string.drawer_close,
                R.string.drawer_open
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                getSupportActionBar().setTitle(drawerTitle);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(drawerTitle);
                invalidateOptionsMenu();
            }
        };





    }


    @Override
    public void onBackPressed() {

        if (landscapeMode) {
            finish();
        } else {
            getFragmentManager().popBackStack();
            detailFragment = new DetailFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.master_view,masterFragment);
            ft.commit();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_create:
                Toast.makeText(this, "Action " + "CREATED" + " executed.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_update:
                Toast.makeText(this, "Action " + "UPDATED" + " executed.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_delete:
                Toast.makeText(this, "Action " + "DELETED" + " executed.", Toast.LENGTH_SHORT).show();
                Snackbar.make(findViewById(R.id.master_view), "test", Snackbar.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);

    }
    @Override
    public void onItemSelected(int position) {

        this.position = position;

        if (landscapeMode) {
            detailFragment.updateContent(position);
        } else {
            detailFragment = new DetailFragment();
            detailFragment.setContent(position);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction ft1 = fragmentManager.beginTransaction();
            ft1.replace(R.id.master_view,detailFragment);
            ft1.addToBackStack(null);
            ft1.commit();
        }
    }



    @Override
    public void onSpinnerClick(int position) {

        this.position = position;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("position",position);

    }
}
