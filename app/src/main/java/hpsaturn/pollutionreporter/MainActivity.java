package hpsaturn.pollutionreporter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.gson.Gson;
import com.hpsaturn.tools.Logger;
import com.iamhabib.easy_preference.EasyPreference;
import com.polidea.rxandroidble2.RxBleClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import hpsaturn.pollutionreporter.bleservice.ServiceBLE;
import hpsaturn.pollutionreporter.bleservice.ServiceInterface;
import hpsaturn.pollutionreporter.bleservice.ServiceManager;
import hpsaturn.pollutionreporter.bleservice.ServiceScheduler;
import hpsaturn.pollutionreporter.models.SensorData;
import hpsaturn.pollutionreporter.view.ChartFragment;
import hpsaturn.pollutionreporter.view.ScanFragment;

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    private ScanFragment scanFragment;
    private EasyPreference.Builder prefBuilder;
    private ChartFragment chartFragment;
    private ServiceManager serviceManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        prefBuilder = AppData.getPrefBuilder(this);

        setSupportActionBar(toolbar);
        checkBluetoohtBle();
        setupUI();
        serviceManager = new ServiceManager(this,serviceListener);
        startBleService();

    }


    private ServiceInterface serviceListener = new ServiceInterface() {
        @Override
        public void onServiceStatus(String status) {

            if(status.equals(ServiceManager.STATUS_BLE_START)){
                showChartFragment();
            }
            else if(status.equals(ServiceManager.STATUS_BLE_FAILURE)){
                showSnackMessage(getString(R.string.msg_device_disconnected));
            }
        }

        @Override
        public void onServiceStart() {

        }

        @Override
        public void onServiceStop() {

        }

        @Override
        public void onServiceData(byte[] bytes) {
            String strdata = new String(bytes);
            Logger.i(TAG, "data: " + strdata);
            SensorData data = new Gson().fromJson(strdata, SensorData.class);
            if(chartFragment!=null) chartFragment.addData(data.P25);
        }
    };

    private View.OnClickListener onFabClickListener = view -> { };

    private void setupUI() {
        fab.setOnClickListener(onFabClickListener);
        fab.setVisibility(View.INVISIBLE); // TODO: I need work on it
        checkForPermissions();
        if (!prefBuilder.getBoolean(Keys.DEVICE_PAIR, false)) {
            fab.setVisibility(View.INVISIBLE);
            showScanFragment();
        }
    }

    private void showChartFragment() {
        if (chartFragment == null) chartFragment = ChartFragment.newInstance();
        if (!chartFragment.isVisible()) showFragment(chartFragment, ChartFragment.TAG, false);
    }

    private void showScanFragment() {
        if (scanFragment == null) scanFragment = ScanFragment.newInstance();
        if (!scanFragment.isVisible()) showFragment(scanFragment, ScanFragment.TAG, false);
    }

    public void removeScanFragment(){
        if (scanFragment != null)removeFragment(scanFragment);
    }


    private void startBleService() {
        Intent newIntent = new Intent(this, ServiceBLE.class);
        startService(newIntent);
        ServiceScheduler.startScheduleService(this, Config.DEFAULT_INTERVAL);
    }

    private void showSnackMessage(String msg) {
        Snackbar.make(coordinatorLayout, msg, Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    public void deviceConnect() {
        if (prefBuilder.getBoolean(Keys.DEVICE_PAIR, false)) {
            showSnackMessage(getString(R.string.msg_device_connecting));
            serviceManager.start();
        }
    }

    @Override
    void actionUnPair() {
        serviceManager.stop();
        prefBuilder.clearAll().save();
        removeFragment(chartFragment);
        showScanFragment();
    }

}
