package com.citi.nam.abanumber.domain.model;

import java.io.Serializable;

public class GemfireData implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 967670566012057968L;
    private String ani;
    private String dnis;
    private String extension;
    private String startTime;
    private String baseCin;
    private String accountNumber;
    private String someDataElse;
    private String someElse;
    private int failureCount;
    private boolean flag;
    private long maxttl;
    
    public long getMaxttl() {
		return maxttl;
	}
	public void setMaxttl(long maxttl) {
		this.maxttl = maxttl;
	}
	public String getAni() {
        return ani;
    }
    public void setAni(String ani) {
        this.ani = ani;
    }
    public String getDnis() {
        return dnis;
    }
    public void setDnis(String dnis) {
        this.dnis = dnis;
    }
    public String getExtension() {
        return extension;
    }
    public void setExtension(String extension) {
        this.extension = extension;
    }
    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    public String getBaseCin() {
        return baseCin;
    }
    public void setBaseCin(String baseCin) {
        this.baseCin = baseCin;
    }
    public String getAccountNumber() {
        return accountNumber;
    }
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    public String getSomeDataElse() {
        return someDataElse;
    }
    public void setSomeDataElse(String someDataElse) {
        this.someDataElse = someDataElse;
    }
    public String getSomeElse() {
        return someElse;
    }
    public void setSomeElse(String someElse) {
        this.someElse = someElse;
    }
    public int getFailureCount() {
        return failureCount;
    }
    public void setFailureCount(int failureCount) {
        this.failureCount = failureCount;
    }
    public boolean isFlag() {
        return flag;
    }
    public void setFlag(boolean flag) {
        this.flag = flag;
    }
    
}
