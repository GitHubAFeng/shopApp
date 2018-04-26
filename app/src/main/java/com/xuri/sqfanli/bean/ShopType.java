package com.xuri.sqfanli.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/25.
 */

public class ShopType {


    /**
     * caiTag : null
     * cid : null
     * classify : null
     * createTime : 2018-03-06 11:37:14
     * id : 3025
     * img : http://192.168.2.153:8081/ctaobao//file/image/201803/20180320160026.jpg
     * keyword : null
     * list : [{"caiTag":null,"cid":null,"classify":null,"createTime":null,"id":"3049","img":"http://192.168.2.153:8081/ctaobao//file/image/201804/20180425114630.jpg","keyword":null,"list":null,"name":"运动鞋","number":null,"pid":"3025","recommendId":null,"recommendList":null,"recommendName":null,"rid":1,"sex":null,"shopTypeImg":null,"stick":null,"toShopNum":null,"typeSex":null},{"caiTag":null,"cid":null,"classify":null,"createTime":null,"id":"3217","img":"http://192.168.2.153:8081/ctaobao//file/image/201804/20180425114638.jpg","keyword":null,"list":null,"name":"懒人鞋","number":null,"pid":"3025","recommendId":null,"recommendList":null,"recommendName":null,"rid":2,"sex":null,"shopTypeImg":null,"stick":null,"toShopNum":null,"typeSex":null},{"caiTag":null,"cid":null,"classify":null,"createTime":null,"id":"3048","img":"http://192.168.2.153:8081/ctaobao//file/image/201804/20180425114720.jpg","keyword":null,"list":null,"name":"板鞋","number":null,"pid":"3025","recommendId":null,"recommendList":null,"recommendName":null,"rid":3,"sex":null,"shopTypeImg":null,"stick":null,"toShopNum":null,"typeSex":null},{"caiTag":null,"cid":null,"classify":null,"createTime":null,"id":"3050","img":"http://192.168.2.153:8081/ctaobao//file/image/201803/20180307153616.jpg","keyword":null,"list":null,"name":"帆布鞋","number":null,"pid":"3025","recommendId":null,"recommendList":null,"recommendName":null,"rid":4,"sex":null,"shopTypeImg":null,"stick":null,"toShopNum":null,"typeSex":null},{"caiTag":null,"cid":null,"classify":null,"createTime":null,"id":"3051","img":"http://192.168.2.153:8081/ctaobao//file/image/201803/20180307153610.jpg","keyword":null,"list":null,"name":"靴子","number":null,"pid":"3025","recommendId":null,"recommendList":null,"recommendName":null,"rid":5,"sex":null,"shopTypeImg":null,"stick":null,"toShopNum":null,"typeSex":null},{"caiTag":null,"cid":null,"classify":null,"createTime":null,"id":"3052","img":"http://192.168.2.153:8081/ctaobao//file/image/201803/20180307153604.jpg","keyword":null,"list":null,"name":"皮鞋","number":null,"pid":"3025","recommendId":null,"recommendList":null,"recommendName":null,"rid":6,"sex":null,"shopTypeImg":null,"stick":null,"toShopNum":null,"typeSex":null},{"caiTag":null,"cid":null,"classify":null,"createTime":null,"id":"3053","img":"http://192.168.2.153:8081/ctaobao//file/image/201804/20180425094736.png","keyword":null,"list":null,"name":"拖鞋","number":null,"pid":"3025","recommendId":null,"recommendList":null,"recommendName":null,"rid":7,"sex":null,"shopTypeImg":null,"stick":null,"toShopNum":null,"typeSex":null},{"caiTag":null,"cid":null,"classify":null,"createTime":null,"id":"3054","img":"http://192.168.2.153:8081/ctaobao//file/image/201803/20180307153546.jpeg","keyword":null,"list":null,"name":"品牌馆","number":null,"pid":"3025","recommendId":null,"recommendList":null,"recommendName":null,"rid":8,"sex":null,"shopTypeImg":null,"stick":null,"toShopNum":null,"typeSex":null}]
     * name : 男鞋
     * number : null
     * pid : null
     * recommendId : null
     * recommendList : []
     * recommendName : null
     * rid : 2
     * sex : null
     * shopTypeImg : [{"cid":null,"createTime":"2018-04-25 11:46:07","id":null,"img":"http://192.168.2.153:8081/ctaobao//file/image/201804/20180425114734.jpg","imgType":null,"keyword":null,"rid":"0","shopNum":null,"shopTypeId":"3025"},{"cid":null,"createTime":"2018-04-25 11:46:17","id":null,"img":"http://192.168.2.153:8081/ctaobao//file/image/201804/20180425114744.jpg","imgType":null,"keyword":null,"rid":"0","shopNum":null,"shopTypeId":"3025"}]
     * stick : null
     * toShopNum : null
     * typeSex : 1
     */

