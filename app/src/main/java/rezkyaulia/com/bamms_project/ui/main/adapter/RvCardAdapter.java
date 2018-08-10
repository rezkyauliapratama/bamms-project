package rezkyaulia.com.bamms_project.ui.main.adapter;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import rezkyaulia.com.bamms_project.R;
import rezkyaulia.com.bamms_project.data.database.entity.BankAccountTbl;
import rezkyaulia.com.bamms_project.databinding.ListItemCardBinding;
import rezkyaulia.com.bamms_project.ui.main.MainViewModel;
import rezkyaulia.com.bamms_project.util.ParameterConstant;
import timber.log.Timber;

public class RvCardAdapter extends RecyclerView.Adapter<RvCardAdapter.ViewHolder> {


    private MainViewModel viewModel;
    private ParameterConstant constant;
    private List<BankAccountTbl> list = new ArrayList<>();


    public RvCardAdapter(MainViewModel viewModel, LifecycleOwner lifecycleOwner, ParameterConstant constant) {
        this.viewModel = viewModel;
        this.constant = constant;
        this.viewModel.getBankAccountsLD().observe(lifecycleOwner, new Observer<List<BankAccountTbl>>() {
            @Override
            public void onChanged(@Nullable List<BankAccountTbl> bankAccountTbls) {
                Timber.e("list adapter : "+new Gson().toJson(bankAccountTbls));
                list.clear();
                list.addAll(Objects.requireNonNull(bankAccountTbls));
                notifyDataSetChanged();
            }
        });
    }

    @NonNull
    @Override
    public RvCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_card, parent, false);
        return new ViewHolder(view, this.viewModel,constant);    }

    @Override
    public void onBindViewHolder(@NonNull RvCardAdapter.ViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ListItemCardBinding binding;
        private MainViewModel viewModel;
        private ParameterConstant constant;
        private BankAccountTbl bankAccountTbl;

        public ViewHolder(View itemView, MainViewModel viewModel, ParameterConstant constant) {
            super(itemView);
            binding = ListItemCardBinding.bind(itemView);
            this.viewModel = viewModel;
            this.constant = constant;

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewModel.startActivity(bankAccountTbl);
                }
            });
        }

        public void bind(BankAccountTbl bankAccountTbl) {
            this.bankAccountTbl = bankAccountTbl;
            if (bankAccountTbl.getType_code().equals(constant.MASTERCARD)){
                binding.ivIcon.setImageResource(R.drawable.ic_mastercard);
            }else{
                binding.ivIcon.setImageResource(R.drawable.ic_visa);

            }
            binding.tvCardName.setText(bankAccountTbl.getAcountNumber());
            binding.tvCardStaths.setText(R.string.active);
            binding.tvBalance.setText(bankAccountTbl.getAccountBalance()+"");
        }
    }
}
