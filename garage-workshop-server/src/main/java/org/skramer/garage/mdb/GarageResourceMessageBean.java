package org.skramer.garage.mdb;

import org.codehaus.jackson.map.ObjectMapper;
import org.skramer.garage.domain.Garage;
import org.skramer.garage.domain.GarageTool;
import org.skramer.garage.domain.ResourceIdentifier;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by skramer on 9/9/16.
 * Message driven bean for garage resources
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "queue/garage"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class GarageResourceMessageBean implements MessageListener {

  @Inject
  private Garage garage;

  @Resource
  private MessageDrivenContext messageContext;

  private final static Logger logger = Logger.getLogger(GarageResourceMessageBean.class.getName());

  /**
   * Default constructor required by message bean.
   */
  public GarageResourceMessageBean() {
  }

  @Override
  public void onMessage(Message inMessage) {
    try {
      if (inMessage instanceof TextMessage) {
        logger.log(Level.INFO, "Message of type TextMessage received: {0}", ( (TextMessage) inMessage ).getText());
        handleTextMessage((TextMessage) inMessage);
      } else {
        logger.log(Level.WARNING, "Message of wrong type: {0}", inMessage.getClass().getName());
      }
    } catch (JMSException e) {
      logger.log(Level.SEVERE, "GarageResourceMessageBean.onMessage: JMSException: {0}", e.toString());
      messageContext.setRollbackOnly();
    } catch (IOException e) {
      logger.log(Level.SEVERE, "GarageResourceMessageBean.onMessage: IOException: {0}", e.toString());
      messageContext.setRollbackOnly();
    }
  }

  private void handleTextMessage(TextMessage textMessage) throws JMSException, IOException {
    final String text = textMessage.getBody(String.class);

    final ResourceIdentifier resourceIdentifier = parseTextMessage(text);
    handleResourceIdentifier(resourceIdentifier);
  }

  private ResourceIdentifier parseTextMessage(String text) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(text, ResourceIdentifier.class);
  }

  private void handleResourceIdentifier(ResourceIdentifier resourceIdentifier) {
    garage.addTool(new GarageTool(resourceIdentifier));
  }
}
