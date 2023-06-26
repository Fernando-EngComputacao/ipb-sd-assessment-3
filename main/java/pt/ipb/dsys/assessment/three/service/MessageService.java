package pt.ipb.dsys.assessment.three.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import pt.ipb.dsys.assessment.three.model.MessageDto;

import javax.jms.TextMessage;

@Service
public class MessageService {
    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);

    private final JmsTemplate jmsTemplate;

    private final ObjectMapper objectMapper;

    public MessageService(JmsTemplate jmsTemplate,
                          ObjectMapper objectMapper) {
        this.jmsTemplate = jmsTemplate;
        this.objectMapper = objectMapper;
    }

    @JmsListener(destination = "queue-314866")
    public void handeMessage(TextMessage textMessage) throws Exception {
        String json = textMessage.getText();
        MessageDto message = objectMapper.readValue(json, MessageDto.class);
        logger.info("Received message from {} -> {}", message.getFrom(), message.getMessage());
    }

    public void sendMessage(MessageDto dto) {
        try {
            String json = objectMapper.writeValueAsString(dto);
            jmsTemplate.convertAndSend("queue-314866", json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }



}
