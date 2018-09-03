package com.tempus.gss.product.unp.api.entity.enums;

/**
 * @author ZhangBro
 * <p>
 * Unp模块的所有枚举类入口
 * 用法: 例如 想要获取支付状态:  Integer paystatus = EUnpConstant.PayStatus.NOT_PAIED.getKey()
 */
public interface EUnpConstant {
    
    /**
     * 获取对应枚举类的key
     *
     * @return Integer
     */
    Integer getKey();
    
    /**
     * 获取对应枚举类的value
     *
     * @return String
     */
    String getValue();
    
    /**
     * 根据KEY转换为对应枚举类别.
     *
     * @param key
     *         key
     * @return DataType
     */
    <E extends Enum> E format(Integer key);
    
    /**
     * 订单的状态
     */
    enum OrderStatus implements EUnpConstant {
        /** 防止空指针 */
        NULL(0, null),
        /** 1待处理; */
        READY(1, "待处理"),
        /** 2处理中; */
        PROCESSING(2, "处理中"),
        /** 3已完成; */
        DONE(3, "已完成"),
        /** 4已取消; */
        CANCELED(4, "已取消"),
        /** 5变更中 */
        CHANGING(5, "变更中");
        
        /** Key. */
        private int key;
        
        /** 名称. */
        private String value;
        
        /**
         * 构造方法
         *
         * @param key
         * @param value
         */
        OrderStatus(int key, String value) {
            this.key = key;
            this.value = value;
        }
        
        /**
         * 根据KEY转换为对应枚举类别.
         *
         * @param key
         * @return UnpOrderStatus
         */
        @Override
        public OrderStatus format(Integer key) {
            if (null == key) {
                return NULL;
            }
            for (OrderStatus item : OrderStatus.values()) {
                if (key == item.key) {
                    return item;
                }
            }
            return NULL;
        }
        
        @Override
        public Integer getKey() {
            return key;
        }
        
        @Override
        public String getValue() {
            return value;
        }
        
    }
    
    /**
     * 每个通用产品小类的字段类型
     */
    enum DataType implements EUnpConstant {
        /** 防止空指针 */
        NULL(0, null),
        /** 防止空指针 */
        TEXT(1, "文本"),
        /** 防止空指针 */
        NUMBER(2, "数字"),
        /** 防止空指针 */
        DECIMAL(3, "货币"),
        /** 防止空指针 */
        DATE(4, "日期"),
        /** 防止空指针 */
        FILE(5, "文件");
        
        /** Key. */
        private int key;
        
        /** 名称. */
        private String value;
        
        /**
         * 构造方法
         *
         * @param key
         * @param value
         */
        DataType(int key, String value) {
            this.key = key;
            this.value = value;
        }
        
        @Override
        public DataType format(Integer key) {
            if (null == key) {
                return NULL;
            }
            for (DataType item : DataType.values()) {
                if (key == item.key) {
                    return item;
                }
            }
            return NULL;
        }
        
        @Override
        public Integer getKey() {
            return key;
        }
        
        @Override
        public String getValue() {
            return value;
        }
    }
    
    /**
     * 订单的变更类型  标记是否变更
     */
    enum ChangeType implements EUnpConstant {
        /** 防止空指针 */
        DEFAULT(0, null),
        
        /** 1 退 */
        REFUND(1, "退"),
        
        /** 2 改 */
        CHANGE(2, "改");
        
        /** Key. */
        private int key;
        
        /** 名称. */
        private String value;
        
        ChangeType(int key, String value) {
            this.key = key;
            this.value = value;
        }
        
        @Override
        public ChangeType format(Integer key) {
            if (null == key) {
                return DEFAULT;
            }
            for (ChangeType item : ChangeType.values()) {
                if (key == item.key) {
                    return item;
                }
            }
            return DEFAULT;
        }
        
        @Override
        public Integer getKey() {
            return key;
        }
        
        @Override
        public String getValue() {
            return value;
        }
    }
    
    /**
     * 支付状态
     */
    enum PayStatus implements EUnpConstant {
        /** 防止空指针 */
        DEFAULT(1, "未支付"),
        
        /** 1 未支付 */
        NOT_PAIED(1, "未支付"),
        
        /** 2 支付中 */
        PAYING(2, "支付中"),
        
        /** 3 已支付 */
        PAIED(3, "已支付"),
        
        /** 4 挂账支付 */
        BALANCE_PAIED(4, "挂账支付");
        
        /** Key. */
        private int key;
        
        /** 名称. */
        private String value;
        
        PayStatus(int key, String value) {
            this.key = key;
            this.value = value;
        }
        
        @Override
        public PayStatus format(Integer key) {
            if (null == key) {
                return DEFAULT;
            }
            for (PayStatus item : PayStatus.values()) {
                if (key == item.key) {
                    return item;
                }
            }
            return DEFAULT;
        }
        
        @Override
        public Integer getKey() {
            return key;
        }
        
        @Override
        public String getValue() {
            return value;
        }
    }
    
    /**
     * 支付状态
     */
    enum ForType implements EUnpConstant {
        /** 防止空指针 */
        DEFAULT(0, "父类别"),
        /** 防止空指针 */
        PARENT(0, "父类别"),
        
        /** 1 子类别 */
        CHILD(1, "子类别");
        
        /** Key. */
        private int key;
        
        /** 名称. */
        private String value;
        
        ForType(int key, String value) {
            this.key = key;
            this.value = value;
        }
        
        @Override
        public ForType format(Integer key) {
            if (null == key) {
                return DEFAULT;
            }
            for (ForType item : ForType.values()) {
                if (key == item.key) {
                    return item;
                }
            }
            return DEFAULT;
        }
        
        @Override
        public Integer getKey() {
            return key;
        }
        
        @Override
        public String getValue() {
            return value;
        }
    }
    
}
