package chain;

/**
 * @author <a href="mailto:huanhuan.zhan@ptmind.com">詹欢欢</a>
 * @since 2017/5/18 - 12:11
 */
//采购单：请求类
class PurchaseRequest {
    private double amount;  //采购金额
    private int number;  //采购单编号
    private String purpose;  //采购目的

    public PurchaseRequest(double amount, int number, String purpose) {
        this.amount = amount;
        this.number = number;
        this.purpose = purpose;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getPurpose() {
        return this.purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
}




