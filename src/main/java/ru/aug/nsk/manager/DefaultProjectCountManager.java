package ru.aug.nsk.manager;

import com.atlassian.jira.project.ProjectManager;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class DefaultProjectCountManager implements ProjectCountManager {

    private ProjectManager projectManager;

    @Inject
    public DefaultProjectCountManager(@ComponentImport ProjectManager projectManager) {
        this.projectManager = projectManager;
    }

    @Override
    public long getProjectCount() {
        return projectManager.getProjectCount();
    }
}
