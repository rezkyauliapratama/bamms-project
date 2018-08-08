package rezkyaulia.com.bamms_project.ui.main.adapter;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import rezkyaulia.com.bamms_project.R;
import rezkyaulia.com.bamms_project.data.database.entity.TransactionTbl;
import rezkyaulia.com.bamms_project.databinding.ListItemTransactionBinding;
import rezkyaulia.com.bamms_project.ui.MainViewModel;

public class RvTransactionAdapter extends RecyclerView.Adapter<RvTransactionAdapter.ViewHolder> {


    private MainViewModel viewModel;
    private final LifecycleOwner lifecycleOwner;
    private List<TransactionTbl> list = new ArrayList<>();

    public RvTransactionAdapter(MainViewModel viewModel, LifecycleOwner lifecycleOwner) {
        this.viewModel = viewModel;
        this.lifecycleOwner = lifecycleOwner;

        viewModel.getTransactionsLD().observe(lifecycleOwner, new Observer<List<TransactionTbl>>() {
            @Override
            public void onChanged(@Nullable List<TransactionTbl> transactionTbls) {
                list.clear();
                list.addAll(transactionTbls);
                notifyDataSetChanged();
            }
        });
    }

    @NonNull
    @Override
    public RvTransactionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_transaction, parent, false);
        return new ViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull RvTransactionAdapter.ViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ListItemTransactionBinding binding;
        public ViewHolder(View itemView) {
            super(itemView);
            binding = ListItemTransactionBinding.bind(itemView);
        }

        public void bind(TransactionTbl item) {
            binding.tvCardName.setText("aaaa");
            binding.tvTransactionDate.setText(item.getDate());
            binding.tvTransactionAmount.setText(item.getAmount()+"");
            binding.tvTransactionName.setText(item.getName());
        }
    }
}
