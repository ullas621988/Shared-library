import java.io.BufferedReader
import java.util.function.Function
import java.util.regex.Pattern
import java.util.stream.Collectors
import java.util.*
import java.io.*
import java.util.List


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


def BuildDet = new BuildDetails()
String header = "App ID,App Name,Release Version,Environments Passed,Environment Failed,Comments";
Pattern pattern = Pattern.compile(",");

BufferedReader filecontent = new BufferedReader(new FileReader("D:\\Jenkins\\Jenkins\\workspace\\Pipeline-CSVReader\\Input.csv"));
List<BuildDetails> buildStatus = filecontent.lines().skip(1).map({m ->
				String[] x = pattern.split(m);
				println "${x[0]}, ${x[1]}, ${x[2]}, ${x[3]}, ${x[4]}"
				return new BuildDetails(Integer.parseInt(x[0]), x[1], x[2], x[3], x[4]);
			}).collect(Collectors.toList());
StringBuilder sb = new StringBuilder();
sb.append(header);
Map<String, List<BuildDetails>> buildStatusMap = buildStatus.stream()
.collect(Collectors.groupingBy(BuildDetails.&groupBy as Function));
for (Map.Entry<String, List<BuildDetails>> entry : buildStatusMap.entrySet()) {
	System.out.println(entry.getKey());
	List<BuildDetails> buildList = entry.getValue();
	//System.out.println(entry.getValue());
	List<BuildDetails> failedList = buildList.stream().filter({f -> f.getStatus().contains("Failed")})
	.collect(Collectors.toList());
	List<BuildDetails> passedList = buildList.stream().filter({f -> !f.getStatus().contains("Failed")})
	.collect(Collectors.toList());
	String delimitter = ",";
	String passedEnvList = "NA";
	String failedEnvList = "NA";
	String comments = "NA";
	if (passedList != null && passedList.size() > 0) {
		passedEnvList = passedList.stream().map({mp -> mp.getEnvironment()}).collect(Collectors.joining("|"));
		//comments = passedList.stream().map(mp -> mp.getStatus()).collect(Collectors.joining("|"));
		comments="Passed";
	}
	if (failedList != null && failedList.size() > 0) {
		failedEnvList = failedList.stream().map({mp -> mp.getStatus()}).collect(Collectors.joining("|"));
		//comments = failedList.stream().map(mp -> mp.getStatus()).collect(Collectors.joining("|"));
		comments="Failed";
	}
	sb.append('\n');
	sb.append(buildList.get(0).getAppId() + delimitter);
	sb.append(buildList.get(0).getAppName() + delimitter);
	sb.append(buildList.get(0).getReleaseVersion() + delimitter);
	sb.append(passedEnvList + delimitter);
	sb.append(failedEnvList + delimitter);
	sb.append(comments + delimitter);


}
PrintWriter writer;
try {
	writer = new PrintWriter(new File("D:\\Jenkins\\Jenkins\\workspace\\Pipeline-CSVReader\\Release_Status.csv"))
	writer.write(sb.toString());
	System.out.println("done!");

}

catch (FileNotFoundException e) {
	System.out.println(e.getMessage());
}
finally {
	writer.close();
}
