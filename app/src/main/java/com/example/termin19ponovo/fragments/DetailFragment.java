package com.example.termin19ponovo.fragments;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.termin19ponovo.R;
import com.example.termin19ponovo.providers.CategoryProvider;
import com.example.termin19ponovo.providers.JeloProvider;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class DetailFragment extends Fragment {
    private int position = 0;

    public interface OnSpinnerSelected{
        void onSpinnerClick(int position);
    }

    OnSpinnerSelected listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            position = savedInstanceState.getInt("position");

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);


        return inflater.inflate(R.layout.detail_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            position = savedInstanceState.getInt("position",0);
        }


        List<String> listaKategorija = CategoryProvider.getCategorysNames();
        Spinner spCategory = getView().findViewById(R.id.spinner_detail);
        SpinnerAdapter adapterdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, listaKategorija);
        spCategory.setAdapter(adapterdapter);
        spCategory.setSelection(position);
        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                final List<String> jelaByCategory = JeloProvider.getJeloNameByCategoryId(position);
                ListView listJela = getView().findViewById(R.id.lista_jela);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, jelaByCategory);
                listJela.setAdapter(adapter);

                final TextView opisJela = getView().findViewById(R.id.opisJela);
                final ImageView imView = getView().findViewById(R.id.image_view_detail);
                opisJela.setText(JeloProvider.jeloNull().toString());

                try {
                    InputStream is = getActivity().getAssets().open(JeloProvider.jeloNull().getImage());
                    Drawable drawable = Drawable.createFromStream(is, null);
                    imView.setImageDrawable(drawable);
                } catch (IOException e) {
                    e.toString();
                }

                listJela.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        opisJela.setText(JeloProvider.getJeloByName(jelaByCategory.get(position)).toString());

                        try {
                            InputStream is = getActivity().getAssets().open(JeloProvider.getJeloByName(jelaByCategory.get(position)).getImage());
                            Drawable drawable = Drawable.createFromStream(is, null);
                            imView.setImageDrawable(drawable);
                        } catch (IOException e) {
                            e.toString();
                        }
                    }
                });


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putInt("position", position);
    }

    public void setContent(final int position) {

        this.position = position;
    }


    public void updateContent(final int position) {
        this.position = position;

        this.position = position;

        final List<String> listaKategorija = CategoryProvider.getCategorysNames();
        Spinner spinner = getActivity().findViewById(R.id.spinner_detail);
        SpinnerAdapter adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item,listaKategorija);
        spinner.setAdapter(adapter);
        spinner.setSelection(position);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final List<String> listaJela = JeloProvider.getJeloNameByCategoryId(position);
                final ListView listVJela = getActivity().findViewById(R.id.lista_jela);
                ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,listaJela);
                listVJela.setAdapter(adapter1);

                final TextView opisJela = getView().findViewById(R.id.opisJela);
                final ImageView imageView = getView().findViewById(R.id.image_view_detail);

                opisJela.setText(JeloProvider.jeloNull().toString());

                try {
                    InputStream is = getActivity().getAssets().open(JeloProvider.jeloNull().getImage());
                    Drawable drawable = Drawable.createFromStream(is,null);
                    imageView.setImageDrawable(drawable);
                } catch (IOException e) {
                    e.toString();
                }


                listVJela.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        opisJela.setText(JeloProvider.getJeloByName(listaJela.get(position)).toString());

                        try {
                            InputStream is = getActivity().getAssets().open(JeloProvider.getJeloByName(listaJela.get(position)).getImage());
                            Drawable drawable = Drawable.createFromStream(is,null);
                            imageView.setImageDrawable(drawable);
                        } catch (IOException e) {
                            e.toString();
                        }
                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
