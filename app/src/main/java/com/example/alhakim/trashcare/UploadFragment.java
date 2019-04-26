package com.example.alhakim.trashcare;

import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.api.IMapController;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.Objects;

import static android.app.Activity.RESULT_OK;


public class UploadFragment extends Fragment implements View.OnClickListener {

    private MapView mapView;
    private IMapController mapController;
    private MyLocationNewOverlay myLocationNewOverlay;
    private Spinner spinner;
    private EditText urlGambar;
    private Button btnGambar, btnUpload ;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_upload, container, false);

        String [] item_jenis =
                {"Organik", "Anorganik", "B3", "Campuran",};
        Spinner spinner = (Spinner) rootView.findViewById(R.id.jenis_sampah);
        ArrayAdapter<String>  adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1,item_jenis);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);

        btnGambar = (Button) rootView.findViewById(R.id.btnGambar);
        btnGambar.setOnClickListener(this);
        btnUpload = (Button) rootView.findViewById(R.id.btnUpload);
        btnUpload.setOnClickListener(this);
        urlGambar = (EditText) rootView.findViewById(R.id.urlGambar);

        


        return  rootView;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setMap(this.getView());

        setMyLocationNewOverlay();
        myLocationNewOverlay.enableMyLocation();
        mapController.animateTo(myLocationNewOverlay.getMyLocation());
        mapController.setCenter(myLocationNewOverlay.getMyLocation());

        //mapController.setCenter(new GeoPoint(myLocationNewOverlay.getMyLocation().getLatitude(),myLocationNewOverlay.getMyLocation().getLongitude()));

    }

    void setMap(View view) {
        mapView = view.findViewById(R.id.maps);
        Configuration.getInstance().setUserAgentValue(this.getActivity().getPackageName());
        mapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);
        mapView.setMultiTouchControls(true);
        mapView.setBuiltInZoomControls(false);
        mapView.getMapScrollY();
        mapView.getController();
        mapView.setFocusableInTouchMode(true);
        mapController = mapView.getController();
        mapController.setZoom(8);

    }

    void setMyLocationNewOverlay() {
        GpsMyLocationProvider prov= new GpsMyLocationProvider(getActivity().getBaseContext());
        prov.addLocationSource(LocationManager.NETWORK_PROVIDER);

        myLocationNewOverlay = new MyLocationNewOverlay(prov, mapView);
        myLocationNewOverlay.enableMyLocation();
        myLocationNewOverlay.getMyLocation();
        mapView.getOverlays().add(myLocationNewOverlay);


        //mapController.setCenter(new GeoPoint(myLocationNewOverlay.getMyLocation().getLatitude(),myLocationNewOverlay.getMyLocation().getLongitude()));
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnGambar){
            Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(takePicture, 0);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == 0){
                urlGambar.setHint("2019020222573.jpg");
            }
        }

    }




}
