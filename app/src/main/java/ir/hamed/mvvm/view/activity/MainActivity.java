package ir.hamed.mvvm.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import ir.hamed.mvvm.App;
import ir.hamed.mvvm.R;
import ir.hamed.mvvm.viewmodel.SongListViewModel;
import ir.hamed.mvvm.model.data.local.model.SongEntity;
import ir.hamed.mvvm.view.adapter.SongAdapter;


public class MainActivity extends AppCompatActivity implements SongAdapter.OnItemClickListener {

    private static final String TAG = "@MY_APP";
    private SongListViewModel viewModel;

    private ProgressDialog progress;
    private Dialog dialog;
    private SongAdapter adapter;

    @BindView(R.id.rv)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        progress = new ProgressDialog(this);
        progress.setMessage("Wait . . .");
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setIndeterminate(true);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        adapter = new SongAdapter();
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this).get(SongListViewModel.class);
        viewModel.getSongData().observe(this, listResource -> {
            if (listResource != null) {
                Log.d(TAG, "onChanged: status: " + listResource.status);
                switch (listResource.status) {
                    case LOADING: {
                        setProgress(true);
                        break;
                    }
                    case ERROR: {
                        Toast.makeText(MainActivity.this, "Error: " + listResource.getMessage(), Toast.LENGTH_SHORT).show();
                        setProgress(false);
                        break;
                    }
                    case SUCCESS: {
                        adapter.submitList(listResource.data);
                        setProgress(false);
                        break;
                    }
                }
            }
        });
    }

    private void setProgress(boolean show) {
        if (show) {
            if (!progress.isShowing()) {
                progress.show();
            }
        } else {
            progress.dismiss();
        }
    }

    private void showDialogDetail(SongEntity entity) {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.detail_dialog_layout);

        TextView tvTitle = dialog.findViewById(R.id.tv_title);
        TextView tvAlbumTitle = dialog.findViewById(R.id.tv_album_title);
        ImageView imageView = dialog.findViewById(R.id.iv);
        ImageButton btnClose = dialog.findViewById(R.id.btn_close);

        tvTitle.setText(entity.getTitle());
        tvAlbumTitle.setText(entity.getAlbumTitle());
        Glide.with(App.getAppContext())
                .load(entity.getImgUrl())
                .error(R.drawable.ic_broken)
                .into(imageView);

        btnClose.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show();
    }

    @Override
    public void onItemClick(SongEntity entity) {
        showDialogDetail(entity);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

}
