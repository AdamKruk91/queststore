package view;

public class ErrorControllerView extends AbstractView{

    public String getErrorPage(){
        return createTwigWithoutArgs("templates/error-page.twig");
    }
}
