package com.ytxd.model.SystemManage;
import com.ytxd.model.BaseBO;
import com.ytxd.util.StringUtil;
public class SM_Timing extends BaseBO {
	private static final long serialVersionUID = 1L;
	private String id;
	private String jobname;
	private String createtime;
	private String startcreatetime;
	private String endcreatetime;
	private String methodname;
	private String status;
	private String fhtime;
	private String timeexplain;
	private String remark;
	private String parameter;
	
	

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getStartcreatetime() {
		return startcreatetime;
	}

	public void setStartcreatetime(String startcreatetime) {
		this.startcreatetime = startcreatetime;
	}

	public String getEndcreatetime() {
		return endcreatetime;
	}

	public void setEndcreatetime(String endcreatetime) {
		this.endcreatetime = endcreatetime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
		this.setidarray(id.replace("'", "").split(","));
	}

	public String getJobname() {
		return jobname;
	}

	public void setJobname(String jobname) {
		this.jobname = jobname;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getMethodname() {
		return methodname;
	}

	public void setMethodname(String methodname) {
		this.methodname = methodname;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFhtime() {
		return fhtime;
	}

	public void setFhtime(String fhtime) {
		this.fhtime = fhtime;
	}

	public String getTimeexplain() {
		return timeexplain;
	}

	public void setTimeexplain(String timeexplain) {
		this.timeexplain = timeexplain;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public SM_Timing GetdecodeUtf(){
		super.GetdecodeUtf();
		try {
			String remark=this.getRemark();
			if(StringUtil.isNotEmpty(remark)){
				remark=java.net.URLDecoder.decode(remark, "UTF-8");
				this.setRemark(remark);
			}
		} catch (Exception e) {
			System.out.println("中文转码出现问题");
		}
		return this;
	}
}
