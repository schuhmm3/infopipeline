# Info pipeline


## How to run the software
Requirements: 
* Java
* Maven 
* Docker
* Mongodb 

Build the jar: 

    mvn clean package

Build the container:
   
    docker build --build-arg JAR_FILE=target/*.jar -t com.basf/infopipeline .
    
Run the app (set $MONGO_URI , docker run): 
    
    MONGO_URI=mongodb://192.168.1.138:27017/test
    
    docker run -p 8080:8080 -e "JAVA_OPTS=-Dspring.data.mongodb.uri=$MONGO_URI" com.basf/infopipeline

    
Then you can use a basic curl command to call the different endpoints

    curl  -F zipFile=@patents.zip http://localhost:8080/api/import 

    curl -X POST http://localhost:8080/api/dropdatabase
    
You can also find (a very light) Swagger doc at http://localhost:8080/swagger-ui/index.html
    
Alternatively there is a docker-compose file to run the software with a mongo docker image (see docker-compose.yml)

## Design decisions

Java vs python, I am more comfortable with java thus I chose Java.
I went with springboot for ease of use, and production ready features. We could also have looked at other frameworks such as micronaut for example.
Spring provides and easy way to build the rest controllers needed for the API, supports Mongo via spring data (could also be easy to switch to another database using JPA as intermediate and then just switching implementations of repositories)

The zipfile for the patents is uploaded as multipartFile.
The endpoint for dropping the database is POST as we want to specify that we are taking an action. We could have used DELETE method but I felt DELETE means you are deleting an entity and really we use this a a command so I prefer POST.
Both endpoints could have been defined as contract first with OpenAPi with Swagger extension, as a mean of documenting the API.
Zipfile max size is set to 256MB with property "spring.servlet.multipart.max-file-size=256MB" so it is configurable.

 
Parsing the xml is done with a DOM parser, with the use of Xpath expressions to retrieve the elements.
Also we use JAXB mappings to extract the application-reference part as an object.
I chose this approach because judging from the size of the files there is no problem in parsing them to memory and it is easier to manipulate, if that was a problem we could try using Stax (streaming) for example.
Also the code is structured in such way that we could switch the parser implementations if we want to try another library.

Inserts in Mongo are done using the java objects, in collections Patent and NamedEntity.

Depending on the use cases we could use an xsd schema from the patents and do mappings to Java Objects via JAXB, which would allow us to work on the model more easily.

The persist part should have its own model to be able to persist according to the needs of the other applications that may use this data.

For Ner part we used Standford NLP as suggested. We only used the CoreNLP Simple API because it fitted all our requirements. A more detailed analysis of the patents might require using the full API.
For now we persist the NE for each xml file, we could add more info if we had more requirements, or even reorganize and persist by zipfile, or deduplicate, or count occurences of each NE, etc.


## Scalability and maintainability
First of all the restApi should be Async , using CompletableFuture so we can do multiple calls without blocking
Then the services for persisting and Ner should also be Async (use @Async from spring) so they do not block and the first persist and the Ner can be run in parallel.
Last persist needs to be composed on Ner completion.

Also we should split in various micros to enable scalability (database persist micro, Ner micro with more RAM since its heavy), 
Deploy containerized micros and scale accordingly using platform capabilities (Kubernetes or similar on Openshift, AWS, GKE, Azure)
Introduce more asynchronicity to avoid blocking threads and isolate I/O (namely database persists)
Use message broker for communication between micros ( Kafka or similar) [or expose rest APIs but broker approach is superior]


Maintainability should rely on a comprehensive test suite with quality assurance (SonarQube, Kiuwan or similar).
Splitting in various smaller micros also enables more maintainability as each one would have a single responsibility and less code is easier to maintain.
Quality logging and tracing are essential to maintainability, to be able to identify errors quickly and fix them accordingly (use ELK for example).

## Notes
My tests show that the Ner part is very slow, around 30s each patent and use a lot of CPU, which means processing the whole test archive of 7000 patents would take 2.5 days to complete.
It is obviously a problem and does not seem like an acceptable time.
I tried to tune the Simple Ner processor to reduce the number of annotators but it had no effect.
Maybe I need to use the more complex processor and see if performance improves, or find some configuration that would allow for better performance.

