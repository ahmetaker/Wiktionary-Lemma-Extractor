package de.ude.is.dal;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * Class {@code HibernateUtil} sets up the Hibernate session factory
 *
 * @author Firas Sabbah
 * @version 1.0
 * @since 12/23/2016.
 */
public class HibernateUtil {

    private static SessionFactory sessionFactory = null;
    private static ServiceRegistry serviceRegistry = null;

    static {
        if (sessionFactory == null) {
            try {
                configureSessionFactory();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private HibernateUtil() {

    }

    /**
     * Configures the Hibernate session factory
     *
     * @return {@link SessionFactory}
     * @throws HibernateException
     */
    private static SessionFactory configureSessionFactory() throws HibernateException {
        Configuration configuration = new Configuration();
        configuration.configure();

        Properties properties = configuration.getProperties();

        Properties prop = new Properties();
        try {
            File jarPath = new File(SessionFactory.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            String propertiesPath = jarPath.getParentFile().getAbsolutePath();
            prop.load(new FileInputStream(propertiesPath + "/app.config"));
            configuration.getProperties().setProperty("hibernate.connection.password", prop.getProperty("mysql.user", "root"));
            configuration.getProperties().setProperty("hibernate.connection.username", prop.getProperty("mysql.password", "root"));
        } catch (IOException e1) {
            System.err.println(e1.getMessage());
            System.out.println("loading default configurations");
            configuration.getProperties().setProperty("hibernate.connection.password", "root");
            configuration.getProperties().setProperty("hibernate.connection.username",  "root");
        }
        serviceRegistry = new ServiceRegistryBuilder().applySettings(properties).buildServiceRegistry();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);

        return sessionFactory;
    }

    public static Session getSession() {
        return sessionFactory.openSession();
    }
}
