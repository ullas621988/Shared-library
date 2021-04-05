class BuildDetails {
	def appId;
	def appName;
	def environment;
	def releaseVersion;
	def status;
	BuildDetails() {}
	BuildDetails(def appId, def appName, def environment, def releaseVersion, def status) {
		super();
		this.appId = appId;
		this.appName = appName;
		this.environment = environment;
		this.releaseVersion = releaseVersion;
		this.status = status;
	}
	int getAppId() {
		return appId;
	}
	void setAppId(int appId) {
		this.appId = appId;
	}
	def  getAppName() {
		return appName;
	}
	def setAppName(String appName) {
		this.appName = appName;
	}
	def getEnvironment() {
		return environment;
	}
	def setEnvironment(String environment) {
		this.environment = environment;
	}
	def getReleaseVersion() {
		return releaseVersion;
	}
	def setReleaseVersion(String releaseVersion) {
		this.releaseVersion = releaseVersion;
	}
	def getStatus() {
		return status;
	}
	def setStatus(String status) {
		this.status = status;
	}

	def groupBy() {
		return getAppId() + "-" + getAppName() + "-" + getReleaseVersion();
	}
}
