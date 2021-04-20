package model;

public class Reimbursement {

    int reimbId;
    double reimbAmount;
    String reimbSubmitted;
    String reimbResolved;
    String reimbDescription;
    byte[] receipt;
    String reimbAuthor;
    String reimbResolver;
    String reimbStatus;
    String reimbType;
    String fileFormat;

    public Reimbursement() {
    }

    public Reimbursement(double reimbAmount, String reimbResolved, String reimbDescription, byte[] receipt, String reimbAuthor, String reimbResolver, String reimbStatus, String reimbType) {
        this.reimbAmount = reimbAmount;
        this.reimbResolved = reimbResolved;
        this.reimbDescription = reimbDescription;
        this.receipt = receipt;
        this.reimbAuthor = reimbAuthor;
        this.reimbResolver = reimbResolver;
        this.reimbStatus = reimbStatus;
        this.reimbType = reimbType;
    }

    public Reimbursement(int reimbId, double reimbAmount, String reimbSubmitted, String reimbResolved, String reimbDescription, byte[] receipt, String reimbAuthor, String reimbResolver, String reimbStatus, String reimbType) {
        this.reimbId = reimbId;
        this.reimbAmount = reimbAmount;
        this.reimbSubmitted = reimbSubmitted;
        this.reimbResolved = reimbResolved;
        this.reimbDescription = reimbDescription;
        this.receipt = receipt;
        this.reimbAuthor = reimbAuthor;
        this.reimbResolver = reimbResolver;
        this.reimbStatus = reimbStatus;
        this.reimbType = reimbType;
    }

    public Reimbursement(int reimbId, double reimbAmount, String reimbSubmitted, String reimbResolved, String reimbDescription, byte[] receipt, String reimbAuthor, String reimbResolver, String reimbStatus, String reimbType, String fileFormat) {
        this.reimbId = reimbId;
        this.reimbAmount = reimbAmount;
        this.reimbSubmitted = reimbSubmitted;
        this.reimbResolved = reimbResolved;
        this.reimbDescription = reimbDescription;
        this.receipt = receipt;
        this.reimbAuthor = reimbAuthor;
        this.reimbResolver = reimbResolver;
        this.reimbStatus = reimbStatus;
        this.reimbType = reimbType;
        this.fileFormat = fileFormat;
    }

    public int getReimbId() { return reimbId; }
    public double getReimbAmount() { return reimbAmount; }
    public String getReimbSubmitted() { return reimbSubmitted; }
    public String getReimbResolved() { return reimbResolved; }
    public String getReimbDescription() { return reimbDescription; }
    public byte[] getReceipt() { return receipt; }
    public String getReimbAuthor() { return reimbAuthor; }
    public String getReimbResolver() { return reimbResolver; }
    public String getReimbStatus() { return reimbStatus; }
    public String getReimbType() { return reimbType; }
    public String getFileFormat() { return fileFormat; }

    public void setReimbId(int reimbId) { this.reimbId = reimbId; }
    public void setReimbAmount(double reimbAmount) { this.reimbAmount = reimbAmount; }
    public void setReimbSubmitted(String reimbSubmitted) { this.reimbSubmitted = reimbSubmitted; }
    public void setReimbResolved(String reimbResolved) { this.reimbResolved = reimbResolved; }
    public void setReimbDescription(String reimbDescription) { this.reimbDescription = reimbDescription; }
    public void setReceipt(byte[] receipt) { this.receipt = receipt; }
    public void setReimbAuthor(String reimbAuthor) { this.reimbAuthor = reimbAuthor; }
    public void setReimbResolver(String reimbResolver) { this.reimbResolver = reimbResolver; }
    public void setReimbStatus(String reimbStatus) { this.reimbStatus = reimbStatus; }
    public void setReimbType(String reimbType) { this.reimbType = reimbType; }
    public void setFileFormat(String fileFormat) { this.fileFormat = fileFormat; }

    @Override
    public String toString() {
        return "Reimbursement{" +
                "reimbId=" + reimbId +
                ", reimbAmount=" + reimbAmount +
                ", reimbSubmitted='" + reimbSubmitted + '\'' +
                ", reimbResolved='" + reimbResolved + '\'' +
                ", reimbDescription='" + reimbDescription + '\'' +
                ", receipt=" + receipt +
                ", reimbAuthor=" + reimbAuthor +
                ", reimbResolver=" + reimbResolver +
                ", reimbStatusId=" + reimbStatus +
                ", reimbTypeId=" + reimbType +
                '}';
    }
}
