package com.seaway.hiver.main.teacher.apps.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.seaway.android.view.banner.UIBanner;
import com.seaway.hiver.main.teacher.apps.R;
import com.seaway.hiver.model.common.data.vo.AdvertVo;
import com.seaway.hiver.model.common.data.vo.QueryAdvertListVo;

import java.util.ArrayList;
import java.util.List;

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
           List<AdvertVo> list=new ArrayList<>();
            AdvertVo advertVo =new AdvertVo();
            advertVo.setContent("1111");
            advertVo.setId("1");
            advertVo.setIconUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1508208411&di=f5256dc29ef6b48ec5f4d28e629ba2ae&imgtype=jpg&er=1&src=http%3A%2F%2F5.66825.com%2Fdownload%2Fpic%2F000%2F330%2Fcf0f6d186a2f14b9dbf1df36b271ae8c.jpg");
            advertVo.setUrlPath("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1508208411&di=f5256dc29ef6b48ec5f4d28e629ba2ae&imgtype=jpg&er=1&src=http%3A%2F%2F5.66825.com%2Fdownload%2Fpic%2F000%2F330%2Fcf0f6d186a2f14b9dbf1df36b271ae8c.jpg");
            list.add(advertVo);
            AdvertVo advertVo2 =new AdvertVo();
            advertVo2.setContent("2222");
            advertVo2.setId("2");
            advertVo2.setIconUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1507613691076&di=66a45ea1454ab7f11fdcf549b817bc93&imgtype=0&src=http%3A%2F%2Fimgm.cnmo-img.com.cn%2Fappimg%2Fscreenpic%2Fmiddle%2F779%2F778678.jpg");
            advertVo2.setUrlPath("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1507613691076&di=66a45ea1454ab7f11fdcf549b817bc93&imgtype=0&src=http%3A%2F%2Fimgm.cnmo-img.com.cn%2Fappimg%2Fscreenpic%2Fmiddle%2F779%2F778678.jpg");
            list.add(advertVo2);
            vo =new QueryAdvertListVo();
            vo.setAdvertInfos(list);
            banner.setIsLoop("0".equals(vo.getLoopView()));
            banner.setDuration(Integer.parseInt(vo.getViewTime()));
            banner.notifyDataChanged(vo.getAdvertInfos());
//            banner.notifyDataChanged(null);
        } else {
            banner.setIsLoop("0".equals(vo.getLoopView()));
            banner.setDuration(Integer.parseInt(vo.getViewTime()));
            banner.notifyDataChanged(vo.getAdvertInfos());
        }
    }
}