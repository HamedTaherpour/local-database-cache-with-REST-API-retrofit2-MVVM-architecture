package ir.hamed.mvvm.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.hamed.mvvm.App;
import ir.hamed.mvvm.R;
import ir.hamed.mvvm.model.data.local.model.SongEntity;

public class SongAdapter extends ListAdapter<SongEntity, SongAdapter.RecorderHolder> {

    private OnItemClickListener listener;

    private static final DiffUtil.ItemCallback<SongEntity> DIFF_CALLBACK = new DiffUtil.ItemCallback<SongEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull SongEntity oldItem, @NonNull SongEntity newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull SongEntity oldItem, @NonNull SongEntity newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getAlbumTitle().equals(newItem.getAlbumTitle()) &&
                    oldItem.getId() == newItem.getId();
        }
    };

    public SongAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public RecorderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_item_layout, parent, false);
        return new RecorderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecorderHolder holder, int position) {
        holder.setIsRecyclable(false);
        holder.bind(getItem(position));
    }

    class RecorderHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_album_title)
        TextView tvAlbumTitle;
        @BindView(R.id.iv)
        ImageView imageView;

        public RecorderHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(SongEntity entity) {
            tvTitle.setText(entity.getTitle());
            tvAlbumTitle.setText(entity.getAlbumTitle());
            Glide.with(App.getAppContext())
                    .load(entity.getImgUrl())
                    .apply(RequestOptions.circleCropTransform())
                    .error(R.drawable.ic_broken)
                    .into(imageView);
        }

        @OnClick
        public void onClick() {
            int position = getAdapterPosition();
            if (listener != null && position != RecyclerView.NO_POSITION)
                listener.onItemClick(getItem(position));
        }
    }

    public interface OnItemClickListener {
        void onItemClick(SongEntity entity);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
