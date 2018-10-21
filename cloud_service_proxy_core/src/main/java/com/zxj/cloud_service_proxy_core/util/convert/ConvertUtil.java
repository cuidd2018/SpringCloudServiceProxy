package com.zxj.cloud_service_proxy_core.util.convert;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zxj.cloud_service_proxy_core.constant.IntEnumConstant;
import com.zxj.cloud_service_proxy_core.util.invoke.Decoder;
import com.zxj.cloud_service_proxy_core.util.invoke.Encoder;
import java.util.ArrayList;
import java.util.List;

public class ConvertUtil {

    private  static Gson gson=createGson();

    private static Gson createGson() {
        GsonBuilder gb = new GsonBuilder();
        gb.registerTypeAdapterFactory(new EnumAdapterFactory());
        Gson gson = gb.create();
        return gson;
    }

    private static Decoder decoder;
    private static Encoder encoder;



    public static Decoder getDecoder() {
        if(decoder==null)decoder= (row, clazz) -> gson.fromJson(row, clazz);
        return decoder;
    }

    public static Encoder getEncoder() {
       if(encoder==null)encoder= object -> gson.toJson(object);
        return encoder;
    }



    /**
     * 产品状态
     */
    public  enum ProductStatus implements IntEnumConstant {

        STATUS_WILL_PUBLISH("预售中", -5),//预售
        STATUS_LOCK("锁定中", -10),//锁定

        STATUS_RELEASE_MONEY_CONFIRM("放款审核", -2),
        STATUS_PUBLISH_CONFIRM("发布审核", -1),

        STATUS_UN_PUBLISH("未发布", 1),
        STATUS_PUBLISHED("已发布", 2),
        STATUS_FAILED("已流标", 3),
        STATUS_FULL("已满标", 4),

        STATUS_FULL_RELEASE_MONEY("已放款", 5),
        STATUS_FULL_RECEIVE_MONEY_PROFIT("已收息", 6),
        STATUS_FULL_RECEIVE_ALL("已收本金", 7),
        STATUS_PAY_INCOME("已派息", 8),


        STATUS_PAY_BACK("已还款", 9);

        private String name;

        private Integer value;

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








    public static void main(String[] args) throws Exception {
        List<ProductStatus> status = new ArrayList<>();
        status.add(ProductStatus.STATUS_PUBLISHED);
        status.add(ProductStatus.STATUS_WILL_PUBLISH);
        String s= getEncoder().encoder(status);
        System.out.println(s);


        List<ProductStatus> obj = (List<ProductStatus>) getDecoder().decoder(s,status.getClass());
        System.out.println(obj.get(0).getName());
        System.out.println(obj);
    }


}
