package cn.kgc.quiz;

import cn.kgc.quiz.dao.UserDao;
import cn.kgc.quiz.dao.impl.UserDaoImpl;
import cn.kgc.quiz.domain.QuizUser;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.servlet.ServletContainer;

public class QuizServer {
    private final static String PATH = "/*";
    private final static String CONTEXT_PATH = "/api/v1";
    private final static String PACKAGE_PATH = "cn.kgc.quiz.res,cn.kgc.quiz.filter";
    private final static String CLASS_NAMES = "org.glassfish.jersey.media.multipart.MultiPartFeature";


    public static void main(String[] args) throws Exception {
        //通过调用dao，使hibernate初始加载
        UserDao userDao = new UserDaoImpl();
        userDao.get(1l);

        int port = 8099;
        Server server = new Server(port);
        ServletHolder sh = new ServletHolder(ServletContainer.class);
        sh.setInitParameter(ServerProperties.PROVIDER_PACKAGES, PACKAGE_PATH);
        sh.setInitParameter(ServerProperties.PROVIDER_CLASSNAMES, CLASS_NAMES);
        sh.setInitParameter(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, "true");

        ServletContextHandler contextHandler =
                new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.addServlet(sh, PATH);
        contextHandler.setContextPath(CONTEXT_PATH);
        server.setHandler(contextHandler);
        server.start();
    }
}
