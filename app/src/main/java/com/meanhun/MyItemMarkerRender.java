package com.meanhun;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

public class MyItemMarkerRender extends DefaultClusterRenderer<MyItem> {
    private static final int MARKER_DIMENSION = 128;

    private final IconGenerator iconGenerator;
    private final ImageView markerImageView;

    @Override
    protected boolean shouldRenderAsCluster(Cluster<MyItem> cluster) {
        //start clustering if 2 or more items overlap
        return cluster.getSize() > 1;
    }
//
    @Override
    protected void onClusterItemUpdated(@NonNull MyItem item, @NonNull Marker marker) {
        marker.setTitle(item.getTitle());
    }

    public MyItemMarkerRender(Context context, GoogleMap map, ClusterManager<MyItem> clusterManager) {
        super(context, map, clusterManager);
        iconGenerator = new IconGenerator(context);
        markerImageView = new ImageView(context);
        markerImageView.setLayoutParams(new ViewGroup.LayoutParams(MARKER_DIMENSION, MARKER_DIMENSION));
        iconGenerator.setContentView(markerImageView);
    }

    @Override
    protected void onBeforeClusterItemRendered(MyItem item, MarkerOptions markerOptions) {
        markerImageView.setImageResource(R.drawable.pinmaps);
        Bitmap icon = iconGenerator.makeIcon();
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
        markerOptions.title(item.getTitle());
    }



    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int id){
        Drawable vecorDrawable = ContextCompat.getDrawable(context,id);
        vecorDrawable.setBounds(0,0,48,
                48);
        Bitmap bitmap = Bitmap.createBitmap(48,
                48,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vecorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}
