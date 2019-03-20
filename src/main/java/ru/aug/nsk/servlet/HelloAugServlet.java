package ru.aug.nsk.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.aug.nsk.manager.ProjectCountManager;

import com.atlassian.jira.plugin.webresource.JiraWebResourceManager;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.templaterenderer.TemplateRenderer;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Named
public class HelloAugServlet extends HttpServlet {

    // Enable logging by adding "ru.aug.nsk" on "Logging and profiling page" (/secure/admin/ViewLogging.jspa)
    private static final Logger log = LoggerFactory.getLogger(HelloAugServlet.class);

    private TemplateRenderer templateRenderer;
    private ProjectCountManager projectCountManager;
    private JiraWebResourceManager webResourceManager;
    private JiraAuthenticationContext authContext;

    @Inject
    public HelloAugServlet(@ComponentImport TemplateRenderer templateRenderer,
                           @ComponentImport JiraAuthenticationContext authContext,
                           ProjectCountManager projectCountManager) {
        log.debug("<init>(templateRenderer={}, authContext={}, projectCountManager={])");

        this.authContext = authContext;
        this.templateRenderer = templateRenderer;
        this.projectCountManager = projectCountManager;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.debug("doGet(request={}, response={})", request, response);

        if (!authContext.isLoggedInUser()) {
            log.debug("User not logged in");
            return;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("webResourceManager", webResourceManager);
        params.put("projectCount", projectCountManager.getProjectCount());

        templateRenderer.render("templates/hello.vm", params, response.getWriter());
        response.setContentType("text/html");
    }
}