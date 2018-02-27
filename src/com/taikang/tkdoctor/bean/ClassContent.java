package com.taikang.tkdoctor.bean;

import java.io.Serializable;

import org.json.JSONObject;

/**
 * @ClassName: ClassContent
 * @Description: 健康课堂model
 * @author helongfei
 * @date
 * 
 */
public class ClassContent implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -945394411370228567L;

	/**
	 * 标题
	 */
	private String title;
	/**
	 * 图片地址
	 */
	private String imgUrl;
	/**
	 * 本地图片
	 */
	private int imgLocal;
	/**
	 * 内容
	 */
	private String content;

	/**
	 * 分类标签
	 */
	private String label;

	/**
	 * 时间
	 */
	private String time;

	/**
	 * 来源
	 */
	private String source;

	/**
	 * 摘要
	 */
	private String summary;

	/**
	 * id
	 */
	private String id;

	/**
	 * 类型
	 */
	private String category;

	private String httpHost;

	private String largerImgerUrl;
	
	private String jsonData;
	
	/**
	 * 分享链接地址
	 */
	private String shareUrl;
	
	public String getShareUrl() {
		return shareUrl;
	}

	public void setShareUrl(String shareUrl) {
		this.shareUrl = shareUrl;
	}

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	public String getLargerImgerUrl() {
		return largerImgerUrl;
	}

	public void setLargerImgerUrl(String largerImgerUrl) {
		this.largerImgerUrl = largerImgerUrl;
	}

	public String getHttpHost() {
		if (httpHost != null) {
			httpHost = "http://" + httpHost;
		}
		return httpHost;
	}

	public void setHttpHost(String httpHost) {
		this.httpHost = httpHost;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImgUrl() {
		if (imgUrl == null) {
			imgUrl = "http://t3.baidu.com/it/u=4104834685,3908638169&fm=21&gp=0.jpg";
		}
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public int getImgLocal() {
		return imgLocal;
	}

	public void setImgLocal(int imgLocal) {
		this.imgLocal = imgLocal;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public ClassContent(String title, String imgUrl, int imgLocal,
			String content, String label) {
		super();
		this.title = title;
		this.imgUrl = imgUrl;
		this.imgLocal = imgLocal;
		this.content = content;
		this.label = label;
	}

	public ClassContent() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void parjson(JSONObject obj) throws Exception {
		if (!obj.isNull("category")) {
			this.category = obj.getString("category");
		}
		if (!obj.isNull("content")) {
			this.content = obj.getString("content");
		}
		if (!obj.isNull("title")) {
			this.title = obj.getString("title");
		}
		if (!obj.isNull("large_picture")) {
			this.imgUrl = obj.getString("large_picture");
		}
		if (!obj.isNull("id")) {
			this.id = obj.getString("id");
		}
		if (!obj.isNull("summary")) {
			this.summary = obj.getString("summary");
		}
		if (!obj.isNull("http_host")) {
			this.httpHost = obj.getString("http_host");
		}
		if (!obj.isNull("large_picture")) {
			this.largerImgerUrl = obj.getString("large_picture");
		}
		if (!obj.isNull("news_shere")) {
			this.shareUrl = obj.getString("news_shere");
		}
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		sb.append("\n{").append("\ncategory:").append(category)
				.append("\ncontent:").append(content).append("\ntitle:")
				.append(title).append("\nlarge_picture:").append(imgUrl)
				.append("\nid:").append(id).append("\nsummary:")
				.append(summary).append("\n}");
		return sb.toString();
	}
}
