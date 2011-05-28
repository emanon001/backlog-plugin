package hudson.plugins.backlog;

import hudson.Extension;
import hudson.model.AbstractProject;
import hudson.model.Descriptor;
import hudson.scm.RepositoryBrowser;
import hudson.scm.SubversionRepositoryBrowser;
import hudson.scm.SubversionChangeLogSet.LogEntry;
import hudson.scm.SubversionChangeLogSet.Path;

import java.io.IOException;
import java.net.URL;

import org.kohsuke.stapler.DataBoundConstructor;

public class BacklogRepositoryBrowser extends SubversionRepositoryBrowser {

	@DataBoundConstructor
	public BacklogRepositoryBrowser() {
	}

	/**
	 * Gets a Backlog project property configured for the current project.
	 */
	private BacklogProjectProperty getProjectProperty(LogEntry cs) {
		AbstractProject<?, ?> p = (AbstractProject<?, ?>) cs.getParent().build
				.getProject();

		return p.getProperty(BacklogProjectProperty.class);
	}

	@Extension
	public static final class DescriptorImpl extends
			Descriptor<RepositoryBrowser<?>> {
		public DescriptorImpl() {
			super(BacklogRepositoryBrowser.class);
		}

		public String getDisplayName() {
			return "Backlog";
		}
	}

	@Override
	public URL getDiffLink(Path path) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URL getFileLink(Path path) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URL getChangeSetLink(LogEntry changeSet) throws IOException {
		BacklogProjectProperty property = getProjectProperty(changeSet);
		if (property.spaceURL == null || property.projectURL == null) {
			return null;
		}

		// TODO プロパティから引っ張ってくる
		String project = "PRIVATE";

		return new URL(property.spaceURL + "rev/" + project + "/"
				+ changeSet.getRevision());
	}

}