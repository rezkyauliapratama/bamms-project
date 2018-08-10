package rezkyaulia.com.bamms_project.ui.main.adapter;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rezkyaulia.com.bamms_project.R;
import rezkyaulia.com.bamms_project.data.database.entity.TransactionTbl;
import rezkyaulia.com.bamms_project.databinding.ListItemTransactionBinding;
import rezkyaulia.com.bamms_project.ui.main.MainViewModel;
import rezkyaulia.com.bamms_project.util.TimeUtils;

public class RvTransactionAdapter extends RecyclerView.Adapter<RvTransactionAdapter.ViewHolder> {


    private Context context;
    private MainViewModel viewModel;
    private final LifecycleOwner lifecycleOwner;
    private TimeUtils timeUtils;
    private List<TransactionTbl> list = new ArrayList<>();

    public RvTransactionAdapter(Context context, MainViewModel viewModel, LifecycleOwner lifecycleOwner, TimeUtils timeUtils) {
        this.context = context;
        this.viewModel = viewModel;
        this.lifecycleOwner = lifecycleOwner;
        this.timeUtils = timeUtils;

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
        return new ViewHolder(view,timeUtils, context);
    }

    @Override
    public void onBindViewHolder(@NonNull RvTransactionAdapter.ViewHolder holder, int position) {
        holder.bind(list.get(position),position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ListItemTransactionBinding binding;
        private TimeUtils timeUtils;
        private Context context;

        public ViewHolder(View itemView, TimeUtils timeUtils, Context context) {
            super(itemView);
            binding = ListItemTransactionBinding.bind(itemView);
            this.timeUtils = timeUtils;
            this.context = context;
        }

        public void bind(TransactionTbl item,int position) {

            if ((position % 2) != 0){
                binding.getRoot().setBackgroundColor(ContextCompat.getColor(context,R.color.colorBlueGrey_50));

            }else{
                binding.getRoot().setBackgroundColor(ContextCompat.getColor(context,R.color.colorWhite));

            }
            Date date = timeUtils.convertStringToDate(item.getDate());

            binding.tvCardName.setText(item.getAccount().getAcountNumber());
            binding.tvTransactionDate.setText(timeUtils.getUserFriendlyDateTime(date));
            if (item.getType_code().equals("DEBIT")){
                binding.tvTransactionAmount.setText("- "+item.getAmount());
            }else{
                binding.tvTransactionAmount.setText(""+item.getAmount());
            }
            binding.tvTransactionName.setText(item.getName());
        }
    }
}
