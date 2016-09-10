package org.skramer.garage.client.jms;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.json.Json;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;
import java.util.logging.Logger;

public class JndiJMSClient {
  private static final Logger log = Logger.getLogger(JndiJMSClient.class.getName());

  private static final String DEFAULT_CONNECTION_FACTORY = "jms/RemoteConnectionFactory";
  private static final String DEFAULT_DESTINATION = "jms/queue/garage";
  private static final String DEFAULT_USERNAME = "quickstartUser";
  private static final String DEFAULT_PASSWORD = "quickstartPwd1!";
  private static final String INITIAL_CONTEXT_FACTORY = "org.jboss.naming.remote.client.InitialContextFactory";
  private static final String PROVIDER_URL = "http-remoting://127.0.0.1:8080";

  public static void main(String[] args) {
    String message = createMessage();
    sendMessage(message);
  }

  private static String createMessage() {
    return Json.createObjectBuilder()
               .add("carType", "SEDAN")
               .add("carBrand", "OPEL")
               .add("carModel", "VECTRA")
               .build().toString();
  }

  private static void sendMessage(String message) {
    Context namingContext = null;

    try {
      namingContext = createInitialContext();
      ConnectionFactory connectionFactory = findConnectionFactory(namingContext);
      Destination destination = findDestination(namingContext);

      sendMessageToDestination(message, connectionFactory, destination);

    } catch (NamingException e) {
      log.severe(e.getMessage());
    } finally {
      if (namingContext != null) {
        try {
          namingContext.close();
        } catch (NamingException e) {
          log.severe(e.getMessage());
        }
      }
    }
  }

  private static Context createInitialContext() throws NamingException {
    final Properties env = new Properties();
    env.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
    env.put(Context.PROVIDER_URL, System.getProperty(Context.PROVIDER_URL, PROVIDER_URL));
    env.put(Context.SECURITY_PRINCIPAL, DEFAULT_USERNAME);
    env.put(Context.SECURITY_CREDENTIALS, DEFAULT_PASSWORD);
    return new InitialContext(env);
  }

  private static ConnectionFactory findConnectionFactory(Context namingContext) throws NamingException {
    String connectionFactoryString = System.getProperty("connection.factory", DEFAULT_CONNECTION_FACTORY);
    log.info("Attempting to acquire connection factory \"" + connectionFactoryString + "\"");
    ConnectionFactory connectionFactory = (ConnectionFactory) namingContext.lookup(connectionFactoryString);
    log.info("Found connection factory \"" + connectionFactoryString + "\" in JNDI");
    return connectionFactory;
  }

  private static Destination findDestination(Context namingContext) throws NamingException {
    String destinationString = System.getProperty("destination", DEFAULT_DESTINATION);
    log.info("Attempting to acquire destination \"" + destinationString + "\"");
    Destination destination = (Destination) namingContext.lookup(destinationString);
    log.info("Found destination \"" + destinationString + "\" in JNDI");
    return destination;
  }

  private static void sendMessageToDestination(String message, ConnectionFactory connectionFactory, Destination destination) {
    try (JMSContext context = connectionFactory.createContext(DEFAULT_USERNAME, DEFAULT_PASSWORD)) {
      log.info("Sending message with content: " + message);
      context.createProducer().send(destination, message);
    }
  }
}
