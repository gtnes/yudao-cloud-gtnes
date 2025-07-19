package cn.iocoder.yudao.module.infra.controller.admin.softwareManage.vo;

public class CheckNewVerReqVO {
    private String deviceFingerprint;
    private String name;
    private Integer platform;
    private String platformName;
    private String osRelease;
    private String osArch;
    private Integer appType;
    private String appVersion;
    // getter/setter
    public String getDeviceFingerprint() { return deviceFingerprint; }
    public void setDeviceFingerprint(String deviceFingerprint) { this.deviceFingerprint = deviceFingerprint; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getPlatform() { return platform; }
    public void setPlatform(Integer platform) { this.platform = platform; }
    public String getPlatformName() { return platformName; }
    public void setPlatformName(String platformName) { this.platformName = platformName; }
    public String getOsRelease() { return osRelease; }
    public void setOsRelease(String osRelease) { this.osRelease = osRelease; }
    public String getOsArch() { return osArch; }
    public void setOsArch(String osArch) { this.osArch = osArch; }
    public Integer getAppType() { return appType; }
    public void setAppType(Integer appType) { this.appType = appType; }
    public String getAppVersion() { return appVersion; }
    public void setAppVersion(String appVersion) { this.appVersion = appVersion; }
} 