    private Object caiTag;
    private Object cid;
    private Object classify;
    private String createTime;
    private String id;
    private String img;
    private Object keyword;
    private String name;
    private Object number;
    private Object pid;
    private Object recommendId;
    private Object recommendName;
    private int rid;
    private Object sex;
    private Object stick;
    private Object toShopNum;
    private String typeSex;
    private List<ListBean> list;
    private List<?> recommendList;
    private List<ShopTypeImgBean> shopTypeImg;

    public Object getCaiTag() {
        return caiTag;
    }

    public void setCaiTag(Object caiTag) {
        this.caiTag = caiTag;
    }

    public Object getCid() {
        return cid;
    }

    public void setCid(Object cid) {
        this.cid = cid;
    }

    public Object getClassify() {
        return classify;
    }

    public void setClassify(Object classify) {
        this.classify = classify;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Object getKeyword() {
        return keyword;
    }

    public void setKeyword(Object keyword) {
        this.keyword = keyword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getNumber() {
        return number;
    }

    public void setNumber(Object number) {
        this.number = number;
    }

    public Object getPid() {
        return pid;
    }

    public void setPid(Object pid) {
        this.pid = pid;
    }

    public Object getRecommendId() {
        return recommendId;
    }

    public void setRecommendId(Object recommendId) {
        this.recommendId = recommendId;
    }

    public Object getRecommendName() {
        return recommendName;
    }

    public void setRecommendName(Object recommendName) {
        this.recommendName = recommendName;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public Object getSex() {
        return sex;
    }

    public void setSex(Object sex) {
        this.sex = sex;
    }

    public Object getStick() {
        return stick;
    }

    public void setStick(Object stick) {
        this.stick = stick;
    }

    public Object getToShopNum() {
        return toShopNum;
    }

    public void setToShopNum(Object toShopNum) {
        this.toShopNum = toShopNum;
    }

    public String getTypeSex() {
        return typeSex;
    }

    public void setTypeSex(String typeSex) {
        this.typeSex = typeSex;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public List<?> getRecommendList() {
        return recommendList;
    }

    public void setRecommendList(List<?> recommendList) {
        this.recommendList = recommendList;
    }

    public List<ShopTypeImgBean> getShopTypeImg() {
        return shopTypeImg;
    }

    public void setShopTypeImg(List<ShopTypeImgBean> shopTypeImg) {
        this.shopTypeImg = shopTypeImg;
    }

    public static class ListBean {
        /**
         * caiTag : null
         * cid : null
         * classify : null
         * createTime : null
         * id : 3049
         * img : http://192.168.2.153:8081/ctaobao//file/image/201804/20180425114630.jpg
         * keyword : null
         * list : null
         * name : 运动鞋
         * number : null
         * pid : 3025
         * recommendId : null
         * recommendList : null
         * recommendName : null
         * rid : 1
         * sex : null
         * shopTypeImg : null
         * stick : null
         * toShopNum : null
         * typeSex : null
         */

        private Object caiTag;
        private Object cid;
        private Object classify;
        private Object createTime;
        private String id;
        private String img;
        private Object keyword;
        private Object list;
        private String name;
        private Object number;
        private String pid;
        private Object recommendId;
        private Object recommendList;
        private Object recommendName;
        private int rid;
        private Object sex;
        private Object shopTypeImg;
        private Object stick;
        private Object toShopNum;
        private Object typeSex;

        public Object getCaiTag() {
            return caiTag;
        }

        public void setCaiTag(Object caiTag) {
            this.caiTag = caiTag;
        }

        public Object getCid() {
            return cid;
        }

        public void setCid(Object cid) {
            this.cid = cid;
        }

        public Object getClassify() {
            return classify;
        }

        public void setClassify(Object classify) {
            this.classify = classify;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public Object getKeyword() {
            return keyword;
        }

        public void setKeyword(Object keyword) {
            this.keyword = keyword;
        }

        public Object getList() {
            return list;
        }

        public void setList(Object list) {
            this.list = list;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getNumber() {
            return number;
        }

        public void setNumber(Object number) {
            this.number = number;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public Object getRecommendId() {
            return recommendId;
        }

        public void setRecommendId(Object recommendId) {
            this.recommendId = recommendId;
        }

        public Object getRecommendList() {
            return recommendList;
        }

        public void setRecommendList(Object recommendList) {
            this.recommendList = recommendList;
        }

        public Object getRecommendName() {
            return recommendName;
        }

        public void setRecommendName(Object recommendName) {
            this.recommendName = recommendName;
        }

        public int getRid() {
            return rid;
        }

        public void setRid(int rid) {
            this.rid = rid;
        }

        public Object getSex() {
            return sex;
        }

        public void setSex(Object sex) {
            this.sex = sex;
        }

        public Object getShopTypeImg() {
            return shopTypeImg;
        }

        public void setShopTypeImg(Object shopTypeImg) {
            this.shopTypeImg = shopTypeImg;
        }

        public Object getStick() {
            return stick;
        }

        public void setStick(Object stick) {
            this.stick = stick;
        }

        public Object getToShopNum() {
            return toShopNum;
        }

        public void setToShopNum(Object toShopNum) {
            this.toShopNum = toShopNum;
        }

        public Object getTypeSex() {
            return typeSex;
        }

        public void setTypeSex(Object typeSex) {
            this.typeSex = typeSex;
        }
    }

    public static class ShopTypeImgBean {
        /**
         * cid : null
         * createTime : 2018-04-25 11:46:07
         * id : null
         * img : http://192.168.2.153:8081/ctaobao//file/image/201804/20180425114734.jpg
         * imgType : null
         * keyword : null
         * rid : 0
         * shopNum : null
         * shopTypeId : 3025
         */

        private Object cid;
        private String createTime;
        private Object id;
        private String img;
        private Object imgType;
        private Object keyword;
        private String rid;
        private Object shopNum;
        private String shopTypeId;

        public Object getCid() {
            return cid;
        }

        public void setCid(Object cid) {
            this.cid = cid;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
            this.id = id;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public Object getImgType() {
            return imgType;
        }

        public void setImgType(Object imgType) {
            this.imgType = imgType;
        }

        public Object getKeyword() {
            return keyword;
        }

        public void setKeyword(Object keyword) {
            this.keyword = keyword;
        }

        public String getRid() {
            return rid;
        }

        public void setRid(String rid) {
            this.rid = rid;
        }

        public Object getShopNum() {
            return shopNum;
        }

        public void setShopNum(Object shopNum) {
            this.shopNum = shopNum;
        }

        public String getShopTypeId() {
            return shopTypeId;
        }

        public void setShopTypeId(String shopTypeId) {
            this.shopTypeId = shopTypeId;
        }
    }
}
