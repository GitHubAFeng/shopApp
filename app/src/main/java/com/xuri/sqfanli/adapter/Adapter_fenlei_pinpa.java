package com.xuri.sqfanli.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xuri.sqfanli.R;
import com.xuri.sqfanli.bean.Brank;
import com.xuri.sqfanli.bean.Shop;
import com.xuri.sqfanli.ui.activity.A_pinpa_shop_list;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public class Adapter_fenlei_pinpa extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public List<HashMap<String, Object>> ja;
    Context context;

    public Adapter_fenlei_pinpa(Context context) {
        this.context = context;
    }

    public void setData(List<HashMap<String, Object>> ja) {
        this.ja = ja;
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == 1) {
            view = LayoutInflater.from(context).inflate(R.layout.adapter_fenlei_pinpa_2, parent, false);

            return new Holder_pinpa(view);
        } else if (viewType == 2) {
            view = LayoutInflater.from(context).inflate(R.layout.fenlei_new_head, parent, false);

            return new Holder_head(view);

        } else {
            view = LayoutInflater.from(context).inflate(R.layout.adapter_fenlei_pinpa_1, parent, false);
            return new Holder(view);
        }

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final HashMap<String, Object> map = ja.get(position);
        int itemType = getItemViewType(position);
        switch (itemType) {
            case 1://品牌 横向3个排列产品
                Holder_pinpa mHolder_pinpa = (Holder_pinpa) holder;
                mHolder_pinpa.top_title1.setText(map.get("title") + "");
                mHolder_pinpa.top_title2.setText(map.get("subTitle") + "");

                List<Shop> mb_shop = (List<Shop>) map.get("obj");
                for (int z = 0; z < mb_shop.size(); z++) {
                    Shop x_Shop = mb_shop.get(z);
                    x.image().bind(mHolder_pinpa.srcArr[z], x_Shop.getItempic(), options);
                    mHolder_pinpa.ljArr[z].setText("领券减" + x_Shop.getCouponmoney() + "元");
                    mHolder_pinpa.nameArr[z].setText(x_Shop.getItemtitle());
                    mHolder_pinpa.priceArr[z].setText("￥" + x_Shop.getItemprice());
                }

                mHolder_pinpa.p.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("wd", map.toString());
                        Intent in = new Intent(context, A_pinpa_shop_list.class);
                        in.putExtra("id", "" + map.get("id"));
                        in.putExtra("activityId", "" + map.get("activityId"));
                        in.putExtra("photo", "" + map.get("photo"));
                        in.putExtra("title", "" + map.get("title") + "");
                        in.putExtra("child_title", "" + map.get("subTitle") + "");

                        context.startActivity(in);
                    }
                });


                break;
            case 2://头部
                final Holder_head mHolder_head = (Holder_head) holder;

                List<Brank> lll = (List<Brank>) map.get("obj");
                mHolder_head.lm.setSpanCount(lll.size() / 2);
                mHolder_head.adapter.setData(lll);

//                mHolder_head.rv.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        mHolder_head.rv.smoothScrollToPosition(0);
//                    }
//                },500);


                break;
            case 3:


                final Shop mshop = (Shop) map.get("obj");
                Holder mHolder = (Holder) holder;
                x.image().bind(mHolder.src, mshop.getItempic(), options);

                Log.i("", "----------------xxx@@@" + mshop.getItemtitle());

                mHolder.title.setText(mshop.getItemtitle());

                mHolder.mid.setText(mshop.getItemshorttitle());
                mHolder.lijian.setText("领券减" + mshop.getCouponmoney() + "元");
                mHolder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mcallBack != null) {
                            Gson gson = new Gson();
                            String obj = gson.toJson(mshop);

                            mcallBack.id(obj);
                        }

                    }
                });
                mHolder.price.setText("￥" + mshop.getItemprice());


                break;
        }

    }

    callBack mcallBack;

    public void setcallBack(callBack mcallBack) {
        this.mcallBack = mcallBack;
    }

    public interface callBack {
        public void id(String id);
    }

    ImageOptions options = new ImageOptions.Builder()
            //设置使用缓存
            .setUseMemCache(true)
            //设置显示圆形图片
            .build();

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 2;
        }

        String type = (String) ja.get(position).get("type");
        if (type.equals("1"))//横的
        {
            return 1;
        } else {
            return 3;
        }

    }

    @Override
    public int getItemCount() {
        if (ja == null) {
            return 0;
        }
        return ja.size();
    }


    class Holder_pinpa extends RecyclerView.ViewHolder {

        ImageView[] srcArr;
        TextView[] ljArr, nameArr, priceArr;

        LinearLayout p;
        TextView top_title1, top_title2;

        public Holder_pinpa(View itemView) {
            super(itemView);
            srcArr = new ImageView[3];
            ljArr = new TextView[3];
            nameArr = new TextView[3];
            priceArr = new TextView[3];
            p = itemView.findViewById(R.id.p);

            top_title1 = itemView.findViewById(R.id.top_title1);
            top_title2 = itemView.findViewById(R.id.top_title2);
            for (int i = 0; i < 3; i++) {

                int un = i + 1;
                int src = context.getResources().getIdentifier("src" + un, "id", context.getPackageName());
                int lj = context.getResources().getIdentifier("lj" + un, "id", context.getPackageName());
                int name = context.getResources().getIdentifier("name" + un, "id", context.getPackageName());
                int price = context.getResources().getIdentifier("price" + un, "id", context.getPackageName());

                srcArr[i] = itemView.findViewById(src);
                ljArr[i] = itemView.findViewById(lj);
                nameArr[i] = itemView.findViewById(name);
                priceArr[i] = itemView.findViewById(price);
            }


        }
    }


    class Holder extends RecyclerView.ViewHolder {

        ImageView src;
        LinearLayout view;
        TextView title, mid, lijian, price;

        public Holder(View itemView) {
            super(itemView);
            src = itemView.findViewById(R.id.src);
            view = itemView.findViewById(R.id.view);
            title = itemView.findViewById(R.id.title);
            mid = itemView.findViewById(R.id.mid);
            lijian = itemView.findViewById(R.id.lijian);
            price = itemView.findViewById(R.id.price);


        }
    }

    class Holder_head extends RecyclerView.ViewHolder {


        ImageView hs;
        RelativeLayout lll;
        GridLayoutManager lm;
        RecyclerView rv;
        Adapter_fenlei_pinpa_top adapter;

        public Holder_head(View itemView) {
            super(itemView);
            lll = itemView.findViewById(R.id.llll);
            hs = itemView.findViewById(R.id.hs_view);
            WindowManager wm = (WindowManager) context

                    .getSystemService(Context.WINDOW_SERVICE);
            int width = wm.getDefaultDisplay().getWidth();
            int height = wm.getDefaultDisplay().getHeight();

            double f1 = new BigDecimal((float) 480 / width).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

            int height_x = (int) (260 / f1);
            hs.getLayoutParams().height = height_x;
            rv = itemView.findViewById(R.id.rv);
            adapter = new Adapter_fenlei_pinpa_top(context);

            lm = new GridLayoutManager(context, 6) {
            };

            rv.setLayoutManager(lm);
            rv.setAdapter(adapter);
        }
    }

}
