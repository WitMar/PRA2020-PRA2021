package spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.services.Service;

public class MainApp {
    public static void main(String[] args) {
        ApplicationContext ctx =
                new AnnotationConfigApplicationContext(HelloWorldConfig.class);

        HelloWorld helloWorld = ctx.getBean(HelloWorld.class);
        helloWorld.setMessage("Hello World!");
        helloWorld.getMessage();

        Printer printer = ctx.getBean(Printer.class);

        helloWorld.setMessage("czesc");
        printer.sendMessage();

        Service service = ctx.getBean(Service.class);
        service.print();

        //This will give us Error as Spring does not know about newly created objects!!
        Printer p = new Printer();
        p.sendMessage();
    }
}