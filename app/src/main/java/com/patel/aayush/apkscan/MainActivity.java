package com.patel.aayush.apkscan;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.File;
import java.security.Permission;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView tv, pacnametv, sizetv, pathtv;
    ImageView imageView, iconimage;
    RecyclerView recyclerView, servicerecycler, receiverrecycler, providerreceiver;
    List<Permissions_List> permissions_lists = new ArrayList<>();
    PermissionAdapter permissionAdapter;
    List<Service_list> service_lists = new ArrayList<>();
    ServiceAdapter serviceAdapter;
    List<Receiver_list> receiver_lists = new ArrayList<>();
    ReceiverAdapter receiverAdapter;
    List<Providers_list> providers_lists = new ArrayList<>();
    ProviderAdapter providerAdapter;
    LinearLayout nopermission, noreciever, noservices, noprovider;
    List<AppsListClass> appsListClasses = new ArrayList<>();
    AppsAdapter appsAdapter;
    RecyclerView listofapps;
    //-----Pie Chart Variable's-----
    PieChart pieChart;
    ArrayList<Entry> entries;
    ArrayList<String> PieEntryLabels;
    PieDataSet pieDataSet;
    PieData pieData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //--------------get package name (message) from broadcast receiver's------
        Intent intent = getIntent();
        String message = intent.getStringExtra("message");

        // 1.No broadcast is received then display phone app analysis or else show individual app analysis (permissions).
        if (message == null) {
            setContentView(R.layout.homepagelayout);
            pieChart = (PieChart) findViewById(R.id.piechartid);
            entries = new ArrayList<>();
            PieEntryLabels = new ArrayList<String>();
            listofapps = (RecyclerView) findViewById(R.id.list);
            GridLayoutManager gridLayoutManage = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
            listofapps.setLayoutManager(gridLayoutManage);
            PackageManager packageManager = getPackageManager();
            List<PackageInfo> list = getPackageManager().getInstalledPackages(0);
            int dangerous = 0;
            int normal = 0;
            int others = 0;
            int total_dangerous = 0;
            int total_normal = 0;
            int total_others = 0;
            for (int i = 0; i < list.size(); i++) {
                PackageInfo p = list.get(i);
                //  If the package installed is system package then discard it .
                if ((isSystemPackage(p) == false)) {
                    try {
                        PackageInfo packageInfo1 = packageManager.getPackageInfo(p.applicationInfo.packageName, PackageManager.GET_PERMISSIONS);
                        String[] requestedPermissions = packageInfo1.requestedPermissions;
                        if (requestedPermissions != null) {
                            for (int ii = 0; ii < requestedPermissions.length; ii++) {
                                PermissionInfo pi = getPackageManager().getPermissionInfo(requestedPermissions[ii], PackageManager.GET_META_DATA);
                                String protctionLevel = "unknown";
                                switch (pi.protectionLevel) {
                                    case PermissionInfo.PROTECTION_NORMAL:
                                        normal++;
                                        total_normal++;
                                        protctionLevel = "Normal";
                                        break;
                                    case PermissionInfo.PROTECTION_DANGEROUS:
                                        dangerous++;
                                        total_dangerous++;
                                        protctionLevel = "Dangerous";
                                        break;
                                    case PermissionInfo.PROTECTION_SIGNATURE:
                                        protctionLevel = "Signature";
                                        others++;
                                        total_others++;
                                        break;
                                    case PermissionInfo.PROTECTION_SIGNATURE_OR_SYSTEM:
                                        protctionLevel = "SignatureOrSystem";
                                        total_others++;
                                        others++;
                                        break;
                                    case PermissionInfo.PROTECTION_FLAG_SYSTEM:
                                        others++;
                                        total_others++;
                                        protctionLevel = "System";
                                        break;
                                    default:
                                        protctionLevel = "<unknown>";
                                        others++;
                                        total_others++;
                                        break;

                                }
                                Log.d("c", total_dangerous + "" + total_normal + "" + total_others);
                            }
                            Log.d("abc", "packagename:" + p.applicationInfo.packageName + "\n" + "dangerous" + dangerous + "Normal" + normal + "others" + others);
                        }
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                    Log.d("list", p.applicationInfo.packageName);
                    Log.d("name", p.applicationInfo.loadLabel(getPackageManager()).toString());
                    appsListClasses.add(new AppsListClass(p.applicationInfo.loadIcon(getPackageManager()), p.applicationInfo.loadLabel(getPackageManager()).toString(), p.applicationInfo.packageName, dangerous, normal, others));
                    dangerous = 0;
                    normal = 0;
                    others = 0;
// Adding all the details to adapter
                    appsAdapter = new AppsAdapter(this, appsListClasses);
                    listofapps.setAdapter(appsAdapter);
                }
            }
            Log.d("co", total_dangerous + "" + total_normal + "" + total_others);
// Pie chart display
            entries.add(new BarEntry(total_dangerous, 0));
            entries.add(new BarEntry(total_normal, 1));
            entries.add(new BarEntry(total_others, 2));
            AddValuesToPieEntryLabels();
            pieDataSet = new PieDataSet(entries, "");
            pieData = new PieData(PieEntryLabels, pieDataSet);
            pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
            pieChart.setData(pieData);
            pieChart.animateXY(3000, 3000);
// Now if the Broadcast is received.
        } else {
            setContentView(R.layout.activity_main);
            //Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            nopermission = (LinearLayout) findViewById(R.id.nopermissionsid);
            noreciever = (LinearLayout) findViewById(R.id.noreceiversid);
            noservices = (LinearLayout) findViewById(R.id.noservicesid);
            noprovider = (LinearLayout) findViewById(R.id.noprovidersid);
            nopermission.setVisibility(View.GONE);
            noservices.setVisibility(View.GONE);
            noreciever.setVisibility(View.GONE);
            noprovider.setVisibility(View.GONE);
            //listView.addFooterView(footer);
            recyclerView = (RecyclerView) findViewById(R.id.recyclerid);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
            GridLayoutManager gridLayoutManager1 = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
            GridLayoutManager gridLayoutManager2 = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
            GridLayoutManager gridLayoutManager3 = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(gridLayoutManager);
            servicerecycler = (RecyclerView) findViewById(R.id.recyclerservicsid);
            servicerecycler.setLayoutManager(gridLayoutManager1);
            receiverrecycler = (RecyclerView) findViewById(R.id.recyclersreceiverid);
            receiverrecycler.setLayoutManager(gridLayoutManager2);
            providerreceiver = (RecyclerView) findViewById(R.id.recycleprovidrid);
            providerreceiver.setLayoutManager(gridLayoutManager3);
            Drawable p1;
            int p;
            try {
                p = getPackageManager().getPermissionInfo("android.permission.INTERNET", 0).protectionLevel;
                PackageManager pm = getPackageManager();
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            pacnametv = (TextView) findViewById(R.id.pnameid);
            sizetv = (TextView) findViewById(R.id.sizeid);
            pathtv = (TextView) findViewById(R.id.apkfilepathid);
            imageView = (ImageView) findViewById(R.id.app_icon);
            tv = (TextView) findViewById(R.id.anameid);
            Drawable icon = null;
            try {
                icon = getPackageManager().getApplicationIcon(message);
                imageView.setImageDrawable(icon);

            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            PackageManager packageManager = getPackageManager();
            ApplicationInfo info = null;
            try {
                info = packageManager.getApplicationInfo(message, PackageManager.GET_META_DATA);
                String appName = (String) packageManager.getApplicationLabel(info);
                tv.setText(appName);
                String apkfilepath = info.sourceDir;
                pathtv.setText(apkfilepath);
                pacnametv.setText(message);
                File file = new File(info.sourceDir);
                float size = file.length();

                float sizemb=size /(1024*1024);
                DecimalFormat df = new DecimalFormat("#.##");
                sizemb = Float.valueOf(df.format(sizemb));

                sizetv.setText("" + sizemb + " Mb");

            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            StringBuffer permissions = new StringBuffer();
            StringBuffer serv = new StringBuffer();
            StringBuffer rec = new StringBuffer();
            StringBuffer provider = new StringBuffer();
            try {
                PackageInfo packageInfo = packageManager.getPackageInfo(message, PackageManager.GET_PERMISSIONS);
                PackageInfo packageInfo1 = packageManager.getPackageInfo(message, PackageManager.GET_SERVICES | PackageManager.GET_RECEIVERS | PackageManager.GET_PROVIDERS);
                ServiceInfo[] services = packageInfo1.services;
                if (services != null) {
                    for (int y = 0; y < services.length; y++) {
                        service_lists.add(new Service_list(String.valueOf(services[y]), services.length));
                        serv.append("Services" + services[y]);
                        Log.d("extra", "Services:" + String.valueOf(serv));
                    }
                    serviceAdapter = new ServiceAdapter(this, service_lists);
                    servicerecycler.setAdapter(serviceAdapter);
                } else {
                    noservices.setVisibility(View.VISIBLE);
                    Log.d("extra", "no services");
                }
                ActivityInfo[] receivers = packageInfo1.receivers;
                if (receivers != null) {
                    for (int y = 0; y < receivers.length; y++) {
                        receiver_lists.add(new Receiver_list(String.valueOf(receivers[y]), receivers.length));
                        rec.append("Receivers" + receivers[y]);
                        Log.d("extra", "Receiver's" + String.valueOf(rec));
                    }
                    receiverAdapter = new ReceiverAdapter(this, receiver_lists);
                    receiverrecycler.setAdapter(receiverAdapter);
                } else {
                    noreciever.setVisibility(View.VISIBLE);
                    Log.d("extra", "no receivers");
                }
                ProviderInfo[] providers = packageInfo1.providers;
                if (providers != null) {
                    for (int y = 0; y < providers.length; y++) {
                        providers_lists.add(new Providers_list(String.valueOf(providers[y]), providers.length));
                        provider.append("Providers" + providers[y]);
                        Log.d("extra", "Providers:" + String.valueOf(provider));
                    }
                    providerAdapter = new ProviderAdapter(this, providers_lists);
                    providerreceiver.setAdapter(providerAdapter);
                } else {
                    noprovider.setVisibility(View.VISIBLE);
                    Log.d("extra", "no providers");
                }
                String[] requestedPermissions = packageInfo.requestedPermissions;
                if (requestedPermissions != null) {
                    for (int i = 0; i < requestedPermissions.length; i++) {
                        PermissionInfo pi = getPackageManager().getPermissionInfo(requestedPermissions[i], PackageManager.GET_META_DATA);
                        String protctionLevel = "unknown";
                        PermissionInfo pi1 = getPackageManager().getPermissionInfo(requestedPermissions[i], PackageManager.GET_META_DATA);
                        String s = (String) pi1.loadDescription(getPackageManager());
                        switch (pi.protectionLevel) {
                            case PermissionInfo.PROTECTION_NORMAL:
                                protctionLevel = "Normal";
                                break;
                            case PermissionInfo.PROTECTION_DANGEROUS:
                                protctionLevel = "Dangerous";
                                break;
                            case PermissionInfo.PROTECTION_SIGNATURE:
                                protctionLevel = "Signature";
                                break;
                            case PermissionInfo.PROTECTION_SIGNATURE_OR_SYSTEM:
                                protctionLevel = "SignatureOrSystem";
                                break;
                            case PermissionInfo.PROTECTION_FLAG_SYSTEM:
                                protctionLevel = "System";
                                break;
                            default:
                                protctionLevel = "<unknown>";
                                break;
                        }
                        permissions.append(requestedPermissions[i] + "Level: " + protctionLevel + " Desc" + s + "\n");
                        //adding to recycler
                        permissions_lists.add(new Permissions_List(requestedPermissions[i].substring(19), protctionLevel, s));
                    }
                    permissionAdapter = new PermissionAdapter(this, permissions_lists);
                    recyclerView.setAdapter(permissionAdapter);
                    Log.d("per", "Permissions: " + permissions);
                } else {
                    nopermission.setVisibility(View.VISIBLE);
                    Toast.makeText(MainActivity.this, "No permissions requires,", Toast.LENGTH_LONG).show();
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

        }

    }
    private boolean isSystemPackage(PackageInfo pkgInfo) {
        return ((pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) ? true : false;
    }
    public void AddValuesToPieEntryLabels() {
        PieEntryLabels.add("Dangerous");
        PieEntryLabels.add("Normal");
        PieEntryLabels.add("Other's");
    }
}