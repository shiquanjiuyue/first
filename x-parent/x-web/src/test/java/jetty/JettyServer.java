package jetty;

import java.net.URL;
import java.net.URLClassLoader;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.log.StdErrLog;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * @author hongxia.huhx SnsJettyServer for Jetty
 */
public class JettyServer {

    /** port */
    private static final int PORT = 8080;

    /**
     * 服务器启动。
     *
     */
    private void start() {

        // 设置Jetty日志
        System.setProperty("org.eclipse.jetty.util.log.class", StdErrLog.class.getName());

        Server server = new Server();

        // 设置连接器
        HttpConfiguration config = new HttpConfiguration();
        config.setRequestHeaderSize(16246);
        ServerConnector connector = new ServerConnector(server,new HttpConnectionFactory(config));
        connector.setReuseAddress(true);
        connector.setIdleTimeout(30000);
        connector.setPort(PORT);
        server.setConnectors(new Connector[] { connector });
        String projectUrl = Class.class.getClass().getResource("/").getPath();
        // 设置context
        WebAppContext context = new WebAppContext();
        context.setResourceBase(projectUrl+"../../src/main/webapp");
        context.setContextPath("/x-web");
        context.setDefaultsDescriptor(projectUrl+"../../src/test/java/jetty/webdefault.xml");
        // PS:嵌入式的Jetty，应用当前工程的ClassPath，如果不设置将使用WebAppClassLoader，WEB-INF/lib目录加载jar。
        //context.setClassLoader(Thread.currentThread().getContextClassLoader());
        // PS:URLClassLoader支持jstl
        ClassLoader jspClassLoader = new URLClassLoader(new URL[0], this.getClass().getClassLoader());
        context.setClassLoader(jspClassLoader);
        context.setParentLoaderPriority(true);
        server.setHandler(context);

        // 启动Server
        try {
            server.start();
            server.join();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        JettyServer ccaServer = new JettyServer();

        ccaServer.start();
    }
}