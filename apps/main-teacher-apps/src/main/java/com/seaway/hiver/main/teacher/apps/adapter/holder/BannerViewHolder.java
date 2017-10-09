package com.seaway.hiver.main.teacher.apps.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.seaway.android.view.banner.UIBanner;
import com.seaway.hiver.main.teacher.apps.R;
import com.seaway.hiver.model.common.data.vo.QueryAdvertListVo;

/**
 * 轮播图
 * Created by Leo.Chang on 2017/5/11.
 */
public class BannerViewHolder extends RecyclerView.ViewHolder {

    private UIBanner banner;

    public BannerViewHolder(View itemView ,View.OnClickListener onClickListener) {
        super(itemView);

        banner = (UIBanner) itemView.findViewById(R.id.bank_portal_banner_view);
        banner.setOnBannerClickListener(onClickListener);
    }

    public void updateView(QueryAdvertListVo vo) {
        if (null == vo) {
            banner.notifyDataChanged(null);
        } else {
            banner.setIsLoop("0".equals(vo.getLoopView()));
            banner.setDuration(Integer.parseInt(vo.getViewTime()));
            banner.notifyDataChanged(vo.getAdvertInfos());
        }
    }
}