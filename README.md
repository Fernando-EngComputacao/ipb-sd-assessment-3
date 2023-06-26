# ipb-sd-assessment-3 about [Distributed Systems]
# 3rd Assignment


* Each assignment must be the sole work of the student who received it.
* Assignment will be closely monitored, and students may be asked to explain any suspicious similarities with any piece of code available.
* When you submit an assignment that is not your own original work you will get Zero _(we have some tools to identify plagio)_.


This assignment contains an _embedded evaluation application_ which will allow you to see your progress in real-time. You will need **Docker**
and a (recent) version of **IntelliJ** (some help [here](https://gitlab.estig.ipb.pt/dsys/ds-classes/-/wikis/first-steps)).

To start the assessment perform the following steps:

* Open the `docker-compose.yml` file on the root of this project and start the service(s)
  there:
    * Either by hitting the <span>&triangleright;&triangleright;</span> button to start all services;
    * Or, if you prefer to use the terminal, `cd` to the project's directory and issue - `docker-compose up`
* Use your browser to open this url - http://localhost:8989 - if all went well, you should see the assessment information
  and the completion status of each step;
* The docker output log may provide useful information on your progress, inspect it if you're having problems;


The assignment requires you to create/modify the following files:

* `src/main/java/pt/ipb/dsys/assessment/three/*` - contains set of `.java` files which you will use to implement a `SpringBoot` with `ActiveMQ producer` application.

The `ActiveMQ Broker`, along side the `consumer` of the message queue, are already running on the `docker` container you started earlier listening on
`tcp://127.0.0.1:61616`, **<u style="color: red">you don't need to start it yourself!</u>**.

The queue name is `queue-314866`.

The next section describes the implementation details you need to achieve in order to complete the assignment.

# Books

On the `src` of the project you will find the package `pt.ipb.dsys.assessment.three` containing one class -
`Main`.

The `main` method bootstraps the `SpringBoot` application, so you don't need to touch it. Implement the necessary code to retrieve a list of books from [https://fakerapi.it/api/v1/books](https://fakerapi.it/api/v1/books). Each of the books must be sent to the message queue and it will be processed by the `consumer` running in `docker`.

>
> Create the entities (POJO - Plain Old Java Object) in an online service, such as [https://json2csharp.com/code-converters/json-to-pojo](https://json2csharp.com/code-converters/json-to-pojo)
> 
> **Hint**: For ease of use, rename the `Datum` generated class to match the object's name (ex.: `Book`)   

The `consumer` in the `docker` image you started, for each book received in the queue, will send a REST `POST` request to the endpoint `http://127.0.0.1:8888/api/v1/books` and it will expect a `200` HTTP code as an answer. For that, you will need to create a `RestController` to deal with this request.

Finally, after `POST`ing all the books, the `consumer` will `GET` the list of books from `http://127.0.0.1:8888/api/v1/books`. For that, you will need to create a `RestController` to deal with this request.

In summary:

* Create a REST client to retrieve a list of Books
* Create a JMS service to send the books (**one-by-one**) to the queue
* Create a REST endpoint to receive the list of books by POST (**one-by-one**)
* Create a REST endpoint to send the list of books by GET (**all in a list**)

Upon successfull submission of the books to the queue, step `1` will be marked complete and the REST endpoint will receive one book at a time by `POST`.

After responding `200` to each POST request, step `2` will be marked complete. and the REST endpoint will be queried for the list of books.

After responding with the list of books, the step `3` will be marked complete.

<font size="5">**Good Luck!**</font>
