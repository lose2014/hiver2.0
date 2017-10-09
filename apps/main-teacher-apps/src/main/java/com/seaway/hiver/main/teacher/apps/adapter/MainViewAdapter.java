package com.seaway.hiver.main.teacher.apps.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.seaway.android.sdk.logger.Logger;
import com.seaway.hiver.main.teacher.apps.R;
import com.seaway.hiver.main.teacher.apps.adapter.holder.BannerViewHolder;
import com.seaway.hiver.model.common.data.vo.QueryAdvertListVo;


import java.util.List;

/**
 * Created by Leo.Chang on 2017/5/13.
 */
public class MainViewAdapter extends RecyclerView.Adapter {
    private View.OnClickListener onClickListener;
    private AdapterView.OnItemClickListener onItemClickListener;
//    private FinancialAndRegInfo items;
    private QueryAdvertListVo advertInfo;

    public MainViewAdapter(View.OnClickListener onClickListener, AdapterView.OnItemClickListener onItemClickListener) {
        this.onClickListener = onClickListener;
        this.onItemClickListener = onItemClickListener;
    }

    public void setAdvertInfo(QueryAdvertListVo advertInfo) {
        this.advertInfo = advertInfo;
    }

//    public void setItems(FinancialAndRegInfo items) {
//        this.items = items;
//    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        RecyclerView.ViewHolder mViewHolder = null;
//        switch (viewType) {
//            case 0: {
//                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.financial_view_menu, parent, false);
//                mViewHolder = new MenuViewHolder(v, onClickListener);
//            }
//            break;
//            case 1: {
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.bank_view_banner, parent, false);
                mViewHolder = new BannerViewHolder(v, onClickListener);
//            }
//            break;
//            case 2: {
//                // 理财产品、定期产品标题
//                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.financial_view_title, parent, false);
//                mViewHolder = new TitleViewHolder(v);
//            }
//            break;
//            case 3: {
//                // 理财产品信息
//                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.financial_view_financial_item, parent, false);
//                mViewHolder = new FinancialItemViewHolder(v,onClickListener);
//            }
//            break;
//            case 4: {
//                // 定期产品信息
//                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.financial_view_reg_item, parent, false);
//                mViewHolder = new RegItemViewHolder(v,onClickListener);
//            }
//            break;
//        }

        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List payloads) {
        Logger.i("onBindViewHolder(RecyclerView.ViewHolder holder, int position, List payloads)" + position + "===payloads is null ? " + (null == payloads || payloads.isEmpty()));
        if (null == payloads || payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            switch (getItemViewType(position)) {
                case 0 : {
                    // 菜单栏
//                    MenuViewHolder viewHolder = (MenuViewHolder) holder;
//                    for (Object obj : payloads) {
//                        if (obj instanceof Integer) {
//                            viewHolder.updateView((int) obj);
//                        }
//                    }
                }
                break;
            }

        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        switch (getItemViewType(position)) {
//            case 1: {
                BannerViewHolder viewHolder = (BannerViewHolder) holder;
                viewHolder.updateView(advertInfo);
//            }
//            break;
//            case 2: {
//                // 理财产品、定期产品标题
//                TitleViewHolder viewHolder = (TitleViewHolder) holder;
//                viewHolder.updateView(position);
//            }
//            break;
//            case 3: {
//                // 理财产品信息
//                FinancialItemViewHolder viewHolder = (FinancialItemViewHolder) holder;
//                viewHolder.updateView((null == items || null == items.getFinancialInfo()) ? null : items.getFinancialInfo().get(position - 3));
//            }
//            break;
//            case 4: {
//                // 定期产品信息
//                RegItemViewHolder viewHolder = (RegItemViewHolder) holder;
//                viewHolder.updateView((null == items  || null == items.getRegInfo()) ? null : items.getRegInfo().get(position - 4 - items.getFinancialCount()));
//            }
//            break;
//        }
    }

    @Override
    public int getItemCount() {
        int count = 1;

//        if (null != items) {
//            count += items.getFinancialCount() + items.getRegCount();
//        } else {
//            count += 2;
//        }

        return count;
    }

    @Override
    public int getItemViewType(int position) {
//        int financialCount = null == items ? 1 : items.getFinancialCount();
//
//        if (position <= 1) {
//            // 菜单栏及轮播广告
//            return position;
//        } else if (position == 2) {
//            // 理财产品标题
//            return 2;
//        } else if (position > 2 && position < 3 + financialCount) {
//            // 理财产品
//            return 3;
//        } else if (position == 3 + financialCount) {
//            // 定期产品标题
//            return 2;
//        } else {
//            // 定期产品
            return 1;
//        }
    }
}
