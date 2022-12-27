package com.example.weatherforecast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class MainActivityLoading extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{

    private final int RC_LOCATION_PERM = 124;
    private static final String TAG = "MainActivityLoading";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_loading);
        // Dùng try catch để tránh bị crash app


        try {
            requestLocationPermission();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                startActivity(new Intent(MainActivityLoading.this,MainActivityHome.class));
//                finish();
//            }
//        },5000);
    }



    private void requestLocationPermission() {
        Log.i(TAG, "requestLocationPermission");
        // Kiểm tra SDK
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }

        // Kiểm tra quyền đã được chấp thuận chưa
        if (EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            Intent intent = new Intent(this, MainActivityHome.class);
            startActivity(intent);
        } else {
            // Nếu chưa được chấp thuận thì yêu cầu chấp thuận
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.rationale_location),
                    RC_LOCATION_PERM,
                    Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        Intent intent = new Intent(this, MainActivityHome.class);
        startActivity(intent);
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        finish();
    }


    //    // Yêu cầu quyền truy cập vị trí
//    private void requestLocationPermission() {
//        // Kiểm tra SDK
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
//            return;
//        }
//
//        // Kiểm tra quyền đã được chấp thuận chưa
//        if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            addMyLocationButton();
//            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
//        } else {
//            // Nếu chưa được chấp thuận thì yêu cầu chấp thuận
//            String[] permissions = {android.Manifest.permission.ACCESS_FINE_LOCATION};
//            requestPermissions(permissions, 1);
//        }
//    }
//
//    // Hàm đề thêm nút MyLocationButton
//    private void addMyLocationButton() {
//        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
//                != getPackageManager().PERMISSION_GRANTED
//
//                &&
//
//                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
//                        != getPackageManager().PERMISSION_GRANTED) {
//            return;
//        }
//        mMap.setMyLocationEnabled(true);
//        mMap.getUiSettings().setMyLocationButtonEnabled(true);
//    }
//
//    // Kiểm tra kết quả Permission đã cho cho phéo hay chưa
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == 1) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                addMyLocationButton();
//                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    // Method yêu cầu bật GPS
//    private void requestLocation() {
//        // LocationRequest Builder
//        LocationRequest locationRequest = new LocationRequest.Builder(Request.Priority.PRIORITY_HIGH_ACCURACY)
//                .setIntervalMillis(1000)
//                .build();
//
//        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
//                .addLocationRequest(locationRequest);
//
//        // Lấy setting từ máy, để biết GPS đã bật chưa
//        SettingsClient client = LocationServices.getSettingsClient(this);
//
//        // Task dùng để mở luồng (thread) mới để kiểm tra GPS
//        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
//
//        // Nếu GPS chưa bật thì mở cửa sổ yêu cầu bật GPS
//        // thêm listener để bắt sự kiện khi người dùng chưa bật GPS (vì Task fail)
//        task.addOnFailureListener(this, new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                if (e instanceof ResolvableApiException) {
//                    try {
//                        // Show the dialog by calling startResolutionForResult(),
//                        // and check the result in onActivityResult().
//                        ResolvableApiException resolvable = (ResolvableApiException) e;
//                        resolvable.startResolutionForResult(MainActivity.this,
//                                51);
//                    } catch (IntentSender.SendIntentException sendEx) {
//                        // Ignore the error.
//                    }
//                }
//            }
//        });
//    }
}