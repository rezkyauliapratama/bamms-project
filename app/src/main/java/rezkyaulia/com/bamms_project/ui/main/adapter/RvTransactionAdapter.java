package rezkyaulia.com.bamms_project.ui.main.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import rezkyaulia.com.bamms_project.R;
import rezkyaulia.com.bamms_project.data.database.entity.TransactionTbl;
import rezkyaulia.com.bamms_project.databinding.ListItemTransactionBinding;
import rezkyaulia.com.bamms_project.ui.MainViewModel;

public class RvTransactionAdapter extends RecyclerView.Adapter<RvTransactionAdapter.ViewHolder> {


    private MainViewModel viewModel;
    private List<TransactionTbl> list;

    public RvTransactionAdapter(MainViewModel viewModel, List<TransactionTbl> list) {
        this.viewModel = viewModel;
        this.list = list;
    }

    @NonNull
    @Override
    public RvTransactionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_card, parent, false);
        return new ViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull RvTransactionAdapter.ViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ListItemTransactionBinding binding;
        public ViewHolder(View itemView) {
            super(itemView);
            binding = ListItemTransactionBinding.bind(itemView);
        }

        public void bind(TransactionTbl item) {
            binding.tvCardName.setText(item.getBankAccountTbl().getAcountNumber());
            binding.tvTransactionDate.setText(item.getDate());
            binding.tvTransactionAmount.setText(item.getAmount());
            binding.tvTransactionName.setText(item.getName());
        }
    }
}
