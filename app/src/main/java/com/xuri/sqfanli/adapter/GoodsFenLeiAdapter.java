package com.xuri.sqfanli.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xuri.sqfanli.R;
import com.xuri.sqfanli.bean.ShaiXuanBean;
import com.xuri.sqfanli.bean.Shop;
import com.xuri.sqfanli.bean.ShopType;
import com.xuri.sqfanli.event.OnLoadMoreListener;
import com.xuri.sqfanli.event.OnShaiXuanListener;
import com.xuri.sqfanli.event.OnShaixuanBtnClickListener;
import com.xuri.sqfanli.ui.activity.FenLeiActivity;
import com.xuri.sqfanli.ui.activity.GoodsDetailActivity;
import com.xuri.sqfanli.util.PicassoHelper;
import com.xuri.sqfanli.view.SquareImageView;
import com.xuri.sqfanli.view.common.GridView_;

import org.apache.commons.lang3.StringUtils;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AFeng on 2018/5/14.
 */

public class GoodsFenLeiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    int Type_header = 0, Type_item = 1;
    Context context;

    ArrayList<Shop> shoplistDatas;   //下拉列表里面的商品数据
    ArrayList<ShopType> shopTypes; //按钮组数据

    String jiagebiaoqian = "";
    String shaixuantiaojian = "";
    int headerLayout;

    View headerView;
    String highLightKey;//按钮高亮关键字
    boolean isNoMore = false; //没有更多数据
    private OnShaixuanBtnClickListener mOnShaixuanBtnClickListener;
    private OnLoadMoreListener onLoadMoreListener;
    private OnShaiXuanListener onShaiXuanListener;


    public void setOnShaixuanBtnClickListener(OnShaixuanBtnClickListener mOnShaixuanBtnClickListener) {
        this.mOnShaixuanBtnClickListener = mOnShaixuanBtnClickListener;
    }

    public OnLoadMoreListener getOnLoadMoreListener() {
        return onLoadMoreListener;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }


    public OnShaiXuanListener getOnShaiXuanListener() {
        return onShaiXuanListener;
    }

    public void setOnShaiXuanListener(OnShaiXuanListener onShaiXuanListener) {
        this.onShaiXuanListener = onShaiXuanListener;
    }

    public GoodsFenLeiAdapter(Context context) {
        if (shoplistDatas == null) {
            shoplistDatas = new ArrayList<Shop>();
        }
        this.context = context;
    }

    public void setShoplistDatas(ArrayList<Shop> shops) {
        this.shoplistDatas = shops;
    }

    public void loadMore(ArrayList<Shop> shops) {
        if (shoplistDatas == null) {
            shoplistDatas = new ArrayList<Shop>();
        }
        shoplistDatas.addAll(shops);
        if (shops.size() == 0) {
            isNoMore = true;
        }
    }


    public void setShopTypes(ArrayList<ShopType> shopTypes) {
        this.shopTypes = shopTypes;
    }

    public List<Shop> getShoplistDatas() {
        return shoplistDatas;
    }

    public List<ShopType> getShopTypes() {
        return shopTypes;
    }


    public void setData_headerLayout(int headerLayout) {
        this.headerLayout = headerLayout;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == Type_header) {
            headerView = LayoutInflater.from(context).inflate(headerLayout, parent, false);
            return new Holder_header(headerView);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_shangpin_huaqian, parent, false);
            return new Holder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (shoplistDatas != null && position >= shoplistDatas.size() - 1) {
            //当加载到倒数第x个的时候，开始加载后面的
            if (!isNoMore) {
                onLoadMoreListener.OnLoadMore();
            }
        }


        int type = getItemViewType(position);
        if (type == Type_header) {
            //头部
            final Holder_header h = (Holder_header) holder;
            h.gv.setAdapter(new BaseAdapter() {
                @Override
                public int getCount() {
                    if (shopTypes == null) {
                        return 0;
                    }
                    return shopTypes.size();
                }

                @Override
                public Object getItem(int i) {
                    return i;
                }

                @Override
                public long getItemId(int i) {
                    return i;
                }

                @Override
                public View getView(final int i, View view, ViewGroup viewGroup) {
                    View v = LayoutInflater.from(context).inflate(R.layout.item_fenlei, viewGroup, false);

                    ImageView iv = (ImageView) v.findViewById(R.id.iv);
                    TextView tv = (TextView) v.findViewById(R.id.tv);

                    if (shopTypes == null || shopTypes.size() == 0) return v;

                    String img = shopTypes.get(i).getImg();
                    final String name = shopTypes.get(i).getName();
                    PicassoHelper.showImageByPicasso(context, iv, img);
                    tv.setText(name);

                    v.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //去子分类页面
                            Intent intent = new Intent();
                            intent.setClass(context, FenLeiActivity.class);
                            intent.putExtra("fenlei", name);
                            intent.putExtra("fenleiId", shopTypes.get(i).getId());
                            intent.putExtra("shifouzifenlei", "true");
                            context.startActivity(intent);

                        }
                    });

                    return v;
                }
            });

            if (!TextUtils.isEmpty(highLightKey)) {
                if (highLightKey.equals("zonghe")) {
                    h.iv_jiage.setImageResource(R.drawable.ic_result_disable);
                    h.tv_zonghe.setTextColor(Color.parseColor("#ff6742"));
                    h.tv_xiaoliang.setTextColor(Color.parseColor("#555555"));
                    h.tv_jiage.setTextColor(Color.parseColor("#555555"));
                    h.tv_shaixuan.setTextColor(Color.parseColor("#555555"));
                    highLightKey = "";

                } else if (highLightKey.equals("xiaoliang")) {
                    h.iv_jiage.setImageResource(R.drawable.ic_result_disable);
                    h.tv_zonghe.setTextColor(Color.parseColor("#555555"));
                    h.tv_xiaoliang.setTextColor(Color.parseColor("#ff6742"));
                    h.tv_jiage.setTextColor(Color.parseColor("#555555"));
                    h.tv_shaixuan.setTextColor(Color.parseColor("#555555"));
                    highLightKey = "";

                } else if (highLightKey.equals("jiage-di")) {
                    h.iv_jiage.setImageResource(R.drawable.ic_result_down);
                    h.tv_zonghe.setTextColor(Color.parseColor("#555555"));
                    h.tv_xiaoliang.setTextColor(Color.parseColor("#555555"));
                    h.tv_jiage.setTextColor(Color.parseColor("#ff6742"));
                    h.tv_shaixuan.setTextColor(Color.parseColor("#555555"));
                    highLightKey = "";

                } else if (highLightKey.equals("jiage-gao")) {
                    h.iv_jiage.setImageResource(R.drawable.ic_result_up);
                    h.tv_zonghe.setTextColor(Color.parseColor("#555555"));
                    h.tv_xiaoliang.setTextColor(Color.parseColor("#555555"));
                    h.tv_jiage.setTextColor(Color.parseColor("#ff6742"));
                    h.tv_shaixuan.setTextColor(Color.parseColor("#555555"));
                    highLightKey = "";

                } else if (highLightKey.equals("shaixuan")) {
                    h.iv_jiage.setImageResource(R.drawable.ic_result_disable);
                    h.tv_zonghe.setTextColor(Color.parseColor("#555555"));
                    h.tv_xiaoliang.setTextColor(Color.parseColor("#555555"));
                    h.tv_jiage.setTextColor(Color.parseColor("#555555"));
                    h.tv_shaixuan.setTextColor(Color.parseColor("#ff6742"));
                    highLightKey = "";

                }
            }

            //region 筛选面板点击事件

            //综合
            h.tv_zonghe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onShaiXuanListener.call("zonghe", null);
                    jiagebiaoqian = "";
                    h.iv_jiage.setImageResource(R.drawable.ic_result_disable);
                    h.tv_zonghe.setTextColor(Color.parseColor("#ff6742"));
                    h.tv_xiaoliang.setTextColor(Color.parseColor("#555555"));
                    h.tv_jiage.setTextColor(Color.parseColor("#555555"));
                    h.tv_shaixuan.setTextColor(Color.parseColor("#555555"));
                    if (mOnShaixuanBtnClickListener != null) {
                        mOnShaixuanBtnClickListener.onShaixuanBtnClick(view, "zonghe");
                    }
                }
            });

            //销量
            h.tv_xiaoliang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onShaiXuanListener.call("xiaoliang", null);
                    jiagebiaoqian = "";
                    h.iv_jiage.setImageResource(R.drawable.ic_result_disable);
                    h.tv_zonghe.setTextColor(Color.parseColor("#555555"));
                    h.tv_xiaoliang.setTextColor(Color.parseColor("#ff6742"));
                    h.tv_jiage.setTextColor(Color.parseColor("#555555"));
                    h.tv_shaixuan.setTextColor(Color.parseColor("#555555"));
                    if (mOnShaixuanBtnClickListener != null) {
                        mOnShaixuanBtnClickListener.onShaixuanBtnClick(view, "xiaoliang");
                    }
                }
            });

            //价格
            h.layout_jiage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (jiagebiaoqian.equals("gao")) {
                        onShaiXuanListener.call("jiage-di", null);
                        jiagebiaoqian = "di";
                        h.iv_jiage.setImageResource(R.drawable.ic_result_down);
                        if (mOnShaixuanBtnClickListener != null) {
                            mOnShaixuanBtnClickListener.onShaixuanBtnClick(view, "jiage-di");
                        }
                    } else {
                        onShaiXuanListener.call("jiage-gao", null);
                        jiagebiaoqian = "gao";
                        h.iv_jiage.setImageResource(R.drawable.ic_result_up);
                        if (mOnShaixuanBtnClickListener != null) {
                            mOnShaixuanBtnClickListener.onShaixuanBtnClick(view, "jiage-gao");
                        }
                    }
                    h.tv_zonghe.setTextColor(Color.parseColor("#555555"));
                    h.tv_xiaoliang.setTextColor(Color.parseColor("#555555"));
                    h.tv_jiage.setTextColor(Color.parseColor("#ff6742"));
                    h.tv_shaixuan.setTextColor(Color.parseColor("#555555"));
                }

            });

            //endregion

            //region 筛选弹出层 点击事件
            h.layout_shaixuan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    View v = LayoutInflater.from(context).inflate(R.layout.dialog_shaixuan, null, false);
                    builder.setView(v);
                    final AlertDialog dialog = builder.create();
                    dialog.show();

                    final TextView tv_shangjia_quanbu = (TextView) v.findViewById(R.id.tv_shangjia_quanbu);
                    final TextView tv_shangjia_tianmao = (TextView) v.findViewById(R.id.tv_shangjia_tianmao);
                    final TextView tv_zhekou1 = (TextView) v.findViewById(R.id.tv_zhekou1);
                    final TextView tv_zhekou2 = (TextView) v.findViewById(R.id.tv_zhekou2);
                    final TextView tv_zhekou3 = (TextView) v.findViewById(R.id.tv_zhekou3);
                    TextView tv_qingkong = (TextView) v.findViewById(R.id.tv_qingkong);
                    TextView tv_queren = (TextView) v.findViewById(R.id.tv_queren);
                    final EditText et_zuidijia = (EditText) v.findViewById(R.id.et_zuidijia);
                    final EditText et_zuigaojia = (EditText) v.findViewById(R.id.et_zuigaojia);

                    //初始化
                    if (shaixuantiaojian.contains("shangjia_tianmao")) {
                        tv_shangjia_tianmao.setBackgroundResource(R.drawable.shape_shaixuan);
                        tv_shangjia_tianmao.setTextColor(Color.parseColor("#ff6742"));
                    } else {
                        tv_shangjia_quanbu.setBackgroundResource(R.drawable.shape_shaixuan);
                        tv_shangjia_quanbu.setTextColor(Color.parseColor("#ff6742"));
                    }
                    if (shaixuantiaojian.contains("zhekou1")) {
                        tv_zhekou1.setBackgroundResource(R.drawable.shape_shaixuan);
                        tv_zhekou1.setTextColor(Color.parseColor("#ff6742"));
                    }
                    if (shaixuantiaojian.contains("zhekou2")) {
                        tv_zhekou2.setBackgroundResource(R.drawable.shape_shaixuan);
                        tv_zhekou2.setTextColor(Color.parseColor("#ff6742"));
                    }
                    if (shaixuantiaojian.contains("zhekou3")) {
                        tv_zhekou3.setBackgroundResource(R.drawable.shape_shaixuan);
                        tv_zhekou3.setTextColor(Color.parseColor("#ff6742"));
                    }
                    String zuidijia = StringUtils.substringBetween(shaixuantiaojian, "zuidijia", ",");
                    String zuigaojia = StringUtils.substringBetween(shaixuantiaojian, "zuigaojia", ",");
                    et_zuidijia.setText(zuidijia);
                    et_zuigaojia.setText(zuigaojia);

                    shaixuantiaojian = "";
                    //点击全部商家
                    tv_shangjia_quanbu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            shaixuantiaojian = shaixuantiaojian + ",shangjia_quanbu,";
                            shaixuantiaojian = shaixuantiaojian.replace(",shangjia_tianmao,", "");

                            tv_shangjia_quanbu.setBackgroundResource(R.drawable.shape_shaixuan);
                            tv_shangjia_quanbu.setTextColor(Color.parseColor("#ff6742"));

                            tv_shangjia_tianmao.setBackgroundResource(R.drawable.shape_shaixuan_weixuanzhong);
                            tv_shangjia_tianmao.setTextColor(Color.parseColor("#555555"));
                        }
                    });

                    //点击天猫商家
                    tv_shangjia_tianmao.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            shaixuantiaojian = shaixuantiaojian + ",shangjia_tianmao,";
                            shaixuantiaojian = shaixuantiaojian.replace(",shangjia_quanbu,", "");

                            tv_shangjia_quanbu.setBackgroundResource(R.drawable.shape_shaixuan_weixuanzhong);
                            tv_shangjia_quanbu.setTextColor(Color.parseColor("#555555"));

                            tv_shangjia_tianmao.setBackgroundResource(R.drawable.shape_shaixuan);
                            tv_shangjia_tianmao.setTextColor(Color.parseColor("#ff6742"));
                        }
                    });
                    //折扣1
                    tv_zhekou1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            shaixuantiaojian = shaixuantiaojian + ",zhekou1,";
                            shaixuantiaojian = shaixuantiaojian.replace(",zhekou2,", "");
                            shaixuantiaojian = shaixuantiaojian.replace(",zhekou3,", "");

                            tv_zhekou1.setBackgroundResource(R.drawable.shape_shaixuan);
                            tv_zhekou1.setTextColor(Color.parseColor("#ff6742"));
                            tv_zhekou2.setBackgroundResource(R.drawable.shape_shaixuan_weixuanzhong);
                            tv_zhekou2.setTextColor(Color.parseColor("#555555"));
                            tv_zhekou3.setBackgroundResource(R.drawable.shape_shaixuan_weixuanzhong);
                            tv_zhekou3.setTextColor(Color.parseColor("#555555"));
                        }
                    });
                    //折扣2
                    tv_zhekou2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            shaixuantiaojian = shaixuantiaojian + ",zhekou2,";
                            shaixuantiaojian = shaixuantiaojian.replace(",zhekou1,", "");
                            shaixuantiaojian = shaixuantiaojian.replace(",zhekou3,", "");
                            tv_zhekou1.setBackgroundResource(R.drawable.shape_shaixuan_weixuanzhong);
                            tv_zhekou1.setTextColor(Color.parseColor("#555555"));
                            tv_zhekou2.setBackgroundResource(R.drawable.shape_shaixuan);
                            tv_zhekou2.setTextColor(Color.parseColor("#ff6742"));
                            tv_zhekou3.setBackgroundResource(R.drawable.shape_shaixuan_weixuanzhong);
                            tv_zhekou3.setTextColor(Color.parseColor("#555555"));
                        }
                    });
                    //折扣3
                    tv_zhekou3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            shaixuantiaojian = shaixuantiaojian + ",zhekou3,";
                            shaixuantiaojian = shaixuantiaojian.replace(",zhekou1,", "");
                            shaixuantiaojian = shaixuantiaojian.replace(",zhekou2,", "");
                            tv_zhekou1.setBackgroundResource(R.drawable.shape_shaixuan_weixuanzhong);
                            tv_zhekou1.setTextColor(Color.parseColor("#555555"));
                            tv_zhekou2.setBackgroundResource(R.drawable.shape_shaixuan_weixuanzhong);
                            tv_zhekou2.setTextColor(Color.parseColor("#555555"));
                            tv_zhekou3.setBackgroundResource(R.drawable.shape_shaixuan);
                            tv_zhekou3.setTextColor(Color.parseColor("#ff6742"));
                        }
                    });
                    //清空
                    tv_qingkong.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            shaixuantiaojian = "";

                            tv_shangjia_quanbu.setBackgroundResource(R.drawable.shape_shaixuan);
                            tv_shangjia_quanbu.setTextColor(Color.parseColor("#ff6742"));
                            tv_shangjia_tianmao.setBackgroundResource(R.drawable.shape_shaixuan_weixuanzhong);
                            tv_shangjia_tianmao.setTextColor(Color.parseColor("#555555"));

                            tv_zhekou1.setBackgroundResource(R.drawable.shape_shaixuan_weixuanzhong);
                            tv_zhekou1.setTextColor(Color.parseColor("#555555"));
                            tv_zhekou2.setBackgroundResource(R.drawable.shape_shaixuan_weixuanzhong);
                            tv_zhekou2.setTextColor(Color.parseColor("#555555"));
                            tv_zhekou3.setBackgroundResource(R.drawable.shape_shaixuan_weixuanzhong);
                            tv_zhekou3.setTextColor(Color.parseColor("#555555"));

                            et_zuidijia.setText("");
                            et_zuigaojia.setText("");

                        }
                    });
                    //确认
                    tv_queren.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            shaixuantiaojian = shaixuantiaojian + ",zuidijia" + et_zuidijia.getText() + ",";
                            shaixuantiaojian = shaixuantiaojian + ",zuigaojia" + et_zuigaojia.getText() + ",";

                            String zuidijia = String.valueOf(et_zuidijia.getText());
                            String zuigaojia = String.valueOf(et_zuigaojia.getText());
                            ShaiXuanBean data = new ShaiXuanBean();
                            data.setZuidijia(zuidijia);
                            data.setZuigaojia(zuigaojia);
                            onShaiXuanListener.call("shaixuantiaojian", data);
                            dialog.cancel();
                            h.iv_jiage.setImageResource(R.drawable.ic_result_disable);
                            h.tv_zonghe.setTextColor(Color.parseColor("#555555"));
                            h.tv_xiaoliang.setTextColor(Color.parseColor("#555555"));
                            h.tv_jiage.setTextColor(Color.parseColor("#555555"));
                            h.tv_shaixuan.setTextColor(Color.parseColor("#ff6742"));
                            if (mOnShaixuanBtnClickListener != null) {
                                mOnShaixuanBtnClickListener.onShaixuanBtnClick(view, "shaixuan");
                            }

                        }
                    });
                }
            });

            //endregion

        } else {
            //商品
            if (shoplistDatas == null || shoplistDatas.size() == 0) return;

            Shop shop = shoplistDatas.get(position - 1);
            final Holder h = (Holder) holder;

            x.image().bind(h.iv, shop.getItempic());

            h.tv_title.setText(shop.getItemtitle());
            h.tv_jiage.setText(shop.getItemendprice());

            //是否包邮，1为包邮，0为不包邮
            if (("1").equals(shop.getPostFree())) {
                h.baoyouIconIv.setVisibility(View.VISIBLE);
            } else {
                h.baoyouIconIv.setVisibility(View.GONE);
            }

            if (shop.getShoptype().equals("B")) {
                //天猫
                h.tv_yuanjia.setText("天猫价:￥" + shop.getItemprice());
            } else {
                h.tv_yuanjia.setText("淘宝价:￥" + shop.getItemprice());
            }
            h.tv_yuanjia.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            h.tv_goumaishuliang.setText("月销" + shop.getItemsale() + "件");
            h.tv_youquanjine.setText(shop.getCouponmoney() + "元券");
            final String url = shop.getCouponurl();

            h.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent();
                    intent.setClass(context, GoodsDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("jsonText", shoplistDatas.get(position - 1));
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });

        }

    }


    @Override
    public int getItemCount() {
        if (shoplistDatas == null) {
            return 1;
        } else {
            return shoplistDatas.size() + 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return Type_header;
        } else {
            return Type_item;
        }
    }

    class Holder extends RecyclerView.ViewHolder {

        SquareImageView iv;
        TextView tv_jiage, tv_yuanjia, tv_title, tv_goumaishuliang, tv_youquanjine;
        ImageView baoyouIconIv;

        public Holder(View itemView) {
            super(itemView);
            iv = (SquareImageView) itemView.findViewById(R.id.iv);
            baoyouIconIv = itemView.findViewById(R.id.baoyou_icon_iv);
            tv_jiage = (TextView) itemView.findViewById(R.id.tv_jiage);
            tv_yuanjia = (TextView) itemView.findViewById(R.id.tv_yuanjia);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_youquanjine = (TextView) itemView.findViewById(R.id.tv_youquanjine);
            tv_goumaishuliang = (TextView) itemView.findViewById(R.id.tv_goumaishuliang);

        }
    }

    class Holder_header extends RecyclerView.ViewHolder {
        ImageView iv_jiage;
        LinearLayout layout_jiage, layout_shaixuan;
        TextView tv_zonghe, tv_xiaoliang, tv_jiage, tv_shaixuan;
        GridView_ gv;

        public Holder_header(View itemView) {
            super(itemView);

            layout_jiage = (LinearLayout) itemView.findViewById(R.id.layout_jiage);
            layout_shaixuan = (LinearLayout) itemView.findViewById(R.id.layout_shaixuan);
            iv_jiage = (ImageView) itemView.findViewById(R.id.iv_jiage);
            tv_zonghe = (TextView) itemView.findViewById(R.id.tv_zonghe);
            tv_xiaoliang = (TextView) itemView.findViewById(R.id.tv_xiaoliang);
            tv_jiage = (TextView) itemView.findViewById(R.id.tv_jiage);
            tv_shaixuan = (TextView) itemView.findViewById(R.id.tv_shaixuan);

            gv = (GridView_) itemView.findViewById(R.id.gv);

        }
    }

    public View getHeaderView() {
        return headerView;
    }

    public void setHighLightKey(String highLightKey) {
        this.highLightKey = highLightKey;
    }

}
