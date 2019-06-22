package com.panaderia.ricopan;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class ListadoProductos extends AppCompatActivity implements
        ProductosFragment.OnFragmentInteractionListener, IFragments{
    private TabLayout tabLayout;
    private ViewPager viewPagerFragment;
    private int idUser;
    Fragment listaPanPeqFragment,
            listaPanGrandeFragment,
            listaDulceriaFragment,
            listaQuesoFragment,
            currentFragment;

    public static final String TIPO="tipoProducto";
    public static final String IDUSER="IDUser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_productos);

        Intent data= getIntent();
        idUser= data.getIntExtra("IDUser", 0);
        System.out.println("ID User - "+idUser);
        /*SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
*/
        //proceso de inflacion de tabLayout, view page para fragments y creacion objeto adapterFragment
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPagerFragment = (ViewPager) findViewById(R.id.view_pager);
        viewPagerFragment.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener());
        SectionsPagerAdapter adapterFragment = new SectionsPagerAdapter(getSupportFragmentManager());
        //creado el objeto ViewPagerFragmentAdapter procedemos a adicionar fragments con la funcion addFragment de la clase ViewPagerFragmentAdapter

        listaPanPeqFragment=new ProductosFragment();
        Bundle bundle= new Bundle();
        bundle.putString(TIPO,"6");
        bundle.putInt(IDUSER,idUser);
        listaPanPeqFragment.setArguments(bundle);

        listaPanGrandeFragment= new ProductosFragment();
        Bundle bundle2= new Bundle();
        bundle2.putString(TIPO,"5");
        bundle2.putInt(IDUSER,idUser);
        listaPanGrandeFragment.setArguments(bundle2);

        listaDulceriaFragment= new ProductosFragment();
        Bundle bundle3= new Bundle();
        bundle3.putString(TIPO,"4");
        bundle3.putInt(IDUSER,idUser);
        listaDulceriaFragment.setArguments(bundle3);

        listaQuesoFragment= new ProductosFragment();
        Bundle bundle4= new Bundle();
        bundle4.putString(TIPO,"3");
        bundle4.putInt(IDUSER,idUser);
        listaQuesoFragment.setArguments(bundle4);

        adapterFragment.addFragment(listaPanPeqFragment, getString(R.string.tab_one));
        adapterFragment.addFragment(listaPanGrandeFragment, getString(R.string.tab_two));
        adapterFragment.addFragment(listaQuesoFragment, getString(R.string.tab_three));
        adapterFragment.addFragment(listaDulceriaFragment, getString(R.string.tab_four));
        //configuracion adapterFragment al content_main(xml)
        viewPagerFragment.setAdapter(adapterFragment);
        tabLayout.setupWithViewPager(viewPagerFragment);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackPressed() {
        Intent volverMain = new Intent(this, MainActivity.class);
        volverMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(volverMain);
    }
}