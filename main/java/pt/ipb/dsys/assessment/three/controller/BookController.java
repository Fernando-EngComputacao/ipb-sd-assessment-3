package pt.ipb.dsys.assessment.three.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pt.ipb.dsys.assessment.three.model.Book;
import pt.ipb.dsys.assessment.three.model.MessageDto;
import pt.ipb.dsys.assessment.three.model.Root;
import pt.ipb.dsys.assessment.three.service.BookService;
import pt.ipb.dsys.assessment.three.service.MessageService;

import javax.jms.Connection;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/")
public class BookController {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);
    private final BookService bookService;
    private final MessageService messageService;
//    private ArrayList<Book> book = new ArrayList<>();

    ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");

    public BookController(BookService bookService, MessageService messageService) {
        this.bookService = bookService;
        this.messageService = messageService;
    }

    @GetMapping("books")
    public ArrayList<Book> getBookList(){
        Root root = bookService.getBook();

        for (Book dataBook : root.getData()) {
            sendProducer(dataBook);
        }

        return root.data;
    }

    @PostMapping("books")
    public Book getBook(@RequestBody Book book) {
        return book;
    }

    @PostMapping
    public void post(@RequestBody MessageDto messageDto){
        messageService.sendMessage(messageDto);

    }

    private void sendProducer(Book dataBook) {
        try {
            Connection connection = factory.createConnection();
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue("queue-314866");
            MessageProducer producer = session.createProducer(queue);
            ActiveMQTextMessage message = new ActiveMQTextMessage();

            ObjectMapper mapper = new ObjectMapper();
            message.setText(mapper.writeValueAsString(dataBook));
            producer.send(message);

            producer.close();
            session.close();
            connection.close();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }


}
