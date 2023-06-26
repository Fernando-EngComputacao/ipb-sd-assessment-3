package pt.ipb.dsys.assessment.three.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pt.ipb.dsys.assessment.three.model.Root;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class BookService {

    private RestTemplate restTemplate;

    public BookService() {
        restTemplate = new RestTemplate();
    }


        public Root getBook() {
            return restTemplate.getForObject("https://fakerapi.it/api/v1/books", Root.class);
        }



}
