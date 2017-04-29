package it.vige.webprogramming.javaserverfaces.ui.action;

import static it.vige.webprogramming.javaserverfaces.ui.view.SummaryMode.BLOCK_TOPICS_MODE_LATEST_POSTS;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;

import it.vige.webprogramming.javaserverfaces.ui.BaseController;
import it.vige.webprogramming.javaserverfaces.ui.view.SummaryMode;

@Named("prefController")
@SessionScoped
public class PreferenceController extends BaseController {

	private static final long serialVersionUID = -3891010378190343197L;

	private boolean notifyOnReply = true;
	private boolean alwaysAllowHtml = true;
	private String postOrder = "ascending";
	private String dateFormat = "EEE MMM d, yyyy HH:mm aaa";
	private boolean alwaysAddSignature = false;
	private String signature;
	private SummaryMode summaryMode = BLOCK_TOPICS_MODE_LATEST_POSTS;
	private int summaryTopicLimit = 6;
	private int summaryTopicDays = 20;
	private int summaryTopicReplies = 15;
	private int forumInstanceId = 1;
	private int postsPerTopic = 15;
	private int topicsPerForum = 10;

	public boolean isNotifyOnReply() {
		return this.notifyOnReply;
	}

	public void setNotifyOnReply(boolean notifyOnReply) {
		this.notifyOnReply = notifyOnReply;
	}

	public boolean isAlwaysAllowHtml() {
		return alwaysAllowHtml;
	}

	public void setAlwaysAllowHtml(boolean alwaysAllowHtml) {
		this.alwaysAllowHtml = alwaysAllowHtml;
	}

	public String getPostOrder() {
		return postOrder;
	}

	public void setPostOrder(String postOrder) {
		this.postOrder = postOrder;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
			simpleDateFormat.format(new Date());
		} catch (Exception e) {
			return;
		}

		this.dateFormat = dateFormat;
	}

	public boolean isAlwaysAddSignature() {
		return alwaysAddSignature;
	}

	public void setAlwaysAddSignature(boolean alwaysAddSignature) {
		this.alwaysAddSignature = alwaysAddSignature;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public SummaryMode getSummaryMode() {
		return summaryMode;
	}

	public void setSummaryMode(SummaryMode summaryMode) {
		this.summaryMode = summaryMode;
	}

	public int getSummaryTopicLimit() {
		return summaryTopicLimit;
	}

	public void setSummaryTopicLimit(int summaryTopicLimit) {
		this.summaryTopicLimit = summaryTopicLimit;
	}

	public int getSummaryTopicDays() {
		return this.summaryTopicDays;
	}

	public void setSummaryTopicDays(int summaryTopicDays) {
		this.summaryTopicDays = summaryTopicDays;
	}

	public int getSummaryTopicReplies() {
		return summaryTopicReplies;
	}

	public void setSummaryTopicReplies(int summaryTopicReplies) {
		this.summaryTopicReplies = summaryTopicReplies;
	}

	public int getPostsPerTopic() {
		return postsPerTopic;
	}

	public void setPostsPerTopic(int postsPerTopic) {
		this.postsPerTopic = postsPerTopic;
	}

	public int getTopicsPerForum() {
		return topicsPerForum;
	}

	public void setTopicsPerForum(int topicsPerForum) {
		this.topicsPerForum = topicsPerForum;
	}

	public int getForumInstanceId() {
		return forumInstanceId;
	}

	public void setForumInstanceId(int forumInstanceId) {
		this.forumInstanceId = forumInstanceId;
	}

	public void cleanup() {
	}

	public String execute() {
		return SUCCESS;
	}

	public SelectItem[] getSummaryModeValues() {
		SelectItem[] items = new SelectItem[SummaryMode.values().length];
		int i = 0;
		for (SummaryMode g : SummaryMode.values()) {
			items[i++] = new SelectItem(g, g.toString());
		}
		return items;
	}

}