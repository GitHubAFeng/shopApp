package com.xuri.sqfanli.bean;

import java.util.List;

/**
 * Created by AFeng on 2018/4/25.
 */

public class Adv {


    /**
     * appversion : {"downUrl":"http://7xspkm.com1.z0.glb.clouddn.com/app-releasectaobao.apk","id":1,"mustup":"1","upDes":"升级描述","updateTime":"2017-12-12","version":0}
     * mainAdvList : [{"advImg":"http://file.17gwx.com/sqkb/element/2018/04/20/288165ad9d3a957948.jpg","advPackName":null,"advType":"3","createTime":"2018-04-24 18:05:05.943","display":"0","gatherUrl":"http://m.sqkb.com/subject/coupon?activityId=2272&index=0&page=0&pageSize=50","id":265,"rid":null,"sex":"1","shopNum":null,"title":null,"url":""},{"advImg":"http://file.17gwx.com/sqkb/element/2018/04/24/838385ade0e92d2116.jpg","advPackName":null,"advType":"3","createTime":"2018-04-24 18:05:06.33","display":"0","gatherUrl":"http://www.sqkb.com/topic/50727/","id":266,"rid":null,"sex":"1","shopNum":null,"title":null,"url":""},{"advImg":"http://file.17gwx.com/sqkb/element/2018/04/23/313645addb84a0d54c.jpg","advPackName":null,"advType":"3","createTime":"2018-04-24 18:05:06.84","display":"0","gatherUrl":"http://www.sqkb.com/topic/50626/","id":267,"rid":null,"sex":"1","shopNum":null,"title":null,"url":""},{"advImg":"http://file.17gwx.com/sqkb/element/2018/04/13/372425ad08e19d2b1c.jpg","advPackName":"","advType":"3","createTime":"2018-04-18 15:52:51.53","display":"1","gatherUrl":"http://m.sqkb.com/subject/2272?use_wk=1","id":210,"rid":"5","sex":"3","shopNum":null,"title":"123","url":"2"}]
     */

    private AppversionBean appversion;
    private List<MainAdvListBean> mainAdvList;

    public AppversionBean getAppversion() {
        return appversion;
    }

    public void setAppversion(AppversionBean appversion) {
        this.appversion = appversion;
    }

    public List<MainAdvListBean> getMainAdvList() {
        return mainAdvList;
    }

    public void setMainAdvList(List<MainAdvListBean> mainAdvList) {
        this.mainAdvList = mainAdvList;
    }

    public static class AppversionBean {
        /**
         * downUrl : http://7xspkm.com1.z0.glb.clouddn.com/app-releasectaobao.apk
         * id : 1
         * mustup : 1
         * upDes : 升级描述
         * updateTime : 2017-12-12
         * version : 0
         */

        private String downUrl;
        private int id;
        private String mustup;
        private String upDes;
        private String updateTime;
        private int version;

        public String getDownUrl() {
            return downUrl;
        }

        public void setDownUrl(String downUrl) {
            this.downUrl = downUrl;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMustup() {
            return mustup;
        }

        public void setMustup(String mustup) {
            this.mustup = mustup;
        }

        public String getUpDes() {
            return upDes;
        }

        public void setUpDes(String upDes) {
            this.upDes = upDes;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }
    }

    public static class MainAdvListBean {
        /**
         * advImg : http://file.17gwx.com/sqkb/element/2018/04/20/288165ad9d3a957948.jpg
         * advPackName : null
         * advType : 3
         * createTime : 2018-04-24 18:05:05.943
         * display : 0
         * gatherUrl : http://m.sqkb.com/subject/coupon?activityId=2272&index=0&page=0&pageSize=50
         * id : 265
         * rid : null
         * sex : 1
         * shopNum : null
         * title : null
         * url :
         */

        private String advImg;
        private Object advPackName;
        private String advType;
        private String createTime;
        private String display;
        private String gatherUrl;
        private int id;
        private Object rid;
        private String sex;
        private Object shopNum;
        private Object title;
        private String url;

        public String getAdvImg() {
            return advImg;
        }

        public void setAdvImg(String advImg) {
            this.advImg = advImg;
        }

        public Object getAdvPackName() {
            return advPackName;
        }

        public void setAdvPackName(Object advPackName) {
            this.advPackName = advPackName;
        }

        public String getAdvType() {
            return advType;
        }

        public void setAdvType(String advType) {
            this.advType = advType;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getDisplay() {
            return display;
        }

        public void setDisplay(String display) {
            this.display = display;
        }

        public String getGatherUrl() {
            return gatherUrl;
        }

        public void setGatherUrl(String gatherUrl) {
            this.gatherUrl = gatherUrl;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getRid() {
            return rid;
        }

        public void setRid(Object rid) {
            this.rid = rid;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public Object getShopNum() {
            return shopNum;
        }

        public void setShopNum(Object shopNum) {
            this.shopNum = shopNum;
        }

        public Object getTitle() {
            return title;
        }

        public void setTitle(Object title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
