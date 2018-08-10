package rezkyaulia.com.bamms_project.ui;

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

import rezkyaulia.com.bamms_project.R;
import rezkyaulia.com.bamms_project.data.database.entity.BankAccountTbl;
import rezkyaulia.com.bamms_project.databinding.ListItemCardAdminBinding;
import rezkyaulia.com.bamms_project.databinding.ListItemCardBinding;
import rezkyaulia.com.bamms_project.ui.main.MainViewModel;
import rezkyaulia.com.bamms_project.util.ParameterConstant;
import timber.log.Timber;

public class RvAccountManagerAdapter extends RecyclerView.Adapter<RvAccountManagerAdapter.ViewHolder> {


    private MainAdminViewModel viewModel;
    private ParameterConstant constant;
    private List<BankAccountTbl> list = new ArrayList<>();


    public RvAccountManagerAdapter(MainAdminViewModel viewModel, LifecycleOwner lifecycleOwner, ParameterConstant constant) {
        this.viewModel = viewModel;
        this.constant = constant;
        this.viewModel.getAccountTblsLD().observe(lifecycleOwner, new Observer<List<BankAccountTbl>>() {
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
    public RvAccountManagerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_card_admin, parent, false);
        return new ViewHolder(view, this.viewModel,constant);    }

    @Override
    public void onBindViewHolder(@NonNull RvAccountManagerAdapter.ViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ListItemCardAdminBinding binding;
        private MainAdminViewModel viewModel;
        private ParameterConstant constant;
        private BankAccountTbl bankAccountTbl;

        public ViewHolder(View itemView, MainAdminViewModel viewModel, ParameterConstant constant) {
            super(itemView);
            binding = ListItemCardAdminBinding.bind(itemView);
            this.viewModel = viewModel;
            this.constant = constant;


        }

        public void bind(BankAccountTbl bankAccountTbl) {
            this.bankAccountTbl = bankAccountTbl;

            binding.tvCardName.setText("Account number : "+bankAccountTbl.getAcountNumber());
            binding.tvBalance.setText("Balance : "+bankAccountTbl.getAccountBalance()+"");
            binding.tvPhone.setText("Phone : " +bankAccountTbl.getUserTbl().getPhone());
            binding.tvAddress.setText("Address : " + bankAccountTbl.getUserTbl().getAddress());
            binding.tvName.setText("Person name : " + bankAccountTbl.getUserTbl().getName());
        }
    }
}
