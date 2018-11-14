package com.zxj.cloud_service_proxy_core.util.convert;

import com.alibaba.fastjson.JSON;
import com.zxj.cloud_service_proxy_core.constant.IntEnumConstant;
import com.zxj.cloud_service_proxy_core.util.enums.EnableScanEnumTable;
import com.zxj.cloud_service_proxy_core.util.invoke.Decoder;
import com.zxj.cloud_service_proxy_core.util.invoke.Encoder;

public class ConvertUtil {
    // private static ObjectMapper objectMapper = new ObjectMapper(new MessagePackFactory());

    private static Decoder decoder;
    private static Encoder encoder;


    public static Decoder getDecoder() {
        if (decoder == null) {
            //decoder= (row, clazz) -> gson.fromJson(row, clazz);
            //MTypeReference cc = new MType<String>();
            decoder = (row, clazz) -> {
                //byte[] b = Base64.getDecoder().decode(row);
                //cc.set_type(clazz);
                // Object o = objectMapper.readValue(b, cc);
                Object o = JSON.parseObject(row, clazz, null);
                return o;
            };
        }
        return decoder;
    }

    public static Encoder getEncoder() {
        if (encoder == null) {
            //encoder= object -> gson.toJson(object);
            encoder = object -> {
                // byte[] b = objectMapper.writeValueAsBytes(object);
                // return Base64.getEncoder().encodeToString(b);
                return JSON.toJSONString(object);
            };
        }
        return encoder;
    }


    /**
     * 产品状态
     */
    public static class ProductStatus implements IntEnumConstant {
        public static class Table {
            public ProductStatus STATUS_WILL_PUBLISH = new ProductStatus("预售中", -5);//预售

            public ProductStatus STATUS_LOCK = new ProductStatus("锁定中", -10);//锁定

            public ProductStatus STATUS_RELEASE_MONEY_CONFIRM = new ProductStatus("放款审核", -2);

            public ProductStatus STATUS_PUBLISH_CONFIRM = new ProductStatus("发布审核", -1);

            public ProductStatus STATUS_UN_PUBLISH = new ProductStatus("未发布", 1);

            public ProductStatus STATUS_PUBLISHED = new ProductStatus("已发布", 2);

            public ProductStatus STATUS_FAILED = new ProductStatus("已流标", 3);

            public ProductStatus STATUS_FULL = new ProductStatus("已满标", 4);

            public ProductStatus STATUS_FULL_RELEASE_MONEY = new ProductStatus("已放款", 5);

            public ProductStatus STATUS_FULL_RECEIVE_MONEY_PROFIT = new ProductStatus("已收息", 6);

            public ProductStatus STATUS_FULL_RECEIVE_ALL = new ProductStatus("已收本金", 7);

            public ProductStatus STATUS_PAY_INCOME = new ProductStatus("已派息", 8);

            public ProductStatus STATUS_PAY_BACK = new ProductStatus("已还款", 9);
        }

        @EnableScanEnumTable
        public static Table table = new Table();

        private String name;

        private Integer value;

        ProductStatus() {
        }

        ProductStatus(String name, Integer value) {
            this.name = name;
            this.value = value;
        }


        public String getName() {
            return name;
        }

        public Integer getValue() {
            return value;
        }

        @Override
        public void setValue(Integer integer) {
            this.value = integer;

        }

        @Override
        public void setName(String name) {
            this.name = name;

        }
    }

}


//abstract class MTypeReference<T> extends TypeReference {
//    protected Type _type;
//
//    public MTypeReference() {
//
//    }
//
//    public MTypeReference set_type(Type _type) {
//        this._type = _type;
//        return this;
//    }
//
//    public Type getType() {
//        return this._type;
//    }
//
//}
//
//
//class MType<T> extends MTypeReference<T> {
//}