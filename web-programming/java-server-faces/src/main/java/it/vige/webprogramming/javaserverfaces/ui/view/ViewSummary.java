package it.vige.webprogramming.javaserverfaces.ui.view;

import static java.util.Calendar.DATE;
import static java.util.Calendar.getInstance;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;

import it.vige.rubia.ForumsModule;
import it.vige.rubia.auth.SecureActionForum;
import it.vige.rubia.model.Topic;
import it.vige.webprogramming.javaserverfaces.auth.AuthorizationListener;
import it.vige.webprogramming.javaserverfaces.ui.BaseController;
import it.vige.webprogramming.javaserverfaces.ui.action.PreferenceController;

@Named("summary")
@RequestScoped
public class ViewSummary extends BaseController {

	private static final long serialVersionUID = 6950361977869824L;

	@EJB
	private ForumsModule forumsModule;

	@Inject
	private PreferenceController userPreferences;

	private Collection<Topic> topics;

	@SecureActionForum
	@Interceptors(AuthorizationListener.class)
	public Collection<Topic> getTopics() {
		if (topics == null) {
			topics = new ArrayList<Topic>();
		}
		return topics;
	}

	public String getNumberOfTopicsFound() {
		return String.valueOf(getTopics().size());
	}

	public PreferenceController getUserPreferences() {
		return userPreferences;
	}

	public void setUserPreferences(PreferenceController userPreferences) {
		this.userPreferences = userPreferences;
	}

	@PostConstruct
	public void execute() {
		try {
			loadDefaultTopics();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getBlockTopicsType() {
		return userPreferences.getSummaryMode().name();
	}

	private void loadDefaultTopics() throws Exception {
		Calendar after = getInstance();
		after.add(DATE, -userPreferences.getSummaryTopicDays());
		Date time = after.getTime();
		int summaryTopicReplies = userPreferences.getSummaryTopicReplies();
		int summaryTopicLimit = userPreferences.getSummaryTopicLimit();

		int forumInstanceId = userPreferences.getForumInstanceId();

		if (forumsModule.findForumInstanceById(forumInstanceId) == null)
			forumsModule.createForumInstance(forumInstanceId, "by_manual_preferences");

		switch (userPreferences.getSummaryMode()) {
		case BLOCK_TOPICS_MODE_HOT_TOPICS:
			topics = forumsModule.findTopicsHot(summaryTopicReplies, summaryTopicLimit, forumInstanceId);
			break;
		case BLOCK_TOPICS_MODE_HOTTEST_TOPICS:
			topics = forumsModule.findTopicsHottest(time, summaryTopicLimit, forumInstanceId);
			break;
		case BLOCK_TOPICS_MODE_LATEST_POSTS:
			topics = forumsModule.findTopicsByLatestPosts(summaryTopicLimit, forumInstanceId);
			break;
		case BLOCK_TOPICS_MODE_MOST_VIEWED:
			topics = forumsModule.findTopicsMostViewed(time, summaryTopicLimit, forumInstanceId);
			break;
		}
	}
}
